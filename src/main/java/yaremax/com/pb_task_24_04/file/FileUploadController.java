package yaremax.com.pb_task_24_04.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/files/uploads")
@RequiredArgsConstructor
@Tag(name = "FileUploadController", description = "Controller for handling file uploads and processing for data of different models")
public class FileUploadController {
    private final AnimalFileProcessor animalFileProcessor;
    private final FileParserService<?> fileParserService;

    @Operation(summary = "Upload file", description = "Uploads a file with any extension and processes it to add animal data in the system")
    @PostMapping
    public ResponseEntity<Optional<List<Animal>>> uploadFile(
            @Parameter(description = "File to be uploaded", required = true) @RequestPart("file") MultipartFile multipartFile) {
        return ResponseEntity.ok(animalFileProcessor.process(multipartFile));
    }

    @Operation(summary = "Get supported file extensions", description = "Retrieves all supported file extensions for uploads")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the supported file extensions", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Set.class))})
    @GetMapping(value = "/supported-extensions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<String>> getAllSupportedExtensions() {
        Set<String> extensions = fileParserService.getAllSupportedExtensions();
        return ResponseEntity.ok(extensions);
    }
}