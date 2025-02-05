package org.example.romashkako.mapper;

import org.example.romashkako.dto.ProductDTO;
import org.example.romashkako.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toProductDTO(Product product);

    Product toProduct(ProductDTO productDTO);

    @Mapping(target = "id", ignore = true)
    void updateProductFromDTO(ProductDTO productDTO, @MappingTarget Product product);
}
