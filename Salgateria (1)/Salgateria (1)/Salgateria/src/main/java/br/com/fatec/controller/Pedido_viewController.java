/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.DAO.EstoqueDAO;
import br.com.fatec.DAO.ItemDAO;
import br.com.fatec.DAO.Item_pedidoDAO;
import br.com.fatec.DAO.PedidoDAO;
import br.com.fatec.model.Estoque;
import br.com.fatec.model.Item;
import br.com.fatec.model.Item_pedido;
import br.com.fatec.model.Pedido;
import br.com.fatec.controller.estoque_viewController;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.Stack;

/**
 * FXML Controller class
 *
 * @author User
 */
public class Pedido_viewController implements Initializable {
    DecimalFormat df = new DecimalFormat("0.00");
   
    DecimalFormat tf = new DecimalFormat("0.000");
    @FXML
    private HBox root;
    @FXML
    private TableView<pedidoView> TablePedidos;
    @FXML
    private TableColumn<pedidoView, String> tcLogradouro;
    @FXML
    private TableColumn<pedidoView, String> tcNumero;
    @FXML
    private TableColumn<pedidoView, String> tcHora;
    @FXML
    private TableColumn<pedidoView, String> tcData;
    @FXML
    private TableColumn<pedidoView, String> tcNumsalgado;
    @FXML
    private TableColumn<pedidoView, String> tcTotal;
    @FXML
    private Button btnAlterar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnVisualizar;
    @FXML
    private TextField txtNome;
    @FXML
    private Button btnCarrinho;
    @FXML
    private ComboBox<Item> cmbSalgado;
    @FXML
    private TextField txtLogradouro;
    @FXML
    private TextField txtNum;
    @FXML
    private TextField txtQuantidade;
    @FXML
    private DatePicker txtData;
    @FXML
    private TextField txtHora;
    @FXML
    private Pane pane_azul;
    @FXML
    private TextField txtValor;
    @FXML
    private Button btnEditar;
    @FXML
    private TableView<CarrinhoView> TableView;
    @FXML
    private TableColumn<CarrinhoView, String> tcSalgado;
    @FXML
    private TableColumn<CarrinhoView, String> tcQuantidade;
    @FXML
    private TableColumn<CarrinhoView, String> tcValor;
    @FXML
    private TabPane TabPane;
    @FXML
    private Tab TabCadastro;
    @FXML
    private Button btnConfirma;
    @FXML
    private ComboBox<String> cmbPagamento;
    @FXML
    private Button btnRemover;
    @FXML
    private TableView<EstoqueView> TableEStoque;
    @FXML
    private TableColumn<EstoqueView, String> tcProduto;
    @FXML
    private TableColumn<EstoqueView, String> tcQtd;
    @FXML
    private TableColumn<EstoqueView, String> tcDataValidade;
    
        
        
    
    private ObservableList<String> listaPagamento =  
    FXCollections.observableArrayList();
    
    private ObservableList<CarrinhoView> listaCarrinho =  
    FXCollections.observableArrayList();
    
    private ObservableList<pedidoView> listaPedidos =  
    FXCollections.observableArrayList();
    
    private ObservableList<EstoqueView> listaEstoque =  
    FXCollections.observableArrayList();
    
    private ObservableList<Item_pedido> listaItem =  
    FXCollections.observableArrayList();
    
    private ObservableList<Item> listaSalgados =  
    FXCollections.observableArrayList();
    
    PedidoDAO pedidodao = new PedidoDAO();
    
    Item_pedidoDAO item_pedidodao = new Item_pedidoDAO();
    
    ItemDAO itemdao = new ItemDAO();
    
    EstoqueDAO estoquedao = new EstoqueDAO();
    @FXML
    private Button btnVoltarMenu;
    @FXML
    private Button btnVoltarMenu2;
    @FXML
    private Button btnVoltarMenu3;
    @FXML
    private Button btnVoltarMenu4;
    
