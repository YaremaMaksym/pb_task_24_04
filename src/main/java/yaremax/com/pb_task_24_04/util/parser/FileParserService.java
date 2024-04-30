package yaremax.com.pb_task_24_04.util.parser;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yaremax.com.pb_task_24_04.markers.Processable;
import yaremax.com.pb_task_24_04.util.MultipartFileToFileConverter;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FileParserService<T extends Processable> {
    private final FileParserFactory<T> fileParserFactory;
    private final MultipartFileToFileConverter multipartFileToFileConverter;

    public Set<String> getAllSupportedExtensions() {
        return fileParserFactory.getAllSupportedExtensions();
    }

    public Optional<List<T>> parseFile(MultipartFile multipartFile, Class<T> entityClass) {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        return fileParserFactory.getParser(extension)
                .flatMap(parser -> {
                        Optional<File> file = multipartFileToFileConverter.convert(multipartFile);
                        return parser.parse(file, entityClass);
                });
    }
}
