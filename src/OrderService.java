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
        // Использование перечисления (ENUM) для статуса заказа - пример избегания магических чисел
        Order newOrder = new Order(user, products, Order.OrderStatus.PENDING);
        orders.add(newOrder);
        return newOrder;
    }

    // Получение заказа по его идентификатору
    public Order getOrderById(int orderId) {
        // Принцип единственной ответственности (Single Responsibility Principle) - метод выполняет только одну задачу: поиск заказа по ID
        return orders.stream()
                .filter(order -> order.getOrderId() == orderId)
                .findFirst()
                .orElse(null);
    }

    // Обновление статуса заказа
    public boolean updateOrderStatus(int orderId, Order.OrderStatus newStatus) {
        // Принцип  открытости/закрытости (Open/Closed Principle) - метод открыт для расширения (можно добавить новые статусы), но закрыт для модификации
        Order order = getOrderById(orderId);
        if (order != null) {
            order.setStatus(newStatus);
            return true;
        }
        return false;
    }

    // Отмена заказа
    public boolean cancelOrder(int orderId) {
        // Принцип DRY - метод использует updateOrderStatus, избегая дублирования кода
        return updateOrderStatus(orderId, Order.OrderStatus.CANCELED);
    }

    // Получение всех заказов пользователя
    public List<Order> getUserOrders(User user) {
        // Принцип подстановки Лисков (Liskov Substitution Principle) - метод корректно работает с объектами User и Order, которые могут быть подтипами
        return orders.stream()
                .filter(order -> order.getUser().equals(user))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        // Принцип разделения интерфейса (Interface Segregation Principle) - класс OrderService не зависит от интерфейсов, которые он не использует
        return "OrderService{" +
                "orders=" + orders +
                '}';
    }
}