package yaremax.com.pb_task_24_04.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yaremax.com.pb_task_24_04.entity.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Long>{
}
