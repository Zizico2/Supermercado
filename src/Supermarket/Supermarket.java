package Supermarket;

public interface Supermarket {

    void registerCart(String ID, int capacity);
    void registerItem(String ID, int price, int volume);
    void addToCart(String itemID, String cartID);
    void removeFromCart(String itemID, String cartID);
    String listItems(String ID);
    int pay(String ID);

}
