/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.models.report;

import java.util.List;

/**
 * All the products that are supposed to be a double are in the format of String
 * because of jasper report format.
 *
 * @author derickfelix
 */
public class SubgroupReport {

    private String description;
    private String quantity;
    private String cost;
    private String icms;
    private String pisCofins;
    private String profit;
    private String revenue;
    private String profitMargin;
    private String revenueMargin;
    private String marginOverCost;
    private String marginOverRevenue;
    private List<ProductReport> products;

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public String getCost()
    {
        return cost;
    }

    public void setCost(String cost)
    {
        this.cost = cost;
    }

    public String getIcms()
    {
        return icms;
    }

    public void setIcms(String icms)
    {
        this.icms = icms;
    }

    public String getPisCofins()
    {
        return pisCofins;
    }

    public void setPisCofins(String pisCofins)
    {
        this.pisCofins = pisCofins;
    }

    public String getProfit()
    {
        return profit;
    }

    public void setProfit(String profit)
    {
        this.profit = profit;
    }

    public String getRevenue()
    {
        return revenue;
    }

    public void setRevenue(String revenue)
    {
        this.revenue = revenue;
    }

    public String getProfitMargin()
    {
        return profitMargin;
    }

    public void setProfitMargin(String profitMargin)
    {
        this.profitMargin = profitMargin;
    }

    public String getRevenueMargin()
    {
        return revenueMargin;
    }

    public void setRevenueMargin(String revenueMargin)
    {
        this.revenueMargin = revenueMargin;
    }

    public String getMarginOverCost()
    {
        return marginOverCost;
    }

    public void setMarginOverCost(String marginOverCost)
    {
        this.marginOverCost = marginOverCost;
    }

    public String getMarginOverRevenue()
    {
        return marginOverRevenue;
    }

    public void setMarginOverRevenue(String marginOverRevenue)
    {
        this.marginOverRevenue = marginOverRevenue;
    }

    public List<ProductReport> getProducts()
    {
        return products;
    }

    public void setProducts(List<ProductReport> products)
    {
        this.products = products;
    }

}
