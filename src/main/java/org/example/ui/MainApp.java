package org.example.ui;

import org.example.domain.Cart;
import org.example.domain.Order;
import org.example.domain.Product;
import org.example.filters.InventoryCheckFilter;
import org.example.filters.ShippingCalculationFilter;
import org.example.repository.CartRepository;
import org.example.repository.OrderRepository;
import org.example.repository.ProductRepository;
import org.example.service.CartService;
import org.example.service.OrderService;
import org.example.service.ProductService;

import java.util.Scanner;


public class MainApp {
    public static void main(String[] args) {
        // Repositórios
        ProductRepository productRepository = new ProductRepository();
        CartRepository cartRepository = new CartRepository();
        OrderRepository orderRepository = new OrderRepository();

        // Serviços
        ProductService productService = new ProductService(productRepository);
        CartService cartService = new CartService(cartRepository);
        OrderService orderService = new OrderService(orderRepository);

        // Filtros
        InventoryCheckFilter inventoryCheckFilter = new InventoryCheckFilter();
        ShippingCalculationFilter shippingCalculationFilter = new ShippingCalculationFilter();

        // Adiciona produtos ao repositório
        productService.addProduct(new Product(1, "Celular", 1500.00, 10));
        productService.addProduct(new Product(2, "Notebook", 4500.00, 5));
        productService.addProduct(new Product(3, "Tablet", 800.00, 15));

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Listar Produtos");
            System.out.println("2. Adicionar Produto ao Carrinho");
            System.out.println("3. Finalizar Pedido");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            int escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    // Lista todos os produtos disponíveis
                    for (Product product : productService.listProducts()) {
                        System.out.println("Produto: " + product.getName() +
                                ", Preço: R$" + product.getPrice() +
                                ", Estoque: " + product.getStock());
                    }
                    break;

                case 2:
                    // Adiciona um produto ao carrinho
                    System.out.print("Informe o ID do produto: ");
                    int productId = scanner.nextInt();
                    Product product = productRepository.findById(productId);
                    if (product != null) {
                        cartService.addProductToCart(1, product);
                        System.out.println("Produto adicionado ao carrinho.");
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                    break;

                case 3:
                    // Finaliza o pedido
                    Cart cart = cartService.getCartById(1);
                    if (cart != null && !cart.getProducts().isEmpty()) {
                        try {
                            // Cria o pedido e aplica os filtros
                            Order order = orderService.createOrder(cart);
                            inventoryCheckFilter.execute(order);
                            shippingCalculationFilter.execute(order);

                            System.out.println("Pedido criado com sucesso!");
                            System.out.println("ID do Pedido: " + order.getId());
                            System.out.println("Total: R$" + order.getTotal());
                        } catch (RuntimeException e) {
                            System.out.println("Erro ao criar pedido: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Carrinho vazio.");
                    }
                    break;

                case 4:
                    // Finaliza o programa
                    running = false;
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }
}
