package Model;

import java.awt.List;
import java.util.ArrayList;

public class SpecialVMModel extends VendingMachine {
    private ArrayList<CustomizableProductModel> customizableProducts;

    public SpecialVMModel() {
        // Initialize the list of customizable products
        customizableProducts = new ArrayList<>();
    }

    public void addCustomizableProduct(CustomizableProductModel product) {
        customizableProducts.add(product);
    }

    public void removeCustomizableProduct(CustomizableProductModel product) {
        customizableProducts.remove(product);
    }

    public void displayCustomizableProducts() {
        System.out.println("Available Customizable Products:");
        for (int i = 0; i < customizableProducts.size(); i++) {
            System.out.println((i + 1) + ". " + customizableProducts.get(i).getName());
        }
    }

}
