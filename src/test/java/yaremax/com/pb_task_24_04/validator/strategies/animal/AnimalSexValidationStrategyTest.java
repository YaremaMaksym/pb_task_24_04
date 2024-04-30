package yaremax.com.pb_task_24_04.validator.strategies.animal;

import org.junit.jupiter.api.Test;
import yaremax.com.pb_task_24_04.animal.Animal;

import static org.junit.jupiter.api.Assertions.*;

class AnimalSexValidationStrategyTest {private final AnimalSexValidationStrategy strategy = new AnimalSexValidationStrategy();

    @Test
    void validate_ValidSex_ReturnsTrue() {
        Animal animal = Animal.builder().sex("male").build();
        assertTrue(strategy.validate(animal));
    }

    @Test
    void validate_EmptySex_ReturnsFalse() {
        Animal animal = Animal.builder().sex("").build();
        assertFalse(strategy.validate(animal));
    }

    @Test
    void validate_NullSex_ReturnsFalse() {
        Animal animal = Animal.builder().sex(null).build();
        assertFalse(strategy.validate(animal));
    }
}