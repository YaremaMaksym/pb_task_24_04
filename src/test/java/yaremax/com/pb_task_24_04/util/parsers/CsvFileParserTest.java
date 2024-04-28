package yaremax.com.pb_task_24_04.util.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yaremax.com.pb_task_24_04.animal.AnimalDto;
import yaremax.com.pb_task_24_04.util.parsers.CsvFileParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CsvFileParserTest {

    private CsvFileParser<AnimalDto> csvFileParser;

    @BeforeEach
    void setUp() {
        csvFileParser = new CsvFileParser<>();
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
        List<AnimalDto> dtoList = List.of(
                AnimalDto.builder().name("Milo").type("cat").sex("male").weight(40).cost(51).build(),
                AnimalDto.builder().name("Toby").type("dog").sex("female").weight(7).cost(14).build()
        );
        String csvContent = """
                name,type,sex,weight,cost
                Milo,cat,male,40,51
                Toby,dog,female,7,14
                """;
        File csvFile = createTempCsvFile(csvContent);

        // Act
        Optional<List<AnimalDto>> optionalDataList = csvFileParser.parse(csvFile, AnimalDto.class);

        // Assert
        assertThat(optionalDataList).isPresent();
        assertThat(optionalDataList.get().size()).isEqualTo(2);
        assertThat(optionalDataList.get()).isEqualTo(dtoList);
    }

    @Test
    void parse_InvalidCsvFile_ReturnsEmptyList() throws IOException {
        // Arrange
        String invalidCsvContent = "invalid csv content";
        File csvFile = createTempCsvFile(invalidCsvContent);

        // Act
        Optional<List<AnimalDto>> optionalData = csvFileParser.parse(csvFile, AnimalDto.class);

        // Assert
        assertThat(optionalData.get().isEmpty()).isTrue();
    }
}