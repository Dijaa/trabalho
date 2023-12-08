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

            if (resultSet.next()) {
                p.setId(resultSet.getInt("id"));
                p.setNome(resultSet.getString("nome"));
            }
            return p;

        } catch (Exception e) {
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
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean atualizarSenha(UsuarioModel usuario) {
        try {
            Connection conn = new Conexao().conectar();

            PreparedStatement statement = conn
                    .prepareStatement("update usuarios set senha=? where id=?");

            statement.setString(1, usuario.getSenha());
            System.out.println("senha: " + usuario.getSenha());
            statement.setInt(2, usuario.getId());
            System.out.println("id: " + usuario.getId());
            int qtdLinhas = statement.executeUpdate();

            conn.close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static UsuarioModel login(String email, String senha) {
        UsuarioModel p = new UsuarioModel();

        try {

            Connection conn = new Conexao().conectar();

            PreparedStatement statement = conn
                    .prepareStatement("select * from usuarios where email=? and status = 1");

            statement.setString(1, email);
            System.out.println(222);
            ResultSet resultSet = statement.executeQuery();
            System.out.println(333);
            if (resultSet.next()) {
                System.out.println(resultSet.getString("senha"));
                if (BCrypt.checkpw(senha, resultSet.getString("senha"))) {
                    p.setId(resultSet.getInt("id"));
                    p.setEmail(resultSet.getString("email"));
                    System.out.println(resultSet.getInt("id"));
                    p.setNome(resultSet.getString("nome"));
                    System.out.println(444);
                    System.out.println(resultSet.getInt("tipo"));
                    p.setTipo(resultSet.getInt("tipo"));
                    System.out.println(p);
                }

            }

            conn.close();
            System.out.println(p);
            return p;
        } catch (Exception e) {
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
                user.setId(resultSet.getInt("id"));
                user.setNome(resultSet.getString("nome"));
                user.setEmail(resultSet.getString("email"));
                user.setSenha(resultSet.getString("senha"));
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean ativaUsuario(UsuarioModel usuario) {
        try {
            Connection conn = new Conexao().conectar();

            PreparedStatement statement = conn.prepareStatement("update usuarios set status = 1 where id = ?");

            statement.setInt(1, usuario.getId());

            statement.execute();

            conn.close();

            return true;

        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public static UsuarioModel randomizaAprovador(UsuarioModel requerente) {
        try {
            Connection conn = new Conexao().conectar();
            UsuarioModel user = new UsuarioModel();
            int id = requerente.getId();
            if (countAprovador() == 1) {
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM usuarios WHERE tipo=1 LIMIT 1");

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    user.setId(resultSet.getInt("id"));
                    user.setNome(resultSet.getString("nome"));
                    user.setEmail(resultSet.getString("email"));
                    user.setSenha(resultSet.getString("senha"));
                    return user;
                } else {
                    return null;
                }
            } else {
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM usuarios WHERE tipo=1 AND id != ? ORDER BY RAND() LIMIT 1");
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    user.setId(resultSet.getInt("id"));
                    user.setNome(resultSet.getString("nome"));
                    user.setEmail(resultSet.getString("email"));
                    user.setSenha(resultSet.getString("senha"));
                    return user;
                } else {
                    return null;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int countAprovador() {
        try {
            Connection conn = new Conexao().conectar();
            PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM usuarios WHERE tipo=1");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static UsuarioModel buscaById(int id) {
        try {
            Connection conn = new Conexao().conectar();
            UsuarioModel user = new UsuarioModel();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM usuarios WHERE id=?");

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setNome(resultSet.getString("nome"));
                user.setEmail(resultSet.getString("email"));
                user.setSenha(resultSet.getString("senha"));
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}