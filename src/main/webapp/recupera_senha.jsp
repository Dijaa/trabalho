<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Recuperar Senha</title>
    <link
            href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
            rel="stylesheet"
    />
</head>
<body class="bg-gray-200 flex items-center justify-center h-screen">
<div class="bg-white p-8 rounded shadow-md w-96 text-center">
    <h2 class="text-2xl font-bold mb-8">Recuperar senha</h2>
    <p class="mb-4">Lamentamos pela situação, insira seu e-mail para recuperar sua senha.</p>
    <% if ("error".equals(request.getAttribute("status"))) { %>

    <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
        <strong class="font-bold">Erro!</strong>
        <span class="block sm:inline"><%=request.getAttribute("message")%></span>
    </div>
    <% } %>
    <form method="post" action="<%=request.getContextPath()%>/resetPassword">
        <div>
            <input type="email" name="email" required
                   class="mt-1 block w-full py-2 px-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            >
        </div>
        <div class="mt-4">
            <button type="submit" class="bg-indigo-500 text-white px-4 py-2 rounded">Enviar</button>
        </div>
    </form>
    <br>
    <a href="<%=request.getContextPath()%>" class="text-indigo-600 hover:text-indigo-500">Voltar para o login</a>
</div>
</body>
</html>