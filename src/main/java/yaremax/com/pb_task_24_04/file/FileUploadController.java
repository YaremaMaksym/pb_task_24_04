package yaremax.com.pb_task_24_04.file;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yaremax.com.pb_task_24_04.animal.Animal;
import yaremax.com.pb_task_24_04.file.processors.AnimalFileProcessor;
import yaremax.com.pb_task_24_04.util.parser.FileParserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("files/uploads")
@RequiredArgsConstructor
public class FileUploadController {
    private final AnimalFileProcessor animalFileProcessor;
    private final FileParserService<?> fileParserService;

    // think better would be to make sub route - "/animals"
    // but made like this to meet the requirements
    @PostMapping
    public ResponseEntity<Optional<List<Animal>>> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        return ResponseEntity.ok(animalFileProcessor.process(multipartFile));
    }

    @GetMapping(value = "/supported-extensions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<String>> getAllSupportedExtensions() {
        Set<String> extensions = fileParserService.getAllSupportedExtensions();
        return ResponseEntity.ok(extensions);
    }
}
