package com.lambo.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambo.api.controllers.CarController;
import com.lambo.api.controllers.OwnerController;
import com.lambo.api.dto.CarDto;
import com.lambo.api.service.CarService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;


@WebMvcTest(controllers = OwnerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CarControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @MockBean
    private CarDto car;

    @MockBean
    private List<CarDto> carList;

    @InjectMocks
    private CarController carController;

    @Before
    public void setUp() throws Exception {
        carService = Mockito.mock(CarService.class);
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
        car = new CarDto();
        car.setId(1);
        car.setBrand("Brand");
        car.setModel("Model");

        carList = new ArrayList<>();
        carList.add(car);
    }

    @Test
    public void testCreateCar() throws Exception {
        Mockito.when(this.carService.createCar(car)).thenReturn(car);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/car/create").contentType(MediaType.APPLICATION_JSON).content(jsonToString(car)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testgetAllCars() throws Exception {
        Mockito.when(this.carService.getAllCars()).thenReturn(carList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetCarsByOwnerIdAndGarageId() throws Exception {
        Mockito.when(this.carService.getCarsByOwnerIdAndGarageId(1,1)).thenReturn(carList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/owner/1/garages/1/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testRemoveCarFromGarageByOwnerIdAndGarageId() throws Exception {
        this.carService.removeCarFromGarageByOwnerIdAndGarageId(1,1,1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/owner/1/garages/1/cars/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void testDeleteAllCars() throws Exception {
        this.carService.deleteAllCars();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cars/delete").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void testAssignGarageToCar() throws Exception {
        this.carService.assignGarageToCar(1,1);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/cars/1/garages/1/assign").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
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
