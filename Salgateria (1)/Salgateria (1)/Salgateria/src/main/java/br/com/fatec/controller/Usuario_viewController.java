/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.DAO.UsuarioDAO;
import br.com.fatec.model.Usuario;
import java.io.IOException;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.Collection;
import java.util.Stack;
import java.util.function.UnaryOperator;
import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

public class Usuario_viewController implements Initializable {

    @FXML
    private TextField txtCPF;
    @FXML
    private TextField txtNome;
    @FXML
    private PasswordField txtSenha;
    @FXML
    private PasswordField txtConfirmarSenha;
    @FXML
    private DatePicker txtNascimento;
    @FXML
    private TextField txtFone;
    @FXML
    private TextField txtCEP;
    @FXML
    private TextField txtLogradouro;
    @FXML
    private TextField txtNumero;
    @FXML
    private TextField txtBairro;
    @FXML
    private TextField txtCidade;
    @FXML
    private TextField txtUF;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnExcluir;
    @FXML
    private TableView<Usuario> TableView;
    @FXML
    private TableColumn<Usuario, String> tcCPF;
    @FXML
    private TableColumn<Usuario, String> tcNome;
    @FXML
    private TableColumn<Usuario, String> tcCEP;
    @FXML
    private TextField txtBuscaCPF;
    @FXML
    private TextField txtBuscaNome;
    @FXML
    private TabPane tabpaneCadastro;
    @FXML
    private Tab tabCadastro;
    @FXML
    private Button btnMenu1;
    @FXML
    private Button btnMenu2;
    @FXML
    private Button btnAtualizar;

    private Usuario selectedUser;
    @FXML
    private HBox root;
    @FXML
    private AnchorPane anchor_pane;
    @FXML
    private Pane pane_azul;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnBusca;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureCPFTextField();
        configurePhoneTextField();
        configureCEPTextField();
        configureUFField();
        configureBuscaCPFTextField();
        btnAtualizar.setVisible(false);
        TableView.setItems(FXCollections.observableArrayList());
        
        // Definindo as fábricas de valores de célula
        tcCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcCEP.setCellValueFactory(new PropertyValueFactory<>("cep"));

