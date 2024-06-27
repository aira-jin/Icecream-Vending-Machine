package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Model.*;
import Controller.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MaintenanceView extends JPanel {

    private VendingMachine vendingMachine;
    private JButton backButton;
    private JButton refreshButton;
    private JButton displaySummaryButton;
    private JTextField totalAmountField;
    private double totalAmount = 0.0;
    private ItemModel[] tempSlots;
    private JPanel moneyDenominationsPanel;
    private JButton customizeVMButton;
    private SpecialVendingMachines specialVM;


    public MaintenanceView(VendingMachine vendingMachine) {
        setLayout(new BorderLayout());
        this.vendingMachine = vendingMachine;
        this.tempSlots = new ItemModel[9];
        this.specialVM = new SpecialVendingMachines();

        vendingMachine.initializeMoney();
        totalAmount = vendingMachine.getTotalPaymentAmount();

        // Create the totalAmountField before using it in createTotalAmountPanel
        totalAmountField = new JTextField("Total Amount: PHP 0.00", 15);
        totalAmountField.setEditable(false);

        // Create the panel to show money denominations and the total amount
        // JPanel moneyDenominationsPanel = createMoneyDenominationsPanel();
        // moneyDenominationsPanel.setBorder(BorderFactory.createTitledBorder("Money
        // Denominations"));
        // add(moneyDenominationsPanel, BorderLayout.NORTH);

        // Panel 1: Uneditable Text Field
        JPanel displayAmountPanel = createTotalAmountPanel();
        displayAmountPanel.setBorder(BorderFactory.createTitledBorder("Available Cash"));
        add(displayAmountPanel, BorderLayout.NORTH);

        // Panel 2: Main Panel (for Buttons)
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel 2.1: Add buttons
        JPanel panel1 = createPanelWithButtons();
        panel1.setBorder(BorderFactory.createTitledBorder("Vending Machine Available Products"));
        mainPanel.add(panel1, BorderLayout.CENTER);

        // Panel 2.2: Money buttons
        JPanel panel2 = createMoneyButtons();
        panel2.setBorder(BorderFactory.createTitledBorder("Insert and Withdraw Money"));
        mainPanel.add(panel2, BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);

        // Panel 3: Black Panel
        JPanel blackPanel = new JPanel();
        backButton = new JButton("Cancel"); // Use the class-level backButton variable
        refreshButton = new JButton("Refresh"); // Use the class-level backButton variable
        customizeVMButton = new JButton("Add Customizable Item");
        customizeVMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customizeVMButtonPanel();
                showInfo();
                //showVMInfo();
            }
        });
        blackPanel.add(backButton); // Add the backButton to the blackPanel
        
        blackPanel.add(refreshButton);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMoneyDenominationsPanel();
                showInfo();
                showVMInfo();
            }
        });
        blackPanel.add(customizeVMButton);

        add(blackPanel, BorderLayout.SOUTH);

        // display
        displaySummaryButton = new JButton("Display Summary of Transactions");
        blackPanel.add(displaySummaryButton); // Add the button to the blackPanel
        // action listener for summary button
        displaySummaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show options in a pop-up dialog
                String[] options = { "Show Summary of Transactions", "Show Starting Inventory",
                        "Show Ending Inventory" };
                int choice = JOptionPane.showOptionDialog(null,
                        "Please select an option:\n",
                        "Display Summary of Transactions",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                // Handle the user's choice
                if (choice == JOptionPane.YES_OPTION) {
                    String summary = generateTransactionSummary();
                    showTransactionSummary(summary);
                } else if (choice == JOptionPane.NO_OPTION) {
                    String startingInventory = generateStartingInventory();
                    showStartingInventory(startingInventory);
                } else if (choice == JOptionPane.CANCEL_OPTION) {
                    String currentInventory = generateCurrentInventory();
                    showCurrentInventory(currentInventory);
                }
            }
        });

        // Panel 4: Money Denominations Panel
        moneyDenominationsPanel = createMoneyDenominationsPanel();
        moneyDenominationsPanel.setBorder(BorderFactory.createTitledBorder("Money Denominations"));
        add(moneyDenominationsPanel, BorderLayout.NORTH); // Add the money denominations panel to the top side
        updateMoneyDenominationsPanel();

    }

    private JPanel createUneditableTextPanel() {
        JPanel panel = new JPanel();
        JTextField textField = new JTextField("Total Amount: " + totalAmount, 20);
        textField.setEditable(false);
        panel.add(textField);
        return panel;
    }

    public void backButton(ActionListener actionListener) {
        backButton.addActionListener(actionListener);
    }

    private JPanel createPanelWithButtons() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));

        // Add 9 buttons to the panel
        for (int i = 1; i <= 9; i++) {
            JButton button = new JButton(Integer.toString(i));
            button.addActionListener(new ButtonActionListener());
            panel.add(button);
        }

        return panel;
    }

    private JPanel createMoneyButtons() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 5));

        // Add 10 money buttons to the panel with specific denominations
        for (DenominationModel denomination : vendingMachine.getAvailableCash()) {
            double currency = denomination.getCurrency();
            String buttonText = String.format("PHP %.2f", currency);
            JButton button = new JButton(buttonText);
            button.addActionListener(new MoneyButtonActionListener(currency, denomination));
            panel.add(button);
        }

        return panel;
    }

    private class MoneyButtonActionListener implements ActionListener {
        private final double denomination;
        private final DenominationModel denominationModel;

        public MoneyButtonActionListener(double denomination, DenominationModel denominationModel) {
            this.denomination = denomination;
            this.denominationModel = denominationModel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            int index = -1, i = 0;
            for (DenominationModel denominationModel : vendingMachine.getAvailableCash()) {
                if (denominationModel.getCurrency() == denomination) {
                    System.out.println("Found");
                    index = i;
                } else
                    i++;
            }
            // Handle money button click action here
            String buttonText = String.format("PHP %.2f", denomination);

            // Customize the JOptionPane options
            Object[] options = { "Insert Money", "Withdraw", "Close" };
            int choice = JOptionPane.showOptionDialog(null,
                    "Money Denomination:\n\t" + buttonText,
                    "Money Button Clicked",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == JOptionPane.YES_OPTION) {
                // Option to insert money
                String input = JOptionPane.showInputDialog(null, "Enter the amount to insert:", "Insert Money",
                        JOptionPane.QUESTION_MESSAGE);
                if (input != null) {
                    try {
                        double amountToAdd = Double.parseDouble(input);
                        vendingMachine.getAvailableCash().get(index).depositMoney((int) amountToAdd);
                        denominationModel.depositMoney((int) amountToAdd);

                        amountToAdd *= denomination;
                        totalAmount += amountToAdd;

                        JOptionPane.showMessageDialog(null, "You have inserted PHP " + amountToAdd, "Money Inserted",
                                JOptionPane.INFORMATION_MESSAGE);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (choice == JOptionPane.NO_OPTION) {
                // Option to withdraw
                if (denominationModel.getNumCurrency() > 0) {
                    double withdrawnAmount = denomination * denominationModel.getNumCurrency();

                    vendingMachine.getAvailableCash().get(index)
                            .withdrawMoney(vendingMachine.getAvailableCash().get(index).getNumCurrency());
                    denominationModel.withdrawMoney(denominationModel.getNumCurrency());

                    totalAmount -= withdrawnAmount;
                    JOptionPane.showMessageDialog(null, "You have withdrawn PHP " + withdrawnAmount, "Money Withdrawn",
                            JOptionPane.INFORMATION_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(null, "No money to withdraw.", "Information",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            // Choice is JOptionPane.CANCEL_OPTION for "Close" button, so no action needed
            // for Close.

            // Update the total amount field
            vendingMachine.setTotalPaymentAmount(totalAmount);
            totalAmountField.setText("Total Amount: PHP " + String.format("%.2f", totalAmount));
            displayDenominations();

            // Inside MoneyButtonActionListener actionPerformed() method:
            // After updating the available cash and totalAmount, call the method to update
            // the panel
            vendingMachine.setTotalPaymentAmount(totalAmount);
            updateMoneyDenominationsPanel();
        }
    }

    private JPanel createTotalAmountPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create a new JPanel to display total amount
        JPanel totalAmountPanel = createUneditableTextPanel();
        totalAmountPanel.setBorder(BorderFactory.createTitledBorder("Total Amount"));
        panel.add(totalAmountPanel, BorderLayout.CENTER);

        return panel;
    }

    // transaction summary
    private void showTransactionSummary(String summary) {
        JTextArea summaryTextArea = new JTextArea(summary, 10, 30);
        summaryTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(summaryTextArea);

        JOptionPane.showMessageDialog(null, scrollPane, "Summary of Transactions",
                JOptionPane.PLAIN_MESSAGE);
    }

    private String generateTransactionSummary() {
        // Implement the logic to generate the summary here
        // For example, you can retrieve the transaction data from the VendingMachine
        // and create a formatted string containing the relevant information.
        // Replace the return statement below with the actual logic.

        String summary = "Summary of Transactions:\n\n";
        
        System.out.println("[ Summary of Transactions ] \n\n");
        int counter = 1;
        double totalIncome = 0;
        if (vendingMachine.getTransactions().size() == 0)
            System.out.println("No transactions yet. ");
        for (TransactionModel transaction : vendingMachine.getTransactions()) {
            double itemIncome = transaction.getNumSold() * transaction.getItem().getPrice();
            summary += counter++ + ". Sold : " + transaction.getItem().getName() + " || Quantity: " + transaction.getNumSold() 
                                + " x " + transaction.getItem().getPrice() + " || Total Amount Sold: " + itemIncome + "\n";
            totalIncome += itemIncome;
        }
        summary += "\nTotal Sales = PHP" + totalIncome + "\n";
        return summary;
    }

    // starting inventory summary
    private void showStartingInventory(String startingInventory) {
        JTextArea startInventoryTextArea = new JTextArea(startingInventory, 10, 30);
        startInventoryTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(startInventoryTextArea);

        JOptionPane.showMessageDialog(null, scrollPane, "Starting Inventory",
                JOptionPane.PLAIN_MESSAGE);
    }

    private String generateStartingInventory() {
        // Implement the logic to generate the summary here
        // For example, you can retrieve the transaction data from the VendingMachine
        // and create a formatted string containing the relevant information.
        // Replace the return statement below with the actual logic.

        String summary = "[ Starting Inventory ] \n\n";

        for (SlotModel slot : vendingMachine.getItemSlot()) {
            if (slot.getItem() != null)
                summary += "Item: " + slot.getDesignatedItem().getName() + " - Stock [ " + slot.getInitialQuantity() + " ]\n";
            else
                summary += "Item: " + "UNOCCUPIED" + " - Stock [ " + "0" + " ]\n";
        }
        return summary;
    }

    // current inventory summary
    private void showCurrentInventory(String currentInventory) {
        JTextArea currentInventoryTextArea = new JTextArea(currentInventory, 10, 30);
        currentInventoryTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(currentInventoryTextArea);

        JOptionPane.showMessageDialog(null, scrollPane, "Current Inventory",
                JOptionPane.PLAIN_MESSAGE);
    }

    private String generateCurrentInventory() {

        String summary = "[ Current Inventory ]\n\n";

            for (SlotModel slot : vendingMachine.getItemSlot()) {
                if (slot.getItem() != null && slot.getDesignatedItem().getName() != "1" && slot.getDesignatedItem().getName() != "2"
                && slot.getDesignatedItem().getName() != "3" && slot.getDesignatedItem().getName() != "4" && slot.getDesignatedItem().getName() != "5"
                && slot.getDesignatedItem().getName() != "6" && slot.getDesignatedItem().getName() != "7" && slot.getDesignatedItem().getName() != "8" && slot.getDesignatedItem().getName() != "9")
                    summary += "Item: " + slot.getDesignatedItem().getName() + " - Stock [ " + slot.getItemQuantity() + " ]\n";
                else
                    summary += "Item: " + "UNOCCUPIED" + " - Stock [ " + "0" + " ]\n";
            }
        
        return summary;
    }

    private JPanel createMoneyDenominationsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 5, 10, 10)); // 2 rows, 5 columns, with 10px vertical and horizontal gaps

        // Add non-editable text fields with borders for each money denomination
        ArrayList<DenominationModel> denominations = vendingMachine.getAvailableCash();
        for (DenominationModel denomination : denominations) {
            String labelText = denomination.getCurrencyName() + ": " + denomination.getNumCurrency() + " x "
                    + denomination.getCurrency();
            JTextField textField = new JTextField(labelText);
            textField.setEditable(false);
            textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // Center-align the text in the text field
            textField.setHorizontalAlignment(JTextField.CENTER);

            // Set the text to be bold
            Font font = textField.getFont();
            textField.setFont(font.deriveFont(font.getStyle() | Font.BOLD));

            panel.add(textField);
        }

        return panel;
    }

    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle button click action here
            JButton button = (JButton) e.getSource();
            String buttonText = button.getText();
            try {
                int intIndex = Integer.parseInt(buttonText);
                if (intIndex >= 0 && intIndex <= 9) {
                    // Customize the JOptionPane options
                    Object[] options = { "Add Item", "Close" };
                    int choice = JOptionPane.showOptionDialog(null,
                            "Button clicked: " + buttonText,
                            "Button Clicked",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            options,
                            options[0]);

                    if (choice == JOptionPane.YES_OPTION) {
                        // If "Add Item" is clicked, show another JOptionPane to get item details
                        JPanel panel = new JPanel(new GridLayout(4, 2));
                        panel.add(new JLabel("Name:"));
                        JTextField nameField = new JTextField(15);
                        panel.add(nameField);

                        panel.add(new JLabel("Price:"));
                        JTextField priceField = new JTextField(15);
                        panel.add(priceField);

                        panel.add(new JLabel("Calories:"));
                        JTextField caloriesField = new JTextField(15);
                        panel.add(caloriesField);

                        panel.add(new JLabel("Stock:"));
                        JTextField stockField = new JTextField(15);
                        panel.add(stockField);

                        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Item Details",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if (result == JOptionPane.OK_OPTION) {
                            // Process the entered item details here
                            String name = nameField.getText();
                            double price = 0.0;
                            double calories = 0.0;
                            int stock = 0;

                            try {
                                price = Double.parseDouble(priceField.getText());
                                calories = Double.parseDouble(caloriesField.getText());
                                stock = Integer.parseInt(stockField.getText());

                                if (stock <= 10) {
                                    ItemModel tempItem = new ItemModel(name, price, calories);
                                    // vendingMachine.getStartingInventory().set(intIndex-1, new SlotModel("Null", tempItem));
                                    vendingMachine.getItemSlot().get(intIndex-1).setInitialQuantity(stock);
                                    vendingMachine.getItemSlot().get(intIndex - 1).setItem(tempItem);
                                    vendingMachine.getItemSlot().get(intIndex - 1).addQuantity(stock);

                                    tempSlots[intIndex - 1] = tempItem;
                                    // Update the button text to the item name
                                    button.setText(name);
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Error: Total stock cannot exceed 10.",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null,
                                        "Invalid input. Please enter valid numbers for price, calories, and stock.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            // Here you can use the name, price, calories, and stock variables as needed //
                            // update

                        }
                    }
                }
            } catch (NumberFormatException ex) {
                String itemName = buttonText;
                int itemIndex = -1;

                // Find the index of the item with the given name
                for (int i = 0; i < tempSlots.length; i++) {
                    if (tempSlots[i] != null && tempSlots[i].getName().equals(itemName)) {
                        itemIndex = i;
                        break;
                    }
                }
                ItemModel item = vendingMachine.getItemSlot().get(itemIndex).getDesignatedItem();
                String itemDetails = "<html><b>Name:</b> " + item.getName() + "<br>"
                        + "<b>Price:</b> $" + item.getPrice() + "<br>"
                        + "<b>Calories:</b> " + item.getCalories() + "<br>"
                        + "<b>Stock:</b> " + vendingMachine.getItemSlot().get(itemIndex).getItemQuantity()
                        + "<br></html>";

                // Create a custom panel to display the item details
                JPanel panel = new JPanel();
                JLabel label = new JLabel(itemDetails);
                panel.add(label);

                // Show the custom panel with item details first
                JOptionPane.showMessageDialog(null, panel, "Item Details",
                        JOptionPane.PLAIN_MESSAGE);

                String[] options2 = { "Restock Item", "Reprice Item", "Cancel" };

                // Show the option dialog

                int choice2 = JOptionPane.showOptionDialog(null,
                        "Please select an option:\n",
                        "Three Options",
                        JOptionPane.YES_NO_CANCEL_OPTION, // Specify the option type
                        JOptionPane.QUESTION_MESSAGE, // Specify the message type
                        null, // Use the default icon
                        options2, // Provide the option buttons
                        options2[0]); // Set the default option (Optional)

                // Handle the user's choice
                if (choice2 == JOptionPane.YES_OPTION) {
                    // Restock
                    itemName = buttonText;
                    itemIndex = -1;

                    // Find the index of the item with the given name
                    for (int i = 0; i < tempSlots.length; i++) {
                        if (tempSlots[i] != null && tempSlots[i].getName().equals(itemName)) {
                            itemIndex = i;
                            break;
                        }
                    }

                    if (itemIndex != -1) {
                        item = vendingMachine.getItemSlot().get(itemIndex).getDesignatedItem();
                        itemDetails = "Name: " + item.getName() + "\n"
                                + "Price: PHP" + item.getPrice() + "\n"
                                + "Calories: " + item.getCalories() + "\n"
                                + "Stock: " + vendingMachine.getItemSlot().get(itemIndex).getItemQuantity() + "\n";

                        JOptionPane.showMessageDialog(null, itemDetails, "Item Details",
                                JOptionPane.INFORMATION_MESSAGE);

                        JPanel panel2 = new JPanel(new GridLayout(1, 2));
                        panel2.add(new JLabel("Restock:"));
                        JTextField stockField = new JTextField(5);
                        panel2.add(stockField);

                        int result = JOptionPane.showConfirmDialog(null, panel2, "Restock Item",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if (result == JOptionPane.OK_OPTION) {
                            int quantity = 0;
                            try {
                                quantity = Integer.parseInt(stockField.getText());
                                int currentStock = vendingMachine.getItemSlot().get(itemIndex).getItemQuantity();
                                int newStock = currentStock + quantity;

                                if (newStock <= 10) {
                                    vendingMachine.getItemSlot().get(itemIndex).addQuantity(quantity);

                                    // Show a message to inform the user about the restock
                                    JOptionPane.showMessageDialog(null,
                                            "Successfully restocked " + quantity + " items for "
                                                    + vendingMachine.getItemSlot().get(itemIndex).getDesignatedItem()
                                                            .getName(),
                                            "Restock Successful", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    // Show an error message if the new stock exceeds the limit of 10
                                    JOptionPane.showMessageDialog(null,
                                            "Error: Total stock cannot exceed 10. Current stock: " + currentStock,
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                }

                            } catch (NumberFormatException ex1) {
                                JOptionPane.showMessageDialog(null,
                                        "Invalid input. Please enter a valid number.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Item not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (choice2 == JOptionPane.NO_OPTION) {
                    // Reprice
                    itemName = buttonText;
                    itemIndex = -1;

                    // Find the index of the item with the given name
                    for (int i = 0; i < tempSlots.length; i++) {
                        if (tempSlots[i] != null && tempSlots[i].getName().equals(itemName)) {
                            itemIndex = i;
                            break;
                        }
                    }

                    if (itemIndex != -1) {
                        JPanel panel3 = new JPanel(new GridLayout(1, 2));
                        panel3.add(new JLabel("New Price:"));
                        JTextField priceField = new JTextField(10);
                        panel3.add(priceField);

                        int result = JOptionPane.showConfirmDialog(null, panel3, "Reprice Item",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if (result == JOptionPane.OK_OPTION) {
                            double newPrice = 0.0;
                            try {
                                newPrice = Double.parseDouble(priceField.getText());
                            } catch (NumberFormatException ex1) {
                                JOptionPane.showMessageDialog(null,
                                        "Invalid input. Please enter a valid number for the new price.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            // Get the corresponding item from the tempSlots array
                            item = vendingMachine.getItemSlot().get(itemIndex).getDesignatedItem();
                            if (item != null) {
                                item.setPrice(newPrice);

                                // Show a message to inform the user about the repriced item
                                JOptionPane.showMessageDialog(null,
                                        "Item " + item.getName() + " has been repriced to $" + newPrice,
                                        "Reprice Successful", JOptionPane.INFORMATION_MESSAGE);
                            }
                            System.out.println("PRICE: " + item.getPrice());
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Item not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        }
    }

    public void updateMoneyDenominationsPanel() {
        // Update the content of the existing money denominations panel
        ArrayList<DenominationModel> denominations = vendingMachine.getAvailableCash();
        int index = 0;
        for (DenominationModel denomination : denominations) {
            String labelText = denomination.getCurrencyName() + ": " + denomination.getNumCurrency() + " x "
                    + denomination.getCurrency();
            JTextField textField = (JTextField) moneyDenominationsPanel.getComponent(index);
            textField.setText(labelText);
            index++;
        }

        revalidate();
        repaint();
    }

    public void customizeVMButtonPanel() {
        JFrame frame = new JFrame("Customize Vending Machine");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1));

        JButton addFlavorsButton = new JButton("Add Ice Cream Flavors");
        addFlavorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addIceCreamFlavors();
            }
        });

        JButton addToppingsButton = new JButton("Add Toppings");
        addToppingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToppings();
            }
        });

        JButton addSyrupsButton = new JButton("Add Syrups");
        addSyrupsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSyrups();
            }
        });

        JButton addICHoldersButton = new JButton("Add Ice Cream Holders");
        addICHoldersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addIceCreamHolders();
            }
        });

        mainPanel.add(addFlavorsButton);
        mainPanel.add(addToppingsButton);
        mainPanel.add(addSyrupsButton);
        mainPanel.add(addICHoldersButton);

        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void addIceCreamFlavors() {
        String name = JOptionPane.showInputDialog(null, "Enter the Ice Cream Flavor name:");
        float price = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter the Ice Cream Flavor price:"));
        float calories = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter the Ice Cream Flavor calories:"));

        ItemModel flavor = new ItemModel(name, price, calories);
        this.specialVM.addICflavor(flavor);
        JOptionPane.showMessageDialog(null, "Ice Cream Flavor added successfully.");
    }

    public void addToppings() {
        String name = JOptionPane.showInputDialog(null, "Enter the Topping name:");
        float price = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter the Topping price:"));
        float calories = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter the Topping calories:"));

        ItemModel topping = new ItemModel(name, price, calories);
        this.specialVM.addToppings(topping);
        JOptionPane.showMessageDialog(null, "Topping added successfully.");
    }

    public void addSyrups() {
        String name = JOptionPane.showInputDialog(null, "Enter the Syrup name:");
        float price = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter the Syrup price:"));
        float calories = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter the Syrup calories:"));

        ItemModel syrup = new ItemModel(name, price, calories);
        this.specialVM.addSyrup(syrup);
        JOptionPane.showMessageDialog(null, "Syrup added successfully.");
    }

    public void addIceCreamHolders() {
        String name = JOptionPane.showInputDialog(null, "Enter the Ice Cream Holder name:");
        float price = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter the Ice Cream Holder price:"));
        float calories = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter the Ice Cream Holder calories:"));

        ItemModel holder = new ItemModel(name, price, calories);
        this.specialVM.addICHolder(holder);
        JOptionPane.showMessageDialog(null, "Ice Cream Holder added successfully.");
    }

    public void showInfo() {
        System.out.println("Ice Cream Flavors:");
        for (ItemModel flavor : this.specialVM.getIcecreamFlavors()) {
            System.out.println(flavor.getName() + " - Price: PHP " + flavor.getPrice() + " | Calories: " + flavor.getCalories());
        }

        System.out.println("\nToppings:");
        for (ItemModel topping : this.specialVM.getAvailableToppings()) {
            System.out.println(topping.getName() + " - Price: PHP " + topping.getPrice() + " | Calories: " + topping.getCalories());
        }

        System.out.println("\nSyrups:");
        for (ItemModel syrup : this.specialVM.getAvailableSyrups()) {
            System.out.println(syrup.getName() + " - Price: PHP " + syrup.getPrice() + " | Calories: " + syrup.getCalories());
        }

        System.out.println("\nIce Cream Holders:");
        for (ItemModel holder : this.specialVM.getAvailableICHolders()) {
            System.out.println(holder.getName() + " - Price: PHP " + holder.getPrice() + " | Calories: " + holder.getCalories());
        }
    }

    public void showVMInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ice Cream Flavors:\n");
        displayItems(specialVM.getIcecreamFlavors(), sb);

        sb.append("\nToppings:\n");
        displayItems(specialVM.getAvailableToppings(), sb);

        sb.append("\nSyrups:\n");
        displayItems(specialVM.getAvailableSyrups(), sb);

        sb.append("\nIce Cream Holders:\n");
        displayItems(specialVM.getAvailableICHolders(), sb);

        JOptionPane.showMessageDialog(null, sb.toString(), "Vending Machine Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayItems(ArrayList<ItemModel> items, StringBuilder sb) {
        for (ItemModel item : items) {
            sb.append(item.getName()).append(" - Price: PHP ").append(item.getPrice())
                    .append(" | Calories: ").append(item.getCalories()).append("\n");
        }
    }

    public void setVendingMachine(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public VendingMachine getVendingMachine() {
        return this.vendingMachine;
    }

    public VendingMachine getSpecialVendingMachine() {
        return this.specialVM;
    }

    public ItemModel[] getTempSlots() {
        return this.tempSlots;
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
}
