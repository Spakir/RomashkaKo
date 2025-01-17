package org.example.romashkako.service;

import jakarta.validation.Valid;
import org.example.romashkako.dto.ProductDTO;
import org.example.romashkako.exception.ProductNotFoundException;
import org.example.romashkako.mapper.ProductMapper;
import org.example.romashkako.model.Product;
import org.example.romashkako.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public void createProduct(@Valid ProductDTO productDTO) {
        Product product = productMapper.toProduct(productDTO);
        productRepository.save(product);
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> products = productRepository.findAll().stream()
                .map(productMapper::toProductDTO)
                .collect(Collectors.toList());

        return products;
    }

    public ProductDTO getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("Товар с данным id не был найден"));
        ProductDTO productDTO = productMapper.toProductDTO(product);

        return productDTO;
    }

    public void updateProduct(Long id,@Valid ProductDTO productDTO) {
        if(!productRepository.findById(id).isPresent()){
            throw new ProductNotFoundException("Товар не был найден");
        }

        productDTO.setId(id);
        Product product = productMapper.toProduct(productDTO);

        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
