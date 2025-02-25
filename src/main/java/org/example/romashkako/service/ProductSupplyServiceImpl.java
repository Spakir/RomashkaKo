package org.example.romashkako.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.romashkako.dto.ProductSupplyDTO;
import org.example.romashkako.mapper.ProductMapper;
import org.example.romashkako.mapper.ProductSupplyMapper;
import org.example.romashkako.model.ProductSupply;
import org.example.romashkako.repository.ProductSupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSupplyServiceImpl implements ProductSupplyService {

    private final ProductSupplyRepository productSupplyRepository;

    private final ProductSupplyMapper productSupplyMapper;

    private final ProductService productService;

    private final ProductMapper productMapper;

    @Autowired
    public ProductSupplyServiceImpl(ProductSupplyRepository productSupplyRepository,
                                    ProductSupplyMapper productSupplyMapper,
                                    ProductService productService,
                                    ProductMapper productMapper) {
        this.productSupplyMapper = productSupplyMapper;
        this.productSupplyRepository = productSupplyRepository;
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @Override
    @CacheEvict(value = "productsSupplies", allEntries = true)
    public ProductSupplyDTO createProductSupply(ProductSupplyDTO productSupplyDTO) {
        ProductSupply productSupply = mapToProductSupply(productSupplyDTO);
        ProductSupply createdProductSupply = productSupplyRepository.save(productSupply);
        return mapToProductSupplyDTO(createdProductSupply);
    }

    @Override
    @Cacheable(value = "productSupply", key = "#id")
    public ProductSupplyDTO getProductSupplyById(Long id) {
        ProductSupply productSupply = getExistProductSupplyOrThrow(id);
        return mapToProductSupplyDTO(productSupply);
    }

    @Override
    @Cacheable(value = "productsSupplies",key = "'allProductSupplies'")
    public List<ProductSupplyDTO> getAllProductSupplies() {
        return productSupplyRepository.findAll()
                .stream()
                .map(this::mapToProductSupplyDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Caching(
            evict = @CacheEvict(value = "productsSupplies", allEntries = true),
            put = @CachePut(value = "productSupply", key = "#id")
    )
    public ProductSupplyDTO updateProductSupply(Long id, ProductSupplyDTO productSupplyDTO) {
        ProductSupply existProductSupply = getExistProductSupplyOrThrow(id);
        productSupplyMapper.updateProductSuppleFromDTO(productSupplyDTO, existProductSupply);
        ProductSupply updatedProductSupply = productSupplyRepository.save(existProductSupply);
        return mapToProductSupplyDTO(updatedProductSupply);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "productSupply", key = "#id"),
                    @CacheEvict(value = "productsSupplies", allEntries = true)
            }
    )
    public void deleteProductSupplyById(Long id) {
        getExistProductSupplyOrThrow(id);
        productSupplyRepository.deleteById(id);
    }

    private ProductSupply getExistProductSupplyOrThrow(Long id) {
        return productSupplyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Поставка товара с данным ID не была найдена"));
    }

    private ProductSupply mapToProductSupply(ProductSupplyDTO productSupplyDTO) {
        return productSupplyMapper.toProductSupply(productSupplyDTO, productService, productMapper);
    }

    private ProductSupplyDTO mapToProductSupplyDTO(ProductSupply productSupply) {
        return productSupplyMapper.toProductSupplyDTO(productSupply);
    }
}
