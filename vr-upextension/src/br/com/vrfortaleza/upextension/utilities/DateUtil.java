/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author derickfelix
 */
public class DateUtil {

    public static Date parse(String source, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(source);
        } catch (ParseException ex) {
            Logger.getGlobal().log(Level.SEVERE, "Failed to parse " + source + " to " + format + " format", ex);
        }
        return Calendar.getInstance().getTime();
    }

    public static String format(Date date, String format)
    {
        DateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
    
}
