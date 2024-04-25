package yaremax.com.pb_task_24_04.dto;

import lombok.Builder;

@Builder
public record AnimalDto(String name,
                        String type,
                        String sex,
                        int weight,
                        int cost) implements Parsable {

}