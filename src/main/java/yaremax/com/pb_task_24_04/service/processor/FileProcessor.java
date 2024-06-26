package yaremax.com.pb_task_24_04.service.processor;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FileProcessor<T> {
    Optional<List<T>> process(MultipartFile multipartFile) throws IOException;
}
