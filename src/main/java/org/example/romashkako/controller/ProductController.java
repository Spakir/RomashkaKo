package org.example.romashkako.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.example.romashkako.dto.ProductDTO;
import org.example.romashkako.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product/")
@Api(value = "Product API")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    @ApiOperation(value = "Создание нового товара")
    public void createProduct(@ApiParam(value = "данные добавляемого товара") @RequestBody ProductDTO productDTO) {
        productService.createProduct(productDTO);
    }

    @GetMapping("/all")
    @ApiOperation(value = "Получение всех товаров")
    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return products;
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable(value = "id") Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable(name = "id") Long id, @RequestBody ProductDTO productDTO) {
        productService.updateProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
    }
}
