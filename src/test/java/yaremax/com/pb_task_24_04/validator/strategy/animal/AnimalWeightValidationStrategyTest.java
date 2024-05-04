package yaremax.com.pb_task_24_04.validator.strategy.animal;

import org.junit.jupiter.api.Test;
import yaremax.com.pb_task_24_04.entity.Animal;
import yaremax.com.pb_task_24_04.service.validator.strategy.animal.AnimalWeightValidationStrategy;

import static org.junit.jupiter.api.Assertions.*;

class AnimalWeightValidationStrategyTest {

    private final AnimalWeightValidationStrategy strategy = new AnimalWeightValidationStrategy();

    @Test
    void validate_PositiveWeight_ReturnsTrue() {
        Animal animal = Animal.builder().weight(5).build();
        assertTrue(strategy.validate(animal));
    }

    @Test
    void validate_ZeroWeight_ReturnsFalse() {
        Animal animal = Animal.builder().weight(0).build();
        assertFalse(strategy.validate(animal));
    }

    @Test
    void validate_NegativeWeight_ReturnsFalse() {
        Animal animal = Animal.builder().weight(-3).build();
        assertFalse(strategy.validate(animal));
    }
}