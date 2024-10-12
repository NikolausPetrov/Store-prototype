import java.util.*;
import java.util.stream.Collectors;

public class RecommendationService {
    private ProductService productService;
    private OrderService orderService;

    public RecommendationService(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    // Рекомендации продуктов для пользователя
    public List<Product> recommendProductsForUser(User user) {
        List<Order> userOrders = orderService.getUserOrders(user);

        Set<Product> purchasedProducts = userOrders.stream()
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.toSet());

        return productService.getAllProducts().stream()
                .filter(product -> !purchasedProducts.contains(product))
                .limit(5)
                .collect(Collectors.toList());
    }

    // Получение популярных продуктов
    public List<Product> getPopularProducts() {
        Map<Product, Long> productFrequency = orderService.getAllOrders().stream()
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));

        return productFrequency.entrySet().stream()
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}