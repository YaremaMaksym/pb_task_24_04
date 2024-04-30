package yaremax.com.pb_task_24_04.util.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import yaremax.com.pb_task_24_04.markers.Processable;
import yaremax.com.pb_task_24_04.util.parser.strategies.FileParserStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
        Optional<FileParserStrategy<Processable>> csvParser = fileParserFactory.getParser("csv");
        Optional<FileParserStrategy<Processable>> xmlParser = fileParserFactory.getParser("xml");

        // Assert
        assertThat(csvParser).isPresent().contains(csvStrategy);
        assertThat(xmlParser).isPresent().contains(xmlStrategy);
    }

    @Test
    void getParser_NonExistingExtension_ReturnsEmpty() {
        // Arrange
        when(csvStrategy.getSupportedExtension()).thenReturn("csv");
        List<FileParserStrategy<Processable>> strategies = Arrays.asList(csvStrategy);
        FileParserFactory<Processable> fileParserFactory = new FileParserFactory<>(strategies);

        // Act
        Optional<FileParserStrategy<Processable>> txtParser = fileParserFactory.getParser("txt");

        // Assert
        assertThat(txtParser).isEmpty();
    }
}