package yaremax.com.pb_task_24_04.util.parser.strategies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yaremax.com.pb_task_24_04.animal.Animal;
import yaremax.com.pb_task_24_04.exceptions.FileParsingException;
import yaremax.com.pb_task_24_04.util.parser.strategies.CsvFileParserStrategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CsvFileParserStrategyTest {

    private CsvFileParserStrategy<Animal> csvFileParser;

    @BeforeEach
    void setUp() {
        csvFileParser = new CsvFileParserStrategy<>();
    }

    private File createTempCsvFile(String csvContent) throws IOException {
        File file = File.createTempFile("temp", ".csv");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(csvContent);
        }
        return file;
    }

    @Test
    void getSupportedExtension() {
        assertThat("csv").isEqualTo(csvFileParser.getSupportedExtension());
    }

    @Test
    void parse_ValidCsvFile_ReturnsData() throws IOException {
        // Arrange
        List<Animal> list = List.of(
                Animal.builder().name("Milo").type("cat").sex("male").weight(40).cost(51).build(),
                Animal.builder().name("Toby").type("dog").sex("female").weight(7).cost(14).build()
        );
        String csvContent = """
                name,type,sex,weight,cost
                Milo,cat,male,40,51
                Toby,dog,female,7,14
                """;
        File csvFile = createTempCsvFile(csvContent);

        // Act
        Optional<List<Animal>> optionalDataList = csvFileParser.parse(Optional.of(csvFile), Animal.class);

        // Assert
        assertThat(optionalDataList).isPresent();
        assertThat(optionalDataList.get()).asList().containsExactlyInAnyOrderElementsOf(list);
    }


    @Test
    void parse_InvalidCsvFile_ReturnsEmptyList() throws IOException {
        // Arrange
        String invalidCsvContent = "invalid csv content";
        File csvFile = createTempCsvFile(invalidCsvContent);

        // Act
        Optional<List<Animal>> optionalData = csvFileParser.parse(Optional.of(csvFile), Animal.class);

        // Assert
        assertThat(optionalData).isPresent();
        assertThat(optionalData.get().isEmpty()).isTrue();
    }

    @Test
    void parse_NullFile_ThrowsException() {
        // Act & Assert
        assertThatThrownBy(() -> csvFileParser.parse(null, Animal.class))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void parse_OptionalEmptyInsteadOfFile_ReturnsEmptyOptional() {
        // Act & Assert
        assertThat(csvFileParser.parse(Optional.empty(), Animal.class))
                .isEmpty();
    }

    @Test
    void parse_EmptyFile_ThrowsException() {
        // Act & Assert
        assertThatThrownBy(() -> csvFileParser.parse(Optional.of(new File("empty.csv")), Animal.class))
                .isInstanceOf(FileParsingException.class);
    }

    @Test
    void parse_NullClass_ThrowsException() throws IOException {
        // Arrange
        String csvContent = """
                name,type,sex,weight,cost
                Milo,cat,male,40,51
                Toby,dog,female,7,14
                """;
        File csvFile = createTempCsvFile(csvContent);

        // Act & Assert
        assertThatThrownBy(() -> csvFileParser.parse(Optional.of(csvFile), null))
                .isInstanceOf(IllegalStateException.class);
    }
}
