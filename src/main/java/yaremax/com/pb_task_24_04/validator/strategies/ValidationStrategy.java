package yaremax.com.pb_task_24_04.validator.strategies;

import yaremax.com.pb_task_24_04.markers.Validatable;

public interface ValidationStrategy<T extends Validatable> {
    boolean validate(T object);
}
