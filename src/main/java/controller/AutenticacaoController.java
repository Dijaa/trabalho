package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Conexao;
import dao.UsuarioDAO;
import model.UsuarioModel;
import util.SenhaUtil;

@WebServlet(urlPatterns = { "/AutenticacaoController", "/login", "/logout"})
public class AutenticacaoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AutenticacaoController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getServletPath();
        if (acao.equals("/logout")) {
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect("login.jsp");
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getServletPath();

        if (acao.equals("/login")) {
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");

            UsuarioModel usuario = UsuarioDAO.login(email, senha);

            if(usuario!=null && usuario.getId()!=null) {
                System.out.println(usuario.toString());

                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);

                response.sendRedirect("admin/");
            }
            else {
                response.sendRedirect("login.jsp");
            }
        }

        if(acao.equals("/alterarsenha")) {


            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");

            UsuarioModel usuario = new UsuarioModel();

            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(senha);

            usuario = new UsuarioDAO().atualizar(usuario);
        }

    }

}
