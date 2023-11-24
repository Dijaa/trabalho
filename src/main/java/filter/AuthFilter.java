package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter("/authArea/*")
public class AuthFilter extends HttpFilter implements Filter {


    public AuthFilter() {
        super();
    }

    public void destroy() {
        // TODO Auto-generated method stub
    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);


        boolean estaLogado = (session != null && session.getAttribute("usuario") != null);

        String urlLogin = httpRequest.getContextPath() + "/login";

        boolean eRequisicaoLogin = httpRequest.getRequestURI().equals(urlLogin);

        boolean ePaginaLogin = httpRequest.getRequestURI().endsWith("index.jsp");

        if (estaLogado || eRequisicaoLogin) {
            chain.doFilter(request, response);
        }
        else if(estaLogado && (eRequisicaoLogin || ePaginaLogin)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/authArea/");
            dispatcher.forward(request, response);
        }
        else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
    }


    public void init(FilterConfig fConfig) throws ServletException {

    }

}
