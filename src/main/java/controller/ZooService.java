package controller;

import db.DatabaseManager;
import model.Animal;
import model.Enclos;

import java.util.List;

public class ZooService {
    private DatabaseManager dbManager;

    public ZooService(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public Animal getAnimalById(int id) {
        return dbManager.getAnimal(id);
    }

    public List<Animal> getAllAnimals() {
        return dbManager.getAllAnimales();
    }

    public void addAnimal(Animal animal) {
        if (animal != null && animal.getNom() != null && !animal.getNom().isEmpty()) {
            dbManager.addAnimal(animal);
        } else {
            throw new IllegalArgumentException("O animal deve ter um nome válido.");
        }
    }

    public void updateAnimal(Animal animal) {
        if (animal != null && animal.getId() > 0) {
            dbManager.updateAnimal(animal);
        } else {
            throw new IllegalArgumentException("O animal deve ter um ID válido.");
        }
    }

    public void deleteAnimal(int id) {
        if (id > 0) {
            dbManager.deleteAnimal(id);
        } else {
            throw new IllegalArgumentException("ID inválido para exclusão de animal.");
        }
    }
    
    public List<Enclos> getAllEnclos() {
    	return dbManager.getAllEnclos();
    }
    
    
}
