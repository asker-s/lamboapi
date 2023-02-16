package com.lambo.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambo.api.controllers.GarageController;
import com.lambo.api.controllers.OwnerController;
import com.lambo.api.dto.GarageDto;
import com.lambo.api.dto.OwnerDto;
import com.lambo.api.service.GarageService;
import com.lambo.api.service.OwnerService;
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
public class GarageControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GarageService garageService;

    @MockBean
    private GarageDto garage;

    @MockBean
    private List<GarageDto> garageList;

    @InjectMocks
    private GarageController garageController;

    @Before
    public void setUp() throws Exception {
        garageService = Mockito.mock(GarageService.class);
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(garageController).build();
        garage = new GarageDto();
        garage.setId(1);
        garage.setName("Name");
        garage.setLocation("Location");

        garageList = new ArrayList<>();
        garageList.add(garage);
    }

    @Test
    public void testCreateGarage() throws Exception {
        Mockito.when(this.garageService.createGarage(1,garage)).thenReturn(garage);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/owner/1/garage").contentType(MediaType.APPLICATION_JSON).content(jsonToString(garage)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetGaragesByOwnerId() throws Exception {
        Mockito.when(this.garageService.getGaragesByOwnerId(1)).thenReturn(garageList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/owner/1/garages").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testAssignCarToGarage() throws Exception {
        Mockito.when(this.garageService.assignCarToGarage(1,1,1)).thenReturn(garage);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/owner/1/garages/1/cars/1/assign").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testGetGarageById() throws Exception {
        Mockito.when(this.garageService.getGarageById(1,1)).thenReturn(garage);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/owner/1/garages/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeleteGarage() throws Exception {
        this.garageService.deleteGarage(1,1);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cars/1/garages").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void testGetGaragesCarHasAccessTo() throws Exception {
        Mockito.when(this.garageService.getGaragesCarHasAccessTo(1)).thenReturn(garageList);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/owner/1/garages/1").contentType(MediaType.APPLICATION_JSON))
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
