package yaremax.com.pb_task_24_04.service.validator;

import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import yaremax.com.pb_task_24_04.entity.Animal;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class EntityValidatorTest {

    @Mock
    private Validator validator;

    private EntityValidator<Animal> entityValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        entityValidator = new EntityValidator<>();
    }

    @Test
    void isValid_WithValidAnimal_ShouldReturnTrue() {
        // Arrange
        Animal validAnimal = new Animal(1L, "Buddy", "Dog", "Male", 10, 100, null);
        when(validator.validate(validAnimal)).thenReturn(Collections.emptySet());

        // Act
        boolean isValid = entityValidator.isValid(validAnimal);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    void isValid_WithInvalidAnimal_ShouldReturnFalse() {
        // Arrange
        Animal invalidAnimal = new Animal(2L, "", "Cat", "Female", -5, 50, null);

        // Act
        boolean isValid = entityValidator.isValid(invalidAnimal);

        // Assert
        assertThat(isValid).isFalse();
    }
}