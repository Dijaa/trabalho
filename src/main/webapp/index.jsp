<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.UsuarioModel" %>
<%
    String currentURL = request.getRequestURL().toString();
    String targetURL = request.getContextPath();

    if (currentURL.equals("http://localhost:8080/trabalho_war_exploded/index.jsp")) {
        response.sendRedirect(targetURL);
    }
%>
<%
    UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuario");
    if (usuario != null) {
        response.sendRedirect(targetURL + "/authArea");
    }
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Formulário de Login e Registro</title>
    <link
            href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
            rel="stylesheet"
    />
    <script>
        function register() {
            document.getElementById("loginForm").style.display = "none";
            document.getElementById("registerForm").style.display = "block";
        }
        function login() {
            document.getElementById("registerForm").style.display = "none";
            document.getElementById("loginForm").style.display = "block";
        }
    </script>
</head>
<body class="bg-gray-200 flex items-center justify-center h-screen">
<div class="bg-white p-8 rounded shadow-md w-96">
    <div id="loginForm">
        <h2 class="text-2xl font-bold mb-8 text-center">Login </h2>
        <% if ("login".equals(request.getAttribute("type"))) { %>

        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
            <strong class="font-bold">Erro!</strong>
            <span class="block sm:inline"><%=request.getAttribute("error")%></span>
        </div>
        <% } %>
        <form
                action="<%=request.getContextPath()%>/login"
                method="post"
                class="space-y-6"
        >
            <div>
                <label for="email" class="block text-sm font-medium text-gray-700"
                >E-mail:</label
                >
                <input
                        type="email"
                        id="email"
                        name="email"
                        required
                        class="mt-1 block w-full py-2 px-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                />
            </div>
            <div>
                <label for="senha" class="block text-sm font-medium text-gray-700"
                >Senha:</label
                >
                <input
                        type="password"
                        id="senha"
                        name="senha"
                        required
                        class="mt-1 block w-full py-2 px-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                />
            </div>
            <div>
                <button
                        type="submit"
                        class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                    Entrar
                </button>
            </div>
        </form>
        <p class="text-center">
            Não tem uma conta?
            <a
                    href="#"
                    id="showRegisterForm"
                    class="text-indigo-600 hover:text-indigo-500"
            >Registrar</a
            >
        </p>
        <p class="text-center">

            <a
                href="recupera_senha.jsp"
                id="showRecuperationForm"
                class="text-indigo-600 hover:text-indigo-500">Esqueceu sua senha</a
            >
        </p>
    </div>
    <div id="RecuperationForm" class="hidden">
        <h2 class="text-2xl font-bold mb-8 text-center">Recuperar Senha</h2>
    </div>
    <div id="registerForm" class="hidden">
        <h2 class="text-2xl font-bold mb-8 text-center">Registrar</h2>
        <% if ("register".equals(request.getAttribute("type"))) { %>
        <script>
            register();
        </script>
        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
            <strong class="font-bold">Erro!</strong>
            <span class="block sm:inline"><%=request.getAttribute("error")%></span>
        </div>
        <% } %>
        <form
                action="UsuarioController"
                method="post"
                class="space-y-6"
        >
            <div>
                <label for="nome" class="block text-sm font-medium text-gray-700"
                >Nome:</label
                >
                <input
                        type="text"
                        id="nome"
                        name="nome"
                        required
                        class="mt-1 block w-full py-2 px-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                />
            </div>
            <div>
                <label for="email" class="block text-sm font-medium text-gray-700"
                >E-mail:</label
                >
                <input
                        type="email"
                        id="email"
                        name="email"
                        required
                        class="mt-1 block w-full py-2 px-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                />
            </div>
            <div>
                <label for="senha" class="block text-sm font-medium text-gray-700"
                >Senha:</label
                >
                <input
                        type="password"
                        id="senha"
                        name="senha"
                        required
                        class="mt-1 block w-full py-2 px-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                />
            </div>
            <div>
                <button
                        type="submit"
                        class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                    Registrar
                </button>
            </div>
        </form>
        <p class="text-center">
            Já tem uma conta?
            <a
                    href="#"
                    id="showLoginForm"
                    class="text-indigo-600 hover:text-indigo-500"
            >Entrar</a
            >
        </p>
    </div>
</div>
<script>

    document
        .getElementById("showRegisterForm")
        .addEventListener("click", register);

    document
        .getElementById("showLoginForm")
        .addEventListener("click", login);


</script>
</body>
</html>
