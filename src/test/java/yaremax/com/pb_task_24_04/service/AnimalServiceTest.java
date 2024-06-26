package yaremax.com.pb_task_24_04.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import yaremax.com.pb_task_24_04.entity.Animal;
import yaremax.com.pb_task_24_04.entity.Category;
import yaremax.com.pb_task_24_04.repository.AnimalRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AnimalServiceTest {
    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalService animalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addAnimal_ValidAnimal_ShouldSaveAnimal() {
        // Arrange
        Animal animal = Animal.builder()
                .name("Buddy")
                .type("Dog")
                .sex("Male")
                .weight(10)
                .cost(100)
                .category(Category.FOURTH)
                .build();
        when(animalRepository.save(animal)).thenReturn(animal);

        // Act
        Animal savedAnimal = animalService.addAnimal(animal);

        // Assert
        assertThat(savedAnimal).isEqualTo(animal);
        verify(animalRepository, times(1)).save(animal);
    }

    @Test
    void getAnimals_NoExampleNoSort_ShouldReturnAllAnimals() {
        // Arrange
        List<Animal> animals = Arrays.asList(
                Animal.builder().name("Buddy").type("Dog").sex("Male").weight(10).cost(100).category(Category.FOURTH).build(),
                Animal.builder().name("Kitty").type("Cat").sex("Female").weight(5).cost(50).category(Category.SECOND).build()
        );
        when(animalRepository.findAll()).thenReturn(animals);

        // Act
        List<Animal> result = animalService.getAnimals(null, Sort.unsorted());

        // Assert
        assertThat(result).isEqualTo(animals);
        verify(animalRepository, times(1)).findAll();
    }

    @Test
    void getAnimals_NoExampleWithSort_ShouldReturnAllAnimalsWithSort() {
        // Arrange
        List<Animal> animals = Arrays.asList(
                Animal.builder().name("Buddy").type("Dog").sex("Male").weight(10).cost(100).category(Category.FOURTH).build(),
                Animal.builder().name("Kitty").type("Cat").sex("Female").weight(5).cost(50).category(Category.SECOND).build()
        );
        Sort sort = Sort.by("weight");
        when(animalRepository.findAll(sort)).thenReturn(animals);

        // Act
        List<Animal> result = animalService.getAnimals(null, sort);

        // Assert
        assertThat(result).isEqualTo(animals);
        verify(animalRepository, times(1)).findAll(sort);
    }

    @Test
    void getAnimals_WithExampleAndSort_ShouldReturnFilteredAnimalsWithSort() {
        // Arrange
        Animal animal1 = Animal.builder().name("Buddy").type("Dog").sex("Male").weight(10).cost(100).category(Category.FOURTH).build();
        Animal animal2 = Animal.builder().name("Kitty").type("Cat").sex("Female").weight(5).cost(50).category(Category.SECOND).build();
        List<Animal> allAnimals = Arrays.asList(animal1, animal2);
        List<Animal> expectedAnimals = Arrays.asList(animal1);
        Animal animalExample = Animal.builder().type("Dog").build();
        Sort sort = Sort.by("weight");

        when(animalRepository.findAll(Example.of(animalExample), sort)).thenReturn(expectedAnimals);

        // Act
        List<Animal> result = animalService.getAnimals(animalExample, sort);

        // Assert
        assertThat(result).isEqualTo(expectedAnimals);
        verify(animalRepository, times(1)).findAll(Example.of(animalExample), sort);
    }
}