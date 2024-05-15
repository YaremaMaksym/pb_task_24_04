package yaremax.com.pb_task_24_04.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import yaremax.com.pb_task_24_04.entity.Animal;
import yaremax.com.pb_task_24_04.entity.Category;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AnimalRepositoryTest {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findById_ExistingId_ReturnsAnimal() {
        // Arrange
        Animal animal = createTestAnimal();
        entityManager.persist(animal);

        // Act
        Optional<Animal> foundAnimal = animalRepository.findById(animal.getId());

        // Assert
        assertThat(foundAnimal).isPresent();
        assertThat(foundAnimal.get()).isEqualTo(animal);
    }

    @Test
    void findById_NonExistingId_ReturnsEmpty() {
        // Act
        Optional<Animal> foundAnimal = animalRepository.findById(10L);

        // Assert
        assertThat(foundAnimal).isNotPresent();
    }

    @Test
    void findAll_NoAnimals_ReturnsEmptyList() {
        // Act
        List<Animal> animals = animalRepository.findAll();

        // Assert
        assertThat(animals).isEmpty();
    }

    @Test
    void save_NewAnimal_ReturnsAnimal() {
        // Arrange
        Animal animal = createTestAnimal();

        // Act
        Animal savedAnimal = animalRepository.save(animal);

        // Assert
        assertThat(savedAnimal).isNotNull();
        assertThat(savedAnimal.getId()).isNotNull();
    }

    private Animal createTestAnimal() {
        return Animal.builder()
                .name("Buddy")
                .type("Dog")
                .sex("Male")
                .weight(10)
                .cost(100)
                .category(Category.FOURTH)
                .build();
    }
}