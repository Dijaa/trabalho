<%@ page import="model.UsuarioModel" %>
<%@ page import="model.FileModel" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.FileDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Documentos</title>
    <link
            href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
            rel="stylesheet"
    />
</head>
<body class="bg-gray-200 flex items-center justify-center h-screen">
<div class="bg-white p-8 rounded shadow-md w-96">
    <h2 class="text-2xl font-bold mb-8 text-center">Documentos</h2>
    <div class="grid grid-cols-1 gap-4 mt-8">
        <a href="<%=request.getContextPath()%>/authArea/"
           class="bg-indigo-600 hover:bg-indigo-500 text-white font-bold py-2 px-4 rounded">Voltar ao Inicio</a>
        <% List<FileModel> arquivos = FileDAO.listaTodosAprovado(); %>
        <% if (arquivos == null) { %>
        <p class="text-center">Nenhum documento encontrado</p>
        <% } else { %>
        <% for (FileModel documento : arquivos) { %>
        <div class="border border-gray-200 rounded p-4">
            <p><strong>Nome:</strong> <%= documento.getNome() %>
            </p>
            <p>
                <strong>Usuario:</strong> <%= documento.getUsuario().getNome() %>
            </p>
            <p><strong>Aprovador:</strong> <%= documento.getAprovador().getNome() %>
            </p>

<div>
            <a href="<%=request.getContextPath()%>/<%= documento.getCaminho() %>" target="_blank"
               class="text-indigo-600 hover:text-indigo-500 hover:bg-gray-200 px-2 py-1 rounded">Visualizar
                Documento</a>
        </div>
        <div>
            <a href="<%=request.getContextPath()%>/download?id=<%= documento.getId() %>"
               class="text-indigo-600 hover:text-indigo-500 hover:bg-gray-200 px-2 py-1 rounded">Baixar Documento</a>
        </div>
    </div>
    <% } %>
    <% } %>
</div>
</div>
<script>
    function deletar(link) {
        if (confirm("Você tem certeza quer deletar esse arquivo?"))
            location.href = link;
    }
</script>
</body>
</html>