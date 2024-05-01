package yaremax.com.pb_task_24_04.animal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
@Tag(name = "AnimalController", description = "Controller for handling requests related to animals")
public class AnimalController {
    private final AnimalService animalService;

    @Operation(summary = "Get a list of animals", description = "Retrieve animals optionally filtered by type, category, sex, and sorted by provided parameters")
    @ApiResponse(responseCode = "200", description = "Successful response", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Animal.class))})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Animal>> getAnimals(
            @Parameter(description = "Type of the animal") @RequestParam(required = false) String type,
            @Parameter(description = "Category of the animal") @RequestParam(required = false) Category category,
            @Parameter(description = "Sex of the animal") @RequestParam(required = false) String sex,
            @Parameter(description = "Direction to sort by") @RequestParam(required = false) Sort.Direction sortDirection,
            @Parameter(description = "Property to sort by") @RequestParam(required = false) String sortProperty) {

        Sort sort = sortDirection != null && sortProperty != null
                ? Sort.by(sortDirection, sortProperty)
                : Sort.unsorted();

        return ResponseEntity.ok(animalService.getAnimals(type, category, sex, sort));
    }
}