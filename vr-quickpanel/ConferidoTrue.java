import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.InputStream;
import javax.swing.JOptionPane;

public class ConferidoTrue {

  public static void main(String[] args)
  {
    String res = JOptionPane.showInputDialog("Informe o código do fornecedor:");
    try {
      long id = Long.parseLong(res);

      QuickPanel.Query("update produto p set conferido = true from produtofornecedor pf where p.id = pf.id_produto and pf.id_fornecedor = " + id);
      JOptionPane.showMessageDialog(null, "Produtos atualizados com sucesso!");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Código informado é inválido!");
    }
  }
}
