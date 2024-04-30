package yaremax.com.pb_task_24_04.util;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MultipartFileToFileConverterTest {

    private final MultipartFileToFileConverter converter = new MultipartFileToFileConverter();

    @Test
    void convert_ValidMultipartFile_ReturnsFile() throws IOException {
        // Arrange
        byte[] fileContent = "test text".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("test.txt", "test.txt", "text/plain", fileContent);

        // Act
        Optional<File> result = converter.convert(multipartFile);

        // Assert
        assertThat(result).isPresent();
        File tempFile = result.get();
        assertThat(tempFile.exists()).isTrue();
        assertThat(Files.readAllBytes(tempFile.toPath())).isEqualTo(fileContent);
    }

    @Test
    void convert_EmptyMultipartFile_ReturnsEmptyOptional() {
        // Arrange
        MultipartFile multipartFile = new MockMultipartFile("test.txt", new byte[0]);

        // Act
        Optional<File> result = converter.convert(multipartFile);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void convert_NullMultipartFile_ReturnsEmptyOptional() {
        // Act
        Optional<File> result = converter.convert(null);

        // Assert
        assertThat(result).isEmpty();
    }
}