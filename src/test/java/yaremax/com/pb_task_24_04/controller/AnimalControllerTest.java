package yaremax.com.pb_task_24_04.controller;

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
import yaremax.com.pb_task_24_04.entity.Animal;
import yaremax.com.pb_task_24_04.entity.Category;
import yaremax.com.pb_task_24_04.service.AnimalService;

import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
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
    void getAnimals_WithExampleAndNoSorting_ReturnsFilteredAnimals() throws Exception {
        // Arrange
        Animal exampleAnimal = Animal.builder().type("Dog").build();
        List<Animal> filteredAnimals = Arrays.asList(
                Animal.builder().id(1L).name("Animal 1").type("Dog").sex("Male").weight(10).cost(100).category(Category.FOURTH).build()
        );
        when(animalService.getAnimals(exampleAnimal, Sort.unsorted())).thenReturn(filteredAnimals);

        // Act & Assert
        mockMvc.perform(get("/animals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exampleAnimal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Animal 1")))
                .andExpect(jsonPath("$[0].type", is("Dog")))
                .andExpect(jsonPath("$[0].sex", is("Male")))
                .andExpect(jsonPath("$[0].weight", is(10)))
                .andExpect(jsonPath("$[0].cost", is(100)))
                .andExpect(jsonPath("$[0].category", is("FOURTH")));
    }

    @Test
    void getAnimals_WithExampleAndSorting_ReturnsFilteredAndSortedAnimals() throws Exception {
        // Arrange
        Animal exampleAnimal = Animal.builder().category(Category.THIRD).build();
        List<Animal> filteredAndSortedAnimals = Arrays.asList(
                Animal.builder().id(2L).name("Animal 2").type("Cat").sex("Female").weight(5).cost(50).category(Category.THIRD).build(),
                Animal.builder().id(3L).name("Animal 3").type("Bird").sex("Male").weight(2).cost(30).category(Category.THIRD).build()
        );
        when(animalService.getAnimals(exampleAnimal, Sort.by(Sort.Direction.DESC, "cost"))).thenReturn(filteredAndSortedAnimals);

        // Act & Assert
        mockMvc.perform(get("/animals")
                        .param("sortDirection", "DESC")
                        .param("sortProperty", "cost")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exampleAnimal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].cost", is(50)))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].cost", is(30)));
    }

    @Test
    void getAnimals_WithInvalidSortProperty_ReturnsUnsortedAnimals() throws Exception {
        // Arrange
        List<Animal> animals = Arrays.asList(
                Animal.builder().id(1L).name("Animal 1").type("Dog").sex("Male").weight(10).cost(100).category(Category.FOURTH).build(),
                Animal.builder().id(2L).name("Animal 2").type("Cat").sex("Female").weight(5).cost(50).category(Category.THIRD).build()
        );
        when(animalService.getAnimals(any(), any(Sort.class))).thenReturn(animals);

        // Act & Assert
        mockMvc.perform(get("/animals")
                        .param("sortDirection", "ASC")
                        .param("sortProperty", "invalidProperty")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }
}