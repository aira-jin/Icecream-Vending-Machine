package Model;

/**
 * Class representing the money denominations in the Vending Machine.
 */
public class DenominationModel {
    private String currencyName;
    private double currency;
    private int numCurrency;
    private double totalValue;

    /**
     * Constructs a MoneyDenomination object with the given currency name, currency
     * value, and number of currency.
     *
     * @param currencyName The name of the currency.
     * @param currency     The value of the currency.
     * @param numCurrency  The number of currency units.
     */
    public DenominationModel(String currencyName, double currency, int numCurrency) {
        this.currencyName = currencyName;
        this.currency = currency;
        this.numCurrency = numCurrency;
        this.totalValue = currency * numCurrency;
    }

    /**
     * Constructs a MoneyDenomination object with the given currency name and
     * currency value.
     * The number of currency units is initially set to 0.
     *
     * @param currencyName The name of the currency.
     * @param currency     The value of the currency.
     */
    public DenominationModel(String currencyName, double currency) {
        this.currencyName = currencyName;
        this.currency = currency;
        this.numCurrency = 0;
        this.totalValue = currency * numCurrency;
    }

    /**
     * Retrieves the name of the currency.
     *
     * @return The name of the currency.
     */
    public String getCurrencyName() {
        return this.currencyName;
    }

    /**
     * Retrieves the value of the currency.
     *
     * @return The value of the currency.
     */
    public double getCurrency() {
        return this.currency;
    }

    /**
     * Retrieves the number of currency units.
     *
     * @return The number of currency units.
     */
    public int getNumCurrency() {
        return this.numCurrency;
    }

    /**
     * A helper method to calculate the total value based on the currency and number
     * of currency units.
     */
    private void helperCalculate() {
        this.totalValue = this.currency * this.numCurrency;
    }

    /**
     * Deposits the specified amount of currency units into the MoneyDenomination
     * object.
     *
     * @param amt The amount to deposit.
     */
    public void depositMoney(int amt) {
        if (amt > 0)
            this.numCurrency += amt;
        helperCalculate();
    }

    /**
     * Withdraws the specified amount of currency units from the MoneyDenomination
     * object.
     *
     * @param amt The amount to withdraw.
     */
    public void withdrawMoney(int amt) {
        if (amt <= this.numCurrency && amt >= 0)
            this.numCurrency -= amt;
        int i;
        for (i = 0; i < amt; i++) {
            System.out.println("[ Dispensing < " + amt + " > " + this.getCurrencyName() + " ]");
        }
        helperCalculate();
    }

    /**
     * Retrieves the total value of the currency in the MoneyDenomination object.
     *
     * @return The total value of the currency.
     */
    public double getTotalValue() {
        helperCalculate();
        return this.totalValue;
    }

    /**
     * Sets the number of currency units.
     *
     * @param numCurrency The number of currency units to set.
     */
    public void setNumCurrency(int numCurrency) {
        this.numCurrency = numCurrency;
    }

}
