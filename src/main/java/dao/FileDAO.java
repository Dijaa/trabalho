package dao;

import model.FileModel;
import model.UsuarioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FileDAO {
    public static boolean inserir(FileModel file) {
        try {
            Connection conn = new Conexao().conectar();

            PreparedStatement statement = conn
                    .prepareStatement("insert into arquivos (caminho, nome, usuario, aprovador, token) values (?,?,?,?,?)");

            statement.setString(1, file.getCaminho());
            statement.setString(2, file.getNome());
            statement.setInt(3, file.getUsuario_id());
            statement.setInt(4, file.getAprovador_id());
            statement.setString(5, file.getToken());
            boolean retorno = statement.execute();

            System.out.println(retorno);
            if (retorno == false) {
                conn.close();
                return true;
            } else {
                conn.close();
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public static boolean deletar(int id) {
        try {
            Connection conn = new Conexao().conectar();
            PreparedStatement statement = conn.prepareStatement("update arquivos set status = 3 where id = ?");
            statement.setInt(1, id);
            boolean retorno = statement.execute();
            if (retorno == false) {
                conn.close();
                return true;
            } else {
                conn.close();
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public static FileModel buscarById(int id) {
        FileModel p = new FileModel();

        try {
            Connection conn = new Conexao().conectar();
            PreparedStatement statement = conn.prepareStatement("select * from arquivos where id = ?");
            statement.setInt(1, id);
            ResultSet retorno = statement.executeQuery();

            while (retorno.next()) {
                p.setId(retorno.getInt("id"));
                p.setNome(retorno.getString("nome"));
                p.setCaminho(retorno.getString("caminho"));
                p.setUsuario_id(retorno.getInt("usuario"));
                p.setAprovador_id(retorno.getInt("aprovador"));
                p.setStatus(retorno.getInt("status"));
                p.setAprovador(UsuarioDAO.buscaById(retorno.getInt("aprovador")));
                p.setUsuario(UsuarioDAO.buscaById(retorno.getInt("usuario")));
                p.setToken(retorno.getString("token"));
            }
            conn.close();
            return p;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public static String getFilePathByToken(String token) {
        try {
            Connection conn = new Conexao().conectar();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM arquivos WHERE token=?");

            statement.setString(1, token);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("caminho");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<FileModel> buscarByIdUsuario(UsuarioModel usuario) {
        List<FileModel> lista = new ArrayList<FileModel>();

        try {
            int id = usuario.getId();
            Connection conn = new Conexao().conectar();
            PreparedStatement statement = conn.prepareStatement("select * from arquivos where usuario = ? and status != 3");
            statement.setInt(1, id);
            ResultSet retorno = statement.executeQuery();

            while (retorno.next()) {
                FileModel p = new FileModel();
                p.setId(retorno.getInt("id"));
                p.setNome(retorno.getString("nome"));
                p.setCaminho(retorno.getString("caminho"));
                p.setUsuario_id(retorno.getInt("usuario"));
                p.setAprovador_id(retorno.getInt("aprovador"));
                p.setStatus(retorno.getInt("status"));
                p.setAprovador(UsuarioDAO.buscaById(retorno.getInt("aprovador")));
                p.setUsuario(UsuarioDAO.buscaById(retorno.getInt("usuario")));
                p.setToken(retorno.getString("token"));
                lista.add(p);
            }
            conn.close();
            return lista;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;


        }
    }

    public static List<FileModel> listaTodosAprovado() {
        List<FileModel> lista = new ArrayList<FileModel>();

        try {
            Connection conn = new Conexao().conectar();
            PreparedStatement statement = conn.prepareStatement("select * from arquivos where status = 1");
            ResultSet retorno = statement.executeQuery();

            while (retorno.next()) {
                FileModel p = new FileModel();
                p.setId(retorno.getInt("id"));
                p.setNome(retorno.getString("nome"));
                p.setCaminho(retorno.getString("caminho"));
                p.setUsuario_id(retorno.getInt("usuario"));
                p.setAprovador_id(retorno.getInt("aprovador"));
                p.setStatus(retorno.getInt("status"));
                p.setAprovador(UsuarioDAO.buscaById(retorno.getInt("aprovador")));
                p.setUsuario(UsuarioDAO.buscaById(retorno.getInt("usuario")));
                p.setToken(retorno.getString("token"));
                lista.add(p);
            }
            conn.close();
            return lista;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public static List<FileModel> buscarPendeteByIdAprovador(int id) {
        List<FileModel> lista = new ArrayList<FileModel>();

        try {
            Connection conn = new Conexao().conectar();
            PreparedStatement statement = conn.prepareStatement("select * from arquivos where aprovador = ? and status = 0");
            statement.setInt(1, id);
            ResultSet retorno = statement.executeQuery();

            while (retorno.next()) {
                FileModel p = new FileModel();
                p.setId(retorno.getInt("id"));
                p.setNome(retorno.getString("nome"));
                p.setCaminho(retorno.getString("caminho"));
                p.setUsuario_id(retorno.getInt("usuario"));
                p.setAprovador_id(retorno.getInt("aprovador"));
                p.setStatus(retorno.getInt("status"));
                p.setAprovador(UsuarioDAO.buscaById(retorno.getInt("aprovador")));
                p.setUsuario(UsuarioDAO.buscaById(retorno.getInt("usuario")));
                p.setToken(retorno.getString("token"));
                lista.add(p);
            }
            conn.close();
            return lista;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public static boolean update(FileModel file) {
        try {
            Connection conn = new Conexao().conectar();
            PreparedStatement statement = conn.prepareStatement("update arquivos set status = ? where id = ?");
            statement.setInt(1, file.getStatus());
            statement.setInt(2, file.getId());
            statement.setString(3, file.getObs());
            boolean retorno = statement.execute();
            if (retorno == false) {
                conn.close();
                return true;
            } else {
                conn.close();
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }
}
