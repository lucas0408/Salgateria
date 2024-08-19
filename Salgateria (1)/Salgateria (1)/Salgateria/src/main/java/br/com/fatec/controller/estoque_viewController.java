/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.Banco.Banco;
import br.com.fatec.DAO.EstoqueDAO;
import br.com.fatec.DAO.InsumoDAO;
import br.com.fatec.model.Estoque;
import br.com.fatec.model.Insumo;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;
import java.time.LocalDate;
import static java.time.LocalDate.now;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import java.util.Stack;

public class estoque_viewController implements Initializable {
    
    @FXML
    private HBox root;
    @FXML
    private Pane pane_azul;
    @FXML
    private Button btnSalvar;
    @FXML
    private TextField txtEstoque;
    @FXML
    private ComboBox<String> cmbInsumo;
    @FXML
    private TextField txtQuantidade;
    @FXML
    private DatePicker txtDate;
    @FXML
    private TextField txtValor;
    @FXML
    private ToggleGroup Entrada;
    @FXML
    private RadioButton rdSaida;
    @FXML
    private RadioButton rdEntrada;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnVoltar;
    @FXML
    private TableView<EstoqueView> table;
    @FXML
    private TableColumn<EstoqueView, String> tcProduto;
    @FXML
    private TableColumn<EstoqueView, String> tcQuantidade;
    @FXML
    private TableColumn<EstoqueView, String> tcValor;
    @FXML
    private TableColumn<EstoqueView, Date> tcData;
    @FXML
    private Tab TabAlterar;
    @FXML
    private Button btnVoltarMenu;
    @FXML
    private TabPane TabPane;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnRemover;
   
    private ObservableList<String> listaInsumo =  
    FXCollections.observableArrayList();
        
    InsumoDAO dao = new InsumoDAO();
    
    EstoqueDAO estoquedao = new EstoqueDAO();
    
    String insumo;
    
    DecimalFormat df = new DecimalFormat("0.00");
   
