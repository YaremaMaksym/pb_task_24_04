package yaremax.com.pb_task_24_04.file.processors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import yaremax.com.pb_task_24_04.animal.Animal;
import yaremax.com.pb_task_24_04.animal.Category;
import yaremax.com.pb_task_24_04.categorization.AnimalCategoryAssigner;
import yaremax.com.pb_task_24_04.util.parser.FileParserService;
import yaremax.com.pb_task_24_04.validator.ValidatorService;
import yaremax.com.pb_task_24_04.animal.AnimalService;
import yaremax.com.pb_task_24_04.validator.strategies.ValidationStrategy;
import yaremax.com.pb_task_24_04.validator.strategies.animal.AnimalNameValidationStrategy;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimalFileProcessorTest {

    @Mock
    private AnimalCategoryAssigner animalCategoryAssigner;

    @Mock
    private FileParserService<Animal> fileParserService;

    @Mock
    private ValidatorService<Animal> validatorService;

    @Mock
    private AnimalService animalService;

    private AnimalFileProcessor animalFileProcessor;

    @BeforeEach
    void setUp() {
        animalFileProcessor = new AnimalFileProcessor(
                animalCategoryAssigner,
                fileParserService,
                validatorService,
                animalService
        );
    }

    @Test
    void process_ValidFile_ReturnsProcessedAnimals() {
        // Arrange
        MultipartFile multipartFile = new MockMultipartFile("test.csv", "test.csv", "text/csv", "data".getBytes());
        List<Animal> parsedAnimals = List.of(
                Animal.builder().name("Milo").type("cat").sex("male").weight(40).cost(51).build(),
                Animal.builder().name("Toby").type("dog").sex("female").weight(7).cost(14).build()
        );
        List<Animal> validatedAnimals = List.of(
                Animal.builder().name("Milo").type("cat").sex("male").weight(40).cost(51).category(Category.THIRD).build(),
                Animal.builder().name("Toby").type("dog").sex("female").weight(7).cost(14).category(Category.FIRST).build()
        );

        when(fileParserService.parseFile(multipartFile, Animal.class)).thenReturn(Optional.of(parsedAnimals));
        when(validatorService.validateList(eq(parsedAnimals), any())).thenReturn(Optional.of(validatedAnimals));
        when(animalService.addAnimal(any(Animal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Optional<List<Animal>> result = animalFileProcessor.process(multipartFile);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).containsExactlyElementsOf(validatedAnimals);

        verify(fileParserService).parseFile(multipartFile, Animal.class);
        verify(validatorService).validateList(eq(parsedAnimals), any());
        validatedAnimals.forEach(animal -> {
            verify(animalCategoryAssigner).assignCategory(animal);
            verify(animalService).addAnimal(animal);
        });
    }

    @Test
    void process_InvalidFile_ReturnsEmptyOptional() {
        // Arrange
        MultipartFile multipartFile = new MockMultipartFile("test.csv", "test.csv", "text/csv", "data".getBytes());

        when(fileParserService.parseFile(multipartFile, Animal.class)).thenReturn(Optional.empty());

        // Act
        Optional<List<Animal>> result = animalFileProcessor.process(multipartFile);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void process_NullFile_ThrowsException() {
        // Act & Assert
        assertThat(animalFileProcessor.process(null)).isEmpty();
    }
}