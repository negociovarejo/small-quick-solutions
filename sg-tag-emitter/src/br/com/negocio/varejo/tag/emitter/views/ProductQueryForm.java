/* * Copyright (c) 2019 Negocio Varejo. All rights reserved. * */
package br.com.negocio.varejo.tag.emitter.views;

import br.com.negocio.varejo.tag.emitter.dao.ProductDao;
import br.com.negocio.varejo.tag.emitter.models.Product;
import br.com.negocio.varejo.tag.emitter.utilities.NumberUtil;
import br.com.negocio.varejo.tag.emitter.views.custom.StripedTableCellRenderer;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author derickfelix
 */
public class ProductQueryForm extends javax.swing.JInternalFrame {

    private final MainForm mainForm;
    private final TagEmitterForm tagEmitterForm;
    private final ProductDao productDao;
    private List<Product> products;
    private int lastSelected;

    /**
     * Creates new form ProductQueryForm
     * @param mainForm the main form
     */
    public ProductQueryForm(MainForm mainForm)
    {
        this(mainForm, null);
    }
    
    /**
     * Creates new form ProductQueryForm with a tagEmitter. It will bind the
     * product if the user perform a double click.
     *
     * @param mainForm the main form
     * @param tagEmitterForm the instance of a tagEmitterForm
     */
    public ProductQueryForm(MainForm mainForm, TagEmitterForm tagEmitterForm)
    {
        this.mainForm = mainForm;
        this.productDao = new ProductDao();
        this.tagEmitterForm = tagEmitterForm;
        initComponents();
        customSettings();
    }
    
    private void customSettings()
    {
        DefaultTableCellRenderer leftRenderer = new StripedTableCellRenderer(JLabel.LEFT);
        DefaultTableCellRenderer rightRenderer = new StripedTableCellRenderer(JLabel.RIGHT);
        
        productsTable.setDefaultRenderer(String.class, leftRenderer);
        productsTable.setShowGrid(false);
        productsTable.getTableHeader().setFont(new Font(Font.DIALOG, Font.PLAIN, 11));
        productsTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
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

        searchPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTerm = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        productsScrollPane = new javax.swing.JScrollPane();
        productsTable = new javax.swing.JTable();
        linePanel = new javax.swing.JPanel();
        lblLine = new javax.swing.JLabel();
        lblSelectedLine = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        btnQuery = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Consulta de Produtos");

        searchPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()-3f));
        jLabel1.setText("Descricao");

        txtTerm.setFont(txtTerm.getFont().deriveFont(txtTerm.getFont().getSize()-2f));
        txtTerm.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                txtTermKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtTerm))
                .addContainerGap())
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        productsTable.setFont(productsTable.getFont().deriveFont(productsTable.getFont().getSize()-3f));
        productsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "Codigo", "Descricao", "Preco"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        productsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        productsTable.getTableHeader().setReorderingAllowed(false);
        productsTable.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                productsTableMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                productsTableMouseClicked(evt);
            }
        });
        productsTable.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                productsTableKeyPressed(evt);
            }
        });
        productsScrollPane.setViewportView(productsTable);
        if (productsTable.getColumnModel().getColumnCount() > 0)
        {
            productsTable.getColumnModel().getColumn(0).setMinWidth(110);
            productsTable.getColumnModel().getColumn(0).setPreferredWidth(110);
            productsTable.getColumnModel().getColumn(0).setMaxWidth(150);
            productsTable.getColumnModel().getColumn(1).setMinWidth(400);
            productsTable.getColumnModel().getColumn(1).setPreferredWidth(400);
            productsTable.getColumnModel().getColumn(1).setMaxWidth(430);
        }

        linePanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        lblLine.setFont(lblLine.getFont().deriveFont(lblLine.getFont().getSize()-3f));
        lblLine.setText("Linha:");

        lblSelectedLine.setFont(lblSelectedLine.getFont().deriveFont(lblSelectedLine.getFont().getSize()-3f));
        lblSelectedLine.setText("0/0");

        javax.swing.GroupLayout linePanelLayout = new javax.swing.GroupLayout(linePanel);
        linePanel.setLayout(linePanelLayout);
        linePanelLayout.setHorizontalGroup(
            linePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(linePanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblLine)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSelectedLine, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        linePanelLayout.setVerticalGroup(
            linePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, linePanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(linePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLine)
                    .addComponent(lblSelectedLine))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(productsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
                    .addComponent(linePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(productsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(linePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

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

        btnQuery.setFont(btnQuery.getFont().deriveFont(btnQuery.getFont().getSize()-3f));
        btnQuery.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/negocio/varejo/tag/emitter/resources/search.png"))); // NOI18N
        btnQuery.setText("Consultar");
        btnQuery.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnQueryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(searchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnQuery)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExit)))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuery)
                    .addComponent(btnExit))
                .addGap(6, 6, 6))
        );

        setBounds(360, 100, 646, 357);
    }// </editor-fold>//GEN-END:initComponents

    private void btnQueryActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnQueryActionPerformed
    {//GEN-HEADEREND:event_btnQueryActionPerformed
        this.products = this.productDao.findAllByTerm(txtTerm.getText());
        lblSelectedLine.setText("0/" + products.size());
        
        DefaultTableModel model = (DefaultTableModel) productsTable.getModel();
        if (!model.getDataVector().isEmpty()) {
            model.getDataVector().removeAllElements();
        }
        
        for (Product product : products) {
            String[] row = new String[3];
            row[0] = product.getEancode();
            row[1] = product.getDescription();
            row[2] = String.format(Locale.getDefault(), "%.2f", product.getPrice1());
            model.addRow(row);
        }
    }//GEN-LAST:event_btnQueryActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnExitActionPerformed
    {//GEN-HEADEREND:event_btnExitActionPerformed
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void productsTableMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_productsTableMouseReleased
    {//GEN-HEADEREND:event_productsTableMouseReleased
        int selectedRow = productsTable.getSelectedRow();
        lblSelectedLine.setText(selectedRow + "/" + products.size());

        if (lastSelected == selectedRow) {
            selectProduct();
        }

        lastSelected = selectedRow;
    }//GEN-LAST:event_productsTableMouseReleased

    private void productsTableKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_productsTableKeyPressed
    {//GEN-HEADEREND:event_productsTableKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                selectProduct();
                break;
        }
    }//GEN-LAST:event_productsTableKeyPressed

    private void productsTableMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_productsTableMouseClicked
    {//GEN-HEADEREND:event_productsTableMouseClicked
    }//GEN-LAST:event_productsTableMouseClicked

    private void txtTermKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_txtTermKeyReleased
    {//GEN-HEADEREND:event_txtTermKeyReleased
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                btnQueryActionPerformed(null);
                break;
        }
    }//GEN-LAST:event_txtTermKeyReleased

    private void selectProduct()
    {
        if (tagEmitterForm != null) {
            tagEmitterForm.setProduct(products.get(productsTable.getSelectedRow()));
            dispose();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnQuery;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblLine;
    private javax.swing.JLabel lblSelectedLine;
    private javax.swing.JPanel linePanel;
    private javax.swing.JScrollPane productsScrollPane;
    private javax.swing.JTable productsTable;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTextField txtTerm;
    // End of variables declaration//GEN-END:variables
}
