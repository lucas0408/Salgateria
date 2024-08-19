/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.model.Insumo;
import br.com.fatec.Banco.Banco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
/**
 *
 * @author lucas
 */
public class InsumoDAO 
            implements DAO <Insumo> {
    
    private Insumo insumo;
    //auxiliares para acesso aos dados
    
    //para conter os comandos DML
    private PreparedStatement pst; //pacote java.sql
    //para conter os dados vindos do BD
    private ResultSet rs; //pacote java.sql

    @Override
    public boolean insere(Insumo model) throws SQLException {
        String sql = "INSERT INTO insumo (nome, tipo, estoque_minimo) "
                + "VALUES(?, ?, ?);"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        ;
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getNome());
        pst.setString(2, model.getTipo());
        pst.setFloat(3, model.getEstoque_minimo());
        
        
        //executa o comando
        if(pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        }
        else {
            Banco.desconectar();
            return false;
        }
    }

    

    @Override
    public boolean remove(Insumo model) throws SQLException {
        String sql = "DELETE FROM insumo WHERE id = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setInt(1, model.getId());
        
        System.out.println("puta");
        //executa o comando
        if(pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        }
        else {
            Banco.desconectar();
            return false;
        }// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean altera(Insumo model) throws SQLException {
        String sql = "UPDATE insumo SET nome = ?, tipo = ?, estoque_minimo = ? "
                + "WHERE id = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getNome());
        pst.setString(2, model.getTipo());
        pst.setFloat(3, model.getEstoque_minimo());
        pst.setInt(4, model.getId());
        
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
    public Insumo buscaNome(Insumo model) throws SQLException {
        insumo = null;
        //Comando SELECT
        String sql = "SELECT * FROM insumo WHERE nome = ?;";
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
            insumo = new Insumo();
            //move os dados do resultSet para o objeto proprietario
            insumo.setId(rs.getInt("id"));
            insumo.setNome(rs.getString("nome"));
            insumo.setTipo(rs.getString("tipo"));
            insumo.setEstoque_minimo(rs.getFloat("estoque_minimo"));
        }
        Banco.desconectar();
        
        return insumo;        
    } // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    @Override
    public Collection<Insumo> lista(String criterio) throws SQLException {
        Collection<Insumo> listagem = new ArrayList<>();
        
        insumo = null;
        //Comando SELECT
        String sql = "SELECT * FROM insumo ";
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
            insumo = new Insumo();
            //move os dados do resultSet para o objeto veiculo
            insumo.setId(rs.getInt("id"));
            insumo.setNome(rs.getString("nome"));
            insumo.setTipo(rs.getString("tipo"));
            insumo.setEstoque_minimo(rs.getFloat("estoque_minimo"));
            
            //adicionar na coleção
            listagem.add(insumo);
        }
        
        Banco.desconectar();
        
        return listagem; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Insumo buscaID(Insumo model) throws SQLException {
                insumo = null;
        //Comando SELECT
        String sql = "SELECT * FROM insumo WHERE id = ?;";
        
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
            insumo = new Insumo();
            //move os dados do resultSet para o objeto proprietario
            insumo.setId(rs.getInt("id"));
            insumo.setNome(rs.getString("nome"));
            insumo.setTipo(rs.getString("tipo"));
            insumo.setEstoque_minimo(rs.getFloat("estoque_minimo"));
        }
        
        Banco.desconectar();
        
        return insumo;    // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
