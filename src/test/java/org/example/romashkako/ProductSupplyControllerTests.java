package org.example.romashkako;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.romashkako.controller.ProductSupplyController;
import org.example.romashkako.dto.ProductDTO;
import org.example.romashkako.dto.ProductSupplyDTO;
import org.example.romashkako.service.ProductSupplyService;
import org.example.romashkako.service.ProductSupplyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProductSupplyControllerTests {

    @Mock
    private ProductSupplyServiceImpl productSupplyService;

    @InjectMocks
    private ProductSupplyController productSupplyController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(productSupplyController).build();
    }

    @Test
    public void test_createProductSupply() throws Exception {
        Long id = 1L;
        String documentName = "document";
        int quantity = 400;

        ProductSupplyDTO productSupplyDTO = new ProductSupplyDTO(null, documentName, id, quantity);
        ProductSupplyDTO createdProductSupply = new ProductSupplyDTO(id, documentName, id, 400);
        String jsonRequest = objectMapper.writeValueAsString(productSupplyDTO);
        String jsonResponse = objectMapper.writeValueAsString(createdProductSupply);

        when(productSupplyService.createProductSupply(productSupplyDTO)).thenReturn(createdProductSupply);

        mockMvc.perform(post("/api/supply/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));

        verify(productSupplyService, times(1)).createProductSupply(productSupplyDTO);
    }

    @Test
    public void test_getProductSupplyById() throws Exception {
        Long id = 1L;
        String documentName = "document";
        int quantity = 100;

        ProductSupplyDTO productSupplyDTO = new ProductSupplyDTO(id, documentName, id, quantity);
        String jsonResponse = objectMapper.writeValueAsString(productSupplyDTO);

        when(productSupplyService.getProductSupplyById(id)).thenReturn(productSupplyDTO);

        mockMvc.perform(get("/api/supply/{id}", id))
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));

        verify(productSupplyService, times(1)).getProductSupplyById(id);
    }

    @Test
    public void test_getAllProductsSupplies() throws Exception {
        Long id = 1L;
        String documentName = "document";
        int quantity = 100;

        ProductSupplyDTO productSupplyDTO = new ProductSupplyDTO(id, documentName, id, quantity);
        List<ProductSupplyDTO> list = List.of(productSupplyDTO);
        String jsonResponse = objectMapper.writeValueAsString(list);

        when(productSupplyService.getAllProductSupplies()).thenReturn(list);

        mockMvc.perform(get("/api/supply/all"))
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));

        verify(productSupplyService,times(1)).getAllProductSupplies();
    }

    @Test
    public void test_updateProductSupply() throws Exception {
        Long id = 1L;
        String documentName = "document";
        int quantity = 100;

        ProductSupplyDTO updateProductSupplyDTO = new ProductSupplyDTO(null,documentName,id,quantity);
        ProductSupplyDTO updatedProductSupplyDTOWithId = new ProductSupplyDTO(id,documentName,id,quantity);
        String jsonRequest = objectMapper.writeValueAsString(updateProductSupplyDTO);
        String jsonResponse = objectMapper.writeValueAsString(updatedProductSupplyDTOWithId);

        when(productSupplyService.updateProductSupply(id,updateProductSupplyDTO))
                .thenReturn(updatedProductSupplyDTOWithId);

        mockMvc.perform(put("/api/supply/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));

        verify(productSupplyService,times(1)).updateProductSupply(id,updateProductSupplyDTO);
    }

    @Test
    public void test_deleteProductSupply(){

    }
}
