package vrintegracao.dao.interfaces;

import java.sql.ResultSet;
import java.sql.Statement;
import vrframework.classe.Conexao;
import vrframework.classe.Database;
import vrframework.classe.VRInstance;
import vrintegracao.dao.LogTransacaoDAO;
import vrintegracao.dao.cadastro.UsuarioDAO;
import vrintegracao.vo.Formulario;
import vrintegracao.vo.TipoFornecedor;
import vrintegracao.vo.TipoFreteNotaFiscal;
import vrintegracao.vo.TipoTransacao;
import vrintegracao.vo.interfaces.fortes.FortesConfiguracaoLojaVO;
import vrintegracao.vo.interfaces.fortes.FortesConfiguracaoTipoProdutoVO;
import vrintegracao.vo.interfaces.fortes.FortesConfiguracaoVO;

public class FortesDAO {
  public void salvarParametro(FortesConfiguracaoVO i_configuracao) throws Exception
  {
    Statement stm = null;
    StringBuilder sql = null;
    ResultSet rst = null;
    try {
      Conexao.begin();
      stm = Conexao.createStatement();
      for (FortesConfiguracaoLojaVO oLoja : i_configuracao.vLoja) {
        rst = stm.executeQuery("SELECT id FROM fortes.parametro WHERE id_loja = " + oLoja.idLoja);
        if (rst.next()) {
          sql = new StringBuilder();
          sql.append("UPDATE fortes.parametro SET");
          sql.append(" codigoempresa = '" + oLoja.codigoEmpresa + "',");
          sql.append(" codigoestabelecimento = '" + oLoja.codigoEstabelecimento + "',");
          sql.append(" codigoincidencia = '" + oLoja.codigoIncidencia + "',");
          sql.append(" aliquotasespecificas = " + oLoja.aliquotasEspecificas);
          sql.append(" WHERE id_loja = " + oLoja.idLoja);
          stm.execute(sql.toString());
        } else {
          sql = new StringBuilder();
          sql.append("INSERT INTO fortes.parametro (id_loja, codigoempresa, codigoestabelecimento, codigoincidencia, aliquotasespecificas) VALUES (");
          sql.append(oLoja.idLoja + ",");
          sql.append("'" + oLoja.codigoEmpresa + "',");
          sql.append("'" + oLoja.codigoEstabelecimento + "',");
          sql.append("'" + oLoja.codigoIncidencia + "',");
          sql.append(oLoja.aliquotasEspecificas + ")");
          stm.execute(sql.toString());
        } 
        if (Database.schemaExiste("sped")) {
          sql = new StringBuilder();
          sql.append("UPDATE sped.configuracao SET");
          sql.append(" codigoincidencia = '" + oLoja.codigoIncidencia + "'");
          sql.append(" WHERE id_loja = '" + oLoja.idLoja + "'");
          stm.execute(sql.toString());
        } 
      } 
      stm.execute("DELETE FROM fortes.inventarioparametro");
      for (FortesConfiguracaoTipoProdutoVO oTipoProduto : i_configuracao.vTipoProduto) {
        sql = new StringBuilder();
        sql.append("INSERT INTO fortes.inventarioparametro (id_tipoproduto) VALUES (" + oTipoProduto.idTipoProduto + ")");
        stm.execute(sql.toString());
      } 
      for (FortesConfiguracaoLojaVO oLoja : i_configuracao.vLoja)
        (new LogTransacaoDAO()).gerar(Formulario.INTERFACE_EXPORTACAO_FORTES, TipoTransacao.CONFIGURACAO, 0L, "", oLoja.idLoja); 
      stm.close();
      Conexao.commit();
    } catch (Exception ex) {
      Conexao.rollback();
      throw ex;
    } 
  }
  
