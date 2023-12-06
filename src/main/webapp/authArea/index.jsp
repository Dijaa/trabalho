<%@ page import="model.UsuarioModel" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuario"); %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Área Logada</title>
    <link
            href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
            rel="stylesheet"
    />
</head>
<body class="bg-gray-200 flex items-center justify-center h-screen">
<div class="bg-white p-8 rounded shadow-md w-96">
    <h2 class="text-2xl font-bold mb-8 text-center">Bem-vindo, <%= usuario.getNome() %>
    </h2>
    <div class="grid grid-cols-1 gap-4 mt-8">
        <div class="border border-gray-200 rounded p-4">
            <a href="<%=request.getContextPath()%>/viewDocuments" class="text-indigo-600 hover:text-indigo-500">Visualizar
                Documentos</a>
        </div>
        <div class="border border-gray-200 rounded p-4">
            <a href="<%=request.getContextPath()%>/viewPendingDocuments" class="text-indigo-600 hover:text-indigo-500">Visualizar
                Meus Documentos Pendentes</a>
        </div>
        <% if (usuario.getTipo() == 1) { %>
        <div class="border border-gray-200 rounded p-4">
            <a href="<%=request.getContextPath()%>/viewPendingApprovalDocuments"
               class="text-indigo-600 hover:text-indigo-500">Visualizar Documentos Pendentes de Aprovação</a>
        </div>
        <div class="border border-gray-200 rounded p-4">
            <a href="<%=request.getContextPath()%>/editUser" class="text-indigo-600 hover:text-indigo-500">Editar
                Usuarios</a>
        </div>
        <% } %>
    </div>
    <button class="bg-indigo-600 text-white font-bold py-2 px-4 rounded hover:bg-indigo-500 mt-8">
        <a href="<%=request.getContextPath()%>/logout">Clique aqui para deslogar</a>
    </button>
</div>
</html>