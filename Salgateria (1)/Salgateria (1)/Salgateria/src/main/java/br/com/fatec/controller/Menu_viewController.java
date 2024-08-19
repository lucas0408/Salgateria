/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class Menu_viewController implements Initializable {

    @FXML
    private HBox root;
    @FXML
    private AnchorPane side_ankerpane;
    @FXML
    private Button btnEstoque;
    @FXML
    private Button btnProdutos;
    @FXML
    private Button btnLucro;
    @FXML
    private Button btnFuncionario;
    @FXML
    private Button btnDeslogar;
    @FXML
    private Button btnPedidos;
    @FXML
    private Pane menu;
    @FXML
    private Text lblNome;

    private boolean isAdmin = false;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin, String nome) {
        this.isAdmin = isAdmin;
        configurarVisibilidadeBotoes(isAdmin);
        lblNome.setText(nome);
    }
    private static Stack<Scene> cenaAnterior = new Stack<>();

    public static Stack<Scene> getCenaAnterior() {
        return cenaAnterior;
    }
    public void setNomeUsuario(String nomeUsuario) {
        lblNome.setText(nomeUsuario);
    }
    public void configurarVisibilidadeBotoes(boolean isAdmin) {
        this.isAdmin = isAdmin;
        btnEstoque.setVisible(isAdmin);
        btnProdutos.setVisible(isAdmin);
        btnLucro.setVisible(isAdmin);
        btnFuncionario.setVisible(isAdmin);
    }

    
    @FXML
    private void btnEstoque_Click(ActionEvent event) throws IOException {
        // Obtém a cena atual e a adiciona à pilha
        Stack<Scene> cenaAnterior = Menu_viewController.getCenaAnterior();
        Scene cenaAtual = ((Node) event.getSource()).getScene();
        cenaAnterior.push(cenaAtual);

        // Carrega a tela de pedidos
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/view/estoque_view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void btnProdutos_Click(ActionEvent event) throws IOException {
        // Obtém a cena atual e a adiciona à pilha
        Stack<Scene> cenaAnterior = Menu_viewController.getCenaAnterior();
        Scene cenaAtual = ((Node) event.getSource()).getScene();
        cenaAnterior.push(cenaAtual);

        // Carrega a tela de pedidos
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/view/intem_view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void btnLucro_Click(ActionEvent event) throws IOException {
        // Obtém a cena atual e a adiciona à pilha
        Stack<Scene> cenaAnterior = Menu_viewController.getCenaAnterior();
        Scene cenaAtual = ((Node) event.getSource()).getScene();
        cenaAnterior.push(cenaAtual);

        // Carrega a tela de pedidos
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/view/lucro_view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void btnFuncionario_Click(ActionEvent event) throws IOException {
            // Obtém a cena atual e a adiciona à pilha
            Stack<Scene> cenaAnterior = Menu_viewController.getCenaAnterior();
            Scene cenaAtual = ((Node) event.getSource()).getScene();
            cenaAnterior.push(cenaAtual);

            // Carrega a tela de pedidos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/view/usuario_view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    @FXML
    private void btnDeslogar_Click(ActionEvent event) {
        // Obtém o Stage atual
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Fecha o Stage atual
        stage.close();

        try {
            // Carrega a tela de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/view/login_view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            // Configura a visibilidade dos botões no menu
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/br/com/fatec/view/menu_view.fxml"));
            Parent menuRoot = menuLoader.load();
            Menu_viewController menuController = menuLoader.getController();
            menuController.configurarVisibilidadeBotoes(false); // Define como não administrador
            menuController.setNomeUsuario(""); // Limpa o nome do usuário (opcional)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnPedidos_Click(ActionEvent event) throws IOException {
        // Obtém a cena atual e a adiciona à pilha
        Stack<Scene> cenaAnterior = Menu_viewController.getCenaAnterior();
        Scene cenaAtual = ((Node) event.getSource()).getScene();
        cenaAnterior.push(cenaAtual);

        // Carrega a tela de pedidos
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/view/pedido_view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
