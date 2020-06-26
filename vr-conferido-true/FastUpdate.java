import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.InputStream;
import javax.swing.JOptionPane;

public class FastUpdate {

    public static void main(String[] args)
    {
        String res = JOptionPane.showInputDialog("Digite o código do fornecedor:");
        try {
            long id = Long.parseLong(res);

            Query("update produto p set conferido = true from produtofornecedor pf where p.id = pf.id_produto and pf.id_fornecedor = " + id);
            JOptionPane.showMessageDialog(null, "Produtos atualizados com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Código informado é inválido!");
        }
    }

    private static List<String> Query(String query)
    {
        try {
            String script = System.getProperty("os.name").equals("Linux") ? "./database.sh" : "./database.bat";
            Process process = Runtime.getRuntime().exec(new String[]{script, query});

            return readFrom(process.getInputStream());
        } catch (Exception e) {
          e.printStackTrace();
        }

        return null;
    }

    private static List<String> readFrom(InputStream inputStream)
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
