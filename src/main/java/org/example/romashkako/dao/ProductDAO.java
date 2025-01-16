package org.example.romashkako.dao;

import org.example.romashkako.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDAO {

    void createProduct(Product product);

    Optional<Product> getProductByName(String name);

    List<Product> getAllProducts();

    void updateProduct(Product product);

    void deleteProduct(String name);



}
