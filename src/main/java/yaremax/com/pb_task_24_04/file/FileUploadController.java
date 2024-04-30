package yaremax.com.pb_task_24_04.file;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "FileUploadController", description = "Controller for handling file uploads and processing for data of different models")
public class FileUploadController {
    private final AnimalFileProcessor animalFileProcessor;
    private final FileParserService<?> fileParserService;

    // think better would be to make sub route - "/animals"
    // but made like this to meet the requirements
    @ApiOperation(value = "Upload file", notes = "Uploads a file with any extension and processes it to add animal data in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully processed the file", response = List.class),
            @ApiResponse(code = 400, message = "Bad request, unable to process file"),
            @ApiResponse(code = 500, message = "Internal server error occurred while processing the file")
    })
    @PostMapping
    public ResponseEntity<Optional<List<Animal>>> uploadFile(
            @ApiParam(value = "File to be uploaded", required = true) @RequestParam("file") MultipartFile multipartFile) {
        return ResponseEntity.ok(animalFileProcessor.process(multipartFile));
    }

    @ApiOperation(value = "Get supported file extensions", notes = "Retrieves all supported file extensions for uploads")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the supported file extensions", response = Set.class),
            @ApiResponse(code = 500, message = "Internal server error occurred while retrieving the supported file extensions")
    })
    @GetMapping(value = "/supported-extensions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<String>> getAllSupportedExtensions() {
        Set<String> extensions = fileParserService.getAllSupportedExtensions();
        return ResponseEntity.ok(extensions);
    }
}
