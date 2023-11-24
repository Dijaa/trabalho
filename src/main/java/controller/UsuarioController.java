package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TokenDAO;
import dao.UsuarioDAO;
import model.TokenModel;
import model.UsuarioModel;
import org.mindrot.jbcrypt.BCrypt;
import util.MailUtil;
import util.SenhaUtil;

//@WebServlet("/UsuarioController")
@WebServlet(urlPatterns = {"/UsuarioController", "/listarusuarios", "/detalhar", "/editar", "/atualizar", "/valida", "/resetPassword", "/reset"})
public class UsuarioController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UsuarioController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getServletPath();

        if (acao.equals("/reset")) {
            String token = request.getParameter("token");
            TokenModel tokenModel = TokenDAO.buscarPorToken(token);
            if (tokenModel == null || tokenModel.getId() == null) {
                request.setAttribute("status", "error");
                request.setAttribute("message", "Token inválido");
                request.getRequestDispatcher("reset_senha.jsp").forward(request, response);
                return;
            }
            if (tokenModel.getStatus() == 0) {
                request.setAttribute("status", "error");
                request.setAttribute("message", "Token já utilizado");
                request.getRequestDispatcher("reset_senha.jsp").forward(request, response);
                return;
            }
            request.setAttribute("token", token);
            request.getRequestDispatcher("reset_senha.jsp").forward(request, response);
            return;
        }


        if (acao.equals("/valida")) {
            String token = request.getParameter("token");
            TokenModel tokenModel = TokenDAO.buscarPorToken(token);
            if (tokenModel == null || tokenModel.getId() == null) {
                request.setAttribute("status", "error");
                request.setAttribute("message", "Token inválido");
                request.getRequestDispatcher("valida.jsp").forward(request, response);
                return;
            }
            if (tokenModel.getStatus() == 0) {
                request.setAttribute("status", "error");
                request.setAttribute("message", "Token já utilizado");
                request.getRequestDispatcher("valida.jsp").forward(request, response);
                return;
            }
            boolean result = TokenDAO.desativaToken(tokenModel);
            boolean result2 = UsuarioDAO.ativaUsuario(tokenModel.getUsuario());
            if (result && result2) {
                request.setAttribute("status", "success");
                request.setAttribute("message", "Usuário ativado com sucesso");
                request.getRequestDispatcher("valida.jsp").forward(request, response);
                return;
            }
            request.setAttribute("status", "error");
            request.setAttribute("message", "Erro ao ativar usuário");
            request.getRequestDispatcher("valida.jsp").forward(request, response);
            return;
        }


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

        if (acao.equals("/reset")) {
            System.out.println("reset");
            String token = request.getParameter("token");
            System.out.println("token:1 " + token);
            TokenModel tokenModel = TokenDAO.buscarPorToken(token);
            if (tokenModel == null || tokenModel.getId() == null) {
                request.setAttribute("status", "error");
                request.setAttribute("message", "Token inválido");
                request.getRequestDispatcher("confirm_reset.jsp").forward(request, response);
                return;
            }
            if (tokenModel.getStatus() == 0) {
                request.setAttribute("status", "error");
                request.setAttribute("message", "Token já utilizado");
                request.getRequestDispatcher("confirm_reset.jsp").forward(request, response);
                return;
            }
            String senha = request.getParameter("senha");
            String senhaCrip = SenhaUtil.hashSenha(senha);
            System.out.println("senha: " + senha);
            System.out.println("senhaCrip: " + senhaCrip);
            System.out.println((BCrypt.checkpw(senha, senhaCrip)));
            UsuarioModel usuario = tokenModel.getUsuario();
            usuario.setSenha(senhaCrip);
            System.out.println("usuario: " + usuario);
            boolean result = UsuarioDAO.atualizarSenha(usuario);
            if (result) {
                boolean result2 = TokenDAO.desativaToken(tokenModel);
                if (result2) {
                    request.setAttribute("status", "success");
                    request.setAttribute("message", "Senha alterada com sucesso");
                    request.getRequestDispatcher("confirm_reset.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("status", "error");
                request.setAttribute("message", "Erro ao alterar senha");
                request.getRequestDispatcher("confirm_reset.jsp").forward(request, response);
                return;
            }
            request.setAttribute("status", "error");
            request.setAttribute("message", "Erro ao alterar senha");
            request.getRequestDispatcher("confirm_reset.jsp").forward(request, response);
            return;
        }
        if (acao.equals("/resetPassword")) {
            String email = request.getParameter("email");
            UsuarioModel usuario = UsuarioDAO.listarPorEmail(email);
            if (usuario != null && usuario.getId() != null) {
                TokenModel tokenPass = new TokenModel();
                tokenPass.setUsuarioId(usuario.getId());
                String tokenGerado = SenhaUtil.createToken();
                tokenPass.setToken(tokenGerado);
                boolean result = TokenDAO.inserir(tokenPass);

                if (result) {
                    Runnable task = () -> {
                        MailUtil.send(email, "Alteração de Senha", "<a href=\"http://localhost:8080/" + request.getContextPath() + "/reset?token=" + tokenGerado + "\">Clique</a>");
                    };
                    Thread thread = new Thread(task);
                    thread.start();
                    request.getRequestDispatcher("email_sent.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("status", "error");
                request.setAttribute("message", "Erro ao gerar token");
                request.getRequestDispatcher("recupera_senha.jsp").forward(request, response);
                return;
            }
            request.setAttribute("status", "error");
            request.setAttribute("message", "Email não cadastrado");
            request.getRequestDispatcher("recupera_senha.jsp").forward(request, response);
            return;
        }

        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        String senhaCrip = SenhaUtil.hashSenha(senha);

        UsuarioModel usuario = UsuarioDAO.listarPorEmail(email);
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
            usuario = UsuarioDAO.listarPorEmail(email);
            TokenModel token = new TokenModel();
            token.setUsuarioId(usuario.getId());
            String tokenGerado = SenhaUtil.createToken();
            token.setToken(tokenGerado);
            boolean result = TokenDAO.inserir(token);
            if (result) {
                Runnable task = () -> {
                    MailUtil.send(email, "Sua conta foi criada", "<a href=\"http://localhost:8080/" + request.getContextPath() + "/valida?token=" + tokenGerado + "\">Clique</a>");
                };
                Thread thread = new Thread(task);
                thread.start();
            }
        }


        doGet(request, response);

    }

}
