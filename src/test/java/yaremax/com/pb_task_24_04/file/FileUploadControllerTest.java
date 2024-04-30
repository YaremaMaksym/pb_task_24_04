package yaremax.com.pb_task_24_04.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import yaremax.com.pb_task_24_04.animal.Animal;
import yaremax.com.pb_task_24_04.exceptions.FileParsingException;
import yaremax.com.pb_task_24_04.exceptions.GlobalExceptionHandler;
import yaremax.com.pb_task_24_04.file.processors.AnimalFileProcessor;
import yaremax.com.pb_task_24_04.util.parser.FileParserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class FileUploadControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AnimalFileProcessor animalFileProcessor;

    @Mock
    private FileParserService<?> fileParserService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FileUploadController(animalFileProcessor, fileParserService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void uploadFile_ValidFile_ReturnsListOfAnimals() throws Exception {
        // Arrange
        List<Animal> animals = new ArrayList<>();
        when(animalFileProcessor.process(any())).thenReturn(Optional.of(animals));
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "data".getBytes());

        // Act & Assert
        mockMvc.perform(multipart("/files/uploads")
                        .file(file))
                .andExpect(status().isOk());

        verify(animalFileProcessor).process(file);
    }

    @Test
    void uploadFile_InvalidFile_ReturnsBadRequest() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "test.xml", "text/xml", "data".getBytes());
        doThrow(new FileParsingException("Invalid file"))
                .when(animalFileProcessor).process(any());

        // Act & Assert
        mockMvc.perform(multipart("/files/uploads")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());

        verify(animalFileProcessor).process(file);
    }

    @Test
    void getAllSupportedExtensions_ReturnsCorrectSet() throws Exception {
        // Arrange
        Set<String> extensions = Set.of("csv", "xml");
        when(fileParserService.getAllSupportedExtensions()).thenReturn(extensions);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/files/uploads/supported-extensions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasItems("csv", "xml")));

        verify(fileParserService).getAllSupportedExtensions();
    }

}
