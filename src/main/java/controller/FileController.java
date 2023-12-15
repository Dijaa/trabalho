package controller;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.FileDAO;
import model.FileModel;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.Conexao;
import dao.UsuarioDAO;
import model.UsuarioModel;
import util.MailUtil;
import util.SenhaUtil;

@WebServlet(urlPatterns = {"/upload", "/download", "/deleteDocument", "/reprove", "/aprove", "/updateDocument"})
public class FileController extends HttpServlet {

    public FileController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getServletPath();
       /* if(acao.equals("/aproveOrDisaprove")){
            int id = Integer.parseInt(request.getParameter("id"));
            int status = Integer.parseInt(request.getParameter("aprove"));
            int finalStatus = status == 0 ? 2 : 1;
            FileModel file = FileDAO.buscarById(id);
            file.setStatus(finalStatus);
            boolean result = FileDAO.update(file);
            if (result) {
                request.setAttribute("message", "Arquivo Aprovado com Sucesso");
                request.setAttribute("title", "Aprovação Bem Sucedida");
                request.setAttribute("success", true);
            } else {
                request.setAttribute("message", "Ocorreu um Erro na Aprovação do Arquivo");
                request.setAttribute("title", "Aprovação Falhou");
                request.setAttribute("success", false);
            }
            request.getRequestDispatcher("authArea/confirm.jsp").forward(request, response);

        }*/


        if (acao.equals("/deleteDocument")) {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean result = FileDAO.deletar(id);
            if (result) {
                request.setAttribute("message", "Arquivo Deletado com Sucesso");
                request.setAttribute("title", "Remoção Bem Sucedida");
                request.setAttribute("success", true);
            } else {
                request.setAttribute("message", "Ocorreu um Erro na Remoção do Arquivo");
                request.setAttribute("title", "Remoção Falhou");
                request.setAttribute("success", false);
            }
            request.getRequestDispatcher("authArea/confirm.jsp").forward(request, response);
        }

        if (acao.equals("/download")) {


            int id = Integer.parseInt(request.getParameter("id"));
            FileModel file = FileDAO.buscarById(id);
            String token = file.getToken();
            // Aqui você deve buscar o arquivo pelo token e obter o caminho do arquivo
            FileDAO fileDAO = new FileDAO();
            String filePath = fileDAO.getFilePathByToken(token);
            String absoluteFilePath = getServletContext().getRealPath("/") + filePath;
            File downloadFile = new File(absoluteFilePath);
            FileInputStream inStream = new FileInputStream(downloadFile);

            // if you want to use a relative path to context root:
            String relativePath = getServletContext().getRealPath("");
            System.out.println("relativePath = " + relativePath);

            // obtains ServletContext
            ServletContext context = getServletContext();

            // gets MIME type of the file
            String mimeType = context.getMimeType(filePath);
            if (mimeType == null) {
                // set to binary type if MIME mapping not found
                mimeType = "application/octet-stream";
            }
            System.out.println("MIME type: " + mimeType);

            // modifies response
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            // forces download
            String headerKey = "Content-Disposition";
            String desiredFileName = file.getNome() + ".pdf"; // Substitua por qualquer nome que você queira
            String headerValue = String.format("attachment; filename=\"%s\"", desiredFileName);
            response.setHeader(headerKey, headerValue);

            // obtains response's output stream
            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inStream.close();
            outStream.close();

        }
           /* if (acao.equals("/logout")) {
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect(request.getContextPath());
        }*/
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getServletPath();



        if (acao.equals("/reprove")){
            int id = Integer.parseInt(request.getParameter("id"));
            String observacao = request.getParameter("observacao");
            FileModel file = FileDAO.buscarById(id);
            file.setStatus(2);
            file.setObs(observacao);
            boolean result = FileDAO.AproveOrReprove(file);
            if (result) {
                request.setAttribute("message", "Arquivo Reprovado com Sucesso");
                request.setAttribute("title", "Reprovação Bem Sucedida");
                request.setAttribute("success", true);
            } else {
                request.setAttribute("message", "Ocorreu um Erro na Reprovação do Arquivo");
                request.setAttribute("title", "Reprovação Falhou");
                request.setAttribute("success", false);
            }
            request.getRequestDispatcher("authArea/confirm.jsp").forward(request, response);
        }

