package View;

import javax.swing.*;
import Model.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The VendingMachineView class represents the graphical user interface for the
 * vending machine.
 */
public class VendingMachineView extends JPanel {

    private VendingMachine vendingMachine;
    private JButton backButton;
    private JTextField totalAmountField;
    private double totalAmount = 0.0;
    private ArrayList<String> tempName;
    private JPanel panel1;

    /**
     * Constructs a VendingMachineView object with the specified vending machine.
     *
     * @param vendingMachine The VendingMachine object to associate with the view.
     */
    public VendingMachineView(VendingMachine vendingMachine) {
        this.vendingMachine = new VendingMachine();
        this.vendingMachine.initializeSlots();

        this.tempName = new ArrayList<String>();
        setLayout(new BorderLayout());

        // initialize temporary names
        int i = 0;
        int size = this.vendingMachine.getItemSlot().size();
        System.out.println("HHHH" + size);
        for (i = 0; i < 9; i++) {
            tempName.add(i, this.vendingMachine.getItemSlot().get(i).getDesignatedItem().getName());
            System.out.println(i + "ITEM- " + tempName.get(i));
        }

        // Panel 1: Uneditable Text Field
        JPanel displayAmountPanel = createTotalAmountPanel();
        displayAmountPanel.setBorder(BorderFactory.createTitledBorder("Uneditable Text Field"));
        add(displayAmountPanel, BorderLayout.NORTH);

        // Panel 2: Main Panel (for Buttons)
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel 2.1: Add buttons
        JPanel panel1 = createPanelWithButtons();
        panel1.setBorder(BorderFactory.createTitledBorder("Choose a Product"));
        mainPanel.add(panel1, BorderLayout.CENTER);

        // Panel 2.2: Money buttons
        JPanel panel2 = createMoneyButtons();
        panel2.setBorder(BorderFactory.createTitledBorder("Insert Money"));
        mainPanel.add(panel2, BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);

        // Panel 3: Black Panel
        JPanel centerButtonPanel = createCenterButtonPanel();
        JPanel blackPanel = new JPanel();
        backButton = new JButton("Cancel"); // Use the class-level backButton variable
        blackPanel.add(centerButtonPanel);
        blackPanel.add(backButton); // Add the backButton to the blackPanel
        add(blackPanel, BorderLayout.SOUTH);

    }

