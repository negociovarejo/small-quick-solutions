/* * Copyright (c) 2019 Negocio Varejo. All rights reserved. * */
package br.com.negocio.varejo.tag.emitter.views;

import br.com.negocio.varejo.tag.emitter.dao.PrinterDao;
import br.com.negocio.varejo.tag.emitter.models.Product;
import br.com.negocio.varejo.tag.emitter.utilities.MessageUtil;
import br.com.negocio.varejo.tag.emitter.utilities.ViewUtil;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;
import javax.print.DocFlavor;
import javax.print.DocFlavor.STRING;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

/**
 *
 * @author derickfelix
 */
public class TagEmitterForm extends javax.swing.JInternalFrame {

    private final MainForm mainForm;
    private final PrinterDao printerDao;
    private final PrintService[] printServices;
    private Product product;

    /**
     * Creates new form EmissionForm
     *
     * @param mainForm the main form
     */
    public TagEmitterForm(MainForm mainForm)
    {
        this.mainForm = mainForm;
        this.printerDao = new PrinterDao();
        this.printServices = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.AUTOSENSE, null);

        initComponents();
        customSettings();
    }
    
    private void customSettings()
    {
        PrintService def = PrintServiceLookup.lookupDefaultPrintService();
        int def_index = 0;

        for (int i = 0; i < printServices.length; ++i) {
            PrintService service = printServices[i];
            
            if (service.getName().equals(def.getName())) {
                def_index = i;
            }
            
            cmbPrinter.addItem(service.getName());
        }

        if (printServices.length > 0) {
            cmbPrinter.setSelectedIndex(def_index);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        productPanel = new javax.swing.JPanel();
        btnQueryProduct = new javax.swing.JButton();
        lblDescription = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        retailTitle = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        wholesaleTitle = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cmbPrinter = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        spnQuantity = new javax.swing.JSpinner();
        wholesaleTitle1 = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        btnPrintTag = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Emissao de Etiquetas");

        productPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnQueryProduct.setFont(btnQueryProduct.getFont().deriveFont(btnQueryProduct.getFont().getSize()-3f));
        btnQueryProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/negocio/varejo/tag/emitter/resources/search.png"))); // NOI18N
        btnQueryProduct.setText("Selecionar");
        btnQueryProduct.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnQueryProductActionPerformed(evt);
            }
        });

        lblDescription.setFont(lblDescription.getFont().deriveFont(lblDescription.getFont().getSize()-3f));
        lblDescription.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), javax.swing.BorderFactory.createEmptyBorder(2, 4, 4, 2)));
        lblDescription.setOpaque(true);

        lblPrice.setFont(lblPrice.getFont().deriveFont(lblPrice.getFont().getSize()-3f));
        lblPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPrice.setText("0,00");
        lblPrice.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), javax.swing.BorderFactory.createEmptyBorder(2, 4, 4, 2)));
        lblPrice.setOpaque(true);

        retailTitle.setFont(retailTitle.getFont().deriveFont(retailTitle.getFont().getSize()-3f));
        retailTitle.setText("Preco Varejo");

        txtPrice.setFont(txtPrice.getFont().deriveFont(txtPrice.getFont().getSize()-3f));
        txtPrice.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                txtPriceActionPerformed(evt);
            }
        });

        wholesaleTitle.setFont(wholesaleTitle.getFont().deriveFont(wholesaleTitle.getFont().getSize()-3f));
        wholesaleTitle.setText("Preco Atacado");

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()-3f));
        jLabel1.setText("Impressora");

        cmbPrinter.setFont(cmbPrinter.getFont().deriveFont(cmbPrinter.getFont().getSize()-3f));

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()-3f));
        jLabel2.setText("Produto");

        spnQuantity.setModel(new javax.swing.SpinnerNumberModel(1, 1, 1000, 1));

        wholesaleTitle1.setFont(wholesaleTitle1.getFont().deriveFont(wholesaleTitle1.getFont().getSize()-3f));
        wholesaleTitle1.setText("A partir de (UN)");

        javax.swing.GroupLayout productPanelLayout = new javax.swing.GroupLayout(productPanel);
        productPanel.setLayout(productPanelLayout);
        productPanelLayout.setHorizontalGroup(
            productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productPanelLayout.createSequentialGroup()
                        .addComponent(cmbPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(productPanelLayout.createSequentialGroup()
                        .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(retailTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(wholesaleTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(productPanelLayout.createSequentialGroup()
                                .addComponent(wholesaleTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(productPanelLayout.createSequentialGroup()
                                .addComponent(spnQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnQueryProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))))
                    .addGroup(productPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(90, 90, 90)
                        .addComponent(jLabel2)))
                .addContainerGap())
        );
        productPanelLayout.setVerticalGroup(
            productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(productPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wholesaleTitle)
                    .addComponent(wholesaleTitle1)
                    .addComponent(retailTitle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQueryProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        productPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnQueryProduct, lblDescription});

        productPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblPrice, txtPrice});

        btnExit.setFont(btnExit.getFont().deriveFont(btnExit.getFont().getSize()-3f));
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/negocio/varejo/tag/emitter/resources/error.png"))); // NOI18N
        btnExit.setText("Sair");
        btnExit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnExitActionPerformed(evt);
            }
        });

        btnPrintTag.setFont(btnPrintTag.getFont().deriveFont(btnPrintTag.getFont().getSize()-3f));
        btnPrintTag.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/negocio/varejo/tag/emitter/resources/print.png"))); // NOI18N
        btnPrintTag.setText("Imprimir");
        btnPrintTag.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnPrintTagActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnPrintTag)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExit))
                    .addComponent(productPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(productPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExit)
                    .addComponent(btnPrintTag))
                .addContainerGap())
        );

        setBounds(424, 60, 548, 226);
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnExitActionPerformed
    {//GEN-HEADEREND:event_btnExitActionPerformed
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnQueryProductActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnQueryProductActionPerformed
    {//GEN-HEADEREND:event_btnQueryProductActionPerformed
        setCursor(ViewUtil.WAIT_CURSOR);
        ProductQueryForm pqf = new ProductQueryForm(mainForm, this);
        mainForm.getDesktop().add(pqf);
        pqf.setVisible(true);
        setCursor(ViewUtil.DEFAULT_CURSOR);
    }//GEN-LAST:event_btnQueryProductActionPerformed

    private void btnPrintTagActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnPrintTagActionPerformed
    {//GEN-HEADEREND:event_btnPrintTagActionPerformed
        try {
            if (product != null) {
                double price = product.getPrice1();
                int quantity = 1;

                try {
                    price = Double.parseDouble(txtPrice.getText().replace(",", "."));
                    quantity = Integer.parseInt(spnQuantity.getValue().toString());
                } catch (NumberFormatException ignore) {
                }

                product.setPrice2(price);
                product.setQuantity(quantity);
                setCursor(ViewUtil.WAIT_CURSOR);
                printerDao.print(product, printServices[cmbPrinter.getSelectedIndex()]);
                setCursor(ViewUtil.DEFAULT_CURSOR);
            }
        } catch (PrintException e) {
            MessageUtil.showException(mainForm, e);
        }
    }//GEN-LAST:event_btnPrintTagActionPerformed

    private void txtPriceActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_txtPriceActionPerformed
    {//GEN-HEADEREND:event_txtPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriceActionPerformed

    public void setProduct(Product product)
    {
        product.setQuantity(1);
        lblDescription.setText(product.getDescription());
        lblPrice.setText(String.format(Locale.getDefault(), "%.2f", product.getPrice1()));
        txtPrice.setText(String.format(Locale.getDefault(), "%.2f", product.getPrice1()));
        this.product = product;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnPrintTag;
    private javax.swing.JButton btnQueryProduct;
    private javax.swing.JComboBox<String> cmbPrinter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JPanel productPanel;
    private javax.swing.JLabel retailTitle;
    private javax.swing.JSpinner spnQuantity;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JLabel wholesaleTitle;
    private javax.swing.JLabel wholesaleTitle1;
    // End of variables declaration//GEN-END:variables
}