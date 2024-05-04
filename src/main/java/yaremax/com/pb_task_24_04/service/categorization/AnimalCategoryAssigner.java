package yaremax.com.pb_task_24_04.service.categorization;

import org.springframework.stereotype.Component;
import yaremax.com.pb_task_24_04.entity.Animal;
import yaremax.com.pb_task_24_04.entity.Category;
import yaremax.com.pb_task_24_04.exception.InvalidDataException;

@Component
public class AnimalCategoryAssigner implements CategoryAssigner<Animal> {

    @Override
    public Category assignCategory(Animal animal) {
        int cost = animal.getCost();
        Category category;

        if (cost >= 0 && cost <= 20) {
            category = Category.FIRST;
        } else if (cost >= 21 && cost <= 40) {
            category = Category.SECOND;
        } else if (cost >= 41 && cost <= 60) {
            category = Category.THIRD;
        } else if (cost >= 61) {
            category = Category.FOURTH;
        } else throw new InvalidDataException("Invalid animal cost: " + cost);

        animal.setCategory(category);
        return category;
    }
}
