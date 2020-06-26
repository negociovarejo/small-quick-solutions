/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.wholesale.models;

import br.com.vrfortaleza.upextension.models.Entity;

/**
 *
 * @author derickfelix
 */
public class Seller extends Entity {

    private String name;
    private double percentual;
    private double maxDiscount;
    private boolean allowChangePrice;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getPercentual()
    {
        return percentual;
    }

    public void setPercentual(double percentual)
    {
        this.percentual = percentual;
    }

    public double getMaxDiscount()
    {
        return maxDiscount;
    }

    public void setMaxDiscount(double maxDiscount)
    {
        this.maxDiscount = maxDiscount;
    }

    public boolean isAllowChangePrice()
    {
        return allowChangePrice;
    }

    public void setAllowChangePrice(boolean allowChangePrice)
    {
        this.allowChangePrice = allowChangePrice;
    }

    @Override
    public String toString()
    {
        return "Seller{" + "id=" + id + ", name=" + name + ", percentual=" + percentual + ", maxDiscount=" + maxDiscount + ", allowChangePrice=" + allowChangePrice + '}';
    }

}
