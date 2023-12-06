<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Upload de Arquivo</title>
    <link
            href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
            rel="stylesheet"
    />
</head>
<body class="bg-gray-200 flex items-center justify-center h-screen">
<div class="bg-white p-8 rounded shadow-md w-96 text-center">
    <h2 class="text-2xl font-bold mb-8">Upload de Arquivo</h2>
    <form method="post" action="<%=request.getContextPath()%>/upload" enctype="multipart/form-data">
        <label for="file" class="block text-left text-sm font-medium text-gray-600">Escolha um arquivo</label>
        <input type="file" id="file" name="file" required class="w-full px-3 py-2 mb-4 border rounded-md" accept="application/pdf">
        <button type="submit" class="bg-indigo-500 text-white px-4 py-2 rounded">Enviar</button>
    </form>
    <br>
    <a href="<%=request.getContextPath()%>" class="text-indigo-600 hover:text-indigo-500">Voltar para o início</a>
</div>
</body>
</html>