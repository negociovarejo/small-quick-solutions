/* * Copyright (c) 2019 Negocio Varejo. All rights reserved. * */
package br.com.negocio.varejo.tag.emitter.models;

import java.util.List;

/**
 *
 * @author derickfelix
 */
public class Product {
    
    private Long id;
    private Eancode selected;
    private String description;
    private Double price1;
    private Double price2;
    private List<Eancode> eancodes;

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
        
    public Eancode getSelected() {
        return selected;
    }
    
    public void setSelected(Eancode eancode) {
        this.selected = eancode;
    }
    
    public void setSelected(int index) {
        if (index < 0 || index >= eancodes.size()) {
            return;
        }
        
        this.selected = eancodes.get(index);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice1() {
        return price1;
    }

    public void setPrice1(Double price1) {
        this.price1 = price1;
    }

    public Double getPrice2() {
        return price2;
    }

    public void setPrice2(Double price2) {
        this.price2 = price2;
    }
    
    public List<Eancode> getEancodes() {
        return eancodes;
    }

    public void setEancodes(List<Eancode> eancodes) {
        this.eancodes = eancodes;
    }
    
    public double getPrice3() {
        if (selected == null) {
            return 0;
        }

        double percentage = (100 - selected.getDiscount()) / 100;
        return price1 * percentage;
    }
}
