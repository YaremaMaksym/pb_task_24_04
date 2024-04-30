package yaremax.com.pb_task_24_04.util.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import yaremax.com.pb_task_24_04.exceptions.FileParsingException;
import yaremax.com.pb_task_24_04.markers.Processable;
import yaremax.com.pb_task_24_04.util.MultipartFileToFileConverter;
import yaremax.com.pb_task_24_04.util.parser.strategies.FileParserStrategy;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileParserServiceTest {

    @Mock
    private FileParserFactory<Processable> fileParserFactory;

    @Mock
    private FileParserStrategy<Processable> csvStrategy;

    @Mock
    private MultipartFileToFileConverter multipartFileToFileConverter;

    @InjectMocks
    private FileParserService<Processable> fileParserService;

    @Test
    void parseFile_ExistingExtension_ReturnsEntities() throws IOException {
        // Arrange
        MultipartFile multipartFile = new MockMultipartFile("test.csv", "test.csv", "text/csv", "content".getBytes());
        File file = new File("test.csv");
        Processable entity1 = new Processable() {};
        Processable entity2 = new Processable() {};
        List<Processable> entities = Arrays.asList(entity1, entity2);

        when(fileParserFactory.getParser("csv")).thenReturn(csvStrategy);
        when(multipartFileToFileConverter.convert(multipartFile)).thenReturn(Optional.of(file));
        when(csvStrategy.parse(Optional.of(file), Processable.class)).thenReturn(Optional.of(entities));

        // Act
        Optional<List<Processable>> parsedEntities = fileParserService.parseFile(multipartFile, Processable.class);

        // Assert
        assertThat(parsedEntities).isPresent().contains(entities);
    }

    @Test
    void parseFile_NonExistingExtension_ThrowsException() throws IOException {
        // Arrange
        MultipartFile multipartFile = new MockMultipartFile("test.txt", "test.txt", "text/plain", "content".getBytes());

        when(fileParserFactory.getParser("txt")).thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> fileParserService.parseFile(multipartFile, Processable.class))
                .isInstanceOf(FileParsingException.class);
    }

    @Test
    void getAllSupportedExtensions_ReturnsCorrectSet() {
        // Arrange
        Set<String> supportedExtensions = Set.of("csv", "xml");
        when(fileParserFactory.getAllSupportedExtensions()).thenReturn(supportedExtensions);

        // Act
        Set<String> result = fileParserService.getAllSupportedExtensions();

        // Assert
        assertThat(result).isEqualTo(supportedExtensions);
    }
}
