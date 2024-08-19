/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.Banco.Banco;
import br.com.fatec.model.Insumo;
import br.com.fatec.model.Item;
import br.com.fatec.model.Item_pedido;
import br.com.fatec.model.Pedido;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author lucas
 */
public class Item_pedidoDAO 
                    implements DAO<Item_pedido>{
    
    private Item_pedido item_pedido;
    //auxiliares para acesso aos dados
    
    //para conter os comandos DML
    private PreparedStatement pst; //pacote java.sql
    //para conter os dados vindos do BD
    private ResultSet rs; //pacote java.sql

    @Override
    public boolean insere(Item_pedido model) throws SQLException {
           String sql = "INSERT INTO item_pedido (quantidade, pedido_id, item_id) "
        + "VALUES(?, ?, ?);"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        ;
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        
        pst.setInt(1, model.getQuantidade());
        pst.setInt(2, model.getPedido().getId());
        pst.setFloat(3, model.getItem().getId());
        
        
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
    public boolean remove(Item_pedido model) throws SQLException {
                String sql = "DELETE FROM item_pedido WHERE pedido_id = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setInt(1, model.getPedido().getId());
        
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
    public boolean altera(Item_pedido model) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Item_pedido buscaID(Item_pedido model) throws SQLException {
       item_pedido = null;
        //Comando SELECT
        String sql = "SELECT * FROM item_pedido WHERE item_id = ?;";
        //conecta ao banco
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //troca a ?
        pst.setInt(1,model.getItem().getId());
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        if(rs.next()) { //achou 1 registro
            //cria o objeto proprietario
            item_pedido = new Item_pedido();
            //move os dados do resultSet para o objeto proprietario
            item_pedido.setQuantidade(rs.getInt("quantidade"));
        }
        Banco.desconectar();
        
        return item_pedido;        
    } // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    

    @Override
    public Item_pedido buscaNome(Item_pedido model) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Item_pedido> lista(String criterio) throws SQLException {
            
        Collection<Item_pedido> listagem = new ArrayList<>();
        
        item_pedido = null;
        //Comando SELECT
        String sql = "SELECT * FROM item_pedido ";
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
            
            item_pedido = new Item_pedido();
            //move os dados do resultSet para o objeto veiculo
            PedidoDAO dao = new PedidoDAO();
            Pedido aux = new Pedido();
            aux.setId(rs.getInt("pedido_id"));
            
            ItemDAO itemdao = new ItemDAO();
            Item x = new Item();
            x.setId(rs.getInt("item_id"));
            item_pedido.setPedido(dao.buscaID(aux));
            item_pedido.setItem(itemdao.buscaID(x));
            item_pedido.setQuantidade(rs.getInt("quantidade"));
            
            //adicionar na coleção
            listagem.add(item_pedido);
        }
        
        Banco.desconectar();
        
        return listagem; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