        if(acao.equals("/aprove")){
            int id = Integer.parseInt(request.getParameter("id"));
            String observacao = request.getParameter("observacao");
            FileModel file = FileDAO.buscarById(id);
            file.setStatus(1);
            file.setObs(observacao);
            boolean result = FileDAO.AproveOrReprove(file);
            if (result) {
                request.setAttribute("message", "Arquivo Aprovado com Sucesso");
                request.setAttribute("title", "Aprovação Bem Sucedida");
                request.setAttribute("success", true);
            } else {
                request.setAttribute("message", "Ocorreu um Erro na Aprovação do Arquivo");
                request.setAttribute("title", "Aprovação Falhou");
                request.setAttribute("success", false);
            }
            request.getRequestDispatcher("authArea/confirm.jsp").forward(request, response);
        }
        if (acao.equals("/updateDocument")) {
            if (ServletFileUpload.isMultipartContent(request)) {
                try {
                    FileModel file = new FileModel();
                    List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

                    String filename = null;
                    FileItem fileItem = null;
                    int id = 0;

                    for (FileItem item : multiparts) {
                        if (item.isFormField()) {
                            if (item.getFieldName().equals("nome")) {
                                filename = item.getString();
                            } else if (item.getFieldName().equals("id")) {
                                id = Integer.parseInt(item.getString());
                            }
                        } else {
                            fileItem = item;
                        }
                    }

                    if (filename == null) {
                        throw new ServletException("Filename not provided");
                    }

                    file = FileDAO.buscarById(id);
                    file.setNome(filename);


                    if (fileItem != null && fileItem.getSize() > 0) {

                        String path = getServletContext().getRealPath("authArea/files") + File.separator;
                        String name = new File(fileItem.getName()).getName();
                        String ext = name.substring(name.lastIndexOf("."));
                        String newFileName = file.getToken() + ext;
                        System.out.println("nomeee " + newFileName);
                        fileItem.write(new File(path + newFileName));
                        file.setCaminho("authArea/files" + "/" + newFileName);
                    }

                    boolean result = FileDAO.update(file);
                    if (result) {
                        request.setAttribute("message", "Arquivo Atualizado com Sucesso");
                        request.setAttribute("title", "Atualização Bem Sucedida");
                        request.setAttribute("success", true);
                    } else {
                        request.setAttribute("message", "Ocorreu um Erro na Atualização do Arquivo");
                        request.setAttribute("title", "Atualização Falhou");
                        request.setAttribute("success", false);
                    }
                    request.getRequestDispatcher("authArea/confirm.jsp").forward(request, response);
                } catch (Exception ex) {
                    request.setAttribute("message", "Ocorreu um Erro na Atualização do Arquivo");
                    request.setAttribute("success", false);
                    request.getRequestDispatcher("authArea/confirm.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("message", "Desculpe este Servlet lida apenas com pedido de upload de arquivos");
                request.setAttribute("success", false);
                request.getRequestDispatcher("authArea/confirm.jsp").forward(request, response);
            }
        }
        if (acao.equals("/upload")) {

            if (ServletFileUpload.isMultipartContent(request)) {
                try {
                    FileModel file = new FileModel();
                    List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

                    // input filename - change the file to filename
                    // input file - field of the file
                    // i get the filename change the file name, so a save it

                    String filename = null;


                    for (FileItem item : multiparts) {
                        if (item.isFormField() && item.getFieldName().equals("filename")) {
                            filename = item.getString();
                            break;
                        }
                    }

                    if (filename == null) {
                        throw new ServletException("Filename not provided");
                    }

// ...
                    System.out.println(multiparts.get(0).toString());
                    System.out.println(multiparts.get(1).toString());
                    System.out.println(filename);


                    String path = getServletContext().getRealPath("authArea/files") + File.separator;
                    System.out.println(path);
                    for (FileItem item : multiparts) {
                        if (!item.isFormField()) {
                            String name = new File(item.getName()).getName();
                            System.out.println(name);
                            String ext = name.substring(name.lastIndexOf("."));
                            // Generate a unique token for the file
                            String token = UUID.randomUUID().toString();

                            // Use the token as part of the file name
                            String newFileName = token + ext;

                            item.write(new File(path + newFileName));
                            file.setNome(filename);
                            file.setCaminho("authArea/files" + "/" + newFileName);
                            file.setUsuario((UsuarioModel) request.getSession().getAttribute("usuario"));
                            file.setUsuario_id(file.getUsuario().getId());
                            file.setToken(token);
                            UsuarioModel aprovador = UsuarioDAO.randomizaAprovador(file.getUsuario());
                            if (aprovador == null) {
                                throw new ServletException("Aprovador não encontrado");
                            }
                            file.setAprovador(aprovador);
                            file.setAprovador_id(aprovador.getId());

                            boolean inseriu = FileDAO.inserir(file);
                            System.out.println(inseriu);
                            if (inseriu) {
                                request.setAttribute("message", "Arquivo Enviado com Sucesso");
                                request.setAttribute("title", "Upload Bem Sucedido");
                                request.setAttribute("success", true);
                            } else {
                                request.setAttribute("message", "Ocorreu um Erro no Upload do Arquivo");
                                request.setAttribute("title", "Upload Falhou");
                                request.setAttribute("success", false);
                            }
                            /*
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
                             */

                            // envia email para o aprovador e para o usuario
                            Runnable task = () -> {

                                String workspace = request.getContextPath();
                                MailUtil.send(file.getAprovador().getEmail(), "Arquivo Enviado para sua Aprovação", "<a href=\"http://localhost:8080" + workspace + "/authArea/files/toAprove.jsp\">Clique</a>");
                                MailUtil.send(file.getUsuario().getEmail(), "Arquivo Enviado com Sucesso", "<a href=\"http://localhost:8080" + workspace + "/authArea/files/myFiles.jsp\">Clique</a>");
                            };
                            Thread thread = new Thread(task);
                            thread.start();


                            request.getRequestDispatcher("authArea/confirm.jsp").forward(request, response);

                            System.out.println(file);
                        }
                    }
// ... 
                } catch (Exception ex) {
                    request.setAttribute("message", "Ocorreu um Erro no Upload do Arquivo");
                    request.setAttribute("success", false);
                    request.getRequestDispatcher("authArea/confirm.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("message", "Desculpe este Servlet lida apenas com pedido de upload de arquivos");
                request.setAttribute("success", false);
                request.getRequestDispatcher("authArea/confirm.jsp").forward(request, response);
            }

        }
    }


}