  public FortesConfiguracaoVO carregarConfiguracao(int i_idFormulario, int i_idTipoOperacao) throws Exception {
    Statement stm = null;
    StringBuilder sql = null;
    ResultSet rst = null;
    stm = Conexao.createStatement();
    String loja = ((UsuarioDAO)VRInstance.criar(UsuarioDAO.class)).getLojasPermissao(i_idFormulario, i_idTipoOperacao);
    if (loja.equals(""))
      loja = "-1"; 
    sql = new StringBuilder();
    sql.append("SELECT loja.id AS id_loja, loja.descricao AS loja, p.codigoempresa, p.codigoestabelecimento,");
    sql.append(" p.codigoincidencia, p.aliquotasespecificas");
    sql.append(" FROM loja");
    sql.append(" LEFT JOIN fortes.parametro AS p ON p.id_loja = loja.id");
    sql.append(" WHERE loja.id IN (" + loja + ")");
    sql.append(" ORDER BY loja.descricao");
    rst = stm.executeQuery(sql.toString());
    FortesConfiguracaoVO oConfiguracao = new FortesConfiguracaoVO();
    while (rst.next()) {
      FortesConfiguracaoLojaVO oLoja = new FortesConfiguracaoLojaVO();
      oLoja.idLoja = rst.getInt("id_loja");
      oLoja.loja = rst.getString("loja");
      oLoja.codigoEmpresa = rst.getString("codigoempresa");
      oLoja.codigoEstabelecimento = rst.getString("codigoestabelecimento");
      oLoja.codigoIncidencia = rst.getInt("codigoincidencia");
      oLoja.aliquotasEspecificas = rst.getInt("aliquotasespecificas");
      oConfiguracao.vLoja.add(oLoja);
    } 
    sql = new StringBuilder();
    sql.append("SELECT id_tipoproduto FROM fortes.inventarioparametro");
    rst = stm.executeQuery(sql.toString());
    while (rst.next()) {
      FortesConfiguracaoTipoProdutoVO oTipoProduto = new FortesConfiguracaoTipoProdutoVO();
      oTipoProduto.idTipoProduto = rst.getInt("id_tipoproduto");
      oConfiguracao.vTipoProduto.add(oTipoProduto);
    } 
    return oConfiguracao;
  }
  
  public boolean isFornecedorDistribuidor(int i_idLoja) throws Exception {
    Statement stm = null;
    StringBuilder sql = null;
    ResultSet rst = null;
    stm = Conexao.createStatement();
    sql = new StringBuilder();
    sql.append("SELECT f.id_tipofornecedor FROM fornecedor f");
    sql.append(" INNER JOIN loja l ON l.id_fornecedor = f.id");
    sql.append(" WHERE l.id = " + i_idLoja);
    rst = stm.executeQuery(sql.toString());
    if (rst.next()) {
      if (rst.getInt("id_tipofornecedor") == TipoFornecedor.DISTRIBUIDOR.getId())
        return true; 
      return false;
    } 
    return false;
  }
  
  public String getCodigoEmpresa(int i_idLoja) throws Exception {
    Statement stm = null;
    StringBuilder sql = null;
    ResultSet rst = null;
    stm = Conexao.createStatement();
    sql = new StringBuilder();
    sql.append("SELECT * FROM fortes.parametro WHERE id_loja = " + i_idLoja);
    rst = stm.executeQuery(sql.toString());
    if (rst.next())
      return rst.getString("codigoempresa"); 
    return "";
  }
  
  public String getCodigoEstabelecimento(int i_idLoja) throws Exception {
    Statement stm = null;
    StringBuilder sql = null;
    ResultSet rst = null;
    stm = Conexao.createStatement();
    sql = new StringBuilder();
    sql.append("SELECT * FROM fortes.parametro WHERE id_loja = " + i_idLoja);
    rst = stm.executeQuery(sql.toString());
    if (rst.next())
      return rst.getString("codigoestabelecimento"); 
    return "";
  }
  
  public int getAliquotasEspecificas(int i_idLoja) throws Exception {
    Statement stm = null;
    StringBuilder sql = null;
    ResultSet rst = null;
    stm = Conexao.createStatement();
    sql = new StringBuilder();
    sql.append("SELECT aliquotasespecificas FROM fortes.parametro WHERE id_loja = " + i_idLoja);
    rst = stm.executeQuery(sql.toString());
    if (rst.next())
      return rst.getInt("aliquotasespecificas"); 
    return -1;
  }
  
