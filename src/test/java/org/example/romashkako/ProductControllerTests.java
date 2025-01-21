package org.example.romashkako;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.romashkako.controller.ProductController;
import org.example.romashkako.dto.ProductDTO;
import org.example.romashkako.service.ProductService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTests {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateProduct_returnJSONCreatedProductDTO() throws Exception {
        ProductDTO requestProductDTO = new ProductDTO(
                "Product",
                "Product description",
                1,
                true
        );

        ProductDTO createdProductDTO = new ProductDTO(
                1L,
                requestProductDTO.getName(),
                requestProductDTO.getDescription(),
                requestProductDTO.getPrice(),
                requestProductDTO.isInStock()
        );

        String jsonRequestContent = objectMapper.writeValueAsString(requestProductDTO);

        String jsonResponseContent = objectMapper.writeValueAsString(createdProductDTO);

        when(productService.createProduct(requestProductDTO)).thenReturn(createdProductDTO);

        mockMvc.perform(post("/api/product/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestContent))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponseContent));

        verify(productService,times(1)).createProduct(requestProductDTO);
    }


}
