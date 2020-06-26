/* * Copyright (c) 2020 Negocio Varejo. All rights reserved. * */
package br.com.negociovarejo.extensionplus;

import br.com.negociovarejo.extensionplus.views.LoginForm;

/**
 *
 * @author derickfelix
 */
public class ExtensionPlus {

    public static void main(String[] args)
    {
        /* Set the System look and feel */
        //<editor-fold defaultstate="collapsed" desc="System look and feel setting code">
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
       
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}
