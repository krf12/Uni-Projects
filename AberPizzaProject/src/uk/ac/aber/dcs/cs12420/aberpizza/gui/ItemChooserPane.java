/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.*;
import uk.ac.aber.dcs.cs12420.aberpizza.data.*;

/**
 * This is the class ItemChooserPane which allows the customer to choose the
 * type of Item they are adding to the current order.
 *
 * @author Kit
 */
public class ItemChooserPane extends JFrame {

    OrderPanel odp;
    ArrayList<Object> msg = new ArrayList<>();
    int choice;

    ItemChooserPane(OrderPanel odp) {
        this.odp = odp;
        JComboBox itemChoice = new JComboBox();

        itemChoice.addItem("Pizza");
        itemChoice.addItem("Drinks");
        itemChoice.addItem("Sides");
        this.add(itemChoice);
        msg.add(itemChoice);

        JOptionPane op = new JOptionPane(
                msg.toArray(),
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION,
                null,
                null);
        JDialog dialog = op.createDialog(this, "Choose Item");
        dialog.setVisible(true);

        int result = JOptionPane.OK_OPTION;

        try {
            result = ((Integer) op.getValue()).intValue();
        } catch (Exception uninitializedValue) {
        }

        if (result == JOptionPane.OK_OPTION) {
            if (itemChoice.getSelectedItem() == "Pizza") {
                choice = 1;
            } else if (itemChoice.getSelectedItem() == "Drinks") {
                choice = 2;
            } else if (itemChoice.getSelectedItem() == "Sides") {
                choice = 3;
            }
        }

        if (result == JOptionPane.CANCEL_OPTION) {
        }

    }
}
