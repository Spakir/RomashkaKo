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
        if (getProductByName(product.getName()).isPresent()) {
            throw new IllegalArgumentException("Товар с данным названием уже существует");
        }
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
            products.add(index, product);
        } else {
            throw new IllegalArgumentException("Товара с данным названием не существует");
        }
    }

    @Override
    public void deleteProduct(String name) {
        boolean isDeleted = products.removeIf(product -> product.getName().equals(name));

        if (!isDeleted) {
            throw new RuntimeException("Не удалось удалить товар");
        }
    }
}
