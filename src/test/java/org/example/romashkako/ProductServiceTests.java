package org.example.romashkako;

import org.example.romashkako.dto.ProductDTO;
import org.example.romashkako.exception.ProductNotFoundException;
import org.example.romashkako.mapper.ProductMapper;
import org.example.romashkako.model.Product;
import org.example.romashkako.repository.ProductRepository;
import org.example.romashkako.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    ProductDTO correctProductDTO = new ProductDTO(
            1L,
            "Product",
            "product description",
            1,
            true
    );

    Product correctProduct = new Product(
            1L,
            "Product",
            "product description",
            1,
            true
    );

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        when(productMapper.toProduct(correctProductDTO)).thenReturn(correctProduct);

        productService.createProduct(correctProductDTO);

        verify(productRepository, times(1)).save(correctProduct);
    }

    @Test
    void testGetAllProducts_returnProductDTOList() {
        List<ProductDTO> productDTOList = new ArrayList<>(List.of(correctProductDTO));
        List<Product> productList = new ArrayList<>(List.of(correctProduct));

        when(productMapper.toProductDTO(correctProduct)).thenReturn(correctProductDTO);
        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDTO> result = productService.getAllProducts();

        verify(productRepository, times(1)).findAll();
        assertEquals(productDTOList, result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetProductById_existProductId_returnProductDTO() {
        Long existProductId = correctProductDTO.getId();

        when(productMapper.toProductDTO(correctProduct)).thenReturn(correctProductDTO);
        when(productRepository.findById(existProductId)).thenReturn(Optional.of(correctProduct));

        ProductDTO result = productService.getProductById(existProductId);

        verify(productMapper, times(1)).toProductDTO(correctProduct);
        verify(productRepository, times(1)).findById(existProductId);
        assertEquals(correctProductDTO, result);
    }

    @Test
    void testGetProductById_notExistProductId_returnProductNotExistsException() {
        String exceptionMessage = "Товар с данным id не был найден";
        Long notExistProductId = 0L;

        when(productRepository.findById(notExistProductId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(notExistProductId));

        verify(productRepository, times(1)).findById(notExistProductId);
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void testUpdateProduct_existProduct() {
        Long existProductId = correctProductDTO.getId();

        when(productMapper.toProduct(correctProductDTO)).thenReturn(correctProduct);
        when(productRepository.findById(existProductId)).thenReturn(Optional.of(correctProduct));

        assertDoesNotThrow(() -> productService.updateProduct(existProductId, correctProductDTO));

        verify(productRepository, times(1)).save(correctProduct);
        verify(productRepository, times(1)).findById(existProductId);
        verify(productMapper, times(1)).toProduct(correctProductDTO);
    }

    @Test
    void testUpdateProduct_notExistProduct_returnProductDTO() {
        String exceptionMessage = "Товар не был найден";
        Long notExistsId = 0L;
        ProductDTO notExistProductDTO = new ProductDTO(
                notExistsId,
                "name",
                "description",
                0,
                false
        );

        when(productRepository.findById(notExistsId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProductNotFoundException.class, () ->
                productService.updateProduct(notExistsId, notExistProductDTO));

        verify(productRepository, times(1)).findById(notExistsId);
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void testDeleteProduct_existProduct() {
        Long id = correctProductDTO.getId();

        assertDoesNotThrow(() -> productService.deleteProduct(id));

        verify(productRepository, times(1)).deleteById(id);
    }
}
