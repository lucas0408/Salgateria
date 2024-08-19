/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.DAO.Item_pedidoDAO;
import br.com.fatec.DAO.PedidoDAO;
import br.com.fatec.model.Item_pedido;
import br.com.fatec.model.Pedido;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class Visualizar_pedido_viewController implements Initializable {

    @FXML
    private HBox root;
    @FXML
    private Pane pane_azul;
    @FXML
    private Button btnMenu;
    @FXML
    private TextField txtHora;
    @FXML
    private DatePicker txtData;
    @FXML
    private TextField txtNum;
    @FXML
    private TextField txtLogradouro;
    @FXML
    private TextField txtNome;
    @FXML
    private TableView<Item_pedidoView> TableView;
    @FXML
    private TableColumn<Item_pedidoView, String> tcSalgado;
    @FXML
    private TableColumn<Item_pedidoView, String> tcQuantidade;
    @FXML
    private Button btnPronto;
    
    private Pedido pedido;
    
    
    Item_pedidoDAO item_pedidodao= new Item_pedidoDAO();
    
    private ObservableList<Item_pedidoView> listaItens =  
    FXCollections.observableArrayList();
    
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(pedido == null){
            return;
        }
        try {
            initializeTable();
        } catch (SQLException ex) {
            System.out.println("o erro foi: " + ex.getMessage());
        }
    }    
    
    public void initializeTable() throws SQLException{
        for(Item_pedido x : item_pedidodao.lista("pedido_id = " + pedido.getId())){
            listaItens.add(new Item_pedidoView(x));
        }
        preencherListaItens();
        
        txtNome.setText(pedido.getNome());
        txtLogradouro.setText(pedido.getLogradouro());
        txtNum.setText(Integer.toString(pedido.getNumero()));
        txtData.setValue(pedido.getData());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String timeString = sdf.format(pedido.getHorario());
        txtHora.setText(timeString);
    }
    
    private void preencherListaItens(){
        tcSalgado.setCellValueFactory( 
                new PropertyValueFactory<>("item")); 
        tcQuantidade.setCellValueFactory( 
                new PropertyValueFactory<>("quantidade")); 
        TableView.setItems(listaItens);
    }

    @FXML
    private void btnMenu_Click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/view/pedido_view.fxml"));
        Parent root = loader.load();
            // Obtém o Stage atual
        Pedido_viewController PedidoController = loader.getController();
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void btnPronto_click(ActionEvent event) throws SQLException, IOException {
        String str = pedido.calcularCusto(item_pedidodao.lista("pedido_id = " + pedido.getId()));
        if(str.length() == 0){
            PedidoDAO pedidodao = new PedidoDAO();
            pedidodao.altera(pedido);
            
            Item_pedido item_pedido = new Item_pedido();
            item_pedido.setPedido(pedido);
            item_pedidodao.remove(item_pedido);
            
            limpar(event);
            input("pedido registrado como pronto");
        }else{
            input(str);
        }
    }
    
    private void limpar(ActionEvent event) throws IOException{
        txtNome.setText("");
        txtLogradouro.setText("");
        txtNum.setText("");
        txtData.setValue(null);
        txtHora.setText("");
        TableView.getSelectionModel().select(null);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/view/pedido_view.fxml"));
        Parent root = loader.load();
            // Obtém o Stage atual
        Pedido_viewController PedidoController = loader.getController();
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    private void input(String txt){
         Alert alerta = new Alert(Alert.AlertType.INFORMATION);
         alerta.setTitle("Mensagem");
         alerta.setHeaderText(txt.toUpperCase());
         alerta.setContentText("");
         alerta.showAndWait();
    }
    
    public class Item_pedidoView{
        private String item;
        private int quantidade;

        public Item_pedidoView(Item_pedido item_pedido) {
            this.item = item_pedido.getItem().getNome();
            this.quantidade = item_pedido.getQuantidade();
        }

        public String getItem() {
            return item;
        }

        public int getQuantidade() {
            return quantidade;
        }
        
    }
}
