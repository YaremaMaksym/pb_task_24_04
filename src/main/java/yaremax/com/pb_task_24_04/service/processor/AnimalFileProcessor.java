package yaremax.com.pb_task_24_04.service.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yaremax.com.pb_task_24_04.entity.Animal;
import yaremax.com.pb_task_24_04.service.AnimalService;
import yaremax.com.pb_task_24_04.service.categorization.AnimalCategoryAssigner;
import yaremax.com.pb_task_24_04.service.parser.FileParserService;
import yaremax.com.pb_task_24_04.service.validator.EntityValidator;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalFileProcessor implements FileProcessor<Animal> {
    private final AnimalCategoryAssigner animalCategoryAssigner;
    private final FileParserService<Animal> fileParserService;
    private final AnimalService animalService;
    private final EntityValidator<Animal> animalEntityValidator;

    @Override
    public Optional<List<Animal>> process(MultipartFile multipartFile) {
        return fileParserService.parseFile(multipartFile, Animal.class)
                .flatMap(parsedEntities ->
                        Optional.of(
                                parsedEntities.stream()
                                        .filter(animalEntityValidator::isValid)
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
