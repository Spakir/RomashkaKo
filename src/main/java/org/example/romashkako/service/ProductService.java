package org.example.romashkako.service;

import jakarta.validation.Valid;
import org.example.romashkako.dto.ProductDTO;
import org.example.romashkako.dto.ProductFiltersDTO;

import java.util.List;

public interface ProductService {

    ProductDTO createProduct(@Valid ProductDTO productDTO);

    List<ProductDTO> getAllProducts(@Valid ProductFiltersDTO filters);

    ProductDTO getProductById(Long id);

    ProductDTO updateProduct(Long id, @Valid ProductDTO productDTO);

    void deleteProductById(Long id);

}
