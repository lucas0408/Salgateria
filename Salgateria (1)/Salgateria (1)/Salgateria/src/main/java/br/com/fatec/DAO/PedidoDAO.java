/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.Banco.Banco;
import br.com.fatec.controller.Lucro_viewController;
import br.com.fatec.controller.Lucro_viewController.LucroView;
import br.com.fatec.model.Pedido;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author lucas
 */
public class PedidoDAO 
            implements DAO<Pedido>{
    private Pedido pedido;
    //auxiliares para acesso aos dados
    
    private LucroView lucroView;
    
    //para conter os comandos DML
    private PreparedStatement pst; //pacote java.sql
    //para conter os dados vindos do BD
    private ResultSet rs; //pacote java.sql
    @Override
    public boolean insere(Pedido model) throws SQLException {
                String sql = "INSERT INTO pedido (data, horario, total, pagamento, logradouro, numero, nome_cliente, ativo, custo) "
        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        ;
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        Date sqldate = Date.valueOf(model.getData());
        pst.setDate(1, sqldate);
        pst.setTime(2, model.getHorario());
        pst.setFloat(3, model.getTotal());
        pst.setString(4, model.getPagamento());
        pst.setString(5, model.getLogradouro());
        pst.setInt(6, model.getNumero());
        pst.setString(7, model.getNome());
        pst.setBoolean(8, model.isAtivo());
        pst.setFloat(9, model.getCusto());
        
        
        
        
        
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
    public boolean remove(Pedido model) throws SQLException {
        String sql = "DELETE FROM pedido WHERE id = ?;";
        
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
        }  // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean altera(Pedido model) throws SQLException {
                String sql = "UPDATE pedido SET data = ?, horario =?, total = ?, pagamento = ?, logradouro = ?"
                        + ", numero = ?, nome_cliente = ?, ativo = ?, custo = ? "
                + "WHERE id = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        Date sqldate = Date.valueOf(model.getData());
        pst.setDate(1, sqldate);
        pst.setTime(2, model.getHorario());
        pst.setFloat(3, model.getTotal());
        pst.setString(4, model.getPagamento());
        pst.setString(5, model.getLogradouro());
        pst.setInt(6, model.getNumero());
        pst.setString(7, model.getNome());
        pst.setBoolean(8, model.isAtivo());
        pst.setFloat(9, model.getCusto());
        pst.setInt(10, model.getId());
        
        
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
    public Pedido buscaID(Pedido model) throws SQLException {
        pedido = null;
        //Comando SELECT
        String sql = "SELECT * FROM pedido WHERE id = ?;";
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
            pedido = new Pedido();
            //move os dados do resultSet para o objeto proprietario
            pedido.setId(rs.getInt("id"));
            pedido.setNome(rs.getString("nome_cliente"));
            pedido.setAtivo(rs.getBoolean("ativo"));
            pedido.setCusto(rs.getFloat("custo"));
            pedido.setData(rs.getDate("data").toLocalDate());
            pedido.setHorario(rs.getTime("horario"));
            pedido.setLogradouro(rs.getString("logradouro"));
            pedido.setNumero(rs.getInt("numero"));
            pedido.setPagamento(rs.getString("pagamento"));
            pedido.setTotal(rs.getFloat("total"));
        }
        Banco.desconectar();
        return pedido;  // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Pedido buscaNome(Pedido model) throws SQLException {
        pedido = null;
        //Comando SELECT
        String sql = "SELECT * FROM pedido WHERE logradouro = ? and ativo = ? and nome_cliente = ?;";
        //conecta ao banco
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //troca a ?
        pst.setString(1,model.getLogradouro());
        pst.setBoolean(2, true);
        pst.setString(3, model.getNome());
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        if(rs.next()) { //achou 1 registro
            //cria o objeto proprietario
            pedido = new Pedido();
            //move os dados do resultSet para o objeto proprietario
            pedido.setId(rs.getInt("id"));
            pedido.setNome(rs.getString("nome_cliente"));
            pedido.setAtivo(rs.getBoolean("ativo"));
            pedido.setCusto(rs.getFloat("custo"));
            pedido.setData(rs.getDate("data").toLocalDate());
            pedido.setHorario(rs.getTime("horario"));
            pedido.setLogradouro(rs.getString("logradouro"));
            pedido.setNumero(rs.getInt("numero"));
            pedido.setPagamento(rs.getString("pagamento"));
            pedido.setTotal(rs.getFloat("total"));
        }
        Banco.desconectar();
        System.out.println("ola");
        return pedido;   // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Pedido> lista(String criterio) throws SQLException {
                    
        Collection<Pedido> listagem = new ArrayList<>();
        
        pedido = null;
        //Comando SELECT
        String sql = "SELECT * FROM pedido ";
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
            
            pedido = new Pedido();
            
            pedido.setId(rs.getInt("id"));
            pedido.setNome(rs.getString("nome_cliente"));
            pedido.setAtivo(rs.getBoolean("ativo"));
            pedido.setCusto(rs.getFloat("custo"));
            pedido.setData(rs.getDate("data").toLocalDate());
            pedido.setHorario(rs.getTime("horario"));
            pedido.setLogradouro(rs.getString("logradouro"));
            pedido.setNumero(rs.getInt("numero"));
            pedido.setPagamento(rs.getString("pagamento"));
            pedido.setTotal(rs.getFloat("total"));
            
            //adicionar na coleção
            listagem.add(pedido);
        }
        
        Banco.desconectar();
        
        return listagem; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public Collection<LucroView> listaLucro(String criterio) throws SQLException {
                    
        Collection<LucroView> listagem = new ArrayList<>();
        
        lucroView = null;
        
        //Comando SELECT
        String sql = "SELECT "
                + "data, "
                + "COUNT(*) AS quantidade_vendas,"
                + "SUM(total) AS total_diario, "
                + "SUM(custo) AS custo_diario, "
                + "(SUM(total) - SUM(custo)) AS lucro_diario "
                + "FROM pedido "
                + criterio
                + "GROUP BY data "
                + "ORDER BY data;";
        //colocar filtro ou nao

        
        //conecta ao banco
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        //le o próximo regitro
        while(rs.next()) { //achou 1 registro
            //cria o objeto veiculo
            
            Lucro_viewController controller = new Lucro_viewController();
            Lucro_viewController.LucroView lucroView = controller.new LucroView();
            
            lucroView.setCusto(rs.getFloat("custo_diario"));
            lucroView.setData(rs.getDate("data").toLocalDate());
            lucroView.setLucro(rs.getFloat("lucro_diario"));
            lucroView.setVendas(rs.getFloat("total_diario"));
            lucroView.setQtd(rs.getInt("quantidade_vendas"));

            
            //adicionar na coleção
            listagem.add(lucroView);
        }
        
        Banco.desconectar();
        
        return listagem; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
