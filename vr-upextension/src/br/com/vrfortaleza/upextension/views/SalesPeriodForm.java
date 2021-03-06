/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.views;

import br.com.vrfortaleza.upextension.views.custom.StripedTableCellRenderer;
import br.com.vrfortaleza.upextension.models.report.SalePeriodReport;
import br.com.vrfortaleza.upextension.services.SalePeriodService;
import br.com.vrfortaleza.upextension.utilities.FileUtil;
import br.com.vrfortaleza.upextension.utilities.MessageUtil;
import br.com.vrfortaleza.upextension.utilities.NumberUtil;
import br.com.vrfortaleza.upextension.utilities.ViewUtil;
import java.awt.Font;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author derickfelix
 */
public class SalesPeriodForm extends javax.swing.JInternalFrame {

    private final MainForm mainForm;
    private final SalePeriodService vendaService;
    private final JFileChooser fileChooser;
    private List<SalePeriodReport> periodSales;
    private Map<String, Object> params;

    /**
     * Creates new form PeriodForm
     *
     * @param mainForm the main form of the application
     */
    public SalesPeriodForm(MainForm mainForm)
    {
        this.fileChooser = new JFileChooser();
        this.vendaService = new SalePeriodService();
        this.mainForm = mainForm;
        initComponents();
        customSettings();
    }

