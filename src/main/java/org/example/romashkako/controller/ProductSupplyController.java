package org.example.romashkako.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.romashkako.dto.ProductSupplyDTO;
import org.example.romashkako.model.ErrorResponse;
import org.example.romashkako.service.ProductSupplyService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supply/")
@Tag(name = "ProductSupply API", description = "Контроллер для управления поставками товаров")
@Validated
public class ProductSupplyController {

    private final ProductSupplyService productSupplyService;

    public ProductSupplyController(ProductSupplyService productSupplyService) {
        this.productSupplyService = productSupplyService;
    }

    @Operation(summary = "Создание поставки товара")
    @ApiResponse(
            responseCode = "200",
            description = "Поставка товара успешно создалась",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductSupplyDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Поля поставки товара не прошли валидацию",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping("/")
    public ProductSupplyDTO createProductSupply(@RequestBody @Valid ProductSupplyDTO productSupplyDTO) {
        return productSupplyService.createProductSupply(productSupplyDTO);
    }

    @Operation(summary = "Получение поставки товара по ID")
    @ApiResponse(
            responseCode = "200",
            description = "Поставка товара успешно найдена",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductSupplyDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Поставка товара не была найдена",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/{id}")
    public ProductSupplyDTO getProductSupplyOById(@PathVariable(name = "id") Long id) {
        return productSupplyService.getProductSupplyById(id);
    }

    @Operation(summary = "Получение списка поставок товаров")
    @ApiResponse(
            responseCode = "200",
            description = "Список товаров успешно найден",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductSupplyDTO.class)))
    )
    @GetMapping("/all")
    public List<ProductSupplyDTO> getAllProductsSupplies() {
        return productSupplyService.getAllProductSupplies();
    }

    @Operation(summary = "Обновление поставки товара по ID")
    @ApiResponse(
            responseCode = "200",
            description = "Поставка товара успешно обновлена",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductSupplyDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Поставка товара не была найдена",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Поля обновляемой поставки товара не прошли валидацию",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    @PutMapping("/{id}")
    public ProductSupplyDTO updateProductSupply(@PathVariable(name = "id") Long id,
                                                @RequestBody @Valid ProductSupplyDTO productSupplyDTO) {
        return productSupplyService.updateProductSupply(id, productSupplyDTO);
    }

    @Operation(summary = "Удаление поставки товара по ID")
    @ApiResponse(
            responseCode = "200",
            description = "Поставка товара успешно удалена"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Поставка товара не была найдена",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    @DeleteMapping("{id}")
    public void deleteProductSupply(@PathVariable(name = "id") Long id) {
        productSupplyService.deleteProductSupplyById(id);
    }
}
