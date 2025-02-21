package org.example.romashkako.service;

import org.example.romashkako.dto.ProductSupplyDTO;
import java.util.List;
public interface ProductSupplyService {

    ProductSupplyDTO createProductSupply(ProductSupplyDTO productSupplyDTO);

    ProductSupplyDTO getProductSupplyById(Long id);

    List<ProductSupplyDTO> getAllProductSupplies();

    ProductSupplyDTO updateProductSupply(Long id,ProductSupplyDTO productSupplyDTO);

    void deleteProductSupplyById(Long id);
}
