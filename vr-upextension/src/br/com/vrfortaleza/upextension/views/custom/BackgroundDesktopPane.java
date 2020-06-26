/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.views.custom;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

/**
 *
 * @author derickfelix
 */
public class BackgroundDesktopPane extends JDesktopPane {

    private final ImageIcon image;

    public BackgroundDesktopPane()
    {
        image = new ImageIcon(getClass().getResource("/br/com/vrfortaleza/upextension/resources/background.png"));
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int x = (getWidth() - image.getIconWidth()) / 2;
        int y = (getHeight() - image.getIconHeight()) / 2;
        g.drawImage(image.getImage(), x, y, this);
    }

}
