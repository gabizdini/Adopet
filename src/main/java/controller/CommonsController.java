package controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import model.Abrigo;
import model.ModelException;
import model.User;
import model.dao.AbrigoDAO;
import model.dao.CompanyDAO;
import model.dao.DAOFactory;
import model.dao.UserDAO;

/**
 * Carrega as listas usadas nos <select> dos formulários
 * (chaves estrangeiras).
 */
public class CommonsController {

	public static void listUsers(HttpServletRequest req) {
		UserDAO dao = DAOFactory.createDAO(UserDAO.class);

		List<User> listUsers = null;
		try {
			listUsers = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}

		if (listUsers != null)
			req.setAttribute("users", listUsers);
	}

	/** Associações para o select do formulário de Abrigo. */
	public static void listCompanies(HttpServletRequest req) {
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);

		try {
			req.setAttribute("companies", dao.listAll());
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	/** Abrigos para o select do formulário de Animal. */
	public static void listAbrigos(HttpServletRequest req) {
		AbrigoDAO dao = DAOFactory.createDAO(AbrigoDAO.class);

		try {
			List<Abrigo> abrigos = dao.listAll();
			req.setAttribute("abrigos", abrigos);
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

}
