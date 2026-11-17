package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ModelException;
import model.PasswordUtil;
import model.User;
import model.dao.DAOFactory;
import model.dao.UserDAO;

/**
 * Controla a entrada (login) e a saída (logout) do sistema.
 *
 * GET  /login  -> exibe a tela de login
 * POST /login  -> valida e-mail e senha e guarda o usuário na sessão
 * GET  /logout -> encerra a sessão
 */
@WebServlet(urlPatterns = {"/login", "/logout"})
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/** Nome do atributo de sessão que guarda o usuário logado. */
	public static final String LOGGED_USER = "loggedUser";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (req.getServletPath().equals("/logout")) {
			HttpSession session = req.getSession(false);
			if (session != null)
				session.invalidate();

			ControllerUtil.redirect(resp, req.getContextPath() + "/login");
			return;
		}

		// Já está logado? Vai direto para o início.
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute(LOGGED_USER) != null) {
			ControllerUtil.redirect(resp, req.getContextPath() + "/");
			return;
		}

		ControllerUtil.forward(req, resp, "/login.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String email = req.getParameter("email");
		String password = req.getParameter("password");

		UserDAO dao = DAOFactory.createDAO(UserDAO.class);

		try {
			User user = dao.findByEmail(email);

			if (user != null && PasswordUtil.matches(password, user.getPassword())) {
				// Troca a sessão ao logar (evita fixação de sessão)
				HttpSession old = req.getSession(false);
				if (old != null)
					old.invalidate();

				req.getSession(true).setAttribute(LOGGED_USER, user);
				ControllerUtil.redirect(resp, req.getContextPath() + "/");
				return;
			}

			req.setAttribute("error", "E-mail ou senha inválidos.");
		} catch (ModelException e) {
			e.printStackTrace();
			req.setAttribute("error", "Não foi possível acessar o banco de dados.");
		}

		req.setAttribute("email", email);
		ControllerUtil.forward(req, resp, "/login.jsp");
	}
}
