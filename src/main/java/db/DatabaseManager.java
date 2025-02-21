package db;

import model.Animal;
import model.Enclos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager implements IZooDataAccess {
    private static DatabaseManager instance;
    private Connection connection;
    private static final String DB_URL = "jdbc:sqlite:gestionZoo.db";

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            initializeTables();
            populateTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void initializeTables() {
        String createAnimalTable = """
            CREATE TABLE IF NOT EXISTS Animal (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom TEXT NOT NULL,
                espece TEXT NOT NULL,
                age INTEGER,
                regimeAlimentaire TEXT
            )""";
        
        String createEnclosTable = """
            CREATE TABLE IF NOT EXISTS Enclos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom TEXT NOT NULL,
                capacite INTEGER,
                typeHabitat TEXT
            )""";
        
        String createSoigneurTable = """
            CREATE TABLE IF NOT EXISTS Soigneur (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom TEXT NOT NULL,
                specialite TEXT
            )""";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createAnimalTable);
            stmt.execute(createEnclosTable);
            stmt.execute(createSoigneurTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    private void populateTables() {
        try (Statement stmt = connection.createStatement()) {
            //stmt.execute("INSERT INTO Animal (nom, espece, age, regimeAlimentaire) VALUES ('Simba', 'Lion', 5, 'Carnivore')");
            //stmt.execute("INSERT INTO Animal (nom, espece, age, regimeAlimentaire) VALUES ('Dumbo', 'Elephant', 10, 'Herbivore')");
            stmt.execute("INSERT INTO Enclos (nom, capacite, typeHabitat) VALUES ('Savane', 10, 'Prairie')");
            stmt.execute("INSERT INTO Enclos (nom, capacite, typeHabitat) VALUES ('Jungle', 8, 'Forest')");
            //stmt.execute("INSERT INTO Soigneur (nom, specialite) VALUES ('Jean Dupont', 'Mammals')");
            //stmt.execute("INSERT INTO Soigneur (nom, specialite) VALUES ('Marie Curie', 'Reptiles')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Animal getAnimal(int id) {
        String query = "SELECT * FROM Animal WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Animal(rs.getInt("id"), rs.getString("nom"), rs.getString("espece"), rs.getInt("age"), rs.getString("regimeAlimentaire"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Animal> getAllAnimales() {
        List<Animal> animals = new ArrayList<>();
        String query = "SELECT * FROM Animal";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                animals.add(new Animal(rs.getInt("id"), rs.getString("nom"), rs.getString("espece"), rs.getInt("age"), rs.getString("regimeAlimentaire")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }

    @Override
    public void addAnimal(Animal animal) {
        String query = "INSERT INTO Animal (nom, espece, age, regimeAlimentaire) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, animal.getNom());
            stmt.setString(2, animal.getEspece());
            stmt.setInt(3, animal.getAge());
            stmt.setString(4, animal.getRegimeAlimentaire());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAnimal(Animal animal) {
        String query = "UPDATE Animal SET nom = ?, espece = ?, age = ?, regimeAlimentaire = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, animal.getNom());
            stmt.setString(2, animal.getEspece());
            stmt.setInt(3, animal.getAge());
            stmt.setString(4, animal.getRegimeAlimentaire());
            stmt.setInt(5, animal.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAnimal(int id) {
        String query = "DELETE FROM Animal WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Enclos> getAllEnclos() {
        List<Enclos> enclosList = new ArrayList<>();
        String query = "SELECT * FROM enclos";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Enclos enclos = new Enclos(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getInt("capacite"),
                    rs.getString("typeHabitat")
                );
                enclosList.add(enclos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enclosList;
    }

}
