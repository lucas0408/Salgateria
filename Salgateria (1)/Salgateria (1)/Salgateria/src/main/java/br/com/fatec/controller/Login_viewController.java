/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.view;

import br.com.fatec.DAO.UsuarioDAO;
import br.com.fatec.controller.Menu_viewController;
import br.com.fatec.model.Usuario;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class Login_viewController implements Initializable {

    @FXML
    private HBox root;
    @FXML
    private Pane pane_azul;
    @FXML
    private TextField txtCPF;
    @FXML
    private CheckBox cbkMostrarSenha;
    @FXML
    private Button btnLogar;
    @FXML
    private PasswordField txtSenha;
    @FXML
    private TextField txtSenhaInvisivel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureCPFTextField();
    }

    @FXML
    private void checkBoxMostrarSenha(ActionEvent event) {
        if (cbkMostrarSenha.isSelected()) {
            txtSenha.setVisible(false);
            txtSenhaInvisivel.setText(txtSenha.getText());
            txtSenhaInvisivel.setVisible(true);
        } else {
            txtSenhaInvisivel.setVisible(false);
            txtSenha.setText(txtSenhaInvisivel.getText());
            txtSenha.setVisible(true);
        }
    }

    @FXML
    private void bntLogarClick(ActionEvent event) throws IOException {
        String cpf = txtCPF.getText();
        String senha;
        if (cbkMostrarSenha.isSelected()) {
            senha = txtSenhaInvisivel.getText();
        } else {
            senha = txtSenha.getText();
        }

        if (cpf.equals("000.000.000-00") && senha.equalsIgnoreCase("Admin")) {
            chamarMenu(event, true, "Administrador");
        } else {
            try {
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                Usuario usuario = usuarioDAO.buscaPorCPFESenha(cpf, senha);

                if (usuario != null) {
                    chamarMenu(event, false, usuario.getNome());
                    System.out.println("Usuário autenticado: " + usuario.getNome());
                } else {
                    exibirAlerta("Erro", "CPF ou senha inválidos");
                }
            } catch (SQLException | IOException ex) {
                exibirAlerta("Erro", "Erro ao Logar: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void chamarMenu(ActionEvent event, boolean isAdmin, String nomeUsuario) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/view/menu_view.fxml"));
        Parent root = loader.load();
        Menu_viewController controller = loader.getController();
        controller.setIsAdmin(isAdmin, ""); // Passa uma string vazia como segundo argumento
        controller.setNomeUsuario(nomeUsuario);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    private void exibirAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    private void configureCPFTextField() {
        txtCPF.textProperty().addListener((observable, oldValue, newValue) -> {
            String digits = newValue.replaceAll("[^\\d]", "");

            if (digits.length() > 11) {
                digits = digits.substring(0, 11);
            }

            StringBuilder formatted = new StringBuilder(digits);
            if (digits.length() > 3) {
                formatted.insert(3, '.');
            }
            if (digits.length() > 6) {
                formatted.insert(7, '.');
            }
            if (digits.length() > 9) {
                formatted.insert(11, '-');
            }

            if (!formatted.toString().equals(newValue)) {
                txtCPF.setText(formatted.toString());
                txtCPF.positionCaret(formatted.length());
            }
        });

        txtCPF.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[\\d\\.\\-]*") && newText.length() <= 14) {
                return change;
            }
            return null;
        }));
    }

}
