package yaremax.com.pb_task_24_04.service.validator.strategy;

public interface ValidationStrategy<T> {
    boolean validate(T object);
}
