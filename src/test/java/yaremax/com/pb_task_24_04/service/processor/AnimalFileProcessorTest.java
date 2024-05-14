package yaremax.com.pb_task_24_04.service.processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import yaremax.com.pb_task_24_04.entity.Animal;
import yaremax.com.pb_task_24_04.service.categorization.AnimalCategoryAssigner;
import yaremax.com.pb_task_24_04.service.parser.FileParserService;
import yaremax.com.pb_task_24_04.service.AnimalService;
import yaremax.com.pb_task_24_04.service.validator.EntityValidator;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalFileProcessorTest {

    @Mock
    private AnimalCategoryAssigner animalCategoryAssigner;

    @Mock
    private FileParserService<Animal> fileParserService;

    @Mock
    private AnimalService animalService;
    @Mock
    private EntityValidator<Animal> animalEntityValidator;

    private AnimalFileProcessor animalFileProcessor;

    @BeforeEach
    void setUp() {
        animalFileProcessor = new AnimalFileProcessor(
                animalCategoryAssigner,
                fileParserService,
                animalService,
                animalEntityValidator
        );
    }

    @Test
    void process_WithValidFile_ShouldReturnProcessedAnimals() {
        // Arrange
        MultipartFile multipartFile = new MockMultipartFile("test.csv", "test.csv", "text/csv", "data".getBytes());
        List<Animal> parsedAnimals = List.of(
                Animal.builder().name("Milo").type("cat").sex("male").weight(40).cost(51).build(),
                Animal.builder().name("Toby").type("dog").sex("female").weight(7).cost(14).build()
        );
        List<Animal> processedAnimals = List.of(
                Animal.builder().name("Milo").type("cat").sex("male").weight(40).cost(51).build(),
                Animal.builder().name("Toby").type("dog").sex("female").weight(7).cost(14).build()
        );

        when(fileParserService.parseFile(multipartFile, Animal.class)).thenReturn(Optional.of(parsedAnimals));
        when(animalService.addAnimal(any(Animal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Optional<List<Animal>> result = animalFileProcessor.process(multipartFile);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).containsExactlyElementsOf(processedAnimals);
    }

    @Test
    void process_WithInvalidFile_ShouldReturnEmptyOptional() {
        // Arrange
        MultipartFile multipartFile = new MockMultipartFile("test.csv", "test.csv", "text/csv", "data".getBytes());

        when(fileParserService.parseFile(multipartFile, Animal.class)).thenReturn(Optional.empty());

        // Act
        Optional<List<Animal>> result = animalFileProcessor.process(multipartFile);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void process_WithNullFile_ShouldReturnEmptyOptional() {
        // Act
        Optional<List<Animal>> result = animalFileProcessor.process(null);

        // Assert
        assertThat(result).isEmpty();
    }
}