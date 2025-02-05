package org.example.romashkako.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.example.romashkako.dto.ProductDTO;
import org.example.romashkako.dto.ProductFiltersDTO;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Override
    public ProductDTO createProduct(@Valid ProductDTO productDTO) {
        Product product = mapToProduct(productDTO);
        Product savedProduct = productRepository.save(product);
        return mapToProductDTO(savedProduct);
    }

    @Override
    public List<ProductDTO> getAllProducts(ProductFiltersDTO filters) {
        List<ProductDTO> products = null;

        int offset = filters.getPage() * filters.getLimit();

        products = productRepository.findByFilters(filters.getFilterName(),
                        filters.getMinPrice(),
                        filters.getMaxPrice(),
                        filters.getInStock(),
                        filters.getSortType(),
                        filters.getSortDirection(),
                        filters.getLimit(),
                        offset)
                .stream()
                .map(this::mapToProductDTO)
                .collect(Collectors.toList());

        return products;
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = getExistProductOrThrow(id);
        return mapToProductDTO(product);
    }

    @Override
    public ProductDTO updateProduct(Long id, @Valid ProductDTO productDTO) {
        Product existProduct = getExistProductOrThrow(id);
        productMapper.updateProductFromDTO(productDTO,existProduct);
        Product updatedProduct = productRepository.save(existProduct);
        return mapToProductDTO(updatedProduct);
    }

    @Override
    public void deleteProductById(Long id) {
        getExistProductOrThrow(id);
        productRepository.deleteById(id);
    }

    private Product getExistProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Товар с данным ID не был найден"));
    }

    private Product mapToProduct(ProductDTO productDTO){
        return productMapper.toProduct(productDTO);
    }

    private ProductDTO mapToProductDTO(Product product){
        return productMapper.toProductDTO(product);
    }
}
