/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.utilities;

import br.com.vrfortaleza.upextension.models.Entity;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * @Date: Feb 28, 2018
 * @author Derick Felix
 */
public class ViewUtil {

    public static final Cursor WAIT_CURSOR = new Cursor(Cursor.WAIT_CURSOR);
    public static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);

    /**
     * It sets the window icon of a specified Window.
     *
     * @param frame the desired window
     */
    public static void addIcon(java.awt.Window frame)
    {
        try {
            java.net.URL url = frame.getClass().getResource("/br/com/vrfortaleza/upextension/resources/partner.png");
            java.awt.image.BufferedImage image = javax.imageio.ImageIO.read(url);
            frame.setIconImage(image);
        } catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(ViewUtil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public static void adjustTableColumn(JTable table)
    {
        for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                //  We've exceeded the maximum width, no need to check other rows
                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }

            tableColumn.setPreferredWidth(preferredWidth);
        }
    }

    /**
     * Get the selected entity from a table (Table must contains the id field as
     * the first column). Returns {@code null} if no entity matched the id field
     * of the selected item of the table.
     *
     * @param <E> an {@link Entity entity} type
     * @param selectedRow the selected element from the table
     * @param table the owner of the rows
     * @param entities the entities from the table as a list
     * @return the selected entity from the table.
     */
    public static <E extends Entity> E getEntityFromTable(int selectedRow, JTable table, List<E> entities)
    {
        if (selectedRow > table.getRowCount()) {
            throw new IllegalArgumentException("Selected row must be less than " + table.getRowCount() + ", given: " + selectedRow);
        }
        // hide table
        Container container = table.getParent().getParent();
        ((JScrollPane) container).setVisible(false);
        // retrieve the entity from its id on the table
        long id = Long.parseLong((String) table.getModel().getValueAt(selectedRow, 0));

        for (E entity : entities) {
            if (entity.getId() == id) {
                return entity;
            }
        }

        return null;
    }

    public static StringBuilder performTableQuery(JLabel lblSearch, StringBuilder buffer, JTable searchTable, TableRowSorter<TableModel> rowSorter, KeyEvent evt)
    {
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            char[] old = buffer.toString().toCharArray();
            buffer = new StringBuilder();
            for (int i = 0; i < old.length - 1; ++i) {
                buffer.append(old[i]);
            }
        } else {
            char c = evt.getKeyChar();
            if (Character.isLetterOrDigit(c) || c == ' ') {
                buffer.append(evt.getKeyChar());
            }
        }

        if (buffer.toString().length() == 0) {
            lblSearch.setVisible(false);
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + buffer.toString()));
            lblSearch.setVisible(true);
            lblSearch.requestFocus();
            searchTable.requestFocus();
        }

        lblSearch.setText("Pesquisa: " + buffer.toString());
        
        return buffer;
    }
}
