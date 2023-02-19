package com.lambo.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambo.api.controllers.CarController;
import com.lambo.api.dto.CarDto;
import com.lambo.api.dto.GarageDto;
import com.lambo.api.models.Garage;
import com.lambo.api.models.Owner;
import com.lambo.api.repository.CarRepository;
import com.lambo.api.repository.GarageRepository;
import com.lambo.api.repository.OwnerRepository;
import com.lambo.api.service.CarService;
import com.lambo.api.service.GarageService;
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
import org.springframework.mock.web.MockHttpServletResponse;
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
public class CarControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarService carService;

    @Autowired
    private GarageService garageService;

    private CarDto car;

    @Autowired
    private CarController carController;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private GarageRepository garageRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();

        Owner owner = new Owner();
        owner.setId(1);
        ownerRepository.save(owner);

        GarageDto garage = new GarageDto();
        garage.setId(1);

        garageService.createGarage(owner.getId(), garage);

        car = new CarDto();
        car.setId(1);
        car.setBrand("Brand");
        car.setModel("Model");

        carService.createCar(car);
    }

    @Test
    public void testCreateCar() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final CarDto carDto = new CarDto();
        carDto.setBrand("Brand 1");
        carDto.setModel("Model 1");

        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "/api/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carDto)))
                .andExpect(result -> {
                    assertThat(result.getResponse().getStatus() == HttpStatus.CREATED.value());
                    CarDto responseBody = objectMapper.readValue(result.getResponse().getContentAsByteArray(), CarDto.class);
                    assertThat(carDto.equals(responseBody));
                })
                .andDo(print());
    }

    @Test
    @Transactional
    public void testGetAllCars() throws Exception {
        final List<CarDto> allCars = carService.getAllCars();
        final ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/api/car")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    assertThat(result.getResponse().getStatus() == HttpStatus.OK.value());
                    List<CarDto> responseBody = objectMapper.readValue(result.getResponse().getContentAsByteArray(), new TypeReference<List<CarDto>>() {
                    });
                    assertThat(allCars.equals(responseBody));
                })
                .andDo(print());
    }

    @Test
    @Transactional
    public void testGetCarsByOwnerIdAndGarageId() throws Exception {
        final List<CarDto> allCars = carService.getCarsByOwnerIdAndGarageId(1, 1);
        final ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/api/owner/1/garages/1/cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    assertThat(result.getResponse().getStatus() == HttpStatus.OK.value());
                    List<CarDto> responseBody = objectMapper.readValue(result.getResponse().getContentAsByteArray(), new TypeReference<List<CarDto>>() {});
                    assertThat(allCars.equals(responseBody));
                })
                .andDo(print());
    }

    @Test
    @Transactional
    public void testRemoveCarFromGarageByOwnerIdAndGarageId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.DELETE, "/api/owner/1/garages/1/cars/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    assertThat(result.getResponse().getStatus() == HttpStatus.OK.value());
                })
                .andDo(print());
    }

    @Test
    @Transactional
    public void testDeleteAllCars() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.DELETE, "/api/car")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    assertThat(result.getResponse().getStatus() == HttpStatus.OK.value());
                })
                .andDo(print());
    }

    @Test
    @Transactional
    public void testAssignGarageToCar() throws Exception {
        final CarDto carDto = new CarDto();
        carDto.setId(1);
        carDto.setBrand("Brand");
        carDto.setModel("Model");

        final GarageDto garageDto = new GarageDto();
        garageDto.setId(1);

        Set<GarageDto> garageDtoSet = new HashSet<>();
        garageDtoSet.add(garageDto);

        carDto.setGarageSet(garageDtoSet);

        final ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.PUT, "/api/car/1/garages/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    assertThat(result.getResponse().getStatus() == HttpStatus.OK.value());
                    CarDto responseBody = objectMapper.readValue(result.getResponse().getContentAsByteArray(), CarDto.class);
                    assertThat(carDto.equals(responseBody));
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
