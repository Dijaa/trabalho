<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Nova Senha</title>
    <link
            href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
            rel="stylesheet"
    />
</head>
<body class="bg-gray-200 flex items-center justify-center h-screen">
<div class="bg-white p-8 rounded shadow-md w-96 text-center">
    <% if ("error".equals(request.getAttribute("status"))) { %>

    <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
        <strong class="font-bold">Erro!</strong>
        <span class="block sm:inline"><%=request.getAttribute("message")%></span>
    </div>
    <% } else { %>
    <h2 class="text-2xl font-bold mb-8">Definir Nova Senha</h2>
    <form method="post" action="<%=request.getContextPath()%>/reset">
        <label for="senha" class="block text-left text-sm font-medium text-gray-600">Nova Senha</label>
        <input type="password" id="senha" name="senha" required class="w-full px-3 py-2 mb-4 border rounded-md">


        <label for="confirmarSenha" class="block text-left text-sm font-medium text-gray-600">Confirmar Senha</label>
        <input type="password" id="confirmarSenha" name="confirmarSenha" required
               class="w-full px-3 py-2 mb-4 border rounded-md" onblur="validaSenha(this)">
    <input type="hidden" value="<%= request.getAttribute("token") %>" name="token" id="token">
        <button type="submit" class="bg-indigo-500 text-white px-4 py-2 rounded">Enviar</button>
    </form>
    <% } %>
    <br>
    <a href="<%=request.getContextPath()%>" class="text-indigo-600 hover:text-indigo-500">Voltar para o login</a>
</div>
<script>
    function validaSenha(input) {
        if (input.value != document.getElementById('senha').value) {
            input.setCustomValidity('Repita a senha corretamente');
        } else {
            input.setCustomValidity('');
        }
    }
</script>
</body>

</html>
