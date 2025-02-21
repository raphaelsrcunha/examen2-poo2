package rsc;

import db.DatabaseManager;
import model.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import controller.ZooService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ZooServiceTest {
    private ZooService zooService;
    private DatabaseManager mockDbManager;

    @BeforeEach
    void setUp() {
        mockDbManager = Mockito.mock(DatabaseManager.class);
        zooService = new ZooService(mockDbManager);
    }

    @Test
    void testGetAnimalById() {
        Animal animal = new Animal(1, "Simba", "Lion", 5, "Carnivore");
        when(mockDbManager.getAnimal(1)).thenReturn(animal);

        Animal result = zooService.getAnimalById(1);

        assertNotNull(result);
        assertEquals("Simba", result.getNom());
        assertEquals("Lion", result.getEspece());
        verify(mockDbManager, times(1)).getAnimal(1);
    }

    @Test
    void testGetAllAnimals() {
        List<Animal> animals = Arrays.asList(
                new Animal(1, "Simba", "Lion", 5, "Carnivore"),
                new Animal(2, "Dumbo", "Elephant", 10, "Herbivore")
        );
        when(mockDbManager.getAllAnimales()).thenReturn(animals);

        List<Animal> result = zooService.getAllAnimals();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(mockDbManager, times(1)).getAllAnimales();
    }

    @Test
    void testAddAnimal() {
        Animal animal = new Animal(3, "Zazu", "Bird", 2, "Omnivore");

        zooService.addAnimal(animal);

        verify(mockDbManager, times(1)).addAnimal(animal);
    }

    @Test
    void testAddAnimalWithInvalidName() {
        Animal invalidAnimal = new Animal(4, "", "Fish", 1, "Herbivore");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> zooService.addAnimal(invalidAnimal));
        assertEquals("O animal deve ter um nome válido.", exception.getMessage());

        verify(mockDbManager, never()).addAnimal(any());
    }

    @Test
    void testUpdateAnimal() {
        Animal animal = new Animal(1, "Nala", "Lion", 4, "Carnivore");

        zooService.updateAnimal(animal);

        verify(mockDbManager, times(1)).updateAnimal(animal);
    }

    @Test
    void testDeleteAnimal() {
        int validId = 1;

        zooService.deleteAnimal(validId);

        verify(mockDbManager, times(1)).deleteAnimal(validId);
    }

    @Test
    void testDeleteAnimalWithInvalidId() {
        int invalidId = -1;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> zooService.deleteAnimal(invalidId));
        assertEquals("ID inválido para exclusão de animal.", exception.getMessage());

        verify(mockDbManager, never()).deleteAnimal(anyInt());
    }
}
