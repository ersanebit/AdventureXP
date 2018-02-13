
public class SweetsQuan {

    private Sweets sweet;
    private int quantity = 1;

    public SweetsQuan(Sweets sweet) {
        this.sweet = sweet;
    }

    public Sweets getSweet() {
        return sweet;
    }

    public String getName() {
        return sweet.getName();
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return sweet.getPrice();
    }

    public int addProduct() {

        quantity++;
        return quantity;
    }

    public int subtractProduct() {

        quantity--;
        return quantity;
    }
}
