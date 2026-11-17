package model.dao;

import java.util.List;

import model.Animal;
import model.ModelException;

public interface AnimalDAO {
	boolean save(Animal animal) throws ModelException;
	boolean update(Animal animal) throws ModelException;
	boolean delete(Animal animal) throws ModelException;
	List<Animal> listAll() throws ModelException;
	List<Animal> listAll(int limit, int offset) throws ModelException;
	int count() throws ModelException;
	Animal findById(int id) throws ModelException;
}
