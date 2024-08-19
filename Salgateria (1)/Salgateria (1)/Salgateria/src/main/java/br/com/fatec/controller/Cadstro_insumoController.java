/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.controller;



import br.com.fatec.DAO.InsumoDAO;
import br.com.fatec.Banco.Banco;
import br.com.fatec.DAO.Item_estoqueDAO;
import br.com.fatec.model.Insumo;
import br.com.fatec.model.item_estoque;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;



/**
 * FXML Controller class
 *
 * @author Aluno
 */
public class Cadstro_insumoController implements Initializable {

    @FXML
    private HBox root;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEstoque;
    @FXML
    private Button btnCadastro;
    @FXML
    private Pane pane_azul;
    @FXML
    private TextField txtTipo;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnBusca;
    @FXML
    private Button btnVoltar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            txtEstoque.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            newValue = newValue.replaceAll(",",".");
            if(newValue.length()>0){
                try{
                    Double.parseDouble(newValue);
                    txtEstoque.setText(newValue.replaceAll(",","."));
                }catch(Exception e){
                    txtEstoque.setText(oldValue);
                }
            }
        });
    }    

    @FXML
    private void btnCadastro_Click(ActionEvent event) throws SQLException {
        Banco.conectar();
        InsumoDAO dao = new InsumoDAO();
        if(txtNome.getText().length()<=3 || txtEstoque.getText().isBlank() || txtNome.getText().isBlank() || txtTipo.getText().isBlank()){
            imputErro("confira se os campos foram preenchidos corretavente");
           
        }else{
            Insumo t = new Insumo(txtNome.getText().toUpperCase(), txtTipo.getText().toUpperCase(), Float.parseFloat(txtEstoque.getText()));
            if(dao.buscaNome(t) != null){
                 t.setId(dao.buscaNome(t).getId());
                 dao.altera(t);
                 imputErro("dados alterados com sucesso");
            }else{
                dao.insere(t);
                imputErro("dados cadastrados com sucesso");
            }

            apagar(); 
           }
        Banco.desconectar();
        }
        
    
    
    @FXML
    private void btnExcluir_Click(ActionEvent event) throws SQLException {
         Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
         alerta.setTitle("Mensagem");
         alerta.setHeaderText("ESSA AÇÃO APAGARA TODOS OS REGISTROS DESSE PRODUTO DA TABELA ESTOQUE, DESEJA CONTINUAR?");
         alerta.setContentText("");
         alerta.showAndWait().ifPresent(response -> {
             try{
                 if(response == ButtonType.OK){
                    Banco.conectar();
                    InsumoDAO dao = new InsumoDAO();
                    Item_estoqueDAO item_estoquedao = new Item_estoqueDAO();
                    item_estoque item_estoque = new item_estoque();
                    
                    Insumo t = new Insumo(txtNome.getText().toUpperCase(), "", 0.0f);
                    if(dao.buscaNome(t) != null){
                        t.setId(dao.buscaNome(t).getId());
                        item_estoque.setInsumo(t);
                        item_estoquedao.removeInsumo(item_estoque);
                        dao.remove(t);
                        imputErro("dados apagado com sucesso");
                    }
                    Banco.desconectar();
                    apagar(); 
                 }else{
                 }
             }catch(SQLException ex){
                 
             }
         });

    }

    @FXML
    private void btnBusca_click(ActionEvent event) throws SQLException {
        
        Banco.conectar();
        InsumoDAO dao = new InsumoDAO();
        Insumo t = new Insumo(txtNome.getText().toUpperCase(), "", 0.0f);
            if(dao.buscaNome(t) != null){
                Insumo aux = new Insumo();
                aux = dao.buscaNome(t);
                txtTipo.setText(aux.getTipo());
                txtEstoque.setText(Float.toString(aux.getEstoque_minimo()));
            }
        Banco.desconectar();
    }
    
    private void imputErro(String txt) {
         Alert alerta = new Alert(Alert.AlertType.INFORMATION);
         alerta.setTitle("Mensagem");
         alerta.setHeaderText(txt.toUpperCase());
         alerta.setContentText("");
         alerta.showAndWait();
    }
    
     private void imputQuestion(String txt) {

    }
    
    private void apagar(){
        txtNome.clear();
        txtTipo.clear();
        txtEstoque.clear();
        
    }

    @FXML
    private void txtEstoqueKeyRelesed(KeyEvent event) {
    }

    @FXML
    private void btnVoltar_Click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/view/estoque_view.fxml"));
        Parent root = loader.load();
            // Obtém o Stage atual
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    
    



    
}
    
