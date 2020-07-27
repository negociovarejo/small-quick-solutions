import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.InputStream;

public class QuickPanel {

  private static String script = System.getProperty("os.name").equals("Linux") ? "./database.sh" : "./database.bat";

  private QuickPanel()
  {
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