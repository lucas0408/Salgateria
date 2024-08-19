package br.com.fatec.DAO;

import br.com.fatec.Banco.Banco;
import br.com.fatec.model.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UsuarioDAO implements DAO<Usuario> {
    
    private Usuario usuario;
    private PreparedStatement pst;
    private ResultSet rs;
    private Connection connection;

    public UsuarioDAO() throws SQLException{
        // Inicializar a conexão com o banco de dados
        Banco.conectar();
        this.connection = Banco.obterConexao();
    }

    @Override
    public boolean insere(Usuario model) throws SQLException {
        String sql = "INSERT INTO usuario (cpf, nome, senha, data_nascimento, fone, cep, logradouro, numero, bairro, cidade, uf) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        pst = connection.prepareStatement(sql);

        pst.setString(1, model.getCpf());
        pst.setString(2, model.getNome());
        pst.setString(3, model.getSenha());
        pst.setDate(4, Date.valueOf(model.getData_nascimento()));
        pst.setString(5, model.getFone());
        pst.setString(6, model.getCep());
        pst.setString(7, model.getLogradouro());
        pst.setString(8, model.getNumero());
        pst.setString(9, model.getBairro());
        pst.setString(10, model.getCidade());
        pst.setString(11, model.getUf());

        boolean resultado = pst.executeUpdate() >= 1;
        Banco.desconectar();
        return resultado;
    }

    @Override
    public boolean remove(Usuario model) throws SQLException {
        String sql = "DELETE FROM usuario WHERE cpf = ?";
        
        pst = connection.prepareStatement(sql);
        pst.setString(1, model.getCpf());

        boolean resultado = pst.executeUpdate() >= 1;
        Banco.desconectar();
        return resultado;
    }

    @Override
    public boolean altera(Usuario model) throws SQLException {
        String sql = "UPDATE usuario SET nome = ?, senha = ?, data_nascimento = ?, fone = ?, cep = ?, logradouro = ?, numero = ?, bairro = ?, cidade = ?, uf = ? "
                + "WHERE cpf = ?";

        pst = connection.prepareStatement(sql);

        pst.setString(1, model.getNome());
        pst.setString(2, model.getSenha());
        pst.setDate(3, Date.valueOf(model.getData_nascimento()));
        pst.setString(4, model.getFone());
        pst.setString(5, model.getCep());
        pst.setString(6, model.getLogradouro());
        pst.setString(7, model.getNumero());
        pst.setString(8, model.getBairro());
        pst.setString(9, model.getCidade());
        pst.setString(10, model.getUf());
        pst.setString(11, model.getCpf());

        boolean resultado = pst.executeUpdate() >= 1;
        Banco.desconectar();
        return resultado;
    }

    @Override
    public Collection<Usuario> lista(String criterio) throws SQLException {
        Collection<Usuario> listagem = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        if (criterio.length() != 0) {
            sql += " WHERE " + criterio;
        }

        pst = connection.prepareStatement(sql);
        rs = pst.executeQuery();

        while(rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setCpf(rs.getString("cpf"));
            usuario.setNome(rs.getString("nome"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setData_nascimento(rs.getDate("data_nascimento").toLocalDate());
            usuario.setFone(rs.getString("fone"));
            usuario.setCep(rs.getString("cep"));
            usuario.setLogradouro(rs.getString("logradouro"));
            usuario.setNumero(rs.getString("numero"));
            usuario.setBairro(rs.getString("bairro"));
            usuario.setCidade(rs.getString("cidade"));
            usuario.setUf(rs.getString("uf"));

            listagem.add(usuario);
        }

        Banco.desconectar();
        return listagem;
    }

    @Override
    public Usuario buscaID(Usuario model) throws SQLException {
        return buscaPorCPF(model.getCpf());
    }

    @Override
    public Usuario buscaNome(Usuario model) throws SQLException {
        List<Usuario> usuarios = buscaPorNome(model.getNome());
        if (!usuarios.isEmpty()) {
            return usuarios.get(0);
        }
        return null;
    }

    public Usuario buscaPorCPF(String cpf) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE cpf = ?";
        Usuario usuario = null;

        pst = connection.prepareStatement(sql);
        pst.setString(1, cpf);
        rs = pst.executeQuery();

        if (rs.next()) {
            usuario = new Usuario();
            usuario.setCpf(rs.getString("cpf"));
            usuario.setNome(rs.getString("nome"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setData_nascimento(rs.getDate("data_nascimento").toLocalDate());
            usuario.setFone(rs.getString("fone"));
            usuario.setCep(rs.getString("cep"));
            usuario.setLogradouro(rs.getString("logradouro"));
            usuario.setNumero(rs.getString("numero"));
            usuario.setBairro(rs.getString("bairro"));
            usuario.setCidade(rs.getString("cidade"));
            usuario.setUf(rs.getString("uf"));
        }

        return usuario;
    }

    public List<Usuario> buscaPorNome(String nome) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE nome LIKE ?";
        
        pst = connection.prepareStatement(sql);
        pst.setString(1, "%" + nome + "%");
        rs = pst.executeQuery();

        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setCpf(rs.getString("cpf"));
            usuario.setNome(rs.getString("nome"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setData_nascimento(rs.getDate("data_nascimento").toLocalDate());
            usuario.setFone(rs.getString("fone"));
            usuario.setCep(rs.getString("cep"));
            usuario.setLogradouro(rs.getString("logradouro"));
            usuario.setNumero(rs.getString("numero"));
            usuario.setBairro(rs.getString("bairro"));
            usuario.setCidade(rs.getString("cidade"));
            usuario.setUf(rs.getString("uf"));
            usuarios.add(usuario);
        }

        return usuarios;
    }
        public Usuario buscaPorCPFESenha(String cpf, String senha) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE cpf = ? AND senha = ?";
        Usuario usuario = null;

        pst = connection.prepareStatement(sql);
        pst.setString(1, cpf);
        pst.setString(2, senha);
        rs = pst.executeQuery();

        if (rs.next()) {
            usuario = new Usuario();
            usuario.setCpf(rs.getString("cpf"));
            usuario.setNome(rs.getString("nome"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setData_nascimento(rs.getDate("data_nascimento").toLocalDate());
            usuario.setFone(rs.getString("fone"));
            usuario.setCep(rs.getString("cep"));
            usuario.setLogradouro(rs.getString("logradouro"));
            usuario.setNumero(rs.getString("numero"));
            usuario.setBairro(rs.getString("bairro"));
            usuario.setCidade(rs.getString("cidade"));
            usuario.setUf(rs.getString("uf"));
        }

        return usuario;
    }

}
