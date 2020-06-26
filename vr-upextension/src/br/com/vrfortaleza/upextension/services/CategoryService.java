/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.services;

import br.com.vrfortaleza.upextension.repositories.impl.FileProductRepository;
import br.com.vrfortaleza.upextension.models.Product;
import br.com.vrfortaleza.upextension.models.Subgroup;
import br.com.vrfortaleza.upextension.models.report.ProductReport;
import br.com.vrfortaleza.upextension.models.report.SubgroupReport;
import br.com.vrfortaleza.upextension.utilities.NumberUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author derickfelix
 */
public class CategoryService {

    private final FileProductRepository repository;
    private final List<Subgroup> subgroups;

    public CategoryService()
    {
        this.repository = new FileProductRepository();
        this.subgroups = new ArrayList<>();
    }

    public List<Product> read(String path)
    {
        return this.repository.read(path);
    }

    public List<String[]> asRows(List<Product> ps)
    {
        List<String[]> rows = new ArrayList<>(ps.size());
        // last sub group
        Subgroup lsg = null;
        for (Product p : ps) {
            String[] row = new String[13];

            // if the product is of a new sub-group, add it before
            if (lsg == null || lsg != p.getSubgroup()) {
                lsg = p.getSubgroup();

                row[0] = lsg.toString();
                row[1] = Integer.toString((int) lsg.getQuantity());
                row[2] = NumberUtil.convertCurrencyFormat(lsg.getCost());
                row[3] = NumberUtil.convertCurrencyFormat(lsg.getRevenue());
                row[4] = NumberUtil.convertCurrencyFormat(lsg.getIcms());
                row[5] = NumberUtil.convertCurrencyFormat(lsg.getPisCofins());
                row[6] = NumberUtil.addPercentageSign(lsg.getProfitMargin());
                row[7] = NumberUtil.addPercentageSign(lsg.getRevenueMargin());
                row[8] = NumberUtil.addPercentageSign(lsg.getMarginOverCost());
                row[9] = NumberUtil.addPercentageSign(lsg.getMarginOverRevenue());
                row[10] = NumberUtil.convertCurrencyFormat(lsg.getProfit());
                row[11] = "";
                row[12] = "";
                rows.add(row);

                row = new String[13];
                subgroups.add(lsg);
            }
            // add product
            String pid = NumberUtil.fixedDigits(6, Long.toString(p.getId()));
            row[0] = "               " + pid + " - " + p.getName();
            row[1] = Integer.toString((int) p.getQuantity());
            row[2] = NumberUtil.convertCurrencyFormat(p.getCost());
            row[3] = NumberUtil.convertCurrencyFormat(p.getRevenue());
            row[4] = NumberUtil.convertCurrencyFormat(p.getIcms());
            row[5] = NumberUtil.convertCurrencyFormat(p.getPisCofins());
            row[6] = NumberUtil.addPercentageSign(p.getProfitMargin());
            row[7] = NumberUtil.addPercentageSign(p.getRevenueMargin());
            row[8] = NumberUtil.addPercentageSign(p.getMarginOverCost());
            row[9] = NumberUtil.addPercentageSign(p.getMarginOverRevenue());
            row[10] = NumberUtil.convertCurrencyFormat(p.getProfit());
            row[11] = NumberUtil.addPercentageSign(p.getPartRevenue());
            row[12] = NumberUtil.addPercentageSign(p.getPartCategory());

            lsg = p.getSubgroup();

            rows.add(row);
        }

        return rows;
    }

    public String[] getRowTotal()
    {
        if (!subgroups.isEmpty()) {
            String[] row = new String[9];
            double[] total = new double[5];

            for (Subgroup sb : subgroups) {
                total[0] += sb.getCost();
                total[1] += sb.getRevenue();
                total[2] += sb.getIcms();
                total[3] += sb.getPisCofins();
                total[4] += sb.getProfit();
            }

            row[0] = NumberUtil.convertCurrencyFormat(total[0]);
            row[1] = NumberUtil.convertCurrencyFormat(total[1]);
            row[2] = NumberUtil.convertCurrencyFormat(total[2]);
            row[3] = NumberUtil.convertCurrencyFormat(total[3]);
            ///////////////////////// Again!! //////////////////////////////////
            // Profit Margin = (Profit / Revenue) * 100;
            row[4] = NumberUtil.addPercentageSign((total[4] / total[1]) * 100);
            // Revenue Margin = (Profit - ? / Revenue) * 100;
            row[5] = row[4];
            // Margin Over Cost = ((Revenue * 100) / Cost) - 100;
            row[6] = NumberUtil.addPercentageSign(((total[1] * 100) / total[0]) - 100);
            // Margin Over Revenue = 100 - ((Cost * 100) / Revenue);
            row[7] = NumberUtil.addPercentageSign(100 - ((total[0] * 100) / total[1]));
            ////////////////////////////////////////////////////////////////////
            row[8] = NumberUtil.convertCurrencyFormat(total[4]);

            return row;
        }

        return null;
    }

    public List<SubgroupReport> getSubgroupReports(List<String[]> rows)
    {
        List<SubgroupReport> srs = new ArrayList<>(subgroups.size());
        SubgroupReport lsr = null;

        for (String[] row : rows) {
            if (Character.isAlphabetic(row[0].charAt(0))) {
                SubgroupReport sr = new SubgroupReport();
                sr.setDescription(row[0]);
                sr.setQuantity(row[1]);
                sr.setCost(row[2]);
                sr.setRevenue(row[3]);
                sr.setIcms(row[4]);
                sr.setPisCofins(row[5]);
                sr.setProfitMargin(row[6]);
                sr.setRevenueMargin(row[7]);
                sr.setMarginOverCost(row[8]);
                sr.setMarginOverRevenue(row[9]);
                sr.setProfit(row[10]);
                sr.setProducts(new ArrayList<ProductReport>());
                srs.add(sr);

                lsr = sr;
            } else if (lsr != null) {
                ProductReport pr = new ProductReport();
                pr.setDescription(row[0].replace("         ", ""));
                pr.setQuantity(row[1]);
                pr.setCost(row[2]);
                pr.setRevenue(row[3]);
                pr.setIcms(row[4]);
                pr.setPisCofins(row[5]);
                pr.setProfitMargin(row[6]);
                pr.setRevenueMargin(row[7]);
                pr.setMarginOverCost(row[8]);
                pr.setMarginOverRevenue(row[9]);
                pr.setProfit(row[10]);
                pr.setPartRevenue(row[11]);
                pr.setPartCategory(row[12]);
                lsr.getProducts().add(pr);
            }
        }

        return srs;
    }

    public Map<String, Object> getTotalParams(String[] row)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("cost", row[0]);
        params.put("revenue", row[1]);
        params.put("icms", row[2]);
        params.put("pisCofins", row[3]);
        params.put("profitMargin", row[4]);
        params.put("revenueMargin", row[5]);
        params.put("marginOverCost", row[6]);
        params.put("marginOverRevenue", row[7]);
        params.put("profit", row[8]);

        return params;
    }
}
