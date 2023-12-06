package controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.Conexao;
import dao.UsuarioDAO;
import model.UsuarioModel;
import util.SenhaUtil;

@WebServlet(urlPatterns = {"/upload",})
public class FileController extends HttpServlet {

    public FileController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getServletPath();
        if (acao.equals("/logout")) {
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect(request.getContextPath());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getServletPath();
        if (acao.equals("/upload")) {
            System.out.println(1);

            if (ServletFileUpload.isMultipartContent(request)) {
                try {
                    List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                    for (FileItem item : multiparts) {
                        if (!item.isFormField()) {
                            String name = new File(item.getName()).getName();
                            item.write(new File("../files" + File.separator + name));
                        }
                    }
                    // File uploaded successfully
                    request.setAttribute("message", "File Uploaded Successfully");
                } catch (Exception ex) {
                    request.setAttribute("message", "File Upload Failed due to " + ex);
                }
            } else {
                request.setAttribute("message", "Sorry this Servlet only handles file upload request");
            }
            request.getRequestDispatcher("/result.jsp").forward(request, response);

        }
    }

}
