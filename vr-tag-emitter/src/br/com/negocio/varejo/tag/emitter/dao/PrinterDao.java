/* * Copyright (c) 2019 Negocio Varejo. All rights reserved. * */
package br.com.negocio.varejo.tag.emitter.dao;

import br.com.negocio.varejo.tag.emitter.models.PrintingType;
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
    
    public void print(Product product) throws PrintException
    {
        print(product, PrintServiceLookup.lookupDefaultPrintService(), PrintingType.CLUB_RETAIL);
    }

    public void print(Product product, PrintService service, PrintingType type) throws PrintException
    {
        List<String> file = readFile(type);
        StringBuilder builder = new StringBuilder();

        file.forEach(line -> {
            line = line.replace("@BARRA", product.getSelected().getCode());
            line = line.replace("@DESCR", product.getDescription());
            line = line.replace("@PRECO", String.format(Locale.getDefault(), "%.2f", product.getPrice1()).replace(".", ","));
            line = line.replace("@VALORCLUBE", String.format(Locale.getDefault(), "%.2f", product.getPrice2()).replace(".", ","));
            line = line.replace("@VALORATAC", String.format(Locale.getDefault(), "%.2f", product.getPrice3()).replace(".", ","));
            line = line.replace("@DATAS", DateUtil.format(new Date()));

            builder.append(line).append("\n\r");
        });
        
        print(builder.toString(), service);
    }

    private void print(String content, PrintService service) throws PrintException
    {
        DocFlavor f = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc d = new SimpleDoc(new ByteArrayInputStream(content.getBytes()), f, null);
        DocPrintJob job = service.createPrintJob();
        job.print(d, new HashPrintRequestAttributeSet());
    }

    private List<String> readFile(PrintingType type)
    {
        String resource = "resources/";
        
        switch (type) {
            case WHOLESALE_CLUB_RETAIL:
                resource += "wholesale_club_retail.prn";
                break;
            case CLUB_RETAIL:
                resource += "club_retail.prn";
                break;
        }
        
        File f = new File(resource);
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
