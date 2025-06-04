package org.example.service;

import org.example.domain.Product;

import java.util.List;

public interface IProductService {
    void addProduct(Product product);
    List<Product> listProducts();
}
