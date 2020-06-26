/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.services;

import br.com.vrfortaleza.upextension.repositories.impl.FileSalePeriodRepository;
import br.com.vrfortaleza.upextension.models.report.SalePeriodReport;
import br.com.vrfortaleza.upextension.utilities.NumberUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;

/**
 * Date: Mar 2, 2018.
 *
 * @author Derick Felix
 */
public class SalePeriodService {

    private final FileSalePeriodRepository repository;

    public SalePeriodService()
    {
        this.repository = new FileSalePeriodRepository();
    }

    public List<String[]> getRows(String file)
    {
        List<String[]> data = repository.read(file);
        for (String[] row : data) {
            calculate(row);
        }
        return data;
    }

    private void calculate(String[] row)
    {
        // PIS/COFINS Recolher
        double pisCofins = NumberUtil.toDouble(row[11]);
        double pisCofinsDebito = NumberUtil.toDouble(row[15]);
        row[row.length - 3] = NumberUtil.convertCurrencyFormat(pisCofinsDebito - pisCofins);
        // Custo c/ imposto economico
        double cic = NumberUtil.toDouble(row[2]);
        double cie = (pisCofinsDebito - pisCofins) + cic;
        row[row.length - 2] = NumberUtil.convertCurrencyFormat(cie);
        // Lucro economico
        double venda = NumberUtil.toDouble(row[3]);
        double lucroEconomico = venda - cie;
        row[row.length - 1] = NumberUtil.convertCurrencyFormat(lucroEconomico);
        // Margem Sobre Venda row[7]
        double mrgSbVenda = (lucroEconomico * 100) / venda;
        row[7] = NumberUtil.addPercentageSign(mrgSbVenda);
    }

    public Map<String, String> getTotalSum(JTable table, List<String[]> rows)
    {
        Map<String, String> totalMap = new LinkedHashMap<>();

        for (int i = 1; i < table.getColumnCount(); i++) {
            double sum = 0;
            boolean isPercent = false;

            for (String[] row : rows) {
                String number;
                if (row[i].contains("%")) {
                    if (i != 8) { // part. venda
                        isPercent = true;
                    }
                    number = NumberUtil.removePercentageSign(row[i]);
                } else {
                    number = row[i];
                }

                sum += NumberUtil.toDouble(number);
            }

            String result;

            if (isPercent) {
                sum /= rows.size();
                result = NumberUtil.addPercentageSign(sum);
            } else {
                result = NumberUtil.convertCurrencyFormat(sum);
                // "Quantidade" is not currency, but weight format
                if (table.getColumnName(i).equals("Quantidade")) {
                    result += 0;
                }
            }

            totalMap.put(table.getColumnName(i), result);
        }
        return totalMap;
    }

    public List<SalePeriodReport> getVendas(List<String[]> rows)
    {
        List<SalePeriodReport> salePeriodReports = new ArrayList<>();
        for (String[] row : rows) {
            SalePeriodReport spr = new SalePeriodReport();
            spr.setDate(row[0]);
            spr.setQuantity(row[1]);
            spr.setCost(row[2]);
            spr.setRevenue(row[3]);
            spr.setRevenueMargin(NumberUtil.removePercentageSign(row[4]));
            spr.setProfitMargin(NumberUtil.removePercentageSign(row[5]));
            spr.setMarginOverCost(NumberUtil.removePercentageSign(row[6]));
            spr.setMarginOverRevenue(NumberUtil.removePercentageSign(row[7]));
            spr.setParticipation(NumberUtil.removePercentageSign(row[8]));
            spr.setPisCofinsCollect(row[22]);
            spr.setEconomicCostWithTax(row[23]);
            spr.setEconomicProfit(row[24]);
          
            salePeriodReports.add(spr);
        }
        return salePeriodReports;
    }

    public Map<String, Object> getReportParameters(Map<String, String> totalSum)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("totalQtd", totalSum.get("Quantidade"));
        params.put("totalTC", totalSum.get("Custo c/ Imposto"));
        params.put("totalTV", totalSum.get("Venda"));
        params.put("totalML", NumberUtil.removePercentageSign(totalSum.get("Margem Líquida")));
        params.put("totalMB", NumberUtil.removePercentageSign(totalSum.get("Margem Bruta")));
        params.put("totalMSC", NumberUtil.removePercentageSign(totalSum.get("Margem Sb Custo")));
        params.put("totalMSV", NumberUtil.removePercentageSign(totalSum.get("Margem Sb Venda")));
        params.put("totalPart", totalSum.get("Part. Venda"));
        params.put("totalPCR", totalSum.get("PIS/COFINS Recolher"));
        params.put("totalCIE", totalSum.get("Custo c/ Imposto Economico"));
        params.put("totalLE", totalSum.get("Lucro Economico"));

        return params;
    }

}
