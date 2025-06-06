package org.example.repository;

import org.example.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements Repository<Product> {
    private final List<Product> products = new ArrayList<>();

    @Override
    public void save(Product product) {
        products.add(product);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    @Override
    public Product findById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteById(int id) {
        products.removeIf(product -> product.getId() == id);
    }

    // Método adicional para buscar por nome
    public Product findByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
