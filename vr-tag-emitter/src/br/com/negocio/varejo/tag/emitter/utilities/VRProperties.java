/* * Copyright (c) 2019 Negocio Varejo. All rights reserved. * */
package br.com.negocio.varejo.tag.emitter.utilities;

import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author acer
 */
public class VRProperties {
    private VRProperties()
    {
    }
    
    public static java.util.Properties getProperties()
    {
        try {
            java.util.Properties properties = new java.util.Properties();
            properties.load(new FileInputStream("/vr/vr.properties"));
            return properties;
        } catch (IOException e) {
            MessageUtil.showException(null, e);
        }
        return null;
    }
}
