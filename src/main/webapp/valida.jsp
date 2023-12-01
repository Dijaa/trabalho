<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Validação de Token</title>
    <link
            href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
            rel="stylesheet"
    />
</head>
<body class="bg-gray-200 flex items-center justify-center h-screen">
<div class="bg-white p-8 rounded shadow-md w-96">
    <h2 class="text-2xl font-bold mb-8 text-center">Validação de Usuário</h2>

    <% if ("success".equals(request.getAttribute("status"))) { %>
    <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative" role="alert">
        <strong class="font-bold">Sucesso!</strong>
        <span class="block sm:inline"><%= request.getAttribute("message") %></span>
        <img src="./src/img/winners-animate.svg">
        <p class="mt-4">
            Usuário validado com sucesso! <br>
            <a href="<%=request.getContextPath()%>/" class="text-indigo-600 hover:text-indigo-500">Ir para o login</a>
        </p>
    </div>
    <% } else { %>
    <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
        <strong class="font-bold">Erro!</strong>
        <span class="block sm:inline"><%= request.getAttribute("message") %></span>
    </div>
    <% } %>
</div>
</body>
</html>