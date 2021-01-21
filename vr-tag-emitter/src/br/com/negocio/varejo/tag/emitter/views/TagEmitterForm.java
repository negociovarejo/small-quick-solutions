/* * Copyright (c) 2019 Negocio Varejo. All rights reserved. * */
package br.com.negocio.varejo.tag.emitter.views;

import br.com.negocio.varejo.tag.emitter.dao.PrinterDao;
import br.com.negocio.varejo.tag.emitter.dao.ProductDao;
import br.com.negocio.varejo.tag.emitter.models.PrintingType;
import br.com.negocio.varejo.tag.emitter.models.Product;
import br.com.negocio.varejo.tag.emitter.utilities.MessageUtil;
import br.com.negocio.varejo.tag.emitter.utilities.ViewUtil;
import java.awt.event.KeyEvent;
import java.util.Locale;
import javax.print.DocFlavor;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JOptionPane;

/**
 *
 * @author derickfelix
 */
public class TagEmitterForm extends javax.swing.JInternalFrame {

    private final MainForm mainForm;
    private final PrinterDao printerDao;
    private final ProductDao productDao;
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
        this.productDao = new ProductDao();
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
        
        cmbPrintingType.addItem("Varejo / Clube");
        cmbPrintingType.addItem("Atacado / Varejo / Clube");
        this.btnPrintTag.setEnabled(false);
        cmbEancodes.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        productPanel = new javax.swing.JPanel();
        btnQueryProduct = new javax.swing.JButton();
        lblDescription = new javax.swing.JLabel();
        lblPriceRetail = new javax.swing.JLabel();
        lblTitlePriceRetail = new javax.swing.JLabel();
        lblPrinter = new javax.swing.JLabel();
        cmbPrinter = new javax.swing.JComboBox<>();
        lblProduct = new javax.swing.JLabel();
        cmbEancodes = new javax.swing.JComboBox<>();
        lblEancodes = new javax.swing.JLabel();
        txtTerm = new javax.swing.JTextField();
        lblPriceClub = new javax.swing.JLabel();
        lblTitlePriceClub = new javax.swing.JLabel();
        cmbPrintingType = new javax.swing.JComboBox<>();
        lblTitlePriceWholesale = new javax.swing.JLabel();
        lblPriceWholesale = new javax.swing.JLabel();
        lblPrintingType = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        btnPrintTag = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Emissao de Etiquetas");

        productPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnQueryProduct.setFont(btnQueryProduct.getFont().deriveFont(btnQueryProduct.getFont().getSize()-1f));
        btnQueryProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/negocio/varejo/tag/emitter/resources/search.png"))); // NOI18N
        btnQueryProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQueryProductActionPerformed(evt);
            }
        });

        lblDescription.setFont(lblDescription.getFont().deriveFont(lblDescription.getFont().getSize()-1f));
        lblDescription.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), javax.swing.BorderFactory.createEmptyBorder(2, 4, 4, 2)));
        lblDescription.setOpaque(true);

        lblPriceRetail.setFont(lblPriceRetail.getFont().deriveFont(lblPriceRetail.getFont().getSize()-1f));
        lblPriceRetail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPriceRetail.setText("0,00");
        lblPriceRetail.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), javax.swing.BorderFactory.createEmptyBorder(2, 4, 4, 2)));
        lblPriceRetail.setOpaque(true);

        lblTitlePriceRetail.setFont(lblTitlePriceRetail.getFont().deriveFont(lblTitlePriceRetail.getFont().getSize()-1f));
        lblTitlePriceRetail.setText("Preco Varejo");

        lblPrinter.setFont(lblPrinter.getFont().deriveFont(lblPrinter.getFont().getSize()-1f));
        lblPrinter.setText("Impressora");

        cmbPrinter.setFont(cmbPrinter.getFont().deriveFont(cmbPrinter.getFont().getSize()-1f));

        lblProduct.setFont(lblProduct.getFont().deriveFont(lblProduct.getFont().getSize()-1f));
        lblProduct.setText("Produto");

        cmbEancodes.setFont(cmbEancodes.getFont().deriveFont(cmbEancodes.getFont().getSize()-1f));
        cmbEancodes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbEancodesItemStateChanged(evt);
            }
        });
        cmbEancodes.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbEancodesPropertyChange(evt);
            }
        });

        lblEancodes.setFont(lblEancodes.getFont().deriveFont(lblEancodes.getFont().getSize()-1f));
        lblEancodes.setText("Codigo de Barras");

        txtTerm.setFont(txtTerm.getFont().deriveFont(txtTerm.getFont().getSize()-1f));
        txtTerm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTermKeyPressed(evt);
            }
        });

        lblPriceClub.setFont(lblPriceClub.getFont().deriveFont(lblPriceClub.getFont().getSize()-1f));
        lblPriceClub.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPriceClub.setText("0,00");
        lblPriceClub.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), javax.swing.BorderFactory.createEmptyBorder(2, 4, 4, 2)));
        lblPriceClub.setOpaque(true);

        lblTitlePriceClub.setFont(lblTitlePriceClub.getFont().deriveFont(lblTitlePriceClub.getFont().getSize()-1f));
        lblTitlePriceClub.setText("Preco Desconto");

        cmbPrintingType.setFont(cmbPrintingType.getFont().deriveFont(cmbPrintingType.getFont().getSize()-1f));

        lblTitlePriceWholesale.setFont(lblTitlePriceWholesale.getFont().deriveFont(lblTitlePriceWholesale.getFont().getSize()-1f));
        lblTitlePriceWholesale.setText("Preco Atacado");

        lblPriceWholesale.setFont(lblPriceWholesale.getFont().deriveFont(lblPriceWholesale.getFont().getSize()-1f));
        lblPriceWholesale.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPriceWholesale.setText("0,00");
        lblPriceWholesale.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), javax.swing.BorderFactory.createEmptyBorder(2, 4, 4, 2)));
        lblPriceWholesale.setOpaque(true);

        lblPrintingType.setFont(lblPrintingType.getFont().deriveFont(lblPrintingType.getFont().getSize()-1f));
        lblPrintingType.setText("Modelo Impressão");

        javax.swing.GroupLayout productPanelLayout = new javax.swing.GroupLayout(productPanel);
        productPanel.setLayout(productPanelLayout);
        productPanelLayout.setHorizontalGroup(
            productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productPanelLayout.createSequentialGroup()
                        .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(productPanelLayout.createSequentialGroup()
                                .addComponent(lblProduct)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(productPanelLayout.createSequentialGroup()
                                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(productPanelLayout.createSequentialGroup()
                                        .addComponent(txtTerm, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnQueryProduct))
                                    .addComponent(lblPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(productPanelLayout.createSequentialGroup()
                        .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblPrintingType, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(productPanelLayout.createSequentialGroup()
                                .addComponent(cmbPrinter, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbPrintingType, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbEancodes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblEancodes, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productPanelLayout.createSequentialGroup()
                        .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPriceRetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTitlePriceRetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPriceWholesale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTitlePriceWholesale, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPriceClub, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTitlePriceClub, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        productPanelLayout.setVerticalGroup(
            productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblProduct)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnQueryProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                        .addComponent(txtTerm))
                    .addComponent(lblDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblEancodes)
                        .addComponent(lblPrintingType))
                    .addComponent(lblPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbPrinter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbEancodes, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbPrintingType, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitlePriceRetail)
                    .addComponent(lblTitlePriceWholesale, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitlePriceClub, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPriceRetail, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPriceWholesale, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPriceClub, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        productPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnQueryProduct, lblDescription});

        btnExit.setFont(btnExit.getFont().deriveFont(btnExit.getFont().getSize()-1f));
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/negocio/varejo/tag/emitter/resources/error.png"))); // NOI18N
        btnExit.setText("Sair");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnPrintTag.setFont(btnPrintTag.getFont().deriveFont(btnPrintTag.getFont().getSize()-1f));
        btnPrintTag.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/negocio/varejo/tag/emitter/resources/print.png"))); // NOI18N
        btnPrintTag.setText("Imprimir");
        btnPrintTag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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
                        .addGap(0, 388, Short.MAX_VALUE)
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

        setBounds(424, 60, 603, 278);
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnExitActionPerformed
    {//GEN-HEADEREND:event_btnExitActionPerformed
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnPrintTagActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnPrintTagActionPerformed
    {//GEN-HEADEREND:event_btnPrintTagActionPerformed
        try {
            if (product != null) {
                product.setSelected(product.getEancodes().get(cmbEancodes.getSelectedIndex()));
                
                PrintingType type = PrintingType.values()[cmbPrintingType.getSelectedIndex()];
                
                setCursor(ViewUtil.WAIT_CURSOR);
                printerDao.print(product, printServices[cmbPrinter.getSelectedIndex()], type);
                setCursor(ViewUtil.DEFAULT_CURSOR);
            }
        } catch (PrintException e) {
            MessageUtil.showException(mainForm, e);
        }
    }//GEN-LAST:event_btnPrintTagActionPerformed

    private void btnQueryProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQueryProductActionPerformed
        setCursor(ViewUtil.WAIT_CURSOR);
        ProductQueryForm pqf = new ProductQueryForm(mainForm, this);
        mainForm.getDesktop().add(pqf);
        pqf.setVisible(true);
        setCursor(ViewUtil.DEFAULT_CURSOR);
    }//GEN-LAST:event_btnQueryProductActionPerformed

    private void txtTermKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTermKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String term = txtTerm.getText();
            Product search = null;
            
            if (term.matches("[0-9]+")) {
                search = this.productDao.findByIdOrEancode(term);

                if (search == null) {
                    JOptionPane.showMessageDialog(this, "Produto não encontrado!", this.title, JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Somente códigos, caso queira pesquisar por descrição acesse a lupa do lado!", this.title, JOptionPane.WARNING_MESSAGE);
            }
            
            setProduct(search);
        }
    }//GEN-LAST:event_txtTermKeyPressed

    private void cmbEancodesPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbEancodesPropertyChange
    }//GEN-LAST:event_cmbEancodesPropertyChange

    private void cmbEancodesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbEancodesItemStateChanged
        if (product != null) {
            product.setSelected(cmbEancodes.getSelectedIndex());
            lblPriceWholesale.setText(String.format(Locale.getDefault(), "%.2f", product.getPrice3()).replace(".", ","));
        }
    }//GEN-LAST:event_cmbEancodesItemStateChanged

    public void setProduct(Product product)
    {
        if (product != null) {
            txtTerm.setText(Long.toString(product.getId()));
            lblDescription.setText(product.getDescription());
            lblPriceRetail.setText(String.format(Locale.getDefault(), "%.2f", product.getPrice1()).replace(".", ","));
            lblPriceClub.setText(String.format(Locale.getDefault(), "%.2f", product.getPrice2()).replace(".", ","));
            
            cmbEancodes.removeAllItems();
            product.getEancodes().forEach(eancode -> {
                cmbEancodes.addItem(eancode.getCode());
            });

            lblPriceWholesale.setText(String.format(Locale.getDefault(), "%.2f", product.getPrice3()).replace(".", ","));
            this.btnPrintTag.setEnabled(true);
            cmbEancodes.setEnabled(true);
        } else {    
            this.btnPrintTag.setEnabled(false);
            cmbEancodes.setEnabled(false);
        }
        
        this.product = product;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnPrintTag;
    private javax.swing.JButton btnQueryProduct;
    private javax.swing.JComboBox<String> cmbEancodes;
    private javax.swing.JComboBox<String> cmbPrinter;
    private javax.swing.JComboBox<String> cmbPrintingType;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblEancodes;
    private javax.swing.JLabel lblPriceClub;
    private javax.swing.JLabel lblPriceRetail;
    private javax.swing.JLabel lblPriceWholesale;
    private javax.swing.JLabel lblPrinter;
    private javax.swing.JLabel lblPrintingType;
    private javax.swing.JLabel lblProduct;
    private javax.swing.JLabel lblTitlePriceClub;
    private javax.swing.JLabel lblTitlePriceRetail;
    private javax.swing.JLabel lblTitlePriceWholesale;
    private javax.swing.JPanel productPanel;
    private javax.swing.JTextField txtTerm;
    // End of variables declaration//GEN-END:variables
}
