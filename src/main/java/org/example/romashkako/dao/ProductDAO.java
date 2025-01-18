package org.example.romashkako.dao;

import org.example.romashkako.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDAO {

    Product createProduct(Product product);

    Optional<Product> getProductById(Long id);

    List<Product> getAllProducts();

    Product updateProduct(Product product);

    boolean deleteProduct(Long id);
}
