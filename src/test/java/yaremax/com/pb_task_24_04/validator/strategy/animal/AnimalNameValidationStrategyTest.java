package yaremax.com.pb_task_24_04.validator.strategy.animal;

import org.junit.jupiter.api.Test;
import yaremax.com.pb_task_24_04.entity.Animal;
import yaremax.com.pb_task_24_04.service.validator.strategy.animal.AnimalNameValidationStrategy;

import static org.junit.jupiter.api.Assertions.*;

class AnimalNameValidationStrategyTest {

    private final AnimalNameValidationStrategy strategy = new AnimalNameValidationStrategy();

    @Test
    void validate_ValidName_ReturnsTrue() {
        Animal animal = Animal.builder().name("Buddy").build();
        assertTrue(strategy.validate(animal));
    }

    @Test
    void validate_EmptyName_ReturnsFalse() {
        Animal animal = Animal.builder().name("").build();
        assertFalse(strategy.validate(animal));
    }

    @Test
    void validate_NullName_ReturnsFalse() {
        Animal animal = Animal.builder().name(null).build();
        assertFalse(strategy.validate(animal));
    }
}