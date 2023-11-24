<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>E-mail Enviado</title>
    <link
            href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
            rel="stylesheet"
    />
</head>
<body class="bg-gray-200 flex items-center justify-center h-screen">
<div class="bg-white p-8 rounded shadow-md w-96 text-center">
    <h2 class="text-2xl font-bold mb-8">E-mail Enviado</h2>
    <a href="<%=request.getContextPath()%>" class="bg-indigo-500 text-white px-4 py-2 rounded inline-block">Voltar para o login</a>
</div>
</body>
</html>
