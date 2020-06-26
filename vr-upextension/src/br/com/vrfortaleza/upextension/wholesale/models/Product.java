/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.wholesale.models;

import br.com.vrfortaleza.upextension.models.Entity;

/**
 *
 * @author derickfelix
 */
public class Product extends Entity {

    private String description;
    private String barcode;
    private String stock;
    private String wrapper;
    private int qtyWrapper;
    private int quantity;
    private String retailPrice;
    private String discount;
    private String wholeSalePrice;
    private String totalPrice;

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getBarcode()
    {
        return barcode;
    }

    public void setBarcode(String barcode)
    {
        this.barcode = barcode;
    }

    public String getStock()
    {
        return stock;
    }

    public void setStock(String stock)
    {
        this.stock = stock;
    }

    public String getWrapper()
    {
        return wrapper;
    }

    public void setWrapper(String wrapper)
    {
        this.wrapper = wrapper;
    }

    public int getQtyWrapper()
    {
        return qtyWrapper;
    }

    public void setQtyWrapper(int qtyWrapper)
    {
        this.qtyWrapper = qtyWrapper;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
    
    public String getRetailPrice()
    {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice)
    {
        this.retailPrice = retailPrice;
    }

    public String getDiscount()
    {
        return discount;
    }

    public void setDiscount(String discount)
    {
        this.discount = discount;
    }

    public String getWholeSalePrice()
    {
        return wholeSalePrice;
    }

    public void setWholeSalePrice(String wholeSalePrice)
    {
        this.wholeSalePrice = wholeSalePrice;
    }

    public String getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice)
    {
        this.totalPrice = totalPrice;
    }
    
    @Override
    public String toString()
    {
        return "WholesaleProduct{" + "id=" + id + ", description=" + description + ", barcode=" + barcode + ", stock=" + stock + ", wrapper=" + wrapper + ", qtyWrapper=" + qtyWrapper + ", retailPrice=" + retailPrice + ", discount=" + discount + ", wholeSalePrice=" + wholeSalePrice + '}';
    }

}
