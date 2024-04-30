package yaremax.com.pb_task_24_04.categorization;

import yaremax.com.pb_task_24_04.animal.Category;

public interface CategoryAssigner<T> {
    Category assignCategory(T tobject);
}
