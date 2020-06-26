/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.wholesale.services;

import br.com.vrfortaleza.upextension.wholesale.models.Client;
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
public class ClientServiceTest {
    
    public ClientServiceTest()
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
     * Test of all method, of class ClientService.
     */
    @Test
    public void testAll()
    {
        System.out.println("all");
        ClientService instance = new ClientService();
        List<Client> expResult = null;
        List<Client> result = instance.all();
        
        for (Client client : result) {
            System.out.println(client);
        }
        
        assertNotEquals(expResult, result);
    }

    /**
     * Test of find method, of class ClientService.
     */
    @Test
    public void testFind()
    {
        System.out.println("find");
        int id = 100;
        
        ClientService instance = new ClientService();
        Client expResult = null;
        Client result = instance.find(id);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of exists method, of class ClientService.
     */
    @Test
    public void testExists()
    {
        System.out.println("exists");
        Client model = new Client();
        model.setId(100);
        
        ClientService instance = new ClientService();
        boolean expResult = true;
        boolean result = instance.exists(model);
        
        assertNotEquals(expResult, result);
        
    }
    
}
