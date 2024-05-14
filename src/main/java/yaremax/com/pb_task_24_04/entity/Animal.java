package yaremax.com.pb_task_24_04.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotBlank
    @NotNull
    private String name;

    @NotEmpty
    @NotBlank
    @NotNull
    private String type;

    @NotEmpty
    @NotBlank
    @NotNull
    private String sex;

    @Min(0)
    @NotNull
    private Integer weight;

    @Min(0)
    @NotNull
    private Integer cost;

    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(id, animal.id) &&
                Objects.equals(name, animal.name) &&
                Objects.equals(type, animal.type) &&
                Objects.equals(sex, animal.sex) &&
                Objects.equals(weight, animal.weight) &&
                Objects.equals(cost, animal.cost) &&
                Objects.equals(category, animal.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, sex, weight, cost, category);
    }
}



