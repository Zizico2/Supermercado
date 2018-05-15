package Supermarket;

import java.util.ArrayList;
import java.util.List;

public class CartClass implements Cart{

    private int capacity;
    private List<Item> items;

    CartClass(int capacity) {
        this.capacity = capacity;
        items = new ArrayList<>();
        }

    public int getCapacity() {
        return capacity;
    }

    public void addItem(Item item){
        items.add(item);
        capacity -= item.getVolume();
    }

    public void removeItem(Item item){
        items.remove(item);
        capacity += item.getVolume();
    }

    public List<Item> getItems(){
        return items;
    }
}