    DecimalFormat tf = new DecimalFormat("0.000");
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
                try {
            verificarValidade();
        } catch (SQLException ex) {
            System.out.println("Erro ao verifacar data de validade, erro: " + ex.getMessage());
        }
         cmbInsumo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue != null){
                apagar();
            }
        });
        
        try {
            setColuns();
        } catch (SQLException ex) {
        }
        
        listaInsumo.clear();
        try {
            for (Insumo aux : dao.lista("")) {
                listaInsumo.add(aux.getNome());
            }
            cmbInsumo.setItems(listaInsumo);
        } catch (SQLException ex) {
        }
        

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
        txtValor.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            newValue = newValue.replaceAll(",",".");
            if(newValue.length()>0){
                try{
                    Double.parseDouble(newValue);
                    txtValor.setText(newValue.replaceAll(",","."));
                }catch(Exception e){
                    txtValor.setText(oldValue);
                }
            }
        });
        txtDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                if(txtEstoque.getText().length() != 0){
                    txtEstoque.setText("");
                    txtQuantidade.setText("");
                    txtValor.setText("");
                }
            }
        });
        configuraChangeValueComboProduto();
    }    
    
    @FXML
    private void btnEditar_Click(ActionEvent event) {
        
        EstoqueView selectedItem = table.getSelectionModel().getSelectedItem();
        
        if(selectedItem != null){
            TabPane.getSelectionModel().select(TabAlterar);
            cmbInsumo.getSelectionModel().select(selectedItem.getNomeInsumo());
            txtDate.setValue(selectedItem.getData().toLocalDate());
            txtEstoque.setText(selectedItem.getQuantidade());
            txtValor.setText(selectedItem.getValor().replace("R$ ", ""));
            
        }
    }
    
    @FXML
    private void btnCadastrar_Click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/view/cadstro_insumo_view.fxml"));
        Parent root = loader.load();
            // Obtém o Stage atual
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void btnRemover_Click(ActionEvent event) throws SQLException {
        EstoqueView selectedItem = table.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            Estoque estoque = new Estoque();
            estoque.setInsumo(dao.buscaNome(new Insumo(selectedItem.getNomeInsumo(), "", 0.0f)));
            estoque.setData_validade(selectedItem.getData());
            estoquedao.remove(estoquedao.buscaNome(estoque));
            setColuns();
        }
    }
    
    @FXML
    private void btnVoltar_Click(ActionEvent event) throws IOException {
        voltarMenu(event);
    }

    @FXML
    private void btnVoltarMenu_click(ActionEvent event) throws IOException {
        voltarMenu(event);
    }
    
    @FXML
    private void btnSalvar_Click(ActionEvent event) throws SQLException {
        txtValor.setEditable(true);
        if(txtQuantidade.getText().isBlank() || txtQuantidade.getText().isBlank() || 
            txtDate.getValue() == null || txtValor.getText().isBlank() || (rdEntrada.isSelected() == false && rdSaida.isSelected() == false)){
            input("confira se todos os campos foram preenchidos");
            return;
        }
        LocalDate dataAgora = LocalDate.now();
        if(txtDate.getValue().isBefore(dataAgora)){
            input("não pode cadastrar produtos vencidos");
            return;
        }
                Banco.conectar();
                Date date1 = Date.valueOf(txtDate.getValue());
                Estoque estoque = new Estoque(dao.buscaNome(new Insumo(insumo, "", 0.0f)), date1, Float.parseFloat((txtQuantidade.getText())), Float.parseFloat(txtValor.getText()));
                if(estoquedao.buscaID(estoque) != null){
                    if(rdSaida.isSelected()){
                        if(estoque.retirar(estoquedao.buscaID(estoque).getQuantidade(), estoquedao.buscaID(estoque).getValor())){
                            input("estoque insuficiente! você está tentando tirar mais do que o estoque disponível");
                            return;
                        }else{
                            if (estoque.getQuantidade() < 0.09){
                                estoquedao.remove(estoquedao.buscaID(estoque));
                                input("dados apagados com sucesso");
                                apagar();
                                setColuns();
                                return;
                            }
                             estoque.setId(estoquedao.buscaID(estoque).getId());
                             estoquedao.altera(estoque);
                             input("dados alterados com sucesso");
                        };
                    }else{
                       estoque.adicionar(estoquedao.buscaID(estoque).getQuantidade(), estoquedao.buscaID(estoque).getValor());
                       estoque.setId(estoquedao.buscaID(estoque).getId());
                       estoquedao.altera(estoque);
                       input("dados alterados com sucesso");
                    }
                    txtValor.setText(df.format(estoque.getValor()));
                    txtEstoque.setText(tf.format(estoque.getQuantidade()));
                    txtQuantidade.setText("");
                 }else{
                    if(rdSaida.isSelected()){
                        input("item não cadastrado no estoque");
                        return;
                    }else{
                        estoquedao.insere(estoque);
                        input("dados cadastrados com sucesso");
                        apagar();
                    }
                }
                setColuns();
                if(estoquedao.consulta(estoque).getQuantidade() < estoque.getInsumo().getEstoque_minimo()){
                    input("o estoque minimo desse produto foi atingido, reponha quando possível");
                }
            }
            
    public void verificarValidade() throws SQLException{
        for(Estoque aux : estoquedao.lista("")){
            if(aux.getData_validade().toLocalDate().isBefore(LocalDate.now())){
                estoquedao.remove(aux);
                input("item removido por exceder a data de validade \nitem:" 
                        + aux.getInsumo().getNome() + "\nquantidade(KG ou L): " + aux.getQuantidade());
            }
        }
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
    }void configuraChangeValueComboProduto() {
        //programando o evento change da combo para
        //exibir seu conteudo nos texts
        cmbInsumo.valueProperty().addListener((value, velho, novo) -> {
        if(novo != null) {
            insumo = value.getValue();
        }
        });
    }
    
    private void input(String txt){
         Alert alerta = new Alert(Alert.AlertType.INFORMATION);
         alerta.setTitle("Mensagem");
         alerta.setHeaderText(txt.toUpperCase());
         alerta.setContentText("");
         alerta.showAndWait();
    }
    
    private void apagar(){
        txtQuantidade.setText("");
        txtValor.setText("");
        txtEstoque.setText("");
        txtDate.setValue(null);
    }
    
    
    private void setColuns() throws SQLException{
        listaEstoque.clear();
        for(Estoque aux : estoquedao.lista("")){
            listaEstoque.add(new EstoqueView(aux));
        }
        tcProduto.setCellValueFactory( 
                new PropertyValueFactory<>("nomeInsumo")); 
        tcQuantidade.setCellValueFactory( 
                new PropertyValueFactory<>("quantidade")); 
        tcData.setCellValueFactory( 
                new PropertyValueFactory<>("data")); 
        tcValor.setCellValueFactory( 
                new PropertyValueFactory<>("valor"));
        
        table.setItems(listaEstoque);
        

    }
    
       private ObservableList<EstoqueView> listaEstoque = 
               FXCollections.observableArrayList();
       
       public class EstoqueView {
            private Date data;
            private String nomeInsumo;
            private String quantidade;
            private Float valor;

    public EstoqueView(Estoque estoque) {
        this.data = estoque.getData_validade();
        this.nomeInsumo = estoque.getInsumo().getNome();
        this.quantidade = tf.format(estoque.getQuantidade());
        this.valor = estoque.getValor();
    }

        public Date getData() {
            return data;
        }

        public void setData(Date data) {
            this.data = data;
        }

        public String getNomeInsumo() {
            return nomeInsumo;
        }

        public void setNomeInsumo(String nomeInsumo) {
            this.nomeInsumo = nomeInsumo;
        }

        public String getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(String quantidade) {
            this.quantidade = tf.format(quantidade);
        }

        public String getValor() {
            return "R$ " + df.format(valor);
        }

        public void setValor(float valor) {
            this.valor = valor;
        }
   }
}
