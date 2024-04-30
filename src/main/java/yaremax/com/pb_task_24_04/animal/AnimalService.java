package yaremax.com.pb_task_24_04.animal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;

    public Animal addAnimal(Animal animal) {
        return animalRepository.save(animal);
    }
}
