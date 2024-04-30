package yaremax.com.pb_task_24_04.validator.strategies.animal;

import org.junit.jupiter.api.Test;
import yaremax.com.pb_task_24_04.animal.Animal;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTypeValidationStrategyTest {
    private final AnimalTypeValidationStrategy strategy = new AnimalTypeValidationStrategy();

    @Test
    void validate_ValidType_ReturnsTrue() {
        Animal animal = Animal.builder().type("dog").build();
        assertTrue(strategy.validate(animal));
    }

    @Test
    void validate_EmptyType_ReturnsFalse() {
        Animal animal = Animal.builder().type("").build();
        assertFalse(strategy.validate(animal));
    }

    @Test
    void validate_NullType_ReturnsFalse() {
        Animal animal = Animal.builder().type(null).build();
        assertFalse(strategy.validate(animal));
    }
}