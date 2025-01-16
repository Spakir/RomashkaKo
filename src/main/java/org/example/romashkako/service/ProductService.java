package org.example.romashkako.service;

import jakarta.validation.Valid;
import org.example.romashkako.dao.ProductDAO;
import org.example.romashkako.dto.ProductDTO;
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
        Product product = productMapper.toProduct(productDTO);
        productDAO.createProduct(product);
    }

    public ProductDTO getProductByName(String name) {
        Product product = productDAO.getProductByName(name).orElseThrow(IllegalAccessError::new);
        System.out.println("Retrieved product: " + product); // Логируем извлекаемый продукт
        ProductDTO productDTO = productMapper.toProductDTO(
                product
        );

        System.out.println("Retrieved productDto: " + productDTO);
        return productDTO;
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> products = productDAO.getAllProducts().stream()
                .map(productMapper::toProductDTO)
                .collect(Collectors.toList());
        return products;
    }

    public void updateProduct(@Valid ProductDTO productDTO) {
        Product product = productMapper.toProduct(productDTO);
        System.out.println(product);
        productDAO.updateProduct(product);
    }

    public void deleteProduct(String name) {
        productDAO.deleteProduct(name);
    }
}
