/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.Banco.Banco;
import br.com.fatec.model.Insumo;
import br.com.fatec.model.Item;
import br.com.fatec.model.item_estoque;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author lucas
 */
public class ItemDAO 
                implements DAO<Item>{
    
    private Item item;
    //auxiliares para acesso aos dados
    
    //para conter os comandos DML
    private PreparedStatement pst; //pacote java.sql
    //para conter os dados vindos do BD
    private ResultSet rs; //pacote java.sql

    @Override
    public boolean insere(Item model) throws SQLException {
        String sql = "INSERT INTO item (nome, tipo, preco) "
        + "VALUES(?, ?, ?);"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        ;
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getNome().toUpperCase());
        pst.setString(2, model.getTipo().toUpperCase());
        pst.setFloat(3, model.getPreco());
        
        
        
        
        //executa o comando
        if(pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        }
        else {
            Banco.desconectar();
            return false;
        } // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean remove(Item model) throws SQLException {
        String sql = "DELETE FROM item WHERE id = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setInt(1, model.getId());
        
        //executa o comando
        if(pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        }
        else {
            Banco.desconectar();
            return false;
        } // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean altera(Item model) throws SQLException {
        String sql = "UPDATE item SET nome = ?, tipo = ?, preco = ? "
                + "WHERE id = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getNome().toUpperCase());
        pst.setString(2, model.getTipo().toUpperCase());
        pst.setFloat(3, model.getPreco());
        pst.setInt(4, model.getId());
        
        //executa o comando
        if(pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        }
        else {
            Banco.desconectar();
            return false;
        }  // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Item buscaID(Item model) throws SQLException {
        item = null;
        //Comando SELECT
        String sql = "SELECT * FROM item WHERE id = ?;";
        //conecta ao banco
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //troca a ?
        pst.setInt(1, model.getId());
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        if(rs.next()) { //achou 1 registro
            //cria o objeto proprietario
            item = new Item();
            //move os dados do resultSet para o objeto proprietario
            item.setId(rs.getInt("id"));
            item.setNome(rs.getString("nome"));
            item.setTipo(rs.getString("tipo"));
            item.setPreco(rs.getFloat("preco"));
        }
        Banco.desconectar();
        
        return item;  // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Item buscaNome(Item model) throws SQLException {
        item = null;
        //Comando SELECT
        String sql = "SELECT * FROM item WHERE nome = ?;";
        //conecta ao banco
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //troca a ?
        pst.setString(1,model.getNome());
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        if(rs.next()) { //achou 1 registro
            //cria o objeto proprietario
            item = new Item();
            //move os dados do resultSet para o objeto proprietario
            item.setId(rs.getInt("id"));
            item.setNome(rs.getString("nome"));
            item.setTipo(rs.getString("tipo"));
            item.setPreco(rs.getFloat("preco"));
        }
        Banco.desconectar();
        
        return item;         // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Item> lista(String criterio) throws SQLException {
            
        Collection<Item> listagem = new ArrayList<>();
        
        item = null;
        //Comando SELECT
        String sql = "SELECT * FROM item ";
        //colocar filtro ou nao
        if(criterio.length() != 0) {
            sql += "WHERE " + criterio;
        }
        
        //conecta ao banco
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        //le o próximo regitro
        while(rs.next()) { //achou 1 registro
            //cria o objeto veiculo
            
            item = new Item();
            
            item.setId(rs.getInt("id"));
            item.setNome(rs.getString("nome"));
            item.setPreco(rs.getFloat("preco"));
            item.setTipo(rs.getString("tipo"));
            
            //adicionar na coleção
            listagem.add(item);
        }
        
        Banco.desconectar();
        
        return listagem; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
