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

        // Принцип DRY (Don't Repeat Yourself): Использование методов для получения всех продуктов и фильтрации
        return productService.getAllProducts().stream()
                .filter(product -> !purchasedProducts.contains(product))
                // Избегание магических чисел
                .limit(RecommendationConstants.RECOMMENDATION_LIMIT)
                .collect(Collectors.toList());
    }

    // Получение популярных продуктов
    public List<Product> getPopularProducts() {
        Map<Product, Long> productFrequency = orderService.getAllOrders().stream()
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));

        // Принцип единственной ответственности (Single Responsibility Principle): Метод выполняет только одну задачу - получение популярных продуктов
        return productFrequency.entrySet().stream()
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                // Избегание магических чисел: использование константы вместо "5"
                .limit(RecommendationConstants.POPULAR_PRODUCT_LIMIT)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}

// Константы для избежания магических чисел
class RecommendationConstants {
    public static final int RECOMMENDATION_LIMIT = 5;
    public static final int POPULAR_PRODUCT_LIMIT = 5;
}