package org.example.romashkako.service;

import jakarta.validation.Valid;
import org.example.romashkako.dao.ProductDAO;
import org.example.romashkako.dto.ProductDTO;
import org.example.romashkako.exception.ProductDoesNotDeletedException;
import org.example.romashkako.exception.ProductNotFoundException;
import org.example.romashkako.mapper.ProductMapper;
import org.example.romashkako.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductMapper productMapper;

    public ProductDTO createProduct(@Valid ProductDTO productDTO) {
        Product product = productMapper.toProduct(productDTO);
        Product createdProduct = productDAO.createProduct(product);
        ProductDTO createdProductDTO = productMapper.toProductDTO(createdProduct);

        return createdProductDTO;
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> products = productDAO.getAllProducts().stream()
                .map(productMapper::toProductDTO)
                .collect(Collectors.toList());

        return products;
    }

    public ProductDTO getProductById(Long id){
        Product product = productDAO.getProductById(id).orElseThrow(() ->
                new ProductNotFoundException("Товар с данным id не был найден"));
        ProductDTO productDTO = productMapper.toProductDTO(product);

        return productDTO;
    }

    public ProductDTO updateProduct(Long id,@Valid ProductDTO productDTO) {
        if(!productDAO.getProductById(id).isPresent()){
            throw new ProductNotFoundException("Товар с данным id не был найден");
        }

        productDTO.setId(id);
        Product product = productMapper.toProduct(productDTO);
        Product updatedProduct = productDAO.updateProduct(product);
        ProductDTO updatedProductDTO = productMapper.toProductDTO(updatedProduct);

        return updatedProductDTO;
    }

    public void deleteProduct(Long id) {
        if(!productDAO.deleteProduct(id)){
            throw new ProductDoesNotDeletedException("Не удалось удалить товар");
        }
    }
}
