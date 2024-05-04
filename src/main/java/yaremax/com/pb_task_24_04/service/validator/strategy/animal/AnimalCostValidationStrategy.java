package yaremax.com.pb_task_24_04.service.validator.strategy.animal;

import org.springframework.stereotype.Component;
import yaremax.com.pb_task_24_04.entity.Animal;
import yaremax.com.pb_task_24_04.service.validator.strategy.ValidationStrategy;

@Component
public class AnimalCostValidationStrategy implements ValidationStrategy<Animal> {

    @Override
    public boolean validate(Animal animal) {
        return animal.getCost() > 0;
    }
}
