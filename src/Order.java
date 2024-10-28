import java.util.List;

public class Order {
    private static int orderCounter = 0; // Избегание магических чисел: переменная для уникального идентификатора
    private int orderId;
    private User user;
    private List<Product> products;
    private OrderStatus status;

    public enum OrderStatus {
        PENDING,
        SHIPPED,
        DELIVERED,
        CANCELED
    }

    public Order(User user, List<Product> products, OrderStatus status) {
        this.orderId = ++orderCounter; // Уникальный идентификатор для каждого заказа
        this.user = user;
        this.products = products;
        this.status = status;
    }

    // Геттеры и сеттеры для полей класса
    public int getOrderId() {
        return orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    // Методы для добавления и удаления продуктов из заказа
    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", user=" + user.getName() +
                ", products=" + products +
                ", status=" + status +
                '}';
    }
}