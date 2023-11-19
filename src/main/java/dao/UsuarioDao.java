package dao;

import model.UsuarioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    public static boolean inserir(UsuarioModel usuario) {
        try {

            Connection conn = new Conexao().conectar();

            PreparedStatement statement = conn
                    .prepareStatement("insert into usuarios (nome, email, senha) values (?,?,?)");

            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());
            statement.setString(3, usuario.getSenha());

            statement.execute();

            conn.close();

            return true;

        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    @SuppressWarnings("finally")
    public static List<UsuarioModel> buscar() {
        List<UsuarioModel> lista = new ArrayList<UsuarioModel>();

        try {
            Connection conn = new Conexao().conectar();

            PreparedStatement statement = conn.prepareStatement("select * from usuarios");

            ResultSet retorno = statement.executeQuery();

            while (retorno.next()) {
                UsuarioModel p = new UsuarioModel();

                p.setId(retorno.getInt("id"));
                p.setNome(retorno.getString("nome"));

                lista.add(p);
            }
            conn.close();

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            return lista;
        }
    }

    public boolean excluirPorId(int id) {
        try {
            Connection conn = new Conexao().conectar();

            PreparedStatement statement = conn.prepareStatement("delete from usuarios where id=?");

            statement.setInt(1, id);

            statement.execute();

            conn.close();

            return true;

        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    // método para buscar um usuário pelo ID
    public UsuarioModel buscarPorId(int id) {
        // variável para retornar
        UsuarioModel usuario = new UsuarioModel();
        try {
            Connection conn = new Conexao().conectar();
            PreparedStatement statement = conn.prepareStatement("select * from usuarios where id=?");

            statement.setInt(1, id);
            ResultSet retorno = statement.executeQuery();

            if (retorno.next()) {
                // coloca o id na variável usuario
                // id que vem do banco
                usuario.setId(retorno.getInt("id"));
                usuario.setNome(retorno.getString("nome"));
            }
            return usuario;
        } catch (Exception e) {
            return null;
        }
    }

    public static UsuarioModel login(String email, String senha) {
        UsuarioModel p = new UsuarioModel();

        try {
            Connection conn = new Conexao().conectar();

            PreparedStatement statement = conn
                    .prepareStatement("select * from usuarios where email=? and senha=?");

            statement.setString(1, email);

            statement.setString(2, senha);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                p.setId(resultSet.getInt("id"));
                p.setNome(resultSet.getString("nome"));
            }

            conn.close();

            return p;
        } catch (Exception e) {
            return null;
        }
    }
}