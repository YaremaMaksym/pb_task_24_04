package yaremax.com.pb_task_24_04.service.parser;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yaremax.com.pb_task_24_04.exception.FileParsingException;
import yaremax.com.pb_task_24_04.util.MultipartFileToFileConverter;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FileParserService<T> {
    private final FileParserFactory<T> fileParserFactory;
    private final MultipartFileToFileConverter multipartFileToFileConverter;

    public Set<String> getAllSupportedExtensions() {
        return fileParserFactory.getAllSupportedExtensions();
    }

    public Optional<List<T>> parseFile(MultipartFile multipartFile, Class<T> entityClass) {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        return Optional.ofNullable(fileParserFactory.getParser(extension))
                .map(parser -> {
                        Optional<File> file = multipartFileToFileConverter.convert(multipartFile);
                        return parser.parse(file, entityClass);
                })
                .orElseThrow(() -> new FileParsingException("Unsupported file format: " + extension));
    }
}
