/* * Copyright (c) 2019 Negocio Varejo. All rights reserved. * */
package br.com.negocio.varejo.tag.emitter.utilities;

import javax.swing.JOptionPane;
import br.com.negocio.varejo.tag.emitter.views.dialogs.ExceptionDialog;

/**
 *
 * @author derickfelix
 */
public class MessageUtil {
    
    /**
     * Show an exception dialog with two buttons, okay (closes the dialog) and
     * detail (shows another dialog detailing the exception).
     *
     * @param parent the calling frame of this dialog
     * @param e the exception which this dialog is showing
     */
    public static void showException(java.awt.Frame parent, Exception e) {
        showException(parent, e.getMessage(), e);
    }

    public static void showException(java.awt.Frame parent, String message, Exception e) {
        Object[] choices = {"Ok", "Detalhes >>>"};
        Object defaultChoice = choices[1];

        if (JOptionPane.showOptionDialog(null, message, "Negocio Varejo", JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE, null, choices, defaultChoice) == 1) {
            new ExceptionDialog(parent, true, e).setVisible(true);
        }
    }
}
