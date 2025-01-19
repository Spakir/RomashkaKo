package org.example.romashkako.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.example.romashkako.dto.ProductDTO;
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

    public ProductDTO createProduct(@Valid ProductDTO productDTO) {
        Product product = productMapper.toProduct(productDTO);
        Product savedProduct = productRepository.save(product);
        ProductDTO savedProductDTO = productMapper.toProductDTO(savedProduct);

        return savedProductDTO;
    }

    public List<ProductDTO> getAllProducts(String filterName) {
        System.out.println(filterName);
        List<ProductDTO> products = null;
        if (filterName == null) {
            products = productRepository.findAll().stream()
                    .map(productMapper::toProductDTO)
                    .collect(Collectors.toList());
        } else {
            products = productRepository.findByNameContainingIgnoreCase(filterName)
                    .stream()
                    .map(productMapper::toProductDTO)
                    .collect(Collectors.toList());
        }

        return products;
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Товар с данным id не был найден"));
        ProductDTO productDTO = productMapper.toProductDTO(product);

        return productDTO;
    }

    public ProductDTO updateProduct(Long id, @Valid ProductDTO productDTO) {
        if (!productRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException("Товар с данным id не был найден");
        }

        productDTO.setId(id);
        Product product = productMapper.toProduct(productDTO);
        Product updatedProduct = productRepository.save(product);
        ProductDTO updatedProductDTO = productMapper.toProductDTO(updatedProduct);

        return updatedProductDTO;
    }

    public void deleteProductById(Long id) {
        if (!productRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException("Товар с данным ID не был найден");
        }
        productRepository.deleteById(id);
    }
}
