package fortesplus;

import java.util.List;
import java.util.ArrayList;

import vrframework.classe.App;
import vrframework.classe.Conexao;
import vrframework.classe.Folder;
import vrframework.classe.LookAndFeel;
import vrframework.classe.Mensagem;
import vrframework.classe.Proxy;
import vrframework.classe.SplashScreen;
import vrframework.classe.VRProperties;
import vrintegracao.classe.Global;
import vrintegracao.dao.BancoDAO;
import fortesplus.gui.Fortes158;

public class FortesPlus {

  public static void main(String[] args)
  {
    try {
      LookAndFeel.set();
      App.setName("FortesPlus");
      Folder.changeVRFolder();
      SplashScreen.show();
      SplashScreen.setSobre("Fortes Plus", Global.getVersaoRelease(), Global.getData());
      SplashScreen.setStatus("Inicializando sistema...");
      String ipBanco = VRProperties.getString("database.ip");
      String ipSecBanco = VRProperties.getString("database.ipsec");
      int portaBanco = VRProperties.getInt("database.porta");
      String nomeBanco = VRProperties.getString("database.nome");
      String usuarioBanco = VRProperties.getString("database.usuario").isEmpty() ? "postgres" : VRProperties.getString("database.usuario");
      String senhaBanco = VRProperties.getString("database.senha").isEmpty() ? "postgres" : VRProperties.getString("database.senha");
      SplashScreen.setStatus("Abrindo conexão");
      Conexao.abrirConexao(ipBanco, ipSecBanco, portaBanco, nomeBanco, usuarioBanco, senhaBanco);
      Global.ipBanco = ipBanco;
      Global.portaBanco = portaBanco;
      Global.nomeBanco = nomeBanco;
      Global.usuarioBanco = usuarioBanco;
      Global.senhaBanco = senhaBanco;
      Proxy.ip = VRProperties.getString("proxy.ip");
      Proxy.porta = VRProperties.getString("proxy.porta");
      Proxy.usuario = VRProperties.getString("proxy.usuario");
      Proxy.senha = VRProperties.getString("proxy.senha");
      
      if (!Proxy.ip.isEmpty()) {
        Proxy.setProxy(); 
      }

      SplashScreen.setStatus("Atualizando banco...");
      (new BancoDAO()).atualizar();
      SplashScreen.dispose();

      Fortes158 form = new Fortes158();
      form.setVisible(true);
    } catch (Exception ex) {
      Mensagem.exibirErro(ex, "Atenção");
    } 
  }

}