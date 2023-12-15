<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.FileModel" %>
<%@ page import="dao.FileDAO" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Edição de Arquivo</title>
    <link
            href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
            rel="stylesheet"
    />
</head>
<body class="bg-gray-200 flex items-center justify-center h-screen">
<div class="bg-white p-8 rounded shadow-md w-96">
    <h2 class="text-2xl font-bold mb-8 text-center">Edição de Arquivo</h2>
    <% int id = Integer.parseInt(request.getParameter("id")); %>
    <% FileModel documento = FileDAO.buscarById(id); %>
    <% if (documento == null) { %>
    <div class="text-red-500 text-center">
        <p>Documento não encontrado!</p>
        <a href="<%=request.getContextPath()%>/viewDocuments" class="text-indigo-600 hover:text-indigo-500">Voltar para
            Documentos</a>
    </div>
    <% } else { %>
    <form
            action="<%=request.getContextPath()%>/updateDocument"
            method="post"
            class="space-y-6"
            enctype="multipart/form-data"
    >
        <input type="hidden" name="id" value="<%= documento.getId() %>">
        <div>
            <label for="nome" class="block text-sm font-medium text-gray-700"
            >Nome do arquivo:</label
            >
            <input
                    type="text"
                    id="nome"
                    name="nome"
                    value="<%= documento.getNome() %>"
                    required
                    class="mt-1 block w-full py-2 px-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
        </div>
        <div>
            <label for="arquivo" class="block text-sm font-medium text-gray-700"
            >Arquivo:</label
            >
            <input
                    type="file"
                    id="arquivo"
                    name="arquivo"
                    accept="application/pdf"
                    class="mt-1 block w-full py-2 px-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
        </div>
        <div>
            <button
                    type="submit"
                    class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            >
                Salvar
            </button>
        </div>
    </form>

    <div class="mt-4">
        <a href="<%=request.getContextPath()%>/authArea/files/myFiles.jsp" class="text-indigo-600 hover:text-indigo-500">Voltar para
            Documentos</a>
    </div>
    <% } %>
</div>
</body>
</html>