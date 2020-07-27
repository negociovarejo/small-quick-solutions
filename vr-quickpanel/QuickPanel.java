import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.InputStream;
import java.io.InputStream;
import javax.swing.JOptionPane;

public class QuickPanel {

  private static String script = System.getProperty("os.name").equals("Linux") ? "./database.sh" : "./database.bat";

  private QuickPanel()
  {
  }

  /**
    @return "All" or a comma separated storeIds
   */
  public static String InputStores()
  {
    List<String> resultSet = QuickPanel.Query("select id, descricao from loja order by id desc");

    String textStores = "";
    for (int i = 1; i < resultSet.size() - 1; ++i) {
      String tokens[] = resultSet.get(i).split(";");
      textStores += tokens[0] + " -> " + tokens[1] + "<br>";
    }

    textStores += "x -> Todos<br>";

    String inputStores[] = JOptionPane.showInputDialog("<html>Informe os números das lojas separando por vírgula, ou * para todos: <br>" + textStores + "</html>").split(",");
    
    if (inputStores.length == 0) {
      throw new RuntimeException();
    }


    if (inputStores[0].equals("x")) {
      return "All";
    } else {
      String stores = "";

      for (int i = 0; i < inputStores.length; ++i) {
        if (inputStores[i].length() > 0) {
          try {
            stores += Long.parseLong(inputStores[i]) + ",";
          } catch (Exception e) {}
        }
      }
      
      return stores;
    }
  }

  public static List<String> Query(String query)
  {
    try {
      Process process = Runtime.getRuntime().exec(new String[]{script, query});

      return ReadFrom(process.getInputStream());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  private static List<String> ReadFrom(InputStream inputStream)
  {
    List<String> lines = new ArrayList<>();
    try (Scanner scanner = new Scanner(inputStream)) {
      while (scanner.hasNext()) {
        lines.add(scanner.nextLine());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return lines;
  }
}