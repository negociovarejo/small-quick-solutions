/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.database;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author derickfelix
 */
public class SQLQueryBuilderTest {

    public SQLQueryBuilderTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void selectWithSpecificFields()
    {
        System.out.println("SELECT WITH SPECIFIC FIELDS");
        String expResult = "select ap.a, ap.b, ap.c, ap.d, ap.e from atacado.produtos ap where id = :id";

        String result = SQLQueryBuilder
                .select("a", "b", "c", "d", "e")
                .from("atacado.produtos", "ap")
                .with("id = :id")
                .build();

        check(expResult, result);
    }

    @Test
    public void selectAll()
    {
        System.out.println("SELECT ALL");

        String expResult = "select * from atacado.produtos ap";

        String result = SQLQueryBuilder
                .select()
                .from("atacado.produtos", "ap")
                .build();

        check(expResult, result);
    }

    @Test
    public void selectWithInnerJoin()
    {
        System.out.println("SELECT WITH INNER JOIN");
        String expResult = "select pb_eventual_client.nome from atacado.vendedorcliente "
                + "ws_seller_client "
                + "inner join public.clienteeventual pb_eventual_client on "
                + "ws_seller_client.id_clienteeventual = pb_eventual_client.id "
                + "where id_vendedor = 1";

        String result = SQLQueryBuilder.select().from("atacado.vendedorcliente", "ws_seller_client")
                .innerJoin("public.clienteeventual","pb_eventual_client", 
                        "ws_seller_client.id_clienteeventual = pb_eventual_client.id", "nome")
                .with("id_vendedor = 1")
                .build();

        check(expResult, result);
    }

    @Test
    public void testMultipleQueries()
    {
        System.out.println("MULTIPLE QUERIES");
        String q1 = "select mq.a, mq.b, mq.c, mq.d from multiple.queries mq";
        String q2 = "select mq.a, mq.b, mq.c, mq.d from multiple.queries mq where id = 2";
        String q3 = "select * from multiple.queries mq";

        SQLQueryBuilder mq1 = SQLQueryBuilder.select("a", "b", "c", "d").from("multiple.queries", "mq");
        SQLQueryBuilder mq2 = SQLQueryBuilder.select().from("multiple.queries", "mq");

        String r1 = mq1.build();
        String r2 = mq1.with("id = 2").build();
        String r3 = mq2.build();

        check(q1, r1);
        check(q2, r2);
        check(q3, r3);
    }

    @Test
    public void testOneInnerJoin()
    {
        System.out.println("ONE INNER JOIN");
        String exp = "select "
                + "a.a as a_a, a.b as a_b, "
                + "b.a as b_a, b.b as b_b "
                + "from table_a a "
                + "inner join table_b b on a.b = b.b";

        String rst = SQLQueryBuilder.select("a as a_a", "b as a_b").from("table_a", "a")
                .innerJoin("table_b", "b", "a.b = b.b","a as b_a", "b as b_b").build();

        check(exp, rst);
    }

    @Test
    public void testTwoInnerJoins()
    {
        System.out.println("TWO INNER JOINS");
        String exp = "select "
                + "a.a as a_a, a.b as a_b, a.c as a_c, "
                + "b.a as b_a, b.b as b_b, b.c as b_c, "
                + "c.a as c_a, c.b as c_b, c.c as c_c "
                + "from table_a a "
                + "inner join table_b b on a.b = b.b "
                + "inner join table_c c on a.c = c.c";

        String rst = SQLQueryBuilder.select("a as a_a", "b as a_b", "c as a_c").from("table_a", "a")
                .innerJoin("table_b", "b", "a.b = b.b", "a as b_a", "b as b_b", "c as b_c")
                .innerJoin("table_c", "c", "a.c = c.c", "a as c_a", "b as c_b", "c as c_c")
                .build();

        check(exp, rst);
    }

    private void check(String exp, String rst)
    {
        System.out.println(exp);
        System.out.println(rst);
        assertEquals(exp, rst);
    }

}
