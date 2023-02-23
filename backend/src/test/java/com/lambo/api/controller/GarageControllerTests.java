package com.lambo.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambo.api.controllers.GarageController;
import com.lambo.api.dto.CarDto;
import com.lambo.api.dto.GarageDto;
import com.lambo.api.models.Car;
import com.lambo.api.models.Garage;
import com.lambo.api.models.Owner;
import com.lambo.api.repository.CarRepository;
import com.lambo.api.repository.GarageRepository;
import com.lambo.api.repository.OwnerRepository;
import com.lambo.api.service.GarageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc(addFilters = false)
public class GarageControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private GarageService garageService;

    @Autowired
    private CarRepository carRepository;

    private GarageDto garage;
    private Owner owner;

    private Car car;

    @Autowired
    private GarageController garageController;
    @Autowired
    private GarageRepository garageRepository;


    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(garageController).build();
        garage = new GarageDto();
        garage.setId(1);
        garage.setName("Name");
        garage.setLocation("Location");

        owner = new Owner();
        owner.setId(1);

        owner = ownerRepository.save(owner);

        car = new Car();
        car.setId(1);
        car = carRepository.save(car);
        garage = garageService.createGarage(owner.getId(), garage);
    }

    @Test
    public void testCreateGarage() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "/api/owner/" + garage.getId() + "/garage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(garage)))
                .andExpect(result -> {
                    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
                })
                .andDo(print());
    }

    @Test
    public void testGetGaragesByOwnerId() throws Exception {
        final List<GarageDto> allGarages = garageService.getGaragesByOwnerId(owner.getId());
        final ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/api/owner/" + owner.getId() + "/garage")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
                    List<GarageDto> responseBody = objectMapper.readValue(result.getResponse().getContentAsByteArray(), new TypeReference<List<GarageDto>>() {});
                    Assertions.assertThat(allGarages).isEqualTo(responseBody);
                })
                .andDo(print());
    }

    @Test
    @Transactional
    public void testAssignCarToGarage() throws Exception {
        final GarageDto garageDto = new GarageDto();
        garageDto.setId(1);
        garageDto.setName("Name");
        garageDto.setLocation("Location");

        CarDto carDto = new CarDto(1, null, null, new HashSet<>());
        Set<CarDto> carDtoSet = new HashSet<>();
        carDtoSet.add(carDto);

        garageDto.setCars(carDtoSet);

        final ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.PUT, "/api/owner/" + owner.getId() + "/garages/" + garage.getId() + "/cars/" + car.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
                    GarageDto responseBody = objectMapper.readValue(result.getResponse().getContentAsByteArray(), GarageDto.class);
                    Assertions.assertThat(garageDto).isEqualTo(responseBody);
                })
                .andDo(print());
    }

    @Test
    public void testGetGarageById() throws Exception {
        final GarageDto garageDto = garageService.getGarageById(garage.getId(), owner.getId());
        final ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/api/owner/" + owner.getId() + "/garages/" + garage.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
                    GarageDto responseBody = objectMapper.readValue(result.getResponse().getContentAsByteArray(), GarageDto.class);
                    Assertions.assertThat(garageDto).isEqualTo(responseBody);
                })
                .andDo(print());
    }

    @Test
    @Transactional
    public void testDeleteGarage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.DELETE, "/api/owner/" + owner.getId() + "/garages/" + garage.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
                })
                .andDo(print());
    }

    @Test
    @Transactional
    public void testGetGaragesCarHasAccessTo() throws Exception {
        final List<GarageDto> allGarages = garageService.getGaragesCarHasAccessTo(car.getId());
        final ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET,"/api/cars/" + car.getId() + "/garages")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
                    List<GarageDto> responseBody = objectMapper.readValue(result.getResponse().getContentAsByteArray(), new TypeReference<List<GarageDto>>() {});
                    Assertions.assertThat(allGarages).isEqualTo(responseBody);
                })
                .andDo(print());
    }

    // Parsing String format data into JSON format
    private static String jsonToString(final Object obj) throws JsonProcessingException {
        String result;
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            result = jsonContent;
        } catch (JsonProcessingException e) {
            result = "Json processing error";
        }
        return result;
    }

}
