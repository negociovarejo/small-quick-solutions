package fortesplus;

import java.sql.ResultSet;
import java.util.Locale;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import vrframework.classe.Conexao;
import vrframework.classe.VRStatement;

public class Functions {

  private static String MasterVersion = null;

  private Functions()
  {
  }

  private static String GetMasterVersion()
  {
    if (MasterVersion == null) {
      try {
        Conexao.begin();
        VRStatement stm = VRStatement.createStatement();
        ResultSet res = stm.executeQuery("select versao from versao where id_programa = 0");
        
        if (res.next()) {
          MasterVersion = res.getString("versao");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return MasterVersion;
  }

  private static int[] GetVersionNumbers(String ver)
  {
    if (ver == null) {
      return null;
    }

    String[] tokens = ver.split("\\.");
    int[] versions = new int[tokens.length];
    
    for (int i = 0; i < tokens.length; ++i) {
      versions[i] = Integer.parseInt(tokens[i]);
    }
    
    return versions;
  }
  
  public static int MasterVersionComparedWith(String version)
  {
    int[] a = GetVersionNumbers(GetMasterVersion());
    int[] b = GetVersionNumbers(version);
    
    if (a == null || a.length != 3 || b == null || b.length != 3) {
      throw new IllegalArgumentException("Invalid version param given!");
    }
            
    for (int i = 0; i < a.length; ++i) {
      if (a[i] > b[i]) {
        return 1;
      } else if (a[i] < b[i]) {
        return -1;
      }
    }
    
    return 0;
  }

  private static String FormatDecimal(double i_valor, int decimals)
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

  public static boolean ArrayUtilsContains(int[] array, int value)
  {
    for (int i = 0; i < array.length; ++i) {
      if (array[i] == value) {
        return true;
      }
    }

    return false;
  }
}