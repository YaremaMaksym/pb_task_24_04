package yaremax.com.pb_task_24_04.validator;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import yaremax.com.pb_task_24_04.entity.Animal;
import yaremax.com.pb_task_24_04.service.validator.ValidatorService;
import yaremax.com.pb_task_24_04.service.validator.strategy.ValidationStrategy;
import yaremax.com.pb_task_24_04.service.validator.strategy.animal.AnimalCostValidationStrategy;
import yaremax.com.pb_task_24_04.service.validator.strategy.animal.AnimalNameValidationStrategy;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


class ValidatorServiceTest {
    private final ValidatorService<Animal> validatorService = new ValidatorService<>();

    @Test
    void validateList_ValidObjects_ReturnsValidObjects() {
        Animal validAnimal = Animal.builder().name("Buddy").cost(10).build();
        Animal invalidAnimal = Animal.builder().name("").cost(-5).build();
        List<Animal> animals = List.of(validAnimal, invalidAnimal);

        AnimalNameValidationStrategy nameStrategy = Mockito.mock(AnimalNameValidationStrategy.class);
        when(nameStrategy.validate(validAnimal)).thenReturn(true);
        when(nameStrategy.validate(invalidAnimal)).thenReturn(false);

        AnimalCostValidationStrategy costStrategy = Mockito.mock(AnimalCostValidationStrategy.class);
        when(costStrategy.validate(validAnimal)).thenReturn(true);
        when(costStrategy.validate(invalidAnimal)).thenReturn(false);

        List<ValidationStrategy<Animal>> strategies = List.of(nameStrategy, costStrategy);

        Optional<List<Animal>> validAnimals = validatorService.validateList(animals, strategies);
        assertThat(validAnimals).isPresent();
        assertThat(validAnimals.get()).hasSize(1).contains(validAnimal);
    }

    @Test
    void validateList_NoStrategies_ReturnsEmpty() {
        List<Animal> animals = List.of(Animal.builder().build());
        List<ValidationStrategy<Animal>> strategies = List.of();

        Optional<List<Animal>> validAnimals = validatorService.validateList(animals, strategies);
        assertThat(validAnimals).isEmpty();
    }

    @Test
    void validateList_NullListOfAnimals_ThrowsException() {
        List<Animal> animals = null;
        List<ValidationStrategy<Animal>> strategies = List.of(new AnimalNameValidationStrategy());

        assertThatThrownBy(() -> validatorService.validateList(animals, strategies))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void validateList_NullListOfStrategies_ThrowsException() {
        List<Animal> animals = List.of(Animal.builder().build());
        List<ValidationStrategy<Animal>> strategies = null;

        assertThatThrownBy(() -> validatorService.validateList(animals, strategies))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void validateList_EmptyListOfAnimals_ReturnsEmpty() {
        List<Animal> animals = List.of();
        List<ValidationStrategy<Animal>> strategies = List.of();

        Optional<List<Animal>> validAnimals = validatorService.validateList(animals, strategies);
        assertThat(validAnimals).isEmpty();
    }
}