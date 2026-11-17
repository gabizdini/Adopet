package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Abrigo;
import model.Animal;
import model.ModelException;
import model.dao.AnimalDAO;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = {"/animais", "/animal/form",
		"/animal/insert", "/animal/update", "/animal/delete"})
public class AnimaisController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		switch (req.getServletPath()) {
		case "/animal/form": {
			CommonsController.listAbrigos(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-animal.jsp");
			break;
		}
		case "/animal/update": {
			Animal animal = loadAnimal(req);

			CommonsController.listAbrigos(req);
			req.setAttribute("action", "update");
			req.setAttribute("animal", animal);
			ControllerUtil.forward(req, resp, "/form-animal.jsp");
			break;
		}
		default:
			listAnimais(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/animais.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		switch (req.getServletPath()) {
		case "/animal/insert":
			insertAnimal(req);
			break;
		case "/animal/update":
			updateAnimal(req);
			break;
		case "/animal/delete":
			deleteAnimal(req);
			break;
		default:
			System.out.println("URL inválida " + req.getRequestURI());
		}

		ControllerUtil.redirect(resp, req.getContextPath() + "/animais");
	}

	private void listAnimais(HttpServletRequest req) {
		AnimalDAO dao = DAOFactory.createDAO(AnimalDAO.class);

		try {
			int total = dao.count();
			int page = ControllerUtil.currentPage(req, total);

			List<Animal> animais = dao.listAll(ControllerUtil.PAGE_SIZE, ControllerUtil.offset(page));

			req.setAttribute("animais", animais);
			ControllerUtil.setPagination(req, page, total, "/animais");
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private Animal loadAnimal(HttpServletRequest req) {
		int id = Integer.parseInt(req.getParameter("animalId"));

		AnimalDAO dao = DAOFactory.createDAO(AnimalDAO.class);

		try {
			return dao.findById(id);
		} catch (ModelException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** Monta o objeto Animal com os dados enviados pelo formulário. */
	private Animal readForm(HttpServletRequest req, Animal animal) {
		animal.setNome(req.getParameter("nome"));
		animal.setEspecie(req.getParameter("especie"));
		animal.setPorte(req.getParameter("porte"));
		animal.setDataResgate(ControllerUtil.formatDate(req.getParameter("dataResgate")));
		animal.setPeso(Double.parseDouble(req.getParameter("peso")));
		// checkbox desmarcado não é enviado pelo navegador
		animal.setCastrado(req.getParameter("castrado") != null);
		animal.setStatus(req.getParameter("status"));
		animal.setAbrigo(new Abrigo(Integer.parseInt(req.getParameter("abrigo"))));
		return animal;
	}

	private void insertAnimal(HttpServletRequest req) {
		Animal animal = readForm(req, new Animal());

		AnimalDAO dao = DAOFactory.createDAO(AnimalDAO.class);

		try {
			if (dao.save(animal))
				ControllerUtil.sucessMessage(req, "Animal '" + animal.getNome() + "' salvo com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Animal '" + animal.getNome() + "' não pode ser salvo.");
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updateAnimal(HttpServletRequest req) {
		int id = Integer.parseInt(req.getParameter("animalId"));
		Animal animal = readForm(req, new Animal(id));

		AnimalDAO dao = DAOFactory.createDAO(AnimalDAO.class);

		try {
			if (dao.update(animal))
				ControllerUtil.sucessMessage(req, "Animal '" + animal.getNome() + "' atualizado com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Animal '" + animal.getNome() + "' não pode ser atualizado.");
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deleteAnimal(HttpServletRequest req) {
		int id = Integer.parseInt(req.getParameter("id"));

		AnimalDAO dao = DAOFactory.createDAO(AnimalDAO.class);

		try {
			Animal animal = dao.findById(id);

			if (animal == null)
				throw new ModelException("Animal não encontrado para exclusão.");

			if (dao.delete(animal))
				ControllerUtil.sucessMessage(req, "Animal '" + animal.getNome() + "' excluído com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Animal '" + animal.getNome() + "' não pode ser excluído.");
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
}
