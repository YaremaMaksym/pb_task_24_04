package yaremax.com.pb_task_24_04.animal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yaremax.com.pb_task_24_04.markers.Processable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDto implements Processable {
    private String name;
    private String type;
    private String sex;
    private int weight;
    private int cost;
}