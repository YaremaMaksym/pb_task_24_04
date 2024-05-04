package yaremax.com.pb_task_24_04.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import yaremax.com.pb_task_24_04.entity.Animal;
import yaremax.com.pb_task_24_04.entity.Category;

@Component
public class AnimalSpecification {
    public static Specification<Animal> hasType(String type) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("type"), type);
    }

    public static Specification<Animal> hasCategory(Category category) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category"), category);
    }

    public static Specification<Animal> hasSex(String sex) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("sex"), sex);
    }
}
