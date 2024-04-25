package yaremax.com.pb_task_24_04.parsers;

import yaremax.com.pb_task_24_04.dto.Parsable;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface FileParser<T extends Parsable> {
    String getSupportedExtension();
    Optional<List<T>> parse(File file, Class<T> entityClass);
}