    private void customSettings()
    {
        //ViewUtility.addIcon(this);
        fileChooser.setCurrentDirectory(FileUtil.getJarFileLocation());
        DefaultTableCellRenderer rightRenderer = new StripedTableCellRenderer(JLabel.RIGHT);
        DefaultTableCellRenderer leftRenderer = new StripedTableCellRenderer(JLabel.LEFT);
        // All columns to right
        mainTable.setDefaultRenderer(String.class, rightRenderer);
        totalTable.setDefaultRenderer(String.class, rightRenderer);
        // First column to left
        mainTable.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
        mainTable.setAutoCreateRowSorter(true);
        mainTable.getTableHeader().setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        totalTable.getTableHeader().setFont(new Font(Font.DIALOG, Font.PLAIN, 12));

        totalScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        btnReport.setEnabled(false);
        chbSomenteOferta.setEnabled(false);
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

        mainScroll = new javax.swing.JScrollPane();
        mainTable = new javax.swing.JTable();
        totalScroll = new javax.swing.JScrollPane();
        totalTable = new javax.swing.JTable();
        statusPanel = new javax.swing.JPanel();
        lblLine = new javax.swing.JLabel();
        lblSelectedLine = new javax.swing.JLabel();
        mainToolBar = new javax.swing.JToolBar();
        btnOpen = new javax.swing.JButton();
        btnReport = new javax.swing.JButton();
        chbSomenteOferta = new javax.swing.JCheckBox();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Consulta de Venda");

        mainTable.setFont(new java.awt.Font("Noto Sans", 0, 11)); // NOI18N
        mainTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "Data", "Quantidade", "Custo c/ Imposto", "Venda", "Margem Bruta", "Margem Líquida", "Margem Sb Custo", "Margem Sb Venda", "Part. Venda", "Operacional", "ICMS", "PIS/COFINS", "Lucro", "Custo s/ Imposto", "ICMS Débito", "PIS/COFINS Débito", "Valor Sell-Out", "Custo c/ Sell-Out", "Mg. Bruta c/ Sell-Out", "Mg. Líq c/ Sell-Out", "Mg. Sb Custo c/ Sell-Out", "Mg. Sb Venda c/ Sell-Out", "PIS/COFINS Recolher", "Custo c/ Imposto Economico", "Lucro Economico"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
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
        mainTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        mainTable.setGridColor(new java.awt.Color(255, 255, 255));
        mainTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        mainTable.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                mainTableMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                mainTableMouseClicked(evt);
            }
        });
        mainScroll.setViewportView(mainTable);
        if (mainTable.getColumnModel().getColumnCount() > 0)
        {
            mainTable.getColumnModel().getColumn(0).setMinWidth(130);
            mainTable.getColumnModel().getColumn(0).setPreferredWidth(130);
            mainTable.getColumnModel().getColumn(1).setMinWidth(90);
            mainTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            mainTable.getColumnModel().getColumn(2).setMinWidth(130);
            mainTable.getColumnModel().getColumn(2).setPreferredWidth(140);
            mainTable.getColumnModel().getColumn(3).setMinWidth(80);
            mainTable.getColumnModel().getColumn(3).setPreferredWidth(80);
            mainTable.getColumnModel().getColumn(4).setMinWidth(110);
            mainTable.getColumnModel().getColumn(4).setPreferredWidth(120);
            mainTable.getColumnModel().getColumn(5).setMinWidth(110);
            mainTable.getColumnModel().getColumn(5).setPreferredWidth(120);
            mainTable.getColumnModel().getColumn(6).setMinWidth(120);
            mainTable.getColumnModel().getColumn(6).setPreferredWidth(130);
            mainTable.getColumnModel().getColumn(7).setMinWidth(130);
            mainTable.getColumnModel().getColumn(7).setPreferredWidth(140);
            mainTable.getColumnModel().getColumn(8).setMinWidth(100);
            mainTable.getColumnModel().getColumn(8).setPreferredWidth(100);
            mainTable.getColumnModel().getColumn(9).setMinWidth(90);
            mainTable.getColumnModel().getColumn(9).setPreferredWidth(100);
            mainTable.getColumnModel().getColumn(10).setMinWidth(70);
            mainTable.getColumnModel().getColumn(10).setPreferredWidth(70);
            mainTable.getColumnModel().getColumn(11).setMinWidth(90);
            mainTable.getColumnModel().getColumn(11).setPreferredWidth(90);
            mainTable.getColumnModel().getColumn(12).setMinWidth(80);
            mainTable.getColumnModel().getColumn(12).setPreferredWidth(80);
            mainTable.getColumnModel().getColumn(13).setMinWidth(130);
            mainTable.getColumnModel().getColumn(13).setPreferredWidth(130);
            mainTable.getColumnModel().getColumn(14).setMinWidth(100);
            mainTable.getColumnModel().getColumn(14).setPreferredWidth(100);
            mainTable.getColumnModel().getColumn(15).setMinWidth(140);
            mainTable.getColumnModel().getColumn(15).setPreferredWidth(140);
            mainTable.getColumnModel().getColumn(16).setMinWidth(100);
            mainTable.getColumnModel().getColumn(16).setPreferredWidth(110);
            mainTable.getColumnModel().getColumn(17).setMinWidth(120);
            mainTable.getColumnModel().getColumn(17).setPreferredWidth(130);
            mainTable.getColumnModel().getColumn(18).setMinWidth(140);
            mainTable.getColumnModel().getColumn(18).setPreferredWidth(150);
            mainTable.getColumnModel().getColumn(19).setMinWidth(150);
            mainTable.getColumnModel().getColumn(19).setPreferredWidth(150);
            mainTable.getColumnModel().getColumn(20).setMinWidth(170);
            mainTable.getColumnModel().getColumn(20).setPreferredWidth(180);
            mainTable.getColumnModel().getColumn(21).setMinWidth(160);
            mainTable.getColumnModel().getColumn(21).setPreferredWidth(180);
            mainTable.getColumnModel().getColumn(22).setMinWidth(160);
            mainTable.getColumnModel().getColumn(22).setPreferredWidth(160);
            mainTable.getColumnModel().getColumn(23).setMinWidth(200);
            mainTable.getColumnModel().getColumn(23).setPreferredWidth(210);
            mainTable.getColumnModel().getColumn(24).setMinWidth(130);
            mainTable.getColumnModel().getColumn(24).setPreferredWidth(130);
        }

        totalTable.setFont(new java.awt.Font("Noto Sans", 0, 11)); // NOI18N
        totalTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "Quantidade", "Custo c/ Imposto", "Venda", "Operacional", "ICMS", "PIS/COFINS", "Lucro", "Margem Bruta", "Margem Líquida", "Margem Sb Custo", "Margem Sb Venda", "Valor Sell-Out", "Custo c/ Sell-Out", "Mg. Bruta c/ Sell-Out", "Mg. Líq c/ Sell-Out", "Mg. Sb Custo c/ Sell-Out", "Mg. Sb Venda c/ Sell-Out", "PIS/COFINS Recolher", "Custo c/ Imposto Economico", "Lucro Economico"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
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
        totalTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        totalTable.setEnabled(false);
        totalTable.setGridColor(new java.awt.Color(255, 255, 255));
        totalScroll.setViewportView(totalTable);
        if (totalTable.getColumnModel().getColumnCount() > 0)
        {
            totalTable.getColumnModel().getColumn(0).setMinWidth(110);
            totalTable.getColumnModel().getColumn(0).setPreferredWidth(110);
            totalTable.getColumnModel().getColumn(1).setMinWidth(130);
            totalTable.getColumnModel().getColumn(1).setPreferredWidth(130);
            totalTable.getColumnModel().getColumn(2).setMinWidth(100);
            totalTable.getColumnModel().getColumn(2).setPreferredWidth(100);
            totalTable.getColumnModel().getColumn(3).setMinWidth(90);
            totalTable.getColumnModel().getColumn(3).setPreferredWidth(100);
            totalTable.getColumnModel().getColumn(4).setMinWidth(80);
            totalTable.getColumnModel().getColumn(4).setPreferredWidth(80);
            totalTable.getColumnModel().getColumn(5).setMinWidth(90);
            totalTable.getColumnModel().getColumn(5).setPreferredWidth(90);
            totalTable.getColumnModel().getColumn(6).setMinWidth(100);
            totalTable.getColumnModel().getColumn(6).setPreferredWidth(100);
            totalTable.getColumnModel().getColumn(7).setMinWidth(110);
            totalTable.getColumnModel().getColumn(7).setPreferredWidth(110);
            totalTable.getColumnModel().getColumn(8).setMinWidth(110);
            totalTable.getColumnModel().getColumn(8).setPreferredWidth(120);
            totalTable.getColumnModel().getColumn(9).setMinWidth(130);
            totalTable.getColumnModel().getColumn(9).setPreferredWidth(130);
            totalTable.getColumnModel().getColumn(10).setMinWidth(130);
            totalTable.getColumnModel().getColumn(10).setPreferredWidth(140);
            totalTable.getColumnModel().getColumn(11).setMinWidth(110);
            totalTable.getColumnModel().getColumn(11).setPreferredWidth(110);
            totalTable.getColumnModel().getColumn(12).setMinWidth(120);
            totalTable.getColumnModel().getColumn(12).setPreferredWidth(130);
            totalTable.getColumnModel().getColumn(13).setMinWidth(160);
            totalTable.getColumnModel().getColumn(13).setPreferredWidth(170);
            totalTable.getColumnModel().getColumn(14).setMinWidth(130);
            totalTable.getColumnModel().getColumn(14).setPreferredWidth(140);
            totalTable.getColumnModel().getColumn(15).setMinWidth(170);
            totalTable.getColumnModel().getColumn(15).setPreferredWidth(180);
            totalTable.getColumnModel().getColumn(16).setMinWidth(170);
            totalTable.getColumnModel().getColumn(16).setPreferredWidth(180);
            totalTable.getColumnModel().getColumn(17).setMinWidth(150);
            totalTable.getColumnModel().getColumn(17).setPreferredWidth(160);
            totalTable.getColumnModel().getColumn(18).setMinWidth(200);
            totalTable.getColumnModel().getColumn(18).setPreferredWidth(210);
            totalTable.getColumnModel().getColumn(19).setMinWidth(130);
            totalTable.getColumnModel().getColumn(19).setPreferredWidth(140);
        }

        statusPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        lblLine.setFont(new java.awt.Font("Noto Sans", 0, 11)); // NOI18N
        lblLine.setText("Linha:");

        lblSelectedLine.setFont(new java.awt.Font("Noto Sans", 0, 11)); // NOI18N
        lblSelectedLine.setText("0/0");

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLine)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSelectedLine, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLine)
                    .addComponent(lblSelectedLine))
                .addGap(0, 0, 0))
        );

        mainToolBar.setFloatable(false);
        mainToolBar.setRollover(true);

        btnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/vrfortaleza/upextension/resources/actualsize.GIF"))); // NOI18N
        btnOpen.setToolTipText("Abrir arquivo");
        btnOpen.setFocusable(false);
        btnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOpen.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnOpenActionPerformed(evt);
            }
        });
        mainToolBar.add(btnOpen);

        btnReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/vrfortaleza/upextension/resources/print.GIF"))); // NOI18N
        btnReport.setFocusable(false);
        btnReport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReport.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnReportActionPerformed(evt);
            }
        });
        mainToolBar.add(btnReport);

        chbSomenteOferta.setFont(new java.awt.Font("Noto Sans", 0, 11)); // NOI18N
        chbSomenteOferta.setText("Somente Oferta");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE)
                    .addComponent(statusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainScroll)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chbSomenteOferta)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chbSomenteOferta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setBounds(200, 10, 965, 469);
    }// </editor-fold>//GEN-END:initComponents

    private void mainTableMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_mainTableMouseReleased
    {//GEN-HEADEREND:event_mainTableMouseReleased
        lblSelectedLine.setText((mainTable.getSelectedRow() + 1) + "/" + mainTable.getRowCount());
    }//GEN-LAST:event_mainTableMouseReleased

    private void mainTableMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_mainTableMouseClicked
    {//GEN-HEADEREND:event_mainTableMouseClicked

    }//GEN-LAST:event_mainTableMouseClicked

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnOpenActionPerformed
    {//GEN-HEADEREND:event_btnOpenActionPerformed
        selectFile();
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnReportActionPerformed
    {//GEN-HEADEREND:event_btnReportActionPerformed
        String fromDate = NumberUtil.getDDMMYYYY(periodSales.get(0).getDate());
        String toDate = NumberUtil.getDDMMYYYY(periodSales.get(periodSales.size() - 1).getDate());
        String subtitle = mainForm.getUsername() + " - " + fromDate + " A " + toDate + " - " + mainForm.getStoreNumber() + " - SOMENTE OFERTA: " + getCheckbox();
        JRBeanCollectionDataSource collectionDataSource = new JRBeanCollectionDataSource(periodSales);
        params.put("subtitle", subtitle);
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(FileUtil.jasper("upsalesperiod.jasper"), params, collectionDataSource);
            JasperViewer view = new JasperViewer(jasperPrint, false);
            ViewUtil.addIcon(view);
            view.setTitle("Consulta Venda - VRFortaleza");
            view.setVisible(true);
        } catch (JRException e) {
            Logger.getGlobal().log(Level.SEVERE, "Falha ao gerar relarório", e);
            JOptionPane.showMessageDialog(this, "Falha ao gerar relarório");
        }
    }//GEN-LAST:event_btnReportActionPerformed

    private void selectFile()
    {
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            clearTable(mainTable);
            clearTable(totalTable);
            File file = fileChooser.getSelectedFile();
            loadData(file.getAbsolutePath());
        }
    }

    /**
     * This method is called to clear table content
     */
    private void clearTable(javax.swing.JTable table)
    {
        DefaultTableModel dm = (DefaultTableModel) table.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged(); // notifies the JTable that the model has changed
    }

    private void loadData(String filePath)
    {
        try {
            List<String[]> rows = vendaService.getRows(filePath);
            Map<String, String> totalSum = vendaService.getTotalSum(mainTable, rows);

            fillTotalTable(totalSum);
            fillMainTable(rows);

            this.periodSales = vendaService.getVendas(rows);
            this.params = vendaService.getReportParameters(totalSum);

            totalScroll.getColumnHeader().setVisible(true);
            mainScroll.getColumnHeader().setVisible(true);
            totalScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            mainScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            btnReport.setEnabled(true);
            chbSomenteOferta.setEnabled(true);
            lblSelectedLine.setText(0 + "/" + mainTable.getRowCount());
        } catch (Exception e) {
            MessageUtil.showException(mainForm, "Arquivo selecionado não suportado.", e);
        }
    }

    private void fillMainTable(List<String[]> rows)
    {
        for (String[] row : rows) {
            ((DefaultTableModel) mainTable.getModel()).addRow(row);
        }
    }

    private void fillTotalTable(Map<String, String> map)
    {
        String[] totalRow = new String[totalTable.getColumnCount()];
        for (int i = 0; i < totalTable.getColumnCount(); i++) {
            String columnName = totalTable.getColumnName(i);

            if (map.containsKey(columnName)) {
                totalRow[i] = map.get(columnName);
            }

        }
        ((DefaultTableModel) totalTable.getModel()).addRow(totalRow);
    }

    private String getCheckbox()
    {
        if (this.chbSomenteOferta.isSelected()) {
            return "SIM";
        }
        return "NÃO";
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnReport;
    private javax.swing.JCheckBox chbSomenteOferta;
    private javax.swing.JLabel lblLine;
    private javax.swing.JLabel lblSelectedLine;
    private javax.swing.JScrollPane mainScroll;
    private javax.swing.JTable mainTable;
    private javax.swing.JToolBar mainToolBar;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JScrollPane totalScroll;
    private javax.swing.JTable totalTable;
    // End of variables declaration//GEN-END:variables
}
