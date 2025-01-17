package org.example.romashkako.dao;

import org.example.romashkako.model.Product;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class IProductDAO implements ProductDAO {

    private List<Product> products = new ArrayList<>();

    private AtomicLong idCounter = new AtomicLong(0);

    @Override
    public void createProduct(Product product) {
        product.setId(idCounter.incrementAndGet());
        products.add(product);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
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
            System.out.println(products);
        }
    }

    @Override
    public boolean deleteProduct(Long id) {
        return products.removeIf(product -> product.getId().equals(id));
    }
}
