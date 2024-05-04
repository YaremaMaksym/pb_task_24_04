package yaremax.com.pb_task_24_04.animal;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;
import yaremax.com.pb_task_24_04.entity.Animal;
import yaremax.com.pb_task_24_04.entity.Category;
import yaremax.com.pb_task_24_04.specification.AnimalSpecification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnimalSpecificationTest {

    @Test
    void hasType_ShouldReturnPredicateWithTypeCondition() {
        // Arrange
        Root<Animal> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder builder = mock(CriteriaBuilder.class);
        String type = "Dog";

        when(builder.equal(root.get("type"), type)).thenReturn(mock(Predicate.class));

        // Act
        Specification<Animal> specification = AnimalSpecification.hasType(type);
        Predicate predicate = specification.toPredicate(root, query, builder);

        // Assert
        assertThat(predicate).isNotNull();
    }

    @Test
    void hasCategory_ShouldReturnPredicateWithCategoryCondition() {
        // Arrange
        Root<Animal> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder builder = mock(CriteriaBuilder.class);
        Category category = Category.FIRST;

        when(builder.equal(root.get("category"), category)).thenReturn(mock(Predicate.class));

        // Act
        Specification<Animal> specification = AnimalSpecification.hasCategory(category);
        Predicate predicate = specification.toPredicate(root, query, builder);

        // Assert
        assertThat(predicate).isNotNull();
    }

    @Test
    void hasSex_ShouldReturnPredicateWithSexCondition() {
        // Arrange
        Root<Animal> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder builder = mock(CriteriaBuilder.class);
        String sex = "Male";

        when(builder.equal(root.get("sex"), sex)).thenReturn(mock(Predicate.class));

        // Act
        Specification<Animal> specification = AnimalSpecification.hasSex(sex);
        Predicate predicate = specification.toPredicate(root, query, builder);

        // Assert
        assertThat(predicate).isNotNull();
    }
}