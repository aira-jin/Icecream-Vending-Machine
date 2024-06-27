package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * MainMenu class represents the main menu view of the vending machine
 * application.
 * It extends JPanel and provides buttons to create a vending machine, test the
 * vending machine,
 * and exit the application.
 */
public class MainMenu extends JPanel {

    private JButton createButton;
    private JButton testButton;
    private JButton exitButton;

    /**
     * Constructs a new instance of the MainMenu class.
     * Initializes the main menu view with buttons for creating a vending machine,
     * testing the vending machine, and exiting the application.
     * The buttons are added to the panel using GridBagLayout.
     */
    public MainMenu() {
        createButton = new JButton("Create Vending Machine");
        createButton.setPreferredSize(new Dimension(278, 40));

        testButton = new JButton("Test Vending Machine");
        testButton.setPreferredSize(new Dimension(278, 40));

        exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(278, 40));

        // space between fields
        Insets fieldsInset = new Insets(0, 0, 10, 0);
        // space between buttons
        Insets buttonInset = new Insets(20, 0, 0, 0);

        // uses Grid Bag Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = fieldsInset;
        gridBagConstraints.fill = GridBagConstraints.NONE;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;

        add(createButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;

        add(testButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;

        add(exitButton, gridBagConstraints);
    }

    /**
     * Sets up an action listener for the "Create Vending Machine" button.
     *
     * @param actionListener The ActionListener to be triggered on button click.
     */
    public void createVM(ActionListener actionListener) {
        createButton.addActionListener(actionListener);
    }

    /**
     * Sets up an action listener for the "Test Vending Machine" button.
     *
     * @param actionListener The ActionListener to be triggered on button click.
     */
    public void testVM(ActionListener actionListener) {
        testButton.addActionListener(actionListener);
    }

    /**
     * Sets up an action listener for the "Exit" button.
     *
     * @param actionListener The ActionListener to be triggered on button click.
     */
    public void exit(ActionListener actionListener) {
        exitButton.addActionListener(actionListener);
    }
}
