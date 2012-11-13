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
 *
 * This is the class SideChooserPane which allows the customer to choose the
 * type and quantity of sides.
 *
 *
 * @author Kit
 */
public class SideChooserPane extends JFrame implements ActionListener {

    Side createdSide;
    OrderPanel odp;
    Till till;
    ArrayList<Object> msg = new ArrayList<>();
    int quantityno;
    JLabel qlbl = new JLabel("Enter Quantity");
    JTextField quantity = new JTextField(0);
    JComboBox sChoice = new JComboBox();

    SideChooserPane(OrderPanel odp, Till till) {
        this.odp = odp;
        this.till = till;
        sChoice.addItem("Choose Side");
        sChoice.addItem("Garlic Bread");
        sChoice.addItem("Fries");
        sChoice.addItem("Potato Wedges");
        sChoice.addItem("Coleslaw");
        this.add(sChoice);
        this.add(qlbl);
        this.add(quantity);
        quantity.addActionListener(this);

        msg.add(sChoice);
        msg.add(qlbl);
        msg.add(quantity);

        JOptionPane op = new JOptionPane(
                msg.toArray(),
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION,
                null,
                null);
        JDialog dialog = op.createDialog(this, "Choose Side");
        dialog.setVisible(true);

        int result = JOptionPane.OK_OPTION;

        try {
            result = ((Integer) op.getValue()).intValue();
        } catch (Exception uninitializedValue) {
        }

        if (result == JOptionPane.OK_OPTION) {
            String text = quantity.getText();
            quantityno = Integer.parseInt(text);
            createdSide = new Side(BigDecimal.ZERO, "", "");
            createdSide.setSideType((String) sChoice.getSelectedItem());
            createdSide.calculatePrice();
            till.getOrders().get(till.getOrders().size() - 1).addItem(createdSide, quantityno);
        }
        if (result == JOptionPane.CANCEL_OPTION) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
