/* * Copyright (c) 2019 Negocio Varejo. All rights reserved. * */
package br.com.negocio.varejo.tag.emitter.views.custom;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

/**
 *
 * @author derickfelix
 */
public class NVDesktopPane extends JDesktopPane {

    private final ImageIcon image;

    public NVDesktopPane()
    {
        image = new ImageIcon(getClass().getResource("/br/com/negocio/varejo/tag/emitter/resources/background.png"));
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
