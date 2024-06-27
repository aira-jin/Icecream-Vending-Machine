package Model;

import java.util.ArrayList;

/**
 * CustomizableProductModel class represents a customizable ice cream product
 * model that can be composed of multiple
 * scoops, syrups, toppings, and a specific ice cream holder (cup, cone, waffle
 * cone, or waffle). It extends the ItemModel
 * class to inherit the properties of a general item and adds specific features
 * for creating customizable ice cream products.
 */
public class CustomizableProductModel extends ItemModel {

    private ArrayList<ArrayList<ItemModel>> ingredients;
    private ArrayList<ItemModel> scoops = new ArrayList<ItemModel>();;
    private ArrayList<ItemModel> syrups = new ArrayList<ItemModel>();;
    private ArrayList<ItemModel> toppings = new ArrayList<ItemModel>();;
    private ItemModel icHolder = null;
    /*
     * Item One - Ice Cream Flavor <ArrayList>
     * Item Two - Pumps of Syrups <ArrayList>
     * Item Three - Toppings <ArrayList>
     * Item Three - Cup / Cone / Waffle Cone / Waffle <Item>
     */

    /**
     * Constructs a new instance of the CustomizableProductModel class without any
     * initial values.
     * Initializes the ArrayLists for scoops, syrups, and toppings and sets up an
     * empty list of ingredients.
     */
    public CustomizableProductModel() {
        super(null, 0, 0);
        this.ingredients = new ArrayList<ArrayList<ItemModel>>();
        this.scoops = new ArrayList<ItemModel>();
        this.syrups = new ArrayList<ItemModel>();
        this.toppings = new ArrayList<ItemModel>();
    }

    /**
     * Constructs a new instance of the CustomizableProductModel class with the
     * provided name, scoops, syrups, toppings,
     * and ice cream holder.
     *
     * @param name     The name of the customizable ice cream product.
     * @param scoops   The list of ice cream scoops in the product.
     * @param syrups   The list of syrups used in the product.
     * @param toppings The list of toppings used in the product.
     * @param icHolder The ice cream holder (cup, cone, waffle cone, or waffle) used
     *                 for the product.
     */
    public CustomizableProductModel(String name, ArrayList<ItemModel> scoops, ArrayList<ItemModel> syrups,
            ArrayList<ItemModel> toppings, ItemModel icHolder) {
        super(name, 0, 0);
        this.scoops = scoops;
        this.syrups = syrups;
        this.toppings = toppings;
        addIngredients();

        this.icHolder = icHolder;
    }

    /**
     * Adds the scoops, syrups, and toppings ArrayLists to the ingredients list to
     * create a complete set of
     * customizable ice cream product ingredients.
     */
    public void addIngredients() {
        this.ingredients.add(scoops);
        this.ingredients.add(syrups);
        this.ingredients.add(toppings);
    }

    /**
     * Retrieves the name of the customizable ice cream product.
     *
     * @return The name of the product.
     */
    public String getName() {
        return this.getName();
    }

    /**
     * Sets the name of the customizable ice cream product.
     *
     * @param name The name to set for the product.
     */
    public void setName(String name) {
        this.setName(name);
    }

    /**
     * Creates a name for the customizable ice cream product based on the ice cream
     * holder's name.
     * The format of the name will be "Special [Ice Cream Holder Name] Ice Cream".
     *
     * @return The generated name for the product.
     */
    public String createName() {
        String tempName = "Special " + icHolder.getName() + " Ice Cream";
        return tempName;
    }

    /**
     * Adds a scoop to the list of scoops in the customizable ice cream product.
     *
     * @param scoop The ice cream scoop to add.
     */
    public void addScoop(ItemModel scoop) {
        this.scoops.add(scoop);
    }

    /**
     * Adds a syrup to the list of syrups in the customizable ice cream product.
     *
     * @param syrup The syrup to add.
     */
    public void addSyrup(ItemModel syrup) {
        this.syrups.add(syrup);
    }

    /**
     * Adds a topping to the list of toppings in the customizable ice cream product.
     *
     * @param topping The topping to add.
     */
    public void addTopping(ItemModel topping) {
        this.toppings.add(topping);
    }

    /**
     * Sets the ice cream holder for the customizable ice cream product.
     *
     * @param icHolder The ice cream holder to set.
     */
    public void setICHolder(ItemModel icHolder) {
        this.icHolder = icHolder;
    }

    /**
     * Computes the total price of the customizable ice cream product by summing up
     * the prices of scoops, syrups,
     * toppings, and the ice cream holder.
     */
    public void computePrice() {
        double price = 0;
        for (ItemModel temp : scoops) {
            price += temp.getPrice();
        }
        for (ItemModel temp : syrups) {
            price += temp.getPrice();
        }
        for (ItemModel temp : toppings) {
            price += temp.getPrice();
        }
        price += this.icHolder.getPrice();
        this.setPrice(price);
    }

    /**
     * Computes the total calories of the customizable ice cream product by summing
     * up the calories of scoops, syrups,
     * toppings, and the ice cream holder.
     */
    public void computeCalories() {
        double calories = 0;
        for (ItemModel temp : scoops) {
            calories += temp.getCalories();
        }
        for (ItemModel temp : syrups) {
            calories += temp.getCalories();
        }
        for (ItemModel temp : toppings) {
            calories += temp.getCalories();
        }
        calories += this.icHolder.getCalories();
        this.setCalories(calories);
    }

    /**
     * Updates the information (price and calories) of the customizable ice cream
     * product after adding or removing
     * ingredients.
     */
    private void updateInfo() {
        computeCalories();
        computePrice();
    }
}
