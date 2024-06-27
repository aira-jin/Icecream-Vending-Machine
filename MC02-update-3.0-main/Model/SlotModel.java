package Model;

import java.util.ArrayList;

/**
 * Class representing a slot that can hold an item in Vending Machine.
 */
public class SlotModel {
    private String slotName;
    private int capacity = 10;
    private ArrayList<ItemModel> item;
    private ItemModel designatedItem = null;
    private int nItems = 0;
    private int initialQuantity = 0;

    /**
     * Constructs a slot with the given name and item.
     *
     * @param slotName The name of the slot.
     * @param item     The item to be placed in the slot.
     */
    public SlotModel(String slotName, ItemModel designatedItem) {
        this.slotName = slotName;
        this.designatedItem = designatedItem;
        this.item = new ArrayList<ItemModel>();
    }

    /**
     * Constructs an empty slot (default constructor) .
     */
    public SlotModel(String slotName) {
        this.slotName = slotName;
        this.item = new ArrayList<ItemModel>();
    }

    /**
     * Retrieves the item in the slot.
     *
     * @return The item in the slot.
     */
    public ArrayList<ItemModel> getItem() {
        return this.item;
    }

    public ItemModel getDesignatedItem() {
        return this.designatedItem;
    }

    /**
     * Retrieves the name of the slot.
     *
     * @return The name of the slot.
     */
    public String getSlotName() {
        return this.slotName;
    }

    public int getItemQuantity() {
        updateNItems();
        return this.nItems;
    }

    public int getInitialQuantity() {
        return this.initialQuantity;
    }

    /**
     * Retrieves the capacity of the slot.
     *
     * @return The capacity of the slot.
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Sets the name of the slot.
     *
     * @param slotName The name to set for the slot.
     */
    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    /**
     * Sets the capacity of the slot.
     *
     * @param capacity The capacity to set for the slot.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // public void setNItems(int nItems) {
    // this.nItems = nItems;
    // }

    public void setInitialQuantity(int initialQuantity) {
        this.initialQuantity = initialQuantity;
        this.nItems = initialQuantity;
    }

    public void setItem(ItemModel item) {
        if (designatedItem != null)
            this.designatedItem = new ItemModel(item.getName(), item.getPrice(), item.getCalories());
        else
            System.out.println("ERROR. NO ITEM IN SLOT");
        updateNItems();
    }

    /**
     * Increments the quantity of the item by 1.
     */
    public void addQuantity() {
        if (designatedItem != null) {
            item.add(designatedItem);
            this.nItems++;
        } else
            System.out.println("NULL ITEM");
        updateNItems();
    }

    /**
     * Increments the quantity of the item by n.
     * 
     * @param num the quantity to be added
     */
    public void addQuantity(int num) {
        int i;
        if (nItems + num <= capacity) {
            for (i = 0; i < num; i++) {
                item.add(designatedItem);
                this.nItems += num;
            }
        } else
            System.out.println("Stocks cannot exceed 10.");
        updateNItems();
    }

    /**
     * Decrements the quantity of the item by 1.
     */
    public void decQuantity() {
        if (designatedItem != null)
            item.remove(designatedItem);
        else
            System.out.println("CANNOT REMOVE ITEM");
        updateNItems();
    }

    /**
     * Decrements the quantity of the item by n.
     * 
     * @param num the quantity to be subtracted
     */
    public void decQuantity(int num) {
        int i;
        if (nItems - num >= 0) {
            for (i = 0; i < num; i++) {
                item.remove(designatedItem);
            }
        } else {
            System.out.println("INVALID NUM OF ITEMS TO REMOVE");
        }
        updateNItems();
    }

    // helper
    private void updateNItems() {
        this.nItems = item.size();
    }

}
