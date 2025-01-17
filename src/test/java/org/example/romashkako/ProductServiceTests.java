package org.example.romashkako;

import org.example.romashkako.dao.ProductDAO;
import org.example.romashkako.dto.ProductDTO;
import org.example.romashkako.exception.ProductAlreadyExistsException;
import org.example.romashkako.exception.ProductDoesNotDeletedException;
import org.example.romashkako.exception.ProductNotFoundException;
import org.example.romashkako.mapper.ProductMapper;
import org.example.romashkako.model.Product;
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
    private ProductDAO productDAO;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    ProductDTO correctProductDTO = new ProductDTO(
            "Product",
            "product description",
            1,
            true
    );

    Product correctProduct = new Product(
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
    void testCreateProduct_ProductIsNotPresent() {
        String name = correctProductDTO.getName();

        when(productDAO.getProductByName(name)).thenReturn(Optional.empty());
        when(productMapper.toProduct(correctProductDTO)).thenReturn(correctProduct);

        productService.createProduct(correctProductDTO);

        verify(productDAO,times(1)).getProductByName(name);
        verify(productDAO, times(1)).createProduct(correctProduct);
    }

    @Test
    void testCreateProduct_ProductIsPresent() {
        String name = correctProductDTO.getName();
        String exceptionMessage = "Товар уже существует";

        when(productDAO.getProductByName(name)).thenReturn(Optional.of(correctProduct));

        Exception exception = assertThrows(ProductAlreadyExistsException.class,()->
                productService.createProduct(correctProductDTO));

        verify(productDAO,times(1)).getProductByName(name);
        assertEquals(exceptionMessage,exception.getMessage());
    }

    @Test
    void testGetProductByName_validName_returnProductDTO() {
        String validName = correctProductDTO.getName();

        when(productDAO.getProductByName(validName)).thenReturn(Optional.of(correctProduct));
        when(productMapper.toProductDTO(correctProduct)).thenReturn(correctProductDTO);

        ProductDTO result = productService.getProductByName(validName);

        assertEquals(correctProductDTO, result);
        verify(productDAO, times(1)).getProductByName(validName);
    }

    @Test
    void testGetProductByName_invalidName_returnProductDTONotFoundException() {
        String invalidName = "invalid name";
        String exceptionMessage = "Товар с таким названием не найден";

        when(productDAO.getProductByName(invalidName)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProductNotFoundException.class, () ->
                productService.getProductByName(invalidName));

        assertEquals(exceptionMessage, exception.getMessage());
        verify(productDAO, times(1)).getProductByName(invalidName);
    }

    @Test
    void testGetAllProducts_returnProductDTOList() {
        List<ProductDTO> productDTOList = new ArrayList<>(List.of(correctProductDTO));
        List<Product> productList = new ArrayList<>(List.of(correctProduct));

        when(productMapper.toProductDTO(correctProduct)).thenReturn(correctProductDTO);
        when(productDAO.getAllProducts()).thenReturn(productList);

        List<ProductDTO> result = productService.getAllProducts();

        verify(productDAO, times(1)).getAllProducts();
        assertEquals(productDTOList, result);
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateProduct_existProduct() {
        String validName = correctProductDTO.getName();

        when(productMapper.toProduct(correctProductDTO)).thenReturn(correctProduct);
        when(productDAO.getProductByName(validName)).thenReturn(Optional.of(correctProduct));

        assertDoesNotThrow(() -> productService.updateProduct(correctProductDTO));

        verify(productDAO, times(1)).updateProduct(correctProduct);
        verify(productDAO, times(1)).getProductByName(validName);
        verify(productMapper, times(1)).toProduct(correctProductDTO);
    }

    @Test
    void testUpdateProduct_notExistProduct_returnProductDTO() {
        String invalidName = "invalidName";
        String exceptionMessage = "Товар не был найден";

        ProductDTO incorrectDTO = new ProductDTO(
                invalidName,
                "",
                0,
                false
        );

        when(productDAO.getProductByName(invalidName)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProductNotFoundException.class, () ->
                productService.updateProduct(incorrectDTO));

        verify(productDAO, times(1)).getProductByName(invalidName);
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void testDeleteProduct_existProduct(){
        String name = correctProductDTO.getName();

        when(productDAO.deleteProduct(name)).thenReturn(true);

        assertDoesNotThrow(() -> productService.deleteProduct(name));

        verify(productDAO,times(1)).deleteProduct(name);
    }

    @Test
    void testDeleteProduct_notExistProduct(){
        String name= correctProductDTO.getName();
        String exceptionMessage = "Не удалось удалить товар";

        when(productDAO.deleteProduct(name)).thenReturn(false);

        Exception exception = assertThrows(ProductDoesNotDeletedException.class,() ->
                productService.deleteProduct(name));

        verify(productDAO,times(1)).deleteProduct(name);
        assertEquals(exceptionMessage,exception.getMessage());
    }
}
