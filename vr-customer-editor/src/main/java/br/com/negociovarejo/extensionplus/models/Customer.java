/* * Copyright (c) 2020 Negocio Varejo. All rights reserved. * */
package br.com.negociovarejo.extensionplus.models;

/**
 *
 * @author derickfelix
 */
public class Customer extends Entity {

    private String name;
    private String taxPayerRegistration;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTaxPayerRegistration()
    {
        return taxPayerRegistration;
    }

    public void setTaxPayerRegistration(String taxPayerRegistration)
    {
        this.taxPayerRegistration = taxPayerRegistration;
    }
    
    
}
