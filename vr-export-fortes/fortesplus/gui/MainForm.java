package fortesplus.gui;


import java.util.List;
import java.util.ArrayList;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle;

import vrframework.bean.button.VRButton;
import vrframework.bean.buttonFlat.VRButtonFlat;

import vrframework.bean.calendar.VRCalendar;
import vrframework.bean.comboBox.VRComboBox;
import vrframework.bean.fileChooser.VRFileChooser;
import vrframework.bean.label.VRLabel;
import vrframework.bean.panel.VRPanel;
import vrframework.bean.toolBarPadrao.VRToolBarPadrao;
import vrframework.remote.ItemComboVO;
import vrframework.classe.Mensagem;
import vrframework.classe.VRInstance;
import vrframework.classe.ProgressBar;

import vrintegracao.classe.Global;

import vrintegracao.dao.DataProcessamentoDAO;
import vrintegracao.dao.ParametroDAO;
import vrintegracao.dao.interfaces.Fortes158DAO;
import vrintegracao.dao.interfaces.Fortes161DAO;
import vrintegracao.vo.TipoData;
import vrintegracao.vo.TipoOperacao;
import vrintegracao.vo.Formulario;
import vrintegracao.vo.interfaces.fortes.ExportarFortesVO;

public class MainForm extends JFrame {

