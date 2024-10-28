import java.util.ArrayList;
import java.util.List;

// Принцип единственной ответственности (Single Responsibility Principle) - класс User отвечает только за управление данными пользователя и действиями, связанными с пользователем.
public class User {
    private int id;
    private String name;
    private List<Product> cart;
    private List<Order> orderHistory;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.cart = new ArrayList<>();
        this.orderHistory = new ArrayList<>();
    }

    // Геттеры и сеттеры для полей класса
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getCart() {
        return cart;
    }

    public void setCart(List<Product> cart) {
        this.cart = cart;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    // Принцип DRY (Don't Repeat Yourself) - методы addToCart и removeFromCart используют методы коллекции для добавления и удаления продуктов, избегая дублирования кода для управления списком.
    public void addToCart(Product product) {
        cart.add(product);
    }

    public void removeFromCart(Product product) {
        cart.remove(product);
    }

    // Принцип открытости/закрытости (Open/Closed Principle) - метод placeOrder открыт для расширения (можно изменить логику создания заказа, передав другой OrderService), но закрыт для модификации.
    public void placeOrder(OrderService orderService) {
        // Принцип избегания магических чисел: вместо использования магических чисел, мы используем понятные имена переменных и методов, например, new ArrayList<>(cart) явно указывает, что создается новый список на основе корзины.
        Order newOrder = orderService.createOrder(this, new ArrayList<>(cart));
        orderHistory.add(newOrder);
        cart.clear();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cart=" + cart +
                ", orderHistory=" + orderHistory +
                '}';
    }
}