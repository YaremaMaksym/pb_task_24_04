package yaremax.com.pb_task_24_04.service.parser.strategy;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface FileParserStrategy<T> {
    String getSupportedExtension();
    Optional<List<T>> parse(Optional<File> fileOptional, Class<T> entityClass);
}
