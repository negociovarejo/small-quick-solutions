/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.utilities;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * @Date: Mar 1, 2018
 * @author Derick Felix
 */
public class NumberUtil {

    private NumberUtil()
    {
    }

    public static String removePercentageSign(String value)
    {
        return value.replace("%", "").replace(" ", "");
    }

    public static String addPercentageSign(double number)
    {
        return String.format("%.2f %%", number);
    }

    public static double toDouble(String value)
    {
        if (value.isEmpty()) {
            return 0;
        }
        return Double.parseDouble(value.replace(".", "").replace(",", ".").replace("%", ""));
    }

    public static String convertCurrencyFormat(double number)
    {
        return NumberFormat.getCurrencyInstance(Locale.GERMANY).format(number).replace(" €", "");
    }

    public static String getDDMMYYYY(String str)
    {
        return str.substring(0, str.length() - 6);
    }

    /**
     * Returns a number if a given number of digits, if the number has less
     * digit than the given one, it'll have zeros before it.
     *
     * @param aod the amount of digits
     * @param n the number
     * @return a number if a fixed digits
     */
    public static String fixedDigits(int aod, String n)
    {
        if (n.length() < aod) {
            int nzs = aod - n.length();
            String zrs = "";
            for (int i = 0; i < nzs; i++) {
                zrs += "0";
            }
            return zrs + n;
        }
        return n;
    }

}
