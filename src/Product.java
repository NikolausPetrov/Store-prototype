public class Product {
    private int id;
    private String name;
    private double price;
    private String manufacturer;
    private double rating;

    public Product(int id, String name, double price, String manufacturer, double rating) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
        this.rating = rating;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", manufacturer='" + manufacturer + '\'' +
                ", rating=" + rating +
                '}';
    }
}