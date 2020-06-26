/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.wholesale.models;

import br.com.vrfortaleza.upextension.models.Entity;

/**
 *
 * @author derickfelix
 */
public class Client extends Entity {
    
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Client{" + "id=" + id + ", name=" + name + '}';
    }
    
}
