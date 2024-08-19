/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.DAO.EstoqueDAO;
import br.com.fatec.DAO.InsumoDAO;
import br.com.fatec.DAO.ItemDAO;
import br.com.fatec.DAO.Item_estoqueDAO;
import br.com.fatec.DAO.Item_pedidoDAO;
import br.com.fatec.model.Estoque;
import br.com.fatec.model.Insumo;
import br.com.fatec.model.Item;
import br.com.fatec.model.Item_pedido;
import br.com.fatec.model.item_estoque;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import java.util.Stack;

/**
 * FXML Controller class
 *
 * @author User
 */
public class Intem_viewController implements Initializable {

    @FXML
    private HBox root;
    @FXML
    private Pane pane_azul;
    @FXML
    private ComboBox<Insumo> cmbInsumo;
    @FXML
    private TextField txtQuantidade;
    @FXML
    private Button btnAdicionar;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtTipo;
    @FXML
    private TextField txtPreco;
    @FXML
    private Button btnBusca;
    @FXML
    private TableView<Item_estoqueView> TableView;
    @FXML
    private TableColumn<Item_estoqueView, String> tcInsumo;
    @FXML
    private TableColumn<Item_estoqueView, String> tcQuantidade;
    @FXML
    private Button btnConsfirma;
    @FXML
    private Button btnExcluir;
    @FXML
    private Tab TabCadastro;
    @FXML
    private TabPane TabPane;
    @FXML
    private AnchorPane btnLimpar;
    @FXML
    private Button btnLimpa;
    @FXML
    private Button btnRemover;
    
    private ObservableList<item_estoque> listaItem =  
    FXCollections.observableArrayList();
    
    private ObservableList<Item_estoqueView> listaItemView =  
    FXCollections.observableArrayList();
    
    ObservableList<Insumo> listaInsumo = FXCollections.observableArrayList();
    
    InsumoDAO dao= new InsumoDAO();
    
    EstoqueDAO estoquedao = new EstoqueDAO();
    
    ItemDAO itemdao = new ItemDAO();
        
