/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.*;
import uk.ac.aber.dcs.cs12420.aberpizza.data.*;

/**
 * This is the class PizzaChooserPane which allows the customer to choose the
 * flavour, size and quantity of pizzas.
 *
 * @author Kit
 */
public class PizzaChooserPane extends JFrame implements ActionListener {

    Pizza createdPizza;
    OrderPanel odp;
    Till till;
    ArrayList<Object> msg = new ArrayList<>();
    int quantityno;
    JComboBox flavour = new JComboBox();
    JLabel qlbl = new JLabel("Enter Quantity");
    JTextField quantity = new JTextField(0);
    JComboBox size = new JComboBox();
    Size itemSize;

    PizzaChooserPane(OrderPanel odp, Till till) {
        this.odp = odp;
        this.till = till;
        size.addItem("Choose Size");
        size.addItem("Small");
        size.addItem("Medium");
        size.addItem("Large");

        flavour.addItem("Choose Flavour");
        flavour.addItem("Hawaian");
        flavour.addItem("Margherita");
        flavour.addItem("Pepperoni");
        flavour.addItem("Meat Feast");
        flavour.addItem("Veggie Feast");

        this.add(flavour);
        this.add(size);
        this.add(qlbl);
        this.add(quantity);
        quantity.addActionListener(this);

        msg.add(flavour);
        msg.add(size);
        msg.add(qlbl);
        msg.add(quantity);

        JOptionPane op = new JOptionPane(
                msg.toArray(),
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION,
                null,
                null);
        JDialog dialog = op.createDialog(this, "Choose Pizza");
        dialog.setVisible(true);

        int result = JOptionPane.OK_OPTION;

        try {
            result = ((Integer) op.getValue()).intValue();
        } catch (Exception uninitializedValue) {
        }

        if (result == JOptionPane.OK_OPTION) {
            if (size.getSelectedItem() == "Small") {
                itemSize = Size.SMALL;
            } else if (size.getSelectedItem() == "Medium") {
                itemSize = Size.MEDIUM;
            } else if (size.getSelectedItem() == "Large") {
                itemSize = Size.LARGE;
            }
            String text = quantity.getText();
            quantityno = Integer.parseInt(text);
            createdPizza = new Pizza(BigDecimal.ZERO, "", "", itemSize);
            createdPizza.setFlavour((String) flavour.getSelectedItem());
            createdPizza.calculatePrice();
            till.getOrders().get(till.getOrders().size() - 1).addItem(createdPizza, quantityno);
        }
        if (result == JOptionPane.CANCEL_OPTION) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
