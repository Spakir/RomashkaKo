package org.example.romashkako;

import jakarta.persistence.EntityNotFoundException;
import org.example.romashkako.dto.ProductSupplyDTO;
import org.example.romashkako.mapper.ProductMapper;
import org.example.romashkako.mapper.ProductSupplyMapper;
import org.example.romashkako.model.Product;
import org.example.romashkako.model.ProductSupply;
import org.example.romashkako.repository.ProductSupplyRepository;
import org.example.romashkako.service.ProductServiceImpl;
import org.example.romashkako.service.ProductSupplyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductSupplyServiceTests {

    @Mock
    private ProductSupplyMapper productSupplyMapper;

    @Mock
    private ProductSupplyRepository productSupplyRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductServiceImpl productService;

    @InjectMocks
    private ProductSupplyServiceImpl productSupplyService;

    @Test
    public void test_createProductSupply() {
        Long id = 1L;
        String productName = "name";
        String productDescription = "description";
        int productPrice = 100;
        int quantity = 100;
        boolean inStock = true;
        String documentName = "documentName";

        ProductSupplyDTO productSupplyDTO = new ProductSupplyDTO(null, documentName, id, quantity);
        Product product = new Product(id, productName, productDescription, productPrice, inStock);
        ProductSupply mappedProductSupply = new ProductSupply(documentName, product, quantity);
        ProductSupply createdProductSupply = new ProductSupply(id, documentName, product, quantity);

        ProductSupplyDTO createdProductSupplyDTO = new ProductSupplyDTO(id, documentName, id, quantity);

        when(productSupplyMapper.toProductSupply(productSupplyDTO, productService, productMapper))
                .thenReturn(mappedProductSupply);
        when(productSupplyRepository.save(mappedProductSupply)).thenReturn(createdProductSupply);
        when(productSupplyMapper.toProductSupplyDTO(createdProductSupply)).thenReturn(createdProductSupplyDTO);

        ProductSupplyDTO result = productSupplyService.createProductSupply(productSupplyDTO);

        assertEquals(createdProductSupplyDTO, result);
        verify(productSupplyMapper, times(1)).toProductSupplyDTO(createdProductSupply);
        verify(productSupplyMapper, times(1)).toProductSupply(productSupplyDTO, productService, productMapper);
        verify(productSupplyRepository, times(1)).save(mappedProductSupply);
    }

    @Test
    public void test_getProductSupplyById_returnExistProductSupplyDTO() {
        Long id = 1L;
        String productName = "name";
        String productDescription = "description";
        int productPrice = 100;
        int quantity = 100;
        boolean inStock = true;
        String documentName = "documentName";

        Product product = new Product(id, productName, productDescription, productPrice, inStock);
        ProductSupply productSupply = new ProductSupply(id, documentName, product, quantity);
        ProductSupplyDTO productSupplyDTO = new ProductSupplyDTO(id, documentName, id, quantity);

        when(productSupplyRepository.findById(id)).thenReturn(Optional.of(productSupply));
        when(productSupplyMapper.toProductSupplyDTO(productSupply)).thenReturn(productSupplyDTO);

        ProductSupplyDTO result = productSupplyService.getProductSupplyById(id);

        assertEquals(productSupplyDTO, result);
        verify(productSupplyMapper, times(1)).toProductSupplyDTO(productSupply);
        verify(productSupplyRepository, times(1)).findById(id);
    }

    @Test
    public void test_getProductSupplyById_throwEntityNotFoundException(){
        Long id = 0L;
        String exceptionMessage = "Поставка товара с данным ID не найдена";
        when(productSupplyRepository.findById(id))
                .thenThrow(new EntityNotFoundException(exceptionMessage));

        RuntimeException exception = assertThrows(EntityNotFoundException.class,
                () -> productSupplyService.getProductSupplyById(id));

        assertEquals(exceptionMessage,exception.getMessage());
        verify(productSupplyRepository,times(1)).findById(id);
    }

    @Test
    public void test_getAllProductSupply_returnListProductSupplyDTO(){
        Long id = 1L;
        String documentName = "document";
        int quantity = 400;

        String productName = "product";
        boolean inStock = true;
        int price = 100;
        String description = "description";

        Product product = new Product(id,productName,description,price,inStock);
        ProductSupply productSupply = new ProductSupply(id,documentName,product,quantity);
        ProductSupplyDTO productSupplyDTO = new ProductSupplyDTO(id,documentName,id,quantity);

        List<ProductSupply> productSupplyList = List.of(productSupply);
        List<ProductSupplyDTO> productSupplyDTOList = List.of(productSupplyDTO);

        when(productSupplyRepository.findAll()).thenReturn(productSupplyList);
        when(productSupplyMapper.toProductSupplyDTO(productSupply)).thenReturn(productSupplyDTO);

        List<ProductSupplyDTO> result = productSupplyService.getAllProductSupplies();
        assertEquals(productSupplyDTOList,result);
        verify(productSupplyRepository,times(1)).findAll();
        verify(productSupplyMapper,times(1)).toProductSupplyDTO(productSupply);
    }

    @Test
    public void test_updateProductSupply_returnProductSupplyDTO(){
        Long id = 1L;
        String documentName = "document";
        int quantity = 400;

        String productName = "product";
        boolean inStock = true;
        int price = 100;
        String description = "description";

        Product product = new Product(id,productName,description,price,inStock);
        ProductSupply existProductSupply = new ProductSupply(id,"null",product,1);
        ProductSupplyDTO productSupplyDTO = new ProductSupplyDTO(null,documentName,id,quantity);
        ProductSupply updatedProductSupply = new ProductSupply(id,documentName,product,quantity);
        ProductSupplyDTO updatedProductSupplyDTO = new ProductSupplyDTO(id,documentName,id,quantity);

        when(productSupplyRepository.findById(id)).thenReturn(Optional.of(existProductSupply));
        when(productSupplyRepository.save(updatedProductSupply)).thenReturn(updatedProductSupply);
        when(productSupplyMapper.toProductSupplyDTO(updatedProductSupply)).thenReturn(updatedProductSupplyDTO);

        ProductSupplyDTO result = productSupplyService.updateProductSupply(id,productSupplyDTO);

        assertEquals(updatedProductSupplyDTO,result);
        verify(productSupplyRepository,times(1)).findById(id);
        verify(productSupplyMapper,times(1))
                .updateProductSuppleFromDTO(productSupplyDTO,existProductSupply);
        verify(productSupplyMapper,times(1)).toProductSupplyDTO(updatedProductSupply);
    }

    @Test
    public void test_deleteProductSupplyById(){
        Long id = 1L;
        String documentName = "document";
        int quantity = 400;

        String productName = "product";
        boolean inStock = true;
        int price = 100;
        String description = "description";

        Product product = new Product(id,productName,description,price,inStock);
        ProductSupply existProductSupply = new ProductSupply(id,documentName,product,quantity);

        when(productSupplyRepository.findById(id)).thenReturn(Optional.of(existProductSupply));

        productSupplyService.deleteProductSupplyById(id);

        verify(productSupplyRepository,times(1)).findById(id);
    }
}