    Item_estoqueDAO item_estoquedao = new Item_estoqueDAO();
    @FXML
    private Button btnMenu1;
    @FXML
    private Button btnMenu2;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        
        txtPreco.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            newValue = newValue.replaceAll(",",".");
            if(newValue.length()>0){
                try{
                    Double.parseDouble(newValue);
                    txtPreco.setText(newValue.replaceAll(",","."));
                }catch(Exception e){
                    txtPreco.setText(oldValue);
                }
            }
        });
        
        txtQuantidade.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            newValue = newValue.replaceAll(",",".");
            if(newValue.length()>0){
                try{
                    Double.parseDouble(newValue);
                    txtQuantidade.setText(newValue.replaceAll(",","."));
                }catch(Exception e){
                    txtQuantidade.setText(oldValue);
                }
            }
        });
        
        listaInsumo.clear();
        try {
            for (Insumo aux : dao.lista("")) {
                listaInsumo.add(aux);
            }
            cmbInsumo.setItems(listaInsumo);
        } catch (SQLException ex) {
        }
        
                // Adicionando evento de clique duplo
        TableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Item_estoqueView selectedItem = TableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    try {
                        editarInsumo(selectedItem);
                    } catch (SQLException ex) {
                    }
                }
            }
        });
        
        txtNome.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
            } else {
                try {
                    preencher();
                } catch (SQLException ex) {
                }
            }
        });
        
    }    

    @FXML
    private void btnAdicionar_click(ActionEvent event) throws SQLException {
        if(txtQuantidade.getText().isBlank() || cmbInsumo.getSelectionModel().isEmpty()){
            return;
            
        }
        item_estoque item = new item_estoque(cmbInsumo.getSelectionModel().getSelectedItem(), new Item(), Float.parseFloat(txtQuantidade.getText()));
        Item_estoqueView itemView = new Item_estoqueView(cmbInsumo.getSelectionModel().getSelectedItem().getNome(), Float.parseFloat(txtQuantidade.getText()));
        int aux = 0;
        for(item_estoque x : listaItem){
            if(x.getInsumo().getId() == item.getInsumo().getId()){
               x.setQuantidade(item.getQuantidade());
               aux ++;
             }
        } 
        for(Item_estoqueView x : listaItemView){
            if(x.getNome().equals(itemView.getNome())){
                x.setQuantidade(itemView.getQuantidade());
                TableView.refresh();
                aux++;
            }
        }
        if(aux == 0){
            listaItem.add(item);
            listaItemView.add(itemView);
            preencherLista();
         }
        }
        
        
        
    

    @FXML
    private void btnBusca_Click(ActionEvent event) throws SQLException {
       preencher();
    }
    
    private void preencher() throws SQLException{
        Item t = new Item(txtNome.getText().toUpperCase(), "", 0.0f);
            if(itemdao.buscaNome(t) != null){
                Item aux = new Item();
                aux = itemdao.buscaNome(t);
                txtNome.setText(aux.getNome());
                txtTipo.setText(aux.getTipo());
                txtPreco.setText(Float.toString(aux.getPreco()));
                listaItem.clear();
                listaItem.addAll(item_estoquedao.lista("item_id = " + aux.getId()));
                listaItemView.clear();

                for(item_estoque x : listaItem){
                    listaItemView.add(new Item_estoqueView(x.getInsumo().getNome(), x.getQuantidade()));
                    preencherLista();
                }
             }
    }

    @FXML
    private void btnConsfirma_Click(ActionEvent event) throws SQLException {
        if(txtNome.getText().isBlank() || txtTipo.getText().isBlank() || txtPreco.getText().isBlank() || listaItem.size() < 1){
            input("preencha todos os campos para realizar um registro");
            return;
        }
        
        Item item = new Item(txtNome.getText(), txtTipo.getText(), Float.parseFloat(txtPreco.getText()));
        if(itemdao.buscaNome(item) != null){
            item.setId(itemdao.buscaNome(item).getId());
            System.out.println(item.getId());
            System.out.println(item.getPreco());
            itemdao.altera(item);
            listaItem.get(0).setItem(item);
            item_estoquedao.remove(listaItem.get(0));
            for(item_estoque x : listaItem){
                x.setItem(itemdao.buscaNome(item));
                item_estoquedao.insere(x);
            }
            input("dados atualizados com sucesso");
        }else{
            itemdao.insere(item);
            for(item_estoque x : listaItem){
                x.setItem(itemdao.buscaNome(item));
                item_estoquedao.insere(x);
            }
            input("dados cadastrados com sucesso");
        }
        apagar();
    }

    @FXML
    private void btnExcluir_Click(ActionEvent event) {
        Item_estoqueView selectedItem = TableView.getSelectionModel().getSelectedItem();
        
        if(selectedItem != null){
            int index = 0;
            for(item_estoque x : listaItem){
                if(selectedItem.getNome().equals(x.getInsumo().getNome())){
                    break;
                }
                index ++;
            }
            
            listaItem.remove(index);
            
            listaItemView.remove(selectedItem);
            
        }
        TableView.refresh();

    }
    
    private void preencherLista(){

        tcInsumo.setCellValueFactory( 
                new PropertyValueFactory<>("nome")); 
        tcQuantidade.setCellValueFactory( 
                new PropertyValueFactory<>("quantidade")); 
        TableView.setItems(listaItemView);
    }
    
    private void editarInsumo(Item_estoqueView selectedItem) throws SQLException{
        int index = 0;
        for(Insumo x : listaInsumo){
            if(x.getNome().equals(selectedItem.getNome())){
                break;
            }
            index++;
        }
        TabPane.getSelectionModel().select(TabCadastro);
        cmbInsumo.setValue(listaInsumo.get(index));
        txtQuantidade.setText(selectedItem.getQuantidade());
        
    }
    
    private void input(String txt){
         Alert alerta = new Alert(Alert.AlertType.INFORMATION);
         alerta.setTitle("Mensagem");
         alerta.setHeaderText(txt.toUpperCase());
         alerta.setContentText("");
         alerta.showAndWait();
    }
    
    private void apagar(){
        txtNome.setText("");
        txtQuantidade.setText("");
        cmbInsumo.getSelectionModel().clearSelection();
        txtPreco.setText("");
        txtTipo.setText("");
        listaItemView.clear();
        listaItem.clear();
        preencherLista();
    }

    private void btnRemove_Click(ActionEvent event) throws SQLException {
    }

    @FXML
    private void btnLimpar_Click(MouseEvent event) {
    }

    @FXML
    private void btnLimpa_Click(ActionEvent event) {
        apagar();
    }

    @FXML
    private void btnRemover_Click(ActionEvent event) throws SQLException {
        Item item = new Item(txtNome.getText(), txtTipo.getText(), Float.parseFloat(txtPreco.getText()));
        item = itemdao.buscaNome(item);
        Item_pedidoDAO item_pedidodao = new Item_pedidoDAO();
        Item_pedido item_pedido = new Item_pedido();
        item_pedido.setItem(item);
        if(item_pedidodao.buscaID(item_pedido) != null){
            input("A operação não pode ser concluída devido à existência de registros deste produto em pedidos recentes.");
            return;
        };
        
        if(item != null){
            item_estoque item_estoque = new item_estoque();
            item_estoque.setItem(itemdao.buscaNome(item));
            item_estoquedao.remove(item_estoque);
            itemdao.remove(itemdao.buscaNome(item));
            input("dados apagados com sucesso");
            apagar();
        }
    }

    @FXML
    private void btnMenu1_Click(ActionEvent event) throws IOException {
        voltarMenu(event);
    }

    @FXML
    private void btnMenu2_Click(ActionEvent event) throws IOException {
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
    DecimalFormat tf = new DecimalFormat("0.000");
    public class Item_estoqueView {
    private String nome;
    private String quantidade;

        public Item_estoqueView(String nome, float quantidade) {
            this.nome = nome;
            this.quantidade = tf.format(quantidade);
        }
    
        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(String quantidade) {
            this.quantidade = quantidade;
        }
    
    
    
    }
    
}
