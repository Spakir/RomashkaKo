package org.example.romashkako.mapper;

import org.example.romashkako.dto.ProductDTO;
import org.example.romashkako.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toProductDTO(Product product);

    Product toProduct(ProductDTO productDTO);
}
