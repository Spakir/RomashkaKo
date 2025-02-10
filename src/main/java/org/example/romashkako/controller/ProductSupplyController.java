package org.example.romashkako.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import org.example.romashkako.dto.ProductSupplyDTO;
import org.example.romashkako.service.ProductSupplyService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supply/")
@Validated
public class ProductSupplyController {

    private final ProductSupplyService productSupplyService;

    public ProductSupplyController(ProductSupplyService productSupplyService) {
        this.productSupplyService = productSupplyService;
    }

    @PostMapping("/")
    @ApiOperation("Создание новой поставки товара")
    public ProductSupplyDTO createProductSupply(@ApiParam(value = "Данные добавляемой поставки товара")
                                                @RequestBody @Valid ProductSupplyDTO productSupplyDTO) {
        return productSupplyService.createProductSupply(productSupplyDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Получение поставки товара по ID")
    public ProductSupplyDTO getProductSupplyOById(@ApiParam(value = "id поставки товара для получения")
                                                  @PathVariable(name = "id") Long id) {
        return productSupplyService.getProductSupplyById(id);
    }

    @GetMapping("/all")
    @ApiOperation("Получение всех поставок товара")
    public List<ProductSupplyDTO> getAllProductsSupplies() {
        return productSupplyService.getAllProductSupplies();
    }

    @PutMapping("/{id}")
    @ApiOperation("Обновление поставки товара по ID")
    public ProductSupplyDTO updateProductSupply(@ApiParam(value = "ID поставки товара,которую надо обновить")
                                                @PathVariable(name = "id") Long id,
                                                @ApiParam(value = "DTO поставки товара,которую надо обновить")
                                                @RequestBody @Valid ProductSupplyDTO productSupplyDTO) {
        return productSupplyService.updateProductSupply(id, productSupplyDTO);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Удаление поставки товара по ID")
    public void deleteProductSupply(@ApiParam(value = "ID поставки товара,которую надо удалить")
                                    @PathVariable(name = "id") Long id) {
        productSupplyService.deleteProductSupplyById(id);
    }
}
