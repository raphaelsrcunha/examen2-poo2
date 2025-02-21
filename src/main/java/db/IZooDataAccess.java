package db;

import java.util.List;

import model.Animal;

public interface IZooDataAccess {
	
	//CRUD pour les animales
	Animal getAnimal(int id);
	List<Animal> getAllAnimales();
	void addAnimal(Animal animal);
	void updateAnimal(Animal animal);
	void deleteAnimal(int id);

}
