package Supermarket;

import java.util.*;

public class SupermarketClass implements Supermarket {

    private Map<String, Cart> carts;
    private Map<String, Item> items;


    public SupermarketClass() {
        carts = new HashMap<>();
        items = new HashMap<>();

    }

    @Override
    public void registerCart(String ID, int capacity) {
            carts.put(ID, new CartClass(capacity));

    }

    @Override
    public void registerItem(String ID, int price, int volume) {
            items.put(ID, new ItemClass(ID, price, volume));
    }

    @Override
    public void addToCart(String itemID, String cartID) {
        carts.get(cartID).addItem(items.get(itemID));
    }


    @Override
    public void removeFromCart(String itemID, String cartID) {
        carts.get(cartID).removeItem(items.get(itemID));
    }

    @Override
    public String listItems(String ID) {
        String msg = "";

        for (Item item: carts.get(ID).getItems()) {
            msg += item.toString();
        }
        return msg;
    }

    @Override
    public int pay(String ID) {
        int price = 0;
        Cart cart = carts.get(ID);

        for (Item item: cart.getItems()) {
            price += item.getPrice();
        }
        cart.getItems().clear();
        return price;
    }
}
