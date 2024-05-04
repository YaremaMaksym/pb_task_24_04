package yaremax.com.pb_task_24_04.service.mapper;

import org.springframework.stereotype.Component;
import yaremax.com.pb_task_24_04.entity.Animal;
import yaremax.com.pb_task_24_04.dto.AnimalDto;

@Component
public class AnimalMapper implements Mapper<Animal, AnimalDto> {
    @Override
    public Animal toEntity(AnimalDto dto) {
        return Animal.builder()
                .name(dto.getName())
                .type(dto.getType())
                .cost(dto.getCost())
                .weight(dto.getWeight())
                .build();
    }

    @Override
    public AnimalDto toDto(Animal entity) {
        return AnimalDto.builder()
                .name(entity.getName())
                .type(entity.getType())
                .cost(entity.getCost())
                .weight(entity.getWeight())
                .build();
    }
}
