package Model;

/**
 * Class representing the transaction summary of the Vending Machine.
 */
public class TransactionModel {
    private ItemModel item = null;
    private int numSold;
    private double totalSales = 0;

    /**
     * Constructs an empty Transaction object.
     */
    public TransactionModel() {
    }

    /**
     * Constructs a Transaction object with the specified item and number of items
     * sold.
     * The total sales amount is calculated automatically based on the item's price
     * and the number of items sold.
     * 
     * @param <Item>
     *
     * @param item    the item being sold
     * @param numSold the number of items sold
     */
    public TransactionModel(ItemModel item, int numSold) {
        this.item = item;
        this.numSold = numSold;
        helperTotalSales();
    }

    /**
     * Returns the item being sold in this transaction.
     *
     * @return the item being sold
     */
    public ItemModel getItem() {
        return this.item;
    }

    /**
     * Returns the number of items sold in this transaction.
     *
     * @return the number of items sold
     */
    public int getNumSold() {
        return this.numSold;
    }

    /**
     * Returns the total sales amount of this transaction.
     * The total sales amount is calculated based on the item's price and the number
     * of items sold.
     *
     * @return the total sales amount
     */
    public double getTotalSales() {
        helperTotalSales();
        return totalSales;
    }

    /**
     * Updates the total sales amount when the number of items sold is increased by
     * 1.
     */
    private void helperTotalSales() {
        this.totalSales = this.item.getPrice() * numSold;
    }

    /**
     * Increases the number of items sold by 1 and updates the total sales amount
     * accordingly.
     */
    public void addNumSold() {
        this.numSold += 1;
        helperTotalSales();
    }
}
