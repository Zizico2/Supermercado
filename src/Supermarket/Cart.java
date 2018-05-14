package Supermarket;

import java.util.List;

public interface Cart {

    int getCapacity();

    void addItem(Item item);

    void removeItem(Item item);

    List<Item> getItems();
}
