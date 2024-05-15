package yaremax.com.pb_task_24_04.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import yaremax.com.pb_task_24_04.repository.AnimalRepository;
import yaremax.com.pb_task_24_04.entity.Animal;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;

    public Animal addAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    public List<Animal> getAnimals(Animal animalExample, Sort sort) {
        if (animalExample == null && sort.isUnsorted()) {
            return animalRepository.findAll();
        }
        if (animalExample == null && sort.isSorted()) {
            return animalRepository.findAll(sort);
        }
        else return animalRepository.findAll(Example.of(animalExample), sort);
    }
}
