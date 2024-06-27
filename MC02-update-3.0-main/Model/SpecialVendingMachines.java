package Model;

import Model.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SpecialVendingMachines extends VendingMachine {
        Scanner scanner = new Scanner(System.in);
        CustomizableProductModel customItem;
        ArrayList<ItemModel> icecreamFlavors = new ArrayList<ItemModel>();
        ArrayList<ItemModel> availableToppings = new ArrayList<ItemModel>();
        ArrayList<ItemModel> availableSyrups = new ArrayList<ItemModel>();
        ArrayList<ItemModel> availableICHolders = new ArrayList<ItemModel>();

     public SpecialVendingMachines() {
        super();
        this.customItem = null;
    }

    /**
     * Tests the vending machine features based on the user's choice.
     * 
     * @param choice the user's choice for testing a specific feature
     */

    public void addCustomProduct() {
         
        System.out.println("Add Customizable Product");
        
        System.out.println("Enter Product Name");
        String name = scanner.nextLine();

        addToppings();

    }

    public void addICflavor(ItemModel IceCreamFlavor) {
        this.icecreamFlavors.add(IceCreamFlavor);
    }

    public void addToppings(ItemModel availableToppings) {
        this.availableToppings.add(availableToppings);
    }

    public void addSyrup(ItemModel syrup) {
        this.availableSyrups.add(syrup);
    }

    public void addICHolder(ItemModel icHolder) {
        this.availableICHolders.add(icHolder);
    }

    // Getter for icecreamFlavors
    public ArrayList<ItemModel> getIcecreamFlavors() {
        return icecreamFlavors;
    }

    // Getter for availableToppings
    public ArrayList<ItemModel> getAvailableToppings() {
        return availableToppings;
    }

    // Getter for availableSyrups
    public ArrayList<ItemModel> getAvailableSyrups() {
        return availableSyrups;
    }

    // Getter for availableICHolders
    public ArrayList<ItemModel> getAvailableICHolders() {
        return availableICHolders;
    }


    public void addToppings() {

        System.out.println("\nAdd Ice Cream Flavors\n");
        addCustomItems("Ice Cream Flavor", icecreamFlavors);

        System.out.println("\nAdd Syrups\n");
        addCustomItems("Syrup", availableSyrups);

        System.out.println("\nAdd Toppings\n");
        addCustomItems("Topping", availableToppings);

        System.out.println("\nAdd Ice Cream Holders\n");
        addCustomItems("Ice Cream Holder", availableICHolders);

    }
    

    public void addCustomItems(String item, ArrayList<ItemModel> itemList) {
        int i;
        System.out.println("Add " + item);

        System.out.print("Enter the " + item + " name: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.print("Enter the " + item + " price: ");
        float price = scanner.nextFloat();

        System.out.print("Enter the " + item + " amount of calories: ");
        float calories = scanner.nextFloat();

        //initialize new item
        ItemModel tempItem = new ItemModel(name, price, calories);

        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter the " + item + "'s initial stock (must be less than or equal to 10): ");
            int quantity = scanner.nextInt();
    
        if (quantity <= 10) {
            for (i=0; i<quantity; i++) {
                itemList.add(tempItem);
            }
            validInput = true;
        } else {
            System.out.println("Invalid input! Quantity must be less than 10.");
        }
        }
    }

    public void setSlotItem(int slotIndex) {
        SlotModel tempSlot = itemSlot.get(slotIndex-1);

        System.out.println("Add Ice Cream");

        System.out.print("Enter the Ice Cream Flavor: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.print("Enter the Ice Cream price: ");
        float price = scanner.nextFloat();

        System.out.print("Enter the Ice Cream's amount of calories: ");
        float calories = scanner.nextFloat();
        
        // set slot designated item
        tempSlot.setItem(new ItemModel(name, price, calories));

        
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter the item's initial stock (must be less than or equal to 10): ");
            int quantity = scanner.nextInt();
    
        if (quantity <= 10) {
            tempSlot.addQuantity(quantity);
            tempSlot.setInitialQuantity(quantity);
            validInput = true;
        } else {
            System.out.println("Invalid input! Quantity must be less than 10.");
        }
        }       

        System.out.println("Item set successfully.");
    }

    

}