/* * Copyright (c) 2019 Negocio Varejo. All rights reserved. * */
package br.com.negocio.varejo.tag.emitter.utilities;

/**
 *
 * @author derickfelix
 */
public class NumberUtil {
    
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
        if (n == null) {
            return "7890000000000";
        }
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
