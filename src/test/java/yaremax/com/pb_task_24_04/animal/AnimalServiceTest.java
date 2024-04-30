package yaremax.com.pb_task_24_04.animal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

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
    void getAnimals_NoFilters_ShouldReturnAllAnimals() {
        // Arrange
        List<Animal> animals = Arrays.asList(
                Animal.builder().name("Buddy").type("Dog").sex("Male").weight(10).cost(100).category(Category.FOURTH).build(),
                Animal.builder().name("Kitty").type("Cat").sex("Female").weight(5).cost(50).category(Category.SECOND).build()
        );
        when(animalRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(animals);

        // Act
        List<Animal> result = animalService.getAnimals(null, null, null, Sort.unsorted());

        // Assert
        assertThat(result).isEqualTo(animals);
        verify(animalRepository, times(1)).findAll(any(Specification.class), any(Sort.class));
    }

    @Test
    void getAnimals_WithFilters_ShouldReturnFilteredAnimals() {
        // Arrange
        Animal animal1 = Animal.builder().name("Buddy").type("Dog").sex("Male").weight(10).cost(100).category(Category.FOURTH).build();
        Animal animal2 = Animal.builder().name("Kitty").type("Cat").sex("Female").weight(5).cost(50).category(Category.SECOND).build();
        List<Animal> allAnimals = Arrays.asList(animal1, animal2);
        List<Animal> expectedAnimals = Arrays.asList(animal1);

        when(animalRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(expectedAnimals);

        // Act
        List<Animal> result = animalService.getAnimals("Dog", Category.FOURTH, "Male", Sort.by("weight"));

        // Assert
        assertThat(result).isEqualTo(expectedAnimals);
        verify(animalRepository, times(1)).findAll(any(Specification.class), any(Sort.class));
    }
}