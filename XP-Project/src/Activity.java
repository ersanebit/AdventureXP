public class Activity {

    private int id;
    private String name;
    private int price;
    private int age;
    private double height;

    public Activity(int id, String name, int price, int age, double height) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.age = age;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

}
