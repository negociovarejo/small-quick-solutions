package vrintegracao.dao.interfaces;

import java.sql.ResultSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import vrframework.classe.DataHora;
import vrframework.classe.Format;
import vrframework.classe.Numero;
import vrframework.classe.ProgressBar;
import vrframework.classe.Texto;
import vrframework.classe.VRInstance;
import vrframework.classe.VRStatement;
import vrframework.remote.Arquivo;
import vrintegracao.classe.Global;
import vrintegracao.dao.LogTransacaoDAO;
import vrintegracao.dao.ParametroDAO;
import vrintegracao.dao.cadastro.FornecedorDAO;
import vrintegracao.dao.cadastro.LojaDAO;
import vrintegracao.dao.cadastro.TipoPisCofinsDAO;
import vrintegracao.dao.contabilidade.ParametroContabilidadeDAO;
import vrintegracao.vo.Formulario;
import vrintegracao.vo.TipoCartaoTef;
import vrintegracao.vo.TipoCrt;
import vrintegracao.vo.TipoData;
import vrintegracao.vo.TipoEntradaSaida;
import vrintegracao.vo.TipoFreteNotaFiscal;
import vrintegracao.vo.TipoImpostoDespesa;
import vrintegracao.vo.TipoIncidencia;
import vrintegracao.vo.TipoSimNao;
import vrintegracao.vo.TipoTransacao;
import vrintegracao.vo.cadastro.FornecedorVO;
import vrintegracao.vo.cadastro.LojaVO;
import vrintegracao.vo.cadastro.SituacaoCadastro;
import vrintegracao.vo.cadastro.SituacaoTributaria;
import vrintegracao.vo.cadastro.TipoInscricao;
import vrintegracao.vo.cadastro.TipoOrigemMercadoria;
import vrintegracao.vo.fiscal.SituacaoNfe;
import vrintegracao.vo.fiscal.TipoEstado;
import vrintegracao.vo.fiscal.TipoNota;
import vrintegracao.vo.interfaces.ModeloNotaFiscal;
import vrintegracao.vo.interfaces.TipoEmpresa;
import vrintegracao.vo.interfaces.fortes.ExportarFortesVO;
import vrintegracao.vo.interfaces.fortes.FortesCABVO;
import vrintegracao.vo.interfaces.fortes.FortesCCFVO;
import vrintegracao.vo.interfaces.fortes.FortesCFCVO;
import vrintegracao.vo.interfaces.fortes.FortesCFIVO;
import vrintegracao.vo.interfaces.fortes.FortesCPEVO;
import vrintegracao.vo.interfaces.fortes.FortesCTCVO;
import vrintegracao.vo.interfaces.fortes.FortesConfiguracaoLojaVO;
import vrintegracao.vo.interfaces.fortes.FortesConfiguracaoVO;
import vrintegracao.vo.interfaces.fortes.FortesECPVO;
import vrintegracao.vo.interfaces.fortes.FortesESIVO;
import vrintegracao.vo.interfaces.fortes.FortesGRPVO;
import vrintegracao.vo.interfaces.fortes.FortesICFVO;
import vrintegracao.vo.interfaces.fortes.FortesIESVO;
import vrintegracao.vo.interfaces.fortes.FortesIIVVO;
import vrintegracao.vo.interfaces.fortes.FortesINMVO;
import vrintegracao.vo.interfaces.fortes.FortesIVCVO;
import vrintegracao.vo.interfaces.fortes.FortesNFMVO;
import vrintegracao.vo.interfaces.fortes.FortesNSCVO;
import vrintegracao.vo.interfaces.fortes.FortesNOPVO;
import vrintegracao.vo.interfaces.fortes.FortesNVCVO;
import vrintegracao.vo.interfaces.fortes.FortesOCCVO;
import vrintegracao.vo.interfaces.fortes.FortesOUMVO;
import vrintegracao.vo.interfaces.fortes.FortesOVEVO;
import vrintegracao.vo.interfaces.fortes.FortesOVFVO;
import vrintegracao.vo.interfaces.fortes.FortesOVNVO;
import vrintegracao.vo.interfaces.fortes.FortesPARVO;
import vrintegracao.vo.interfaces.fortes.FortesPCCVO;
import vrintegracao.vo.interfaces.fortes.FortesPCEVO;
import vrintegracao.vo.interfaces.fortes.FortesPNCVO;
import vrintegracao.vo.interfaces.fortes.FortesPNMVO;
import vrintegracao.vo.interfaces.fortes.FortesPROVO;
import vrintegracao.vo.interfaces.fortes.FortesSNMVO;
import vrintegracao.vo.interfaces.fortes.FortesTRAVO;
import vrintegracao.vo.interfaces.fortes.FortesUNDVO;

import static fortesplus.Functions.MasterVersionComparedWith;
import static fortesplus.Functions.FormatDecimal2R;
import static fortesplus.Functions.FormatDecimal3R;
import static fortesplus.Functions.ArrayUtilsContains;

public class Fortes161DAO {
  private final FortesDAO oFortesDAO = (FortesDAO)VRInstance.criar(FortesDAO.class);

  private final ParametroContabilidadeDAO oParametroContabilidadeDAO = (ParametroContabilidadeDAO)VRInstance.criar(ParametroContabilidadeDAO.class);
  public FortesCABVO oCAB = null;

  public void exportar(ExportarFortesVO i_exportacao, List<Integer> i_loja, int i_idFormulario, int i_idTipoOperacao) throws Exception
  {
    ProgressBar.setStatus("Exportando Cabecalho");
    for (Integer idLoja : i_loja) {
      Arquivo arquivo = new Arquivo(i_exportacao.caminho + "Fortes_loja" + idLoja + ".fs", "w", "windows-1252");
      i_exportacao.qtdRegistro = 0;
      this.oCAB = new FortesCABVO();
      this.oCAB.campo1 = "CAB";
      this.oCAB.campo2 = "161";
      this.oCAB.campo3 = "VR SOFTWARE";
      this.oCAB.campo4 = Format.data(DataHora.getDataAtual(), "dd/MM/yyyy", "yyyyMMdd");
      this.oCAB.campo5 = this.oFortesDAO.getCodigoEmpresa(idLoja.intValue());
      this.oCAB.campo6 = Format.data(i_exportacao.dataInicio, "dd/MM/yyyy", "yyyyMMdd");
      this.oCAB.campo7 = Format.data(i_exportacao.dataTermino, "dd/MM/yyyy", "yyyyMMdd");
      this.oCAB.campo8 = i_exportacao.dataInicio + " A " + i_exportacao.dataTermino;
      this.oCAB.campo9 = "N";
      i_exportacao.qtdRegistro++;
      arquivo.write(this.oCAB.getString());
      i_exportacao.idLoja = idLoja.intValue();

      updateEscritaModelo();

      if (i_exportacao.participantes)
        exportarParticipantes(i_exportacao, arquivo); 
      if (i_exportacao.produto) {
        exportarGrupoProduto(i_exportacao, arquivo);
        exportarUnidadeMedida(i_exportacao, arquivo);
        exportarNaturezaOperacao(i_exportacao, arquivo);
        exportarProduto(i_exportacao, arquivo);
      } 
      if (i_exportacao.notaServico) {
        exportarNotaServico(i_exportacao, arquivo);
      }

      if (i_exportacao.notaEntrada)
        exportarNotaFiscalEntrada(i_exportacao, arquivo); 
      if (i_exportacao.notaSaida)
        exportarNotaFiscalSaida(i_exportacao, arquivo);
      if (i_exportacao.cupomFiscalEletronico)
        exportarCupomFiscalEletronico(i_exportacao, arquivo); 
      if (i_exportacao.conhecimentoTransporteCarga)
        exportarConhecimentoTransporteCarga(i_exportacao, arquivo); 
      if (i_exportacao.inventario)
        exportarInventario(i_exportacao, arquivo); 
      if (i_exportacao.operacaoCreditoDebito)
        exportarOperacoesCartaoCredito(i_exportacao, arquivo); 
      if (i_exportacao.estoqueEscriturado)
        exportarEstoqueEscriturado(i_exportacao, arquivo, i_idFormulario, i_idTipoOperacao); 
      
      i_exportacao.qtdRegistro++;
      FortesTRAVO oTRA = new FortesTRAVO();
      oTRA.campo1 = "TRA";
      oTRA.campo2 = Format.number(i_exportacao.qtdRegistro, 10);
      arquivo.write(oTRA.getString());
      i_exportacao.qtdRegistro++;
      arquivo.close();
      ((LogTransacaoDAO) VRInstance.criar(LogTransacaoDAO.class)).gerar(Formulario.INTERFACE_EXPORTACAO_FORTES, TipoTransacao.EXPORTACAO, 0L, "", idLoja.intValue());
    } 
  }

  private void updateEscritaModelo() throws Exception
  {
    VRStatement stm = VRStatement.createStatement();
    StringBuilder sql = null;
    sql = new StringBuilder();
    sql.append("update escrita set modelo = '59' where id_estado = 23 and modelo = '2D'");
    stm.execute(sql.toString());
    sql = new StringBuilder();
    sql.append("update escrita set modelo = '65' where (id_estado = 18 or id_estado = 19 or id_estado = 24) and modelo = '2D'");
    stm.execute(sql.toString());
  }
  
