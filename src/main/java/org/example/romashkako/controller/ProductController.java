package org.example.romashkako.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.romashkako.dto.ProductDTO;
import org.example.romashkako.dto.ProductFiltersDTO;
import org.example.romashkako.model.ErrorResponse;
import org.example.romashkako.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product/")
@Tag(name = "Product API", description = "Контроллер для управления товарами")
@Validated
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Создание нового товара")
    @ApiResponse(
            responseCode = "200",
            description = "Товар создан",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Поля создаваемого товара не прошли валидацию",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping("/")
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @Operation(summary = "Получение всех товаров")
    @ApiResponse(
            responseCode = "200",
            description = "Список товаров успешно найден",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Фильтры не прошли валидацию",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/all")
    public List<ProductDTO> getAllProducts(@ModelAttribute @Valid ProductFiltersDTO filters) {
        return productService.getAllProducts(filters);
    }

    @Operation(summary = "Получение товара по ID")
    @ApiResponse(
            responseCode = "200",
            description = "Товар успешно найден",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Товар не был найден",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable(value = "id") Long id) {
        return productService.getProductById(id);
    }

    @Operation(summary = "Обновление товара с указанным ID")
    @ApiResponse(
            responseCode = "200",
            description = "Товар успешно обновлён",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Товар не был найден",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Поля обновляемого товара не прошли валидацию",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable(name = "id") Long id,
                                    @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @Operation(summary = "Удаление товара с указанным ID")
    @ApiResponse(
            responseCode = "200",
            description = "Товар успешно удалён"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Товар не был найден",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
    }
}
