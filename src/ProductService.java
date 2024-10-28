import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private List<Product> products;

    public ProductService() {
        this.products = new ArrayList<>();
    }

    // Добавление нового продукта
    public void addProduct(Product product) {
        products.add(product);
    }

    // Получение всех продуктов
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    // Фильтрация продуктов по диапазону цен
    public List<Product> filterByPriceRange(double minPrice, double maxPrice) {
        // Принцип избегания магических чисел: minPrice и maxPrice передаются как параметры, а не используются как фиксированные значения
        return products.stream()
                .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    // Фильтрация продуктов по ключевому слову
    public List<Product> filterByKeyword(String keyword) {
        // Принцип DRY: повторяющийся код фильтрации продуктов по условию вынесен в отдельные методы
        return products.stream()
                .filter(product -> product.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Фильтрация продуктов по производителю
    public List<Product> filterByManufacturer(String manufacturer) {
        // Принцип DRY: повторяющийся код фильтрации продуктов по условию вынесен в отдельные методы
        return products.stream()
                .filter(product -> product.getManufacturer().equalsIgnoreCase(manufacturer))
                .collect(Collectors.toList());
    }

    // Получение продукта по его идентификатору
    public Product getProductById(int id) {
        // Принцип единственной ответственности (SRP): метод отвечает только за получение продукта по идентификатору
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        // Принцип открытости/закрытости (OCP): метод toString может быть переопределен для изменения формата вывода без изменения существующего кода
        return "ProductService{" +
                "products=" + products +
                '}';
    }
}