  public double getValorImpostoNotaDespesa(int i_idNotaDespesa, int i_idTipoImpostoDespesa) throws Exception {
    Statement stm = null;
    StringBuilder sql = null;
    ResultSet rst = null;
    stm = Conexao.createStatement();
    sql = new StringBuilder();
    sql.append("SELECT valor FROM notadespesaimposto AS nei");
    sql.append(" INNER JOIN tipoimpostodespesa AS tid ON tid.id = nei.id_tipoimpostodespesa");
    sql.append(" WHERE id_notadespesa = " + i_idNotaDespesa);
    sql.append(" AND id_tipoimpostodespesa = " + i_idTipoImpostoDespesa);
    rst = stm.executeQuery(sql.toString());
    if (rst.next())
      return rst.getDouble("valor"); 
    return 0.0D;
  }
  
  public int converteTipoFrete(int i_tipoFrete) {
    if (TipoFreteNotaFiscal.SEM_COBRANCA.getId() == i_tipoFrete)
      return 0; 
    if (TipoFreteNotaFiscal.EMITENTE.getId() == i_tipoFrete)
      return 1; 
    if (TipoFreteNotaFiscal.DESTINATARIO.getId() == i_tipoFrete)
      return 2; 
    return 9;
  }
  
  public int converteCstPisCofins(int i_cst) {
    int cst = i_cst;
    switch (i_cst) {
      case 50:
        cst = 1;
        break;
      case 73:
        cst = 6;
        break;
      case 75:
        cst = 5;
        break;
      case 70:
        cst = 4;
        break;
      case 60:
        cst = 2;
        break;
      case 51:
        cst = 3;
        break;
      case 99:
        cst = 49;
        break;
      case 98:
        cst = 49;
        break;
    } 
    return cst;
  }
  
