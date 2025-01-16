package org.example.romashkako.dao;

import org.example.romashkako.model.Product;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class IProductDAO implements ProductDAO {

    private List<Product> products = new ArrayList<>();

    @Override
    public void createProduct(Product product) {
        products.add(product);
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public void updateProduct(Product product) {
        int index = products.indexOf(product);
        if (index >= 0) {
            products.set(index, product);
        }
    }

    @Override
    public boolean deleteProduct(String name) {
        return products.removeIf(product -> product.getName().equals(name));
    }
}
