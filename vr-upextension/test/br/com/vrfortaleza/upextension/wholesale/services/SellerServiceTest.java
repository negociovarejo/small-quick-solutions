/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.wholesale.services;

import br.com.vrfortaleza.upextension.wholesale.models.Seller;
import java.util.List;
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
public class SellerServiceTest {
    
    public SellerServiceTest()
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

    /**
     * Test of all method, of class SellerService.
     */
    @Test
    public void testAll()
    {
        System.out.println("all");
        SellerService instance = new SellerService();

        for (Seller seller : instance.all()) {
            System.out.println(seller);
        }
    }

    /**
     * Test of find method, of class SellerService.
     */
    @Test
    public void testFind()
    {
        System.out.println("find");
        int id = 2;
        SellerService instance = new SellerService();
        Seller expResult = new Seller();
        expResult.setId(id);

        Seller result = instance.find(id);
        System.out.println(result);
        assertEquals(expResult, result);
    }

    @Test
    public void testExists()
    {
        System.out.println("Exists");
        Seller seller = new Seller();
        seller.setId(18);
        
        SellerService instance = new SellerService();
        boolean expResult = true;
        
        boolean result = instance.exists(seller);
        
        assertNotEquals(result, expResult);
    }
    
    /**
     * Test of create method, of class SellerService.
     */
    @Test
    public void testCreate()
    {
        System.out.println("create");

        SellerService instance = new SellerService();
        List<Seller> sellers = instance.all();
        Seller last = sellers.get(sellers.size() - 1);

        Seller model = new Seller();
        model.setId(last.getId() + 1);
        model.setName("Vendedor " + model.getId());
        model.setMaxDiscount(0.01 + (last.getId() * 0.02));
        model.setPercentual(0.1 + (last.getId() * 0.1));
        model.setAllowChangePrice(last.getId() % 2 == 0);

        instance.create(model);
        Seller result = instance.find(model.getId());
        assertEquals(model, result);
    }

    /**
     * Test of update method, of class SellerService.
     */
    @Test
    public void testUpdate()
    {
        System.out.println("update");

        SellerService instance = new SellerService();
        List<Seller> sellers = instance.all();
        for (Seller model : sellers) {
            model.setName(model.getName().toUpperCase());
            instance.update(model);
        }
    }

    @Test
    public void testDelete()
    {
        System.out.println("delete");

        SellerService instance = new SellerService();
        List<Seller> sellers = instance.all();

        long id = sellers.get(sellers.size() - 1).getId();

        Seller unexpected = instance.find(id);

        instance.delete(id);

        Seller actual = instance.find(id);

        assertNotEquals(unexpected, actual);
    }
    
}
