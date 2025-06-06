package org.example.service;

import org.example.domain.Product;
import org.example.repository.ProductRepository;
import org.example.repository.Repository;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findByName(String name) {
        for (Product product : productRepository.findAll()) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }
}