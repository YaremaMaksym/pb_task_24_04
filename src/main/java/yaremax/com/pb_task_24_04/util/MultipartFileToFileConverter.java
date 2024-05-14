package yaremax.com.pb_task_24_04.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yaremax.com.pb_task_24_04.exception.FileConversionException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class MultipartFileToFileConverter {
    public Optional<File> convert(MultipartFile multipartFile) {
        try {
            if (multipartFile != null && !multipartFile.isEmpty()) {
                File tempFile = File.createTempFile("temp", null);
                try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                    fos.write(multipartFile.getBytes());
                }
                return Optional.of(tempFile);
            }
        } catch (IOException e) {
            throw new FileConversionException("Failed to convert MultipartFile to File", e);
        }
        return Optional.empty();
    }
}
