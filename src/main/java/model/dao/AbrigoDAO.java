package model.dao;

import java.util.List;

import model.Abrigo;
import model.ModelException;

public interface AbrigoDAO {
	boolean save(Abrigo abrigo) throws ModelException;
	boolean update(Abrigo abrigo) throws ModelException;
	boolean delete(Abrigo abrigo) throws ModelException;
	List<Abrigo> listAll() throws ModelException;
	List<Abrigo> listAll(int limit, int offset) throws ModelException;
	int count() throws ModelException;
	Abrigo findById(int id) throws ModelException;
}
