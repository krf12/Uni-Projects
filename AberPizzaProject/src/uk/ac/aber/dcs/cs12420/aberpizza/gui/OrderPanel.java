package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import uk.ac.aber.dcs.cs12420.aberpizza.data.*;
import java.awt.BorderLayout;
import uk.ac.aber.dcs.cs12420.aberpizza.data.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Kit
 */
public class OrderPanel extends JPanel implements ActionListener {

    JTextArea orderBox = new JTextArea();
    JButton itemButton = new JButton("Choose Item");
    JButton orderButton = new JButton("New Order");
    JButton updateButton = new JButton("Update Order");
    Till till;
    int choice;

    /**
     * This is the constructor for the class OrderPanel, which houses the order
     *
     * @param t
     */
    public OrderPanel(Till t) {
        till = t;
        setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(600, 500));

        orderBox.setPreferredSize(new Dimension(200, 200));
        orderBox.setEditable(false);

        this.add(itemButton, BorderLayout.PAGE_START);
        this.add(orderBox, BorderLayout.CENTER);
        this.add(updateButton, BorderLayout.EAST);
        this.add(orderButton, BorderLayout.PAGE_END);

        itemButton.addActionListener(this);
        orderButton.addActionListener(this);
        updateButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) (e.getSource());
        if (source == orderButton) {
            till.addOrder(new Order());
            orderBox.setText("");
        } else if (source == itemButton) {
            ItemChooserPane icp = new ItemChooserPane(this);
            this.choice = icp.choice;
            while (choice != 0) {
                if (choice == 1) {
                    PizzaChooserPane pcp = new PizzaChooserPane(this, till);
                    this.till = pcp.till;
                    String flavour = (String) pcp.flavour.getSelectedItem();
                    if (till.getOrders().get(till.getOrders().size() - 1).getItems() != null) {
                        orderBox.append(System.getProperty("line.separator"));
                        orderBox.append(till.getOrders().get(till.getOrders().size() - 1).getItems().get(till.getOrders().get(till.getOrders().size() - 1).getItems().size() - 1).getItem().getDescription());
                        orderBox.append("                            ");
                        orderBox.append(flavour);
                        orderBox.append("                            ");
                        orderBox.append(till.getOrders().get(till.getOrders().size() - 1).getItems().get(till.getOrders().get(till.getOrders().size() - 1).getItems().size() - 1).getItem().getPrice().toString());
                        orderBox.append("                            ");
                        int quantity = (till.getOrders().get(till.getOrders().size() - 1).getItems().get(till.getOrders().get(till.getOrders().size() - 1).getItems().size() - 1).getQuantity());
                        orderBox.append(Integer.toString(quantity));
                        orderBox.append(System.getProperty("line.separator"));
                        choice = 0;
                    }
                }
                if (choice == 2) {
                    DrinkChooserPane dcp = new DrinkChooserPane(this, till);
                    this.till = dcp.till;
                    String type = (String) dcp.dChoice.getSelectedItem();
                    if (till.getOrders().get(till.getOrders().size() - 1).getItems() != null) {
                        orderBox.append(System.getProperty("line.separator"));
                        orderBox.append(till.getOrders().get(till.getOrders().size() - 1).getItems().get(till.getOrders().get(till.getOrders().size() - 1).getItems().size() - 1).getItem().getDescription());
                        orderBox.append("                            ");
                        orderBox.append(type);
                        orderBox.append("                            ");
                        orderBox.append(till.getOrders().get(till.getOrders().size() - 1).getItems().get(till.getOrders().get(till.getOrders().size() - 1).getItems().size() - 1).getItem().getPrice().toString());
                        orderBox.append("                            ");
                        int quantity = (till.getOrders().get(till.getOrders().size() - 1).getItems().get(till.getOrders().get(till.getOrders().size() - 1).getItems().size() - 1).getQuantity());
                        orderBox.append(Integer.toString(quantity));
                        orderBox.append(System.getProperty("line.separator"));
                        choice = 0;
                    }
                }
                if (choice == 3) {
                    SideChooserPane scp = new SideChooserPane(this, till);
                    this.till = scp.till;
                    String type = (String) scp.sChoice.getSelectedItem();
                    orderBox.append(System.getProperty("line.separator"));
                    if (till.getOrders().get(till.getOrders().size() - 1).getItems() != null) {
                        orderBox.append(till.getOrders().get(till.getOrders().size() - 1).getItems().get(till.getOrders().get(till.getOrders().size() - 1).getItems().size() - 1).getItem().getDescription());
                        orderBox.append("                            ");
                        orderBox.append(type);
                        orderBox.append("                            ");
                        orderBox.append(till.getOrders().get(till.getOrders().size() - 1).getItems().get(till.getOrders().get(till.getOrders().size() - 1).getItems().size() - 1).getItem().getPrice().toString());
                        orderBox.append("                            ");
                        int quantity = (till.getOrders().get(till.getOrders().size() - 1).getItems().get(till.getOrders().get(till.getOrders().size() - 1).getItems().size() - 1).getQuantity());
                        orderBox.append(Integer.toString(quantity));
                        orderBox.append(System.getProperty("line.separator"));
                        choice = 0;
                    }
                }
            }
        } else if (source == updateButton) {
            int item = (Integer.parseInt(JOptionPane.showInputDialog("Please enter number of item :")) - 1);
            Item wanted = till.getOrders().get(till.getOrders().size() - 1).getItems().get(item).getItem();
            int quantity = Integer.parseInt(JOptionPane.showInputDialog("Please enter updated quantity :"));
            till.getOrders().get(till.getOrders().size() - 1).updateItemQuantity(wanted, quantity);
            try {
                orderBox.replaceRange(Integer.toString(till.getOrders().get(till.getOrders().size() - 1).getItems().get(item).getQuantity()), orderBox.getLineEndOffset(item + 2) - 3, orderBox.getLineEndOffset(item + 2));
            } catch (BadLocationException ex) {
            }
        }
    }
}
