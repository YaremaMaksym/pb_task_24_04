package yaremax.com.pb_task_24_04.animal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
}