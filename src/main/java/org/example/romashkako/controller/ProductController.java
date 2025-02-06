package org.example.romashkako.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.example.romashkako.dto.ProductDTO;
import org.example.romashkako.dto.ProductFiltersDTO;
import org.example.romashkako.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product/")
@Api(value = "Product API")
@Validated
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    @ApiOperation(value = "Создание нового товара")
    public ProductDTO createProduct(@ApiParam(value = "данные добавляемого товара")
                                    @RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @GetMapping("/all")
    @ApiOperation(value = "Получение всех товаров")
    public List<ProductDTO> getAllProducts(
            @ApiParam(value = "Фильтры для поиска товаров")
            @ModelAttribute ProductFiltersDTO filters) {
        return productService.getAllProducts(filters);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получение товара по ID")
    public ProductDTO getProductById(@ApiParam(value = "ID искомого товара")
                                     @PathVariable(value = "id") Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Обновление товара с указанным ID")
    public ProductDTO updateProduct(@ApiParam(value = "ID товара,который надо обновить")
                                    @PathVariable(name = "id") Long id,
                                    @ApiParam(value = "DTO товара,который надо обновить")
                                    @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление товара с указанным ID")
    public void deleteProduct(@ApiParam(value = "ID товара,который надо удалить")
                              @PathVariable("id") Long id) {
        productService.deleteProductById(id);
    }
}
