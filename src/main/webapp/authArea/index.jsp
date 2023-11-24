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
    <h2 class="text-2xl font-bold mb-8 text-center">Bem-vindo, <%= usuario.getNome() %> </h2>
    <p class="text-center">
        <a
                href="<%=request.getContextPath()%>/logout"
                class="text-indigo-600 hover:text-indigo-500"
        >Clique aqui para deslogar</a
        >
    </p>
</div>
</body>
</html>