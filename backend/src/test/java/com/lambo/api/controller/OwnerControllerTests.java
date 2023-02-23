package com.lambo.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambo.api.controllers.OwnerController;
import com.lambo.api.dto.GarageDto;
import com.lambo.api.dto.OwnerDto;
import com.lambo.api.models.Owner;
import com.lambo.api.repository.OwnerRepository;
import com.lambo.api.service.OwnerService;
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

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc(addFilters = false)
public class OwnerControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OwnerService ownerService;

    private OwnerDto owner;

    @Autowired
    private OwnerController ownerController;
    @Autowired
    private OwnerRepository ownerRepository;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
        owner = new OwnerDto();
        owner.setId(1);
        owner.setName("First");
        owner.setSurname("Surname");

        owner = ownerService.createOwner(owner);
    }

    @Test
    public void testOwners() throws Exception {
        final List<OwnerDto> allOwners = ownerService.getAllOwners();
        final ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/api/owner")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
                    List<OwnerDto> responseBody = objectMapper.readValue(result.getResponse().getContentAsByteArray(), new TypeReference<List<OwnerDto>>() {});
                    Assertions.assertThat(allOwners).isEqualTo(responseBody);
                })
                .andDo(print());
    }

    @Test
    public void testOwnerDetail() throws Exception {
        final OwnerDto ownerDto = ownerService.getOwnerByID(owner.getId());
        final ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/api/owner/" + owner.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
                    OwnerDto responseBody = objectMapper.readValue(result.getResponse().getContentAsByteArray(), OwnerDto.class);
                    System.out.println(ownerDto.getId() + "\n" + responseBody.getId());
                    Assertions.assertThat(ownerDto).isEqualTo(responseBody);
                })
                .andDo(print());
    }

    @Test
    public void testCreateOwner() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final OwnerDto newOwner = new OwnerDto(2, "Second", "Surname");

        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "/api/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOwner)))
                .andExpect(result -> {
                    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
                    OwnerDto responseBody = objectMapper.readValue(result.getResponse().getContentAsByteArray(), OwnerDto.class);
                    Assertions.assertThat(newOwner).isEqualTo(responseBody);
                })
                .andDo(print());
    }

    @Test
    public void testUpdateOwner() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.PUT, "/api/owner/" + owner.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(owner)))
                .andExpect(result -> {
                    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
                    OwnerDto responseBody = objectMapper.readValue(result.getResponse().getContentAsByteArray(), OwnerDto.class);
                    Assertions.assertThat(owner).isEqualTo(responseBody);
                })
                .andDo(print());
    }

    @Test
    public void testDeleteOwner() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.DELETE, "/api/owner/" + owner.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
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
