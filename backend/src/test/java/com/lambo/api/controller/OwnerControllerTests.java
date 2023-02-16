package com.lambo.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambo.api.controllers.OwnerController;
import com.lambo.api.dto.OwnerDto;
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
public class OwnerControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @MockBean
    private OwnerDto owner;

    @MockBean
    private List<OwnerDto> ownerList;

    @InjectMocks
    private OwnerController ownerController;

    @Before
    public void setUp() throws Exception {
        ownerService = Mockito.mock(OwnerService.class);
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
        owner = new OwnerDto();
        owner.setId(1);
        owner.setName("First");
        owner.setSurname("Surname");

        ownerList = new ArrayList<>();
        ownerList.add(owner);
    }

    @Test
    public void testOwners() throws Exception {
        Mockito.when(this.ownerService.getOwnerByID(1)).thenReturn(owner);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/owner/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testOwnerDetail() throws Exception {
        Mockito.when(this.ownerService.getAllOwners()).thenReturn(ownerList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/owner").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testCreateOwner() throws Exception {
        Mockito.when(this.ownerService.createOwner(owner)).thenReturn(owner);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/owner/create").contentType(MediaType.APPLICATION_JSON).content(jsonToString(owner)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdateOwner() throws Exception {
        Mockito.when(this.ownerService.updateOwner(owner,1)).thenReturn(owner);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/owner/1/update").contentType(MediaType.APPLICATION_JSON).content(jsonToString(owner)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeleteOwner() throws Exception {
        Mockito.when(this.ownerService.updateOwner(owner,1)).thenReturn(owner);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/owner/1/delete").contentType(MediaType.APPLICATION_JSON))
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
