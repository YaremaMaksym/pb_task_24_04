package yaremax.com.pb_task_24_04.validator.strategies.animal;

import org.springframework.stereotype.Component;
import yaremax.com.pb_task_24_04.animal.Animal;
import yaremax.com.pb_task_24_04.validator.strategies.ValidationStrategy;

@Component
public class AnimalCostValidationStrategy implements ValidationStrategy<Animal> {

    @Override
    public boolean validate(Animal animal) {
        return animal.getCost() > 0;
    }
}