package org.example.ui;

import org.example.domain.Cart;
import org.example.domain.Order;
import org.example.domain.Product;
import org.example.filters.Filter;
import org.example.filters.InventoryCheckFilter;
import org.example.filters.ShippingCalculationFilter;
import org.example.infra.RabbitMQClient;
import org.example.repository.ProductRepository;
import org.example.repository.OrderRepository;
import org.example.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainCLI {
    public static void main(String[] args) {
        List<Filter> filters = new ArrayList<>();
        filters.add(new InventoryCheckFilter());
        filters.add(new ShippingCalculationFilter());
        ProductRepository productRepository = new ProductRepository();
        OrderRepository orderRepository = new OrderRepository();
        OrderService orderService = new OrderService(orderRepository, filters);

        // Pré-carregar alguns produtos no repositório
        productRepository.save(new Product(1, "Notebook", 3000.0, 15));
        productRepository.save(new Product(2, "Mouse", 100.0, 5));
        productRepository.save(new Product(3, "Teclado", 150.0, 10));

        Cart cart = new Cart(1);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nMenu Cliente:");
            System.out.println("1. Pesquisar produtos");
            System.out.println("2. Adicionar produto ao carrinho");
            System.out.println("3. Ver carrinho");
            System.out.println("4. Pagar e finalizar pedido");
            System.out.println("5. Acompanhar pedido");
            System.out.println("6. (ADMIN) Monitorar pedidos");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            int escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    // Listar produtos
                    System.out.println("Lista de produtos disponíveis:");
                    productRepository.findAll().forEach(product ->
                            System.out.printf("Produto: %s | Preço: R$ %.2f | Estoque: %d%n",
                                    product.getName(), product.getPrice(), product.getStock())
                    );
                    break;

                case 2:
                    // Adicionar produto ao carrinho
                    System.out.print("Digite o nome do produto: ");
                    scanner.nextLine(); // Consumir quebra de linha
                    String nomeProdutoAdicionar = scanner.nextLine();
                    Product produtoParaAdicionar = productRepository.findByName(nomeProdutoAdicionar);

                    if (produtoParaAdicionar != null) {
                        System.out.print("Digite a quantidade: ");
                        int quantidade = scanner.nextInt();

                        if (quantidade > 0 && quantidade <= produtoParaAdicionar.getStock()) {
                            produtoParaAdicionar.reduceStock(quantidade);
                            cart.addProduct(produtoParaAdicionar, quantidade);
                            System.out.println("Produto adicionado ao carrinho.");
                        } else {
                            System.out.println("Quantidade inválida ou indisponível no estoque.");
                        }
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                    break;

                case 3:
                    // Ver carrinho
                    System.out.println("Carrinho:");
                    cart.getProducts().forEach((product, quantity) ->
                            System.out.printf("Produto: %s | Preço Unitário: R$ %.2f | Quantidade: %d | Subtotal: R$ %.2f%n",
                                    product.getName(), product.getPrice(), quantity, product.getPrice() * quantity)
                    );
                    System.out.printf("Total: R$ %.2f%n", cart.getTotal());
                    break;

                case 4:
                    // Pagar e finalizar pedido
                    System.out.println("Carrinho atual:");
                    cart.getProducts().forEach((product, quantity) ->
                            System.out.printf("Produto: %s | Preço Unitário: R$ %.2f | Quantidade: %d | Subtotal: R$ %.2f%n",
                                    product.getName(), product.getPrice(), quantity, product.getPrice() * quantity)
                    );
                    System.out.printf("Total: R$ %.2f%n", cart.getTotal());
                    System.out.print("Deseja pagar e finalizar o pedido? (1 - Sim, 2 - Não): ");
                    int confirmacao = scanner.nextInt();

                    if (confirmacao == 1) {
                        Order order = orderService.createOrder(cart);
                        orderService.payOrder(order.getId());
                        System.out.println("Pedido finalizado com sucesso!");
                        cart.clear();
                    } else {
                        System.out.println("Pedido cancelado.");
                    }
                    break;

                case 5:
                    // Acompanhar pedido
                    System.out.println("Acompanhando pedidos (consumindo mensagens):");
                    new Thread(() -> RabbitMQClient.consumeMessages()).start();
                    System.out.println("Pressione Enter para voltar ao menu principal.");
                    scanner.nextLine(); // Consumir o Enter para retornar ao menu
                    scanner.nextLine(); // Consumir possível input residual
                    break;


                case 6:
                    // Monitorar pedidos (ADMIN)
                    System.out.println("Pedidos monitorados (ADMIN):");
                    orderRepository.findAll().forEach(order ->
                            System.out.printf("Pedido ID: %d | Status: %s | Total: R$ %.2f%n",
                                    order.getId(), order.getStatus(), order.getTotal())
                    );
                    break;

                case 7:
                    running = false;
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }

        scanner.close();
    }
}
