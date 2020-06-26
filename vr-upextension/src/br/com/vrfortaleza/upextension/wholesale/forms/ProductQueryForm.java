/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.wholesale.forms;

import br.com.vrfortaleza.upextension.utilities.NumberUtil;
import br.com.vrfortaleza.upextension.utilities.ViewUtil;
import br.com.vrfortaleza.upextension.wholesale.models.Product;
import br.com.vrfortaleza.upextension.wholesale.services.ProductService;
import br.com.vrfortaleza.upextension.views.custom.StripedTableCellRenderer;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author derickfelix
 */
public class ProductQueryForm extends javax.swing.JInternalFrame {

    private final ProductService service;
    private List<Product> products;
    private int highlighted;
    private TableRowSorter<TableModel> rowSorter;
    private StringBuilder buffer;

    /**
     * Creates new form WholesaleQueryForm
     *
     */
    public ProductQueryForm()
    {
        this.service = new ProductService();
        this.buffer = new StringBuilder();

        initComponents();
        customSettings();
    }

    private void customSettings()
    {
        this.products = service.all();
        scrollpane.setVisible(false);
        lblSearch.setVisible(false);
        // set table stripped
        DefaultTableCellRenderer leftRenderer = new StripedTableCellRenderer(JLabel.LEFT);
        searchTable.setDefaultRenderer(String.class, leftRenderer);
        searchTable.setShowGrid(false);
        searchTable.getTableHeader().setFont(new Font(Font.DIALOG, Font.PLAIN, 11));

        rowSorter = new TableRowSorter<>(searchTable.getModel());
        searchTable.setRowSorter(rowSorter);

        txtBarcode.setFocusable(false);
        btnExit.setFocusable(false);
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

        lblSearch = new javax.swing.JLabel();
        scrollpane = new javax.swing.JScrollPane();
        searchTable = new javax.swing.JTable();
        panelFields = new javax.swing.JPanel();
        lblBarcode = new javax.swing.JLabel();
        txtBarcode = new javax.swing.JTextField();
        lblDescription = new javax.swing.JLabel();
        lblWrapper = new javax.swing.JLabel();
        lblQtyWrapper = new javax.swing.JLabel();
        lblStock = new javax.swing.JLabel();
        lblRetailPrice = new javax.swing.JLabel();
        lblWholesalePrice = new javax.swing.JLabel();
        lblDiscount = new javax.swing.JLabel();
        txtDescription = new javax.swing.JLabel();
        txtWrapper = new javax.swing.JLabel();
        txtStock = new javax.swing.JLabel();
        txtRetailPrice = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JLabel();
        txtWholesalePrice = new javax.swing.JLabel();
        txtQtyWrapper = new javax.swing.JLabel();
        paneButtons = new javax.swing.JPanel();
        btnExit = new javax.swing.JButton();

        setClosable(true);
        setTitle("Consulta de preco Venda");
        addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                formMouseClicked(evt);
            }
        });
        getContentPane().setLayout(null);

        lblSearch.setBackground(new java.awt.Color(184, 207, 229));
        lblSearch.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        lblSearch.setText("Pesquisa:");
        lblSearch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(1, 180, 234)));
        lblSearch.setOpaque(true);
        getContentPane().add(lblSearch);
        lblSearch.setBounds(39, 24, 210, 20);

        scrollpane.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N

        searchTable.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        searchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "Código", "Descrição"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false
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
        searchTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        searchTable.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                searchTableMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                searchTableMouseClicked(evt);
            }
        });
        searchTable.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                searchTableKeyPressed(evt);
            }
        });
        scrollpane.setViewportView(searchTable);
        if (searchTable.getColumnModel().getColumnCount() > 0)
        {
            searchTable.getColumnModel().getColumn(0).setResizable(false);
            searchTable.getColumnModel().getColumn(0).setPreferredWidth(2);
            searchTable.getColumnModel().getColumn(1).setResizable(false);
            searchTable.getColumnModel().getColumn(1).setPreferredWidth(240);
        }

        getContentPane().add(scrollpane);
        scrollpane.setBounds(32, 22, 440, 150);

        panelFields.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(184, 207, 229)));
        panelFields.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                panelFieldsMouseClicked(evt);
            }
        });

        lblBarcode.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        lblBarcode.setText("Código de Barras");

        txtBarcode.setEditable(false);
        txtBarcode.setBackground(new java.awt.Color(231, 248, 240));
        txtBarcode.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtBarcode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBarcode.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtBarcode.addCaretListener(new javax.swing.event.CaretListener()
        {
            public void caretUpdate(javax.swing.event.CaretEvent evt)
            {
                txtBarcodeCaretUpdate(evt);
            }
        });
        txtBarcode.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                txtBarcodeMouseClicked(evt);
            }
        });
        txtBarcode.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                txtBarcodeActionPerformed(evt);
            }
        });

        lblDescription.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        lblDescription.setText("Descrição");

        lblWrapper.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        lblWrapper.setText("Tipo Emb.");

        lblQtyWrapper.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        lblQtyWrapper.setText("Qtd. Emb.");

        lblStock.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        lblStock.setText("Estoque");

        lblRetailPrice.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        lblRetailPrice.setText("Preço");

        lblWholesalePrice.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        lblWholesalePrice.setText("Preço Atacado");

        lblDiscount.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        lblDiscount.setText("% Desconto");

        txtDescription.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtDescription.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtDescription.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(184, 207, 229)), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));

        txtWrapper.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtWrapper.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtWrapper.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(184, 207, 229)), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));

        txtStock.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtStock.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtStock.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(184, 207, 229)), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));

        txtRetailPrice.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtRetailPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtRetailPrice.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(184, 207, 229)), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));

        txtDiscount.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtDiscount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtDiscount.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(184, 207, 229)), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));

        txtWholesalePrice.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtWholesalePrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtWholesalePrice.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(184, 207, 229)), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));

        txtQtyWrapper.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtQtyWrapper.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtQtyWrapper.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(184, 207, 229)), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));

        javax.swing.GroupLayout panelFieldsLayout = new javax.swing.GroupLayout(panelFields);
        panelFields.setLayout(panelFieldsLayout);
        panelFieldsLayout.setHorizontalGroup(
            panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFieldsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFieldsLayout.createSequentialGroup()
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBarcode)
                            .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFieldsLayout.createSequentialGroup()
                                .addComponent(lblDescription)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelFieldsLayout.createSequentialGroup()
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblWrapper, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                            .addComponent(txtWrapper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFieldsLayout.createSequentialGroup()
                                .addComponent(lblQtyWrapper, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6))
                            .addGroup(panelFieldsLayout.createSequentialGroup()
                                .addComponent(txtQtyWrapper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblStock, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblRetailPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(txtRetailPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtWholesalePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblWholesalePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        panelFieldsLayout.setVerticalGroup(
            panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFieldsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBarcode)
                    .addComponent(lblDescription))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblWrapper)
                    .addComponent(lblQtyWrapper)
                    .addComponent(lblStock)
                    .addComponent(lblRetailPrice)
                    .addComponent(lblDiscount)
                    .addComponent(lblWholesalePrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtWrapper, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtWholesalePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRetailPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQtyWrapper, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(panelFields);
        panelFields.setBounds(12, 12, 566, 123);

        paneButtons.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(184, 207, 229)));

        btnExit.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/vrfortaleza/upextension/resources/close-button-icon.png"))); // NOI18N
        btnExit.setText("Sair");
        btnExit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paneButtonsLayout = new javax.swing.GroupLayout(paneButtons);
        paneButtons.setLayout(paneButtonsLayout);
        paneButtonsLayout.setHorizontalGroup(
            paneButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneButtonsLayout.createSequentialGroup()
                .addContainerGap(464, Short.MAX_VALUE)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        paneButtonsLayout.setVerticalGroup(
            paneButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnExit)
        );

        getContentPane().add(paneButtons);
        paneButtons.setBounds(12, 141, 566, 30);

        setBounds(320, 130, 601, 214);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBarcodeCaretUpdate(javax.swing.event.CaretEvent evt)//GEN-FIRST:event_txtBarcodeCaretUpdate
    {//GEN-HEADEREND:event_txtBarcodeCaretUpdate

    }//GEN-LAST:event_txtBarcodeCaretUpdate

    private void txtBarcodeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_txtBarcodeActionPerformed
    {//GEN-HEADEREND:event_txtBarcodeActionPerformed

    }//GEN-LAST:event_txtBarcodeActionPerformed

    private void txtBarcodeMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_txtBarcodeMouseClicked
    {//GEN-HEADEREND:event_txtBarcodeMouseClicked
        scrollpane.setVisible(true);
        buffer = new StringBuilder();
        searchTable.requestFocus();
        fillTable();
    }//GEN-LAST:event_txtBarcodeMouseClicked

    private void searchTableMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchTableMouseClicked
    {//GEN-HEADEREND:event_searchTableMouseClicked

    }//GEN-LAST:event_searchTableMouseClicked

    private void searchTableMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchTableMouseReleased
    {//GEN-HEADEREND:event_searchTableMouseReleased
        int selectedRow = searchTable.getSelectedRow();
        if (highlighted == selectedRow) {
            showSelectedProduct(highlighted);
        }
        highlighted = selectedRow;
    }//GEN-LAST:event_searchTableMouseReleased

    private void searchTableKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_searchTableKeyPressed
    {//GEN-HEADEREND:event_searchTableKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            showSelectedProduct(searchTable.getSelectedRow());
        } else {
            buffer = ViewUtil.performTableQuery(lblSearch, buffer, searchTable, rowSorter, evt);
        }

    }//GEN-LAST:event_searchTableKeyPressed

    private void panelFieldsMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_panelFieldsMouseClicked
    {//GEN-HEADEREND:event_panelFieldsMouseClicked
        lblSearch.setVisible(false);
        scrollpane.setVisible(false);
    }//GEN-LAST:event_panelFieldsMouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMouseClicked
    {//GEN-HEADEREND:event_formMouseClicked
        lblSearch.setVisible(false);
        scrollpane.setVisible(false);
    }//GEN-LAST:event_formMouseClicked

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnExitActionPerformed
    {//GEN-HEADEREND:event_btnExitActionPerformed
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void showSelectedProduct(int selectedRow)
    {
        lblSearch.setVisible(false);
        scrollpane.setVisible(false);

        Product product = ViewUtil.getEntityFromTable(selectedRow, searchTable, products);

        if (product != null) {
            txtBarcode.setText(product.getBarcode());
            txtDescription.setText(product.getDescription());
            txtDiscount.setText(product.getDiscount());
            txtQtyWrapper.setText(Integer.toString(product.getQtyWrapper()));
            txtStock.setText(product.getStock());
            txtRetailPrice.setText(product.getRetailPrice());
            txtWholesalePrice.setText(product.getWholeSalePrice());
            txtWrapper.setText(product.getWrapper());
        } else {
            JOptionPane.showMessageDialog(this, "O produto informado não foi encontrado!", "Falha ao pesquisar", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void fillTable()
    {
        DefaultTableModel model = (DefaultTableModel) searchTable.getModel();

        if (!model.getDataVector().isEmpty()) {
            model.getDataVector().removeAllElements();
        }

        for (Product wp : products) {
            String[] row = new String[2];
            row[0] = NumberUtil.fixedDigits(14, String.valueOf(wp.getId()));
            row[1] = wp.getDescription();
            model.addRow(row);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JLabel lblBarcode;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblDiscount;
    private javax.swing.JLabel lblQtyWrapper;
    private javax.swing.JLabel lblRetailPrice;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblStock;
    private javax.swing.JLabel lblWholesalePrice;
    private javax.swing.JLabel lblWrapper;
    private javax.swing.JPanel paneButtons;
    private javax.swing.JPanel panelFields;
    private javax.swing.JScrollPane scrollpane;
    private javax.swing.JTable searchTable;
    private javax.swing.JTextField txtBarcode;
    private javax.swing.JLabel txtDescription;
    private javax.swing.JLabel txtDiscount;
    private javax.swing.JLabel txtQtyWrapper;
    private javax.swing.JLabel txtRetailPrice;
    private javax.swing.JLabel txtStock;
    private javax.swing.JLabel txtWholesalePrice;
    private javax.swing.JLabel txtWrapper;
    // End of variables declaration//GEN-END:variables
}