        try {
            carregarDadosTableView();
        } catch (SQLException ex) {
            exibirAlerta("Erro", "Erro ao carregar dados: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void carregarDadosTableView() throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Collection<Usuario> listaUsuarios = usuarioDAO.lista("");
        TableView.setItems(FXCollections.observableArrayList(listaUsuarios));
    }


    @FXML
    private void btnCadastrar_click(ActionEvent event) {
        try {
            if (txtCPF.getText().isBlank() || txtNome.getText().isBlank() || txtSenha.getText().isBlank() ||
                txtConfirmarSenha.getText().isBlank() || txtNascimento.getValue() == null ||
                txtFone.getText().isBlank() || txtCEP.getText().isBlank() || txtLogradouro.getText().isBlank() ||
                txtNumero.getText().isBlank() || txtBairro.getText().isBlank() || txtCidade.getText().isBlank() ||
                txtUF.getText().isBlank()) {
                exibirAlerta("Erro", "Todos os campos são obrigatórios.");
                return;
            }
            if (!txtSenha.getText().equals(txtConfirmarSenha.getText())) {
                exibirAlerta("Erro", "As senhas não coincidem.");
                return;
            }

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuarioExistente = usuarioDAO.buscaPorCPF(txtCPF.getText());

            if (usuarioExistente == null) {
                // Inserir novo usuário
                Usuario novoUsuario = new Usuario();
                novoUsuario.setCpf(txtCPF.getText());
                novoUsuario.setNome(txtNome.getText());
                novoUsuario.setSenha(txtSenha.getText());
                novoUsuario.setData_nascimento(txtNascimento.getValue());
                novoUsuario.setFone(txtFone.getText());
                novoUsuario.setCep(txtCEP.getText());
                novoUsuario.setLogradouro(txtLogradouro.getText());
                novoUsuario.setNumero(txtNumero.getText());
                novoUsuario.setBairro(txtBairro.getText());
                novoUsuario.setCidade(txtCidade.getText());
                novoUsuario.setUf(txtUF.getText());

                boolean inserido = usuarioDAO.insere(novoUsuario);
                if (inserido) {
                    exibirAlerta("Sucesso", "Usuário inserido com sucesso.");
                    carregarDadosTableView();
                } else {
                    exibirAlerta("Erro", "Falha ao inserir o usuário.");
                }
            } else {
                // Exibir mensagem de erro se o CPF já existir
                exibirAlerta("Erro", "Usuário com esse CPF já existe.");
            }
            limparCampos();
            txtCPF.setEditable(true);
            txtCPF.requestFocus();
        } catch (SQLException ex) {
            exibirAlerta("Erro", "Erro ao processar dados: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void btnAtualizar_click(ActionEvent event) {
        try {
            if (txtCPF.getText().isBlank() || txtNome.getText().isBlank() || txtSenha.getText().isBlank() ||
                txtConfirmarSenha.getText().isBlank() || txtNascimento.getValue() == null ||
                txtFone.getText().isBlank() || txtCEP.getText().isBlank() || txtLogradouro.getText().isBlank() ||
                txtNumero.getText().isBlank() || txtBairro.getText().isBlank() || txtCidade.getText().isBlank() ||
                txtUF.getText().isBlank()) {
                exibirAlerta("Erro", "Todos os campos são obrigatórios.");
                return;
            }
            if (!txtSenha.getText().equals(txtConfirmarSenha.getText())) {
                exibirAlerta("Erro", "As senhas não coincidem.");
                return;
            }

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuarioExistente = usuarioDAO.buscaPorCPF(txtCPF.getText());

            if (usuarioExistente != null) {
                // Atualizar usuário existente
                usuarioExistente.setNome(txtNome.getText());
                usuarioExistente.setSenha(txtSenha.getText());
                usuarioExistente.setData_nascimento(txtNascimento.getValue());
                usuarioExistente.setFone(txtFone.getText());
                usuarioExistente.setCep(txtCEP.getText());
                usuarioExistente.setLogradouro(txtLogradouro.getText());
                usuarioExistente.setNumero(txtNumero.getText());
                usuarioExistente.setBairro(txtBairro.getText());
                usuarioExistente.setCidade(txtCidade.getText());
                usuarioExistente.setUf(txtUF.getText());

                boolean atualizado = usuarioDAO.altera(usuarioExistente);
                if (atualizado) {
                    exibirAlerta("Sucesso", "Usuário atualizado com sucesso.");
                    carregarDadosTableView();
                } else {
                    exibirAlerta("Erro", "Falha ao atualizar o usuário.");
                }
            } else {
                exibirAlerta("Erro", "Usuário não encontrado.");
            }
            limparCampos();
            txtCPF.setEditable(true);
            txtCPF.requestFocus();

            // Restaurar visibilidade dos botões
            btnCadastrar.setVisible(true);
            btnAtualizar.setVisible(false);
        } catch (SQLException ex) {
            exibirAlerta("Erro", "Erro ao processar dados: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    private void limparCampos() {
        txtCPF.clear();
        txtNome.clear();
        txtSenha.clear();
        txtConfirmarSenha.clear();
        txtNascimento.setValue(null);
        txtFone.clear();
        txtCEP.clear();
        txtLogradouro.clear();
        txtNumero.clear();
        txtBairro.clear();
        txtCidade.clear();
        txtUF.clear();
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @FXML
    private void btnExcluir_Click(ActionEvent event) throws SQLException {
        Usuario selectedUser = TableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            boolean removed = usuarioDAO.remove(selectedUser);

            if (removed) {
                exibirAlerta("Sucesso", "Usuário removido com sucesso.");
                carregarDadosTableView();
            } else {
                exibirAlerta("Erro", "Falha ao remover o usuário.");
            }
        }
    }


    @FXML
    private void btnEditar_Click(ActionEvent event) {
        Usuario selectedUser = TableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            txtCPF.setText(selectedUser.getCpf());
            txtCPF.setEditable(false); 
            txtNome.setText(selectedUser.getNome());
            txtSenha.setText(selectedUser.getSenha());
            txtConfirmarSenha.setText(selectedUser.getSenha());
            txtNascimento.setValue(selectedUser.getData_nascimento());
            txtFone.setText(selectedUser.getFone());
            txtCEP.setText(selectedUser.getCep());
            txtLogradouro.setText(selectedUser.getLogradouro());
            txtNumero.setText(selectedUser.getNumero());
            txtBairro.setText(selectedUser.getBairro());
            txtCidade.setText(selectedUser.getCidade());
            txtUF.setText(selectedUser.getUf());

            tabpaneCadastro.getSelectionModel().select(tabCadastro);
            Platform.runLater(() -> txtNome.requestFocus());

            btnCadastrar.setVisible(false);
            btnAtualizar.setVisible(true);
        }
    }

@FXML
    private void btnBusca_Click(ActionEvent event) throws SQLException {
        String termoBuscaCPF = txtBuscaCPF.getText().trim();
        String termoBuscaNome = txtBuscaNome.getText().trim();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> listaUsuarios = new ArrayList<>();

        if (termoBuscaCPF.isEmpty() && termoBuscaNome.isEmpty()) {
            listaUsuarios = new ArrayList<>(usuarioDAO.lista(""));
        } else {
            StringBuilder query = new StringBuilder();

            if (!termoBuscaCPF.isEmpty()) {
                query.append("cpf LIKE '").append(termoBuscaCPF).append("%'");
            }

            if (!termoBuscaNome.isEmpty()) {
                if (query.length() > 0) {
                    query.append(" AND ");
                }
                query.append("nome LIKE '%").append(termoBuscaNome).append("%'");
            }

            listaUsuarios = new ArrayList<>(usuarioDAO.lista(query.toString()));
        }

        TableView.getItems().clear();
        TableView.getItems().addAll(listaUsuarios);
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
   private void configureCPFTextField() {
        txtCPF.textProperty().addListener((observable, oldValue, newValue) -> {
            // Remove todos os caracteres não numéricos
            String digits = newValue.replaceAll("[^\\d]", "");

            // Limitar o número de dígitos a 11
            if (digits.length() > 11) {
                digits = digits.substring(0, 11);
            }

            // Formatar o texto com pontos e hífen
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

            // Atualizar o texto apenas se a formatação mudou
            if (!formatted.toString().equals(newValue)) {
                txtCPF.setText(formatted.toString());
                // Posicionar o cursor no final do texto
                txtCPF.positionCaret(formatted.length());
            }
        });

        txtCPF.setTextFormatter(new TextFormatter<>(change -> {
            // Permitir apenas dígitos e pontos e hífens já formatados
            String newText = change.getControlNewText();
            if (newText.matches("[\\d\\.\\-]*") && newText.length() <= 14) {
                return change;
            }
            return null;
        }));
    }

    private void configurePhoneTextField() {
        txtFone.textProperty().addListener((observable, oldValue, newValue) -> {
            // Remove todos os caracteres não numéricos
            String digits = newValue.replaceAll("[^\\d]", "");

            // Limitar o número de dígitos a 11
            if (digits.length() > 11) {
                digits = digits.substring(0, 11);
            }

            // Formatar o texto com parênteses, espaço e hífen
            StringBuilder formatted = new StringBuilder(digits);
            if (digits.length() > 0) {
                formatted.insert(0, '(');
            }
            if (digits.length() > 2) {
                formatted.insert(3, ')');
            }
            if (digits.length() > 2) {
                formatted.insert(4, ' ');
            }
            if (digits.length() > 7) {
                formatted.insert(10, '-');
            }

            // Atualizar o texto apenas se a formatação mudou
            if (!formatted.toString().equals(newValue)) {
                txtFone.setText(formatted.toString());
                // Posicionar o cursor no final do texto
                txtFone.positionCaret(formatted.length());
            }
        });

        txtFone.setTextFormatter(new TextFormatter<>(change -> {
            // Permitir apenas dígitos, parênteses, espaços e hífens já formatados
            String newText = change.getControlNewText();
            if (newText.matches("[\\d\\(\\)\\- ]*") && newText.length() <= 15) {
                return change;
            }
            return null;
        }));
    }

    private void configureCEPTextField() {
        txtCEP.textProperty().addListener((observable, oldValue, newValue) -> {
            // Remove todos os caracteres não numéricos
            String digits = newValue.replaceAll("[^\\d]", "");

            // Limitar o número de dígitos a 8
            if (digits.length() > 8) {
                digits = digits.substring(0, 8);
            }

            // Formatar o texto com hífen
            StringBuilder formatted = new StringBuilder(digits);
            if (digits.length() > 5) {
                formatted.insert(5, '-');
            }

            // Atualizar o texto apenas se a formatação mudou
            if (!formatted.toString().equals(newValue)) {
                txtCEP.setText(formatted.toString());
                // Posicionar o cursor no final do texto
                txtCEP.positionCaret(formatted.length());
            }
        });

        txtCEP.setTextFormatter(new TextFormatter<>(change -> {
            // Permitir apenas dígitos e o hífen já formatado
            String newText = change.getControlNewText();
            if (newText.matches("[\\d\\-]*") && newText.length() <= 9) {
                return change;
            }
            return null;
        }));
    }

    private void configureUFField() {
        txtUF.textProperty().addListener((observable, oldValue, newValue) -> {
            // Limitar para apenas dois caracteres
            if (newValue.length() > 2) {
                txtUF.setText(oldValue);
            }

            // Aceitar apenas letras
            if (!newValue.matches("[a-zA-Z]*")) {
                txtUF.setText(newValue.replaceAll("[^a-zA-Z]", ""));
            }
        });

        txtUF.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-Z]{0,2}")) {
                return change;
            }
            return null;
        }));
    }

    private void configureBuscaCPFTextField() {
        txtBuscaCPF.textProperty().addListener((observable, oldValue, newValue) -> {
            // Remove todos os caracteres não numéricos
            String digits = newValue.replaceAll("[^\\d]", "");

            // Limitar o número de dígitos a 11
            if (digits.length() > 11) {
                digits = digits.substring(0, 11);
            }

            // Formatar o texto com pontos e hífen
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

            // Atualizar o texto apenas se a formatação mudou
            if (!formatted.toString().equals(newValue)) {
                txtBuscaCPF.setText(formatted.toString());
                // Posicionar o cursor no final do texto
                txtBuscaCPF.positionCaret(formatted.length());
            }
        });

        txtBuscaCPF.setTextFormatter(new TextFormatter<>(change -> {
            // Permitir apenas dígitos e pontos e hífens já formatados
            String newText = change.getControlNewText();
            if (newText.matches("[\\d\\.\\-]*") && newText.length() <= 14) {
                return change;
            }
            return null;
        }));
    }
}

