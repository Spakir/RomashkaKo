package org.example.romashkako.mapper;

import org.example.romashkako.dto.ProductSupplyDTO;
import org.example.romashkako.model.Product;
import org.example.romashkako.model.ProductSupply;
import org.example.romashkako.service.ProductService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductSupplyMapper {

    @Mapping(target = "product", source = "productId",qualifiedByName = "getProduct")
    ProductSupply toProductSupply(ProductSupplyDTO productSupplyDTO,
                                  @Context ProductService productService,
                                  @Context ProductMapper productMapper);

    @Mapping(target = "productId", source = "product.id")
    ProductSupplyDTO toProductSupplyDTO(ProductSupply productSupply);

    @Named("getProduct")
    default Product getProduct(Long id, @Context ProductService productService,@Context ProductMapper productMapper){
        return productMapper.toProduct(productService.getProductById(id));
    }
}