    private Pedido pedido;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      try {
            for(Estoque aux : estoquedao.lista("")){
                if(aux.getData_validade().toLocalDate().isBefore(LocalDate.now())){
                    estoquedao.remove(aux);
                    input("item removido por exceder a data de validade \nitem:"
                            + aux.getInsumo().getNome() + "\nquantidade(KG ou L): " + aux.getQuantidade());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pedido_viewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            // Allow deletion
            if (change.isDeleted()) {
                return change;
            }

            // Ensure only digits and colon are allowed
            if (!newText.matches("[0-9:]*")) {
                return null;
            }

            // Automatically add colon after the second digit
            if (newText.length() == 2 && !newText.contains(":")) {
                change.setText(change.getText() + ":");
                change.setCaretPosition(change.getCaretPosition() + 1);
                change.setAnchor(change.getAnchor() + 1);
            }

            // Limit to 5 characters
            if (newText.length() > 5) {
                return null;
            }

            return change;
        });

        txtHora.setTextFormatter(textFormatter);

        
        txtNum.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtNum.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        txtQuantidade.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtQuantidade.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        try {
            iniciaListas();
        } catch (SQLException ex) {
            System.out.println("ocorreu um erro: " + ex);
        }
        
    }    
    
    private void iniciaListas() throws SQLException{
        preencherListaPedido();
        
        listaPagamento.add("CARTÃO");
        listaPagamento.add("PIX");
        listaPagamento.add("DINHEIRO");
        cmbPagamento.setItems(listaPagamento);
        
        for(Item x : itemdao.lista("")){
            listaSalgados.add(x);
         }
         cmbSalgado.setItems(listaSalgados);
         
         preencherListaEstoque();
        
    }
    @FXML
    private void btnRemover_Click(ActionEvent event) throws SQLException {
        Pedido_viewController.pedidoView selectedItem = TablePedidos.getSelectionModel().getSelectedItem();
        
        if(selectedItem == null){
            return;
        }
        
        if(question()){
            Pedido pedido = new Pedido();
            pedido.setId(selectedItem.getId());

            Item_pedido item_pedido = new Item_pedido();
            item_pedido.setPedido(pedido);
            item_pedidodao.remove(item_pedido);

            pedidodao.remove(pedido);

            input("pedido excluido com sucesso");
            preencherListaPedido();
        };
    }

    @FXML
    private void btnAlterar_Click(ActionEvent event) throws SQLException {
        Pedido_viewController.pedidoView selectedItem = TablePedidos.getSelectionModel().getSelectedItem();
        
        if(selectedItem == null){
            return;
        }
                
        pedido = new Pedido();
        pedido.setId(selectedItem.getId());
        pedido = pedidodao.buscaID(pedido);
        
        txtNome.setText(pedido.getNome());
        txtLogradouro.setText(pedido.getLogradouro());
        txtNum.setText(Integer.toString(pedido.getNumero()));
        txtData.setValue(pedido.getData());
        cmbPagamento.getSelectionModel().select(pedido.getPagamento());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String timeString = sdf.format(pedido.getHorario());
        txtHora.setText(timeString);
        
        listaCarrinho.clear();
        listaItem.clear();
        listaItem.addAll(item_pedidodao.lista("pedido_id = " + pedido.getId()));
        for(Item_pedido x : item_pedidodao.lista("pedido_id = " + pedido.getId())){
            listaCarrinho.add(new CarrinhoView(x));
        }
        preencherLista();
        TabPane.getSelectionModel().select(TabCadastro);
        
    }

    @FXML
    private void btnExcluir_Click(ActionEvent event) {
        Pedido_viewController.CarrinhoView selectedItem = TableView.getSelectionModel().getSelectedItem();
        
        if(selectedItem == null){
            return;
        }
        
        if(selectedItem != null){
            int index = 0;
            for(Item_pedido x : listaItem){
                if(selectedItem.getNome().equals(x.getItem().getNome())){
                    break;
                }
                index ++;
            }
            listaItem.remove(index);
            
            listaCarrinho.remove(selectedItem);
            
        }
        TableView.refresh();
        calcularTotal();
    }