  private void exportarParticipantes(ExportarFortesVO i_exportacao, Arquivo i_arquivo) throws Exception {
    ResultSet rst = null;
    VRStatement stm = null;
    StringBuilder sql = null;
    stm = VRStatement.createStatement();
    ProgressBar.setStatus("Exportando Participantes...");
    sql = new StringBuilder();
    sql.append("WITH");
    sql.append("  tiporecebivelfornecedor AS (");
    sql.append("    SELECT DISTINCT ");
    sql.append("      id_fornecedor, true AS administrador");
    sql.append("    FROM tiporecebivel");
    sql.append("  ) ");
    sql.append("SELECT");
    sql.append("  f.id, f.razaosocial, f.id_tipoinscricao, e.sigla AS uf, ");
    sql.append("  f.cnpj, f.inscricaoestadual, f.id_tipoindicadorie, ");
    sql.append("  f.inscricaomunicipal, f.endereco, f.numero, ");
    sql.append("  COALESCE(f.complemento,'') AS complemento, ");
    sql.append("  f.bairro, f.cep, f.id_municipio, f.telefone, f.inscricaosuframa, ");
    sql.append("  f.id_pais, 0 AS orgaopublico, t.administrador, f.id_tipofornecedor ");
    sql.append("FROM fornecedor AS f");
    sql.append("  INNER JOIN estado AS e ON e.id = f.id_estado");
    sql.append("  LEFT JOIN tiporecebivelfornecedor t ON t.id_fornecedor = f.id ");
    sql.append("WHERE");
    sql.append("  f.id IN (");
    sql.append("    SELECT e.id_fornecedor");
    sql.append("    FROM escrita AS e");
    sql.append("    WHERE");
    
    if (i_exportacao.tipoData == TipoData.ENTRADA.getId()) {
      sql.append("    e.data BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    } else if (i_exportacao.tipoData == TipoData.EMISSAO.getId()) {
      sql.append("    e.dataemissao BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    }

    sql.append("      AND e.cupomfiscal = FALSE");
    sql.append("      AND e.id_loja = " + i_exportacao.idLoja);
    sql.append("    UNION");
    sql.append("      SELECT tr.id_fornecedor");
    sql.append("      FROM caixaitem AS ci");
    sql.append("        INNER JOIN caixa AS c ON c.id = ci.id_caixa");
    sql.append("        INNER JOIN tiporecebivel AS tr ON tr.id = ci.id_tiporecebivel");
    sql.append("      WHERE");
    sql.append("        tr.id_tipotef IS NOT NULL");
    sql.append("        AND tr.id_fornecedor IS NOT NULL");
    sql.append("        AND c.id_loja = " + i_exportacao.idLoja);
    sql.append("        AND c.data BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    sql.append("      GROUP BY tr.id_fornecedor");
    sql.append("      HAVING SUM(ci.valor) > 0");
    sql.append("  )");

    sql.append("  OR f.id IN (");
    sql.append("    SELECT e.id_fornecedorprodutorrural");
    sql.append("    FROM escrita AS e");
    sql.append("    WHERE");
    
    if (i_exportacao.tipoData == TipoData.ENTRADA.getId()) {
      sql.append("    e.data BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    } else if (i_exportacao.tipoData == TipoData.EMISSAO.getId()) {
      sql.append("    e.dataemissao BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    }

    sql.append("      AND e.cupomfiscal = FALSE");
    sql.append("      AND e.id_loja = " + i_exportacao.idLoja);
    sql.append("    UNION");
    sql.append("      SELECT tr.id_fornecedor");
    sql.append("      FROM caixaitem AS ci");
    sql.append("        INNER JOIN caixa AS c ON c.id = ci.id_caixa");
    sql.append("        INNER JOIN tiporecebivel AS tr ON tr.id = ci.id_tiporecebivel");
    sql.append("      WHERE");
    sql.append("        tr.id_tipotef IS NOT NULL");
    sql.append("        AND tr.id_fornecedor IS NOT NULL");
    sql.append("        AND c.id_loja = " + i_exportacao.idLoja);
    sql.append("        AND c.data BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    sql.append("      GROUP BY tr.id_fornecedor");
    sql.append("      HAVING SUM(ci.valor) > 0");
    sql.append("  )");

    sql.append("UNION ALL");
    sql.append("  SELECT");
    sql.append("    ce.id, ce.nome AS razaosocial, ce.id_tipoinscricao, e.sigla AS uf, ce.cnpj,");
    sql.append("    ce.inscricaoestadual, ce.id_tipoindicadorie, ce.inscricaomunicipal, ce.endereco,");
    sql.append("    ce.numero, COALESCE(ce.complemento,'') AS complemento,");
    sql.append("    ce.bairro, ce.cep, ce.id_municipio, ce.telefone, '' AS inscricaosuframa, ce.id_pais, ");
    sql.append("    ce.id_tipoorgaopublico AS orgaopublico, false, 0");
    sql.append("  FROM clienteeventual AS ce");
    sql.append("    INNER JOIN estado AS e ON e.id = ce.id_estado");
    sql.append("  WHERE");
    sql.append("    ce.id IN (");
    sql.append("      SELECT id_clienteeventual");
    sql.append("      FROM escrita AS e");
    sql.append("      WHERE");
    sql.append("        e.id_clienteeventual IS NOT NULL");
    if (i_exportacao.tipoData == TipoData.ENTRADA.getId()) {
      sql.append("      AND e.data BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    } else if (i_exportacao.tipoData == TipoData.EMISSAO.getId()) {
      sql.append("      AND e.dataemissao BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    } 
    sql.append("        AND e.id_loja = " + i_exportacao.idLoja);
    sql.append("    )");

    rst = stm.executeQuery(sql.toString());
    rst.last();
    ProgressBar.setMaximum(rst.getRow());
    rst.beforeFirst();
    while (rst.next()) {
      FortesPARVO oPAR = new FortesPARVO();
      oPAR.campo1 = "PAR";
      oPAR.campo2 = Format.number(rst.getString("id"), 9);
      oPAR.campo3 = (rst.getString("razaosocial").length() > 100) ? Format.string(rst.getString("razaosocial"), 100) : rst.getString("razaosocial");
      oPAR.campo4 = rst.getString("uf");
      if (rst.getInt("id_tipoinscricao") == TipoInscricao.FISICA.getId()) {
        oPAR.campo5 = Format.number(rst.getLong("cnpj"), 11);
      } else {
        oPAR.campo5 = Format.number(rst.getLong("cnpj"), 14);
      }
      
      String ie = rst.getString("inscricaoestadual").replace(".", "").replace("-", "").trim();

      if (
        Numero.is(ie) && Double.parseDouble(ie) > 0.0D &&
        rst.getInt("id_tipoindicadorie") == 1
      ) {
        oPAR.campo6 = Format.number(Texto.substring(ie, 0, 14), 9); 
      } else {
        oPAR.campo6 = "ISENTO";
      }

      oPAR.campo7 = rst.getString("inscricaomunicipal");
      oPAR.campo8 = "N";
      oPAR.campo9 = "N";
      oPAR.campo10 = "N";
      oPAR.campo11 = "N";
      if (rst.getInt("orgaopublico") == 0) {
        oPAR.campo12 = "N";
      } else {
        oPAR.campo12 = "S";
      } 
      oPAR.campo13 = "N";
      oPAR.campo14 = "N";
      oPAR.campo15 = "N";
      oPAR.campo16 = "";
      oPAR.campo17 = Texto.substring(rst.getString("endereco"), 0, 60);
      String number = rst.getString("numero");
      oPAR.campo18 = Texto.substring(number.chars().allMatch(Character::isDigit) ? number : "0", 0, 6);
      oPAR.campo19 = Texto.substring(rst.getString("complemento"), 0, 20);
      oPAR.campo20 = "";
      oPAR.campo21 = Texto.substring(rst.getString("bairro"), 0, 50);
      oPAR.campo22 = Format.number(rst.getLong("cep"), 8);
      oPAR.campo23 = rst.getString("id_municipio").substring(2, rst.getString("id_municipio").length());
      oPAR.campo24 = Format.number(rst.getString("telefone").replace("(", "").replace(")", "").replace(".", "").replace(" ", "").replace("_", ""), 10).substring(0, 2);
      oPAR.campo25 = Texto.substring(rst.getString("telefone").replace("(", "").replace(")", "").replace(".", "").replace(" ", "").replace("_", ""), 2, 11);
      oPAR.campo26 = Texto.substring(rst.getString("inscricaosuframa"), 0, 14);
      oPAR.campo27 = "N";
      oPAR.campo28 = "";
      oPAR.campo29 = "";
      oPAR.campo30 = rst.getString("id_pais");
      oPAR.campo31 = "N";
      oPAR.campo32 = "1";
      oPAR.campo33 = "";
      oPAR.campo34 = "N";
      oPAR.campo35 = Format.number(rst.getBoolean("administrador") ? "S" : "N", 1);
      oPAR.campo36 = "";
      oPAR.campo37 = "N";

      int tipoFornecedor = rst.getInt("id_tipofornecedor");

      switch (tipoFornecedor) {
        case 8:
        case 9:
        case 10:
          oPAR.campo38 = "1";
          break;
        case 11:
          oPAR.campo38 = "2";
          break;
        default:
          oPAR.campo38 = "0";
      }

      i_exportacao.qtdRegistro++;
      i_arquivo.write(oPAR.getStringLayout158());
      ProgressBar.next();
    } 
    stm.close();
  }
  
  private void exportarGrupoProduto(ExportarFortesVO i_exportacao, Arquivo i_arquivo) throws Exception {
    ResultSet rst = null;
    VRStatement stm = null;
    StringBuilder sql = null;
    stm = VRStatement.createStatement();
    ProgressBar.setStatus("Exportando Grupo de Produtos...");
    sql = new StringBuilder();
    sql.append("SELECT tp.id + 1 AS id, tp.descricao");
    sql.append(" FROM tipoproduto AS tp");
    sql.append(" WHERE tp.id + 1 <= 10");
    sql.append(" ORDER BY id");
    rst = stm.executeQuery(sql.toString());
    rst.last();
    ProgressBar.setMaximum(rst.getRow());
    rst.beforeFirst();
    while (rst.next()) {
      FortesGRPVO oGRP = new FortesGRPVO();
      oGRP.campo1 = "GRP";
      oGRP.campo2 = Format.number(rst.getInt("id"), 8);
      oGRP.campo3 = rst.getString("descricao");
      oGRP.campo4 = "";
      oGRP.campo5 = Format.number(rst.getInt("id"), 2);
      oGRP.campo6 = "";
      i_exportacao.qtdRegistro++;
      i_arquivo.write(oGRP.getString());
      ProgressBar.next();
    } 
    stm.close();
  }
  
  private void exportarUnidadeMedida(ExportarFortesVO i_exportacao, Arquivo i_arquivo) throws Exception {
    ResultSet rst = null;
    VRStatement stm = null;
    StringBuilder sql = null;
    stm = VRStatement.createStatement();
    ProgressBar.setStatus("Exportando Unidades de Medida...");
    sql = new StringBuilder();
    sql.append("SELECT DISTINCT te.descricao AS tipoembalagem");
    sql.append(" FROM tipoembalagem AS te");
    rst = stm.executeQuery(sql.toString());
    rst.last();
    ProgressBar.setMaximum(rst.getRow());
    rst.beforeFirst();
    while (rst.next()) {
      FortesUNDVO oUND = new FortesUNDVO();
      oUND.campo1 = "UND";
      oUND.campo2 = rst.getString("tipoembalagem");
      oUND.campo3 = rst.getString("tipoembalagem");
      i_exportacao.qtdRegistro++;
      i_arquivo.write(oUND.getString());
      ProgressBar.next();
    } 
    stm.close();
  }
  
  private void exportarNaturezaOperacao(ExportarFortesVO i_exportacao, Arquivo i_arquivo) throws Exception {
    ResultSet rst = null;
    VRStatement stm = null;
    StringBuilder sql = null;
    stm = VRStatement.createStatement();
    ProgressBar.setStatus("Exportando Natureza de Operacao");
    sql = new StringBuilder();
    sql.append("SELECT c.cfop, c.descricao");
    sql.append(" FROM cfop AS c");
    sql.append(" WHERE c.cfop IN (");
    sql.append(" SELECT DISTINCT cfop");
    sql.append(" FROM escrita AS e");
    sql.append(" INNER JOIN escritaitem AS ei ON e.id = ei.id_escrita");
    sql.append(" WHERE e.id_loja =  " + i_exportacao.idLoja);
    if (i_exportacao.tipoData == TipoData.ENTRADA.getId()) {
      sql.append(" AND e.data BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    } else if (i_exportacao.tipoData == TipoData.EMISSAO.getId()) {
      sql.append(" AND e.dataemissao BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    } 
    sql.append(") ORDER BY c.cfop");
    rst = stm.executeQuery(sql.toString());
    rst.last();
    ProgressBar.setMaximum(rst.getRow());
    rst.beforeFirst();
    while (rst.next()) {
      FortesNOPVO oUND = new FortesNOPVO();
      oUND.campo1 = "NOP";
      oUND.campo2 = Format.number(rst.getString("cfop").replace(".", ""), 8);
      oUND.campo3 = Format.string(rst.getString("descricao"), 60);
      oUND.campo4 = "N";
      i_exportacao.qtdRegistro++;
      i_arquivo.write(oUND.getString());
      ProgressBar.next();
    } 
    stm.close();
  }
  
  private void exportarProduto(ExportarFortesVO i_exportacao, Arquivo i_arquivo) throws Exception {
    ResultSet rst = null;
    ResultSet rstAutomacao = null;
    VRStatement stm = null;
    VRStatement stmAutomacao = null;
    StringBuilder sql = null;
    stm = VRStatement.createStatement();
    stmAutomacao = VRStatement.createStatement();
    FornecedorVO oFornecedor = ((FornecedorDAO)VRInstance.criar(FornecedorDAO.class)).carregar((((LojaDAO)VRInstance.criar(LojaDAO.class)).carregar(i_exportacao.idLoja)).idFornecedor);
    ProgressBar.setStatus("Exportando Produtos...");
    sql = new StringBuilder();
    sql.append("SELECT p.id, p.descricaocompleta, p.ncm1, p.ncm2, p.ncm3, te.descricao as tipoembalagem,");
    sql.append(" COALESCE((SELECT codigobarras FROM produtoautomacao WHERE id_produto = p.id AND LENGTH(codigobarras::varchar) <= 13 LIMIT 1), 0) AS codigobarras,");
    sql.append(" ad.situacaotributaria AS csticms, ad.reduzido, tpc.cst AS cstpiscofins,ad.csosn, pc.id_situacaocadastro, (pc.id_tipoproduto + 1) AS id_tipoproduto , (ac.porcentagemfinal + COALESCE(ac.porcentagemfcp, 0)) AS porcentagemfinal,");
    sql.append(" c.cest1, c.cest2, c.cest3, p.id_tipoorigemmercadoriaentrada, pc.id_loja,");
    sql.append(" (SELECT codigoincidencia FROM fortes.parametro WHERE id_loja = pc.id_loja) AS codigoincidencia ");
    sql.append(" FROM  produto AS p");
    sql.append(" INNER JOIN tipoembalagem AS te ON te.id = p.id_tipoembalagem");
    sql.append(" INNER JOIN produtoaliquota AS pa ON pa.id_produto = p.id AND pa.id_estado = " + oFornecedor.idEstado);
    sql.append(" INNER JOIN aliquota AS ad ON ad.id = pa.id_aliquotadebito");
    sql.append(" LEFT JOIN aliquota AS ac ON ac.id = pa.id_aliquotacredito");
    sql.append(" INNER JOIN tipopiscofins AS tpc ON tpc.id = p.id_tipopiscofinscredito");
    sql.append(" INNER JOIN produtocomplemento AS pc ON pc.id_produto = p.id AND pc.id_loja = " + i_exportacao.idLoja);
    sql.append(" INNER JOIN (");
    sql.append(" SELECT DISTINCT id_produto");
    sql.append(" FROM escrita ");
    sql.append(" INNER JOIN escritaitem AS item ON item.id_escrita = escrita.id");
    if (i_exportacao.tipoData == TipoData.ENTRADA.getId()) {
      sql.append(" WHERE escrita.data BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    } else if (i_exportacao.tipoData == TipoData.EMISSAO.getId()) {
      sql.append(" WHERE escrita.dataemissao BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    } 
    sql.append(" AND escrita.id_loja = " + i_exportacao.idLoja);
    if (i_exportacao.inventario) {
      sql.append(" UNION ");
      sql.append(" SELECT DISTINCT i.id_produto");
      sql.append(" FROM inventario AS i");
      sql.append(" INNER JOIN produto AS p ON p.id = i.id_produto");
      sql.append(" INNER JOIN produtocomplemento AS pc ON pc.id_produto = p.id AND pc.id_loja = " + i_exportacao.idLoja);
      sql.append(" WHERE data BETWEEN '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
      sql.append(" AND i.id_loja = " + i_exportacao.idLoja);
      sql.append(" AND i.quantidade > 0");
      sql.append(" AND pc.id_tipoproduto in (SELECT id_tipoproduto FROM fortes.inventarioparametro)");
    } 
    sql.append(" ) AS tabela ON tabela.id_produto = p.id");
    sql.append(" LEFT JOIN cest AS c ON c.id = p.id_cest");
    sql.append(" WHERE p.id IN (SELECT DISTINCT id_produto FROM produtoautomacao)");
    sql.append(" ORDER BY p.id");
    rst = stm.executeQuery(sql.toString());
    rst.last();
    ProgressBar.setMaximum(rst.getRow());
    rst.beforeFirst();
    while (rst.next()) {
      FortesPROVO oPRO = new FortesPROVO();
      oPRO.campo1 = "PRO";
      oPRO.campo2 = rst.getString("id");
      oPRO.campo3 = (rst.getString("descricaocompleta").length() > 60) ? Format.string(rst.getString("descricaocompleta"), 60) : rst.getString("descricaocompleta");
      oPRO.campo4 = String.valueOf(rst.getInt("id"));
      oPRO.campo5 = Format.number(rst.getInt("ncm1"), 4) + Format.number(rst.getInt("ncm2"), 2) + Format.number(rst.getInt("ncm3"), 2);
      oPRO.campo6 = rst.getString("tipoembalagem");
      oPRO.campo7 = "";
      oPRO.campo8 = "";
      oPRO.campo9 = "";
      if (rst.getInt("id_tipoproduto") <= 10) {
        oPRO.campo10 = Format.number(rst.getInt("id_tipoproduto"), 3);
      } else {
        oPRO.campo10 = "";
      } 
      oPRO.campo11 = "";
      oPRO.campo12 = rst.getString("codigobarras");
      oPRO.campo13 = FormatDecimal2R(rst.getDouble("reduzido"));
      oPRO.campo14 = "";
      oPRO.campo15 = Format.number(rst.getInt("csticms"), 3);
      oPRO.campo16 = "";
      oPRO.campo17 = Format.number(rst.getInt("cstpiscofins"), 2);
      oPRO.campo18 = Format.number(rst.getInt("cstpiscofins"), 2);
      oPRO.campo19 = "";
      oPRO.campo20 = "";
      oPRO.campo21 = "";
      oPRO.campo22 = "";
      oPRO.campo23 = "";
      oPRO.campo24 = (rst.getInt("id_situacaocadastro") == SituacaoCadastro.ATIVO.getId()) ? "N" : "S";
      oPRO.campo25 = "";
      oPRO.campo26 = "";
      oPRO.campo27 = "";
      oPRO.campo28 = "01";
      oPRO.campo29 = "";
      oPRO.campo30 = "";
      oPRO.campo31 = "";
      oPRO.campo32 = "";
      oPRO.campo33 = "";
      oPRO.campo34 = "";
      oPRO.campo35 = "4";
      oPRO.campo36 = "6";
      oPRO.campo37 = FormatDecimal2R(rst.getDouble("porcentagemfinal"));
      oPRO.campo38 = "N";
      oPRO.campo39 = "";
      oPRO.campo40 = "";
      String cest = Format.number(rst.getInt("cest1"), 2) + Format.number(rst.getInt("cest2"), 3) + Format.number(rst.getInt("cest3"), 2);
      if (cest.equals("0000000")) {
        oPRO.campo41 = "";
      } else {
        oPRO.campo41 = cest;
      } 
      oPRO.campo42 = "";
      oPRO.campo43 = (rst.getInt("csticms") == 60) ? "S" : "N";
      oPRO.campo44 = (rst.getInt("id_tipoorigemmercadoriaentrada") == TipoOrigemMercadoria.ESTRANGEIRA_IMPORTACAO_DIRETA.getId()) ? "S" : "N";
      oPRO.campo45 = (rst.getInt("cstpiscofins") == 6) ? "S" : "N";
      oPRO.campo46 = (rst.getInt("cstpiscofins") == 6) ? "S" : "N";
      oPRO.campo47 = (rst.getInt("cstpiscofins") == 4) ? "S" : "N";
      oPRO.campo48 = (rst.getInt("cstpiscofins") == 4) ? "S" : "N";
      if (rst.getInt("codigoincidencia") == TipoIncidencia.NAO_CUMULATIVO.getId()) {
        oPRO.campo49 = "1";
      } else if (rst.getInt("codigoincidencia") == TipoIncidencia.CUMULATIVO.getId()) {
        oPRO.campo49 = "2";
      } else {
        oPRO.campo49 = "";
      } 
      i_exportacao.qtdRegistro++;
      i_arquivo.write(oPRO.getStringLayout140());
      sql = new StringBuilder();
      sql.append("SELECT pa.codigobarras, pa.qtdembalagem, te.descricao AS tipoembalagem");
      sql.append(" FROM produtoautomacao AS pa");
      sql.append(" INNER JOIN tipoembalagem AS te ON te.id = pa.id_tipoembalagem");
      sql.append(" WHERE pa.id_produto = " + rst.getInt("id"));
      rstAutomacao = stmAutomacao.executeQuery(sql.toString());
      while (rstAutomacao.next()) {
        FortesOUMVO oOUM = new FortesOUMVO();
        oOUM.campo1 = "OUM";
        oOUM.campo2 = rst.getString("id");
        oOUM.campo3 = rstAutomacao.getString("tipoembalagem");
        oOUM.campo4 = "1.000";
        oOUM.campo5 = rstAutomacao.getString("codigobarras");
        i_exportacao.qtdRegistro++;
        i_arquivo.write(oOUM.getString());
      } 
      ProgressBar.next();
    } 
    stm.close();
  }
  
  private void exportarNotaServico(ExportarFortesVO i_exportacao, Arquivo i_arquivo) throws Exception {
    ResultSet rst = null;
    VRStatement stm = null;
    StringBuilder sql = null;
    stm = VRStatement.createStatement();
    ProgressBar.setStatus("Exportando Nota Servico");
    sql = new StringBuilder();
    sql.append("SELECT e.id_notadespesa, e.id_fornecedor, e.dataemissao, e.numeronota, SUM(ei.valorbasecalculo + ei.valorisento + ei.valoroutras) AS valorbruto,");
    sql.append(" e.serie, e.chavenfe, e.especie, e.observacao, ei.valordesconto, ei.valorisento,");
    sql.append(" e.id_tipoentrada, te.contabilidadepadrao");
    sql.append(" FROM escrita AS e");
    sql.append(" INNER JOIN escritaitem AS ei ON ei.id_escrita = e.id");
    sql.append(" INNER JOIN cfop AS c ON c.cfop = ei.cfop");
    sql.append(" INNER JOIN fornecedor AS f ON f.id = e.id_fornecedor");
    sql.append(" INNER JOIN tipoentrada AS te ON e.id_tipoentrada = te.id");
    sql.append(" WHERE COALESCE(e.id_notadespesa,0) > 0");
    if (i_exportacao.tipoData == TipoData.ENTRADA.getId()) {
      sql.append(" AND e.data BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    } else if (i_exportacao.tipoData == TipoData.EMISSAO.getId()) {
      sql.append(" AND e.dataemissao BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    } 
    sql.append(" AND c.servico = TRUE");
    sql.append(" AND e.id_loja = " + i_exportacao.idLoja);
    sql.append(" GROUP BY e.id_notadespesa, e.id_fornecedor, e.dataemissao, e.numeronota, e.serie, e.chavenfe, e.especie, e.observacao, ei.valordesconto, ei.valorisento,");
    sql.append(" e.id_tipoentrada, te.contabilidadepadrao");
    rst = stm.executeQuery(sql.toString());
    while (rst.next()) {
      FortesESIVO oESI = new FortesESIVO();
      oESI.campo1 = "ESI";
      oESI.campo2 = Format.number(this.oFortesDAO.getCodigoEstabelecimento(i_exportacao.idLoja), 4);
      oESI.campo3 = Format.number(rst.getInt("id_fornecedor"), 9);
      oESI.campo4 = Format.data(Format.dataGUI(rst.getDate("dataemissao")), "dd/MM/yyyy", "yyyyMMdd");
      oESI.campo5 = "S";
      oESI.campo6 = "";
      oESI.campo7 = "N";
      oESI.campo8 = Format.number(rst.getInt("numeronota"), 15);
      oESI.campo9 = FormatDecimal2R(rst.getDouble("valorbruto"));
      oESI.campo10 = Format.data(Format.dataGUI(rst.getDate("dataemissao")), "dd/MM/yyyy", "yyyyMMdd");
      oESI.campo11 = "";
      oESI.campo12 = FormatDecimal2R(this.oFortesDAO.getValorImpostoNotaDespesa(rst.getInt("id_notadespesa"), TipoImpostoDespesa.CSRF_COFINS.getId()));
      oESI.campo13 = FormatDecimal2R(this.oFortesDAO.getValorImpostoNotaDespesa(rst.getInt("id_notadespesa"), TipoImpostoDespesa.CSRF_PIS.getId()));
      oESI.campo14 = FormatDecimal2R(this.oFortesDAO.getValorImpostoNotaDespesa(rst.getInt("id_notadespesa"), TipoImpostoDespesa.CSRF_CSLL.getId()));
      oESI.campo15 = FormatDecimal2R(this.oFortesDAO.getValorImpostoNotaDespesa(rst.getInt("id_notadespesa"), TipoImpostoDespesa.IRRF.getId()));
      oESI.campo16 = FormatDecimal2R(this.oFortesDAO.getValorImpostoNotaDespesa(rst.getInt("id_notadespesa"), TipoImpostoDespesa.INSS.getId()));
      oESI.campo17 = "N/I";
      oESI.campo18 = "N";
      oESI.campo19 = rst.getString("observacao").replace("\n", " ").replace("|", " ");
      oESI.campo20 = rst.getString("chavenfe");
      oESI.campo21 = FormatDecimal2R(rst.getDouble("valorbruto"));
      oESI.campo22 = FormatDecimal2R(rst.getDouble("valordesconto"));
      oESI.campo23 = "";
      oESI.campo24 = FormatDecimal2R(rst.getDouble("valorbruto") - rst.getDouble("valordesconto"));
      if (rst.getBoolean("contabilidadepadrao")) {
        int idContaContabilDebito = this.oParametroContabilidadeDAO.carregarContaContabilPadrao();
        oESI.campo25 = (idContaContabilDebito == 0) ? "" : Format.number(idContaContabilDebito, 15);
      } else {
        int idContaContabilDebito = this.oParametroContabilidadeDAO.carregarContaContabil(rst.getInt("id_tipoentrada"));
        oESI.campo25 = (idContaContabilDebito == 0) ? "" : Format.number(idContaContabilDebito, 15);
      } 
      
      oESI.campo26 = "";
      oESI.campo27 = "";
      oESI.campo28 = "";

      i_exportacao.qtdRegistro++;
      i_arquivo.write(oESI.getStringLayout141());
      
      FortesIESVO oIES = new FortesIESVO();
      oIES.campo1 = "IES";
      oIES.campo2 = FormatDecimal2R(rst.getDouble("valorbruto"));
      oIES.campo3 = "N";
      oIES.campo4 = "01";
      oIES.campo5 = FormatDecimal2R(rst.getDouble("valorbruto"));
      oIES.campo6 = "0.00";
      oIES.campo7 = "";
      oIES.campo8 = "03";
      oIES.campo9 = "";
      oIES.campo10 = "0.00";
      oIES.campo11 = "";
      oIES.campo12 = FormatDecimal2R(rst.getDouble("valordesconto"));
      oIES.campo13 = FormatDecimal2R(rst.getDouble("valorisento"));
      oIES.campo14 = FormatDecimal2R(rst.getDouble("valorbruto") - rst.getDouble("valordesconto") - rst.getDouble("valorisento"));
      if (rst.getBoolean("contabilidadepadrao")) {
        int idContaContabilDebito = this.oParametroContabilidadeDAO.carregarContaContabilPadrao();
        oIES.campo15 = (idContaContabilDebito == 0) ? "" : Format.number(idContaContabilDebito, 15);
      } else {
        int idContaContabilDebito = this.oParametroContabilidadeDAO.carregarContaContabil(rst.getInt("id_tipoentrada"));
        oIES.campo15 = (idContaContabilDebito == 0) ? "" : Format.number(idContaContabilDebito, 15);
      } 

      oIES.campo16 = "";
      oIES.campo17 = "";
      oIES.campo18 = "";
      oIES.campo19 = "";
      oIES.campo20 = "";
      oIES.campo21 = "";
      oIES.campo22 = "";
      oIES.campo23 = "";
      oIES.campo24 = "";
      oIES.campo25 = "";
      oIES.campo26 = "";
      oIES.campo27 = "";
      oIES.campo28 = "";
      oIES.campo29 = "";
      oIES.campo30 = "";
      oIES.campo31 = "";
      oIES.campo32 = "";
      oIES.campo33 = "";
      oIES.campo34 = "";
      oIES.campo35 = "";

      i_exportacao.qtdRegistro++;
      i_arquivo.write(oIES.getStringLayout158());
      FortesOVEVO oOVE = new FortesOVEVO();
      oOVE.campo1 = "OVE";
      oOVE.campo2 = "DINHEIRO";
      oOVE.campo3 = FormatDecimal2R(rst.getDouble("valorbruto"));
      i_exportacao.qtdRegistro++;
      i_arquivo.write(oOVE.getString());
    } 
    stm.close();
  }
  
  private void exportarComunicacao(ExportarFortesVO i_exportacao, Arquivo i_arquivo) throws Exception {
    VRStatement stm = null;
    ResultSet rst = null;
    StringBuilder sql = null;
    stm = VRStatement.createStatement();
    ProgressBar.setStatus("Exportando Comunicacao...");
    LojaVO lojaVO = ((LojaDAO) VRInstance.criar(LojaDAO.class)).carregar(i_exportacao.idLoja);
    FornecedorVO oFornecedor = ((FornecedorDAO) VRInstance.criar(FornecedorDAO.class)).carregar(lojaVO.idFornecedor);
    sql = new StringBuilder();
    sql.append("SELECT");
    sql.append(" e.id_tipoentradasaida, e.serie, e.numeronota, e.dataemissao,");
    sql.append(" e.data, e.id_fornecedor, ei.cfop, sum(ei.valortotal) as valortotal,");
    sql.append(" e.valorbasecalculo, sum(a.porcentagem) as porcentagem,");
    sql.append(" sum(ei.valoricms) as valoricms, sum(ei.valorisento) as valorisento,");
    sql.append(" sum(ei.valoroutras) as valoroutras, count(ei.id) as qtditem,");
    sql.append(" e.observacao, e.id_indicadorpagamento, ei.situacaotributaria, a.csosn ");
    sql.append("FROM escrita e");
    sql.append(" INNER JOIN escritaitem ei ON ei.id_escrita = e.id");
    sql.append(" INNER JOIN aliquota a ON ei.id_aliquota = a.id ");
    sql.append("WHERE");
    if (i_exportacao.tipoData == TipoData.ENTRADA.getId()) {
      sql.append(" e.data BETWEEN '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    } else if (i_exportacao.tipoData == TipoData.EMISSAO.getId()) {
      sql.append(" e.dataemissao BETWEEN '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    }

    sql.append(" AND e.modelo = '21'");
    sql.append("GROUP BY");
    sql.append(" e.id_tipoentradasaida, e.serie, e.numeronota, e.dataemissao,");
    sql.append(" e.data, e.id_fornecedor, e.valorbasecalculo, e.observacao,");
    sql.append(" e.id_indicadorpagamento, a.csosn, ei.cfop, ei.situacaotributaria");
    
    rst = stm.executeQuery(sql.toString());
    while (rst.next()) {
      FortesNSCVO oNsc = new FortesNSCVO();
      oNsc.campo1 = "NSC";
      oNsc.campo2 = Format.number(this.oFortesDAO.getCodigoEstabelecimento(i_exportacao.idLoja), 4);
      oNsc.campo3 = rst.getString("id_tipoentradasaida");
      oNsc.campo5 = Format.number(rst.getString("serie"), 3);
      oNsc.campo7 = Format.number(rst.getInt("numeronota"), 9);
      oNsc.campo9 = Format.data(Format.dataGUI(rst.getDate("dataemissao")), "dd/MM/yyyy", "yyyyMMdd");
      oNsc.campo11 = Format.data(Format.dataGUI(rst.getDate("data")), "dd/MM/yyyy", "yyyyMMdd");
      oNsc.campo12 = Format.number(rst.getString("id_fornecedor"), 9);
      oNsc.campo13 = rst.getString("cfop").replace(".", "").replace(",", "");
      oNsc.campo14 = FormatDecimal2R(rst.getDouble("valortotal"));
      oNsc.campo15 = FormatDecimal2R(rst.getDouble("valorbasecalculo"));
      oNsc.campo16 = FormatDecimal2R(rst.getDouble("porcentagem"));
      oNsc.campo17 = FormatDecimal2R(rst.getDouble("valoricms"));
      oNsc.campo18 = FormatDecimal2R(rst.getDouble("valorisento"));
      oNsc.campo19 = FormatDecimal2R(rst.getDouble("valoroutras"));
      oNsc.campo21 = Format.number(rst.getInt("qtditem"), 4);
      oNsc.campo35 = rst.getString("observacao");
        
      int paymentIndicator = rst.getInt("id_indicadorpagamento");

      switch (paymentIndicator) {
        case 0:
          oNsc.campo38 = "V";
          break;
        case 1:
          oNsc.campo38 = "P";
          break;
        case 2:
          oNsc.campo38 = "V";
          break;
        case 9:
          oNsc.campo38 = "N";
          break;
        default:
          oNsc.campo38 = "V";
          break;
      } 

      oNsc.campo39 = rst.getString("situacaotributaria");
      
      if (oFornecedor.idTipoEmpresa == TipoEmpresa.EPP_SIMPLES.getId() || oFornecedor.idTipoEmpresa == TipoEmpresa.ME_SIMPLES.getId()) {
        oNsc.campo40 = rst.getString("csosn");
      }
    
      oNsc.campo41 = "";
      oNsc.campo42 = "";

      i_exportacao.qtdRegistro++;
      i_arquivo.write(oNsc.getStringLayout158());
    } 
    stm.close();
  }
  
  private void exportarTelaComunicacao(ExportarFortesVO i_exportacao, Arquivo i_arquivo) throws Exception {
    VRStatement stm = null;
    ResultSet rst = null;
    StringBuilder sql = null;
    stm = VRStatement.createStatement();
    ProgressBar.setStatus("Exportando Tela Comunicacao...");
    LojaVO lojaVO = ((LojaDAO) VRInstance.criar(LojaDAO.class)).carregar(i_exportacao.idLoja);
    FornecedorVO oFornecedor = ((FornecedorDAO) VRInstance.criar(FornecedorDAO.class)).carregar(lojaVO.idFornecedor);
    sql = new StringBuilder();
    sql.append("SELECT");
    sql.append(" e.id_tipoentradasaida, e.serie, e.numeronota, e.dataemissao,");
    sql.append(" e.data, e.id_fornecedor, ei.cfop, sum(ei.valortotal) as valortotal,");
    sql.append(" e.valorbasecalculo, sum(a.porcentagem) as porcentagem,");
    sql.append(" sum(ei.valoricms) as valoricms, sum(ei.valorisento) as valorisento,");
    sql.append(" sum(ei.valoroutras) as valoroutras, count(ei.id) as qtditem,");
    sql.append(" e.observacao, e.id_indicadorpagamento, ei.situacaotributaria, a.csosn ");
    sql.append("FROM escrita e");
    sql.append(" INNER JOIN escritaitem ei ON ei.id_escrita = e.id");
    sql.append(" INNER JOIN aliquota a ON ei.id_aliquota = a.id ");
    sql.append("WHERE");
    if (i_exportacao.tipoData == TipoData.ENTRADA.getId()) {
      sql.append(" e.data BETWEEN '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    } else if (i_exportacao.tipoData == TipoData.EMISSAO.getId()) {
      sql.append(" e.dataemissao BETWEEN '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    }

    sql.append(" AND e.modelo = '21'");
    sql.append("GROUP BY");
    sql.append(" e.id_tipoentradasaida, e.serie, e.numeronota, e.dataemissao,");
    sql.append(" e.data, e.id_fornecedor, e.valorbasecalculo, e.observacao,");
    sql.append(" e.id_indicadorpagamento, a.csosn, ei.cfop, ei.situacaotributaria");
    
    rst = stm.executeQuery(sql.toString());
    while (rst.next()) {
      FortesNSCVO oNsc = new FortesNSCVO();
      oNsc.campo1 = "NSC";
      oNsc.campo2 = Format.number(this.oFortesDAO.getCodigoEstabelecimento(i_exportacao.idLoja), 4);
      oNsc.campo3 = rst.getString("id_tipoentradasaida");
      oNsc.campo5 = Format.number(rst.getString("serie"), 3);
      oNsc.campo7 = Format.number(rst.getInt("numeronota"), 9);
      oNsc.campo9 = Format.data(Format.dataGUI(rst.getDate("dataemissao")), "dd/MM/yyyy", "yyyyMMdd");
      oNsc.campo11 = Format.data(Format.dataGUI(rst.getDate("data")), "dd/MM/yyyy", "yyyyMMdd");
      oNsc.campo12 = Format.number(rst.getString("id_fornecedor"), 9);
      oNsc.campo13 = rst.getString("cfop").replace(".", "").replace(",", "");
      oNsc.campo14 = FormatDecimal2R(rst.getDouble("valortotal"));
      oNsc.campo15 = FormatDecimal2R(rst.getDouble("valorbasecalculo"));
      oNsc.campo16 = FormatDecimal2R(rst.getDouble("porcentagem"));
      oNsc.campo17 = FormatDecimal2R(rst.getDouble("valoricms"));
      oNsc.campo18 = FormatDecimal2R(rst.getDouble("valorisento"));
      oNsc.campo19 = FormatDecimal2R(rst.getDouble("valoroutras"));
      oNsc.campo21 = Format.number(rst.getInt("qtditem"), 4);
      oNsc.campo35 = rst.getString("observacao");
        
      int paymentIndicator = rst.getInt("id_indicadorpagamento");

      switch (paymentIndicator) {
        case 0:
          oNsc.campo38 = "V";
          break;
        case 1:
          oNsc.campo38 = "P";
          break;
        case 2:
          oNsc.campo38 = "V";
          break;
        case 9:
          oNsc.campo38 = "N";
          break;
        default:
          oNsc.campo38 = "V";
          break;
      } 

      oNsc.campo39 = rst.getString("situacaotributaria");
      
      if (oFornecedor.idTipoEmpresa == TipoEmpresa.EPP_SIMPLES.getId() || oFornecedor.idTipoEmpresa == TipoEmpresa.ME_SIMPLES.getId()) {
        oNsc.campo40 = rst.getString("csosn");
      }
    
      oNsc.campo41 = "";
      oNsc.campo42 = "";

      i_exportacao.qtdRegistro++;
      i_arquivo.write(oNsc.getStringLayout158());
    } 
    stm.close();
  }

  private void exportarNotaFiscalEntrada(ExportarFortesVO i_exportacao, Arquivo i_arquivo) throws Exception {
    VRStatement stm = null;
    VRStatement stmProduto = null;
    VRStatement stmImposto = null;
    VRStatement stmST = null;
    ResultSet rst = null;
    ResultSet rstProduto = null;
    ResultSet rstImposto = null;
    ResultSet rstST = null;
    StringBuilder sql = null;
    stm = VRStatement.createStatement();
    stmProduto = VRStatement.createStatement();
    stmImposto = VRStatement.createStatement();
    stmST = VRStatement.createStatement();
    ProgressBar.setStatus("Exportando Nota Fiscal (Entrada)...");
    FornecedorVO oFornecedor = ((FornecedorDAO) VRInstance.criar(FornecedorDAO.class)).carregar((((LojaDAO) VRInstance.criar(LojaDAO.class)).carregar(i_exportacao.idLoja)).idFornecedor);
    sql = new StringBuilder();
    sql.append("SELECT e.id, e.id_fornecedor, e.id_fornecedorprodutorrural, e.dataemissao, e.numeronota, te.id_tipocrt,");
    sql.append("SUM(ei.valorbasecalculo + ei.valorisento + ei.valoroutras - ei.valordesconto + ei.valoracrescimo) AS valortotal, e.serie, e.chavenfe, e.especie, e.data,");
    sql.append("e.valorfrete, e.valoroutrasdespesas, e.modelo, e.valoripi, e.valorbasesubstituicao, (e.valoricmssubstituicao + COALESCE(e.valorfcpst, 0)) AS valoricmssubstituicao, e.valordesconto, COUNT(ei.id) AS qtditem,");
    sql.append("e.id_tipofretenotafiscal, e.observacao, f.id_tipofornecedor, ");
    if (oFornecedor.idTipoEmpresa == TipoEmpresa.LUCRO_PRESUMIDO.getId()) {
      sql.append("SUM(CASE WHEN tpc.valorpispresumido <> 0 THEN ROUND((ei.valorbasecalculo + ei.valorisento + ei.valoroutras + (CASE WHEN e.aplicaicmsipi = FALSE THEN ei.valoripi ELSE 0 END) - ei.valordesconto), 2) ELSE 0 END) AS valorbasepiscofins,");
      sql.append("SUM(ROUND(((ei.valorbasecalculo + ei.valorisento + ei.valoroutras + (CASE WHEN e.aplicaicmsipi = FALSE THEN ei.valoripi ELSE 0 END) - ei.valordesconto) * ((100 - tpc.reduzidocreditopresumido) / 100)) * tpc.valorpispresumido / 100, 2)) AS valorpis,");
      sql.append("SUM(ROUND(((ei.valorbasecalculo + ei.valorisento + ei.valoroutras + (CASE WHEN e.aplicaicmsipi = FALSE THEN ei.valoripi ELSE 0 END) - ei.valordesconto) * ((100 - tpc.reduzidocreditopresumido) / 100)) * tpc.valorcofinspresumido / 100, 2)) AS valorcofins,");
    } else {
      sql.append("SUM(CASE WHEN tpc.valorpis <> 0 THEN ROUND((ei.valorbasecalculo + ei.valorisento + ei.valoroutras + (CASE WHEN e.aplicaicmsipi = FALSE THEN ei.valoripi ELSE 0 END) - ei.valordesconto), 2) ELSE 0 END) AS valorbasepiscofins,");
      sql.append("SUM(ROUND(((ei.valorbasecalculo + ei.valorisento + ei.valoroutras + (CASE WHEN e.aplicaicmsipi = FALSE THEN ei.valoripi ELSE 0 END) - ei.valordesconto) * ((100 - tpc.reduzidocredito) / 100)) * tpc.valorpis / 100, 2)) AS valorpis,");
      sql.append("SUM(ROUND(((ei.valorbasecalculo + ei.valorisento + ei.valoroutras + (CASE WHEN e.aplicaicmsipi = FALSE THEN ei.valoripi ELSE 0 END) - ei.valordesconto) * ((100 - tpc.reduzidocredito) / 100)) * tpc.valorcofins / 100, 2)) AS valorcofins,");
    } 
    sql.append(" f.id_estado AS estadoFornecedor, estabelecimentote.id_tipocrt AS id_tipocrt_loja, e.id_situacaonfe,");
    sql.append(" e.id_tipoentradasaida, e.id_notasaida, SUM(COALESCE(ad.porcentagemfcp, 0) * ei.valorbasesubstituicao) as valorfcpst, e.id_tiponota, f.id_tipoempresa, ");
    sql.append(" e.valortotalbruto, e.valorcontabil, e.valordescontofiscal, e.id_indicadorpagamento");
    sql.append(" FROM escrita AS e");
    sql.append(" INNER JOIN escritaitem AS ei ON ei.id_escrita = e.id");
    sql.append(" INNER JOIN produto p ON p.id = ei.id_produto");
    sql.append(" INNER JOIN produtoaliquota pa ON pa.id_produto = p.id AND pa.id_estado = " + oFornecedor.idEstado);
    sql.append(" INNER JOIN fornecedor AS f ON f.id = e.id_fornecedor");
    sql.append(" INNER JOIN tipoempresa AS te ON te.id = f.id_tipoempresa");
    sql.append(" INNER JOIN tipopiscofins AS tpc ON tpc.id = ei.id_tipopiscofins");
    sql.append(" INNER JOIN loja l ON l.id = e.id_loja");
    sql.append(" INNER JOIN fornecedor AS estabelecimento ON estabelecimento.id = l.id_fornecedor");
    sql.append(" INNER JOIN tipoempresa AS estabelecimentote ON estabelecimentote.id = estabelecimento.id_tipoempresa");
    sql.append(" LEFT JOIN pautafiscal pt ON pt.ncm1 = p.ncm1 AND pt.ncm2 = p.ncm2 AND p.ncm3 = pt.ncm3 AND pt.excecao = pa.excecao AND pt.id_estado = pa.id_estado");
    sql.append(" LEFT JOIN aliquota ad ON ad.id = pt.id_aliquotadebito");
    sql.append(" WHERE COALESCE(e.id_notaentrada,0) > 0");
    if (i_exportacao.tipoData == TipoData.ENTRADA.getId()) {
      sql.append(" AND e.data BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    } else if (i_exportacao.tipoData == TipoData.EMISSAO.getId()) {
      sql.append(" AND e.dataemissao BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    } 
    sql.append(" AND e.id_loja = " + i_exportacao.idLoja);
    sql.append(" GROUP BY e.id, e.modelo, e.id_fornecedor, e.id_fornecedorprodutorrural, e.dataemissao, e.id_situacaonfe, ");
    sql.append(" e.numeronota, te.id_tipocrt, e.serie, e.chavenfe, e.especie, e.data, e.valorfrete,");
    sql.append(" e.valoroutrasdespesas, e.valoripi, e.valorbasesubstituicao, (e.valoricmssubstituicao + COALESCE(e.valorfcpst, 0)), e.valordesconto,");
    sql.append(" e.id_tipofretenotafiscal, e.observacao, f.id_tipofornecedor, e.aplicaicmsipi, f.id_estado, id_tipocrt_loja, e.id_tipoentradasaida, e.id_notasaida, e.id_tiponota,");
    sql.append(" e.valortotalbruto, e.valorcontabil, f.id_tipoempresa, e.valordescontofiscal, e.id_indicadorpagamento");
    sql.append(" ORDER BY e.id");
    rst = stm.executeQuery(sql.toString());
    rst.last();
    ProgressBar.setMaximum(rst.getRow());
    rst.beforeFirst();
    while (rst.next()) {
      FortesNFMVO oNFM = new FortesNFMVO();
      oNFM.campo1 = "NFM";
      oNFM.campo2 = Format.number(this.oFortesDAO.getCodigoEstabelecimento(i_exportacao.idLoja), 4);
      oNFM.campo3 = "E";
      
      if (rst.getString("modelo").equals("01")) {
        oNFM.campo4 = "NF1";
      } else {
        oNFM.campo4 = rst.getString("especie");
      }

      oNFM.campo5 = "N";
      oNFM.campo6 = "";
      oNFM.campo7 = rst.getString("serie");
      oNFM.campo8 = "";
      oNFM.campo9 = Format.number(rst.getInt("numeronota"), rst.getString("modelo").equals("55") ? 9 : 7);
      oNFM.campo10 = "";
      oNFM.campo11 = "";
      oNFM.campo12 = Format.data(Format.dataGUI(rst.getDate("dataemissao")), "dd/MM/yyyy", "yyyyMMdd");
      if (rst.getInt("id_tiponota") == TipoNota.NORMAL.getId()) {
        oNFM.campo13 = "0";
      } else if (rst.getInt("id_situacaonfe") == SituacaoNfe.CANCELADA.getId()) {
        oNFM.campo13 = "1";
      } else if (rst.getInt("id_tiponota") == TipoNota.COMPLEMENTO.getId()) {
        oNFM.campo13 = "6";
      } else if (rst.getInt("id_situacaonfe") == SituacaoNfe.INUTILIZADA.getId() && (rst
        .getInt("id_tipoempresa") == TipoEmpresa.PRODUTOR_RURAL_PESSOA_JURIDICA.getId() || rst.getInt("id_tipoempresa") == TipoEmpresa.PRODUTOR_RURAL_PESSOA_FISICA.getId())) {
        oNFM.campo13 = "2";
      } else {
        oNFM.campo13 = "";
      } 
      oNFM.campo14 = Format.data(Format.dataGUI(rst.getDate("data")), "dd/MM/yyyy", "yyyyMMdd");
      oNFM.campo15 = Format.number(rst.getInt("id_fornecedor"), 9);
      oNFM.campo16 = "";
      oNFM.campo17 = "";
      oNFM.campo18 = "";
      oNFM.campo19 = "";
      oNFM.campo20 = "";
      oNFM.campo21 = "";
      oNFM.campo22 = "";
      oNFM.campo23 = "";
      oNFM.campo24 = "";
      oNFM.campo25 = "";
      
      if (rst.getInt("id_situacaonfe") != SituacaoNfe.CANCELADA.getId()) {
        oNFM.campo26 = FormatDecimal2R(rst.getDouble("valortotalbruto") + rst.getDouble("valordescontofiscal"));
      } else {
        oNFM.campo26 = "";
      }

      oNFM.campo27 = FormatDecimal2R(rst.getDouble("valorfrete"));
      oNFM.campo28 = "";
      oNFM.campo29 = FormatDecimal2R(rst.getDouble("valoroutrasdespesas"));
      oNFM.campo30 = "";
      oNFM.campo31 = "";
      oNFM.campo32 = (rst.getInt("id_situacaonfe") == SituacaoNfe.CANCELADA.getId() || rst.getInt("id_situacaonfe") == SituacaoNfe.INUTILIZADA.getId()) ? "" : FormatDecimal2R(rst.getDouble("valoripi"));
      oNFM.campo33 = FormatDecimal2R(rst.getDouble("valoricmssubstituicao"));
      oNFM.campo34 = "0.00";
      oNFM.campo35 = FormatDecimal2R(rst.getDouble("valordescontofiscal"));
      oNFM.campo36 = (rst.getInt("id_situacaonfe") != SituacaoNfe.CANCELADA.getId()) ? rst.getString("valorcontabil") : "";
      oNFM.campo37 = Format.number(rst.getInt("qtditem"), 10);
      oNFM.campo38 = "";
      oNFM.campo39 = "";
      oNFM.campo40 = "";
      if (rst.getInt("id_situacaonfe") != SituacaoNfe.CANCELADA.getId() && rst.getInt("id_situacaonfe") != SituacaoNfe.INUTILIZADA.getId()) {
        oNFM.campo41 = FormatDecimal2R(rst.getDouble("valoricmssubstituicao"));
        oNFM.campo42 = FormatDecimal2R(rst.getDouble("valorbasesubstituicao"));
        oNFM.campo43 = "0.00";
        oNFM.campo44 = "";
        oNFM.campo45 = "";
        oNFM.campo46 = "";
        oNFM.campo47 = "";
        if (rst.getInt("id_tipofretenotafiscal") == TipoFreteNotaFiscal.DESTINATARIO.getId()) {
          oNFM.campo48 = "D";
        } else if (rst.getInt("id_tipofretenotafiscal") == TipoFreteNotaFiscal.EMITENTE.getId()) {
          oNFM.campo48 = "R";
        } else {
          oNFM.campo48 = "N";
        }

        int paymentIndicator = rst.getInt("id_indicadorpagamento");

        switch (paymentIndicator) {
          case 0:
            oNFM.campo49 = "V";
            break;
          case 1:
            oNFM.campo49 = "P";
            break;
          case 2:
            oNFM.campo49 = "V";
            break;
          case 9:
            oNFM.campo49 = "N";
            break;
        } 
        
        oNFM.campo50 = "";
        oNFM.campo51 = "";
        if (this.oFortesDAO.isFornecedorDistribuidor(i_exportacao.idLoja)) {
          oNFM.campo52 = "";
          oNFM.campo53 = "";
        } else {
          oNFM.campo52 = FormatDecimal2R(rst.getDouble("valorbasepiscofins"));
          oNFM.campo53 = FormatDecimal2R(rst.getDouble("valorbasepiscofins"));
        } 
        oNFM.campo54 = "";
        oNFM.campo55 = "";
        oNFM.campo56 = "";
        oNFM.campo57 = "";
        oNFM.campo58 = "";
        oNFM.campo59 = "";
        oNFM.campo60 = FormatDecimal2R(rst.getDouble("valorcofins"));
        oNFM.campo61 = FormatDecimal2R(rst.getDouble("valorpis"));
        oNFM.campo62 = "0.00";
        oNFM.campo63 = "0.00";
        oNFM.campo64 = "";
        oNFM.campo65 = rst.getString("observacao").replace("\n", " ").replace("|", " ");
        oNFM.campo66 = "";
      } 
      oNFM.campo67 = rst.getString("chavenfe");
      oNFM.campo68 = (rst.getInt("id_situacaonfe") != SituacaoNfe.CANCELADA.getId()) ? "0.00" : "";
      oNFM.campo69 = "";
      oNFM.campo70 = "";
      oNFM.campo71 = "";
      oNFM.campo72 = "";
      oNFM.campo73 = "";
      oNFM.campo74 = "";
      oNFM.campo75 = "";
      oNFM.campo76 = "";
      oNFM.campo77 = "";
      oNFM.campo78 = "";
      oNFM.campo79 = "";
      oNFM.campo80 = "N";
      oNFM.campo81 = "";
      oNFM.campo82 = "";
      oNFM.campo83 = "";
      oNFM.campo84 = "";
      oNFM.campo85 = "";
      oNFM.campo86 = "";
      oNFM.campo87 = "";
      oNFM.campo88 = "";
      oNFM.campo89 = Format.data(Format.dataGUI(rst.getDate("dataemissao")), "dd/MM/yyyy", "yyyyMMdd");
      
      if (
        rst.getString("especie").equals("NFE") &&
        rst.getInt("id_situacaonfe") != SituacaoNfe.CANCELADA.getId() &&
        rst.getInt("estadoFornecedor") != 23 &&
        rst.getDouble("valorfcpst") > 0
      ) {
        oNFM.campo90 = FormatDecimal2R(rst.getDouble("valorfcpst"));
      }

      oNFM.campo91 = "N";
      
      i_exportacao.qtdRegistro++;
      i_arquivo.write(oNFM.getStringLayout158());
      if (rst.getInt("id_situacaonfe") != SituacaoNfe.CANCELADA.getId()) {
        sql = new StringBuilder();
        sql.append("SELECT ei.id_produto, ei.cfop, COALESCE(ei.id_tipoorigemmercadoria, p.id_tipoorigemmercadoriaentrada) AS id_tipoorigemmercadoria, p.utilizatabelasubstituicaotributaria, ei.quantidade, ei.valoripi,");
        sql.append(" SUM(ei.valortotal) as valortotal, ei.valorbasecalculo, ");
        sql.append(" ei.id_aliquota, ei.situacaotributaria,a.reduzido,ei.valorbasesubstituicao, (ei.valoricmssubstituicao + COALESCE(ei.valorfcpst, 0)) AS valoricmssubstituicao, ten.notaprodutor,");
        sql.append(" (a.porcentagem + COALESCE(a.porcentagemfcp, 0)) AS porcentagem, ROUND((ei.valortotal - (ei.valortotal * a.reduzido) / 100),2) AS base, ei.valoroutrasdespesasfiscal, c.geraicms,");
        sql.append(" ROUND((ei.valortotal - (ei.valortotal * a.reduzido) / 100) * ((a.porcentagem + COALESCE(a.porcentagemfcp, 0)) / 100), 2) AS icms, ROUND(ei.valoripi + (ei.valortotal - (ei.valortotal * a.reduzido) / 100),3) AS total,");
        sql.append(" CASE WHEN ei.quantidade = 0 THEN 0 ELSE ROUND(ei.valortotal / ei.quantidade,3) end AS valorunitario, tpc.cst AS cstpiscofins,");
        if (oFornecedor.idTipoEmpresa == TipoEmpresa.LUCRO_PRESUMIDO.getId()) {
          sql.append(" tpc.valorpispresumido as valorpis, tpc.valorcofinspresumido as valorcofins,");
          sql.append(" tpc.valorpispresumido AS aliquotapis, tpc.valorcofinspresumido AS aliquotacofins, ");
          sql.append(" CASE WHEN (tpc.valorpispresumido <> 0 OR tpc.cst = 73) THEN ROUND((ei.valorbasecalculo + ei.valorisento + ei.valoroutras + (CASE WHEN e.aplicaicmsipi = FALSE THEN ei.valoripi ELSE 0 END) - ei.valordesconto), 2) ELSE 0 END AS valorbasepiscofins,");
        } else {
          sql.append(" tpc.valorpis,tpc.valorcofins, tpc.valorpis AS aliquotapis, tpc.valorcofins AS aliquotacofins, ");
          sql.append(" CASE WHEN (tpc.valorpis <> 0 OR tpc.cst = 73) THEN ROUND((ei.valorbasecalculo + ei.valorisento + ei.valoroutras + (CASE WHEN e.aplicaicmsipi = FALSE THEN ei.valoripi ELSE 0 END) - ei.valordesconto), 2) ELSE 0 END AS valorbasepiscofins,");
        } 

        if (MasterVersionComparedWith("4.0.0") > 0) {
          sql.append(" ei.valorfretefiscal, ");
        } else {
          sql.append(" 0 as valorfretefiscal, ");
        }
        
        sql.append(" ei.valordesconto, ei.valordescontofiscal, ei.valorseguro, ei.valoroutras, ei.valorfrete, te.descricao as tipoembalagem,  tst.percentualmva, tst.percentualmvasimples, ROUND(ei.valortotal, 2) AS totalpiscofins,");
        sql.append(" (COALESCE(aorigem.porcentagem,0) + COALESCE(aorigem.porcentagemfcp, 0)) AS aliq_orig_perc, (COALESCE(adestino.porcentagem,0) + COALESCE(adestino.porcentagemfcp, 0)) AS aliq_dest_perc, a.porcentagemfcp, p.tiponaturezareceita, e.id_tipoentrada, ten. contabilidadepadrao, e.id_tiposaida, e.valorfcp,");
        sql.append(" COALESCE(ad.porcentagemfcp, 0) AS porcentagemfcpst, ei.valorfcpst, a.csosn");
        sql.append(" FROM escrita AS e");
        sql.append(" INNER JOIN escritaitem AS ei ON e.id = ei.id_escrita");
        sql.append(" INNER JOIN aliquota AS a ON a.id = ei.id_aliquota");
        sql.append(" LEFT JOIN aliquota AS aorigem ON aorigem.id = ei.id_aliquotainterestadual");
        sql.append(" LEFT JOIN aliquota AS adestino ON adestino.id = ei.id_aliquotadestino");
        sql.append(" INNER JOIN produto AS p ON p.id = ei.id_produto");
        sql.append(" INNER JOIN produtoaliquota AS pa ON pa.id_produto = p.id AND pa.id_estado = " + oFornecedor.idEstado);
        sql.append(" INNER JOIN tipoembalagem AS te ON te.id = p.id_tipoembalagem");
        sql.append(" INNER JOIN tipopiscofins AS tpc ON tpc.id = ei.id_tipopiscofins");
        sql.append(" INNER JOIN cfop AS c ON c.cfop = ei.cfop");
        sql.append(" LEFT JOIN tabelasubstituicaotributaria AS tst ON tst.id_aliquota = pa.id_aliquotacredito AND tst.id_estado = e.id_estado");
        sql.append(" INNER JOIN tipoentrada AS ten ON ten.id = ei.id_tipoentrada");
        sql.append(" LEFT JOIN pautafiscal AS pt ON pt.ncm1 = p.ncm1 AND pt.ncm2 = p.ncm2 AND pt.ncm3 = p.ncm3 AND pt.excecao = pa.excecao AND pt.id_estado = pa.id_estado");
        sql.append(" LEFT JOIN aliquota AS ad ON ad.id = pt.id_aliquotadebito");
        sql.append(" WHERE ei.id_escrita = " + rst.getLong("id"));
        sql.append(" GROUP BY ei.id_produto, ei.cfop, ei.id_tipoorigemmercadoria, p.id_tipoorigemmercadoriaentrada ,p.utilizatabelasubstituicaotributaria,ei.quantidade,ei.valoripi,  ei.id_aliquota");
        sql.append(" ,ei.situacaotributaria ,a.reduzido,ei.valorbasesubstituicao,ei.valoricmssubstituicao, ei.valorfcpst,ten.notaprodutor, a.porcentagem , a.porcentagemfcp, ei.valortotal");
        sql.append(" ,ei.valoroutrasdespesasfiscal, c.geraicms,tpc.cst, ");
        if (oFornecedor.idTipoEmpresa == TipoEmpresa.LUCRO_PRESUMIDO.getId()) {
          sql.append(" tpc.valorpispresumido, tpc.valorcofinspresumido,");
        } else {
          sql.append(" tpc.valorpis, tpc.valorcofins,");
        } 
        sql.append(" te.descricao, ei.valorbasecalculo,  ei.valorisento, ei.valoroutras, e.aplicaicmsipi, ei.valordesconto,");

        if (MasterVersionComparedWith("4.0.0") > 0) {
          sql.append(" ei.valorfretefiscal,");
        }

        sql.append(" ei.valordescontofiscal, ei.valoroutras, ei.valorseguro, ei.valorfrete, ");
        if (oFornecedor.idTipoEmpresa == TipoEmpresa.LUCRO_PRESUMIDO.getId()) {
          sql.append(" tpc.valorpispresumido, tpc.valorcofinspresumido, ");
        } else {
          sql.append(" tpc.valorpis, tpc.valorcofins, ");
        } 
        sql.append("tst.percentualmva, tst.percentualmvasimples, aorigem.porcentagem, aorigem.porcentagemfcp, adestino.porcentagemfcp, ");
        sql.append("adestino.porcentagem, p.tiponaturezareceita, e.id_tipoentrada, ");
        sql.append("ten.contabilidadepadrao, e.id_tiposaida, e.valorfcp, ad.porcentagemfcp, e.valorfcpst, a.csosn");
        rstProduto = stmProduto.executeQuery(sql.toString());
        while (rstProduto.next()) {
          FortesPNMVO oPNM = new FortesPNMVO();
          oPNM.campo1 = "PNM";
          oPNM.campo2 = rstProduto.getString("id_produto");
          oPNM.campo3 = rstProduto.getString("cfop").replace(".", "").replace(",", ".");
          oPNM.campo4 = "";

          switch (oFornecedor.idTipoEmpresa) {
            case 8:
            case 9:
            case 10:
            case 11:
              break;
            default:
              oPNM.campo5 = rstProduto.getString("id_tipoorigemmercadoria");
              oPNM.campo6 = Format.number(rstProduto.getInt("situacaotributaria"), 2);
          }

          oPNM.campo7 = rstProduto.getString("tipoembalagem");
          oPNM.campo8 = FormatDecimal2R(rstProduto.getDouble("quantidade"));
          
          if (rst.getDouble("valoroutrasdespesas") > 0) {
            oPNM.campo9 = FormatDecimal2R(rstProduto.getDouble("valortotal") + rstProduto.getDouble("valordescontofiscal"));
          } else {
            oPNM.campo9 = FormatDecimal2R(rstProduto.getDouble("valortotal") + rstProduto.getDouble("valordescontofiscal") - rstProduto.getDouble("valoroutrasdespesasfiscal"));
          }

          oPNM.campo10 = FormatDecimal2R(rstProduto.getDouble("valoripi"));
          if (((AliquotaDAO) VRInstance.criar(AliquotaDAO.class)).isIsento(rstProduto.getInt("situacaotributaria"))) {
            oPNM.campo11 = "2";
            oPNM.campo17 = "";
            oPNM.campo19 = "0.00";
          } else if (((AliquotaDAO)VRInstance.criar(AliquotaDAO.class)).isOutras(rstProduto.getInt("situacaotributaria"))) {
            oPNM.campo11 = "3";
            oPNM.campo17 = "";
            oPNM.campo19 = "0.00";
          } else if (rstProduto.getInt("situacaotributaria") == SituacaoTributaria.ISENTO_ICMS_ST.getId() || rstProduto.getInt("situacaotributaria") == SituacaoTributaria.SUBSTITUIDO.getId()) {
            oPNM.campo11 = "3";
            oPNM.campo17 = "1";
            oPNM.campo19 = "0.00";
          } else {
            oPNM.campo11 = "1";
            oPNM.campo17 = "";
            oPNM.campo19 = "0.00";
          } 
          if (rstProduto.getBoolean("geraicms") && rstProduto.getInt("situacaotributaria") != SituacaoTributaria.ISENTO_ICMS_ST.getId()) {
            if (rstProduto.getInt("situacaotributaria") == SituacaoTributaria.ISENTO.getId()) {
              if ("1.102,2.102".contains(rstProduto.getString("cfop"))) {
                oPNM.campo12 = FormatDecimal2R(0.0D);
              } else {
                if (rst.getDouble("valoroutrasdespesas") > 0) {
                  oPNM.campo12 = FormatDecimal2R(rstProduto.getDouble("valortotal"));
                } else {
                  oPNM.campo12 = FormatDecimal2R(rstProduto.getDouble("valortotal") - rstProduto.getDouble("valoroutrasdespesasfiscal"));
                }
              } 
            } else if ("1.556,2.556,1.551,2.551,1.910,2.910".contains(rstProduto.getString("cfop"))) {
              oPNM.campo12 = FormatDecimal2R(0.0D);
            } else {
              if (rst.getDouble("valoroutrasdespesas") > 0) {
                oPNM.campo12 = FormatDecimal2R(rstProduto.getDouble("valortotal"));
              } else {
                oPNM.campo12 = FormatDecimal2R(rstProduto.getDouble("valortotal") - rstProduto.getDouble("valoroutrasdespesasfiscal"));
              }
            } 
            oPNM.campo13 = FormatDecimal2R(rstProduto.getDouble("porcentagem"));
          } else {
            oPNM.campo12 = FormatDecimal2R(0.0D);
            oPNM.campo13 = FormatDecimal2R(0.0D);
          } 
          if (rstProduto.getInt("situacaotributaria") == SituacaoTributaria.SUBSTITUIDO.getId()) {
            oPNM.campo14 = FormatDecimal2R(rstProduto.getDouble("valorbasesubstituicao"));
            oPNM.campo15 = FormatDecimal2R(rstProduto.getDouble("valoricmssubstituicao"));
            oPNM.campo16 = "0";
            oPNM.campo18 = "0.00";
            oPNM.campo20 = "0.00";
            oPNM.campo21 = "0.00";
          } else if (rstProduto.getInt("situacaotributaria") == SituacaoTributaria.ISENTO_ICMS_ST.getId()) {
            oPNM.campo14 = "0.00";
            oPNM.campo15 = "0.00";
            if (rstProduto.getDouble("percentualmva") > 0.0D) {
              oPNM.campo16 = "1";
            } else {
              oPNM.campo16 = "0";
            } 
            oPNM.campo18 = rstProduto.getString("valortotal");
            oPNM.campo20 = rstProduto.getString("valortotal");
            if (rst.getInt("id_tipocrt") == 1 && rstProduto.getBoolean("utilizatabelasubstituicaotributaria")) {
              oPNM.campo21 = FormatDecimal2R(rstProduto.getDouble("percentualmva") + rstProduto.getDouble("percentualmvasimples"));
            } else {
              oPNM.campo21 = FormatDecimal2R(rstProduto.getDouble("percentualmva"));
            } 
          } else {
            oPNM.campo14 = "0.00";
            oPNM.campo15 = "0.00";
            oPNM.campo16 = "0";
            oPNM.campo18 = "0.00";
            oPNM.campo20 = FormatDecimal2R(rstProduto.getDouble("valorbasesubstituicao"));
            oPNM.campo21 = FormatDecimal2R(rstProduto.getDouble("percentualmva"));
          } 
          oPNM.campo22 = "0.00";
          oPNM.campo23 = "0.00";

          //////////////////////////////////////////////////////// 
          if (rst.getDouble("valoroutrasdespesas") > 0) {
            oPNM.campo24 = FormatDecimal2R(rstProduto.getDouble("valortotal"));
          } else {
            oPNM.campo24 = FormatDecimal2R(rstProduto.getDouble("valortotal") - rstProduto.getDouble("valoroutrasdespesasfiscal"));
          }
          
          oPNM.campo25 = "0.00";
          oPNM.campo26 = FormatDecimal2R(rstProduto.getDouble("porcentagem"));
          oPNM.campo27 = "0.00";
          oPNM.campo28 = "0.00";
          oPNM.campo29 = "0.00";
          oPNM.campo30 = "0.00";
          oPNM.campo31 = FormatDecimal2R(rstProduto.getDouble("porcentagem"));
          oPNM.campo32 = "";
          oPNM.campo33 = "";
          oPNM.campo34 = "";
          oPNM.campo35 = "";
          oPNM.campo36 = "";
          String cstPisCofins = "";
          if (oFornecedor.idTipoEmpresa == TipoEmpresa.LUCRO_PRESUMIDO.getId()) {
            oPNM.campo37 = "";
            oPNM.campo38 = "";
          } else if ("5.929, 6.929".contains(rstProduto.getString("cfop"))) {
            oPNM.campo37 = "49";
            oPNM.campo38 = "49";
            cstPisCofins = "49";
          } else if (rstProduto.getBoolean("notaprodutor") || "1.102,2.102,1.949,2.949,1.556,2.556,1.551,2.551,1.910,2.910,1.920,2.920,1.908,2.908,1.926,2.926".contains(rstProduto.getString("cfop"))) {
            oPNM.campo37 = "99";
            oPNM.campo38 = "99";
            cstPisCofins = "99";
          } else {
            oPNM.campo37 = Format.number(rstProduto.getInt("cstpiscofins"), 2);
            oPNM.campo38 = Format.number(rstProduto.getInt("cstpiscofins"), 2);
            cstPisCofins = Format.number(rstProduto.getInt("cstpiscofins"), 2);
          }

          double valueOff = 0;

          if (rstProduto.getDouble("valordesconto") > 0) {
            valueOff = rstProduto.getDouble("valordesconto");
          }
          
          if (rstProduto.getDouble("valordescontofiscal") > 0) {
            valueOff = rstProduto.getDouble("valordescontofiscal");
          }

          String netValue = FormatDecimal2R(
            Double.parseDouble(oPNM.campo9) +
            rstProduto.getDouble("valorfretefiscal") +
            rstProduto.getDouble("valorseguro") +
            rstProduto.getDouble("valoroutrasdespesasfiscal") -
            valueOff
          );
          
          switch (cstPisCofins) {
            case "09":
            case "49":
            case "99":
              oPNM.campo39 = "0.00";
              break;
            default:
              oPNM.campo39 = netValue;
          }

          switch (cstPisCofins) {
            case "09":
            case "49":
            case "99":
              oPNM.campo40 = "0.00";
              break;
            default:
              oPNM.campo40 = netValue;
          }
      
          oPNM.campo41 = FormatDecimal2R(rstProduto.getDouble("valorfretefiscal"));

          oPNM.campo42 = FormatDecimal2R(rstProduto.getDouble("valorseguro"));
          oPNM.campo43 = FormatDecimal2R(valueOff);
          
          if (rst.getDouble("valoroutrasdespesas") > 0) {
            oPNM.campo44 = netValue;
          } else {
            oPNM.campo44 = FormatDecimal2R(
              Double.parseDouble(oPNM.campo9) +
              rstProduto.getDouble("valorfrete") +
              Double.parseDouble(oPNM.campo42) +
              rstProduto.getDouble("valoroutrasdespesasfiscal") -
              Double.parseDouble(oPNM.campo43)
            );
          }
          
          oPNM.campo45 = "";
          oPNM.campo46 = "";
          oPNM.campo47 = "";
          oPNM.campo48 = "";
          
          switch (oFornecedor.idTipoEmpresa) {
            case 8:
            case 9:
            case 10:
            case 11:
              oPNM.campo49 = Format.number(rstProduto.getInt("id_tipoorigemmercadoria"), 1);
              oPNM.campo50 = Format.number(rstProduto.getInt("csosn"), 3);
              break;
            default:
              oPNM.campo49 = "";
              oPNM.campo50 = "";
          }

          oPNM.campo51 = "1";
          oPNM.campo52 = "0.00";
          oPNM.campo54 = "0.00";
          oPNM.campo56 = "0.00";
          oPNM.campo58 = "0.00";

          if (((oFornecedor.idTipoEmpresa == TipoEmpresa.LUCRO_REAL.getId() || oFornecedor.idTipoEmpresa == TipoEmpresa.LUCRO_PRESUMIDO.getId()) && this.oFortesDAO
            .isFornecedorDistribuidor(i_exportacao.idLoja)) || rstProduto
            .getString("cfop").startsWith("3.")) {
            int[] arrayAliquotaPisCofins = { 3, 4, 6, 73 };
            if (ArrayUtilsContains(arrayAliquotaPisCofins, rstProduto.getInt("cstpiscofins"))) {
              oPNM.campo52 = Format.decimal4(rstProduto.getDouble("aliquotacofins"));
              oPNM.campo56 = Format.decimal4(rstProduto.getDouble("aliquotapis"));
            } 
            int[] arrayValorPisCofins = { 4, 6, 73 };
            if (ArrayUtilsContains(arrayValorPisCofins, rstProduto.getInt("cstpiscofins"))) {
              oPNM.campo54 = FormatDecimal2R(rstProduto.getDouble("valorcofins") * rstProduto.getDouble("aliquotacofins") / 100.0D);
              oPNM.campo58 = FormatDecimal2R(rstProduto.getDouble("valorpis") * rstProduto.getDouble("valorpis") / 100.0D);
            } 
          } 
          oPNM.campo53 = "";
          oPNM.campo55 = "1";
          oPNM.campo57 = "";
          oPNM.campo59 = "";
          oPNM.campo60 = "";
          oPNM.campo61 = "";
          oPNM.campo62 = FormatDecimal2R(rstProduto.getDouble("valoroutrasdespesasfiscal"));
          String conta = "";
          if (rstProduto.getBoolean("contabilidadepadrao")) {
            int idContaContabilDebito = this.oParametroContabilidadeDAO.carregarContaContabilPadrao();
            conta = getContaContabil(idContaContabilDebito);
            if (!conta.isEmpty()) {
              oPNM.campo63 = conta;
            } else {
              oPNM.campo63 = "";
            } 
          } else {
            int idContaContabilDebito = this.oParametroContabilidadeDAO.carregarContaContabil(rstProduto.getInt("id_tipoentrada"));
            conta = getContaContabil(idContaContabilDebito);
            if (!conta.isEmpty()) {
              oPNM.campo63 = conta;
            } else {
              oPNM.campo63 = "";
            } 
          } 
          oPNM.campo64 = "0";
          oPNM.campo65 = "";
          oPNM.campo66 = "";
          oPNM.campo67 = "";
          oPNM.campo68 = "";
          oPNM.campo69 = "";
          int[] arrayCfopDerivadoPetroleo = { 
            1651, 1652, 1653, 1658, 1659, 1660, 1661, 1662, 1663, 1664, 
            2651, 2652, 2653, 2658, 2659, 2660, 2661, 2662, 2663, 2664, 
            3651, 3652, 3653
          };
          oPNM.campo70 = ArrayUtilsContains(arrayCfopDerivadoPetroleo, rstProduto.getInt("cfop")) ? "S" : "N";
          oPNM.campo71 = "";
          oPNM.campo72 = "";
          oPNM.campo73 = "";
          if (oFornecedor.idEstado == TipoEstado.PE.getId()) {
            oPNM.campo74 = "1";
          } else {
            oPNM.campo74 = "";
          } 
          oPNM.campo75 = "";
          oPNM.campo76 = "";
          if (i_exportacao.tipoData == TipoData.EMISSAO.getId()) {
            oPNM.campo77 = FormatDecimal2R(rstProduto.getDouble("valortotal"));
            if (rstProduto.getDouble("valortotal") > 0.0D && rstProduto.getDouble("porcentagemfcp") >= 2.0D)
              oPNM.campo78 = FormatDecimal2R(rstProduto.getDouble("porcentagemfcp")); 
            oPNM.campo79 = FormatDecimal2R(rstProduto.getDouble("aliq_orig_perc"));
            oPNM.campo80 = FormatDecimal2R(rstProduto.getDouble("aliq_dest_perc"));
          } 
          if (rst.getInt("estadoFornecedor") != ((FornecedorDAO)VRInstance.criar(FornecedorDAO.class)).getIdEstadoPadrao(i_exportacao.idLoja) && rstProduto.getBoolean("utilizatabelasubstituicaotributaria")) {
            oPNM.campo6 = "60";
            oPNM.campo16 = "0";
            oPNM.campo17 = "1";
            oPNM.campo21 = "";
          } 
          
          int[] aCstPisCofinsSaida = { 2, 3, 4, 5, 6, 7, 8, 9 };
          if (ArrayUtilsContains(aCstPisCofinsSaida, rstProduto.getInt("cstpiscofins"))) {
            String code = getCodigoACFiscal(rstProduto.getInt("tiponaturezareceita"), rstProduto.getInt("cstpiscofins"));
            oPNM.campo45 = code;
            oPNM.campo46 = code;
            oPNM.campo47 = (verificaProdepe(rst.getInt("id")) == true) ? code : "";
          } else {
          
            oPNM.campo45 = "";
            oPNM.campo46 = "";
            oPNM.campo47 = "";
          }

          if (!cstPisCofins.isEmpty()) {
            int[] aCstPisCofinsEntrada = { 
                50, 51, 52, 53, 54, 55, 56, 60, 61, 62, 
                63, 64, 65, 66, 71, 72, 73, 75, 99 };
            if (!ArrayUtilsContains(aCstPisCofinsEntrada, rstProduto.getInt("cstpiscofins"))) {
              oPNM.campo39 = "0.00";
              oPNM.campo40 = "0.00";
            } 
          } 
          if (oPNM.campo16.equals("1") && rstProduto.getDouble("porcentagemfcp") > 0.0D) {
            oPNM.campo81 = "S";
          } else {
            oPNM.campo81 = "N";
          } 
          oPNM.campo82 = "";
          ParametroDAO oParametroDAO = (ParametroDAO)VRInstance.criar(ParametroDAO.class);
          if (oParametroDAO.get(i_exportacao.idLoja, 511).getBoolean())
            oPNM.campo83 = FormatDecimal2R(rstProduto.getDouble("icms")); 
          oPNM.campo84 = "";

          if (
            rst.getInt("id_tipoentradasaida") == TipoEntradaSaida.ENTRADA.getId() &&
            rst.getString("especie").equals("NFE") && 
            oPNM.campo5.equals("S") &&
            !"30,60".contains(oPNM.campo6)
          ) {
            oPNM.campo85 = FormatDecimal2R(rstProduto.getDouble("base"));
          } else {
            oPNM.campo85 = "";
          }

          if (rst.getInt("id_tipoentradasaida") == TipoEntradaSaida.ENTRADA.getId() && rst.getObject("id_notasaida") == null) {
            if (rst.getString("especie").equals("NFE")) {
              if (!oPNM.campo6.isEmpty() && "30,60".contains(oPNM.campo6)) {
                oPNM.campo86 = "";
                oPNM.campo87 = "";
              } else {
                oPNM.campo86 = FormatDecimal2R(rstProduto.getDouble("valorfcp") / rstProduto.getDouble("base") * 100.0D);
                oPNM.campo87 = FormatDecimal2R(rstProduto.getDouble("valorfcp"));
              }

              if (!oPNM.campo6.isEmpty() && !"10,30,60,70,90".contains(oPNM.campo6)) {
                oPNM.campo88 = FormatDecimal2R(rstProduto.getDouble("valorbasesubstituicao"));
                oPNM.campo89 = FormatDecimal2R(rstProduto.getDouble("porcentagemfcpst"));
                oPNM.campo90 = FormatDecimal2R(rstProduto.getDouble("valorfcpst"));
              } else {
                oPNM.campo88 = "";
                oPNM.campo89 = "";
                oPNM.campo90 = "";
              }
            } 
          }

          oPNM.campo91 = "N";
          oPNM.campo92 = "";
          oPNM.campo93 = "";
          oPNM.campo94 = "";
          oPNM.campo95 = "";
          oPNM.campo96 = "";
          oPNM.campo97 = "";
          oPNM.campo98 = "";
          oPNM.campo99 = "";
          oPNM.campo100 = "";
          oPNM.campo101 = "";
          oPNM.campo102 = "";
          oPNM.campo103 = "";
          oPNM.campo104 = "";
          oPNM.campo105 = "";
          oPNM.campo106 = "";
          oPNM.campo107 = "";
          oPNM.campo108 = "";
          oPNM.campo108 = "";
          oPNM.campo109 = "";
          oPNM.campo110 = "";
          oPNM.campo111 = "";
          oPNM.campo112 = "";
          oPNM.campo113 = "";
          oPNM.campo114 = "";
          oPNM.campo115 = "";
          oPNM.campo116 = "";
          oPNM.campo117 = "";
          oPNM.campo118 = "";
          oPNM.campo119 = "";

          if (rstProduto.getInt("situacaotributaria") == 51 && rstProduto.getDouble("valorbasecalculo") > 0) {
            oPNM.campo120 = FormatDecimal2R(rstProduto.getDouble("porcentagem"));
          }

          oPNM.campo121 = "";
          oPNM.campo122 = "";
          oPNM.campo123 = "";

          i_exportacao.qtdRegistro++;
          i_arquivo.write(oPNM.getStringLayout160());
        } 
        sql = new StringBuilder();
        sql.append("SELECT SUM(ei.valortotal + ei.valorfrete - ei.valordesconto) AS valortotal, es.sigla AS uf, ei.cfop, SUM(ei.valorbasecalculo) AS basecalculoicms, (a.porcentagem + COALESCE(a.porcentagemfcp, 0)) AS aliquotaicms, SUM(ei.valoricms + COALESCE(ei.valorfcp, 0)) AS valoricms,");
        sql.append("SUM(ei.valorisento) AS valorisento, SUM(ei.valoroutras) AS valoroutras, SUM(ei.valorfrete) AS valorfrete, COALESCE(ei.id_tipoorigemmercadoria, p.id_tipoorigemmercadoriaentrada) AS id_tipoorigemmercadoria, ei.situacaotributaria, tpc.cst, te.id_tipocrt,");
        sql.append(" SUM(ei.valorbasecalculo + ei.valorisento + ei.valorfrete + ei.valoroutras - ei.valordesconto + ei.valoricmssubstituicao) AS valortotaloperacao, COALESCE(a.porcentagemfcp, 0) AS porcentagemfcp,");
        sql.append(" SUM(COALESCE(ei.valorfcp, 0)) AS valorfcp, SUM(COALESCE(ei.valorfcpst)) AS valorfcpst,");
        sql.append(" SUM(COALESCE(ei.valorbasesubstituicao, 0)) AS valorbasesubstituicao");
        sql.append(" FROM escrita AS e");
        sql.append(" INNER JOIN escritaitem AS ei ON ei.id_escrita = e.id");
        sql.append(" INNER JOIN aliquota AS a ON a.id = ei.id_aliquota");
        sql.append(" INNER JOIN produto AS p ON p.id = ei.id_produto");
        sql.append(" INNER JOIN tipopiscofins AS tpc ON tpc.id = ei.id_tipopiscofins");
        sql.append(" LEFT JOIN fornecedor AS f ON f.id = e.id_fornecedor");
        sql.append(" LEFT JOIN tipoempresa AS te ON te.id = f.id_tipoempresa");
        sql.append(" LEFT JOIN clienteeventual AS ce ON ce.id = e.id_clienteeventual");
        sql.append(" INNER JOIN estado AS es ON es.id =  COALESCE(f.id_estado, ce.id_estado)");
        sql.append(" WHERE e.id = " + rst.getLong("id"));
        sql.append(" GROUP BY ei.cfop, es.sigla, (a.porcentagem + COALESCE(a.porcentagemfcp, 0)), COALESCE(ei.id_tipoorigemmercadoria, p.id_tipoorigemmercadoriaentrada), ei.situacaotributaria, tpc.cst, te.id_tipocrt, COALESCE(a.porcentagemfcp, 0)");
        rstImposto = stmImposto.executeQuery(sql.toString());
        while (rstImposto.next()) {
          FortesINMVO oINM = new FortesINMVO();
          oINM.campo1 = "INM";
          oINM.campo2 = FormatDecimal2R(rstImposto.getDouble("valortotaloperacao"));
          oINM.campo3 = rstImposto.getString("uf");
          oINM.campo4 = rstImposto.getString("cfop").replace(".", "").replace(",", ".");
          oINM.campo5 = "";
          oINM.campo6 = "";
          oINM.campo7 = "";
          oINM.campo8 = "";
          oINM.campo9 = "";
          oINM.campo10 = FormatDecimal2R(rstImposto.getDouble("valortotal"));
          oINM.campo11 = "";
          oINM.campo12 = "";
          oINM.campo13 = "";
          oINM.campo14 = "";
          oINM.campo15 = (rst.getInt("id_tipocrt") == TipoCrt.SIMPLES_NACIONAL.getId()) ? "S" : "N";
          oINM.campo16 = "N";
          if (((TipoPisCofinsDAO)VRInstance.criar(TipoPisCofinsDAO.class)).isSubstituido(rstImposto.getInt("cst"))) {
            oINM.campo17 = "S";
            oINM.campo18 = "S";
          } else {
            oINM.campo17 = "N";
            oINM.campo18 = "N";
          } 
          oINM.campo19 = rstImposto.getString("id_tipoorigemmercadoria");
          oINM.campo20 = Format.number(rstImposto.getInt("situacaotributaria"), 2);
          if (rst.getInt("estadoFornecedor") != ((FornecedorDAO) VRInstance.criar(FornecedorDAO.class)).getIdEstadoPadrao(i_exportacao.idLoja))
            oINM.campo20 = "60"; 
          oINM.campo21 = "";
          oINM.campo22 = "";
          oINM.campo23 = "";
          if (rst.getInt("id_tipocrt_loja") == TipoCrt.SIMPLES_NACIONAL.getId() && (
            (TipoPisCofinsDAO)VRInstance.criar(TipoPisCofinsDAO.class)).isMonofasico(rstImposto.getInt("cst"))) {
            oINM.campo24 = "S";
            oINM.campo25 = "S";
          } else {
            oINM.campo24 = "N";
            oINM.campo25 = "N";
          } 
          if (rst.getInt("id_tipocrt") != TipoCrt.SIMPLES_NACIONAL.getId() && rstImposto.getDouble("porcentagemfcp") > 0.0D) {
            oINM.campo26 = "S";
          } else {
            oINM.campo26 = "N";
          }
          
          oINM.campo27 = "";

          if (
            rst.getInt("id_tipoentradasaida") == TipoEntradaSaida.ENTRADA.getId() &&
            rst.getObject("id_notasaida") == null &&
            rst.getString("modelo").equals("55")
          ) {
            if (!oINM.campo20.isEmpty() && !"10,30,60,70,90".contains(oINM.campo20)) {
              oINM.campo28 = FormatDecimal2R(rstImposto.getDouble("basecalculoicms"));
              oINM.campo29 = FormatDecimal2R(rstImposto.getDouble("valorfcp") / rstImposto.getDouble("basecalculoicms") * 100.0D);
              oINM.campo30 = FormatDecimal2R(rstImposto.getDouble("valorfcp"));
              oINM.campo31 = FormatDecimal2R(rstImposto.getDouble("valorbasesubstituicao"));
              
              if (!oINM.campo20.equals("00")) {
                oINM.campo32 = FormatDecimal2R(rstImposto.getDouble("porcentagemfcp"));
              }

              oINM.campo33 = FormatDecimal2R(rstImposto.getDouble("valorbasesubstituicao") * rstImposto.getDouble("porcentagemfcp"));
            } else {
              oINM.campo28 = "";
              oINM.campo29 = "";
              oINM.campo30 = "";
              oINM.campo31 = "";
              oINM.campo32 = "";
              oINM.campo33 = "";
            }
          }

          if (rstImposto.getInt("situacaotributaria") == 51 && rstImposto.getDouble("basecalculoicms") > 0) {
            oINM.campo34 = FormatDecimal2R(rstImposto.getDouble("aliquotaicms"));
          }

          i_exportacao.qtdRegistro++;
          i_arquivo.write(oINM.getStringLayout158());
        } 
        sql = new StringBuilder();
        sql.append("SELECT SUM(ei.valortotal) AS valortotal, tst.percentualmva, tst.percentualmvasimples, SUM(ei.valorbasecalculo) AS valorbasecalculo, (a.porcentagemfinal + COALESCE(a.porcentagemfcp, 0)) AS porcentagemfinal, ei.situacaotributaria,");
        sql.append(" p.utilizatabelasubstituicaotributaria, a.porcentagemfcp");
        sql.append(" FROM escrita AS e");
        sql.append(" INNER JOIN escritaitem AS ei ON e.id = ei.id_escrita");
        sql.append(" INNER JOIN produto AS p ON p.id = ei.id_produto");
        sql.append(" INNER JOIN aliquota AS a ON a.id = ei.id_aliquota");
        sql.append(" LEFT JOIN tabelasubstituicaotributaria AS tst ON tst.id_aliquota = ei.id_aliquota AND tst.id_estado = e.id_estado");
        sql.append(" WHERE ei.id_escrita = " + rst.getLong("id"));
        sql.append(" GROUP BY tst.percentualmva, tst.percentualmvasimples, (a.porcentagemfinal + COALESCE(a.porcentagemfcp, 0)), ei.situacaotributaria, p.utilizatabelasubstituicaotributaria, a.porcentagemfcp");
        rstST = stmST.executeQuery(sql.toString());
        while (rstST.next()) {
          FortesSNMVO oSNM = new FortesSNMVO();
          oSNM.campo1 = "SNM";
          oSNM.campo2 = "1";
          oSNM.campo3 = FormatDecimal2R(rstST.getDouble("valortotal"));
          oSNM.campo4 = "0.00";
          oSNM.campo5 = FormatDecimal2R(rstST.getDouble("valortotal"));
          switch (rstST.getInt("situacaotributaria")) {
            case 30:
              if (rst.getInt("id_tipocrt") == 1 && rstST.getBoolean("utilizatabelasubstituicaotributaria")) {
                oSNM.campo6 = FormatDecimal2R(rstST.getDouble("percentualmva") + rstST.getDouble("percentualmvasimples"));
                break;
              } 
              oSNM.campo6 = FormatDecimal2R(rstST.getDouble("percentualmva"));
              break;
            case 60:
              oSNM.campo6 = FormatDecimal2R(0.0D);
              break;
            default:
              oSNM.campo6 = FormatDecimal2R(rstST.getDouble("percentualmva"));
              break;
          } 

          oSNM.campo7 = "0.00";
          oSNM.campo8 = "0.00";
          if (rstST.getDouble("porcentagemfcp") > 0.0D) {
            oSNM.campo9 = "S";
          } else if (rstST.getDouble("porcentagemfcp") == 0.0D) {
            oSNM.campo9 = "N";
          } else {
            oSNM.campo9 = "";
          }

          i_exportacao.qtdRegistro++;
          i_arquivo.write(oSNM.getString());
        } 
      } 
      ProgressBar.next();
    } 
    stmProduto.close();
    stm.close();
  }
  
  private void exportarNotaFiscalSaida(ExportarFortesVO i_exportacao, Arquivo i_arquivo) throws Exception {
    VRStatement stm = null;
    VRStatement stmProduto = null;
    VRStatement stmImposto = null;
    ResultSet rst = null;
    ResultSet rstProduto = null;
    ResultSet rstImposto = null;
    StringBuilder sql = null;
    FornecedorVO oFornecedor = ((FornecedorDAO)VRInstance.criar(FornecedorDAO.class)).carregar((((LojaDAO)VRInstance.criar(LojaDAO.class)).carregar(i_exportacao.idLoja)).idFornecedor);
    stm = VRStatement.createStatement();
    stmProduto = VRStatement.createStatement();
    stmImposto = VRStatement.createStatement();
    ProgressBar.setStatus("Exportando Nota Fiscal (Saida)");
    sql = new StringBuilder();
    sql.append("SELECT e.id, ei.cfop, COALESCE(e.id_fornecedor, e.id_clienteeventual, 0) AS id_fornecedor, e.id_fornecedorprodutorrural, e.dataemissao, e.numeronota, e.id_tipoentradasaida,");
    sql.append("SUM(ei.valorbasecalculo + ei.valorisento + ei.valoroutras) AS valortotal, e.serie, e.chavenfe, e.especie, e.data, e.id_situacaonfe,");
    sql.append("e.valorfrete, e.valoroutrasdespesas, e.valoripi, e.valorbasesubstituicao, (e.valoricmssubstituicao + COALESCE(e.valorfcpst, 0)) AS valoricmssubstituicao, e.valordesconto, COUNT(ei.id) AS qtditem,");
    sql.append("e.id_tipofretenotafiscal, e.observacao,");
    if (oFornecedor.idTipoEmpresa == TipoEmpresa.LUCRO_PRESUMIDO.getId()) {
      sql.append("SUM(CASE WHEN tpc.valorpispresumido <> 0 THEN ROUND((ei.valorbasecalculo + ei.valorisento + ei.valoroutras + (CASE WHEN e.aplicaicmsipi = FALSE THEN ei.valoripi ELSE 0 END) - ei.valordesconto), 2) ELSE 0 END) AS valorbasepiscofins,");
      sql.append("SUM(ROUND(((ei.valorbasecalculo + ei.valorisento + ei.valoroutras + (CASE WHEN e.aplicaicmsipi = FALSE THEN ei.valoripi ELSE 0 END) - ei.valordesconto) * ((100 - tpc.reduzidocreditopresumido) / 100)) * tpc.valorpispresumido / 100, 2)) AS valorpis,");
      sql.append("SUM(ROUND(((ei.valorbasecalculo + ei.valorisento + ei.valoroutras + (CASE WHEN e.aplicaicmsipi = FALSE THEN ei.valoripi ELSE 0 END) - ei.valordesconto) * ((100 - tpc.reduzidocreditopresumido) / 100)) * tpc.valorcofinspresumido / 100, 2)) AS valorcofins");
    } else {
        sql.append("SUM(CASE WHEN tpc.valorpis <> 0 THEN ROUND((ei.valorbasecalculo + ei.valorisento + ei.valoroutras + (CASE WHEN e.aplicaicmsipi = FALSE THEN ei.valoripi ELSE 0 END) - ei.valordesconto), 2) ELSE 0 END) AS valorbasepiscofins,");
        sql.append("SUM(ROUND(((ei.valorbasecalculo + ei.valorisento + ei.valoroutras + (CASE WHEN e.aplicaicmsipi = FALSE THEN ei.valoripi ELSE 0 END) - ei.valordesconto) * ((100 - tpc.reduzidocredito) / 100)) * tpc.valorpis / 100, 2)) AS valorpis,");
      sql.append("SUM(ROUND(((ei.valorbasecalculo + ei.valorisento + ei.valoroutras + (CASE WHEN e.aplicaicmsipi = FALSE THEN ei.valoripi ELSE 0 END) - ei.valordesconto) * ((100 - tpc.reduzidocredito) / 100)) * tpc.valorcofins / 100, 2)) AS valorcofins");
    } 
    sql.append(" , estabelecimentote.id_tipocrt AS id_tipocrt_loja, e.modelo, e.valorfcpst,");
    sql.append(" e.valortotalbruto, e.valordescontofiscal, e.id_indicadorpagamento, e.valorcontabil, e.id_clienteeventual");
    sql.append(" FROM escrita AS e");
    sql.append(" INNER JOIN escritaitem AS ei ON ei.id_escrita = e.id");
    sql.append(" LEFT JOIN fornecedor AS f ON f.id = e.id_fornecedor");
    sql.append(" LEFT JOIN clienteeventual AS ce ON ce.id = e.id_clienteeventual");
    sql.append(" INNER JOIN tipopiscofins AS tpc ON tpc.id = ei.id_tipopiscofins");
    sql.append(" INNER JOIN loja l ON l.id = e.id_loja");
    sql.append(" INNER JOIN fornecedor AS estabelecimento ON estabelecimento.id = l.id_fornecedor");
    sql.append(" INNER JOIN tipoempresa AS estabelecimentote ON estabelecimentote.id = estabelecimento.id_tipoempresa");
    sql.append(" WHERE COALESCE(e.id_notasaida, 0) > 0");
    if (i_exportacao.tipoData == TipoData.ENTRADA.getId()) {
      sql.append(" AND e.data BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    } else if (i_exportacao.tipoData == TipoData.EMISSAO.getId()) {
      sql.append(" AND e.dataemissao BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    } 
    sql.append(" AND e.cupomfiscal = FALSE");
    sql.append(" AND e.id_loja = " + i_exportacao.idLoja);
    sql.append(" GROUP BY e.id, ei.cfop, COALESCE(e.id_fornecedor, e.id_clienteeventual, 0), e.dataemissao, e.id_tipoentradasaida, e.modelo, e.valortotalbruto, e.valordescontofiscal, e.id_indicadorpagamento, e.valorcontabil, ");
    sql.append(" e.numeronota, e.serie, e.chavenfe, e.especie, e.data, e.valorfrete, e.aplicaicmsipi, e.id_situacaonfe, e.id_fornecedorprodutorrural,");
    sql.append(" e.valoroutrasdespesas, e.valoripi, e.valorbasesubstituicao, (e.valoricmssubstituicao + COALESCE(e.valorfcpst, 0)), e.valordesconto, e.id_tipofretenotafiscal, e.observacao, id_tipocrt_loja, e.valorfcpst, e.id_clienteeventual");
    sql.append(" ORDER BY e.id");
    rst = stm.executeQuery(sql.toString());
    rst.last();
    ProgressBar.setMaximum(rst.getRow());
    rst.beforeFirst();
    while (rst.next()) {
      FortesNFMVO oNFM = new FortesNFMVO();
      oNFM.campo1 = "NFM";
      oNFM.campo2 = Format.number(this.oFortesDAO.getCodigoEstabelecimento(i_exportacao.idLoja), 4);
      if (rst.getInt("id_tipoentradasaida") == TipoEntradaSaida.SAIDA.getId()) {
        oNFM.campo3 = "S";
        oNFM.campo5 = "S";
      } else {
        oNFM.campo3 = "E";
        oNFM.campo5 = "S";
      } 
      oNFM.campo4 = rst.getString("especie");
      oNFM.campo6 = "";
      oNFM.campo7 = rst.getString("serie");
      oNFM.campo8 = "";
      oNFM.campo9 = Format.number(rst.getInt("numeronota"), rst.getString("modelo").equals("55") ? 9 : 7);
      oNFM.campo10 = "";
      oNFM.campo11 = "";
      oNFM.campo12 = Format.data(Format.dataGUI(rst.getDate("dataemissao")), "dd/MM/yyyy", "yyyyMMdd");
      oNFM.campo28 = "0.00";
      SituacaoNfe situacaoNfe = SituacaoNfe.getEnumById(rst.getInt("id_situacaonfe"));
      switch (situacaoNfe) {
        case CANCELADA:
          oNFM.campo13 = "1";
          oNFM.campo28 = "";
          break;
        case DENEGADA:
          oNFM.campo13 = "1";
          break;
        case INUTILIZADA:
          oNFM.campo13 = "1";
          break;
        default:
          oNFM.campo13 = "0";
          break;
      } 
      if (rst.getInt("id_situacaonfe") != 1) {
        oNFM.campo14 = "";
        oNFM.campo15 = "";
        oNFM.campo26 = "";
        oNFM.campo36 = "";
        oNFM.campo37 = "";
        oNFM.campo48 = "";
        oNFM.campo52 = "";
        oNFM.campo53 = "";
        oNFM.campo60 = "";
        oNFM.campo61 = "";
      } else {
        oNFM.campo14 = Format.data(Format.dataGUI(rst.getDate("data")), "dd/MM/yyyy", "yyyyMMdd");
        
        if (rst.getInt("id_tipoentradasaida") == TipoEntradaSaida.SAIDA.getId()) {
          if (rst.getInt("id_fornecedorprodutorrural") > 0) {
            oNFM.campo15 = Format.number(rst.getInt("id_fornecedorprodutorrural"), 9);
          } else if (rst.getInt("id_clienteeventual") > 0) {
            oNFM.campo15 = Format.number(rst.getInt("id_clienteeventual"), 9);
          } else {
            oNFM.campo15 = Format.number(rst.getInt("id_fornecedor"), 9);
          }

          oNFM.campo26 = FormatDecimal2R(rst.getDouble("valorcontabil") + rst.getDouble("valordesconto"));
        } else {
          if (rst.getInt("id_fornecedorprodutorrural") > 0) {
            oNFM.campo15 = Format.number(rst.getInt("id_fornecedorprodutorrural"), 9);
            oNFM.campo26 = FormatDecimal2R(rst.getDouble("valorcontabil"));
          } else {
            oNFM.campo15 = Format.number(rst.getInt("id_fornecedor"), 9);

            if ("1.102,1.403".contains(rst.getString("cfop"))) {
              oNFM.campo26 = FormatDecimal2R(rst.getDouble("valortotalbruto"));
            } else {
              oNFM.campo26 = FormatDecimal2R(rst.getDouble("valorcontabil"));
            }
          }
        }
        
        oNFM.campo36 = FormatDecimal2R(rst.getDouble("valorcontabil"));
        oNFM.campo37 = Format.number(rst.getInt("qtditem"), 10);
        if (rst.getInt("id_tipofretenotafiscal") == TipoFreteNotaFiscal.DESTINATARIO.getId()) {
          oNFM.campo48 = "D";
        } else if (rst.getInt("id_tipofretenotafiscal") == TipoFreteNotaFiscal.EMITENTE.getId()) {
          oNFM.campo48 = "R";
        } else {
          oNFM.campo48 = "N";
        } 
        if (this.oFortesDAO.isFornecedorDistribuidor(i_exportacao.idLoja)) {
          oNFM.campo52 = "";
          oNFM.campo53 = "";
        } else {
          oNFM.campo52 = FormatDecimal2R(rst.getDouble("valorbasepiscofins"));
          oNFM.campo53 = FormatDecimal2R(rst.getDouble("valorbasepiscofins"));
        } 
        oNFM.campo60 = FormatDecimal2R(rst.getDouble("valorcofins"));
        oNFM.campo61 = FormatDecimal2R(rst.getDouble("valorpis"));
      } 
      oNFM.campo16 = "";
      oNFM.campo17 = "";
      oNFM.campo18 = "";
      oNFM.campo19 = "";
      oNFM.campo20 = "";
      oNFM.campo21 = "";
      oNFM.campo22 = "";
      oNFM.campo23 = "";
      oNFM.campo24 = "";
      oNFM.campo25 = "";
      oNFM.campo27 = FormatDecimal2R(rst.getDouble("valorfrete"));
      oNFM.campo29 = FormatDecimal2R(rst.getDouble("valoroutrasdespesas"));
      oNFM.campo30 = "";
      oNFM.campo31 = "";
      oNFM.campo32 = (rst.getInt("id_situacaonfe") == SituacaoNfe.CANCELADA.getId() || rst.getInt("id_situacaonfe") == SituacaoNfe.INUTILIZADA.getId()) ? "" : FormatDecimal2R(rst.getDouble("valoripi"));
      oNFM.campo33 = "0.00";
      oNFM.campo34 = "0.00";
      oNFM.campo35 = FormatDecimal2R(rst.getDouble("valordescontofiscal"));
      oNFM.campo38 = "";
      oNFM.campo39 = "";
      oNFM.campo40 = "";
      if (rst.getInt("id_situacaonfe") != SituacaoNfe.CANCELADA.getId() && rst.getInt("id_situacaonfe") != SituacaoNfe.INUTILIZADA.getId()) {
        oNFM.campo41 = FormatDecimal2R(rst.getDouble("valoricmssubstituicao"));
        oNFM.campo42 = FormatDecimal2R(rst.getDouble("valorbasesubstituicao"));
        oNFM.campo43 = "0.00";
        oNFM.campo44 = "";
        oNFM.campo45 = "";
        oNFM.campo46 = "";
        oNFM.campo47 = "";

        int paymentIndicator = rst.getInt("id_indicadorpagamento");

        switch (paymentIndicator) {
          case 0:
            oNFM.campo49 = "V";
            break;
          case 1:
            oNFM.campo49 = "P";
            break;
          case 2:
            oNFM.campo49 = "V";
            break;
          case 9:
            oNFM.campo49 = "N";
            break;
        } 

        oNFM.campo50 = "";
        oNFM.campo51 = "";
        oNFM.campo54 = "";
        oNFM.campo55 = "";
        oNFM.campo56 = "";
        oNFM.campo57 = "";
        oNFM.campo58 = "";
        oNFM.campo59 = "";
        oNFM.campo62 = "0.00";
        oNFM.campo63 = "0.00";
        oNFM.campo64 = "";
        oNFM.campo65 = rst.getString("observacao").replace("\n", " ").replace("|", " ");
        oNFM.campo66 = "";
      } 
      oNFM.campo67 = rst.getString("chavenfe");
      oNFM.campo68 = (rst.getInt("id_situacaonfe") != SituacaoNfe.CANCELADA.getId()) ? "0.00" : "";
      oNFM.campo69 = "";
      oNFM.campo70 = "";
      oNFM.campo71 = Format.number(rst.getString("cfop").replace(".", ""), 8);
      oNFM.campo72 = "";
      oNFM.campo73 = "";
      oNFM.campo74 = "";
      oNFM.campo75 = "";
      oNFM.campo76 = "";
      oNFM.campo77 = "";
      oNFM.campo78 = "";
      oNFM.campo79 = "";
      oNFM.campo80 = "N";
      if (rst.getInt("id_tipoentradasaida") == TipoEntradaSaida.SAIDA.getId()) {
        oNFM.campo81 = "1";
      } else {
        oNFM.campo81 = "";
      } 
      oNFM.campo82 = "";
      oNFM.campo83 = "";
      oNFM.campo84 = "";
      oNFM.campo85 = "";
      oNFM.campo86 = "";
      oNFM.campo87 = "";
      oNFM.campo88 = "";
      oNFM.campo89 = Format.data(Format.dataGUI(rst.getDate("dataemissao")), "dd/MM/yyyy", "yyyyMMdd");
      oNFM.campo90 = "";
      oNFM.campo91 = "N";

      i_exportacao.qtdRegistro++;
      i_arquivo.write(oNFM.getStringLayout158());
      if (oNFM.campo13.equals("0")) {
        sql = new StringBuilder();
        sql.append("SELECT e.id, ei.id_produto,ei.cfop, COALESCE(ei.id_tipoorigemmercadoria, p.id_tipoorigemmercadoria) AS id_tipoorigemmercadoria, ei.quantidade,ei.valortotal, ei.valoripi,");
      
        if (rst.getInt("id_tipoentradasaida") == TipoEntradaSaida.SAIDA.getId()) {
          sql.append(" COALESCE(tn.geradevolucao,false) as geradevolucao,");
        } else {
          sql.append(" false as geradevolucao,");
        }

        sql.append("ei.id_aliquota, ei.situacaotributaria,a.reduzido,ei.valorbasesubstituicao, (ei.valoricmssubstituicao + COALESCE(ei.valorfcpst, 0)) AS valoricmssubstituicao, p.tiponaturezareceita, ei.valorbasecalculo, ");
        sql.append("(a.porcentagem + COALESCE(a.porcentagemfcp, 0)) AS porcentagem,ROUND((ei.valortotal - (ei.valortotal * a.reduzido) / 100),2) AS base,");
        sql.append("ROUND((ei.valortotal - (ei.valortotal * a.reduzido) / 100) * ((a.porcentagem + COALESCE(a.porcentagemfcp, 0)) / 100),2) AS icms, ROUND(ei.valoripi + (ei.valortotal - (ei.valortotal * a.reduzido) / 100),3) AS total,");
        sql.append("CASE WHEN ei.quantidade = 0 THEN 0 ELSE ROUND(ei.valortotal / ei.quantidade,3) end AS valorunitario, tpc.tipocredito, tpc.cst AS cstpiscofins,");
        sql.append(" tpc.valorpis,tpc.valorcofins,");
        sql.append(" tpc.valorpis AS aliquotapis, tpc.valorcofins AS aliquotacofins, ");
        sql.append(" CASE WHEN tpc.valorpis <> 0 THEN ROUND((ei.valorbasecalculo + ei.valorisento + ei.valoroutras + (CASE WHEN e.aplicaicmsipi = FALSE THEN ei.valoripi ELSE 0 END) - ei.valordesconto), 2) ELSE 0 END AS valorbasepiscofins,");
        sql.append(" ei.valorfrete, ei.valorseguro, ei.valordesconto, ei.valoroutras, ei.valoroutrasdespesas, te.descricao AS tipoembalagem, tst.percentualmva, ROUND(ei.valortotal, 2) AS totalpiscofins,");
        sql.append(" (COALESCE(aorigem.porcentagem, 0) + COALESCE(aorigem.porcentagemfcp, 0)) AS aliq_orig_perc, (COALESCE(adestino.porcentagem, 0) + COALESCE(adestino.porcentagemfcp, 0)) AS aliq_dest_perc,");
        sql.append(" tn.notaprodutor, e.id_tiposaida, tnn.contabilidadepadrao, tnn.contabilidadepadrao, a.porcentagemfcp, a.csosn");
        sql.append(" FROM escrita AS e");
        sql.append(" INNER JOIN escritaitem AS ei ON e.id = ei.id_escrita");
        sql.append(" INNER JOIN aliquota AS a ON a.id = ei.id_aliquota");
        sql.append(" LEFT JOIN aliquota AS aorigem ON aorigem.id = ei.id_aliquotainterestadual");
        sql.append(" LEFT JOIN aliquota AS adestino ON adestino.id = ei.id_aliquotadestino");
        sql.append(" INNER JOIN produto AS p ON p.id = ei.id_produto");
        sql.append(" INNER JOIN tipoembalagem AS te ON te.id = p.id_tipoembalagem");
        sql.append(" INNER JOIN tipopiscofins AS tpc ON tpc.id = ei.id_tipopiscofins");
        sql.append(" LEFT JOIN tabelasubstituicaotributaria AS tst ON tst.id_aliquota = ei.id_aliquota AND tst.id_estado = e.id_estado");
        
        if (rst.getInt("id_tipoentradasaida") == TipoEntradaSaida.SAIDA.getId()) {
          sql.append(" LEFT JOIN tiposaida AS tn ON tn.id = ei.id_tiposaida");
          sql.append(" INNER JOIN tiposaida AS tnn ON tnn.id = ei.id_tiposaida");
        } else {
          sql.append(" LEFT JOIN tipoentrada AS tn ON tn.id = ei.id_tipoentrada");
          sql.append(" INNER JOIN tipoentrada AS tnn ON tnn.id = ei.id_tipoentrada");
        } 

        sql.append(" WHERE ei.id_escrita = " + rst.getLong("id"));
        sql.append(" AND ei.cfop = '" + rst.getString("cfop") + "'");
        rstProduto = stmProduto.executeQuery(sql.toString());
        while (rstProduto.next()) {
          FortesPNMVO oPNM = new FortesPNMVO();
          oPNM.campo1 = "PNM";
          oPNM.campo2 = String.valueOf(rstProduto.getInt("id_produto"));
          oPNM.campo3 = rstProduto.getString("cfop").replace(".", "").replace(",", ".");
          oPNM.campo4 = "";
                   
          switch (oFornecedor.idTipoEmpresa) {
            case 8:
            case 9:
            case 10:
            case 11:
              break;
            default:
              oPNM.campo5 = rstProduto.getString("id_tipoorigemmercadoria");
              oPNM.campo6 = Format.number(rstProduto.getInt("situacaotributaria"), 2);
          }
          
          oPNM.campo7 = rstProduto.getString("tipoembalagem");
          oPNM.campo8 = FormatDecimal2R(rstProduto.getDouble("quantidade"));
          oPNM.campo9 = FormatDecimal2R(rstProduto.getDouble("valortotal"));
          oPNM.campo10 = FormatDecimal2R(rstProduto.getDouble("valoripi"));
          
          if (((AliquotaDAO)VRInstance.criar(AliquotaDAO.class)).isIsento(rstProduto.getInt("situacaotributaria"))) {
            oPNM.campo11 = "2";
            oPNM.campo17 = "";
            oPNM.campo19 = "0.00";
          } 
          
          if (((AliquotaDAO)VRInstance.criar(AliquotaDAO.class)).isOutras(rstProduto.getInt("situacaotributaria"))) {
            oPNM.campo11 = "3";
            oPNM.campo17 = "";
            oPNM.campo19 = "0.00";
          }

          if (rstProduto.getInt("situacaotributaria") == SituacaoTributaria.ISENTO_ICMS_ST.getId()) {
            oPNM.campo11 = "3";
            oPNM.campo17 = "";
            oPNM.campo19 = "0.00";
          } else if (rstProduto.getInt("situacaotributaria") == SituacaoTributaria.ISENTO_ICMS_ST.getId()) {
            oPNM.campo11 = "3";
            oPNM.campo17 = "1";
            oPNM.campo19 = FormatDecimal2R(rstProduto.getDouble("percentualmva"));
          } else {
            oPNM.campo11 = "1";
            oPNM.campo17 = "";
            oPNM.campo19 = "0.00";
          } 
          if (((AliquotaDAO)VRInstance.criar(AliquotaDAO.class)).isSubstituido(rstProduto.getInt("situacaotributaria")) || ((AliquotaDAO)VRInstance.criar(AliquotaDAO.class)).isIsento(rstProduto.getInt("situacaotributaria"))) {
            oPNM.campo12 = FormatDecimal2R(0.0D);
          } else {
            oPNM.campo12 = FormatDecimal2R(rstProduto.getDouble("base"));
          }

          oPNM.campo13 = FormatDecimal2R(rstProduto.getDouble("porcentagem"));
          oPNM.campo14 = "";
          oPNM.campo15 = "";
          oPNM.campo16 = "0";
          oPNM.campo18 = "";
          oPNM.campo20 = "0.00";
          oPNM.campo21 = "";
          oPNM.campo22 = "0.00";
          oPNM.campo23 = "0.00";
          oPNM.campo24 = "";
          oPNM.campo25 = "0.00";
          oPNM.campo26 = "";
          oPNM.campo27 = "0.00";
          oPNM.campo28 = "0.00";
          oPNM.campo29 = "0.00";
          oPNM.campo30 = "0.00";
          oPNM.campo31 = "";
          oPNM.campo32 = "";
          oPNM.campo33 = "";
          oPNM.campo34 = "";
          oPNM.campo35 = "";
          oPNM.campo36 = "";
          boolean isEntrada = false;
          if (!rstProduto.getBoolean("geradevolucao")) {
            if ("5.910,6.910,5.927,6.927,5.949,6.949,5.411,6.411,5.202,6.202,5.929,6.929,5.912,6.912".contains(rstProduto.getString("cfop"))) {
               if (rst.getInt("id_tipoentradasaida") == TipoEntradaSaida.SAIDA.getId()) {
                oPNM.campo37 = "49";
                oPNM.campo38 = "49";
               } else {
                oPNM.campo37 = "99";
                oPNM.campo38 = "99";
               }
            } else {
              oPNM.campo37 = Format.number(rstProduto.getInt("cstpiscofins"), 2);
              oPNM.campo38 = Format.number(rstProduto.getInt("cstpiscofins"), 2);
            } 
          } else if ("1.411,1.202,2.411,2.202".contains(rstProduto.getString("cfop"))) {
            isEntrada = true;
            oPNM.campo37 = Format.number(rstProduto.getInt("cstpiscofins"), 2);
            oPNM.campo38 = Format.number(rstProduto.getInt("cstpiscofins"), 2);
          } else {
            oPNM.campo37 = Format.number(this.oFortesDAO.converteCstPisCofins(rstProduto.getInt("cstpiscofins")), 2);
            oPNM.campo38 = Format.number(this.oFortesDAO.converteCstPisCofins(rstProduto.getInt("cstpiscofins")), 2);
          }

          String netValue = FormatDecimal2R(
              Double.parseDouble(oPNM.campo9) +
              rstProduto.getDouble("valoroutrasdespesas") +
              rstProduto.getDouble("valorseguro") +
              rstProduto.getDouble("valorfrete") -
              rstProduto.getDouble("valordesconto")
          );
          
          if (oPNM.campo37.equals("07") || oPNM.campo37.equals("49")) {
            oPNM.campo39 = "";
          } else {
            oPNM.campo39 = netValue;
          }

          if (oPNM.campo38.equals("07") || oPNM.campo38.equals("49")) {
            oPNM.campo40 = "";
          } else {
            oPNM.campo40 = netValue;
          }

          oPNM.campo41 = FormatDecimal2R(rstProduto.getDouble("valorfrete"));
          oPNM.campo42 = FormatDecimal2R(rstProduto.getDouble("valorseguro"));
          oPNM.campo43 = FormatDecimal2R(rstProduto.getDouble("valordesconto"));
          oPNM.campo44 = netValue;
          
          int[] arrayCstNaturezaReceita = { 2, 3, 4, 5, 6, 7, 8, 9 };
            
          if (ArrayUtilsContains(arrayCstNaturezaReceita, Integer.parseInt(oPNM.campo37))) {
            String code = getCodigoACFiscal(rstProduto.getInt("tiponaturezareceita"), rstProduto.getInt("cstpiscofins"));
            oPNM.campo45 = code;
            oPNM.campo46 = code;
            oPNM.campo47 = (verificaProdepe(rstProduto.getInt("id")) == true) ? code : "";
          }

          oPNM.campo48 = "";

          switch (oFornecedor.idTipoEmpresa) {
            case 8:
            case 9:
            case 10:
            case 11:
              oPNM.campo45 = "";
              oPNM.campo46 = "";
              oPNM.campo49 = Format.number(rstProduto.getInt("id_tipoorigemmercadoria"), 1);
              oPNM.campo50 = Format.number(rstProduto.getInt("csosn"), 3);
              break;
            default:
              oPNM.campo49 = "";
              oPNM.campo50 = "";
          }

          oPNM.campo51 = "1";
          oPNM.campo52 = "0.00";
          oPNM.campo54 = "0.00";
          oPNM.campo56 = "0.00";
          oPNM.campo58 = "0.00";
          if (((oFornecedor.idTipoEmpresa == TipoEmpresa.LUCRO_REAL.getId() || oFornecedor.idTipoEmpresa == TipoEmpresa.LUCRO_PRESUMIDO.getId()) && this.oFortesDAO
            .isFornecedorDistribuidor(i_exportacao.idLoja)) || rstProduto
            .getString("cfop").startsWith("3.")) {
            int[] arrayAliquotaPisCofins = { 3, 4, 6, 73 };
            if (ArrayUtilsContains(arrayAliquotaPisCofins, rstProduto.getInt("cstpiscofins"))) {
              oPNM.campo52 = Format.decimal4(rstProduto.getDouble("aliquotacofins"));
              oPNM.campo56 = Format.decimal4(rstProduto.getDouble("aliquotapis"));
            } 
            int[] arrayValorPisCofins = { 4, 6, 73 };
            if (ArrayUtilsContains(arrayValorPisCofins, rstProduto.getInt("cstpiscofins"))) {
              oPNM.campo54 = FormatDecimal2R(rstProduto.getDouble("valorcofins") * rstProduto.getDouble("aliquotacofins") / 100.0D);
              oPNM.campo58 = FormatDecimal2R(rstProduto.getDouble("valorpis") * rstProduto.getDouble("valorpis") / 100.0D);
            } 
          } 
          oPNM.campo53 = "";
          oPNM.campo55 = "1";
          oPNM.campo57 = "";
          oPNM.campo59 = "";
          oPNM.campo60 = "";
          oPNM.campo61 = "";
          oPNM.campo62 = FormatDecimal2R(rstProduto.getDouble("valoroutrasdespesas"));
          int idContaContabilCredito = this.oParametroContabilidadeDAO.carregarContaContabilTiposaida(rstProduto.getInt("id_tiposaida"));
          String contaExterna = getContaContabil(idContaContabilCredito);
          oPNM.campo63 = contaExterna;
          oPNM.campo64 = "0";
          oPNM.campo65 = "";
          oPNM.campo66 = "";

          if (rstProduto.getBoolean("geradevolucao")) {
            if (Double.parseDouble(oPNM.campo58) > 0.0D && oPNM.campo37.equals("49")) {
              oPNM.campo67 = Format.number(rstProduto.getString("tipocredito"), 3);
              oPNM.campo68 = Format.number(rstProduto.getString("tipocredito"), 3);
            } else {
              oPNM.campo67 = "";
              oPNM.campo68 = "";
            } 
          } else {
            oPNM.campo67 = "";
            oPNM.campo68 = "";
          }

          oPNM.campo69 = "";
          oPNM.campo70 = "";
          oPNM.campo71 = "";
          oPNM.campo72 = "";
          oPNM.campo73 = "";
          if (oFornecedor.idEstado == TipoEstado.PE.getId()) {
            oPNM.campo74 = "1";
          } else {
            oPNM.campo74 = "";
          } 
          oPNM.campo75 = "";
          oPNM.campo76 = "";
          if (i_exportacao.tipoData == TipoData.EMISSAO.getId()) {
            oPNM.campo77 = FormatDecimal2R(rstProduto.getDouble("valortotal"));
            if (rstProduto.getDouble("valortotal") > 0.0D && rstProduto.getDouble("porcentagemfcp") >= 2.0D)
              oPNM.campo78 = FormatDecimal2R(rstProduto.getDouble("porcentagemfcp")); 
            oPNM.campo79 = FormatDecimal2R(rstProduto.getDouble("aliq_orig_perc"));
            oPNM.campo80 = FormatDecimal2R(rstProduto.getDouble("aliq_dest_perc"));
          } 
          if (rstProduto.getString("cfop").equals("5.401") || rstProduto
            .getString("cfop").equals("5.402") || rstProduto
            .getString("cfop").equals("5.403") || rstProduto
            .getString("cfop").equals("5.405") || rstProduto
            .getString("cfop").equals("6.401") || rstProduto
            .getString("cfop").equals("6.403") || rstProduto
            .getString("cfop").equals("6.404")) {
            oPNM.campo81 = "N";
          } else if (rstProduto.getDouble("porcentagemfcp") > 0.0D) {
            oPNM.campo81 = "S";
          } else {
            oPNM.campo81 = "N";
          } 
          oPNM.campo82 = "";
          ParametroDAO oParametroDAO = (ParametroDAO)VRInstance.criar(ParametroDAO.class);
          if (oParametroDAO.get(510, i_exportacao.idLoja).getBoolean())
            oPNM.campo83 = FormatDecimal2R(rstProduto.getDouble("icms")); 
          oPNM.campo85 = "";
          oPNM.campo86 = "";
          oPNM.campo87 = "";
          oPNM.campo88 = "";
          oPNM.campo89 = "";
          oPNM.campo90 = "";
          oPNM.campo91 = "N";
          oPNM.campo92 = "";
          oPNM.campo93 = "";
          oPNM.campo94 = "";
          oPNM.campo95 = "";
          oPNM.campo96 = "";
          oPNM.campo97 = "";
          oPNM.campo98 = "";
          oPNM.campo99 = "";
          oPNM.campo100 = "";
          oPNM.campo101 = "";
          oPNM.campo102 = "";
          oPNM.campo103 = "";
          oPNM.campo104 = "";
          oPNM.campo105 = "";
          oPNM.campo106 = "";
          oPNM.campo107 = "";
          oPNM.campo108 = "";
          oPNM.campo108 = "";
          oPNM.campo109 = "";
          oPNM.campo110 = "";
          oPNM.campo111 = "";
          oPNM.campo112 = "";
          oPNM.campo113 = "";
          oPNM.campo114 = "";
          oPNM.campo115 = "";
          oPNM.campo116 = "";
          int[] aCstPisCofinsSaida = { 49, 98, 99 };

          if (ArrayUtilsContains(aCstPisCofinsSaida, rstProduto.getInt("cstpiscofins"))) {
            oPNM.campo39 = "";
            oPNM.campo40 = "";
          }

          oPNM.campo117 = "";
          oPNM.campo118 = "";
          oPNM.campo119 = "";

          if (rstProduto.getInt("situacaotributaria") == 51 && rstProduto.getDouble("valorbasecalculo") > 0) {
            oPNM.campo120 = FormatDecimal2R(rstProduto.getDouble("porcentagem"));
          }

          i_exportacao.qtdRegistro++;
          i_arquivo.write(oPNM.getStringLayout160());
        }

        sql = new StringBuilder();
        sql.append("SELECT SUM(ei.valorbasecalculo + ei.valorisento + ei.valoroutras) AS valortotal, es.sigla AS uf, ei.cfop, SUM(ei.valorbasecalculo) AS basecalculoicms, (a.porcentagem + COALESCE(a.porcentagemfcp, 0)) AS aliquotaicms, SUM(ei.valoricms + COALESCE(ei.valorfcp, 0)) AS valoricms,");
        sql.append("SUM(ei.valorisento) AS valorisento, SUM(ei.valoroutras) AS valoroutras, COALESCE(ei.id_tipoorigemmercadoria, p.id_tipoorigemmercadoria) AS id_tipoorigemmercadoria, ei.situacaotributaria, tpc.cst,");
        sql.append(" SUM(ei.valorbasecalculo + ei.valorisento + ei.valorfrete + ei.valoroutras - ei.valordesconto) valortotaloperacao, a.porcentagemfcp, (a.porcentagemfinal + COALESCE(a.porcentagemfcp, 0)) AS porcentagemfinal, es.id AS id_uf");
        sql.append(" FROM escrita AS e");
        sql.append(" INNER JOIN escritaitem AS ei ON ei.id_escrita = e.id");
        sql.append(" INNER JOIN aliquota AS a ON a.id = ei.id_aliquota");
        sql.append(" INNER JOIN produto AS p ON p.id = ei.id_produto");
        sql.append(" LEFT JOIN fornecedor AS f ON f.id = e.id_fornecedor");
        sql.append(" LEFT JOIN clienteeventual AS ce ON ce.id = e.id_clienteeventual");
        sql.append(" INNER JOIN estado AS es ON es.id =  COALESCE(f.id_estado, ce.id_estado)");
        sql.append(" INNER JOIN tipopiscofins AS tpc ON tpc.id = ei.id_tipopiscofins");
        sql.append(" WHERE e.id = " + rst.getLong("id"));
        sql.append(" AND ei.cfop = '" + rst.getString("cfop") + "'");
        sql.append(" GROUP BY ei.cfop, es.sigla, (a.porcentagem + COALESCE(a.porcentagemfcp, 0)), COALESCE(ei.id_tipoorigemmercadoria, p.id_tipoorigemmercadoria), ei.situacaotributaria, tpc.cst, a.porcentagemfcp, (a.porcentagemfinal + COALESCE(a.porcentagemfcp, 0)), id_uf");
        rstImposto = stmImposto.executeQuery(sql.toString());
        while (rstImposto.next()) {
          FortesINMVO oINM = new FortesINMVO();
          oINM.campo1 = "INM";
          oINM.campo2 = FormatDecimal2R(rstImposto.getDouble("valortotaloperacao"));
          oINM.campo3 = rstImposto.getString("uf");
          oINM.campo4 = rstImposto.getString("cfop").replace(".", "").replace(",", ".");
          oINM.campo5 = "";
          oINM.campo6 = FormatDecimal2R(rstImposto.getDouble("basecalculoicms"));
          oINM.campo7 = FormatDecimal2R(rstImposto.getDouble("aliquotaicms"));
          oINM.campo8 = FormatDecimal2R(rstImposto.getDouble("valoricms"));
          oINM.campo9 = FormatDecimal2R(rstImposto.getDouble("valorisento"));
          oINM.campo10 = FormatDecimal2R(rstImposto.getDouble("valoroutras"));
          oINM.campo11 = "0.00";
          oINM.campo12 = "0.00";
          oINM.campo13 = "0.00";
          oINM.campo14 = "0.00";
          oINM.campo15 = "";
          oINM.campo16 = "";
          oINM.campo17 = "";
          oINM.campo18 = "";
          oINM.campo19 = rstImposto.getString("id_tipoorigemmercadoria");
          oINM.campo20 = Format.number(rstImposto.getInt("situacaotributaria"), 2);
          oINM.campo21 = "";
          oINM.campo22 = "";
          oINM.campo23 = "";
          if (rst.getInt("id_tipocrt_loja") == TipoCrt.SIMPLES_NACIONAL.getId() && (
            (TipoPisCofinsDAO)VRInstance.criar(TipoPisCofinsDAO.class)).isMonofasico(rstImposto.getInt("cst"))) {
            oINM.campo24 = "S";
            oINM.campo25 = "S";
          } else {
            oINM.campo24 = "N";
            oINM.campo25 = "N";
          } 
          if (rstImposto.getString("cfop").equals("5.401") || rstImposto
            .getString("cfop").equals("5.402") || rstImposto
            .getString("cfop").equals("5.403") || rstImposto
            .getString("cfop").equals("5.405") || rstImposto
            .getString("cfop").equals("6.401") || rstImposto
            .getString("cfop").equals("6.403") || rstImposto
            .getString("cfop").equals("6.404")) {
            oINM.campo26 = "N";
          } else if (oINM.campo15.equals("N") && rstImposto.getDouble("porcentagemfcp") > 0.0D) {
            oINM.campo26 = "S";
          } else {
            oINM.campo26 = "N";
          }

          if (rstImposto.getInt("id_uf") == 23 && rstImposto.getDouble("porcentagemfinal") == 7.0D) {
            oINM.campo27 = "1";
          } else if (rstImposto.getInt("id_uf") == 23 && rstImposto.getDouble("porcentagemfinal") == 12.0D) {
            oINM.campo27 = "2";
          } else if (rstImposto.getInt("id_uf") == 23 && rstImposto.getDouble("porcentagemfinal") == 18.0D) {
            oINM.campo27 = "3";
          } else if (rstImposto.getInt("id_uf") == 23 && rstImposto.getDouble("porcentagemfinal") == 25.0D) {
            oINM.campo27 = "4";
          } else {
            oINM.campo27 = "";
          }

          if (
            rstImposto.getString("cfop").startsWith("1") ||
            rstImposto.getString("cfop").startsWith("2") ||
            rstImposto.getString("cfop").startsWith("3")
          ) {
            oINM.campo27 = "";
          }

          if (oFornecedor.idEstado != 23) {
            oINM.campo27 = "";
          }

          i_exportacao.qtdRegistro++;
          i_arquivo.write(oINM.getStringLayout158());
        } 
        if (i_exportacao.notaFiscalMercadoriaOutrosValores) {
          FortesOVNVO oOVN = new FortesOVNVO();
          oOVN.campo1 = "OVN";
          oOVN.campo2 = "DINHEIRO";
          oOVN.campo3 = FormatDecimal2R(rst.getDouble("valortotal"));
          i_exportacao.qtdRegistro++;
          i_arquivo.write(oOVN.getString());
        } 
        ProgressBar.next();
      } 
    } 
    stmProduto.close();
    stm.close();
  }
    
  private void exportarConhecimentoTransporteCarga(ExportarFortesVO i_exportacao, Arquivo i_arquivo) throws Exception {
    VRStatement stm = null;
    ResultSet rst = null;
    StringBuilder sql = null;
    stm = VRStatement.createStatement();
    ProgressBar.setStatus("Exportando Conhecimentos de Transporte de Carga...");
    FornecedorVO oFornecedor = ((FornecedorDAO)VRInstance.criar(FornecedorDAO.class)).carregar((((LojaDAO)VRInstance.criar(LojaDAO.class)).carregar(i_exportacao.idLoja)).idFornecedor);
    sql = new StringBuilder();
    sql.append("SELECT item.cfop, escopo.id_fornecedor, escopo.serie, escopo.numeronota, escopo.dataemissao, escopo.data, item.id_aliquota, item.id_produto, escopo.modelo, ");
    sql.append("(aliquota.porcentagemfinal + COALESCE(aliquota.porcentagemfcp, 0)) AS porcentagemfinal, COALESCE(escopo.chavenfe, '') AS chavenfe, COALESCE(escopo.informacaocomplementar, '') AS informacaocomplementar, ");
    sql.append("item.situacaotributaria, f.id_estado, est.sigla as uf, f.id_municipio, ");
    sql.append("(CASE escopo.id_tipofretenotafiscal WHEN 0 THEN 'T' WHEN 1 THEN 'C' WHEN 2 THEN 'F' WHEN 3 THEN 'S' ELSE '' END) tipofrete, ");
    sql.append(" ROUND(SUM(item.valorbasecalculo + item.valorisento + item.valoroutras + (CASE WHEN escopo.aplicaicmsipi = FALSE THEN item.valoripi ELSE 0 END) + item.valoricmssubstituicao + COALESCE(item.valorfcpst, 0) + item.valoroutrasdespesas + item.valorfrete - item.valordesconto), 2) AS valorcontabil,");
    sql.append(" COALESCE(SUM(ROUND(CASE WHEN (item.situacaotributaria = " + SituacaoTributaria.SUBSTITUIDO.getId() + " OR item.situacaotributaria = " + SituacaoTributaria.REDUCAO_BASE_CALCULO_ICMS_ST.getId() + " OR item.situacaotributaria = " + SituacaoTributaria.TRIBUTADO_ICMS_ST.getId() + " OR item.situacaotributaria = " + SituacaoTributaria.ISENTO_ICMS_ST.getId() + " OR cf.geraicms = FALSE) THEN 0 ELSE item.valorbasecalculo END)),0) AS baseicms, ");
    sql.append(" COALESCE(SUM(ROUND(CASE WHEN (item.situacaotributaria = " + SituacaoTributaria.SUBSTITUIDO.getId() + " OR item.situacaotributaria = " + SituacaoTributaria.REDUCAO_BASE_CALCULO_ICMS_ST.getId() + " OR item.situacaotributaria = " + SituacaoTributaria.TRIBUTADO_ICMS_ST.getId() + " OR item.situacaotributaria = " + SituacaoTributaria.ISENTO_ICMS_ST.getId() + " OR cf.geraicms = FALSE) THEN 0 ELSE item.valoricms + COALESCE(item.valorfcp, 0) END)),0) AS icms, ");
    sql.append("item.valorisento, item.valoroutras, item.valortotal, ");
    sql.append("(origem.porcentagemfinal + COALESCE(origem.porcentagemfcp, 0)) AS aliquotaorigem, (aloja.porcentagemfinal + COALESCE(aloja.porcentagemfcp, 0)) AS aliquotaloja, ");
    sql.append("piscofins.tipocredito, f.id_tipoempresa, tipoentrada.id_contacontabilfiscalcredito, ");
    sql.append("(CASE WHEN f.id_tipoempresa = 3 THEN tipoentrada.id_tipobasecalculocredito ELSE null END) AS id_tipobasecalculocredito, tipoempresa.id_tipocrt, escopo.observacao, ");
    sql.append("(CASE WHEN f.id_tipoempresa = 1 OR  f.id_tipoempresa = 3 THEN piscofins.cst ELSE null END) as cst, ");
    sql.append("(CASE WHEN f.id_tipoempresa = 3 THEN piscofins.cst ELSE null END) as cstcumulativo, ");
    if (oFornecedor.idTipoEmpresa == TipoEmpresa.LUCRO_PRESUMIDO.getId()) {
      sql.append(" CASE WHEN piscofins.valorpispresumido <> 0 THEN ROUND((item.valorbasecalculo + item.valorisento + item.valoroutras + (CASE WHEN escopo.aplicaicmsipi = FALSE THEN item.valoripi ELSE 0 END) - item.valordesconto) * ((100 - piscofins.reduzidocreditopresumido) / 100), 2) ELSE 0 END AS basepiscofins,");
    } else {
      sql.append(" CASE WHEN piscofins.valorpis <> 0 THEN ROUND((item.valorbasecalculo + item.valorisento + item.valoroutras + (CASE WHEN escopo.aplicaicmsipi = FALSE THEN item.valoripi ELSE 0 END) - item.valordesconto) * ((100 - piscofins.reduzidocredito) / 100), 2) ELSE 0 END AS basepiscofins,");
    } 
    sql.append(" piscofins.cst cst, item.tiponaturezareceita, escopo.id_tipofretenotafiscal, (COALESCE(aorigem.porcentagem, 0) + COALESCE(aorigem.porcentagemfcp, 0)) AS aliq_orig_perc, (COALESCE(adestino.porcentagem, 0) + COALESCE(adestino.porcentagemfcp, 0)) AS aliq_dest_perc, aliquota.porcentagemfcp,");
    sql.append(" ndi.basecalculo AS baseinss, ndi.aliquota AS aliquotainss, ndi.valor AS inss, escopo.id_indicadorpagamento");
    sql.append(" FROM escrita AS escopo");
    sql.append(" INNER JOIN escritaitem AS item ON escopo.id = item.id_escrita");
    sql.append(" INNER JOIN fornecedor f ON f.id = escopo.id_fornecedor");
    sql.append(" INNER JOIN estado est ON est.id = f.id_estado");
    sql.append(" INNER JOIN tipoentrada ON tipoentrada.id = escopo.id_tipoentrada");
    sql.append(" INNER JOIN aliquota ON aliquota.id = item.id_aliquota");
    sql.append(" LEFT JOIN aliquota AS aorigem ON aorigem.id = item.id_aliquotainterestadual");
    sql.append(" LEFT JOIN aliquota AS adestino ON adestino.id = item.id_aliquotadestino");
    sql.append(" INNER JOIN cfop AS cf ON cf.cfop = item.cfop");
    sql.append(" INNER JOIN produtoaliquota pa ON pa.id_produto = item.id_produto and pa.id_estado = escopo.id_estado");
    sql.append(" INNER JOIN aliquota origem ON origem.id = pa.id_aliquotacredito");
    sql.append(" INNER JOIN produtoaliquota paloja ON paloja.id_produto = item.id_produto and paloja.id_estado = " + oFornecedor.idEstado);
    sql.append(" INNER JOIN aliquota aloja ON aloja.id = paloja.id_aliquotacredito");
    sql.append(" INNER JOIN tipopiscofins AS piscofins ON piscofins.id = item.id_tipopiscofins");
    sql.append(" INNER JOIN tipoempresa ON tipoempresa.id = f.id_tipoempresa");
    sql.append(" LEFT JOIN notadespesaimposto AS ndi ON escopo.id_notadespesa = ndi.id_notadespesa AND ndi.id_tipoimpostodespesa = " + TipoImpostoDespesa.INSS.getId());
    if (i_exportacao.tipoData == TipoData.ENTRADA.getId()) {
      sql.append(" WHERE escopo.data BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    } else if (i_exportacao.tipoData == TipoData.EMISSAO.getId()) {
      sql.append(" WHERE escopo.dataemissao BETWEEN  '" + Format.dataBanco(i_exportacao.dataInicio) + "' AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    } 
    sql.append(" AND escopo.id_loja = " + i_exportacao.idLoja);
    sql.append(" AND escopo.modelo IN ('" + ModeloNotaFiscal.CTRC.getModelo() + "', '" + ModeloNotaFiscal.TRANSPORTE.getModelo() + "', '" + ModeloNotaFiscal.CTE.getModelo() + "')");
    sql.append(" GROUP BY item.cfop, escopo.id_fornecedor, escopo.serie, escopo.numeronota, escopo.id_indicadorpagamento ");
    sql.append(",escopo.dataemissao, escopo.data, item.id_aliquota, item.id_produto ");
    sql.append(",escopo.modelo, (aliquota.porcentagemfinal + COALESCE(aliquota.porcentagemfcp, 0)), COALESCE(escopo.chavenfe, '') ");
    sql.append(",COALESCE(escopo.informacaocomplementar, '') ");
    sql.append(",item.situacaotributaria, escopo.id_tipofretenotafiscal ");
    sql.append(",item.valorisento,item.valoroutras, item.valortotal, f.id_estado,est.sigla,f.id_municipio, ");
    sql.append("(origem.porcentagemfinal + COALESCE(origem.porcentagemfcp, 0)), (aloja.porcentagemfinal + COALESCE(aloja.porcentagemfcp, 0)), ");
    sql.append("piscofins.cst, f.id_tipoempresa, piscofins.tipocredito, ");
    sql.append("tipoentrada.id_contacontabilfiscalcredito, ");
    if (oFornecedor.idTipoEmpresa == TipoEmpresa.LUCRO_PRESUMIDO.getId()) {
      sql.append(" piscofins.valorpispresumido, piscofins.reduzidocreditopresumido, ");
    } else {
      sql.append(" piscofins.valorpis, piscofins.reduzidocredito, ");
    } 
    sql.append("item.valorbasecalculo, escopo.aplicaicmsipi, item.valoripi,  ");
    sql.append("item.valordesconto,  ");
    sql.append("tipoentrada.id_tipobasecalculocredito, tipoempresa.id_tipocrt, escopo.observacao, ");
    sql.append("piscofins.cst, item.tiponaturezareceita, escopo.id_tipofretenotafiscal, ");
    sql.append("aliq_orig_perc, aliq_dest_perc, aliquota.porcentagemfcp, ndi.basecalculo, ndi.aliquota, ndi.valor");
    sql.append(" ORDER BY escopo.id_fornecedor,escopo.numeronota ");
    rst = stm.executeQuery(sql.toString());
    while (rst.next()) {
      FortesCTCVO oCtc = new FortesCTCVO();
      oCtc.campo1 = "CTC";
      oCtc.campo2 = Format.number(this.oFortesDAO.getCodigoEstabelecimento(i_exportacao.idLoja), 4);
      oCtc.campo3 = "E";
      oCtc.campo4 = rst.getString("chavenfe").trim().isEmpty() ? "CTA" : "CTE";
      oCtc.campo6 = rst.getString("serie");
      oCtc.campo8 = Format.number(rst.getInt("numeronota"), 9);
      oCtc.campo10 = Format.data(Format.dataGUI(rst.getDate("dataemissao")), "dd/MM/yyyy", "yyyyMMdd");
      oCtc.campo11 = "0";
      oCtc.campo12 = Format.data(Format.dataGUI(rst.getDate("data")), "dd/MM/yyyy", "yyyyMMdd");
      oCtc.campo13 = rst.getString("tipofrete");
      oCtc.campo14 = Format.number(rst.getString("id_fornecedor"), 9);
      oCtc.campo16 = rst.getString("cfop").replace(".", "").replace(",", ".");
      oCtc.campo17 = FormatDecimal2R(rst.getDouble("valorcontabil"));
      oCtc.campo18 = FormatDecimal2R(rst.getDouble("baseicms"));
      oCtc.campo19 = FormatDecimal2R(rst.getDouble("porcentagemfinal"));
      oCtc.campo20 = FormatDecimal2R(rst.getDouble("icms"));
      oCtc.campo21 = FormatDecimal2R(rst.getDouble("valorisento"));
      oCtc.campo22 = FormatDecimal2R(rst.getDouble("valoroutras"));
      oCtc.campo23 = (rst.getInt("id_estado") == TipoEstado.CE.getId()) ? rst.getString("uf") : "";
      oCtc.campo24 = (rst.getInt("id_estado") == TipoEstado.CE.getId()) ? rst.getString("id_municipio").substring(2, rst.getString("id_municipio").length()) : "";
      oCtc.campo39 = FormatDecimal2R(rst.getDouble("aliquotaorigem"));
      oCtc.campo40 = FormatDecimal2R(rst.getDouble("aliquotaloja"));
      oCtc.campo53 = (rst.getObject("observacao") != null) ? rst.getString("observacao") : "";
      
      int paymentIndicator = rst.getInt("id_indicadorpagamento");

      switch (paymentIndicator) {
        case 1:
          oCtc.campo54 = "P";
          break;
        case 0:
        case 2:
          oCtc.campo54 = "V";
          break;
        case 9:
          oCtc.campo54 = "N";
          break;
      }

      oCtc.campo55 = Format.number(rst.getInt("situacaotributaria"), 2);

      oCtc.campo56 = (rst.getString("modelo").equals(ModeloNotaFiscal.CTE.getModelo())) ? rst.getString("chavenfe") : "";
      oCtc.campo57 = "000001";
      oCtc.campo58 = (rst.getObject("cst") != null) ? rst.getString("cst") : "";
      oCtc.campo59 = (rst.getObject("cst") != null) ? rst.getString("cst") : "";
      oCtc.campo60 = (rst.getObject("cstcumulativo") != null) ? rst.getString("cstcumulativo") : "";
      oCtc.campo61 = "9";
      oCtc.campo62 = (rst.getObject("id_tipobasecalculocredito") != null) ? Format.number(rst.getString("id_tipobasecalculocredito"), 2) : "";
      oCtc.campo63 = FormatDecimal2R(rst.getDouble("basepiscofins"));
      String conta = "";
      if (rst.getObject("id_contacontabilfiscalcredito") != null)
        conta = getContaContabil(rst.getLong("id_contacontabilfiscalcredito")); 
      oCtc.campo64 = conta;
      int[] arrayCst = { 4, 5, 6, 7, 8, 9 };
      if (ArrayUtilsContains(arrayCst, rst.getInt("cst"))) {
        String codigoNatureza = Format.number(rst.getInt("tiponatureza"), 3);
        oCtc.campo65 = codigoNatureza;
        oCtc.campo66 = codigoNatureza;
      } 
      if (i_exportacao.tipoData != TipoData.ENTRADA.getId()) {
        oCtc.campo67 = Format.number(rst.getString("id_fornecedor"), 9);
        oCtc.campo68 = Format.number(rst.getString("id_fornecedor"), 9);
        oCtc.campo69 = Format.number(this.oFortesDAO.converteTipoFrete(rst.getInt("id_tipofretenotafiscal")), 1);
      } 
      oCtc.campo70 = "";
      oCtc.campo71 = (rst.getInt("id_tipocrt") == TipoCrt.SIMPLES_NACIONAL.getId()) ? rst.getString("tipocredito") : "";
      if (i_exportacao.tipoData == TipoData.EMISSAO.getId()) {
        oCtc.campo72 = (rst.getDouble("valortotal") > 0.0D) ? "S" : "N";
        oCtc.campo73 = FormatDecimal2R(rst.getDouble("valortotal"));
        if (rst.getDouble("valortotal") > 0.0D && rst.getDouble("porcentagemfcp") >= 2.0D)
          oCtc.campo74 = FormatDecimal2R(rst.getDouble("porcentagemfcp")); 
        oCtc.campo75 = FormatDecimal2R(rst.getDouble("aliq_orig_perc"));
        oCtc.campo76 = FormatDecimal2R(rst.getDouble("aliq_dest_perc"));
      } else {
        oCtc.campo72 = "N";
      } 
      oCtc.campo77 = "";
      oCtc.campo78 = "";
      oCtc.campo79 = "";
      oCtc.campo80 = "";
      oCtc.campo81 = "";
      oCtc.campo82 = "";
      oCtc.campo83 = "";
      oCtc.campo84 = "";
      oCtc.campo85 = "";
      oCtc.campo86 = FormatDecimal2R(rst.getDouble("baseinss"));
      oCtc.campo87 = FormatDecimal2R(rst.getDouble("aliquotainss"));
      oCtc.campo88 = FormatDecimal2R(rst.getDouble("inss"));
      oCtc.campo89 = "";
      oCtc.campo90 = "";
      oCtc.campo91 = "";
      oCtc.campo92 = "";
      oCtc.campo93 = "";
      oCtc.campo94 = "";
      oCtc.campo95 = "";
      oCtc.campo96 = "";
      oCtc.campo97 = "";
      oCtc.campo98 = "";
      oCtc.campo99 = "";
      oCtc.campo100 = "";
      oCtc.campo101 = "";
      oCtc.campo102 = "0";
      oCtc.campo103 = "";
      oCtc.campo104 = "";
      oCtc.campo105 = "";
      oCtc.campo106 = "";
      oCtc.campo107 = "";
      oCtc.campo108 = "";
      oCtc.campo109 = "";
      oCtc.campo110 = "";
      oCtc.campo111 = "";
      oCtc.campo112 = "";
      oCtc.campo113 = "";
      oCtc.campo114 = "";
      oCtc.campo115 = "";
      oCtc.campo116 = "";
      oCtc.campo117 = "";

      i_exportacao.qtdRegistro++;
      i_arquivo.write(oCtc.getStringLayout158());
    } 
    stm.close();
  }
  
  private void exportarInventario(ExportarFortesVO i_exportacao, Arquivo i_arquivo) throws Exception {
    ResultSet rst = null;
    VRStatement stm = null;
    StringBuilder sql = null;
    stm = VRStatement.createStatement();
    ProgressBar.setStatus("Exportando Inventario");
    String dataInventario = DataHora.getUltimoDiaMes(Format.mesAno(i_exportacao.dataTermino));
    sql = new StringBuilder();
    sql.append("SELECT i.data, i.id_produto, i.quantidade, p.id_tipoembalagem, i.custosemimposto, a.situacaotributaria,");
    sql.append(" ROUND(SUM((i.quantidade) * i.custosemimposto),2) AS basecalculoicms, (a.porcentagemfinal + COALESCE(a.porcentagemfcp, 0)) AS porcentagemfinal, p.id_tipoorigemmercadoriaentrada,");
    sql.append(" ROUND(SUM((i.quantidade) * i.custocomimposto) * (a.porcentagemfinal + COALESCE(a.porcentagemfcp, 0)) / 100,2) AS valoricms, a.csosn");
    sql.append(" FROM inventario AS i");
    sql.append(" INNER JOIN produto AS p ON p.id = i.id_produto");
    sql.append(" INNER JOIN aliquota AS a ON a.id = i.id_aliquotacredito");
    sql.append(" INNER JOIN produtocomplemento AS comp ON comp.id_produto = p.id AND comp.id_loja = " + i_exportacao.idLoja);
    sql.append(" WHERE i.id_loja =  " + i_exportacao.idLoja);
    sql.append(" AND i.data = '" + Format.dataBanco(dataInventario) + "'");
    sql.append(" AND i.quantidade > 0");
    sql.append(" AND comp.id_tipoproduto in (SELECT id_tipoproduto FROM fortes.inventarioparametro)");
    sql.append(" GROUP BY i.data, i.id_produto, i.quantidade, p.id_tipoembalagem, i.custosemimposto, a.situacaotributaria, (a.porcentagemfinal + COALESCE(a.porcentagemfcp, 0)), p.id_tipoorigemmercadoriaentrada, a.csosn");
    sql.append(" ORDER BY i.id_produto");
    rst = stm.executeQuery(sql.toString());
    rst.last();
    ProgressBar.setMaximum(rst.getRow());
    rst.beforeFirst();
    while (rst.next()) {
      FortesIIVVO oIIV = new FortesIIVVO();
      oIIV.campo1 = "IIV";
      oIIV.campo2 = Format.number(this.oFortesDAO.getCodigoEstabelecimento(i_exportacao.idLoja), 4);
      oIIV.campo3 = Format.data(Format.dataGUI(rst.getDate("data")), "dd/MM/yyyy", "yyyyMMdd");
      oIIV.campo4 = String.valueOf(rst.getInt("id_produto"));
      oIIV.campo5 = FormatDecimal3R(rst.getDouble("quantidade"));
      if (rst.getInt("id_tipoembalagem") == 4) {
        oIIV.campo6 = "KG";
      } else {
        oIIV.campo6 = "UN";
      } 
      
      if (rst.getDouble("custosemimposto") > 0) {
        oIIV.campo7 = FormatDecimal2R(rst.getDouble("custosemimposto"));
      } else {
        oIIV.campo7 = "0.01";
      }

      oIIV.campo8 = "01";
      oIIV.campo9 = Format.number(rst.getInt("id_tipoorigemmercadoriaentrada"), 1);
      oIIV.campo10 = Format.number(rst.getInt("situacaotributaria"), 2);
      oIIV.campo11 = FormatDecimal2R(rst.getDouble("basecalculoicms"));
      oIIV.campo12 = FormatDecimal2R(rst.getDouble("porcentagemfinal"));
      oIIV.campo13 = FormatDecimal2R(rst.getDouble("valoricms"));
      oIIV.campo14 = FormatDecimal2R(rst.getDouble("custosemimposto"));
      int idFornecedor = (((LojaDAO)VRInstance.criar(LojaDAO.class)).carregar(i_exportacao.idLoja)).idFornecedor;
      int tipoEmpresa = (((FornecedorDAO) VRInstance.criar(FornecedorDAO.class)).carregar(idFornecedor)).idTipoEmpresa;
      
      if (tipoEmpresa == TipoEmpresa.EPP_SIMPLES.getId() || tipoEmpresa == TipoEmpresa.ME_SIMPLES.getId()) {
        oIIV.campo15 = Format.number(rst.getInt("id_tipoorigemmercadoriaentrada"), 1); 
        oIIV.campo16 = Format.number(rst.getInt("csosn"), 1);
      }

      oIIV.campo17 = "";
      oIIV.campo18 = "";
      oIIV.campo19 = "";

      i_exportacao.qtdRegistro++;
      i_arquivo.write(oIIV.getStringLayout158());
      ProgressBar.next();
    } 
    stm.close();
  }
  
  public void exportarOperacoesCartaoCredito(ExportarFortesVO i_exportacao, Arquivo i_arquivo) throws Exception {
    VRStatement stm = null;
    ResultSet rst = null;
    StringBuilder sql = null;
    stm = VRStatement.createStatement();
    sql = new StringBuilder();
    sql.append("SELECT id_fornecedor, SUM(valordebito) AS valordebito, SUM(valorcredito) AS valorcredito, SUBSTRING(data::varchar, 0, 8) AS data FROM (");
    sql.append("SELECT DISTINCT tr.id_fornecedor, SUM(ci.valor) AS valordebito, 0 AS valorcredito,  SUBSTRING(c.data::varchar, 0, 8) AS data");
    sql.append(" FROM caixa AS c");
    sql.append(" INNER JOIN caixaitem AS ci ON c.id = ci.id_caixa");
    sql.append(" INNER JOIN tiporecebivel AS tr ON tr.id = ci.id_tiporecebivel");
    sql.append(" WHERE c.data BETWEEN '" + Format.dataBanco(i_exportacao.dataInicio) + "'");
    sql.append(" AND '" + Format.dataBanco(i_exportacao.dataTermino) + "' AND tr.id_tipocartaotef = " + TipoCartaoTef.DEBITO.getId());
    sql.append(" AND tr.id_tipotef IS NOT NULL");
    sql.append(" AND tr.id_fornecedor IS NOT NULL");
    sql.append(" AND c.id_loja = " + i_exportacao.idLoja);
    sql.append(" GROUP BY SUBSTRING(c.data::varchar, 0, 8), tr.id_fornecedor HAVING SUM(ci.valor) > 0");
    sql.append(" UNION ALL");
    sql.append(" SELECT DISTINCT tr.id_fornecedor, 0 AS valordebito, SUM(ci.valor) AS valorcredito, SUBSTRING(c.data::varchar, 0, 8) AS data");
    sql.append(" FROM caixaitem AS ci");
    sql.append(" INNER JOIN caixa AS c ON c.id = ci.id_caixa");
    sql.append(" INNER JOIN tiporecebivel AS tr ON tr.id = ci.id_tiporecebivel");
    sql.append(" WHERE c.data BETWEEN '" + Format.dataBanco(i_exportacao.dataInicio) + "'");
    sql.append(" AND '" + Format.dataBanco(i_exportacao.dataTermino) + "' AND tr.id_tipocartaotef = " + TipoCartaoTef.CREDITO.getId());
    sql.append(" AND tr.id_tipotef IS NOT NULL");
    sql.append(" AND tr.id_fornecedor IS NOT NULL");
    sql.append(" AND c.id_loja = " + i_exportacao.idLoja);
    sql.append(" GROUP BY SUBSTRING(c.data::varchar, 0, 8), tr.id_fornecedor HAVING SUM(ci.valor) > 0");
    sql.append(" ORDER BY id_fornecedor) AS f GROUP BY id_fornecedor, SUBSTRING(data::varchar, 0, 8)");
    rst = stm.executeQuery(sql.toString());
    while (rst.next()) {
      ProgressBar.setStatus("Exportando operacao com cartao de credito");
      FortesOCCVO oOCC = new FortesOCCVO();
      oOCC.campo1 = "OCC";
      oOCC.campo2 = Format.number(this.oFortesDAO.getCodigoEstabelecimento(i_exportacao.idLoja), 4);
      oOCC.campo3 = Format.data(rst.getString("data"), "yyyy-MM", "MMyyyy");
      oOCC.campo4 = rst.getInt("id_fornecedor") + "";
      oOCC.campo5 = FormatDecimal2R(rst.getDouble("valorcredito"));
      oOCC.campo6 = FormatDecimal2R(rst.getDouble("valordebito"));
      i_exportacao.qtdRegistro++;
      i_arquivo.write(oOCC.getString());
      ProgressBar.next();
    } 
    stm.close();
  }
  
  public void exportarCupomFiscalEletronico(ExportarFortesVO i_exportacao, Arquivo i_arquivo) throws Exception {
    VRStatement stmCupom = null;
    VRStatement stmProduto = null;
    VRStatement stmImposto = null;
    ResultSet rstCupom = null;
    ResultSet rstProduto = null;
    ResultSet rstImposto = null;
    StringBuilder sql = null;
    FornecedorVO oFornecedor = ((FornecedorDAO) VRInstance.criar(FornecedorDAO.class)).carregar((((LojaDAO)VRInstance.criar(LojaDAO.class)).carregar(i_exportacao.idLoja)).idFornecedor);
    boolean parametroExcluirIcmsDaBase = ((ParametroDAO) VRInstance.criar(ParametroDAO.class)).get(i_exportacao.idLoja, 510).getBoolean();
    Map<Integer, Integer> mapTipoSaidaContaContabil = new HashMap<>();
    AliquotaDAO aliquotaDao = (AliquotaDAO) VRInstance.criar(AliquotaDAO.class);

    stmCupom = VRStatement.createStatement();
    sql = new StringBuilder();
    sql.append("SELECT");
    sql.append("  e.id, e.id_loja, e.data, e.dataemissao, e.chavecfe, e.chavenfce, e.id_indicadorpagamento,");
    sql.append("  e.numeronota, e.serie, e.id_situacaonfe, e.valorcontabil, e.especie, e.valoricmsdesonerado,");
    sql.append("  SUM (ei.valordesconto) AS valordesconto, e.modelo, e.valoroutrasdespesas, e.id_estado,");
    sql.append("  SUM (ei.valortotal - ei.valorcancelado) AS valortotal,");
    sql.append("  SUM (ei.quantidade) AS quantidade, count(ei.*) as qtditems,");
    sql.append("  e.valoracrescimo, (e.valoricmssubstituicao + COALESCE(e.valorfcpst, 0)) AS valoricmssubstituicao,");
    sql.append("  SUM (ei.valorbasecalculo + ei.valorisento + ei.valoroutras) AS receitapiscofins, e.id_tiposaida, e.cancelado ");
    sql.append("FROM escrita e");
    sql.append("  INNER JOIN escritaitem AS ei ON e.id = ei.id_escrita ");
    sql.append("WHERE");
    sql.append("  e.data BETWEEN '" + Format.dataBanco(i_exportacao.dataInicio) + "'");
    sql.append("  AND '" + Format.dataBanco(i_exportacao.dataTermino) + "'");
    sql.append("  AND (e.modelo = '" + ModeloNotaFiscal.CFE.getModelo() + "' OR e.modelo = '" + ModeloNotaFiscal.NFCE.getModelo() + "')");
    sql.append("  AND e.id_loja = " + i_exportacao.idLoja + " ");
    sql.append("  AND e.cancelado = 'f'");
    sql.append("GROUP BY");
    sql.append("  e.id, e.id_loja, e.data, e.dataemissao, e.chavecfe, e.chavenfce, e.id_indicadorpagamento,");
    sql.append("  e.numeronota, e.serie, e.id_situacaonfe, e.valorcontabil, e.especie, e.valoricmsdesonerado,");
    sql.append("  e.valordesconto, e.valoracrescimo, e.cancelado, e.modelo, e.valoroutrasdespesas, e.id_estado,");
    sql.append("  (e.valoricmssubstituicao + COALESCE(e.valorfcpst, 0)), e.id_tiposaida");

    rstCupom = stmCupom.executeQuery(sql.toString());
    rstCupom.last();
    ProgressBar.setMaximum(rstCupom.getRow());
    rstCupom.beforeFirst();
    Set<String> vNotFoundAcFiscal = new HashSet<>();

    while (rstCupom.next()) {
      ProgressBar.setStatus("Exportando Cupons Eletronicos");

      if (rstCupom.getString("modelo").equals(ModeloNotaFiscal.CFE.getModelo())) {
        FortesCPEVO oCPE = new FortesCPEVO();
        oCPE.campo1 = "CPE";
        oCPE.campo2 = Format.number(this.oFortesDAO.getCodigoEstabelecimento(i_exportacao.idLoja), 4);
        oCPE.campo3 = Format.data(Format.dataGUI(rstCupom.getDate("data")), "dd/MM/yyyy", "yyyyMMdd");
        
        if (rstCupom.getObject("chavecfe") != null && rstCupom.getObject("chavenfce").equals("")) {
          oCPE.campo4 = rstCupom.getString("chavecfe");
          oCPE.campo5 = Texto.substring(rstCupom.getString("chavecfe").trim(), 22, 31);
        } else {
          oCPE.campo4 = "";
        }

        oCPE.campo6 = Format.number(rstCupom.getInt("numeronota"), 6);
        if (rstCupom.getBoolean("cancelado") == true) {
          oCPE.campo7 = "2";
        } else {
          oCPE.campo7 = "0";
          oCPE.campo9 = FormatDecimal2R(rstCupom.getDouble("valortotal"));
        }

        oCPE.campo8 = "";
        oCPE.campo10 = FormatDecimal2R(rstCupom.getDouble("valordesconto"));
        oCPE.campo11 = "";
        oCPE.campo12 = FormatDecimal2R(rstCupom.getDouble("valoricmssubstituicao"));
        oCPE.campo13 = FormatDecimal2R(rstCupom.getDouble("valoracrescimo"));
        oCPE.campo14 = "";
        oCPE.campo15 = FormatDecimal2R(rstCupom.getDouble("receitapiscofins"));
        oCPE.campo16 = FormatDecimal2R(rstCupom.getDouble("receitapiscofins"));
        oCPE.campo17 = "";
        oCPE.campo18 = "";
        oCPE.campo19 = "";
        oCPE.campo20 = "";
        oCPE.campo21 = "";
        oCPE.campo22 = "";
        
        int paymentIndicator = rstCupom.getInt("id_indicadorpagamento");

        switch (paymentIndicator) {
          case 0:
            oCPE.campo23 = "V";
            break;
          case 1:
            oCPE.campo23 = "P";
            break;
          case 2:
            oCPE.campo23 = "V";
            break;
          case 9:
            oCPE.campo23 = "N";
            break;
        } 

        oCPE.campo24 = "";

        if (mapTipoSaidaContaContabil.get(rstCupom.getInt("id_tiposaida")) == null) {
          mapTipoSaidaContaContabil.put(rstCupom.getInt("id_tiposaida"), this.oParametroContabilidadeDAO.carregarContaContabilTiposaida(rstCupom.getInt("id_tiposaida")));
        }

        int idContaContabilCredito = mapTipoSaidaContaContabil.get(rstCupom.getInt("id_tiposaida"));

        if (idContaContabilCredito > 0) {
          oCPE.campo25 = getContaContabil(idContaContabilCredito); 
        }
        
        oCPE.campo26 = "";
        oCPE.campo27 = "";
        oCPE.campo28 = "";
        oCPE.campo29 = "";
        oCPE.campo30 = "";
        oCPE.campo31 = "";
        oCPE.campo32 = "";
        oCPE.campo33 = "";
        oCPE.campo34 = "";
        oCPE.campo35 = "";

        i_exportacao.qtdRegistro++;
        i_arquivo.write(oCPE.getStringLayout140());
        ProgressBar.next();

        if (oCPE.campo7.equals("2"))
          continue; 
        stmProduto = new VRStatement();
        stmProduto.open();
        sql = new StringBuilder();
        sql.append("SELECT ei.id_produto, ei.quantidade,");
        sql.append("  te.descricao AS tipoembalagem,");
        sql.append("  ei.cfop, ei.valoracrescimo, ei.valoroutras, ei.valorisento,");
        sql.append("  (ei.valorbasecalculo + ei.valorisento + ei.valoroutras + ei.valorcancelado) AS valorbruto,");
        sql.append("  (ei.valortotal + ei.valoracrescimo - ei.valordesconto - ei.valorcancelado) AS valorliquido,");
        sql.append("  COALESCE(ei.id_tipoorigemmercadoria, p.id_tipoorigemmercadoria) AS id_tipoorigemmercadoria,");
        sql.append("  a.csosn,");
        sql.append("  ei.situacaotributaria, ei.valorbasecalculo,");
        sql.append("  (a.porcentagemfinal + COALESCE(a.porcentagemfcp, 0)) AS porcentagemfinal, tpc.cst,");
        sql.append("  (ei.valorbasecalculo + ei.valorisento + ei.valoroutras) AS basecalculo,");
        if (oFornecedor.idTipoEmpresa == TipoEmpresa.LUCRO_PRESUMIDO.getId()) {
          sql.append("  tpc.valorcofinspresumido as valorcofins, tpc.valorpispresumido as valorpis, ");
        } else {
          sql.append("  tpc.valorcofins, tpc.valorpis, ");
        } 
        sql.append("  p.tiponaturezareceita,  ei.id_tiposaida, ei.valoricms,");
        sql.append("  ei.valortotal, ei.valordesconto, ttr.codigoacfiscal ");
        sql.append("FROM escritaitem ei");
        sql.append("  INNER JOIN produto p ON ei.id_produto = p.id");
        sql.append("  INNER JOIN tipoembalagem te ON te.id = p.id_tipoembalagem");
        sql.append("  INNER JOIN aliquota a ON ei.id_aliquota = a.id");
        sql.append("  INNER JOIN tipopiscofins tpc ON ei.id_tipopiscofins = tpc.id ");
        sql.append("  LEFT JOIN tiponaturezareceita ttr ON ttr.codigo = p.tiponaturezareceita and ttr.cst = tpc.cst ");
        sql.append("WHERE id_escrita = " + rstCupom.getLong("id"));
        sql.append("  AND ei.cancelado = false");
        rstProduto = stmProduto.executeQuery(sql.toString());
        double baseCalculoPisCofins = 0.0D;
        double valorPis = 0.0D;
        double valorCofins = 0.0D;
        // long last = System.currentTimeMillis();
      
        while (rstProduto.next()) {
          if (rstProduto.getDouble("valorliquido") == 0) {
            continue;
          }

          FortesPCEVO oPCE = new FortesPCEVO();
          oPCE.campo1 = "PCE";
          oPCE.campo2 = String.valueOf(rstProduto.getInt("id_produto"));
          
          if (rstProduto.getDouble("quantidade") <= 0.0D) {
            oPCE.campo3 = "0.001";
          } else {
            oPCE.campo3 = FormatDecimal3R(rstProduto.getDouble("quantidade"));
          }

          oPCE.campo4 = rstProduto.getString("tipoembalagem");
          oPCE.campo5 = rstProduto.getString("cfop").replace(".", "").replace(",", ".");
          oPCE.campo6 = FormatDecimal2R(rstProduto.getDouble("valortotal"));
          oPCE.campo7 = FormatDecimal2R(rstProduto.getDouble("valordesconto"));
          oPCE.campo8 = "";

          double increaseValue = rstProduto.getDouble("valoroutras") > 0 ? rstProduto.getDouble("valoroutras") : rstProduto.getDouble("valorisento");

          oPCE.campo9 = FormatDecimal2R(increaseValue >= rstProduto.getDouble("valortotal") ? increaseValue - rstProduto.getDouble("valortotal") : 0.00);
          
          if (
            oFornecedor.idTipoEmpresa == TipoEmpresa.EPP_SIMPLES.getId() ||
            oFornecedor.idTipoEmpresa == TipoEmpresa.ME_SIMPLES.getId() ||
            oFornecedor.idTipoEmpresa == TipoEmpresa.MEI.getId()
          ) {
            oPCE.campo10 = Format.number(rstProduto.getInt("id_tipoorigemmercadoria"), 1);
            oPCE.campo11 = Format.number(rstProduto.getInt("csosn"), 3);
            oPCE.campo12 = "";
            oPCE.campo13 = "";
          } else {
            oPCE.campo10 = "";
            oPCE.campo11 = "";
            oPCE.campo12 = Format.number(rstProduto.getInt("id_tipoorigemmercadoria"), 1);
            oPCE.campo13 = Format.number(rstProduto.getInt("situacaotributaria"), 2);
          }

          oPCE.campo14 = FormatDecimal2R(rstProduto.getDouble("valorbasecalculo"));
          oPCE.campo15 = FormatDecimal2R(rstProduto.getDouble("porcentagemfinal"));

          if (
            oFornecedor.idTipoEmpresa == TipoEmpresa.LUCRO_REAL.getId() ||
            oFornecedor.idTipoEmpresa == TipoEmpresa.LUCRO_PRESUMIDO.getId()
          ) {
            oPCE.campo16 = Format.number(rstProduto.getInt("cst"), 2);

            if (!oPCE.campo16.equals("49")) {
              oPCE.campo17 = FormatDecimal2R(rstProduto.getDouble("valortotal") - rstProduto.getDouble("valordesconto") + rstProduto.getDouble("valoracrescimo"));
            }
            
            oPCE.campo18 = "1";
            oPCE.campo19 = (rstProduto.getDouble("valorcofins") > 0.0D && this.oCAB.campo9.equals("S")) ? FormatDecimal2R(rstProduto.getDouble("valorcofins")) : "";
            oPCE.campo20 = "";

            if (parametroExcluirIcmsDaBase) {
              baseCalculoPisCofins = rstProduto.getDouble("basecalculo") - rstProduto.getDouble("valoricms");
            } else {
              baseCalculoPisCofins = rstProduto.getDouble("basecalculo");
            }

            oPCE.campo21 = (Numero.round(baseCalculoPisCofins * rstProduto.getDouble("valorcofins") / 100.0D, 2) > 0.0D && this.oCAB.campo9.equals("S")) ? FormatDecimal2R(Numero.round(baseCalculoPisCofins * rstProduto.getDouble("valorcofins") / 100.0D, 2)) : "";
            oPCE.campo22 = Format.number(rstProduto.getInt("codigoacfiscal"), 3);
            
            if (!oPCE.campo22.isEmpty() && Integer.parseInt(oPCE.campo22) == 0 && rstProduto.getString("tiponaturezareceita") != null) {
              vNotFoundAcFiscal.add(rstProduto.getString("tiponaturezareceita"));
            }
            
            oPCE.campo23 = Format.number(rstProduto.getInt("cst"), 2);

            if (!oPCE.campo23.equals("49")) {
              oPCE.campo24 = FormatDecimal2R(rstProduto.getDouble("valortotal") - rstProduto.getDouble("valordesconto") + rstProduto.getDouble("valoracrescimo"));
            }

            oPCE.campo25 = "1";
            oPCE.campo26 = (rstProduto.getDouble("valorpis") > 0.0D && this.oCAB.campo9.equals("S")) ? FormatDecimal2R(rstProduto.getDouble("valorpis")) : "";
            oPCE.campo27 = "";
            oPCE.campo28 = (Numero.round(baseCalculoPisCofins * rstProduto.getDouble("valorpis") / 100.0D, 2) > 0.0D && this.oCAB.campo9.equals("S")) ? FormatDecimal2R(Numero.round(baseCalculoPisCofins * rstProduto.getDouble("valorpis") / 100.0D, 2)) : "";
            oPCE.campo29 = oPCE.campo22;
          } else {
            oPCE.campo16 = "";
            oPCE.campo17 = "";
            oPCE.campo18 = "";
            oPCE.campo19 = "";
            oPCE.campo20 = "";

            oPCE.campo21 = "";
            oPCE.campo22 = "";
            oPCE.campo23 = "";
            
            oPCE.campo24 = "";
            oPCE.campo25 = "";
            oPCE.campo26 = "";
            oPCE.campo27 = "";
            oPCE.campo28 = "";
            oPCE.campo29 = "";
          }

          if (mapTipoSaidaContaContabil.get(rstCupom.getInt("id_tiposaida")) == null) {
            mapTipoSaidaContaContabil.put(rstCupom.getInt("id_tiposaida"), this.oParametroContabilidadeDAO.carregarContaContabilTiposaida(rstProduto.getInt("id_tiposaida")));
          }

          idContaContabilCredito = mapTipoSaidaContaContabil.get(rstCupom.getInt("id_tiposaida"));
          
          if (idContaContabilCredito > 0) {
            oPCE.campo30 = getContaContabil(idContaContabilCredito);
          }

          oPCE.campo31 = "";

          i_exportacao.qtdRegistro++;
          i_arquivo.write(oPCE.getStringLayout158());
        }
        // System.out.println("Executed: " + (System.currentTimeMillis() - last) + "ms");
      
        stmProduto.close();
        stmImposto = new VRStatement();
        stmImposto.open();
        sql = new StringBuilder();
        sql.append("SELECT SUM (ei.valortotal - ei.valordesconto + ei.valoracrescimo - ei.valorcancelado) AS valor, (a.porcentagemfinal + COALESCE(a.porcentagemfcp, 0)) AS porcentagemfinal,");
        sql.append(" SUM (ei.valoricms + COALESCE(ei.valorfcp, 0)) AS valoricms, SUM (ei.valorisento) AS valorisento,");
        sql.append(" SUM (ei.valoroutras) AS valoroutras, ei.situacaotributaria,");
        sql.append(" COALESCE(ei.id_tipoorigemmercadoria, p.id_tipoorigemmercadoria) AS id_tipoorigemmercadoria,");
        sql.append(" SUM (ei.valorbasecalculo) AS valorbasecalculo, ");
        sql.append(" a.csosn, tpc.cst, ei.cfop, sum(ei.valortotal) as valortotal");
        sql.append(" FROM escritaitem AS ei");
        sql.append(" INNER JOIN aliquota AS a ON ei.id_aliquota = a.id");
        sql.append(" INNER JOIN produto AS p ON ei.id_produto = p.id");
        sql.append(" INNER JOIN tipopiscofins AS tpc ON ei.id_tipopiscofins = tpc.id");
        sql.append(" WHERE ei.id_escrita = " + rstCupom.getLong("id"));
        sql.append(" AND ei.cancelado = false");
        sql.append(" GROUP BY ei.cfop, tpc.cst, valorbasecalculo, (a.porcentagemfinal + COALESCE(a.porcentagemfcp, 0)), ei.situacaotributaria,");
        sql.append(" COALESCE(ei.id_tipoorigemmercadoria, p.id_tipoorigemmercadoria), a.csosn");
        rstImposto = stmImposto.executeQuery(sql.toString());
        while (rstImposto.next()) {
          FortesCFIVO oCFI = new FortesCFIVO();
          oCFI.campo1 = "CFI";
        
          double increaseValue = rstImposto.getDouble("valoroutras") > 0 ? rstImposto.getDouble("valoroutras") : rstImposto.getDouble("valorisento");
          double increaseField = increaseValue >= rstImposto.getDouble("valortotal") ? increaseValue - rstImposto.getDouble("valortotal") : 0.00;

          oCFI.campo2 = FormatDecimal2R(rstImposto.getDouble("valor") + increaseField);
          oCFI.campo3 = oFornecedor.estado;
          oCFI.campo4 = Format.number(rstImposto.getString("cfop").replace(".", ""), 4);
          oCFI.campo5 = FormatDecimal2R(rstImposto.getDouble("valorbasecalculo"));
          oCFI.campo6 = FormatDecimal2R(rstImposto.getDouble("porcentagemfinal"));
          oCFI.campo7 = FormatDecimal2R(rstImposto.getDouble("valoricms"));
          oCFI.campo8 = FormatDecimal2R(rstImposto.getDouble("valor") + increaseField);
          oCFI.campo9 = FormatDecimal2R(rstImposto.getDouble("valor") + increaseField);
          
          boolean substituicao = aliquotaDao.isSubstituido(rstImposto.getInt("situacaotributaria"));

          if (substituicao) {
            oCFI.campo10 = "S";
          } else {
            oCFI.campo10 = "N";
          }

          oCFI.campo11 = "N";
          oCFI.campo12 = "N";

          if (
            oFornecedor.idTipoEmpresa == TipoEmpresa.EPP_SIMPLES.getId() ||
            oFornecedor.idTipoEmpresa == TipoEmpresa.ME_SIMPLES.getId() ||
            oFornecedor.idTipoEmpresa == TipoEmpresa.MEI.getId()
          ) {
            oCFI.campo13 = "";
            oCFI.campo14 = "";
          } else {
            oCFI.campo13 = Format.number(rstImposto.getInt("id_tipoorigemmercadoria"), 1);
            oCFI.campo14 = Format.number(rstImposto.getInt("situacaotributaria"), 2);
          }

          if (
            oFornecedor.idTipoEmpresa == TipoEmpresa.EPP_SIMPLES.getId() ||
            oFornecedor.idTipoEmpresa == TipoEmpresa.ME_SIMPLES.getId() ||
            oFornecedor.idTipoEmpresa == TipoEmpresa.MEI.getId()
          ) {
            oCFI.campo15 = Format.number(rstImposto.getInt("id_tipoorigemmercadoria"), 1);
            oCFI.campo16 = Format.number(rstImposto.getInt("csosn"), 3);
          } else {
            oCFI.campo15 = "";
            oCFI.campo16 = "";
          }

          if (
            (
              oFornecedor.idTipoEmpresa == TipoEmpresa.EPP_SIMPLES.getId() ||
              oFornecedor.idTipoEmpresa == TipoEmpresa.ME_SIMPLES.getId() ||
              oFornecedor.idTipoEmpresa == TipoEmpresa.MEI.getId()
            ) && (rstImposto.getInt("cst") == 4 || rstImposto.getInt("cst") == 70)
          ) {
            oCFI.campo17 = "S";
            oCFI.campo18 = "S";
          } else {
            oCFI.campo17 = "N";
            oCFI.campo18 = "N";
          }

          i_exportacao.qtdRegistro++;
          i_arquivo.write(oCFI.getStringLayout140());
        } 
        stmImposto.close();
      } else {
        FortesNVCVO oNVC = new FortesNVCVO();
        oNVC.campo1 = "NVC";
        oNVC.campo2 = Format.number(this.oFortesDAO.getCodigoEstabelecimento(i_exportacao.idLoja), 4);
        oNVC.campo3 = "";
        oNVC.campo4 = Format.number(rstCupom.getInt("serie"), 3);
        oNVC.campo5 = "";
        oNVC.campo6 = Format.number(rstCupom.getInt("numeronota"), 9);
        oNVC.campo7 = "";
        oNVC.campo8 = "";
        oNVC.campo9 = "";
        oNVC.campo10 = Format.data(Format.dataGUI(rstCupom.getDate("dataemissao")), "dd/MM/yyyy", "yyyyMMdd");

        if (rstCupom.getBoolean("cancelado")) {
          oNVC.campo11 = "1";
          oNVC.campo12 = "";
          oNVC.campo13 = "";
          oNVC.campo14 = "";
          oNVC.campo15 = "";
          oNVC.campo16 = "";
          oNVC.campo17 = "";
          oNVC.campo18 = "";
          oNVC.campo19 = "";
          oNVC.campo20 = "";
        } else {
          oNVC.campo11 = "0";
          oNVC.campo12 = Format.data(Format.dataGUI(rstCupom.getDate("dataemissao")), "dd/MM/yyyy", "yyyyMMdd");
          oNVC.campo13 = FormatDecimal2R(rstCupom.getDouble("valortotal"));
          oNVC.campo14 = FormatDecimal2R(rstCupom.getDouble("valordesconto"));
          oNVC.campo15 = rstCupom.getString("qtditems");
   
          int paymentIndicator = rstCupom.getInt("id_indicadorpagamento");

          switch (paymentIndicator) {
            case 0:
              oNVC.campo16 = "V";
              break;
            case 1:
              oNVC.campo16 = "P";
              break;
            case 2:
              oNVC.campo16 = "V";
              break;
            case 9:
              oNVC.campo16 = "N";
              break;
          }
          
          oNVC.campo17 = Format.number(rstCupom.getInt("serie"), 3);
          oNVC.campo18 = Format.number(rstCupom.getInt("numeronota"), 6);
          oNVC.campo19 = FormatDecimal2R(rstCupom.getDouble("receitapiscofins"));
          oNVC.campo20 = FormatDecimal2R(rstCupom.getDouble("receitapiscofins"));
        }

        oNVC.campo21 = "";
        oNVC.campo22 = "";
        oNVC.campo23 = "";
        oNVC.campo24 = "";
        oNVC.campo25 = "";   
        oNVC.campo26 = "";
        oNVC.campo27 = "";
        oNVC.campo28 = "";

        oNVC.campo29 = rstCupom.getString("especie");
        oNVC.campo30 = "";
        oNVC.campo31 = "";
       
        if (!rstCupom.getBoolean("cancelado")) {
          oNVC.campo32 = FormatDecimal2R(rstCupom.getDouble("valoroutrasdespesas"));
          oNVC.campo33 = FormatDecimal2R(rstCupom.getDouble("valortotal"));
        }
        
        if (
          rstCupom.getString("modelo").equals("65") &&
          rstCupom.getObject("chavecfe").equals("") &&
          rstCupom.getObject("chavenfce") != null
        ) {
          oNVC.campo34 = rstCupom.getString("chavenfce");
        } else {
          oNVC.campo34 = "";
        }


        if (rstCupom.getBoolean("cancelado")) {
          oNVC.campo35 = "CANCELADO PDV";
        }

        oNVC.campo36 = Format.number(rstCupom.getInt("id_tiposaida"), 8);
        oNVC.campo37 = rstCupom.getBoolean("cancelado") ? "" : "1";
        oNVC.campo38 = "";
        oNVC.campo39 = "";
        oNVC.campo40 = "";
        oNVC.campo41 = "";
        oNVC.campo42 = "";
        oNVC.campo43 = "";
        oNVC.campo44 = "";
        oNVC.campo45 = "";
        oNVC.campo46 = "";
        oNVC.campo47 = "";
        oNVC.campo48 = FormatDecimal2R(rstCupom.getDouble("valoricmsdesonerado"));

        i_exportacao.qtdRegistro++;
        i_arquivo.write(oNVC.getStringLayout158());
        ProgressBar.next();

        if (rstCupom.getBoolean("cancelado")) {
          continue;
        }

        stmProduto = new VRStatement();
        stmProduto.open();
        sql = new StringBuilder();
        sql.append("SELECT ei.id_produto, ei.quantidade,");
        sql.append("  te.descricao AS tipoembalagem,");
        sql.append("  ei.cfop, ei.valoracrescimo, ei.valoroutras, ei.valorisento,");
        sql.append("  (ei.valorbasecalculo + ei.valorisento + ei.valoroutras + ei.valorcancelado) AS valorbruto,");
        sql.append("  (ei.valortotal + ei.valoracrescimo - ei.valordesconto - ei.valorcancelado) AS valorliquido,");
        sql.append("  COALESCE(ei.id_tipoorigemmercadoria, p.id_tipoorigemmercadoria) AS id_tipoorigemmercadoria,");
        sql.append("  a.csosn,");
        sql.append("  ei.situacaotributaria, ei.valorbasecalculo,");
        sql.append("  (a.porcentagemfinal + COALESCE(a.porcentagemfcp, 0)) AS porcentagemfinal, tpc.cst,");
        sql.append("  (ei.valorbasecalculo + ei.valorisento + ei.valoroutras) AS basecalculo,");
        if (oFornecedor.idTipoEmpresa == TipoEmpresa.LUCRO_PRESUMIDO.getId()) {
          sql.append("  tpc.valorcofinspresumido as valorcofins, tpc.valorpispresumido as valorpis, ");
        } else {
          sql.append("  tpc.valorcofins, tpc.valorpis, ");
        } 
        sql.append("  p.tiponaturezareceita,  ei.id_tiposaida, ei.valoricms, ei.valoricmsdesonerado, a.idmotivodesoneracao, ");
        sql.append("  ei.valortotal, ei.valordesconto, ttr.codigoacfiscal, ei.valorfcp, a.porcentagemfcp ");
        sql.append("FROM escritaitem ei");
        sql.append("  INNER JOIN produto p ON ei.id_produto = p.id");
        sql.append("  INNER JOIN tipoembalagem te ON te.id = p.id_tipoembalagem");
        sql.append("  INNER JOIN aliquota a ON ei.id_aliquota = a.id");
        sql.append("  INNER JOIN tipopiscofins tpc ON ei.id_tipopiscofins = tpc.id ");
        sql.append("  LEFT JOIN tiponaturezareceita ttr ON ttr.codigo = p.tiponaturezareceita and ttr.cst = tpc.cst ");
        sql.append("WHERE id_escrita = " + rstCupom.getLong("id"));
        sql.append("  AND ei.cancelado = false");
        rstProduto = stmProduto.executeQuery(sql.toString());
        double baseCalculoPisCofins = 0.0D;
        double valorPis = 0.0D;
        double valorCofins = 0.0D;
     
        while (rstProduto.next()) {
          if (rstProduto.getDouble("valorliquido") == 0) {
            continue;
          }

          FortesPNCVO oPNC = new FortesPNCVO();
          oPNC.campo1 = "PNC";
          oPNC.campo2 = String.valueOf(rstProduto.getInt("id_produto"));
          oPNC.campo3 = rstProduto.getString("cfop").replace(".", "").replace(",", ".");
          
          if (
            oFornecedor.idTipoEmpresa != TipoEmpresa.EPP_SIMPLES.getId() &&
            oFornecedor.idTipoEmpresa != TipoEmpresa.ME_SIMPLES.getId()
          ) {
            oPNC.campo4 = Format.number(rstProduto.getInt("id_tipoorigemmercadoria"), 1);
            oPNC.campo5 = Format.number(rstProduto.getInt("situacaotributaria"), 2);
          }
          
          oPNC.campo6 = rstProduto.getString("tipoembalagem");
          
          if (rstProduto.getDouble("quantidade") <= 0.0D) {
            oPNC.campo7 = "0.001";
          } else {
            oPNC.campo7 = FormatDecimal3R(rstProduto.getDouble("quantidade"));
          }

          oPNC.campo8 = FormatDecimal2R(rstProduto.getDouble("valortotal"));

          if ("00,10,20,70".contains(oPNC.campo5)) {
            oPNC.campo9 = "1";
          } else if ("40,41".contains(oPNC.campo5)) {
            oPNC.campo9 = "2";
          } else {
            oPNC.campo9 = "3";
          }

          oPNC.campo10 = FormatDecimal2R(rstProduto.getDouble("valorbasecalculo"));
          oPNC.campo11 = FormatDecimal2R(rstProduto.getDouble("porcentagemfinal"));

          if (parametroExcluirIcmsDaBase) {
            baseCalculoPisCofins = rstProduto.getDouble("basecalculo") - rstProduto.getDouble("valoricms");
          } else {
            baseCalculoPisCofins = rstProduto.getDouble("basecalculo");
          }

          if (
            !oPNC.campo8.equals("0,00") &&
            (
              oFornecedor.idTipoEmpresa == TipoEmpresa.LUCRO_REAL.getId() ||
              oFornecedor.idTipoEmpresa == TipoEmpresa.LUCRO_PRESUMIDO.getId()
            )
          ) {
            oPNC.campo14 = Format.number(rstProduto.getInt("cst"), 2);
            oPNC.campo15 = Format.number(rstProduto.getInt("cst"), 2);
          } else {
            oPNC.campo14 = "";
            oPNC.campo15 = "";
          }

          if (!oPNC.campo14.equals("49")) {
            oPNC.campo12 = FormatDecimal2R(rstProduto.getDouble("valortotal") - rstProduto.getDouble("valordesconto") + rstProduto.getDouble("valoracrescimo"));
            oPNC.campo13 = FormatDecimal2R(rstProduto.getDouble("valortotal") - rstProduto.getDouble("valordesconto") + rstProduto.getDouble("valoracrescimo"));
          } else {
            oPNC.campo12 = "";
            oPNC.campo13 = "";
          }
          
          int[] aCstPisCofinsSaida = { 2, 3, 4, 5, 6, 7, 8, 9 };
          
          if (ArrayUtilsContains(aCstPisCofinsSaida, rstProduto.getInt("cst"))) {
            oPNC.campo16 = Format.number(rstProduto.getInt("codigoacfiscal"), 3);
            oPNC.campo17 = Format.number(rstProduto.getInt("codigoacfiscal"), 3);
          }
            
          oPNC.campo18 = "1";
          oPNC.campo19 = (rstProduto.getDouble("valorcofins") > 0.0D && this.oCAB.campo9.equals("S")) ? FormatDecimal2R(rstProduto.getDouble("valorcofins")) : "";
          oPNC.campo20 = "";
          double pisBase = Numero.round(baseCalculoPisCofins * rstProduto.getDouble("valorpis") / 100.0D, 2);
          double cofinsBase = Numero.round(baseCalculoPisCofins * rstProduto.getDouble("valorcofins") / 100.0D, 2);
          oPNC.campo21 = (cofinsBase > 0.0D && this.oCAB.campo9.equals("S")) ? FormatDecimal2R(cofinsBase) : "";
          oPNC.campo22 = "1";
          oPNC.campo23 = (rstProduto.getDouble("valorpis") > 0.0D && this.oCAB.campo9.equals("S")) ? FormatDecimal2R(rstProduto.getDouble("valorpis")) : "";
          oPNC.campo24 = "";
          oPNC.campo25 = (pisBase > 0.0D && this.oCAB.campo9.equals("S")) ? FormatDecimal2R(pisBase) : "";
          oPNC.campo26 = FormatDecimal2R(rstProduto.getDouble("valortotal"));
          oPNC.campo27 = FormatDecimal2R(rstProduto.getDouble("valordesconto"));
          oPNC.campo28 = "";
          oPNC.campo29 = "";

          if (
            oFornecedor.idTipoEmpresa == TipoEmpresa.EPP_SIMPLES.getId() ||
            oFornecedor.idTipoEmpresa == TipoEmpresa.ME_SIMPLES.getId()
          ) {
            oPNC.campo30 = Format.number(rstProduto.getInt("id_tipoorigemmercadoria"), 1);
            oPNC.campo31 = Format.number(rstProduto.getInt("situacaotributaria"), 2);
          }

          oPNC.campo32 = "";
          oPNC.campo33 = "";

          if (
            rstCupom.getInt("id_estado") != 23 &&
            rstCupom.getString("modelo").equals("65") &&
            !"00,10,20,40,41,50,51,70,90".contains(oPNC.campo5) &&
            !"101,102,103,300,400".contains(oPNC.campo31)
          ) {
            if (rstProduto.getDouble("valorfcp") > 0) {
              oPNC.campo34 = FormatDecimal2R(rstProduto.getDouble("valortotal") - rstProduto.getDouble("valordesconto"));
              oPNC.campo35 = FormatDecimal2R(rstProduto.getDouble("porcentagemfcp"));
              oPNC.campo36 = FormatDecimal2R(rstProduto.getDouble("valorfcp"));
            }
          }

          oPNC.campo37 = "";
          oPNC.campo38 = "";
          oPNC.campo39 = "";
          oPNC.campo40 = "";
          
          if (rstProduto.getDouble("valoricmsdesonerado") > 0) {
            oPNC.campo41 = FormatDecimal2R(rstProduto.getDouble("valoricmsdesonerado"));
            oPNC.campo42 = rstProduto.getString("idmotivodesoneracao");
          } else {
            oPNC.campo41 = "";
            oPNC.campo42 = "";
          }
          oPNC.campo43 = "";
        
          i_exportacao.qtdRegistro++;
          i_arquivo.write(oPNC.getStringLayout160());
        }
        // System.out.println("Executed: " + (System.currentTimeMillis() - last) + "ms");
      
        stmProduto.close();
        stmImposto = new VRStatement();
        stmImposto.open();
        sql = new StringBuilder();
        sql.append("SELECT SUM (ei.valortotal - ei.valordesconto + ei.valoracrescimo - ei.valorcancelado) AS valor, (a.porcentagemfinal + COALESCE(a.porcentagemfcp, 0)) AS porcentagemfinal,");
        sql.append(" SUM (ei.valoricms + COALESCE(ei.valorfcp, 0)) AS valoricms, SUM (ei.valorisento) AS valorisento,");
        sql.append(" SUM (ei.valoroutras) AS valoroutras, ei.situacaotributaria,");
        sql.append(" COALESCE(ei.id_tipoorigemmercadoria, p.id_tipoorigemmercadoria) AS id_tipoorigemmercadoria,");
        sql.append(" SUM (ei.valorbasecalculo) AS valorbasecalculo, a.porcentagemfcp, sum(ei.valorfcp) as valorfcp, ");
        sql.append(" a.csosn, tpc.cst, ei.cfop, sum(ei.valortotal) as valortotal, sum(ei.valordesconto) as valordesconto");
        sql.append(" FROM escritaitem AS ei");
        sql.append(" INNER JOIN aliquota AS a ON ei.id_aliquota = a.id");
        sql.append(" INNER JOIN produto AS p ON ei.id_produto = p.id");
        sql.append(" INNER JOIN tipopiscofins AS tpc ON ei.id_tipopiscofins = tpc.id");
        sql.append(" WHERE ei.id_escrita = " + rstCupom.getLong("id"));
        sql.append(" AND ei.cancelado = false");
        sql.append(" GROUP BY ei.cfop, tpc.cst, valorbasecalculo, (a.porcentagemfinal + COALESCE(a.porcentagemfcp, 0)), ei.situacaotributaria,");
        sql.append(" COALESCE(ei.id_tipoorigemmercadoria, p.id_tipoorigemmercadoria), a.csosn, a.porcentagemfcp");
        rstImposto = stmImposto.executeQuery(sql.toString());
        while (rstImposto.next()) {
          FortesIVCVO oIVC = new FortesIVCVO();
          oIVC.campo1 = "IVC";
          oIVC.campo2 = oFornecedor.estado;
          oIVC.campo3 = Format.number(rstImposto.getString("cfop").replace(".", ""), 4);
          
          double increaseValue = rstImposto.getDouble("valoroutras") > 0 ? rstImposto.getDouble("valoroutras") : rstImposto.getDouble("valorisento");
          double increaseField = increaseValue >= rstImposto.getDouble("valortotal") ? increaseValue - rstImposto.getDouble("valortotal") : 0.00;
          
          oIVC.campo4 = FormatDecimal2R(rstImposto.getDouble("valor") + increaseField);
          oIVC.campo5 = FormatDecimal2R(rstImposto.getDouble("valorbasecalculo"));
          oIVC.campo6 = FormatDecimal2R(rstImposto.getDouble("porcentagemfinal"));
          oIVC.campo7 = FormatDecimal2R(rstImposto.getDouble("valoricms"));
          oIVC.campo8 = FormatDecimal2R(rstImposto.getDouble("valorisento"));
          oIVC.campo9 = FormatDecimal2R(rstImposto.getDouble("valoroutras"));
          
          if (
            aliquotaDao.isSubstituido(rstImposto.getInt("situacaotributaria")) &&
            (
              oFornecedor.idTipoEmpresa == TipoEmpresa.EPP_SIMPLES.getId() ||
              oFornecedor.idTipoEmpresa == TipoEmpresa.ME_SIMPLES.getId() ||
              oFornecedor.idTipoEmpresa == TipoEmpresa.MEI.getId()
            )
          ) {
            oIVC.campo10 = "S";
          } else {
            oIVC.campo10 = "N";
          }

          if (
            (
              oFornecedor.idTipoEmpresa == TipoEmpresa.EPP_SIMPLES.getId() ||
              oFornecedor.idTipoEmpresa == TipoEmpresa.ME_SIMPLES.getId() ||
              oFornecedor.idTipoEmpresa == TipoEmpresa.MEI.getId()
            ) && rstImposto.getString("cst").equals("05")
          ) {
            oIVC.campo11 = "S";
            oIVC.campo12 = "S";
          } else {
            oIVC.campo11 = "N";
            oIVC.campo12 = "N";
          }

          if (
            oFornecedor.idTipoEmpresa != TipoEmpresa.EPP_SIMPLES.getId() &&
            oFornecedor.idTipoEmpresa != TipoEmpresa.ME_SIMPLES.getId()
          ) {
            oIVC.campo13 = Format.number(rstImposto.getInt("id_tipoorigemmercadoria"), 1);
            oIVC.campo14 = Format.number(rstImposto.getInt("situacaotributaria"), 2);
          } else {
            oIVC.campo15 = Format.number(rstImposto.getInt("id_tipoorigemmercadoria"), 1);
            oIVC.campo16 = Format.number(rstImposto.getInt("situacaotributaria"), 2);
          }

          if (
            (
              oFornecedor.idTipoEmpresa == TipoEmpresa.EPP_SIMPLES.getId() ||
              oFornecedor.idTipoEmpresa == TipoEmpresa.ME_SIMPLES.getId() ||
              oFornecedor.idTipoEmpresa == TipoEmpresa.MEI.getId()
            ) && rstImposto.getString("cst").equals("04")
          ) {
            oIVC.campo17 = "S";
            oIVC.campo18 = "S";
          } else {        
            oIVC.campo17 = "N";
            oIVC.campo18 = "N";
          }

          if (
            rstCupom.getInt("id_estado") != 23 &&
            rstCupom.getString("modelo").equals("65") &&
            !"00,10,20,40,41,50,51,70,90".contains(rstImposto.getString("cst")) &&
            !"101,102,103,300,400".contains(rstImposto.getString("csosn"))
          ) {
            if (rstImposto.getDouble("valorfcp") > 0) {
              oIVC.campo19 = FormatDecimal2R(rstImposto.getDouble("valortotal") - rstImposto.getDouble("valordesconto"));
              oIVC.campo20 = FormatDecimal2R(rstImposto.getDouble("porcentagemfcp"));
              oIVC.campo21 = FormatDecimal2R(rstImposto.getDouble("valorfcp"));
            }
          }

          i_exportacao.qtdRegistro++;
          i_arquivo.write(oIVC.getStringLayout158());
        } 
        stmImposto.close();
      }
    } 

    if (!vNotFoundAcFiscal.isEmpty()) {
      generateNotFoundAcFiscal(i_exportacao, vNotFoundAcFiscal);
    }

    stmCupom.close();
  }
  
  public void exportarEstoqueEscriturado(ExportarFortesVO i_exportacao, Arquivo i_arquivo, int i_idFormulario, int i_idTipoOperacao) throws Exception {
    VRStatement stm = null;
    ResultSet rst = null;
    StringBuilder sql = null;
    stm = VRStatement.createStatement();
    FortesConfiguracaoVO oConfiguracao = ((FortesDAO)VRInstance.criar(FortesDAO.class)).carregarConfiguracao(i_idFormulario, i_idTipoOperacao);
    sql = new StringBuilder();
    String dataInventario = DataHora.getUltimoDiaMes(Format.mesAno(i_exportacao.dataTermino));
    sql.append("SELECT i.id_produto, i.data, pc.estoque, t.descricao AS tipoembalagem");
    sql.append(" FROM inventario AS i");
    sql.append(" INNER JOIN produto AS p ON p.id = i.id_produto");
    sql.append(" INNER JOIN tipoembalagem AS t ON t.id = p.id_tipoembalagem");
    sql.append(" INNER JOIN produtocomplemento AS pc ON pc.id_produto = p.id");
    sql.append(" INNER JOIN escritafechamento AS ef ON ef.data = i.data");
    sql.append(" WHERE i.data = '" + Format.dataBanco(dataInventario) + "'");
    sql.append(" AND i.id_loja = " + i_exportacao.idLoja);
    rst = stm.executeQuery(sql.toString());
    rst.last();
    ProgressBar.setMaximum(rst.getRow());
    rst.beforeFirst();
    while (rst.next()) {
      ProgressBar.setStatus("Exportando Estoque Escriturado...");
      FortesECPVO oECP = new FortesECPVO();
      String codigoEstabelecimento = "";
      for (FortesConfiguracaoLojaVO oFortesConfiguracaoLoja : oConfiguracao.vLoja) {
        if (oFortesConfiguracaoLoja.idLoja == i_exportacao.idLoja)
          codigoEstabelecimento = (oFortesConfiguracaoLoja.codigoEstabelecimento != null) ? oFortesConfiguracaoLoja.codigoEstabelecimento : ""; 
      } 
      oECP.campo1 = "ECP";
      oECP.campo2 = codigoEstabelecimento;
      oECP.campo3 = Format.data(Format.dataGUI(rst.getDate("data")), "dd/MM/yyyy", "yyyyMMdd");
      oECP.campo4 = String.valueOf(rst.getInt("id_produto"));
      oECP.campo5 = Format.decimal3(rst.getInt("estoque"));
      oECP.campo6 = rst.getString("tipoembalagem");
      i_exportacao.qtdRegistro++;
      i_arquivo.write(oECP.getStringLayout140());
      ProgressBar.next();
    } 
    stm.close();
  }
  
  public String getCodigoACFiscal(int codigo, int cst) throws Exception {
    VRStatement stm = VRStatement.createStatement();
    ResultSet rst = null;
    StringBuilder sql = new StringBuilder();
    int codigoAcFiscal = 0;
    sql.append("SELECT codigoacfiscal FROM tiponaturezareceita");
    sql.append(" WHERE codigo = " + codigo);
    sql.append(" AND cst = " + cst);
    rst = stm.executeQuery(sql.toString());
    if (rst.next())
      codigoAcFiscal = rst.getInt("codigoacfiscal"); 
    stm.close();
    return Format.number(codigoAcFiscal, 3);
  }
  
  private void generateNotFoundAcFiscal(ExportarFortesVO i_exportacao, Set<String> vNotFoundAcFiscal) throws Exception
  {
    Arquivo file = new Arquivo(i_exportacao.caminho + "Fortes_natureza_nao_encontrada.fs", "w", "windows-1252");
    for (String oNotFoundAcFiscal : vNotFoundAcFiscal) {
      file.write(oNotFoundAcFiscal);
    }

    file.close();
  }

  public String getContaContabil(long i_id) throws Exception {
    VRStatement stm = null;
    ResultSet rst = null;
    StringBuilder sql = null;
    stm = VRStatement.createStatement();
    sql = new StringBuilder();
    sql.append("SELECT contareduzida");
    sql.append(" FROM contacontabilfiscal");
    sql.append(" WHERE id = " + i_id);
    rst = stm.executeQuery(sql.toString());
    String conta = "";
    if (rst.next())
      conta = rst.getString("contareduzida").replace(".", "").trim(); 
    return conta;
  }
  
  public boolean verificaProdepe(int i_idEscrita) throws Exception {
    VRStatement stm = VRStatement.createStatement();
    VRStatement stmFornecedor = VRStatement.createStatement();
    ResultSet rst = null;
    ResultSet rstFornecedor = null;
    StringBuilder sql = new StringBuilder();
    StringBuilder sqlFornecedor = new StringBuilder();
    boolean utilizaProdepe = false;
    sql.append(" SELECT id_fornecedor");
    sql.append(" FROM escrita e");
    sql.append(" WHERE e.id = " + i_idEscrita);
    rst = stm.executeQuery(sql.toString());
    if (rst.next()) {
      sqlFornecedor.append(" SELECT utilizaprodepe");
      sqlFornecedor.append(" FROM fornecedor f");
      sqlFornecedor.append(" WHERE f.id = " + rst.getInt("id_fornecedor"));
      rstFornecedor = stmFornecedor.executeQuery(sqlFornecedor.toString());
      if (rstFornecedor.next())
        utilizaProdepe = rstFornecedor.getBoolean("utilizaprodepe"); 
    } 
    return utilizaProdepe;
  }
}
