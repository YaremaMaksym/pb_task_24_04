package yaremax.com.pb_task_24_04;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Api for test task", version = "1.0", description = "Api for test task"))
public class PbTask2404Application {

    public static void main(String[] args) {
        SpringApplication.run(PbTask2404Application.class, args);
    }

}
