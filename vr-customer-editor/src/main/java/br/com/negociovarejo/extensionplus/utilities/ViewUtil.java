/* * Copyright (c) 2020 Negocio Varejo. All rights reserved. * */
package br.com.negociovarejo.extensionplus.utilities;

import java.awt.Cursor;

/**
 *
 * @author derickfelix
 */
public class ViewUtil {
    
    public static final Cursor WAIT_CURSOR = new Cursor(Cursor.WAIT_CURSOR);
    public static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
    
    private ViewUtil()
    {
    }
    
    /**
     * It sets the window icon of a specified Window.
     *
     * @param frame the desired window
     */
    public static void addIcon(java.awt.Window frame)
    {
        try {
            java.net.URL url = frame.getClass().getResource("/login_logo.png");
            java.awt.image.BufferedImage image = javax.imageio.ImageIO.read(url);
            frame.setIconImage(image);
        } catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(ViewUtil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
