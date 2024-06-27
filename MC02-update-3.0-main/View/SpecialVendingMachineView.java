package View;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Model.VendingMachine; // Import the SpecialVendingMachine class

/**
 * The SpecialVendingMachineView class represents the graphical user interface
 * for the special vending machine.
 * It extends the VendingMachineView class to reuse its functionality and
 * customize as needed.
 */
public class SpecialVendingMachineView extends VendingMachineView {

    // Add any additional fields or methods specific to the special vending machine
    // view if needed.

    /**
     * Constructs a SpecialVendingMachineView object with the specified special
     * vending machine.
     *
     * @param vendingMachine The SpecialVendingMachine object to associate with the
     *                       view.
     */
    public SpecialVendingMachineView(VendingMachine vendingMachine) {
        super(vendingMachine); // Call the constructor of the parent class (VendingMachineView)

        // Any additional initialization or customization for the special vending
        // machine view can be done here.
        // For example, you can add new buttons or panels, or customize the behavior of
        // existing components.

    }

    @Override
    protected JPanel createCenterButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center-align the button
        JButton centerButton = new JButton("Special Ice Cream");
        panel.add(centerButton);

        // Add ActionListener to the centerButton
        centerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new JPanel with CardLayout
                JPanel cardPanel = new JPanel(new CardLayout());

                // Add four cards with labels "Card 1", "Card 2", "Card 3", and "Card 4"
                cardPanel.add(new JLabel("Card 1"), "Card 1");
                cardPanel.add(new JLabel("Card 2"), "Card 2");
                cardPanel.add(new JLabel("Card 3"), "Card 3");
                cardPanel.add(new JLabel("Card 4"), "Card 4");

                // Create "Next" and "Previous" buttons to navigate between cards
                JButton nextButton = new JButton("Next");
                JButton previousButton = new JButton("Previous");

                // Add ActionListener to the "Next" button
                nextButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                        cardLayout.next(cardPanel);
                    }
                });

                // Add ActionListener to the "Previous" button
                previousButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                        cardLayout.previous(cardPanel);
                    }
                });

                // Create the option pane and add the card panel and buttons to it
                Object[] options = { previousButton, nextButton, "Cancel" };
                int choice = JOptionPane.showOptionDialog(null, cardPanel, "Special Ice Cream Options",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[2]);

                if (choice == JOptionPane.YES_OPTION) {
                    // Handle previous button action
                    CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                    cardLayout.previous(cardPanel);
                } else if (choice == JOptionPane.NO_OPTION) {
                    // Handle next button action
                    CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                    cardLayout.next(cardPanel);
                } else if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                    // Handle cancel button or dialog close action
                }
            }
        });

        return panel;
    }

    // You can also override any methods from the parent class if needed.

    // @Override
    // public void refreshButtons() {
    // super.refreshButtons(); // Call the parent method to update the product
    // buttons' names.
    // // You can also add specific behavior for refreshing the buttons in the
    // special
    // // vending machine view if needed.
    // }

}