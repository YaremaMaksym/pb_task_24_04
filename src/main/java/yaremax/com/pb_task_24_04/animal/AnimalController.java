package yaremax.com.pb_task_24_04.animal;

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
public class AnimalController {
    private final AnimalService animalService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Animal>> getAnimals(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) Sort.Direction sortDirection,
            @RequestParam(required = false) String sortProperty) {

        Sort sort = sortDirection != null && sortProperty != null
                ? Sort.by(sortDirection, sortProperty)
                : Sort.unsorted();

        return ResponseEntity.ok(animalService.getAnimals(type, category, sex, sort));
    }
}
