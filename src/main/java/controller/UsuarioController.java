package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsuarioDAO;
import model.UsuarioModel;
import util.MailUtil;
import util.SenhaUtil;

//@WebServlet("/UsuarioController")
@WebServlet(urlPatterns = {"/UsuarioController", "/listarusuarios", "/detalhar", "/editar", "/atualizar"})
public class UsuarioController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UsuarioController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getServletPath();

        if (acao.equals("/detalhar")) {
            String id = request.getParameter("id");
            UsuarioModel p = new UsuarioDAO().buscarPorId(Integer.parseInt(id));
            request.setAttribute("usuario", p);
            request.getRequestDispatcher("detalhar.jsp").forward(request, response);
        }

        if (acao.equals("/editar")) {
            String id = request.getParameter("id");
            UsuarioModel p = new UsuarioDAO().buscarPorId(Integer.parseInt(id));
            request.setAttribute("usuario", p);
            request.getRequestDispatcher("editar.jsp").forward(request, response);
        }


        List<UsuarioModel> listaDeUsuarios = UsuarioDAO.buscar();

        request.setAttribute("listaDeUsuarios", listaDeUsuarios);

        RequestDispatcher resquesDispatcher = request.getRequestDispatcher("sucesso_registro.jsp");
        resquesDispatcher.forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String acao = request.getServletPath();

        if (acao.equals("/atualizar")) {
            String id = request.getParameter("id");

            int idInt = Integer.parseInt(id);

            String nome = request.getParameter("nome");


            UsuarioModel usuario = new UsuarioModel();

            usuario.setId(idInt);
            usuario.setNome(nome);

            usuario = new UsuarioDAO().atualizar(usuario);

        } else {

            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");

            String senhaCrip = SenhaUtil.hashSenha(senha);

            UsuarioModel usuario = UsuarioDAO.listarPorEmail(email);
            System.out.println(usuario);
            if (usuario != null && usuario.getId() != null) {
                request.setAttribute("error", "Email já cadastrado");
                request.setAttribute("type", "register");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }
            UsuarioModel novoUsuario = new UsuarioModel();

            novoUsuario.setNome(nome);
            novoUsuario.setEmail(email);
            novoUsuario.setSenha(senhaCrip);

            boolean resultado = UsuarioDAO.inserir(novoUsuario);


            if (resultado) {
                Runnable task = () -> {
                    MailUtil.send(email, "Sua conta foi criada", "<a href=\"http://localhost:8080/Aula_web/\">Clique</a>");
                };
                Thread thread = new Thread(task);
                thread.start();
            }
        }


        doGet(request, response);

    }

}
