/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.DAO.PedidoDAO;
import br.com.fatec.model.Pedido;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.Stack;
/**
 * FXML Controller class
 *
 * @author User
 */
public class Lucro_viewController implements Initializable {

    @FXML
    private DatePicker txtDataInicial;
    @FXML
    private DatePicker txtDataFinal;
    @FXML
    private Label lblLucro;
    @FXML
    private Label lblCusto;
    @FXML
    private Button btnCalcular;
    @FXML
    private TableView<LucroView> TableView;
    @FXML
    private TableColumn<LucroView, String> tcData;
    @FXML
    private TableColumn<LucroView, String> tcVenda;
    @FXML
    private TableColumn<LucroView, String> tcCusto;
    @FXML
    private TableColumn<LucroView, String> tcLucro;
    @FXML
    private TableColumn<LucroView, String> tcQuantidade;
    @FXML
    private Label lblVendas;
    @FXML
    private Pane pane_azul;
    @FXML
    private Button btnMenu;
    @FXML
    private Button btnLimpar;
    
    private PedidoDAO pedidodao = new PedidoDAO();
    
    private ObservableList<LucroView> listaItens =  
    FXCollections.observableArrayList();
    
    DecimalFormat df = new DecimalFormat("0.00");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            listaItens.addAll(pedidodao.listaLucro("WHERE data >= CURDATE() - INTERVAL 30 DAY and ativo = 0 "));
            preencherLista();
        } catch (SQLException ex) {
            System.out.println("o erro foi: " + ex.getMessage());
        }
    }    
    
    private void preencherLista() throws SQLException{
        tcQuantidade.setCellValueFactory( 
                new PropertyValueFactory<>("qtd")); 
        tcVenda.setCellValueFactory( 
                new PropertyValueFactory<>("vendas")); 
        tcLucro.setCellValueFactory( 
                new PropertyValueFactory<>("lucro")); 
        tcCusto.setCellValueFactory( 
                new PropertyValueFactory<>("custo")); 
        tcData.setCellValueFactory( 
                new PropertyValueFactory<>("data")); 
        TableView.setItems(listaItens);
        
    }
    

    @FXML
    private void btnCalcular_Click(ActionEvent event) throws SQLException {
        if(txtDataFinal.getValue() == null || txtDataInicial.getValue() == null){
            return;
        }
        Date dataInicial = Date.valueOf(txtDataInicial.getValue());
        Date dataFinal = Date.valueOf(txtDataFinal.getValue());
        listaItens.clear();
        listaItens.addAll(pedidodao.listaLucro("WHERE data BETWEEN '" + dataInicial + "' AND '" + dataFinal + "' "));
        preencherLista();
        
        
        float custo = 0f;
        float vendas = 0f;
        float lucro = 0f;
        for(LucroView x : listaItens){
            custo += Float.parseFloat(x.getCusto().replace(" R$", ""));
            vendas += Float.parseFloat(x.getVendas().replace(" R$", ""));
            lucro += Float.parseFloat(x.getLucro().replace(" R$", ""));
        }
        lblCusto.setText("R$ " + Float.toString(custo));
        lblVendas.setText("R$ " + Float.toString(vendas));
        lblLucro.setText("R$ " + Float.toString(lucro));
    }

    @FXML
    private void btnMenu_Click(ActionEvent event) throws IOException {
        voltarMenu(event);
    }
    public void voltarMenu(ActionEvent event) {
        Stack<Scene> cenaAnterior = Menu_viewController.getCenaAnterior();
        if (!cenaAnterior.isEmpty()) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene cena = cenaAnterior.pop();
            stage.setScene(cena);
            stage.show();
        } else {
            // Se não houver cena anterior, não faz nada
            System.out.println("Não há cena anterior");
        }
    }

    @FXML
    private void btnLimpar_Click(ActionEvent event) throws SQLException {
            lblCusto.setText("");
            lblVendas.setText("");
            lblLucro.setText("");
            listaItens.clear();
            txtDataInicial.setValue(null);
            txtDataFinal.setValue(null);
            listaItens.addAll(pedidodao.listaLucro("WHERE data >= CURDATE() - INTERVAL 30 DAY and ativo = 0 "));
            preencherLista();
            return;
    }
    
    public class LucroView{
        private LocalDate data;
        private float Vendas;
        private float custo;
        private float lucro;
        private int qtd;

        public LucroView(Pedido pedido) {
            this.data = pedido.getData();
            this.Vendas = Vendas;
            this.custo = custo;
            this.lucro = lucro;
            this.qtd = qtd;
        }

        public LucroView() {
        }

        public LocalDate getData() {
            return data;
        }

        public void setData(LocalDate data) {
            this.data = data;
        }

        public String getVendas() {
            return df.format(Vendas).replace(",", ".") + " R$";
        }

        public void setVendas(float Vendas) {
            this.Vendas = Vendas;
        }

        public String getCusto() {
           return df.format(Vendas).replace(",", ".") + " R$";
        }

        public void setCusto(float custo) {
            this.custo = custo;
        }

        public String getLucro() {
           return df.format(lucro).replace(",", ".") + " R$";
        }

        public void setLucro(float lucro) {
            this.lucro = lucro;
        }

        public int getQtd() {
            return qtd;
        }

        public void setQtd(int qtd) {
            this.qtd = qtd;
        }
        
    }
    
}