  public int converteTipoNaturezaReceita(int i_tiponaturezareceita, int i_cst) throws Exception {
    int retorno = 0;
    if (i_cst == 2) {
      switch (i_tiponaturezareceita) {
        case 1:
          retorno = 1;
          return retorno;
        case 2:
          retorno = 2;
          return retorno;
        case 3:
          retorno = 3;
          return retorno;
        case 4:
          retorno = 4;
          return retorno;
        case 101:
          retorno = 1;
          return retorno;
        case 102:
          retorno = 2;
          return retorno;
        case 103:
          retorno = 3;
          return retorno;
        case 104:
          retorno = 4;
          return retorno;
        case 105:
          retorno = 5;
          return retorno;
        case 106:
          retorno = 6;
          return retorno;
        case 107:
          retorno = 7;
          return retorno;
        case 108:
          retorno = 8;
          return retorno;
        case 109:
          retorno = 9;
          return retorno;
        case 112:
          retorno = 12;
          return retorno;
        case 113:
          retorno = 13;
          return retorno;
        case 150:
          retorno = 150;
          return retorno;
        case 151:
          retorno = 151;
          return retorno;
        case 201:
          retorno = 14;
          return retorno;
        case 202:
          retorno = 15;
          return retorno;
        case 301:
          retorno = 16;
          return retorno;
        case 302:
          retorno = 17;
          return retorno;
        case 303:
          retorno = 18;
          return retorno;
        case 304:
          retorno = 19;
          return retorno;
        case 401:
          retorno = 20;
          return retorno;
        case 402:
          retorno = 21;
          return retorno;
        case 403:
          retorno = 22;
          return retorno;
        case 404:
          retorno = 23;
          return retorno;
        case 405:
          retorno = 24;
          return retorno;
        case 406:
          retorno = 25;
          return retorno;
        case 407:
          retorno = 26;
          return retorno;
        case 415:
          retorno = 926;
          return retorno;
        case 416:
          retorno = 928;
          return retorno;
        case 419:
          retorno = 934;
          return retorno;
        case 420:
          retorno = 936;
          return retorno;
        case 423:
          retorno = 942;
          return retorno;
        case 424:
          retorno = 944;
          return retorno;
      } 
      retorno = 0;
    } else if (i_cst == 3) {
      switch (i_tiponaturezareceita) {
        case 101:
          retorno = 53;
          return retorno;
        case 102:
          retorno = 54;
          return retorno;
        case 103:
          retorno = 55;
          return retorno;
        case 104:
          retorno = 56;
          return retorno;
        case 105:
          retorno = 57;
          return retorno;
        case 106:
          retorno = 58;
          return retorno;
        case 107:
          retorno = 59;
          return retorno;
        case 108:
          retorno = 60;
          return retorno;
        case 109:
          retorno = 61;
          return retorno;
        case 110:
          retorno = 62;
          return retorno;
        case 111:
          retorno = 63;
          return retorno;
        case 112:
          retorno = 64;
          return retorno;
        case 113:
          retorno = 65;
          return retorno;
        case 701:
          retorno = 66;
          return retorno;
        case 702:
          retorno = 67;
          return retorno;
        case 703:
          retorno = 68;
          return retorno;
        case 704:
          retorno = 69;
          return retorno;
        case 705:
          retorno = 70;
          return retorno;
        case 706:
          retorno = 71;
          return retorno;
        case 707:
          retorno = 72;
          return retorno;
        case 708:
          retorno = 73;
          return retorno;
        case 709:
          retorno = 74;
          return retorno;
        case 710:
          retorno = 75;
          return retorno;
        case 712:
          retorno = 77;
          return retorno;
        case 751:
          retorno = 377;
          return retorno;
        case 752:
          retorno = 379;
          return retorno;
        case 753:
          retorno = 381;
          return retorno;
        case 754:
          retorno = 383;
          return retorno;
        case 755:
          retorno = 385;
          return retorno;
        case 756:
          retorno = 387;
          return retorno;
        case 757:
          retorno = 389;
          return retorno;
        case 758:
          retorno = 391;
          return retorno;
        case 759:
          retorno = 393;
          return retorno;
        case 760:
          retorno = 395;
          return retorno;
        case 762:
          retorno = 397;
          return retorno;
        case 811:
          retorno = 78;
          return retorno;
        case 812:
          retorno = 79;
          return retorno;
        case 821:
          retorno = 80;
          return retorno;
        case 822:
          retorno = 81;
          return retorno;
        case 900:
          retorno = 694;
          return retorno;
        case 910:
          retorno = 903;
          return retorno;
        case 920:
          retorno = 641;
          return retorno;
        case 930:
          retorno = 401;
          return retorno;
        case 940:
          retorno = 437;
          return retorno;
        case 950:
          retorno = 454;
          return retorno;
        case 961:
          retorno = 488;
          return retorno;
        case 962:
          retorno = 489;
          return retorno;
        case 970:
          retorno = 490;
          return retorno;
        case 980:
          retorno = 527;
          return retorno;
        case 990:
          retorno = 545;
          return retorno;
      } 
      retorno = 0;
    } else if (i_cst == 4) {
      switch (i_tiponaturezareceita) {
        case 1:
          retorno = 695;
          return retorno;
        case 2:
          retorno = 696;
          return retorno;
        case 3:
          retorno = 697;
          return retorno;
        case 4:
          retorno = 698;
          return retorno;
        case 27:
          retorno = 27;
          return retorno;
        case 101:
          retorno = 362;
          return retorno;
        case 102:
          retorno = 363;
          return retorno;
        case 103:
          retorno = 364;
          return retorno;
        case 104:
          retorno = 365;
          return retorno;
        case 105:
          retorno = 366;
          return retorno;
        case 106:
          retorno = 367;
          return retorno;
        case 107:
          retorno = 33;
          return retorno;
        case 108:
          retorno = 34;
          return retorno;
        case 109:
          retorno = 35;
          return retorno;
        case 112:
          retorno = 38;
          return retorno;
        case 113:
          retorno = 39;
          return retorno;
        case 150:
          retorno = 358;
          return retorno;
        case 151:
          retorno = 360;
          return retorno;
        case 201:
          retorno = 40;
          return retorno;
        case 202:
          retorno = 41;
          return retorno;
        case 301:
          retorno = 42;
          return retorno;
        case 302:
          retorno = 43;
          return retorno;
        case 303:
          retorno = 44;
          return retorno;
        case 304:
          retorno = 45;
          return retorno;
        case 401:
          retorno = 46;
          return retorno;
        case 402:
          retorno = 47;
          return retorno;
        case 403:
          retorno = 48;
          return retorno;
        case 404:
          retorno = 49;
          return retorno;
        case 405:
          retorno = 50;
          return retorno;
        case 406:
          retorno = 51;
          return retorno;
        case 407:
          retorno = 361;
          return retorno;
        case 415:
          retorno = 926;
          return retorno;
        case 416:
          retorno = 928;
          return retorno;
        case 419:
          retorno = 934;
          return retorno;
        case 420:
          retorno = 936;
          return retorno;
        case 423:
          retorno = 942;
          return retorno;
        case 424:
          retorno = 944;
          return retorno;
        case 701:
          retorno = 143;
          return retorno;
        case 702:
          retorno = 144;
          return retorno;
        case 703:
          retorno = 145;
          return retorno;
        case 704:
          retorno = 146;
          return retorno;
        case 705:
          retorno = 147;
          return retorno;
        case 706:
          retorno = 148;
          return retorno;
        case 707:
          retorno = 149;
          return retorno;
        case 708:
          retorno = 150;
          return retorno;
        case 709:
          retorno = 151;
          return retorno;
        case 710:
          retorno = 152;
          return retorno;
        case 712:
          retorno = 154;
          return retorno;
        case 751:
          retorno = 378;
          return retorno;
        case 752:
          retorno = 380;
          return retorno;
        case 753:
          retorno = 382;
          return retorno;
        case 754:
          retorno = 384;
          return retorno;
        case 755:
          retorno = 386;
          return retorno;
        case 756:
          retorno = 388;
          return retorno;
        case 757:
          retorno = 390;
          return retorno;
        case 758:
          retorno = 392;
          return retorno;
        case 759:
          retorno = 394;
          return retorno;
        case 760:
          retorno = 396;
          return retorno;
        case 762:
          retorno = 398;
          return retorno;
        case 811:
          retorno = 155;
          return retorno;
        case 812:
          retorno = 156;
          return retorno;
        case 821:
          retorno = 157;
          return retorno;
        case 822:
          retorno = 158;
          return retorno;
        case 900:
          retorno = 703;
          return retorno;
        case 910:
          retorno = 168;
          return retorno;
        case 920:
          retorno = 169;
          return retorno;
        case 930:
          retorno = 159;
          return retorno;
        case 940:
          retorno = 160;
          return retorno;
        case 950:
          retorno = 161;
          return retorno;
        case 961:
          retorno = 162;
          return retorno;
        case 962:
          retorno = 163;
          return retorno;
        case 970:
          retorno = 164;
          return retorno;
        case 980:
          retorno = 165;
          return retorno;
        case 990:
          retorno = 166;
          return retorno;
      } 
      retorno = 0;
    } else if (i_cst == 5) {
      switch (i_tiponaturezareceita) {
        case 101:
          retorno = 207;
          return retorno;
        case 102:
          retorno = 102;
          return retorno;
        case 201:
          retorno = 208;
          return retorno;
        case 301:
          retorno = 209;
          return retorno;
        case 401:
          retorno = 210;
          return retorno;
        case 402:
          retorno = 211;
          return retorno;
        case 403:
          retorno = 212;
          return retorno;
        case 404:
          retorno = 213;
          return retorno;
        case 405:
          retorno = 214;
          return retorno;
        case 406:
          retorno = 215;
          return retorno;
        case 407:
          retorno = 216;
          return retorno;
        case 408:
          retorno = 217;
          return retorno;
        case 409:
          retorno = 218;
          return retorno;
      } 
      retorno = 0;
    } else if (i_cst == 6) {
      switch (i_tiponaturezareceita) {
        case 101:
          retorno = 219;
          return retorno;
        case 102:
          retorno = 220;
          return retorno;
        case 103:
          retorno = 221;
          return retorno;
        case 104:
          retorno = 222;
          return retorno;
        case 105:
          retorno = 223;
          return retorno;
        case 106:
          retorno = 224;
          return retorno;
        case 107:
          retorno = 225;
          return retorno;
        case 108:
          retorno = 226;
          return retorno;
        case 109:
          retorno = 227;
          return retorno;
        case 110:
          retorno = 228;
          return retorno;
        case 111:
          retorno = 229;
          return retorno;
        case 112:
          retorno = 230;
          return retorno;
        case 113:
          retorno = 231;
          return retorno;
        case 114:
          retorno = 232;
          return retorno;
        case 115:
          retorno = 233;
          return retorno;
        case 116:
          retorno = 234;
          return retorno;
        case 117:
          retorno = 235;
          return retorno;
        case 118:
          retorno = 236;
          return retorno;
        case 119:
          retorno = 642;
          return retorno;
        case 120:
          retorno = 663;
          return retorno;
        case 121:
          retorno = 868;
          return retorno;
        case 122:
          retorno = 869;
          return retorno;
        case 123:
          retorno = 870;
          return retorno;
        case 124:
          retorno = 871;
          return retorno;
        case 125:
          retorno = 872;
          return retorno;
        case 126:
          retorno = 873;
          return retorno;
        case 127:
          retorno = 874;
          return retorno;
        case 128:
          retorno = 875;
          return retorno;
        case 129:
          retorno = 876;
          return retorno;
        case 130:
          retorno = 877;
          return retorno;
        case 201:
          retorno = 237;
          return retorno;
        case 202:
          retorno = 238;
          return retorno;
        case 203:
          retorno = 239;
          return retorno;
        case 204:
          retorno = 240;
          return retorno;
        case 205:
          retorno = 241;
          return retorno;
        case 206:
          retorno = 242;
          return retorno;
        case 207:
          retorno = 243;
          return retorno;
        case 208:
          retorno = 244;
          return retorno;
        case 209:
          retorno = 245;
          return retorno;
        case 210:
          retorno = 246;
          return retorno;
        case 211:
          retorno = 247;
          return retorno;
        case 212:
          retorno = 248;
          return retorno;
        case 301:
          retorno = 249;
          return retorno;
        case 302:
          retorno = 250;
          return retorno;
        case 303:
          retorno = 251;
          return retorno;
        case 304:
          retorno = 252;
          return retorno;
        case 305:
          retorno = 253;
          return retorno;
        case 306:
          retorno = 254;
          return retorno;
        case 307:
          retorno = 255;
          return retorno;
        case 308:
          retorno = 256;
          return retorno;
        case 401:
          retorno = 257;
          return retorno;
        case 402:
          retorno = 258;
          return retorno;
        case 403:
          retorno = 259;
          return retorno;
        case 404:
          retorno = 260;
          return retorno;
        case 405:
          retorno = 261;
          return retorno;
        case 406:
          retorno = 262;
          return retorno;
        case 407:
          retorno = 655;
          return retorno;
        case 409:
          retorno = 668;
          return retorno;
        case 410:
          retorno = 669;
          return retorno;
        case 411:
          retorno = 670;
          return retorno;
        case 412:
          retorno = 671;
          return retorno;
        case 413:
          retorno = 672;
          return retorno;
        case 414:
          retorno = 673;
          return retorno;
        case 901:
          retorno = 263;
          return retorno;
        case 902:
          retorno = 264;
          return retorno;
        case 903:
          retorno = 265;
          return retorno;
        case 904:
          retorno = 266;
          return retorno;
        case 905:
          retorno = 267;
          return retorno;
        case 906:
          retorno = 268;
          return retorno;
        case 907:
          retorno = 269;
          return retorno;
        case 908:
          retorno = 270;
          return retorno;
        case 909:
          retorno = 271;
          return retorno;
        case 910:
          retorno = 272;
          return retorno;
        case 911:
          retorno = 273;
          return retorno;
        case 912:
          retorno = 274;
          return retorno;
        case 914:
          retorno = 674;
          return retorno;
        case 999:
          retorno = 275;
          return retorno;
      } 
      retorno = 0;
    } else if (i_cst == 7) {
      switch (i_tiponaturezareceita) {
        case 101:
          retorno = 276;
          return retorno;
        case 102:
          retorno = 277;
          return retorno;
        case 103:
          retorno = 278;
          return retorno;
        case 104:
          retorno = 279;
          return retorno;
        case 201:
          retorno = 281;
          return retorno;
        case 202:
          retorno = 282;
          return retorno;
        case 301:
          retorno = 284;
          return retorno;
        case 401:
          retorno = 286;
          return retorno;
        case 402:
          retorno = 287;
          return retorno;
        case 403:
          retorno = 288;
          return retorno;
        case 901:
          retorno = 290;
          return retorno;
        case 902:
          retorno = 291;
          return retorno;
        case 999:
          retorno = 292;
          return retorno;
      } 
      retorno = 0;
    } else if (i_cst == 8) {
      switch (i_tiponaturezareceita) {
        case 101:
          retorno = 294;
          return retorno;
        case 102:
          retorno = 295;
          return retorno;
        case 201:
          retorno = 297;
          return retorno;
        case 301:
          retorno = 299;
          return retorno;
        case 401:
          retorno = 301;
          return retorno;
        case 402:
          retorno = 302;
          return retorno;
        case 403:
          retorno = 303;
          return retorno;
        case 901:
          retorno = 305;
          return retorno;
        case 999:
          retorno = 306;
          return retorno;
      } 
      retorno = 0;
    } else if (i_cst == 9) {
      switch (i_tiponaturezareceita) {
        case 101:
          retorno = 308;
          return retorno;
        case 102:
          retorno = 309;
          return retorno;
        case 103:
          retorno = 310;
          return retorno;
        case 104:
          retorno = 311;
          return retorno;
        case 105:
          retorno = 312;
          return retorno;
        case 201:
          retorno = 314;
          return retorno;
        case 202:
          retorno = 315;
          return retorno;
        case 203:
          retorno = 316;
          return retorno;
        case 204:
          retorno = 317;
          return retorno;
        case 205:
          retorno = 318;
          return retorno;
        case 206:
          retorno = 319;
          return retorno;
        case 207:
          retorno = 320;
          return retorno;
        case 208:
          retorno = 321;
          return retorno;
        case 209:
          retorno = 322;
          return retorno;
        case 212:
          retorno = 677;
          return retorno;
        case 301:
          retorno = 323;
          return retorno;
        case 302:
          retorno = 324;
          return retorno;
        case 303:
          retorno = 325;
          return retorno;
        case 304:
          retorno = 326;
          return retorno;
        case 305:
          retorno = 327;
          return retorno;
        case 306:
          retorno = 328;
          return retorno;
        case 307:
          retorno = 329;
          return retorno;
        case 308:
          retorno = 330;
          return retorno;
        case 309:
          retorno = 331;
          return retorno;
        case 310:
          retorno = 332;
          return retorno;
        case 311:
          retorno = 333;
          return retorno;
        case 312:
          retorno = 312;
          return retorno;
        case 313:
          retorno = 659;
          return retorno;
        case 401:
          retorno = 334;
          return retorno;
        case 402:
          retorno = 335;
          return retorno;
        case 403:
          retorno = 336;
          return retorno;
        case 404:
          retorno = 337;
          return retorno;
        case 405:
          retorno = 338;
          return retorno;
        case 406:
          retorno = 339;
          return retorno;
        case 407:
          retorno = 340;
          return retorno;
        case 408:
          retorno = 341;
          return retorno;
        case 901:
          retorno = 342;
          return retorno;
        case 999:
          retorno = 343;
          return retorno;
      } 
      retorno = 0;
    } 
    return retorno;
  }
}
