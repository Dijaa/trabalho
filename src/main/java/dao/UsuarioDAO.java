package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import model.UsuarioModel;

public class UsuarioDAO {

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



    public UsuarioModel buscarPorId(int id) {
        UsuarioModel p = new UsuarioModel();

        try {

            Connection conn = new Conexao().conectar();

            PreparedStatement statement =
                    conn.prepareStatement("select * from usuarios where id=?");

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                p.setId(resultSet.getInt("id"));
                p.setNome(resultSet.getString("nome"));
            }
            return p;

        }
        catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }


    public UsuarioModel atualizar(UsuarioModel model) {
        try {
            Connection conn = new Conexao().conectar();

            PreparedStatement statement = conn
                    .prepareStatement("update usuarios set nome=? where id=?");

            statement.setString(1, model.getNome());

            statement.setInt(2, model.getId());

            int qtdLinhas = statement.executeUpdate();

            conn.close();

            return model;
        }
        catch (Exception e) {
            return null;
        }
    }


    public static UsuarioModel login(String email, String senha) {
        UsuarioModel p = new UsuarioModel();

        try {
            Connection conn = new Conexao().conectar();

            PreparedStatement statement = conn
                    .prepareStatement("select * from usruarios where email=?");

            statement.setString(1, email);


            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {

                if(BCrypt.checkpw(senha, resultSet.getString("senha"))) {
                    p.setId(resultSet.getInt("id"));
                    p.setNome(resultSet.getString("nome"));
                }

            }

            conn.close();

            return p;
        }
        catch (Exception e) {
            return null;
        }
    }
    public static UsuarioModel listarPorEmail(String email) {
        try {
            Connection conn = new Conexao().conectar();
            UsuarioModel user = new UsuarioModel();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM usuarios WHERE email=?");

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("O email já está cadastrado");
                user.setId(resultSet.getInt("id"));
                user.setNome(resultSet.getString("nome"));
                user.setEmail(resultSet.getString("email"));
                user.setSenha(resultSet.getString("senha"));
                return user;
            } else {
                System.out.println("Email cadastrado com sucesso");
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    }

