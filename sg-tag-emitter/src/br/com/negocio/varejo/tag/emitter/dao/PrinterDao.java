/* * Copyright (c) 2019 Negocio Varejo. All rights reserved. * */
package br.com.negocio.varejo.tag.emitter.dao;

import br.com.negocio.varejo.tag.emitter.models.Product;
import br.com.negocio.varejo.tag.emitter.utilities.DateUtil;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;

/**
 *
 * @author derickfelix
 */
public class PrinterDao {

    public static void main(String[] args) throws PrintException
    {
        Product product = new Product();
        product.setDescription("ARROZ PAI JOAO 1KG");
        product.setPrice1(2.79);
        product.setPrice2(2.45);
        product.setQuantity(6);
        product.setEancode("7890000002664");

        PrinterDao dao = new PrinterDao();
        dao.print(product);
    }

    public void print(Product product) throws PrintException
    {
        print(product, PrintServiceLookup.lookupDefaultPrintService());
    }

    public void print(Product product, PrintService service) throws PrintException
    {
        List<String> file = readFile();
        StringBuilder builder = new StringBuilder();

        for (String line : file) {
            line = line.replace("@eancode", product.getEancode());
            line = line.replace("@description", product.getDescription());
            line = line.replace("@quantity", product.getQuantity().toString());
            line = line.replace("@retailSale", String.format(Locale.getDefault(), "%.2f", product.getPrice1()));
            line = line.replace("@wholeSale", String.format(Locale.getDefault(), "R$ %.2f", product.getPrice2()));
            line = line.replace("@price", product.getDescription());
            line = line.replace("@date", DateUtil.format(new Date()));

            builder.append(line).append("\n\r");
        }

        print(builder.toString(), service);
    }

    private void print(String content, PrintService service) throws PrintException
    {
        DocFlavor f = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc d = new SimpleDoc(new ByteArrayInputStream(content.getBytes()), f, null);
        DocPrintJob job = service.createPrintJob();
        job.print(d, new HashPrintRequestAttributeSet());
    }

    private List<String> readFile()
    {
        File f = new File("/nv/template.prn");
        List<String> file = new LinkedList<>();

        try (FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr)) {
            String line = br.readLine();
            while (line != null) {
                file.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to open file for reading: " + e.getMessage());
        }

        return file;
    }
}