    @FXML
    private void btnVisualizar_Click(ActionEvent event) throws SQLException, IOException {
        Pedido_viewController.pedidoView selectedItem = TablePedidos.getSelectionModel().getSelectedItem();
        
        if(selectedItem == null){
            return;
        }
                
        pedido = new Pedido();
        pedido.setId(selectedItem.getId());
        pedido = pedidodao.buscaID(pedido);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/view/visualizar_pedido_view.fxml"));
        Parent root = loader.load();
            // Obtém o Stage atual
            
        Visualizar_pedido_viewController PedidoController = loader.getController();
        PedidoController.setPedido(pedido);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Configura a nova cena
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Segunda Cena");
        stage.show();
        PedidoController.initializeTable();
    }

    @FXML
    private void btnCarrinho_Clik(ActionEvent event) {
        if(cmbSalgado.getSelectionModel().isEmpty() || txtQuantidade.getText().isBlank()){
            return;
        }
        Item_pedido item = new Item_pedido(new Pedido(), cmbSalgado.getSelectionModel().getSelectedItem(), Integer.parseInt(txtQuantidade.getText()));
        CarrinhoView carrinho = new CarrinhoView(item);
        int aux = 0;
        for(Item_pedido x : listaItem){
            if(x.getItem().getId() == item.getItem().getId()){
                x.setQuantidade(item.getQuantidade());
                aux++;
            }
        }
        for(CarrinhoView x : listaCarrinho){
            if(x.getNome().equals(carrinho.getNome())){
                x.setQuantidade(carrinho.getQuantidade());
                x.setValor(carrinho.getValor());
                TableView.refresh();
            }
        }
        if(aux < 1){
            listaItem.add(item);
            listaCarrinho.add(carrinho);
            preencherLista();
        }
        calcularTotal();
    }
    
        
    private void preencherLista(){
        tcSalgado.setCellValueFactory( 
                new PropertyValueFactory<>("nome")); 
        tcQuantidade.setCellValueFactory( 
                new PropertyValueFactory<>("quantidade")); 
        tcValor.setCellValueFactory( 
                new PropertyValueFactory<>("valor")); 
        TableView.setItems(listaCarrinho);
        calcularTotal();
    }
    
        private void preencherListaEstoque() throws SQLException{
         for(Estoque x : estoquedao.lista("")){
             listaEstoque.add(new EstoqueView(x));
         }
        tcQtd.setCellValueFactory( 
                new PropertyValueFactory<>("quantidade")); 
        tcDataValidade.setCellValueFactory( 
                new PropertyValueFactory<>("data")); 
        tcProduto.setCellValueFactory( 
                new PropertyValueFactory<>("NomeInsumo")); 
        TableEStoque.setItems(listaEstoque);
        calcularTotal();
    }
    
    public void preencherListaPedido() throws SQLException{
        
        listaPedidos.clear();
        for(Pedido x : pedidodao.lista("ativo = 1")){
            listaPedidos.add(new pedidoView(x));
        }
        tcLogradouro.setCellValueFactory( 
                new PropertyValueFactory<>("logradouro")); 
        tcNumero.setCellValueFactory( 
                new PropertyValueFactory<>("num")); 
        tcHora.setCellValueFactory( 
                new PropertyValueFactory<>("horario")); 
        tcData.setCellValueFactory( 
                new PropertyValueFactory<>("data")); 
        tcNumsalgado.setCellValueFactory( 
                new PropertyValueFactory<>("numSalgado")); 
        tcTotal.setCellValueFactory( 
                new PropertyValueFactory<>("total")); 
        TablePedidos.setItems(listaPedidos);
    }
    
