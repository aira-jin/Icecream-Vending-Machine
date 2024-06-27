package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import Model.*;
import View.*;

/**
 * NewMainFrame class represents the main application frame for the "ICE ICE
 * BaBY" vending machine system.
 * It extends JFrame and provides a card layout for switching between different
 * views: Main Menu, Vending Features,
 * and Maintenance Features.
 */
public class NewMainFrame extends JFrame {

    // Card layout for switching view
    private CardLayout cardLayout;
    private MainMenu menu;
    private VendingMachineView vendingVMView;
    private VendingMachine vendingMachine;
    private MaintenanceView maintenanceVM;
    private boolean vmStatus = false;
    private double totalAmount = 0.0;
    private boolean svmStatus = false;

    // Special VEnding Machine
    private SpecialVendingMachineView specialVM;

    /**
     * Constructs a new instance of the NewMainFrame class.
     * Initializes the main application frame with necessary components, sets up the
     * card layout, and adds the
     * MainMenu, VendingMachineView, and MaintenanceView to the card layout.
     * Also sets up event listeners for buttons in the MainMenu, VendingMachineView,
     * and MaintenanceView.
     * Sets the size of the frame and makes it visible.
     */
    public NewMainFrame() {
        super("ICE ICE BaBY");
        cardLayout = new CardLayout();
        menu = new MainMenu();
        vendingMachine = new VendingMachine();
        maintenanceVM = new MaintenanceView(vendingMachine);

        vendingVMView = new VendingMachineView(vendingMachine);
        specialVM = new SpecialVendingMachineView(vendingMachine);

        // sets our layout as a card layout
        setLayout(cardLayout);

        add(menu, "Main Menu");
        add(vendingVMView, "Vending Features");
        add(maintenanceVM, "Maintenance Feature");
        add(specialVM, "Special Vending Machine");

        menu.createVM(e -> {
            JButton button = (JButton) e.getSource();
            String buttonLabel = button.getText();

            if (buttonLabel.equals("Create Vending Machine")) {
                String[] options = { "Regular", "Special" };
                int choice = JOptionPane.showOptionDialog(menu,
                        "Choose an option for the Vending Machine:",
                        "Vending Machine Options", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null,
                        options,
                        options[0]);

                if (choice == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(menu, "You Successfully Created a Regular Vending Machine!");
                    vendingMachine = new VendingMachine(); // regular
                    this.helperInitializeNames(vendingMachine);
                    System.out.println("REGULAR INITIALIZED");
                    vendingMachine.initializeMoney();
                    vendingMachine.initializeSlots();
                    // initialize normal view
                    vendingVMView.setVendingMachine(vendingMachine);
                    vmStatus = true;
                } else if (choice == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(menu, "You Successfully Created a Special Vending Machine!");
                    vendingMachine = new SpecialVMModel(); // special
                    this.helperInitializeNames(vendingMachine);
                    System.out.println("SPECIAL INITIALIZED");
                    vendingMachine.initializeMoney();
                    vendingMachine.initializeSlots();
                    // initialize special view
                    vendingVMView.setVendingMachine(vendingMachine);
                    vmStatus = true;
                    svmStatus = true;
                }
            }
        });

        menu.testVM(e -> {
            JButton button = (JButton) e.getSource();
            String buttonLabel = button.getText();
            if (buttonLabel.equals("Test Vending Machine")) {
                if (vmStatus) {
                    String[] options = { "Vending Features", "Maintenance Features" };
                    int choice = JOptionPane.showOptionDialog(menu,
                            "Choose an option for the Vending Machine",
                            "Vending Machine Options", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null,
                            options,
                            options[0]);

                    if (choice == JOptionPane.YES_OPTION) {

                        if (!svmStatus) {
                            maintenanceVM.updateMoneyDenominationsPanel();
                            vendingVMView.setVendingMachine(this.vendingMachine);
                            vendingVMView.refreshButtons();
                            menu.setVisible(false);
                            vendingVMView.setVisible(true);
                            this.vendingMachine = vendingVMView.getVendingMachine();
                            // maintenanceVM.updateMoneyDenominationsPanel();

                        } else {
                            specialVM.setVendingMachine(this.vendingMachine);
                            specialVM.refreshButtons();
                            menu.setVisible(false);
                            specialVM.setVisible(true);
                            this.vendingMachine = vendingVMView.getVendingMachine();

                        }
                    } else if (choice == JOptionPane.NO_OPTION) {
                        maintenanceVM.setVendingMachine(vendingMachine);
                        vendingVMView.refreshButtons();
                        menu.setVisible(false);
                        maintenanceVM.setVisible(true);
                        this.vendingMachine = maintenanceVM.getVendingMachine();
                        vendingVMView.refreshButtons();
                    }
                } else {
                    // Show an error message if the vending machine is not created yet
                    JOptionPane.showMessageDialog(menu, "Please create a vending machine first!");
                }
            }
        });

        vendingVMView.backButton(e -> {
            JButton button = (JButton) e.getSource();
            String buttonLabel = button.getText();
            if (buttonLabel.equals("Cancel")) {
                vendingVMView.setVisible(false);
                menu.setVisible(true);
            }
        });

        specialVM.backButton(e -> {
            JButton button = (JButton) e.getSource();
            String buttonLabel = button.getText();
            if (buttonLabel.equals("Cancel")) {
                specialVM.setVisible(false);
                menu.setVisible(true);
            }
        });

        maintenanceVM.backButton(e -> {
            JButton button = (JButton) e.getSource();
            String buttonLabel = button.getText();
            if (buttonLabel.equals("Cancel")) {
                maintenanceVM.setVisible(false);
                menu.setVisible(true);
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                // Check if the currently shown component is the VendingMachineView
                if (e.getComponent() == vendingVMView) {
                    vendingVMView.refreshButtons();
                }
            }
        });

        int FRAME_WIDTH = 1200;
        int FRAME_HEIGHT = 700;
        // size of our application frame
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void helperInitializeNames(VendingMachine vendingMachine) {
        int i = 0;
        for (SlotModel temp : vendingMachine.getItemSlot()) {
            temp.setSlotName(String.valueOf(i));
            i++;
        }
    }
}
