package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ControllerUtil {

	private static String MESSAGE_KEY = "message";
	private static int ERROR = 0;
	private static int SUCESS = 1;

	static void forward(HttpServletRequest req, HttpServletResponse resp, String target)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher(target);

		dispatcher.forward(req, resp);
	}

	static void redirect(HttpServletResponse resp, String target) throws IOException {
		resp.sendRedirect(target);
	}

	static void sucessMessage(HttpServletRequest req, String message) {
		req.getSession().setAttribute(MESSAGE_KEY, message);
		req.getSession().setAttribute("alertType", SUCESS);
	}

	static void errorMessage(HttpServletRequest req, String message) {
		req.getSession().setAttribute(MESSAGE_KEY, message);
		req.getSession().setAttribute("alertType", ERROR);
	}

	static void transferSessionMessagesToRequest(HttpServletRequest req) {
		if (req.getSession().getAttribute(MESSAGE_KEY) != null) {
			String message = (String) req.getSession().getAttribute(MESSAGE_KEY);
			req.setAttribute(MESSAGE_KEY, message);
			req.getSession().removeAttribute(MESSAGE_KEY);
		}

		if (req.getSession().getAttribute("alertType") != null) {
			Integer message = (Integer) req.getSession().getAttribute("alertType");
			req.setAttribute("alertType", message);
			req.getSession().removeAttribute("alertType");
		}
	}

	public static Date formatDate(String stringDate) {
		if (stringDate == null || stringDate.isBlank())
			return null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			return dateFormat.parse(stringDate);
		} catch (ParseException e) {
			return null;
		}
	}

	// ------------------------------------------------------------------
	// Paginação
	// ------------------------------------------------------------------

	/** Quantidade de registros exibidos por página em todas as listagens. */
	static final int PAGE_SIZE = 5;

	/**
	 * Lê o parâmetro "page" da URL (ex.: /users?page=2), garantindo que
	 * fique entre 1 e o total de páginas.
	 */
	static int currentPage(HttpServletRequest req, int totalItems) {
		int page = 1;

		try {
			page = Integer.parseInt(req.getParameter("page"));
		} catch (NumberFormatException e) {
			// parâmetro ausente ou inválido: fica na página 1
		}

		return Math.max(1, Math.min(page, totalPages(totalItems)));
	}

	static int offset(int page) {
		return (page - 1) * PAGE_SIZE;
	}

	static int totalPages(int totalItems) {
		return Math.max(1, (int) Math.ceil(totalItems / (double) PAGE_SIZE));
	}

	/**
	 * Envia para a JSP os dados usados pelo fragmento pagination.jsp.
	 *
	 * @param path caminho da listagem sem o contexto (ex.: "/users")
	 */
	static void setPagination(HttpServletRequest req, int page, int totalItems, String path) {
		req.setAttribute("currentPage", page);
		req.setAttribute("totalPages", totalPages(totalItems));
		req.setAttribute("totalItems", totalItems);
		req.setAttribute("paginationUrl", req.getContextPath() + path);
	}
}
