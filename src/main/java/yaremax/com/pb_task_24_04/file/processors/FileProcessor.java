package yaremax.com.pb_task_24_04.file.processors;

import org.springframework.web.multipart.MultipartFile;
import yaremax.com.pb_task_24_04.markers.Processable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FileProcessor<T extends Processable> {
    Optional<List<T>> process(MultipartFile multipartFile) throws IOException;
}
