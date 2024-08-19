/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.Banco.Banco;
import br.com.fatec.model.Estoque;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import br.com.fatec.DAO.InsumoDAO;
import br.com.fatec.model.Insumo;
import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public class EstoqueDAO 
            implements DAO<Estoque>{
    
    private Estoque estoque;
    
    private PreparedStatement pst;
    
    private ResultSet rs;

    @Override
    public boolean insere(Estoque model) throws SQLException {
        String sql = "INSERT INTO item_estoque (data_validade, quantidade, insumo_id, valor) "
                + "VALUES(?, ?, ?, ?);"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
         
        try {
            Banco.conectar();
        } catch (SQLException ex) {
            System.out.println("Erro no Banco..: " + ex.getMessage());
        }
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        ;
        
        pst.setDate(1, model.getData_validade());
        pst.setFloat(2, model.getQuantidade());
        pst.setInt(3, model.getInsumo().getId());
        pst.setFloat(4, model.getValor());
        
        
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
    public boolean remove(Estoque model) throws SQLException {
                String sql = "DELETE FROM item_estoque WHERE id = ?;";
        
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
    public boolean altera(Estoque model) throws SQLException {
        String sql = "UPDATE item_estoque SET data_validade = ?, quantidade = ?, valor = ? "
                + "WHERE id = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setDate(1, model.getData_validade());
        pst.setFloat(2, model.getQuantidade());
        pst.setFloat(3, model.getValor());
        pst.setInt(4, model.getId());
        
        //executa o comando
        if(pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        }
        else {
            Banco.desconectar();
            return false;
        } // Gene // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Estoque buscaID(Estoque model) throws SQLException {
        estoque = null;
        //Comando SELECT
        String sql = "SELECT * FROM item_estoque WHERE insumo_id = ? and data_validade = ?;";
        
        //conecta ao banco
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        //troca a ?
        pst.setInt(1, model.getInsumo().getId());
        pst.setDate(2, model.getData_validade());
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        if(rs.next()) { //achou 1 registro
            //cria o objeto proprietario
            estoque = new Estoque();
            //move os dados do resultSet para o objeto proprietario
            estoque.setId(rs.getInt("id"));
            estoque.setData_validade(rs.getDate("data_validade"));
            Insumo aux = new Insumo();
            aux.setId(rs.getInt("insumo_id"));
            estoque.setInsumo(new InsumoDAO().buscaID(aux));
            estoque.setQuantidade(rs.getFloat("quantidade"));
            estoque.setValor(rs.getFloat("valor"));
            
                    
            

        }
        
        Banco.desconectar();
        return estoque;         // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Estoque> lista(String criterio) throws SQLException {
        Collection<Estoque> listagem = new ArrayList<>();
        
        estoque = null;
        //Comando SELECT
        String sql = "SELECT * FROM item_estoque ";
        //colocar filtro ou nao
        if(criterio.length() != 0) {
            sql += "WHERE " + criterio + " ";
        }
        
        sql +="order by data_validade asc";
        
        //conecta ao banco
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        while(rs.next()) { //achou 1 registro
            //cria o objeto veiculo
            estoque = new Estoque();
            //move os dados do resultSet para o objeto proprietario
            estoque.setId(rs.getInt("id"));
            estoque.setData_validade(rs.getDate("data_validade"));
            Insumo aux = new Insumo();
            aux.setId(rs.getInt("insumo_id"));
            estoque.setInsumo(new InsumoDAO().buscaID(aux));
            estoque.setQuantidade(rs.getFloat("quantidade"));
            estoque.setValor(rs.getFloat("valor"));
            
            //adicionar na coleção
            listagem.add(estoque);
        }
        
        Banco.desconectar();
        
        return listagem; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Estoque buscaNome(Estoque model) throws SQLException {
                estoque = null;
        //Comando SELECT
        String sql = "SELECT * FROM item_estoque WHERE insumo_id = ?";
        
        //conecta ao banco
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        //troca a ?
        pst.setInt(1, model.getInsumo().getId());
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        if(rs.next()) { //achou 1 registro
            //cria o objeto proprietario
            estoque = new Estoque();
            //move os dados do resultSet para o objeto proprietario
            estoque.setId(rs.getInt("id"));
            estoque.setData_validade(rs.getDate("data_validade"));
            Insumo aux = new Insumo();
            aux.setId(rs.getInt("insumo_id"));
            estoque.setInsumo(new InsumoDAO().buscaID(aux));
            estoque.setQuantidade(rs.getFloat("quantidade"));
            estoque.setValor(rs.getFloat("valor"));
            
                    
            

        }
        
        Banco.desconectar();
        return estoque; 
    }
    
    public Estoque consulta(Estoque model) throws SQLException {
                estoque = null;
        //Comando SELECT
        String sql = "SELECT SUM(quantidade) AS total_quantidade " +
        "FROM item_estoque " +
        "WHERE insumo_id = ?; ";
        
        //conecta ao banco
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        //troca a ?
        pst.setInt(1, model.getInsumo().getId());
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        if(rs.next()) { //achou 1 registro
            //cria o objeto proprietario
            estoque = new Estoque();
            //move os dados do resultSet para o objeto proprietario
            estoque.setQuantidade(rs.getFloat("total_quantidade"));
            
                    
            

        }
        
        Banco.desconectar();
        return estoque; 
    }
    
}
