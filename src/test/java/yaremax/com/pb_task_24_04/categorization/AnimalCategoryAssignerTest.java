package yaremax.com.pb_task_24_04.categorization;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import yaremax.com.pb_task_24_04.animal.Animal;
import yaremax.com.pb_task_24_04.animal.Category;
import yaremax.com.pb_task_24_04.exceptions.InvalidDataException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AnimalCategoryAssignerTest {

    private final AnimalCategoryAssigner categoryAssigner = new AnimalCategoryAssigner();

    @ParameterizedTest
    @CsvSource({
            "0, FIRST",
            "10, FIRST",
            "20, FIRST",
            "21, SECOND",
            "30, SECOND",
            "40, SECOND",
            "41, THIRD",
            "50, THIRD",
            "60, THIRD",
            "61, FOURTH",
            "100, FOURTH"
    })
    void assignCategory_CorrectCategoryAssigned(int cost, Category expectedCategory) {
        // Arrange
        Animal animal = Animal.builder().cost(cost).build();

        // Act
        Category assignedCategory = categoryAssigner.assignCategory(animal);

        // Assert
        assertThat(assignedCategory).isEqualTo(expectedCategory);
        assertThat(animal.getCategory()).isEqualTo(expectedCategory);
    }

    @Test
    void assignCategory_NegativeCost_ThrowsException() {
        // Arrange
        Animal animal = Animal.builder().cost(-10).build();

        // Act & Assert
        assertThatThrownBy(() -> categoryAssigner.assignCategory(animal))
                .isInstanceOf(InvalidDataException.class);
    }

    @Test
    void assignCategory_NullAnimal_ThrowsException() {
        // Arrange
        Animal animal = null;

        // Act & Assert
        assertThatThrownBy(() -> categoryAssigner.assignCategory(animal))
                .isInstanceOf(NullPointerException.class);
    }
}