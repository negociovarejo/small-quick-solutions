package fortesplus;

import java.util.Locale;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Functions {
  private Functions()
  {
  }

  public static String FormatDecimal(double i_valor, int decimals)
	{    
    if (Double.isNaN(i_valor)) {
      return "0,00";
    }

    String format = "#.";
    for (int i = 0; i < decimals; ++i) {
      format += "#";
    }

    DecimalFormat df2 = new DecimalFormat(format, new DecimalFormatSymbols(Locale.US));
		String value = df2.format(i_valor);
		
		if (value.contains(".")) {
			String sub = value.substring(value.indexOf("."));
			
			while (sub.length() < decimals + 1) {
				sub += "0";
			}
			
			value = value.substring(0, value.indexOf(".")) + sub;
		} else {
			value += ".";

      for (int i = 0; i < decimals; ++i) {
        value += "0";
      }
		}
		
		return value.replace(".", ",");
	}

  public static String FormatDecimal2R(double i_valor)
	{
    return FormatDecimal(i_valor, 2).replace(".", "").replace(",", ".");
	}

  public static String FormatDecimal3R(double i_valor)
	{
    return FormatDecimal(i_valor, 3).replace(".", "").replace(",", ".");
	}
}