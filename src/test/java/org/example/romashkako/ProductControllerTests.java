package org.example.romashkako;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    public void testGetProductById_returnJSONProductDTO() throws Exception {
        Long existProductId = 1L;
        ProductDTO existProductDto = new ProductDTO(
                existProductId,
                "Product",
                "Product description",
                10,
                true
        );
        String jsonResponse = objectMapper.writeValueAsString(existProductDto);

        when(productService.getProductById(existProductId)).thenReturn(existProductDto);

        mockMvc.perform(get("/api/product/{id}",existProductId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));

        verify(productService,times(1)).getProductById(1L);
    }

    @Test
    public void testUpdateProduct_returnJSONUpdatedProductDTO() throws Exception {
        Long existProductId = 1L;
        ProductDTO productDTOForUpdate = new ProductDTO(
                "new Product",
                "new product description",
                2,
                true
        );
        ProductDTO updatedProductDTO = new ProductDTO(
                existProductId,
                productDTOForUpdate.getName(),
                productDTOForUpdate.getDescription(),
                productDTOForUpdate.getPrice(),
                productDTOForUpdate.isInStock()
        );

        String jsonRequestContent = objectMapper.writeValueAsString(productDTOForUpdate);
        String jsonResultContent = objectMapper.writeValueAsString(updatedProductDTO);

        when(productService.updateProduct(existProductId,productDTOForUpdate)).thenReturn(updatedProductDTO);

        mockMvc.perform(put("/api/product/{id}",existProductId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestContent))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResultContent));

        verify(productService,times(1)).updateProduct(existProductId,productDTOForUpdate);
    }

    @Test
    public void testDeleteProductById_returnStatusOK() throws Exception {
        Long existProductId = 1L;

        mockMvc.perform(delete("/api/product/{id}",existProductId))
                .andExpect(status().isOk());

        verify(productService,times(1)).deleteProductById(existProductId);
    }
}
