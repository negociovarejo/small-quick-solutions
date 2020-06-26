/* * Copyright (c) 2020 Negocio Varejo. All rights reserved. * */
package br.com.negociovarejo.extensionplus.models;

/**
 *
 * @author derickfelix
 */
public class User extends Entity {

    private String username;
    private String password;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
    
    
}
