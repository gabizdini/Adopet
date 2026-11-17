package model.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import model.Abrigo;
import model.Company;
import model.ModelException;
import model.User;

public class MySQLAbrigoDAO implements AbrigoDAO {

	// Consulta base: traz o abrigo junto com a associação e o responsável dela
	private static final String SELECT_WITH_COMPANY =
			  " SELECT a.*, "
			+ "        c.name AS company_name, "
			+ "        u.id AS user_id, u.nome AS user_name "
			+ " FROM abrigos a "
			+ " INNER JOIN companies c ON a.company_id = c.id "
			+ " INNER JOIN users u ON c.user_id = u.id "
			+ " ORDER BY a.nome ";

	@Override
	public boolean save(Abrigo abrigo) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlInsert = "INSERT INTO abrigos "
				+ " (nome, telefone, data_fundacao, capacidade, aceita_voluntarios, descricao, company_id) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?);";

		db.prepareStatement(sqlInsert);
		fillStatement(db, abrigo);

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Abrigo abrigo) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlUpdate = "UPDATE abrigos "
				+ " SET nome = ?, "
				+ " telefone = ?, "
				+ " data_fundacao = ?, "
				+ " capacidade = ?, "
				+ " aceita_voluntarios = ?, "
				+ " descricao = ?, "
				+ " company_id = ? "
				+ " WHERE id = ?;";

		db.prepareStatement(sqlUpdate);
		fillStatement(db, abrigo);
		db.setInt(8, abrigo.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Abrigo abrigo) throws ModelException {
		DBHandler db = new DBHandler();

		db.prepareStatement("DELETE FROM abrigos WHERE id = ?;");
		db.setInt(1, abrigo.getId());

		try {
			return db.executeUpdate() > 0;
		} catch (ModelException e) {
			// Há animais vinculados a este abrigo
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException)
				return false;

			throw e;
		}
	}

	@Override
	public List<Abrigo> listAll() throws ModelException {
		DBHandler db = new DBHandler();

		db.createStatement();
		db.executeQuery(SELECT_WITH_COMPANY);

		List<Abrigo> abrigos = new ArrayList<Abrigo>();
		while (db.next())
			abrigos.add(createAbrigoWithCompany(db));

		return abrigos;
	}

	@Override
	public List<Abrigo> listAll(int limit, int offset) throws ModelException {
		DBHandler db = new DBHandler();

		db.prepareStatement(SELECT_WITH_COMPANY + " LIMIT ? OFFSET ?");
		db.setInt(1, limit);
		db.setInt(2, offset);
		db.executeQuery();

		List<Abrigo> abrigos = new ArrayList<Abrigo>();
		while (db.next())
			abrigos.add(createAbrigoWithCompany(db));

		return abrigos;
	}

	@Override
	public int count() throws ModelException {
		DBHandler db = new DBHandler();

		db.createStatement();
		db.executeQuery("SELECT COUNT(*) AS total FROM abrigos");

		return db.next() ? db.getInt("total") : 0;
	}

	@Override
	public Abrigo findById(int id) throws ModelException {
		DBHandler db = new DBHandler();

		db.prepareStatement("SELECT * FROM abrigos WHERE id = ?;");
		db.setInt(1, id);
		db.executeQuery();

		if (!db.next())
			return null;

		Abrigo abrigo = createAbrigo(db);
		abrigo.setCompany(new Company(db.getInt("company_id")));

		return abrigo;
	}

	private void fillStatement(DBHandler db, Abrigo abrigo) throws ModelException {
		db.setString(1, abrigo.getNome());
		db.setString(2, abrigo.getTelefone());

		if (abrigo.getDataFundacao() == null)
			db.setNullDate(3);
		else
			db.setDate(3, abrigo.getDataFundacao());

		db.setInt(4, abrigo.getCapacidade());
		db.setBoolean(5, abrigo.isAceitaVoluntarios());
		db.setString(6, abrigo.getDescricao());
		db.setInt(7, abrigo.getCompany().getId());
	}

	private Abrigo createAbrigo(DBHandler db) throws ModelException {
		Abrigo abrigo = new Abrigo(db.getInt("id"));
		abrigo.setNome(db.getString("nome"));
		abrigo.setTelefone(db.getString("telefone"));
		abrigo.setDataFundacao(db.getDate("data_fundacao"));
		abrigo.setCapacidade(db.getInt("capacidade"));
		abrigo.setAceitaVoluntarios(db.getBoolean("aceita_voluntarios"));
		abrigo.setDescricao(db.getString("descricao"));
		return abrigo;
	}

	private Abrigo createAbrigoWithCompany(DBHandler db) throws ModelException {
		Abrigo abrigo = createAbrigo(db);

		User user = new User(db.getInt("user_id"));
		user.setName(db.getString("user_name"));

		Company company = new Company(db.getInt("company_id"));
		company.setName(db.getString("company_name"));
		company.setUser(user);

		abrigo.setCompany(company);
		return abrigo;
	}
}
