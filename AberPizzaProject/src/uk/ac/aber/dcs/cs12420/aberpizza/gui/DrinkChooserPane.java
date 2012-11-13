package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.*;
import uk.ac.aber.dcs.cs12420.aberpizza.data.*;

/**
 *
 * This is the class DrinkChooserPane which allows the customer to choose the
 * type and quantity of drinks.
 *
 *
 * @author Kit
 */
public class DrinkChooserPane extends JFrame implements ActionListener {

    Drink createdDrink;
    Till till;
    OrderPanel odp;
    ArrayList<Object> msg = new ArrayList<>();
    int quantityno;
    JComboBox dChoice = new JComboBox();
    JLabel qlbl = new JLabel("Enter Quantity");
    JTextField quantity = new JTextField(0);

    DrinkChooserPane(OrderPanel odp, Till till) {
        this.odp = odp;
        this.till = till;
        dChoice.addItem("Choose Drink");
        dChoice.addItem("Cola");
        dChoice.addItem("Orange Juice");
        dChoice.addItem("Lemonade");
        this.add(dChoice);
        this.add(qlbl);
        this.add(quantity);
        quantity.addActionListener(this);

        msg.add(dChoice);
        msg.add(qlbl);
        msg.add(quantity);

        JOptionPane op = new JOptionPane(
                msg.toArray(),
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION,
                null,
                null);
        JDialog dialog = op.createDialog(this, "Choose Drink");
        dialog.setVisible(true);

        int result = JOptionPane.OK_OPTION;

        try {
            result = ((Integer) op.getValue()).intValue();
        } catch (Exception uninitializedValue) {
        }

        if (result == JOptionPane.OK_OPTION) {
            String text = quantity.getText();
            quantityno = Integer.parseInt(text);
            createdDrink = new Drink(BigDecimal.ZERO, "", "");
            createdDrink.setDrinkType((String) dChoice.getSelectedItem());
            createdDrink.calculatePrice();
            till.getOrders().get(till.getOrders().size() - 1).addItem(createdDrink, quantityno);
        }
        if (result == JOptionPane.CANCEL_OPTION) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}