    @FXML
    private void btnEditar_Click(ActionEvent event) {
        Pedido_viewController.CarrinhoView selectedItem = TableView.getSelectionModel().getSelectedItem();
        
        if(selectedItem == null){
            return;
        }
        
        int index = 0;
        for(Item x : listaSalgados){
            if(x.getNome().equals(selectedItem.getNome())){
                break;
            }
            index++;
        }
        TabPane.getSelectionModel().select(TabCadastro);
        cmbSalgado.setValue(listaSalgados.get(index));
        txtQuantidade.setText(Integer.toString(selectedItem.getQuantidade()));
    }
    
    private void calcularTotal(){
        txtValor.setText("");
        float total = 0f;
        for(CarrinhoView x : listaCarrinho){
            total = total + Float.parseFloat(x.getValor().replace(" R$", ""));
            txtValor.setText(df.format(total).replace(",", ".") + " R$");
        }
    }

    @FXML
    private void btnConfirma_Click(ActionEvent event) throws SQLException {
        if(txtNome.getText().isBlank() || txtLogradouro.getText().isBlank() || txtNum.getText().isBlank() || txtData.getValue() == null || 
                txtHora.getText().isBlank() || listaCarrinho.size() < 1 || cmbPagamento.getSelectionModel().getSelectedItem().isBlank()){
            input("confira se todos os campos foram preenchidos");
            return;
        }
        
        if(txtData.getValue().isBefore(LocalDate.now())){
            input("data invalida");
            return;
        }
            LocalTime localTime = LocalTime.parse(txtHora.getText(), DateTimeFormatter.ofPattern("HH:mm"));
            Time sqlTime = Time.valueOf(localTime);
            
            if(pedido == null){
                pedido = new Pedido(txtNome.getText(), txtData.getValue(), sqlTime, Float.parseFloat(txtValor.getText().replace(" R$", "")), 
                    cmbPagamento.getSelectionModel().getSelectedItem(), txtLogradouro.getText(), Integer.parseInt(txtNum.getText()),
                    true, 0f);
                pedidodao.insere(pedido);
                for(Item_pedido x : listaItem){
                    x.setPedido(pedidodao.buscaNome(pedido));
                    item_pedidodao.insere(x);
                }
                listaItem.clear();
                input("dados cadastrados com sucesso");
                apagar();
                pedido = null;
            }else{
                pedido.setData(txtData.getValue());
                pedido.setHorario(sqlTime);
                pedido.setLogradouro(txtLogradouro.getText());
                pedido.setNome(txtNome.getText());
                pedido.setNumero(Integer.parseInt(txtNum.getText()));
                pedido.setPagamento(cmbPagamento.getSelectionModel().getSelectedItem());
                pedido.setTotal(Float.parseFloat(txtValor.getText().replace(" R$", "")));
                pedidodao.altera(pedido);
                listaItem.get(0).setPedido(pedido);
                item_pedidodao.remove(listaItem.get(0));
                for(Item_pedido x : listaItem){
                    x.setPedido(pedidodao.buscaNome(pedido));
                    item_pedidodao.insere(x);
                }
                listaItem.clear();
                input("dados atualizados com sucesso");
                pedido = null;
            }
            preencherListaPedido();
            apagar();
        
    }
    
    private void apagar(){
        txtNome.setText("");
        txtLogradouro.setText("");
        txtNum.setText("");
        txtData.setValue(null);
        txtHora.setText("");
        cmbSalgado.getSelectionModel().clearSelection();
        cmbPagamento.getSelectionModel().clearSelection();
        txtQuantidade.setText("");
        listaCarrinho.clear();
        preencherLista();
    }
        
    
     private void input(String txt){
         Alert alerta = new Alert(Alert.AlertType.INFORMATION);
         alerta.setTitle("Mensagem");
         alerta.setHeaderText(txt.toUpperCase());
         alerta.setContentText("");
         alerta.showAndWait();
    }

