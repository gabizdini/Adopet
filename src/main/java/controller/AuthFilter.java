package controller;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Filtro de autenticação: toda requisição passa por aqui.
 * Quem não estiver logado é redirecionado para a tela de login,
 * exceto nas rotas públicas (login e arquivos estáticos).
 */
@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {

	private static final String[] PUBLIC_PATHS = {
			"/login", "/css/", "/js/", "/fonts/", "/images/"
	};

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String path = req.getRequestURI().substring(req.getContextPath().length());

		HttpSession session = req.getSession(false);
		boolean loggedIn = session != null
				&& session.getAttribute(LoginController.LOGGED_USER) != null;

		if (loggedIn || isPublic(path)) {
			chain.doFilter(request, response);
		} else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
	}

	private boolean isPublic(String path) {
		for (String publicPath : PUBLIC_PATHS) {
			if (path.equals(publicPath) || (publicPath.endsWith("/") && path.startsWith(publicPath)))
				return true;
		}
		return false;
	}
}
