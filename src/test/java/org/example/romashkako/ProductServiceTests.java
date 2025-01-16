package org.example.romashkako;

import org.example.romashkako.dao.ProductDAO;
import org.example.romashkako.dto.ProductDTO;
import org.example.romashkako.mapper.ProductMapper;
import org.example.romashkako.model.Product;
import org.example.romashkako.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ProductServiceTests {

    @Mock
    private ProductDAO productDAO;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    ProductDTO currentProductDTO = new ProductDTO(
            "Product",
            "product description",
            1,
            true
    );

    Product currentProduct = new Product(
            "Product",
            "product description",
            1,
            true
    );


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct(){
        when(productMapper.toProduct(currentProductDTO)).thenReturn(currentProduct);

        productService.createProduct(currentProductDTO);

        verify(productDAO,times(1)).createProduct(currentProduct);
    }
}
