/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model;

import br.com.fatec.DAO.EstoqueDAO;
import br.com.fatec.DAO.ItemDAO;
import br.com.fatec.DAO.Item_estoqueDAO;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Collection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;

/**
 *
 * @author lucas
 */
public class Pedido {
    private int id;
    private String nome;
    private LocalDate data;
    private Time horario;
    private float total;
    private String pagamento;
    private String logradouro;
    private int numero;
    private boolean ativo;
    private float custo;

    public Pedido() {
    }

    public Pedido(String nome, LocalDate data, Time horario, float total, String pagamento, String logradouro, int numero, boolean ativo, float custo) {
        this.nome = nome;
        this.data = data;
        this.horario = horario;
        this.total = total;
        this.pagamento = pagamento;
        this.logradouro = logradouro;
        this.numero = numero;
        this.ativo = ativo;
        this.custo = custo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public float getCusto() {
        return custo;
    }

    public void setCusto(float custo) {
        this.custo = custo;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Time getHorario() {
        return horario;
    }

    public void setHorario(Time horario) {
        this.horario = horario;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    ObservableList<item_estoque> listaItemEstoque = 
    FXCollections.observableArrayList();
    
    ObservableList<Estoque> listaEstoque = 
    FXCollections.observableArrayList();
    DecimalFormat tf = new DecimalFormat("0.000");
    public String calcularCusto(Collection<Item_pedido> listaItem) throws SQLException{
        ItemDAO itemdao = new ItemDAO();
        Item_estoqueDAO item_estoquedao = new Item_estoqueDAO();
        EstoqueDAO estoquedao = new EstoqueDAO();
        int index = 0;
        float aux = 0;
        float custo = 0F;
                for(Item_pedido item_pedido : listaItem){
                    listaItemEstoque.clear();
                    listaItemEstoque.addAll(item_estoquedao.lista("item_id = " + item_pedido.getItem().getId()));
                    for(item_estoque item_estoque : listaItemEstoque){
                        listaEstoque.clear();
                        index = 0;
                        listaEstoque.addAll(estoquedao.lista("insumo_id = " + item_estoque.getInsumo().getId()));
                        float quantidade = ((item_estoque.getQuantidade() * item_pedido.getQuantidade())/100);
                        float quantidade_aux = quantidade;
                        while(quantidade > 0){
                            if(listaEstoque.size() == index){
                                float f = 0f;
                                for(Estoque x : listaEstoque){
                                    f = f +x.getQuantidade();
                                }
                                return "insumo " + item_estoque.getInsumo().getNome() + " insuficente" + "\nquantidade atual: " 
                                        + tf.format(f) + "\nquantidade necessaria: " + tf.format(quantidade_aux) + "\npara realizar operaçõa registre os insumos usados na produção";
                            }
                            if(listaEstoque.get(index).getQuantidade() >  quantidade){
                                break;
                            }else{
                                quantidade = quantidade - listaEstoque.get(index).getQuantidade();
                                custo = listaEstoque.get(index).getValor();
                                index++;
                            }
                        }
                    }
                }
        for(Item_pedido item_pedido : listaItem){
                    listaItemEstoque.clear();
                    listaItemEstoque.addAll(item_estoquedao.lista("item_id = " + item_pedido.getItem().getId()));
                    for(item_estoque item_estoque : listaItemEstoque){
                        listaEstoque.clear();
                        index = 0;
                        listaEstoque.addAll(estoquedao.lista("insumo_id = " + item_estoque.getInsumo().getId()));
                        float quantidade = (item_estoque.getQuantidade() * item_pedido.getQuantidade())/100;
                        while(quantidade > 0){
                            if(listaEstoque.get(index).getQuantidade() > quantidade){
                                custo = custo + (listaEstoque.get(index).getValor() * quantidade)/listaEstoque.get(index).getQuantidade();
                                float q = listaEstoque.get(index).getQuantidade();
                                System.out.println("\n" + item_estoque.getItem().getNome());
                                System.out.println(item_estoque.getInsumo().getNome());
                                System.out.println(listaEstoque.get(index).getQuantidade());
                                System.out.println(quantidade);
                                listaEstoque.get(index).setQuantidade(listaEstoque.get(index).getQuantidade() - quantidade);
                                listaEstoque.get(index).setValor(listaEstoque.get(index).getValor() - ((listaEstoque.get(index).getValor() * quantidade)/q));
                                estoquedao.altera(listaEstoque.get(index));
                                break;
                            }else{
                                quantidade = quantidade - listaEstoque.get(index).getQuantidade();
                                custo = listaEstoque.get(index).getValor();
                                estoquedao.remove(listaEstoque.get(index));
                                index++;
                                }
                            }
                        }
                    }
                
            this.custo = custo;
            this.ativo = false;
            return "";
        }
    
    
    
}
