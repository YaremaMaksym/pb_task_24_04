package yaremax.com.pb_task_24_04.service.categorization;

import yaremax.com.pb_task_24_04.entity.Category;

public interface CategoryAssigner<T> {
    Category assignCategory(T tobject);
}
