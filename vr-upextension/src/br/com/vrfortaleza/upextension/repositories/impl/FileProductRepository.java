/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.repositories.impl;

import br.com.vrfortaleza.upextension.models.Subgroup;
import br.com.vrfortaleza.upextension.models.Product;
import br.com.vrfortaleza.upextension.repositories.FileRepository;
import br.com.vrfortaleza.upextension.utilities.FileUtil;
import br.com.vrfortaleza.upextension.utilities.NumberUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author derickfelix
 */
public class FileProductRepository implements FileRepository<Product> {

    private final SubgroupRepository subgroupDao;
    private Subgroup subgroup;

    public FileProductRepository()
    {
        this.subgroupDao = new SubgroupRepository();
    }

    @Override
    public List<Product> read(String path)
    {
        List<String> file = FileUtil.read(path);

        if (file != null) {
            List<Product> products = new ArrayList<>(file.size() - 1);
            // remove the first line, and then determine the used separator
            String separator = FileUtil.SEPARATOR(file.remove(0));

            for (String line : file) {
                String[] tokens = line.split(separator);
                Product p = decompress(tokens);
                if (p != null) {
                    products.add(p);
                }
            }

            return products;
        }
        return null;
    }

    private Product decompress(String[] tokens)
    {
        if (!Character.isDigit(tokens[0].charAt(0))) {
            Product p = new Product();
            // code and name are in the same token, so we need to split it
            String[] cn = tokens[0].replace("               ", "").split(" - ");
            p.setId(Integer.parseInt(cn[0]));
            p.setName(cn[1]);

            p.setQuantity(NumberUtil.toDouble(tokens[1]));
            p.setCost(NumberUtil.toDouble(tokens[2]));
            p.setRevenue(NumberUtil.toDouble(tokens[3]));
            p.setIcms(NumberUtil.toDouble(tokens[4]));
            p.setPisCofins(NumberUtil.toDouble(tokens[5]));
            p.setProfitMargin(NumberUtil.toDouble(tokens[6]));
            p.setRevenueMargin(NumberUtil.toDouble(tokens[7]));
            p.setMarginOverCost(NumberUtil.toDouble(tokens[8]));
            p.setMarginOverRevenue(NumberUtil.toDouble(tokens[9]));
            p.setProfit(NumberUtil.toDouble(tokens[10]));
            p.setPartRevenue(NumberUtil.toDouble(tokens[11]));
            p.setPartCategory(NumberUtil.toDouble(tokens[12]));
            p.setSubgroup(subgroup);

            subgroup.setQuantity(subgroup.getQuantity() + p.getQuantity());
            subgroup.setCost(subgroup.getCost() + p.getCost());
            subgroup.setRevenue(subgroup.getRevenue() + p.getRevenue());
            subgroup.setIcms(subgroup.getIcms() + p.getIcms());
            subgroup.setPisCofins(subgroup.getPisCofins() + p.getPisCofins());
            subgroup.setProfit(subgroup.getProfit() + p.getProfit());
            
            return p;
        } else {
            // Compute the remainings fields before going to the next subgroup
            if (subgroup != null) {
                // Profit Margin = (Profit / Revenue) * 100;
                subgroup.setProfitMargin((subgroup.getProfit() / subgroup.getRevenue()) * 100);
                // Revenue Margin = (Profit - ? / Revenue) * 100;
                // I don't know how to calculate the revenue margin, I've tried...
                subgroup.setRevenueMargin(subgroup.getProfitMargin());
                // Margin Over Cost = ((Revenue * 100) / Cost) - 100;
                subgroup.setMarginOverCost(((subgroup.getRevenue() * 100) / subgroup.getCost()) - 100);
                // Margin Over Revenue = 100 - ((Cost * 100) / Revenue);
                subgroup.setMarginOverRevenue(100 - ((subgroup.getCost() * 100) / subgroup.getRevenue()));                
                
                //System.out.println(subgroup);
            }
            subgroup = subgroupDao.find(tokens[0]);
            //System.out.println("Seção: " + subgroup.getSection() + " Grupo: " + subgroup.getGroup() + " Sub-grupo: " + subgroup.getName());
        }

        return null;
    }
    
}
