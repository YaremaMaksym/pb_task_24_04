package yaremax.com.pb_task_24_04.util.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import yaremax.com.pb_task_24_04.exceptions.FileParsingException;
import yaremax.com.pb_task_24_04.markers.Processable;
import yaremax.com.pb_task_24_04.util.parser.strategies.FileParserStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileParserFactoryTest {

    @Mock
    private FileParserStrategy<Processable> csvStrategy;

    @Mock
    private FileParserStrategy<Processable> xmlStrategy;

    @Test
    void getParser_ExistingExtension_ReturnsStrategy() {
        // Arrange
        when(csvStrategy.getSupportedExtension()).thenReturn("csv");
        when(xmlStrategy.getSupportedExtension()).thenReturn("xml");
        List<FileParserStrategy<Processable>> strategies = Arrays.asList(csvStrategy, xmlStrategy);
        FileParserFactory<Processable> fileParserFactory = new FileParserFactory<>(strategies);

        // Act
        FileParserStrategy<Processable> csvParser = fileParserFactory.getParser("csv");
        FileParserStrategy<Processable> xmlParser = fileParserFactory.getParser("xml");

        // Assert
        assertThat(csvParser).isNotNull().isEqualTo(csvStrategy);
        assertThat(xmlParser).isNotNull().isEqualTo(xmlStrategy);
    }

    @Test
    void getParser_NonExistingExtension_ThrowsException() {
        // Arrange
        when(csvStrategy.getSupportedExtension()).thenReturn("csv");
        List<FileParserStrategy<Processable>> strategies = Arrays.asList(csvStrategy);
        FileParserFactory<Processable> fileParserFactory = new FileParserFactory<>(strategies);

        // Act & Assert
        assertThatThrownBy(() -> fileParserFactory.getParser("txt"))
                .isInstanceOf(FileParsingException.class);
    }

    @Test
    void getAllSupportedExtensions_ReturnsCorrectSet() {
        // Arrange
        when(csvStrategy.getSupportedExtension()).thenReturn("csv");
        when(xmlStrategy.getSupportedExtension()).thenReturn("xml");
        List<FileParserStrategy<Processable>> strategies = Arrays.asList(csvStrategy, xmlStrategy);
        FileParserFactory<Processable> fileParserFactory = new FileParserFactory<>(strategies);

        // Act
        Set<String> supportedExtensions = fileParserFactory.getAllSupportedExtensions();

        // Assert
        assertThat(supportedExtensions).containsExactlyInAnyOrder("csv", "xml");
    }
}