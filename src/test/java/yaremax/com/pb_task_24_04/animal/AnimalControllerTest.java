package yaremax.com.pb_task_24_04.animal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import yaremax.com.pb_task_24_04.controller.AnimalController;
import yaremax.com.pb_task_24_04.entity.Animal;
import yaremax.com.pb_task_24_04.entity.Category;
import yaremax.com.pb_task_24_04.service.AnimalService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AnimalControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AnimalService animalService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        AnimalController animalController = new AnimalController(animalService);
        mockMvc = MockMvcBuilders.standaloneSetup(animalController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAnimals_WithoutFilters_ReturnsAllAnimals() throws Exception {
        // Arrange
        List<Animal> animalInfos = Arrays.asList(
                Animal.builder().id(1L).name("Animal 1").type("Dog").sex("Male").weight(10).cost(100).category(Category.FOURTH).build(),
                Animal.builder().id(2L).name("Animal 2").type("Cat").sex("Female").weight(5).cost(50).category(Category.THIRD).build()
        );
        when(animalService.getAnimals(null, null, null, Sort.unsorted())).thenReturn(animalInfos);

        // Act & Assert
        mockMvc.perform(get("/animals")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Animal 1")))
                .andExpect(jsonPath("$[0].type", is("Dog")))
                .andExpect(jsonPath("$[0].sex", is("Male")))
                .andExpect(jsonPath("$[0].weight", is(10)))
                .andExpect(jsonPath("$[0].cost", is(100)))
                .andExpect(jsonPath("$[0].category", is("FOURTH")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Animal 2")))
                .andExpect(jsonPath("$[1].type", is("Cat")))
                .andExpect(jsonPath("$[1].sex", is("Female")))
                .andExpect(jsonPath("$[1].weight", is(5)))
                .andExpect(jsonPath("$[1].cost", is(50)))
                .andExpect(jsonPath("$[1].category", is("THIRD")));
    }

    @Test
    void getAnimals_WithFilters_ReturnsFilteredAnimals() throws Exception {
        // Arrange
        List<Animal> list = Arrays.asList(
                Animal.builder().id(1L).name("Animal 1").type("Dog").sex("Male").weight(10).cost(100).category(Category.FOURTH).build()
        );
        when(animalService.getAnimals("Dog", Category.FOURTH, "Male", Sort.unsorted())).thenReturn(list);

        // Act & Assert
        mockMvc.perform(get("/animals")
                        .param("type", "Dog")
                        .param("category", "FOURTH") // Виправлено значення параметру категорії на "FOURTH"
                        .param("sex", "Male")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Animal 1")))
                .andExpect(jsonPath("$[0].type", is("Dog")))
                .andExpect(jsonPath("$[0].sex", is("Male")))
                .andExpect(jsonPath("$[0].weight", is(10)))
                .andExpect(jsonPath("$[0].cost", is(100)))
                .andExpect(jsonPath("$[0].category", is("FOURTH"))); // Виправлено на значення "FOURTH"
    }

    @Test
    void getAnimals_WithSorting_ReturnsSortedAnimals() throws Exception {
        // Arrange
        List<Animal> list = Arrays.asList(
                Animal.builder().id(1L).name("Animal 1").type("Dog").sex("Male").weight(10).cost(100).category(Category.FOURTH).build(),
                Animal.builder().id(2L).name("Animal 2").type("Cat").sex("Female").weight(5).cost(50).category(Category.THIRD).build()
        );
        Collections.sort(list, Comparator.comparing(Animal::getCost));

        when(animalService.getAnimals(null, null, null, Sort.by(Sort.Direction.ASC, "cost"))).thenReturn(list);

        // Act & Assert
        mockMvc.perform(get("/animals")
                        .param("sortDirection", "ASC")
                        .param("sortProperty", "cost")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].cost", is(50)))
                .andExpect(jsonPath("$[1].id", is(1)))
                .andExpect(jsonPath("$[1].cost", is(100)));
    }
}