package yaremax.com.pb_task_24_04.validator.strategies.animal;

import org.junit.jupiter.api.Test;
import yaremax.com.pb_task_24_04.animal.Animal;

import static org.junit.jupiter.api.Assertions.*;

class AnimalCostValidationStrategyTest {

    private final AnimalCostValidationStrategy strategy = new AnimalCostValidationStrategy();

    @Test
    void validate_PositiveCost_ReturnsTrue() {
        Animal animal = Animal.builder().cost(10).build();
        assertTrue(strategy.validate(animal));
    }

    @Test
    void validate_ZeroCost_ReturnsFalse() {
        Animal animal = Animal.builder().cost(0).build();
        assertFalse(strategy.validate(animal));
    }

    @Test
    void validate_NegativeCost_ReturnsFalse() {
        Animal animal = Animal.builder().cost(-5).build();
        assertFalse(strategy.validate(animal));
    }
}