package yaremax.com.pb_task_24_04.util.parsers;

import yaremax.com.pb_task_24_04.markers.Processable;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface FileParser<T extends Processable> {
    String getSupportedExtension();
    Optional<List<T>> parse(File file, Class<T> entityClass);
}
