package Supermarket;

public class ItemClass implements Item{

    private String ID;
    private int volume;
    private int price;

    public ItemClass(String ID, int volume, int price) {
        this.ID = ID;
        this.volume = volume;
        this.price = price;

        }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public String toString(){
        return ID + " " + price + "\n";
    }
}
