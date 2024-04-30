package yaremax.com.pb_task_24_04.categorization;

import yaremax.com.pb_task_24_04.animal.Category;
import yaremax.com.pb_task_24_04.markers.Processable;

public interface CategoryAssigner<T extends Processable> {
    Category assignCategory(T tobject);
}
