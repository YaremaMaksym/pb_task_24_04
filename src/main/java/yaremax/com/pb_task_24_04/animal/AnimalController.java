package yaremax.com.pb_task_24_04.animal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value = "AnimalController", description = "Controller for handling requests related to animals")
public class AnimalController {
    private final AnimalService animalService;

    @ApiOperation(value = "Get a list of animals", notes = "Retrieve animals optionally filtered by type, category, sex, and sorted by provided parameters")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Animal>> getAnimals(
            @ApiParam(value = "Type of the animal", required = false) @RequestParam(required = false) String type,
            @ApiParam(value = "Category of the animal", required = false) @RequestParam(required = false) Category category,
            @ApiParam(value = "Sex of the animal", required = false) @RequestParam(required = false) String sex,
            @ApiParam(value = "Direction to sort by", required = false) @RequestParam(required = false) Sort.Direction sortDirection,
            @ApiParam(value = "Property to sort by", required = false) @RequestParam(required = false) String sortProperty) {

        Sort sort = sortDirection != null && sortProperty != null
                ? Sort.by(sortDirection, sortProperty)
                : Sort.unsorted();

        return ResponseEntity.ok(animalService.getAnimals(type, category, sex, sort));
    }
}