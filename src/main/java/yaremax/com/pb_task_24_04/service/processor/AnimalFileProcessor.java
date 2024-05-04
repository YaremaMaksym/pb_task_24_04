package yaremax.com.pb_task_24_04.service.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yaremax.com.pb_task_24_04.entity.Animal;
import yaremax.com.pb_task_24_04.service.AnimalService;
import yaremax.com.pb_task_24_04.service.categorization.AnimalCategoryAssigner;
import yaremax.com.pb_task_24_04.service.parser.FileParserService;
import yaremax.com.pb_task_24_04.service.validator.ValidatorService;
import yaremax.com.pb_task_24_04.service.validator.strategy.ValidationStrategy;
import yaremax.com.pb_task_24_04.service.validator.strategy.animal.*;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalFileProcessor implements FileProcessor<Animal> {
    private final AnimalCategoryAssigner animalCategoryAssigner;
    private final FileParserService<Animal> fileParserService;
    private final ValidatorService<Animal> validatorService;
    private final AnimalService animalService;

    @Override
    public Optional<List<Animal>> process(MultipartFile multipartFile) {
        List<ValidationStrategy<Animal>> strategies = List.of(
                new AnimalCostValidationStrategy(),
                new AnimalNameValidationStrategy(),
                new AnimalTypeValidationStrategy(),
                new AnimalSexValidationStrategy(),
                new AnimalWeightValidationStrategy());

        return fileParserService.parseFile(multipartFile, Animal.class)
                .flatMap(parsedEntities -> validatorService.validateList(parsedEntities, strategies)
                                .map(validEntities -> validEntities.stream()
                                                .map(animal -> { // peek() is for debugging.
                                                    animalCategoryAssigner.assignCategory(animal);
                                                    return animal;
                                                })
                                                .map(animalService::addAnimal)
                                                .toList()
                                )
                );
    }
}
