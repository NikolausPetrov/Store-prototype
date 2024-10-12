import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderService {
    private List<Order> orders;

    public OrderService() {
        this.orders = new ArrayList<>();
    }

    // Получение всех заказов
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    // Создание нового заказа
    public Order createOrder(User user, List<Product> products) {
        Order newOrder = new Order(user, products, Order.OrderStatus.PENDING);
        orders.add(newOrder);
        return newOrder;
    }

    // Получение заказа по его идентификатору
    public Order getOrderById(int orderId) {
        return orders.stream()
                .filter(order -> order.getOrderId() == orderId)
                .findFirst()
                .orElse(null);
    }

    // Обновление статуса заказа
    public boolean updateOrderStatus(int orderId, Order.OrderStatus newStatus) {
        Order order = getOrderById(orderId);
        if (order != null) {
            order.setStatus(newStatus);
            return true;
        }
        return false;
    }

    // Отмена заказа
    public boolean cancelOrder(int orderId) {
        return updateOrderStatus(orderId, Order.OrderStatus.CANCELED);
    }

    // Получение всех заказов пользователя
    public List<Order> getUserOrders(User user) {
        return orders.stream()
                .filter(order -> order.getUser().equals(user))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "OrderService{" +
                "orders=" + orders +
                '}';
    }
}