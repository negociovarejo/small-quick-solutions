import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.InputStream;
import javax.swing.JOptionPane;

public class Pesavacao {

  public static void main(String[] args)
  {
    try {
      String res = JOptionPane.showInputDialog("Informe o código do produto:");
      long id = Long.parseLong(res);

      List<String> rs = QuickPanel.Query("select descricaocompleta from produto where id = " + id);

      if (rs.size() != 3) {
        JOptionPane.showMessageDialog(null, "Produto com esse código não encontrado!");
        return;
      }

      String description = rs.get(1);
      boolean flag = false;

      if (JOptionPane.showConfirmDialog(null,
                                        "<html>" +
                                          description + "<br><br>" +
                                          "Marcar como <strong>Pesável</strong>?" +
                                        "</html>",
                                        "Pesável",
                                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        String sql = "update produto p set pesavel = true where p.id = " + id;
        System.out.println(sql);                                          
        QuickPanel.Query(sql);
        flag = true;
      }

      if (JOptionPane.showConfirmDialog(null,
                                        "<html>" +
                                          description + "<br><br>" +
                                          "Marcar como <strong>Fabricação Própria</strong>?" +
                                        "</html>",
                                        "Fabricação Própria",
                                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        String[] stores = QuickPanel.InputStores().split(",");

        if (stores.length == 0 || stores[0].isEmpty()) {
          throw new RuntimeException();
        }

        String sql = "update produtocomplemento pc set fabricacaopropria = true where id_produto = " + id;

        if (!stores[0].equals("All")) {
          sql += " and (";
          
          for (int i = 0; i < stores.length; ++i) {
            sql += "id_loja = " + stores[i];

            if ((i + 1) < stores.length) {
              sql += " or ";
            }
          }
          sql += ")";
        }

        System.out.println(sql);
        QuickPanel.Query(sql);
        flag = true;
      }

      if (flag) {
        JOptionPane.showMessageDialog(null, "Produtos atualizados com sucesso!");
      } else {
        JOptionPane.showMessageDialog(null, "Nenhuma modificação realizada!");
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Informações fornecidas inválidas!");
    }
  }
}