    private boolean question() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText(null);
        alert.setContentText("Você realmente deseja excluir este pedido?");
        
        ButtonType buttonTypeSim = new ButtonType("Sim", ButtonData.OK_DONE);
        ButtonType buttonTypeNao = new ButtonType("Não", ButtonData.CANCEL_CLOSE);

        // Configura os botões personalizados na `Alert`
        alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeSim) {
            // Ação confirmada
            return true;
        } else {
            // Ação cancelada
            return false;
        }
        
    }

    @FXML
    private void btnVoltarMenu_Click(ActionEvent event) throws IOException {
        voltarMenu(event);
    }

    @FXML
    private void btnVoltarMenu2_Click(ActionEvent event) throws IOException {
        voltarMenu(event);
    }

    @FXML
    private void btnVoltarMenu3_Click(ActionEvent event) throws IOException {
        voltarMenu(event);
    }

    @FXML
    private void btnVoltarMenu4_click(ActionEvent event) throws IOException {
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
    

        public class CarrinhoView{
            private String nome;
            private int quantidade;
            private String valor;

            public CarrinhoView(Item_pedido item) {
                this.nome = item.getItem().getNome();
                this.quantidade = item.getQuantidade();
                this.valor = df.format(item.getQuantidade() * (item.getItem().getPreco()/100)).replace(",", ".") + " R$";
            }

            public String getNome() {
                return nome;
            }

            public void setNome(String nome) {
                this.nome = nome;
            }

            public int getQuantidade() {
                return quantidade;
            }

            public void setQuantidade(int quantidade) {
                this.quantidade = quantidade;
            }

            public String getValor() {
                return valor;
            }

            public void setValor(String valor) {
                this.valor = valor.replace(",", ".");
            }
    }
        
    public class pedidoView{
            private int id;
            private String logradouro;
            private int num;
            private Time horario;
            private LocalDate data;
            private int numSalgado;
            private String total;

        public pedidoView(Pedido pedido) throws SQLException {
            this.id = pedido.getId();
            this.logradouro = pedido.getLogradouro();
            this.num = pedido.getNumero();
            this.horario = pedido.getHorario();
            this.data = pedido.getData();
            this.numSalgado = calcularNumSalgado(pedido);
            this.total = df.format(pedido.getTotal()) + " R$";
        }

        public String getTotal() {
            return total;
        }

        public int getId() {
            return id;
        }

        public void setTotal(String total) {
            this.total = total;
        }
        
        
            
        public String getLogradouro() {
            return logradouro;
        }

        public void setLogradouro(String logradouro) {
            this.logradouro = logradouro;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public Time getHorario() {
            return horario;
        }

        public void setHorario(Time horario) {
            this.horario = horario;
        }

        public LocalDate getData() {
            return data;
        }

        public void setData(LocalDate data) {
            this.data = data;
        }

        public int getNumSalgado() {
            return numSalgado;
        }

        public void setNumSalgado(int numSalgado) {
            this.numSalgado = numSalgado;
        }
            
            
        public int calcularNumSalgado(Pedido pedido) throws SQLException{
            int aux = 0;
            for(Item_pedido x : item_pedidodao.lista("pedido_id = " + pedido.getId())){
                aux = aux + x.getQuantidade();
            };
            return aux;
        }
        }
        
    public class EstoqueView {
            private Date data;
            private String nomeInsumo;
            private float quantidade;
            private String valor;

        public EstoqueView(Estoque estoque) {
            this.data = estoque.getData_validade();
            this.nomeInsumo = estoque.getInsumo().getNome();
            this.quantidade = estoque.getQuantidade();
            this.valor = Float.toString(estoque.getValor()) + " R$";
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
            return tf.format(quantidade);
        }

        public void setQuantidade(float quantidade) {
            this.quantidade = quantidade;
        }

        public String getValor() {
            return df.format(valor);
        }

        public void setValor(String valor) {
            this.valor = valor;
        }
     
    }
        
}