    /**
     * Creates the center button panel containing the special ice cream button.
     *
     * @return The JPanel containing the center button.
     */
    protected JPanel createCenterButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center-align the button
        return panel;
    }

    /**
     * Sets the ActionListener for the back button.
     *
     * @param actionListener The ActionListener to be set.
     */
    public void backButton(ActionListener actionListener) {
        backButton.addActionListener(actionListener);
    }

    /**
     * Creates the panel with product buttons for the vending machine.
     *
     * @return The JPanel containing the product buttons.
     */
    private JPanel createPanelWithButtons() {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(3, 3));

        // Add 9 buttons to the panel
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton(); // Create a button without text initially
            button.addActionListener(new ButtonActionListener());
            panel1.add(button);
        }

        updateButtonNames(); // Update the button names

        panel1.revalidate();
        return panel1;
    }

    /**
     * Updates the names of the product buttons based on the vending machine's item
     * slots.
     */
    private void updateButtonNames() {
        for (int i = 0; i < 9; i++) {
            JButton button = (JButton) panel1.getComponent(i);
            if (i < vendingMachine.getItemSlot().size()) {
                String itemName = vendingMachine.getItemSlot().get(i).getDesignatedItem().getName();
                button.setText(itemName);
            } else {
                button.setText(""); // Clear the text for empty slots
            }
        }
    }

    /**
     * Creates the panel with money buttons for inserting money into the vending
     * machine.
     *
     * @return The JPanel containing the money buttons.
     */
    private JPanel createMoneyButtons() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 5));

        // Add 10 money buttons to the panel with specific denominations
        double[] denominations = { 0.25, 1, 5, 10, 20, 50, 100, 200, 500, 1000 };
        for (double denomination : denominations) {
            String buttonText = String.format("PHP%.2f", denomination);
            JButton button = new JButton(buttonText);
            button.addActionListener(new MoneyButtonActionListener(denomination));
            panel.add(button);
        }

        return panel;
    }

    /**
     * ActionListener for the money buttons to handle money insertion.
     */
    private class MoneyButtonActionListener implements ActionListener {
        private final double denomination;

        public MoneyButtonActionListener(double denomination) {
            this.denomination = denomination;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle money button click action here
            String buttonText = String.format("%.2f", denomination);

            // Customize the JOptionPane options
            Object[] options = { "Insert Money", "Close" };
            int choice = JOptionPane.showOptionDialog(null,
                    "Inserted: " + buttonText,
                    "Money Button Clicked",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == JOptionPane.YES_OPTION) {
                // Update Total amount
                totalAmount += denomination;
                // If "Insert Money" is clicked, show another JOptionPane to inform the user
                JOptionPane.showMessageDialog(null, "Inserted: " + buttonText, "Money Inserted",
                        JOptionPane.INFORMATION_MESSAGE);
                totalAmountField.setText("Inserted Money: PHP " + String.format("%.2f", totalAmount));

                // Update the corresponding DenominationModel in availableCash
                for (DenominationModel denominationModel : vendingMachine.getAvailableCash()) {
                    if (denominationModel.getCurrency() == denomination) {
                        denominationModel.depositMoney(1); // Increase the number of currency units by 1
                        break; // No need to check further, as denomination is unique

                    }
                }
            }
            displayDenominations();
        }

    }

    public void displayDenominations() {
        VendingMachine vm = this.vendingMachine;
        int counter = 1;
        System.out.println("Available Cash: ");
        for (DenominationModel money : vm.getAvailableCash()) {
            System.out.println(counter + ". " + money.getCurrencyName() + " || " + money.getCurrency() + " x "
                    + money.getNumCurrency() + " || " + "Total Amount = " + money.getTotalValue());
            counter++;
        }
        System.out.println();

    }

    /**
     * Creates the panel with the uneditable total amount field for displaying the
     * total amount.
     *
     * @return The JPanel containing the total amount field.
     */
    private JPanel createTotalAmountPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        totalAmountField = new JTextField("Total Amount: PHP 0.00", 15);
        totalAmountField.setEditable(false);
        panel.add(totalAmountField);

        return panel;
    }

    /**
     * Refreshes the product buttons' names based on the current item slots of the
     * vending machine.
     */
    public void refreshButtons() {
        updateButtonNames();
    }

    /**
     * ActionListener for the product buttons to handle product purchases.
     */
    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String buttonText = button.getText();

            // Search for the item index based on the button name
            int index = findItemIndexByName(buttonText);

            String name = vendingMachine.getItemSlot().get(index).getDesignatedItem().getName();
            double price = vendingMachine.getItemSlot().get(index).getDesignatedItem().getPrice();
            double calories = vendingMachine.getItemSlot().get(index).getDesignatedItem().getCalories();

            if (index >= 0 && index < vendingMachine.getItemSlot().size()) {
                int itemQuantity = vendingMachine.getItemSlot().get(index).getItemQuantity();
                if (itemQuantity == 0) {
                    JOptionPane.showMessageDialog(null, "Item Unavailable", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Object[] options = { "Buy", "Cancel" };
                int choice = JOptionPane.showOptionDialog(null, "Price: PHP" + price + " \nCalories: " + calories, name,
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                if (choice == JOptionPane.YES_OPTION) {
                    ItemModel item = vendingMachine.getItemSlot().get(index).getDesignatedItem();
                    if (item != null) {
                        if (totalAmount >= price && vendingMachine.getItemSlot().get(index).getItemQuantity() > 0) {
                            // Check if the vending machine can provide change
                            boolean canProvideChange = vendingMachine.canProvideChange(totalAmount - price);

                            if (canProvideChange) {
                                totalAmount -= price;
                                double change = totalAmount;
                                totalAmountField.setText("Total Amount: PHP " + String.format("%.2f", totalAmount));

                                int changeOption = JOptionPane.showOptionDialog(null,
                                        "Your change: PHP " + String.format("%.2f", change)
                                                + "\nDo you want to keep the change?",
                                        "Change", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                                        new Object[] { "Keep Change", "Dispense Change" }, "Keep Change");

                                if (changeOption == JOptionPane.NO_OPTION) {
                                    // Dispense the change

                                    System.out.print(totalAmount + "qweqwew");
                                    produceChange(totalAmount);
                                    System.out.print(totalAmount + "qwewqe");
                                    JOptionPane.showMessageDialog(null, "Dispensing change", "Dispensing.....",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    totalAmount = 0;
                                    vendingMachine.setTotalPaymentAmount(0);
                                } else {
                                    // Dispense the item
                                    vendingMachine.getItemSlot().get(index).decQuantity();
                                    JOptionPane.showMessageDialog(null,
                                            name + " dispensed. You still have PHP "
                                                    + String.format("%.2f", totalAmount),
                                            "Dispensing.....",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }

                                // Update the totalAmountField here, outside the if-else block
                                totalAmountField.setText("Total Amount: PHP " + String.format("%.2f", totalAmount));
                            } else {
                                // Insufficient change
                                JOptionPane.showMessageDialog(null,
                                        "Insufficient change in the vending machine. Please insert exact denomination.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Insufficient funds. Please insert more money.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        }

        // Helper method to find the item index by name
        private int findItemIndexByName(String name) {
            for (int i = 0; i < vendingMachine.getItemSlot().size(); i++) {
                ItemModel item = vendingMachine.getItemSlot().get(i).getDesignatedItem();
                if (item != null && item.getName().equals(name)) {
                    return i;
                }
            }
            return -1; // Item not found
        }
    }

    public boolean produceChange(double amount) {
        // Create a copy of availableCash to track remaining denominations
        List<DenominationModel> remainingDenominations = vendingMachine.getAvailableCash();

        // Calculate and display the change in different denominations
        System.out.println("\nCalculating total change . . .");
        for (DenominationModel denomination : remainingDenominations) {
            double denominationValue = denomination.getCurrency();
            if (amount >= denominationValue && denomination.getNumCurrency() > 0) {
                int numDenominations = (int) (amount / denominationValue);
                numDenominations = Math.min(numDenominations, denomination.getNumCurrency()); // Limit to available
                                                                                              // denominations
                amount -= denominationValue * numDenominations;

                // Update vending machine's denominations only once for each denomination
                if (numDenominations > 0) {
                    denomination.setNumCurrency(denomination.getNumCurrency() - numDenominations);
                    System.out.println(
                            "[ Dispensing < " + numDenominations + " > " + denomination.getCurrencyName() + " ]");
                }

                // Stop the loop if the amount is fully dispensed
                if (amount == 0) {
                    break;
                }
            }
        }

        if (amount > 0) {
            System.out.println("Unable to dispense the full amount due to insufficient change.");
            // Roll back the changes made to remainingDenominations
            for (DenominationModel denomination : remainingDenominations) {
                for (DenominationModel vendingDenomination : vendingMachine.getAvailableCash()) {
                    if (vendingDenomination.getCurrencyName().equals(denomination.getCurrencyName())) {
                        vendingDenomination.depositMoney(denomination.getNumCurrency());
                        break;
                    }
                }
            }
            return false;
        } else {
            System.out.println("Remaining change: PHP " + amount);
        }
        return true;
    }

    /**
     * Sets the VendingMachine object associated with the view.
     *
     * @param vendingMachine The VendingMachine object to set.
     */
    public void setVendingMachine(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    /**
     * Gets the VendingMachine object associated with the view.
     *
     * @return The VendingMachine object associated with the view.
     */
    public VendingMachine getVendingMachine() {
        return this.vendingMachine;
    }
}
