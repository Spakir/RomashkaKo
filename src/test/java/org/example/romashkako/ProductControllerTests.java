package org.example.romashkako;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.romashkako.controller.ProductController;
import org.example.romashkako.dto.ProductDTO;
import org.example.romashkako.dto.ProductFiltersDTO;
import org.example.romashkako.handler.RestExceptionHandler;
import org.example.romashkako.model.ErrorResponse;
import org.example.romashkako.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

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
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
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

        verify(productService, times(1)).createProduct(requestProductDTO);
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

        mockMvc.perform(get("/api/product/{id}", existProductId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));

        verify(productService, times(1)).getProductById(1L);
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

        when(productService.updateProduct(existProductId, productDTOForUpdate)).thenReturn(updatedProductDTO);

        mockMvc.perform(put("/api/product/{id}", existProductId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestContent))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResultContent));

        verify(productService, times(1)).updateProduct(existProductId, productDTOForUpdate);
    }

    @Test
    public void testDeleteProductById_returnStatusOK() throws Exception {
        Long existProductId = 1L;

        mockMvc.perform(delete("/api/product/{id}", existProductId))
                .andExpect(status().isOk());

        verify(productService, times(1)).deleteProductById(existProductId);
    }

    @Test
    public void testDeleteProductById_returnEntityNotFoundException() throws Exception {
        Long notExistProductId = 0L;

        doThrow(new EntityNotFoundException("Товар с данным ID не найден"))
                .when(productService).deleteProductById(notExistProductId);

        mockMvc.perform(delete("/api/product/{id}",notExistProductId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timeStamp").exists())
                .andExpect(jsonPath("$.errorMessage").value("Товар с данным ID не найден"));

        verify(productService,times(1)).deleteProductById(notExistProductId);
    }

    @Test
    public void testGetAllProducts_validParamsOfFilters_ReturnJSONListProductDTO() throws Exception {
        ProductFiltersDTO productFiltersDTO = new ProductFiltersDTO(
                "товар",
                0,
                10,
                true,
                2,
                0,
                "name",
                "ASC"
        );

        ProductDTO productDTO = new ProductDTO(
                1L,
                "товар первый",
                "товар №1",
                0,
                true
        );
        ProductDTO productDTO1 = new ProductDTO(
                2L,
                "товар второй",
                "товар №2",
                10,
                true
        );

        List<ProductDTO> productDTOList = List.of(productDTO, productDTO1);
        String jsonProductDTOList = objectMapper.writeValueAsString(productDTOList);

        when(productService.getAllProducts(productFiltersDTO)).thenReturn(productDTOList);

        mockMvc.perform(get("/api/product/all")
                        .param("filterName", productFiltersDTO.getFilterName())
                        .param("minPrice", String.valueOf(productFiltersDTO.getMinPrice()))
                        .param("maxPrice", String.valueOf(productFiltersDTO.getMaxPrice()))
                        .param("inStock", String.valueOf(productFiltersDTO.getInStock()))
                        .param("limit", String.valueOf(productFiltersDTO.getLimit()))
                        .param("page", String.valueOf(productFiltersDTO.getPage()))
                        .param("sortType", productFiltersDTO.getSortType())
                        .param("sortDirection", productFiltersDTO.getSortDirection()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonProductDTOList));

        verify(productService, times(1)).getAllProducts(productFiltersDTO);
    }

    @Test
    public void testGetAllProducts_invalidMinPrice_returnStatus400() throws Exception {
        String badParamMinPrice = "-1";

        mockMvc.perform(get("/api/product//all")
                        .param("minPrice", badParamMinPrice))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timeStamp").exists())
                .andExpect(jsonPath("$.errorMessage")
                        .value("Минимальная цена должна быть >= 0"));
    }

    @Test
    public void testGetAllProducts_invalidMaxPrice_returnStatus400() throws Exception {
        String badParamMaxPrice = "-1";

        mockMvc.perform(get("/api/product/all")
                        .param("maxPrice", badParamMaxPrice))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timeStamp").exists())
                .andExpect(jsonPath("$.errorMessage")
                        .value("Максимальная цена должна быть >= 0"));
    }

    @Test
    public void testGetAllProducts_invalidFilterName_returnStatus400() throws Exception {
        String badParamFilterName = "a".repeat(256);

        mockMvc.perform(get("/api/product/all")
                        .param("filterName", badParamFilterName))
                .andExpect(jsonPath("$.timeStamp").exists())
                .andExpect(jsonPath("$.errorMessage")
                        .value("Название товара не должно превышать 255 символов"));
    }

    @Test
    public void testGetAllProducts_invalidLimit_returnStatus400() throws Exception {
        String badParamLimit = "-1";

        mockMvc.perform(get("/api/product/all")
                .param("limit",badParamLimit))
                .andExpect(jsonPath("$.timeStamp").exists())
                .andExpect(jsonPath("$.errorMessage")
                        .value("Лимит возвращаемых товаров должен быть больше 0"));
    }

    @Test
    public void testGetAllProducts_invalidPage_returnStatus400() throws Exception {
        String badParamPage = "-1";

        mockMvc.perform(get("/api/product/all")
                .param("page",badParamPage))
                .andExpect(jsonPath("$.timeStamp").exists())
                .andExpect(jsonPath("$.errorMessage")
                        .value("Страница не может быть отрицательным числом"));
    }

    @Test
    public void testGetAllProducts_invalidSortType_returnStatus400() throws Exception {
        String badParamSortType = "bad";

        mockMvc.perform(get("/api/product/all")
                .param("sortType",badParamSortType))
                .andExpect(jsonPath("$.timeStamp").exists())
                .andExpect(jsonPath("$.errorMessage")
                        .value("Тип сортировки должен быть 'name' или 'price'"));
    }

    @Test
    public void testGetAllProducts_invalidSortDirection_returnStatus400() throws Exception {
        String badParamSortDirection = "bad";

        mockMvc.perform(get("/api/product/all")
                .param("sortDirection",badParamSortDirection))
                .andExpect(jsonPath("$.timeStamp").exists())
                .andExpect(jsonPath("$.errorMessage").value("Направление сортировки должно быть 'ASC' или 'DESC'"));
    }
}
