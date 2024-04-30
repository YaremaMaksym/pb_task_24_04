package yaremax.com.pb_task_24_04.util.mappers;

public interface Mapper<E, D> {
    E toEntity(D dto);
    D toDto(E entity);
}
