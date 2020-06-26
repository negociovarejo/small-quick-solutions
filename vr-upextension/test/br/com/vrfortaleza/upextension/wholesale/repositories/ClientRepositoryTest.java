/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.wholesale.repositories;

import br.com.vrfortaleza.upextension.wholesale.models.Client;
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
public class ClientRepositoryTest {
    
    public ClientRepositoryTest()
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
     * Test of all method, of class ClientRepository.
     */
    @Test
    public void testAll()
    {
        System.out.println("all");
        ClientRepository instance = new ClientRepository();
        List<Client> expResult = null;
        List<Client> result = instance.all();
        
        for (Client client : result) {
            System.out.println(client);
        }
        
        assertNotEquals(expResult, result);
    }

    /**
     * Test of find method, of class ClientRepository.
     */
    @Test
    public void testFind()
    {
        System.out.println("find");
        int id = 1;
        ClientRepository instance = new ClientRepository();
        Client expResult = null;
        
        Client result = instance.find(id);
        
        System.out.println(result);
        
        assertNotEquals(expResult, result);
    }
    
    @Test
    public void testAllBySeller()
    {
        System.out.println("TEST ALL BY SELLER");
        
        Seller seller = new Seller();
        seller.setId(1);
        
        ClientRepository instance = new ClientRepository();
        List<Client> clients = instance.allBySeller(seller);
        
        for (Client client : clients) {
            System.out.println(client);
        }
    }
    
}
