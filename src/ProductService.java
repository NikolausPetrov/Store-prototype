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
        return products.stream()
                .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    // Фильтрация продуктов по ключевому слову
    public List<Product> filterByKeyword(String keyword) {
        return products.stream()
                .filter(product -> product.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Фильтрация продуктов по производителю
    public List<Product> filterByManufacturer(String manufacturer) {
        return products.stream()
                .filter(product -> product.getManufacturer().equalsIgnoreCase(manufacturer))
                .collect(Collectors.toList());
    }

    // Получение продукта по его идентификатору
    public Product getProductById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "ProductService{" +
                "products=" + products +
                '}';
    }
}