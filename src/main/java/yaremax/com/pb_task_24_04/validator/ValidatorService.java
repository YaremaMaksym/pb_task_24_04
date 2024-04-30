package yaremax.com.pb_task_24_04.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yaremax.com.pb_task_24_04.markers.Validatable;
import yaremax.com.pb_task_24_04.validator.strategies.ValidationStrategy;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValidatorService<T extends Validatable> {

    public Optional<List<T>> validateList(List<T> objects, List<ValidationStrategy<T>> strategies) {
        if (!strategies.isEmpty()) {
            List<T> validObjects = objects.stream()
                    .filter(obj -> strategies.stream().allMatch(strategy -> strategy.validate(obj)))
                    .toList();
            return Optional.of(validObjects);
        } else {
            return Optional.empty();
        }
    }
}
