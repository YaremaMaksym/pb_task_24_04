package yaremax.com.pb_task_24_04.animal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yaremax.com.pb_task_24_04.markers.Parsable;
import yaremax.com.pb_task_24_04.markers.Validatable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDto implements Parsable, Validatable {
    private String name;
    private String type;
    private String sex;
    private int weight;
    private int cost;
}