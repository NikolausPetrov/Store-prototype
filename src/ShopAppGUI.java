import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShopAppGUI extends JFrame {
    private ProductService productService;
    private OrderService orderService;
    private RecommendationService recommendationService;
    private User user;
    private DefaultListModel<String> cartListModel;

    public ShopAppGUI() {
        productService = new ProductService();
        orderService = new OrderService();
        recommendationService = new RecommendationService(productService, orderService);
        cartListModel = new DefaultListModel<>();

        initDemoProducts();

        setTitle("Shop App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Главная", createHomePanel());
        tabbedPane.addTab("Продукты", createProductsPanel());
        tabbedPane.addTab("Корзина", createCartPanel());
        tabbedPane.addTab("Рекомендации", createRecommendationPanel());

        add(tabbedPane);
    }

    // Добавление демо-продуктов
    private void initDemoProducts() {
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
    }

    // Создание панели для главной вкладки
    private JPanel createHomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Добро пожаловать! Введите ваше имя:", SwingConstants.CENTER);
        panel.add(welcomeLabel, BorderLayout.NORTH);

        JTextField nameField = new JTextField();
        panel.add(nameField, BorderLayout.CENTER);

        JButton submitButton = new JButton("Войти");
        panel.add(submitButton, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> {
            String userName = nameField.getText().trim();
            if (!userName.isEmpty()) {
                user = new User(1, userName);
                welcomeLabel.setText("Привет, " + user.getName() + "!");
                nameField.setVisible(false);
                submitButton.setVisible(false);
                updateCartModel();
            } else {
                JOptionPane.showMessageDialog(panel, "Пожалуйста, введите ваше имя.");
            }
        });

        return panel;
    }

    // Создание панели для вкладки продуктов
    private JPanel createProductsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(productPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new GridLayout(4, 2));
        JTextField keywordField = new JTextField();
        JTextField minPriceField = new JTextField();
        JTextField maxPriceField = new JTextField();
        JTextField manufacturerField = new JTextField();

        filterPanel.add(new JLabel("Ключевое слово:"));
        filterPanel.add(keywordField);
        filterPanel.add(new JLabel("Мин. цена:"));
        filterPanel.add(minPriceField);
        filterPanel.add(new JLabel("Макс. цена:"));
        filterPanel.add(maxPriceField);
        filterPanel.add(new JLabel("Производитель:"));
        filterPanel.add(manufacturerField);

        JButton filterButton = new JButton("Фильтровать");
        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(filterButton, BorderLayout.SOUTH);

        ActionListener refreshList = e -> {
            List<Product> filteredProducts = productService.getAllProducts().stream()
                    .filter(product -> (keywordField.getText().trim().isEmpty() || product.getName().toLowerCase().contains(keywordField.getText().trim().toLowerCase())) &&
                            (minPriceField.getText().isEmpty() || product.getPrice() >= Double.parseDouble(minPriceField.getText())) &&
                            (maxPriceField.getText().isEmpty() || product.getPrice() <= Double.parseDouble(maxPriceField.getText())) &&
                            (manufacturerField.getText().trim().isEmpty() || product.getManufacturer().toLowerCase().contains(manufacturerField.getText().trim().toLowerCase())))
                    .collect(Collectors.toList());

            productPanel.removeAll();
            for (Product product : filteredProducts) {
                JPanel productItemPanel = new JPanel(new GridLayout(3, 2));
                productItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                productItemPanel.add(new JLabel("Название: " + product.getName()));
                productItemPanel.add(new JLabel("Цена: $" + product.getPrice()));
                productItemPanel.add(new JLabel("Производитель: " + product.getManufacturer()));
                productItemPanel.add(new JLabel("Рейтинг: " + product.getRating()));

                JButton addButton = new JButton("Добавить в корзину");
                addButton.addActionListener(event -> {
                    if (user != null) {
                        user.getCart().add(product);
                        JOptionPane.showMessageDialog(panel, "Товар добавлен в корзину");
                        updateCartModel();
                    } else {
                        JOptionPane.showMessageDialog(panel, "Пожалуйста, введите ваше имя, чтобы продолжить.");
                    }
                });
                productItemPanel.add(addButton);

                productPanel.add(productItemPanel);
            }
            productPanel.revalidate();
            productPanel.repaint();
        };

        filterButton.addActionListener(refreshList);
        refreshList.actionPerformed(null);

        return panel;
    }

    // Создание панели для вкладки корзины
    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JList<String> cartList = new JList<>(cartListModel);
        JScrollPane scrollPane = new JScrollPane(cartList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton checkoutButton = new JButton("Оформить заказ");
        checkoutButton.addActionListener(e -> {
            if (user == null || user.getCart().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Ваша корзина пуста или вы не ввели ваше имя!");
                return;
            }
            orderService.createOrder(user, new ArrayList<>(user.getCart()));
            user.getCart().clear();
            JOptionPane.showMessageDialog(panel, "Ваш заказ оформлен!");
            updateCartModel();
        });

        panel.add(checkoutButton, BorderLayout.SOUTH);
        return panel;
    }

    // Обновление корзины
    private void updateCartModel() {
        cartListModel.clear();
        if (user != null) {
            for (Product product : user.getCart()) {
                cartListModel.addElement(product.getName() + " - $" + product.getPrice());
            }
        }
    }

    // Создание панели для вкладки рекомендаций
    private JPanel createRecommendationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel recommendationPanel = new JPanel();
        recommendationPanel.setLayout(new BoxLayout(recommendationPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(recommendationPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Обновить рекомендации");
        refreshButton.addActionListener(e -> {
            if (user == null) {
                JOptionPane.showMessageDialog(panel, "Пожалуйста, введите ваше имя, чтобы продолжить.");
                return;
            }
            List<Product> recommendations = recommendationService.recommendProductsForUser(user);
            recommendationPanel.removeAll();
            for (Product product : recommendations) {
                JPanel productItemPanel = new JPanel(new GridLayout(3, 2));
                productItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                productItemPanel.add(new JLabel("Название: " + product.getName()));
                productItemPanel.add(new JLabel("Цена: $" + product.getPrice()));
                productItemPanel.add(new JLabel("Производитель: " + product.getManufacturer()));
                productItemPanel.add(new JLabel("Рейтинг: " + product.getRating()));

                JButton addButton = new JButton("Добавить в корзину");
                addButton.addActionListener(event -> {
                    if (user != null) {
                        user.getCart().add(product);
                        JOptionPane.showMessageDialog(panel, "Товар добавлен в корзину");
                        updateCartModel();
                    } else {
                        JOptionPane.showMessageDialog(panel, "Пожалуйста, введите ваше имя, чтобы продолжить.");
                    }
                });
                productItemPanel.add(addButton);

                recommendationPanel.add(productItemPanel);
            }
            recommendationPanel.revalidate();
            recommendationPanel.repaint();
        });

        panel.add(refreshButton, BorderLayout.SOUTH);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ShopAppGUI shopAppGUI = new ShopAppGUI();
            shopAppGUI.setVisible(true);
        });
    }
}