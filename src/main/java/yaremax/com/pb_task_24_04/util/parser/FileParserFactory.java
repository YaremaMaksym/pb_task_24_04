package yaremax.com.pb_task_24_04.util.parser;

import org.springframework.stereotype.Component;
import yaremax.com.pb_task_24_04.exceptions.FileParsingException;
import yaremax.com.pb_task_24_04.markers.Processable;
import yaremax.com.pb_task_24_04.util.parser.strategies.FileParserStrategy;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FileParserFactory<T extends Processable> {
    private final Map<String, FileParserStrategy<T>> strategies;

    public FileParserFactory(List<FileParserStrategy<T>> strategies) {
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(FileParserStrategy::getSupportedExtension, s -> s));
    }

    public FileParserStrategy<T> getParser(String extension) {
        return Optional.ofNullable(strategies.get(extension))
                .orElseThrow(() -> new FileParsingException("Unsupported file format: " + extension));
    }

    public Set<String> getAllSupportedExtensions() {
        return strategies.keySet();
    }
}
