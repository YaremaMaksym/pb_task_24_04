package yaremax.com.pb_task_24_04.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import yaremax.com.pb_task_24_04.repository.AnimalRepository;
import yaremax.com.pb_task_24_04.specification.AnimalSpecification;
import yaremax.com.pb_task_24_04.entity.Category;
import yaremax.com.pb_task_24_04.entity.Animal;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;

    public Animal addAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    public List<Animal> getAnimals(String type, Category category, String sex, Sort sort) {
        Specification<Animal> spec = Specification.where(null);

        if (type != null) {
            spec = spec.and(AnimalSpecification.hasType(type));
        }

        if (category != null) {
            spec = spec.and(AnimalSpecification.hasCategory(category));
        }

        if (sex != null) {
            spec = spec.and(AnimalSpecification.hasSex(sex));
        }

        return animalRepository.findAll(spec, sort);
    }
}
