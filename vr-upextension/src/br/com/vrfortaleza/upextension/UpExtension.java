/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension;

import br.com.vrfortaleza.upextension.models.report.ProductReport;
import br.com.vrfortaleza.upextension.models.report.SubgroupReport;
import br.com.vrfortaleza.upextension.utilities.MessageUtil;
import br.com.vrfortaleza.upextension.views.MainForm;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.UIManager;

/**
 * Main class, the one which starts the program.
 *
 * Date: Feb 28, 2018.
 *
 * @author Derick Felix
 */
public class UpExtension {

    public static void main(String[] args)
    {
        /* Set the System look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } else if (os.equals("Linux")) {
//                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            } else {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            MessageUtil.showException(null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run()
            {
                new MainForm().setVisible(true);
            }
        });

    }

    public static Collection<SubgroupReport> subgroups()
    {
        Collection<SubgroupReport> subgroups = new ArrayList<>(3);

        SubgroupReport s1 = new SubgroupReport();

        s1.setDescription("Seção: 1 - CERAIS Grupo: 1 - ARROZ Sub-grupo: 1 - ARROZ BRANCO");
        s1.setCost("10,00");
        s1.setIcms("0,00");
        s1.setPisCofins("23,23");
        s1.setMarginOverCost("43,00");
        s1.setMarginOverRevenue("21,24");
        s1.setProfit("231,24");
        s1.setProfitMargin("25,24");
        s1.setQuantity("1,234");
        s1.setRevenue("1444,24");
        s1.setRevenueMargin("14,54");

        SubgroupReport s2 = new SubgroupReport();
        s2.setDescription("Seção: 1 - CERAIS Grupo: 1 - ARROZ Sub-grupo: 2 - ARROZ INTEGRAL");
        s2.setCost("30,00");
        s2.setIcms("0,00");
        s2.setPisCofins("23,45");
        s2.setMarginOverCost("45,00");
        s2.setMarginOverRevenue("26,54");
        s2.setProfit("131,24");
        s2.setProfitMargin("15,34");
        s2.setQuantity("1,234");
        s2.setRevenue("3444,24");
        s2.setRevenueMargin("44,54");

        SubgroupReport s3 = new SubgroupReport();
        s3.setDescription("Seção: 1 - CERAIS Grupo: 2 - ACUCAR Sub-grupo: 1 - ACUCAR CRISTAL");
        s3.setCost("134,41");
        s3.setIcms("0,00");
        s3.setPisCofins("33,03");
        s3.setMarginOverCost("23,00");
        s3.setMarginOverRevenue("41,24");
        s3.setProfit("174,12");
        s3.setProfitMargin("23,41");
        s3.setQuantity("3,345");
        s3.setRevenue("1637,15");
        s3.setRevenueMargin("24,25");

        ////////////// Products /////////////////
        List<ProductReport> ps1 = new ArrayList<>(2);
        ProductReport a1 = new ProductReport();
        a1.setDescription("093 - ARROZ PAI JOAO 1KG BRANCO");
        a1.setIcms("0,00");
        a1.setCost("3,20");
        a1.setMarginOverCost("21,23");
        a1.setMarginOverRevenue("34,43");
        a1.setPartCategory("34,45%");
        a1.setPartRevenue("24,45%");
        a1.setPisCofins("0,00");
        a1.setProfit("23,23");
        a1.setProfitMargin("1,34");
        a1.setQuantity("23,54");
        a1.setRevenueMargin("23,12");
        a1.setRevenue("302,12");
        ProductReport a2 = new ProductReport();
        a2.setDescription("9392 - ARROZ TOP 1KG BRANCO");
        a2.setIcms("1,00");
        a2.setCost("2,20");
        a2.setMarginOverCost("19,03");
        a2.setMarginOverRevenue("26,32");
        a2.setPartCategory("21,21%");
        a2.setPartRevenue("23,52%");
        a2.setPisCofins("0,00");
        a2.setProfit("23,23");
        a2.setProfitMargin("1,34");
        a2.setQuantity("23,54");
        a2.setRevenueMargin("23,12");
        a2.setRevenue("205,28");
        ProductReport a3 = new ProductReport();
        a3.setDescription("9392 - ARROZ TOP 1KG BRANCO");
        a3.setIcms("1,00");
        a3.setCost("2,20");
        a3.setMarginOverCost("19,03");
        a3.setMarginOverRevenue("26,32");
        a3.setPartCategory("21,21%");
        a3.setPartRevenue("23,52%");
        a3.setPisCofins("0,00");
        a3.setProfit("23,23");
        a3.setProfitMargin("1,34");
        a3.setQuantity("23,54");
        a3.setRevenueMargin("23,12");
        a3.setRevenue("205,28");
        ProductReport a4 = new ProductReport();
        a4.setDescription("9392 - ARROZ TOP 1KG BRANCO");
        a4.setIcms("1,00");
        a4.setCost("2,20");
        a4.setMarginOverCost("19,03");
        a4.setMarginOverRevenue("26,32");
        a4.setPartCategory("21,21%");
        a4.setPartRevenue("23,52%");
        a4.setPisCofins("0,00");
        a4.setProfit("23,23");
        a4.setProfitMargin("1,34");
        a4.setQuantity("23,54");
        a4.setRevenueMargin("23,12");
        a4.setRevenue("205,28");
        ProductReport a5 = new ProductReport();
        a5.setDescription("9392 - ARROZ TOP 1KG BRANCO");
        a5.setIcms("1,00");
        a5.setCost("2,20");
        a5.setMarginOverCost("19,03");
        a5.setMarginOverRevenue("26,32");
        a5.setPartCategory("21,21%");
        a5.setPartRevenue("23,52%");
        a5.setPisCofins("0,00");
        a5.setProfit("23,23");
        a5.setProfitMargin("1,34");
        a5.setQuantity("23,54");
        a5.setRevenueMargin("23,12");
        a5.setRevenue("205,28");
        ProductReport a6 = new ProductReport();
        a6.setDescription("9392 - ARROZ TOP 1KG BRANCO");
        a6.setIcms("1,00");
        a6.setCost("2,20");
        a6.setMarginOverCost("19,03");
        a6.setMarginOverRevenue("26,32");
        a6.setPartCategory("21,21%");
        a6.setPartRevenue("23,52%");
        a6.setPisCofins("0,00");
        a6.setProfit("23,23");
        a6.setProfitMargin("1,34");
        a6.setQuantity("23,54");
        a6.setRevenueMargin("23,12");
        a6.setRevenue("205,28");
        ProductReport a7 = new ProductReport();
        a7.setDescription("9392 - ARROZ TOP 1KG BRANCO");
        a7.setIcms("1,00");
        a7.setCost("2,20");
        a7.setMarginOverCost("19,03");
        a7.setMarginOverRevenue("26,32");
        a7.setPartCategory("21,21%");
        a7.setPartRevenue("23,52%");
        a7.setPisCofins("0,00");
        a7.setProfit("23,23");
        a7.setProfitMargin("1,34");
        a7.setQuantity("23,54");
        a7.setRevenueMargin("23,12");
        a7.setRevenue("205,28");

        ps1.add(a1);
        ps1.add(a2);
        ps1.add(a3);
        ps1.add(a4);
        ps1.add(a5);
        ps1.add(a6);
        ps1.add(a7);

        List<ProductReport> ps2 = new ArrayList<>(2);
        ProductReport at1 = new ProductReport();
        at1.setDescription("000201 - ARROZ PRECIOSO 1KG INTEGRAL");
        at1.setIcms("0,00");
        at1.setCost("3,20");
        at1.setMarginOverCost("21,23");
        at1.setMarginOverRevenue("34,43");
        at1.setPartCategory("34,45%");
        at1.setPartRevenue("24,45%");
        at1.setPisCofins("0,00");
        at1.setProfit("23,23");
        at1.setProfitMargin("1,34");
        at1.setQuantity("23,54");
        at1.setRevenue("235,28");
        
        ProductReport at2 = new ProductReport();
        at2.setDescription("0001345 - ARROZ CAMIL 1KG INTEGRAL PARBOLIZADO");
        at2.setIcms("0,00");
        at2.setCost("3,20");
        at2.setMarginOverCost("21,23");
        at2.setMarginOverRevenue("34,43");
        at2.setPartCategory("34,45%");
        at2.setPartRevenue("24,45%");
        at2.setPisCofins("0,00");
        at2.setProfit("23,23");
        at2.setProfitMargin("1,34");
        at2.setQuantity("23,54");
        at2.setRevenue("235,28");
        ps2.add(at1);
        ps2.add(at2);

        List<ProductReport> ps3 = new ArrayList<>(2);
        ProductReport ac1 = new ProductReport();
        ac1.setDescription("005214 - ACUCAR MESTRE VITO 1KG CRISTAL");
        ac1.setIcms("0,00");
        ac1.setCost("3,20");
        ac1.setMarginOverCost("21,23");
        ac1.setMarginOverRevenue("34,43");
        ac1.setPartCategory("34,45%");
        ac1.setPartRevenue("24,45%");
        ac1.setPisCofins("0,00");
        ac1.setProfit("23,23");
        ac1.setProfitMargin("1,34");
        ac1.setQuantity("23,54");
        ProductReport ac2 = new ProductReport();
        ac2.setIcms("0,00");
        ac2.setCost("3,20");
        ac2.setMarginOverCost("21,23");
        ac2.setMarginOverRevenue("34,43");
        ac2.setPartCategory("34,45%");
        ac2.setPartRevenue("24,45%");
        ac2.setPisCofins("0,00");
        ac2.setProfit("23,23");
        ac2.setProfitMargin("1,34");
        ac2.setQuantity("23,54");
        ac2.setDescription("9332 - ACUCAR ALTEZA 1KG CRISTAL");

        ps3.add(ac1);
        ps3.add(ac2);

        s1.setProducts(ps1);
        s2.setProducts(ps2);
        s3.setProducts(ps3);

        subgroups.add(s1);
        subgroups.add(s2);
        subgroups.add(s3);

        return subgroups;
    }
    
    public static Collection<ProductReport> products()
    {
        Collection<ProductReport> ps = new ArrayList<>(2);
        ProductReport ac1 = new ProductReport();
        ac1.setDescription("5214 - ACUCAR MESTRE VITO 1KG CRISTAL");
        ac1.setIcms("0,00");
        ac1.setCost("3,20");
        ac1.setMarginOverCost("21,23");
        ac1.setMarginOverRevenue("34,43");
        ac1.setPartCategory("34,45%");
        ac1.setPartRevenue("24,45%");
        ac1.setPisCofins("0,00");
        ac1.setProfit("23,23");
        ac1.setProfitMargin("1,34");
        ac1.setQuantity("23,54");
        ProductReport ac2 = new ProductReport();
        ac2.setIcms("0,00");
        ac2.setCost("3,20");
        ac2.setMarginOverCost("21,23");
        ac2.setMarginOverRevenue("34,43");
        ac2.setPartCategory("34,45%");
        ac2.setPartRevenue("24,45%");
        ac2.setPisCofins("0,00");
        ac2.setProfit("23,23");
        ac2.setProfitMargin("1,34");
        ac2.setQuantity("23,54");
        ac2.setDescription("9332 - ACUCAR ALTEZA 1KG CRISTAL");

        ps.add(ac1);
        ps.add(ac2);
        
        return ps;
    }
}
