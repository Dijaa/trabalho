package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.TokenModel;
public class TokenDAO {
    public static boolean inserir(TokenModel token) {
        try {
            Connection conn = new Conexao().conectar();

            PreparedStatement statement = conn
                    .prepareStatement("insert into tokens (usuario_id, token) values (?,?)");

            statement.setInt(1, token.getUsuarioId());
            statement.setString(2, token.getToken());

            statement.execute();

            conn.close();

            return true;

        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }
    public static TokenModel buscarPorToken(String token) {
        TokenModel tokenModel = new TokenModel();
        try {
            Connection conn = new Conexao().conectar();

            PreparedStatement statement = conn.prepareStatement("select * from tokens where token = ?");

            statement.setString(1, token);

            java.sql.ResultSet retorno = statement.executeQuery();

            if (retorno.next()) {
                tokenModel.setId(retorno.getInt("id"));
                tokenModel.setUsuarioId(retorno.getInt("usuario_id"));
                tokenModel.setToken(retorno.getString("token"));
                tokenModel.setStatus(retorno.getInt("status"));
            }
            conn.close();
            return tokenModel;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }

    }
    public static boolean desativaToken(TokenModel token) {
        try {
            Connection conn = new Conexao().conectar();

            PreparedStatement statement = conn.prepareStatement("update tokens set status = 0 where id = ?");

            statement.setInt(1, token.getId());

            statement.execute();

            conn.close();

            return true;

        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }
}
