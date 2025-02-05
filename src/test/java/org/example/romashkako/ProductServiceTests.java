package org.example.romashkako;

import jakarta.persistence.EntityNotFoundException;
import org.example.romashkako.dto.ProductDTO;
import org.example.romashkako.dto.ProductFiltersDTO;
import org.example.romashkako.mapper.ProductMapper;
import org.example.romashkako.model.Product;
import org.example.romashkako.repository.ProductRepository;
import org.example.romashkako.service.ProductServiceImpl;
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
    private ProductServiceImpl productService;


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

    ProductFiltersDTO correctProductFiltersDTO = new ProductFiltersDTO(
            "Product",
            0,
            1,
            true,
            1,
            0,
            "name",
            "ASC"
    );

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        when(productMapper.toProduct(correctProductDTO)).thenReturn(correctProduct);
        when(productRepository.save(correctProduct)).thenReturn(correctProduct);
        when(productMapper.toProductDTO(correctProduct)).thenReturn(correctProductDTO);

        ProductDTO result = productService.createProduct(correctProductDTO);

        verify(productRepository, times(1)).save(correctProduct);
        verify(productMapper,times(1)).toProductDTO(correctProduct);
        verify(productMapper,times(1)).toProduct(correctProductDTO);
        assertEquals(correctProductDTO,result);
    }

    @Test
    void testGetAllProducts_validProductFiltersDTO_returnProductDTOList() {
        List<ProductDTO> productDTOList = new ArrayList<>(List.of(correctProductDTO));
        List<Product> productList = new ArrayList<>(List.of(correctProduct));

        String filterName = correctProductFiltersDTO.getFilterName();
        Integer minPrice = correctProductFiltersDTO.getMinPrice();
        Integer maxPrice = correctProductFiltersDTO.getMaxPrice();
        Boolean inStock = correctProductFiltersDTO.getInStock();
        int limit = correctProductFiltersDTO.getLimit();
        int page = correctProductFiltersDTO.getPage();
        int offset = page * limit;
        String sortType = correctProductFiltersDTO.getSortType();
        String sortDirection = correctProductFiltersDTO.getSortDirection();

        when(productMapper.toProductDTO(correctProduct)).thenReturn(correctProductDTO);
        when(productRepository.findByFilters(filterName,
                minPrice,
                maxPrice,
                inStock,
                sortType,
                sortDirection,
                limit,
                offset)).thenReturn(productList);

        List<ProductDTO> result = productService.getAllProducts(correctProductFiltersDTO);

        verify(productRepository, times(1)).findByFilters(filterName,
                minPrice,
                maxPrice,
                inStock,
                sortType,
                sortDirection,
                limit,
                offset);
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
        String exceptionMessage = "Товар с данным ID не был найден";
        Long notExistProductId = 0L;

        when(productRepository.findById(notExistProductId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> productService.getProductById(notExistProductId));

        verify(productRepository, times(1)).findById(notExistProductId);
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void testUpdateProduct_existProduct() {
        Long existProductId = correctProduct.getId();

        when(productMapper.toProduct(correctProductDTO)).thenReturn(correctProduct);
        when(productRepository.findById(existProductId)).thenReturn(Optional.of(correctProduct));
        when(productMapper.toProductDTO(correctProduct)).thenReturn(correctProductDTO);
        when(productRepository.save(correctProduct)).thenReturn(correctProduct);

        ProductDTO result = productService.updateProduct(existProductId, correctProductDTO);

        verify(productRepository, times(1)).save(correctProduct);
        verify(productRepository, times(1)).findById(existProductId);
        verify(productMapper, times(1)).toProduct(correctProductDTO);
        assertEquals(correctProductDTO,result);
    }

    @Test
    void testUpdateProduct_notExistProduct_returnProductNotFoundException() {
        String exceptionMessage = "Товар с данным ID не был найден";
        Long notExistsId = 0L;
        ProductDTO notExistProductDTO = new ProductDTO(
                notExistsId,
                "name",
                "description",
                0,
                false
        );

        when(productRepository.findById(notExistsId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                productService.updateProduct(notExistsId, notExistProductDTO));

        verify(productRepository, times(1)).findById(notExistsId);
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void testDeleteProduct_existProduct() {
        Long id = correctProductDTO.getId();
        when(productRepository.findById(id)).thenReturn(Optional.of(correctProduct));

        assertDoesNotThrow(() -> productService.deleteProductById(id));

        verify(productRepository,times(1)).findById(id);
        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteProduct_notExistProduct(){
        Long notExistProductId = 0L;
        String exceptionMessage = "Товар с данным ID не был найден";

        when(productRepository.findById(notExistProductId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                productService.deleteProductById(notExistProductId));

        verify(productRepository,times(1)).findById(notExistProductId);
        assertEquals(exceptionMessage,exception.getMessage());
    }
}
