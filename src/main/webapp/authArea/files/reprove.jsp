<%@ page import="model.UsuarioModel" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuario"); %>
<% int id = Integer.parseInt(request.getParameter("id")); %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Reprovação de Documento</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet" />
</head>
<body class="bg-gray-200 flex items-center justify-center h-screen">
<div class="bg-white p-8 rounded shadow-md w-96">
    <h2 class="text-2xl font-bold mb-8 text-center">Reprovação de Documento</h2>
    <form action="<%=request.getContextPath()%>/reprove" method="post">
        <input type="hidden" name="id" value="<%= id %>">
        <div class="mb-4">
            <label class="block text-gray-700 text-sm font-bold mb-2" for="observacao">Observação</label>
            <textarea name="observacao" id="observacao" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"></textarea>
        </div>
        <input type="submit" value="Reprovar" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
    </form>
</div>
</body>
</html>