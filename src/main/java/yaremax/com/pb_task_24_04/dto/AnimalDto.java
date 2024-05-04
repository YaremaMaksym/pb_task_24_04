package yaremax.com.pb_task_24_04.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDto {
    private String name;
    private String type;
    private String sex;
    private int weight;
    private int cost;
}