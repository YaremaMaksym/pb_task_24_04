package yaremax.com.pb_task_24_04.validator.strategies;

public interface ValidationStrategy<T> {
    boolean validate(T object);
}
