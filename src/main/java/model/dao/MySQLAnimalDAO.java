package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.Abrigo;
import model.Animal;
import model.ModelException;

public class MySQLAnimalDAO implements AnimalDAO {

	// Consulta base: traz o animal junto com o nome do abrigo
	private static final String SELECT_WITH_ABRIGO =
			  " SELECT an.*, ab.nome AS abrigo_nome "
			+ " FROM animais an "
			+ " INNER JOIN abrigos ab ON an.abrigo_id = ab.id "
			+ " ORDER BY an.nome ";

	@Override
	public boolean save(Animal animal) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlInsert = "INSERT INTO animais "
				+ " (nome, especie, porte, data_resgate, peso, castrado, status, abrigo_id) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

		db.prepareStatement(sqlInsert);
		fillStatement(db, animal);

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Animal animal) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlUpdate = "UPDATE animais "
				+ " SET nome = ?, "
				+ " especie = ?, "
				+ " porte = ?, "
				+ " data_resgate = ?, "
				+ " peso = ?, "
				+ " castrado = ?, "
				+ " status = ?, "
				+ " abrigo_id = ? "
				+ " WHERE id = ?;";

		db.prepareStatement(sqlUpdate);
		fillStatement(db, animal);
		db.setInt(9, animal.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Animal animal) throws ModelException {
		DBHandler db = new DBHandler();

		db.prepareStatement("DELETE FROM animais WHERE id = ?;");
		db.setInt(1, animal.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public List<Animal> listAll() throws ModelException {
		DBHandler db = new DBHandler();

		db.createStatement();
		db.executeQuery(SELECT_WITH_ABRIGO);

		List<Animal> animais = new ArrayList<Animal>();
		while (db.next())
			animais.add(createAnimalWithAbrigo(db));

		return animais;
	}

	@Override
	public List<Animal> listAll(int limit, int offset) throws ModelException {
		DBHandler db = new DBHandler();

		db.prepareStatement(SELECT_WITH_ABRIGO + " LIMIT ? OFFSET ?");
		db.setInt(1, limit);
		db.setInt(2, offset);
		db.executeQuery();

		List<Animal> animais = new ArrayList<Animal>();
		while (db.next())
			animais.add(createAnimalWithAbrigo(db));

		return animais;
	}

	@Override
	public int count() throws ModelException {
		DBHandler db = new DBHandler();

		db.createStatement();
		db.executeQuery("SELECT COUNT(*) AS total FROM animais");

		return db.next() ? db.getInt("total") : 0;
	}

	@Override
	public Animal findById(int id) throws ModelException {
		DBHandler db = new DBHandler();

		db.prepareStatement("SELECT * FROM animais WHERE id = ?;");
		db.setInt(1, id);
		db.executeQuery();

		if (!db.next())
			return null;

		Animal animal = createAnimal(db);
		animal.setAbrigo(new Abrigo(db.getInt("abrigo_id")));

		return animal;
	}

	private void fillStatement(DBHandler db, Animal animal) throws ModelException {
		db.setString(1, animal.getNome());
		db.setString(2, animal.getEspecie());
		db.setString(3, animal.getPorte());
		db.setDate(4, animal.getDataResgate());
		db.setDouble(5, animal.getPeso());
		db.setBoolean(6, animal.isCastrado());
		db.setString(7, animal.getStatus());
		db.setInt(8, animal.getAbrigo().getId());
	}

	private Animal createAnimal(DBHandler db) throws ModelException {
		Animal animal = new Animal(db.getInt("id"));
		animal.setNome(db.getString("nome"));
		animal.setEspecie(db.getString("especie"));
		animal.setPorte(db.getString("porte"));
		animal.setDataResgate(db.getDate("data_resgate"));
		animal.setPeso(db.getDouble("peso"));
		animal.setCastrado(db.getBoolean("castrado"));
		animal.setStatus(db.getString("status"));
		return animal;
	}

	private Animal createAnimalWithAbrigo(DBHandler db) throws ModelException {
		Animal animal = createAnimal(db);

		Abrigo abrigo = new Abrigo(db.getInt("abrigo_id"));
		abrigo.setNome(db.getString("abrigo_nome"));
		animal.setAbrigo(abrigo);

		return animal;
	}
}
