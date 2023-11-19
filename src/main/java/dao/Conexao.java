package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    public Connection conectar() {
        try {
            Connection conexao = null;
            System.out.println("Conectando ao banco...");
            String url = "jdbc:mysql://localhost/aula?useTimezone=true&serverTimezone=UTC";
            String usuario = "root";
            String senha = "1234";
            System.out.println(url + ", " + usuario + ", " + senha);
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, usuario, senha);
            return conexao;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
}