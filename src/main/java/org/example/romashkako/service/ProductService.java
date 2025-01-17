package org.example.romashkako.service;

import jakarta.validation.Valid;
import org.example.romashkako.dao.ProductDAO;
import org.example.romashkako.dto.ProductDTO;
import org.example.romashkako.exception.ProductAlreadyExistsException;
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

    public void createProduct(@Valid ProductDTO productDTO) {
        String name = productDTO.getName();

        if(productDAO.getProductByName(name).isPresent()){
            throw new ProductAlreadyExistsException("Товар уже существует");
        }

        Product product = productMapper.toProduct(productDTO);
        productDAO.createProduct(product);
    }

    public ProductDTO getProductByName(String name) {
        Product product = productDAO.getProductByName(name).orElseThrow( () ->
                new ProductNotFoundException("Товар не был найден"));
        ProductDTO productDTO = productMapper.toProductDTO(product);

        return productDTO;
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> products = productDAO.getAllProducts().stream()
                .map(productMapper::toProductDTO)
                .collect(Collectors.toList());

        return products;
    }

    public void updateProduct(@Valid ProductDTO productDTO) {
        String name = productDTO.getName();

        if(!productDAO.getProductByName(name).isPresent()){
            throw new ProductNotFoundException("Товар не был найден");
        }

        Product product = productMapper.toProduct(productDTO);
        productDAO.updateProduct(product);
    }

    public void deleteProduct(String name) {
        if(!productDAO.deleteProduct(name)){
            throw new ProductDoesNotDeletedException("Не удалось удалить товар");
        }
    }
}
