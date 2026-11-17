package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Abrigo;
import model.Company;
import model.ModelException;
import model.dao.AbrigoDAO;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = {"/abrigos", "/abrigo/form",
		"/abrigo/insert", "/abrigo/update", "/abrigo/delete"})
public class AbrigosController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		switch (req.getServletPath()) {
		case "/abrigo/form": {
			CommonsController.listCompanies(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-abrigo.jsp");
			break;
		}
		case "/abrigo/update": {
			Abrigo abrigo = loadAbrigo(req);

			CommonsController.listCompanies(req);
			req.setAttribute("action", "update");
			req.setAttribute("abrigo", abrigo);
			ControllerUtil.forward(req, resp, "/form-abrigo.jsp");
			break;
		}
		default:
			listAbrigos(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/abrigos.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		switch (req.getServletPath()) {
		case "/abrigo/insert":
			insertAbrigo(req);
			break;
		case "/abrigo/update":
			updateAbrigo(req);
			break;
		case "/abrigo/delete":
			deleteAbrigo(req);
			break;
		default:
			System.out.println("URL inválida " + req.getRequestURI());
		}

		ControllerUtil.redirect(resp, req.getContextPath() + "/abrigos");
	}

	private void listAbrigos(HttpServletRequest req) {
		AbrigoDAO dao = DAOFactory.createDAO(AbrigoDAO.class);

		try {
			int total = dao.count();
			int page = ControllerUtil.currentPage(req, total);

			List<Abrigo> abrigos = dao.listAll(ControllerUtil.PAGE_SIZE, ControllerUtil.offset(page));

			req.setAttribute("abrigos", abrigos);
			ControllerUtil.setPagination(req, page, total, "/abrigos");
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private Abrigo loadAbrigo(HttpServletRequest req) {
		int id = Integer.parseInt(req.getParameter("abrigoId"));

		AbrigoDAO dao = DAOFactory.createDAO(AbrigoDAO.class);

		try {
			return dao.findById(id);
		} catch (ModelException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** Monta o objeto Abrigo com os dados enviados pelo formulário. */
	private Abrigo readForm(HttpServletRequest req, Abrigo abrigo) {
		abrigo.setNome(req.getParameter("nome"));
		abrigo.setTelefone(req.getParameter("telefone"));
		abrigo.setDataFundacao(ControllerUtil.formatDate(req.getParameter("dataFundacao")));
		abrigo.setCapacidade(Integer.parseInt(req.getParameter("capacidade")));
		// checkbox desmarcado não é enviado pelo navegador
		abrigo.setAceitaVoluntarios(req.getParameter("aceitaVoluntarios") != null);
		abrigo.setDescricao(req.getParameter("descricao"));
		abrigo.setCompany(new Company(Integer.parseInt(req.getParameter("company"))));
		return abrigo;
	}

	private void insertAbrigo(HttpServletRequest req) {
		Abrigo abrigo = readForm(req, new Abrigo());

		AbrigoDAO dao = DAOFactory.createDAO(AbrigoDAO.class);

		try {
			if (dao.save(abrigo))
				ControllerUtil.sucessMessage(req, "Abrigo '" + abrigo.getNome() + "' salvo com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Abrigo '" + abrigo.getNome() + "' não pode ser salvo.");
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updateAbrigo(HttpServletRequest req) {
		int id = Integer.parseInt(req.getParameter("abrigoId"));
		Abrigo abrigo = readForm(req, new Abrigo(id));

		AbrigoDAO dao = DAOFactory.createDAO(AbrigoDAO.class);

		try {
			if (dao.update(abrigo))
				ControllerUtil.sucessMessage(req, "Abrigo '" + abrigo.getNome() + "' atualizado com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Abrigo '" + abrigo.getNome() + "' não pode ser atualizado.");
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deleteAbrigo(HttpServletRequest req) {
		int id = Integer.parseInt(req.getParameter("id"));

		AbrigoDAO dao = DAOFactory.createDAO(AbrigoDAO.class);

		try {
			Abrigo abrigo = dao.findById(id);

			if (abrigo == null)
				throw new ModelException("Abrigo não encontrado para exclusão.");

			if (dao.delete(abrigo))
				ControllerUtil.sucessMessage(req, "Abrigo '" + abrigo.getNome() + "' excluído com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Abrigo '" + abrigo.getNome()
						+ "' não pode ser excluído. Há animais vinculados a ele.");
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
}
