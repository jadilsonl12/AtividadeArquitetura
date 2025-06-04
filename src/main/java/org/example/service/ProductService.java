package org.example.service;

import org.example.domain.Product;
import org.example.repository.ProductRepository;
import org.example.repository.Repository;

import java.util.List;

public class ProductService implements IProductService {
    private final Repository<Product> productRepository;

    public ProductService(Repository<Product> productRepository) {
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
}