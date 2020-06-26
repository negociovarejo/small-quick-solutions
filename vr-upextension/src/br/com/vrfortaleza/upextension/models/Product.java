/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.models;

/**
 *
 * @author derickfelix
 */
public class Product extends Entity {

    private Subgroup subgroup;
    private String name;
    private double quantity;
    private double cost;
    private double icms;
    private double pisCofins;
    private double profit;
    private double revenue;
    private double profitMargin;
    private double revenueMargin;
    private double marginOverCost;
    private double marginOverRevenue;
    private double partRevenue;
    private double partCategory;

    public Subgroup getSubgroup()
    {
        return subgroup;
    }

    public void setSubgroup(Subgroup subgroup)
    {
        this.subgroup = subgroup;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getQuantity()
    {
        return quantity;
    }

    public void setQuantity(double quantity)
    {
        this.quantity = quantity;
    }

    public double getCost()
    {
        return cost;
    }

    public void setCost(double cost)
    {
        this.cost = cost;
    }

    public double getIcms()
    {
        return icms;
    }

    public void setIcms(double icms)
    {
        this.icms = icms;
    }

    public double getPisCofins()
    {
        return pisCofins;
    }

    public void setPisCofins(double pisCofins)
    {
        this.pisCofins = pisCofins;
    }

    public double getProfit()
    {
        return profit;
    }

    public void setProfit(double profit)
    {
        this.profit = profit;
    }

    public double getRevenue()
    {
        return revenue;
    }

    public void setRevenue(double revenue)
    {
        this.revenue = revenue;
    }

    public double getProfitMargin()
    {
        return profitMargin;
    }

    public void setProfitMargin(double profitMargin)
    {
        this.profitMargin = profitMargin;
    }

    public double getRevenueMargin()
    {
        return revenueMargin;
    }

    public void setRevenueMargin(double revenueMargin)
    {
        this.revenueMargin = revenueMargin;
    }

    public double getMarginOverCost()
    {
        return marginOverCost;
    }

    public void setMarginOverCost(double marginOverCost)
    {
        this.marginOverCost = marginOverCost;
    }

    public double getMarginOverRevenue()
    {
        return marginOverRevenue;
    }

    public void setMarginOverRevenue(double marginOverRevenue)
    {
        this.marginOverRevenue = marginOverRevenue;
    }

    public double getPartRevenue()
    {
        return partRevenue;
    }

    public void setPartRevenue(double partRevenue)
    {
        this.partRevenue = partRevenue;
    }

    public double getPartCategory()
    {
        return partCategory;
    }

    public void setPartCategory(double partCategory)
    {
        this.partCategory = partCategory;
    }

    @Override
    public String toString()
    {
        return "Product: {" + "id=" + id + ", name=" + name + ", quantity=" + quantity + ", cost=" + cost + ", icms=" + icms + ", pisCofins=" + pisCofins + ", profit=" + profit + ", revenue=" + revenue + ", profitMargin=" + profitMargin + ", revenueMargin=" + revenueMargin + ", marginOverCost=" + marginOverCost + ", marginOverRevenue=" + marginOverRevenue + ", partRevenue=" + partRevenue + ", partCategory=" + partCategory + '}';
    }

    
}
