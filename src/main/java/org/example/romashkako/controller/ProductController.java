package org.example.romashkako.controller;

import org.example.romashkako.dto.ProductDTO;
import org.example.romashkako.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/product/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public void createProduct(@RequestBody ProductDTO productDTO){
        System.out.println(productDTO);
        productService.createProduct(productDTO);
    }

    @GetMapping("/all")
    public List<ProductDTO> getAllProducts(){
        List<ProductDTO> products =  productService.getAllProducts();
        System.out.println(products);
        return products;
    }

    @GetMapping("/{name}")
    public ProductDTO getProductByName(@PathVariable(value = "name") String name){
        return productService.getProductByName(name);
    }

    @PutMapping("/")
    public void updateProduct(@RequestBody ProductDTO productDTO){
        productService.updateProduct(productDTO);
    }

    @DeleteMapping("/{name}")
    public void deleteProduct(@PathVariable("name") String name){
        productService.deleteProduct(name);
    }
}
