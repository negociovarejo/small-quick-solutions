package fortesplus;

import java.util.List;
import java.util.ArrayList;

import vrframework.classe.App;
import vrframework.classe.Conexao;
import vrframework.classe.Folder;
import vrframework.classe.Java;
import vrframework.classe.LookAndFeel;
import vrframework.classe.Mensagem;
import vrframework.classe.Proxy;
import vrframework.classe.SplashScreen;
import vrframework.classe.VRInstance;
import vrframework.classe.VRProperties;
import vrframework.classe.ProgressBar;
import vrintegracao.classe.Global;
import vrintegracao.dao.BancoDAO;
import vrintegracao.dao.interfaces.Fortes158DAO;
import vrintegracao.vo.Formulario;
import vrintegracao.vo.TipoOperacao;
import vrintegracao.vo.interfaces.fortes.ExportarFortesVO;

public class Fortes158 {

  public static void main(String[] args)
  {
    try {
      if (args.length != 4) {
        System.out.println("Usage: java progName.jar <startDate> <endDate> <path> <storeId>");
        return;
      }

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

      ProgressBar.show();
      ExportarFortesVO oExportacao = new ExportarFortesVO();
      oExportacao.tipoData = 2;
      oExportacao.dataInicio = args[0];
      oExportacao.dataTermino = args[1];
      oExportacao.caminho = args[2];
      oExportacao.participantes = true;
      oExportacao.produto = true;
      oExportacao.notaServico = true;
      oExportacao.notaEntrada = true;
      oExportacao.notaSaida = true;
      oExportacao.conhecimentoTransporteCarga = true;
      oExportacao.inventario = true;
      oExportacao.operacaoCreditoDebito = true;
      oExportacao.outrosValoresDocumento = true;
      oExportacao.cupomFiscalEletronico = true;
      oExportacao.estoqueEscriturado = true;
    
      List<Integer> vLoja = new ArrayList<>();
      vLoja.add(Integer.parseInt(args[3]));

      Fortes158DAO dao = (Fortes158DAO) VRInstance.criar(Fortes158DAO.class);
      dao.exportar(oExportacao, vLoja, Formulario.INTERFACE_EXPORTACAO_FORTES.getId(), TipoOperacao.EXPORTAR.getId()); 
      ProgressBar.dispose();
      Mensagem.exibir("Arquivo exportado com sucesso!", "FortesPlus");

    } catch (Exception ex) {
      ProgressBar.dispose();
      Mensagem.exibirErro(ex, "Atenção");
    } 
  }

}