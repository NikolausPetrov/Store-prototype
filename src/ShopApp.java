import java.util.List;
import java.util.Scanner;

public class ShopApp {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        OrderService orderService = new OrderService();
        RecommendationService recommendationService = new RecommendationService(productService, orderService);

        // Добавление демо-продуктов
        productService.addProduct(new Product(1, "Ноутбук MagicBook X16", 700.0, "HONOR", 4.8));
        productService.addProduct(new Product(2, "Ноутбук IdeaPad 1 15AMN7", 375.0, "Lenovo", 4.5));
        productService.addProduct(new Product(3, "Ноутбук Macbook Pro", 1850.0, "Apple", 5.0));
        productService.addProduct(new Product(4, "Ноутбук MateBook D 14", 600.0, "HUAWEI", 4.8));
        productService.addProduct(new Product(5, "Смартфон Galaxy Z Flip4 8/256 ГБ", 445.0, "Samsung", 4.4));
        productService.addProduct(new Product(6, "Смартфон iPhone 15 Pro Max 256 ГБ, Dual: nano SIM + eSIM", 1430.0, "Apple", 5.0));
        productService.addProduct(new Product(7, "Смартфон Galaxy S24 Ultra 12/256 ГБ, Dual: nano SIM + eSIM", 1150.0, "Samsung", 5.0));
        productService.addProduct(new Product(8, "Смартфон iPhone 15 128 GB, Pink", 740.0, "Samsung", 4.9));
        productService.addProduct(new Product(9, "Смартфон Galaxy S24 Plus 12/256 ГБ, Dual: nano SIM + eSIM", 685.0, "Samsung", 4.9));
        productService.addProduct(new Product(9, "Смартфон 12 Pro 12/256Gb", 1500.0, "Xiaomi", 4.9));
        productService.addProduct(new Product(9, "Смартфон Magic V3 12/512 ГБ Tundra Green, Global, Nano SlM+eSlM", 1550.0, "Honor", 4.9));
        productService.addProduct(new Product(9, "Смартфон Redmi Note 3 32GB 3/32 ГБ", 100.0, "Xiaomi", 3.0));
        productService.addProduct(new Product(10, "Наушники EarPods Lightning", 25.0, "Apple", 4.3));
        productService.addProduct(new Product(11, "Беспроводные наушники WH-1000XM4", 230.0, "Sony", 4.8));
        productService.addProduct(new Product(12, "Беспроводные наушники Monitor II ANC black с шумоподавлением", 400.0, "Marshall", 4.7));
        productService.addProduct(new Product(13, "Беспроводные наушники AirPods 3 Lightning Charging Case", 195.0, "Apple", 4.9));
        productService.addProduct(new Product(14, "Беспроводные наушники Monitor II ANC black с шумоподавлением", 400.0, "Marshall", 4.7));
        productService.addProduct(new Product(15, "Наушники DT 1770 Pro", 1000.0, "Beyerdynamic", 5.0));
        productService.addProduct(new Product(16, "Беспроводные наушники AirPods Pro 2nd Generation", 240.0, "Apple", 4.7));
        productService.addProduct(new Product(17, "Беспроводные наушники Buds 4 Pro", 150.0, "Xiaomi", 4.8));
        productService.addProduct(new Product(18, "Наушники Quantum 100 Black", 150.0, "JBL", 4.7));
        productService.addProduct(new Product(19, "Беспроводные наушники Galaxy Buds 3 Pro", 300.0, "Samsung", 4.7));
        productService.addProduct(new Product(20, "Беспроводные наушники Airpods Max", 650.0, "Apple", 4.6));
        productService.addProduct(new Product(21, "Планшет iPad 10.9 (2022) 64GB Wi-F", 400.0, "Apple", 4.9));
        productService.addProduct(new Product(22, "Планшет K10 PRO, CN, 4/128 ГБ, 2K, Wi-Fi, Android 12", 125.0, "Lenovo", 4.8));
        productService.addProduct(new Product(23, "Планшет Redmi Pad SE (2023), RU, 6/128 ГБ, Wi-Fi, Android 13", 130.0, "Xiaomi", 4.6));

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите ваше имя:");
        String userName = scanner.nextLine();

        User user = new User(1, userName);

        while (true) {
            System.out.println("\nДобро пожаловать в магазин, " + userName + "! Выберите действие:");
            System.out.println("1. Посмотреть все продукты");
            System.out.println("2. Найти продукты по цене");
            System.out.println("3. Найти продукты по производителю");
            System.out.println("4. Добавить продукт в корзину");
            System.out.println("5. Оформить заказ");
            System.out.println("6. Рекомендации для вас");
            System.out.println("7. Популярные продукты");
            System.out.println("8. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Доступные продукты:");
                    List<Product> allProducts = productService.getAllProducts();
                    allProducts.forEach(ShopApp::printProductDetails);
                    break;

                case 2:
                    System.out.println("Введите минимальную цену:");
                    double minPrice = scanner.nextDouble();
                    System.out.println("Введите максимальную цену:");
                    double maxPrice = scanner.nextDouble();
                    List<Product> productsByPrice = productService.filterByPriceRange(minPrice, maxPrice);
                    System.out.println("Продукты в указанном диапазоне цен:");
                    productsByPrice.forEach(ShopApp::printProductDetails);
                    break;

                case 3:
                    System.out.println("Введите производителя:");
                    String manufacturer = scanner.nextLine();
                    List<Product> productsByManufacturer = productService.filterByManufacturer(manufacturer);
                    System.out.println("Продукты от " + manufacturer + ":");
                    productsByManufacturer.forEach(ShopApp::printProductDetails);
                    break;

                case 4:
                    System.out.println("Введите ID продукта для добавления в корзину:");
                    int productId = scanner.nextInt();
                    Product product = productService.getProductById(productId);
                    if (product != null) {
                        user.addToCart(product);
                        System.out.println("Товар добавлен в корзину: " + product.getName());
                    } else {
                        System.out.println("Продукт не найден!");
                    }
                    break;

                case 5:
                    if (user.getCart().isEmpty()) {
                        System.out.println("Ваша корзина пуста!");
                    } else {
                        orderService.createOrder(user, user.getCart());
                        user.getCart().clear();
                        System.out.println("Ваш заказ оформлен!");
                    }
                    break;

                case 6:
                    System.out.println("Рекомендации для вас:");
                    List<Product> recommendations = recommendationService.recommendProductsForUser(user);
                    recommendations.forEach(ShopApp::printProductDetails);
                    break;

                case 7:
                    System.out.println("Популярные продукты:");
                    List<Product> popularProducts = recommendationService.getPopularProducts();
                    popularProducts.forEach(ShopApp::printProductDetails);
                    break;

                case 8:
                    System.out.println("Спасибо за посещение магазина!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте ещё раз.");
                    break;
            }
        }
    }

    private static void printProductDetails(Product product) {
        System.out.println("===========================================");
        System.out.println("ID: " + product.getId());
        System.out.println("Название: " + product.getName());
        System.out.println("Цена: $" + product.getPrice());
        System.out.println("Производитель: " + product.getManufacturer());
        System.out.println("Рейтинг: " + product.getRating());
        System.out.println("===========================================\n");
    }
}