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
public class Item_estoqueDAO
                    implements DAO<item_estoque>{
    private item_estoque item_estoque;
    //auxiliares para acesso aos dados
    
    //para conter os comandos DML
    private PreparedStatement pst; //pacote java.sql
    //para conter os dados vindos do BD
    private ResultSet rs; //pacote java.sql

    @Override
    public boolean insere(item_estoque model) throws SQLException {
        String sql = "INSERT INTO estoque_has_item (insumo_id, item_id, consumo) "
        + "VALUES(?, ?, ?);"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        ;
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        
        pst.setInt(1, model.getInsumo().getId());
        pst.setInt(2, model.getItem().getId());
        pst.setFloat(3, model.getQuantidade());
        
        
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
    public boolean remove(item_estoque model) throws SQLException {
        
        String sql = "DELETE FROM estoque_has_item WHERE item_id = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setInt(1, model.getItem().getId());
        
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
    
    public boolean removeInsumo(item_estoque model) throws SQLException {
        
        String sql = "DELETE FROM estoque_has_item WHERE insumo_id = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setInt(1, model.getInsumo().getId());
        
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
    public boolean altera(item_estoque model) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public item_estoque buscaID(item_estoque model) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public item_estoque buscaNome(item_estoque model) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<item_estoque> lista(String criterio) throws SQLException {
         // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    
        Collection<item_estoque> listagem = new ArrayList<>();
        
        item_estoque = null;
        //Comando SELECT
        String sql = "SELECT * FROM estoque_has_item ";
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
            
            item_estoque = new item_estoque();
            //move os dados do resultSet para o objeto veiculo
            InsumoDAO dao = new InsumoDAO();
            Insumo aux = new Insumo();
            aux.setId(rs.getInt("insumo_id"));
            
            ItemDAO itemdao = new ItemDAO();
            Item x = new Item();
            x.setId(rs.getInt("item_id"));
            item_estoque.setInsumo(dao.buscaID(aux));
            item_estoque.setItem(itemdao.buscaID(x));
            item_estoque.setQuantidade(rs.getFloat("consumo"));
            
            //adicionar na coleção
            listagem.add(item_estoque);
        }
        
        Banco.desconectar();
        
        return listagem;
    }
}