  public static final Cursor WAIT_CURSOR = new Cursor(Cursor.WAIT_CURSOR);
  public static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);

  private VRCalendar txtDateFrom;
  private VRCalendar txtDateTo;
  
  private VRComboBox cboItems;
  private VRComboBox cboStores;
  private VRComboBox cboDateType;
  private VRComboBox cboLayout;

  private VRButton btnExit;
  private VRButton btnExport;
  private VRButtonFlat btnPermission;

  private VRLabel lblDateInterval;
  private VRLabel lblItems;
  private VRLabel lblLayout;
  private VRLabel lblPath;
  private VRLabel lblPeriod;
  private VRLabel lblStores;
  private VRLabel lblTo;

  private VRFileChooser flcPath;

  private VRPanel fieldPanel;
  private VRPanel actionPanel;
  
  private VRToolBarPadrao toolbar;

  public MainForm() throws Exception
  {
    setTitle("Fortes Plus 1.3.0");
    setSize(460, 280);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    initComponents();

    new Thread(new Runnable() {
      public void run()
      {
        try {
          Thread.sleep(300);
          cboStores.setTabela("loja");
          cboStores.carregar();
          cboStores.setSelecionado(false);
          cboLayout.addItem(new ItemComboVO(158, "158"));
          cboLayout.addItem(new ItemComboVO(161, "161"));
          cboLayout.setId(158);
          cboDateType.addItem(new ItemComboVO(TipoData.ENTRADA.getId(), TipoData.ENTRADA.getDescricao()));
          cboDateType.addItem(new ItemComboVO(TipoData.EMISSAO.getId(), TipoData.EMISSAO.getDescricao()));
          cboDateType.setId(TipoData.ENTRADA.getId());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }).start();

    loadCboItems();
  }

    
  private void loadCboItems() throws Exception {
    try {
      this.cboItems.removeAllItems();
      this.cboItems.setCheckBox(false);
      this.cboItems.addItem(new ItemComboVO(0, "PARTICIPANTES"));
      this.cboItems.addItem(new ItemComboVO(1, "PRODUTO"));
      this.cboItems.addItem(new ItemComboVO(2, "NOTA SERVICO"));
      this.cboItems.addItem(new ItemComboVO(3, "NOTA ENTRADA"));
      this.cboItems.addItem(new ItemComboVO(4, "NOTA SAIDA"));
      this.cboItems.addItem(new ItemComboVO(5, "CONHECIMENTOS TRANSP. CARGA"));
      this.cboItems.addItem(new ItemComboVO(6, "INVENTARIO"));
      this.cboItems.addItem(new ItemComboVO(7, "CARTAO CREDITO/DEBITO"));
      this.cboItems.addItem(new ItemComboVO(8, "OUTROS VALORES DO DOCUMENTO"));
      this.cboItems.addItem(new ItemComboVO(9, "CUPOM FISCAL ELETRONICO"));
      this.cboItems.addItem(new ItemComboVO(10, "ESTOQUE ESCRITURADO")); 
      this.cboItems.setCheckBox(true);
      this.cboItems.setSelectedIndex(-1);
      this.cboItems.setSelecionado(true);
    } catch (Exception ex) {
      throw ex;
    } 
  }

  private void btnExitActionPerformed(ActionEvent evt)
  {
    try {
      setCursor(WAIT_CURSOR);
      System.exit(0);
    } catch (Exception ex) {
      Mensagem.exibirErro(ex, getTitle());
    } finally {
      setCursor(DEFAULT_CURSOR);
    } 
  }
  
  private void btnExportActionPerformed(ActionEvent evt)
  {
    try {
      setCursor(WAIT_CURSOR);
      
      new Thread(new Runnable() {
        public void run()
        {
            try {
              ProgressBar.show();
              ExportarFortesVO oExportacao = new ExportarFortesVO();
              oExportacao.caminho = MainForm.this.flcPath.getArquivo();
              oExportacao.tipoData = MainForm.this.cboDateType.getId();
              oExportacao.dataInicio = MainForm.this.txtDateFrom.getText();
              oExportacao.dataTermino = MainForm.this.txtDateTo.getText();
              oExportacao.participantes = MainForm.this.cboItems.isSelecionado(0);
              oExportacao.produto = MainForm.this.cboItems.isSelecionado(1);
              oExportacao.notaServico = MainForm.this.cboItems.isSelecionado(2);
              oExportacao.notaEntrada = MainForm.this.cboItems.isSelecionado(3);
              oExportacao.notaSaida = MainForm.this.cboItems.isSelecionado(4);
              oExportacao.conhecimentoTransporteCarga = MainForm.this.cboItems.isSelecionado(5);
              oExportacao.inventario = MainForm.this.cboItems.isSelecionado(6);
              oExportacao.operacaoCreditoDebito = MainForm.this.cboItems.isSelecionado(7);
              oExportacao.outrosValoresDocumento = MainForm.this.cboItems.isSelecionado(8);
              oExportacao.cupomFiscalEletronico = MainForm.this.cboItems.isSelecionado(9);
              oExportacao.estoqueEscriturado = MainForm.this.cboItems.isSelecionado(10);

              List<Integer> vLoja = new ArrayList<>();
              for (int i = 0; i < MainForm.this.cboStores.getItemCount(); i++) {
                if (MainForm.this.cboStores.isSelecionado(i)) {
                  vLoja.add(Integer.valueOf(MainForm.this.cboStores.getId(i)));
                } 
              }

              switch (cboLayout.getId()) {
                case 158:
                  Fortes158DAO dao158 = (Fortes158DAO) VRInstance.criar(Fortes158DAO.class);
                  dao158.exportar(oExportacao, vLoja, Formulario.INTERFACE_EXPORTACAO_FORTES.getId(), TipoOperacao.EXPORTAR.getId()); 
                  break;
                case 161:
                  Fortes161DAO dao161 = (Fortes161DAO) VRInstance.criar(Fortes161DAO.class);
                  dao161.exportar(oExportacao, vLoja, Formulario.INTERFACE_EXPORTACAO_FORTES.getId(), TipoOperacao.EXPORTAR.getId()); 
                  break;
              }

              ProgressBar.dispose();
              Mensagem.exibir("Arquivo exportado com sucesso!", MainForm.this.getTitle());
            } catch (Exception ex) {
              ProgressBar.dispose();
              Mensagem.exibirErro(ex, MainForm.this.getTitle());
            } 
        }
      }).start();
      
    } catch (Exception ex) {
      Mensagem.exibirErro(ex, getTitle());
    } finally {
      setCursor(DEFAULT_CURSOR);
    } 
  }

  private void addIcon(java.awt.Window frame)
  {
      try {
        java.net.URL url = frame.getClass().getResource("/vrframework/img/atalho/fortes.png");
        java.awt.image.BufferedImage image = javax.imageio.ImageIO.read(url);
        frame.setIconImage(image);
      } catch (java.io.IOException ex) {
        ex.printStackTrace();
      }
  }
  
  private void initComponents()
  {
    this.actionPanel = new VRPanel();
    this.btnExit = new VRButton();
    this.btnExport = new VRButton();
    this.toolbar = new VRToolBarPadrao(null);
    this.fieldPanel = new VRPanel();
    this.lblDateInterval = new VRLabel();
    this.txtDateFrom = new VRCalendar();
    this.lblTo = new VRLabel();
    this.txtDateTo = new VRCalendar();
    this.lblStores = new VRLabel();
    this.cboStores = new VRComboBox();
    this.lblItems = new VRLabel();
    this.cboDateType = new VRComboBox();
    this.lblPeriod = new VRLabel();
    this.flcPath = new VRFileChooser();
    this.lblPath = new VRLabel();
    this.lblLayout = new VRLabel();
    this.cboLayout = new VRComboBox();
    this.cboItems = new VRComboBox();
    this.btnPermission = new VRButtonFlat();

    addIcon(this);
    
    this.btnExit.setIcon(new ImageIcon(getClass().getResource("/vrframework/img/sair.png")));
    this.btnExit.setText("Sair");
    this.btnExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt)
      {
        MainForm.this.btnExitActionPerformed(evt);
      }
    });

    this.btnExport.setIcon(new ImageIcon(getClass().getResource("/vrframework/img/exportar.png")));
    this.btnExport.setText("Exportar");
    this.btnExport.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt)
      {
        MainForm.this.btnExportActionPerformed(evt);
      }
    });

    GroupLayout actionPanelLayout = new GroupLayout((Container)this.actionPanel);
    this.actionPanel.setLayout(actionPanelLayout);
    actionPanelLayout.setHorizontalGroup(actionPanelLayout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(GroupLayout.Alignment.TRAILING, actionPanelLayout.createSequentialGroup()
          .addContainerGap(-1, 32767)
          .addComponent((Component)this.btnExport, -2, -1, -2)
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
          .addComponent((Component)this.btnExit, -2, -1, -2)));
    actionPanelLayout.setVerticalGroup(actionPanelLayout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(actionPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
          .addComponent((Component)this.btnExit, -2, -1, -2)
          .addComponent((Component)this.btnExport, -2, -1, -2)));
    this.lblDateInterval.setText("Data");
    this.txtDateFrom.setObrigatorio(true);
    this.lblTo.setText("a");
    this.txtDateTo.setObrigatorio(true);
    this.lblStores.setText("Loja");
    this.cboStores.setCheckBox(true);
    this.cboStores.setObrigatorio(true);
    this.lblItems.setText("Registro");
    this.lblPeriod.setText("Período");
    this.flcPath.setFileSelectionMode("Directories");
    this.flcPath.setObrigatorio(true);
    this.lblPath.setText("Diretório");
    this.lblLayout.setText("Layout");
    this.cboItems.setCheckBox(true);
    this.cboItems.setObrigatorio(true);
  
    GroupLayout fieldPanelLayout = new GroupLayout((Container)this.fieldPanel);
    this.fieldPanel.setLayout(fieldPanelLayout);
    fieldPanelLayout.setHorizontalGroup(
      fieldPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(
          fieldPanelLayout.createSequentialGroup()
          .addContainerGap()
          .addGroup(
            fieldPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
            .addComponent((Component)this.lblPath, -2, -1, -2)
            .addGroup(
              GroupLayout.Alignment.TRAILING,
              fieldPanelLayout.createSequentialGroup()
              .addGroup(
                fieldPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent((Component) this.lblDateInterval, -2, -1, -2)
                .addComponent((Component) this.cboDateType, -2, 190, -2)
              )
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
              .addGroup(
                fieldPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                  fieldPanelLayout.createSequentialGroup()
                  .addComponent((Component)this.txtDateFrom, -2, -1, -2)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent((Component)this.lblTo, -2, -1, -2)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent((Component)this.txtDateTo, -2, -1, -2)
                )
                .addComponent((Component)this.lblPeriod, -2, -1, -2)
              )
            )
            .addGroup(
              GroupLayout.Alignment.TRAILING, fieldPanelLayout.createSequentialGroup()
              .addGroup(
                fieldPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                  fieldPanelLayout.createSequentialGroup()
                  .addComponent((Component)this.lblStores, -2, -1, -2)
                  .addGap(79, 79, 79)
                  .addComponent((Component)this.lblItems, -2, -1, -2)
                )
                .addGroup(
                  fieldPanelLayout.createSequentialGroup()
                  .addComponent((Component)this.cboStores, -2, 123, -2)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent((Component)this.cboItems, -2, 239, -2)
                )
              )
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(
                fieldPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent((Component)this.lblLayout, -2, -1, -2)
                .addComponent((Component)this.cboLayout, -2, 66, -2)
              )
            )
            .addComponent((Component)this.flcPath, -1, -1, 32767)
          )
          .addContainerGap(-1, 32767)
      )
    );

    fieldPanelLayout.setVerticalGroup(
      fieldPanelLayout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(
          fieldPanelLayout.createSequentialGroup()
          .addContainerGap()
          .addGroup(
            fieldPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
            .addComponent((Component) this.lblTo, -2, -1, -2)
            .addComponent((Component) this.txtDateTo, -2, -1, -2)
            .addComponent((Component) this.txtDateFrom, -2, -1, -2)
            .addGroup(
              fieldPanelLayout.createSequentialGroup()
              .addGroup(
                fieldPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent((Component) this.lblDateInterval, -2, -1, -2)
                .addComponent((Component) this.lblPeriod, -2, -1, -2)
              )
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(
                fieldPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent((Component) this.cboDateType, -2, -1, -2)
              )
            )
          )
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
          .addGroup(
            fieldPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
            .addGroup(
              fieldPanelLayout.createSequentialGroup()
              .addGroup(
                fieldPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent((Component) this.lblStores, -2, -1, -2)
                .addComponent((Component) this.lblItems, -2, -1, -2)
              )
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addComponent((Component) this.cboStores, -2, -1, -2)
            )
            .addComponent((Component) this.cboItems, -2, -1, -2)
            .addGroup(
              GroupLayout.Alignment.LEADING, fieldPanelLayout.createSequentialGroup()
              .addComponent((Component) this.lblLayout, -2, -1, -2)
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addComponent((Component) this.cboLayout, -2, -1, -2)
            )
          )
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
          .addComponent((Component) this.lblPath, -2, -1, -2)
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
          .addComponent((Component) this.flcPath, -2, -1, -2)
          .addContainerGap(-1, 32767)
        )
      );
    
    this.btnPermission.setToolTipText("Permissão");
    this.btnPermission.setIcon(new ImageIcon(getClass().getResource("/vrframework/img/permissao.png")));

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(
        layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(
          layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
          .addComponent((Component) this.fieldPanel, GroupLayout.Alignment.LEADING, -1, -1, 32767)
          .addComponent((Component) this.actionPanel, GroupLayout.Alignment.LEADING, -1, -1, 32767)
        )
        .addContainerGap()
      )
    );
    
    layout.setVerticalGroup(
      layout
      .createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(
        GroupLayout.Alignment.TRAILING, 
        layout.createSequentialGroup()
        .addComponent((Component) this.fieldPanel, -1, -1, 32767)
        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        .addComponent((Component) this.actionPanel, -2, -1, -2)
        .addContainerGap()
      )
    );
    pack();
  }
}