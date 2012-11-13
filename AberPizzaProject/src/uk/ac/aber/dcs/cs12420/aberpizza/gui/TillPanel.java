package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import uk.ac.aber.dcs.cs12420.aberpizza.data.*;

/**
 *
 * @author Kit
 */
public class TillPanel extends JPanel implements ActionListener {

    Till till;
    JTextField cusName = new JTextField("Enter Name Here");
    JButton nameButton = new JButton("Enter");
    JButton showReceiptButton = new JButton("Show Receipt");
    JButton payButton = new JButton("Pay");
    JButton cancelButton = new JButton("Cancel");
    JTextArea receiptArea = new JTextArea();

    /**
     * This is the constructor for the class TillPanel, which houses the till
     *
     * @param t
     */
    public TillPanel(Till t) {
        till = t;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setPreferredSize(new Dimension(400, 500));

        cusName.setAlignmentX(RIGHT_ALIGNMENT);
        cusName.setPreferredSize(new Dimension(100, 35));
        nameButton.setAlignmentX(RIGHT_ALIGNMENT);
        receiptArea.setAlignmentX(CENTER_ALIGNMENT);
        receiptArea.setPreferredSize(new Dimension(500, 500));
        showReceiptButton.setAlignmentX(RIGHT_ALIGNMENT);
        payButton.setAlignmentX(LEFT_ALIGNMENT);
        cancelButton.setAlignmentX(LEFT_ALIGNMENT);

        this.add(cusName);
        this.add(nameButton);
        this.add(receiptArea);
        this.add(showReceiptButton);
        this.add(payButton);
        this.add(cancelButton);

        nameButton.addActionListener(this);
        payButton.addActionListener(this);
        cancelButton.addActionListener(this);
        showReceiptButton.addActionListener(this);

        receiptArea.setEditable(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) (e.getSource());
        if (source == nameButton) {
            till.getOrders().get(till.getOrders().size() - 1).setCustomerName(cusName.getText());
        } else if (source == showReceiptButton) {
            receiptArea.setText(till.getOrders().get(till.getOrders().size() - 1).getReceipt());
        } else if (source == payButton) {
            BigDecimal payment = new BigDecimal(0);
            String paymentStr = JOptionPane.showInputDialog("Please Enter Payment: ");
            payment = BigDecimal.valueOf(this.convertStringToDouble(paymentStr));
            if (payment.compareTo(till.getOrders().get(till.getOrders().size() - 1).getTotal()) == 0) {
                JOptionPane.showMessageDialog(this, "Payment Accepted");
                till.getOrders().get(till.getOrders().size() - 1).setCustomerName(till.getOrders().get(till.getOrders().size() - 1).getCustomerName() + "PAID");
            }
            if (payment.compareTo(till.getOrders().get(till.getOrders().size() - 1).getTotal()) > 0) {
                JOptionPane.showMessageDialog(this, "Payment Accepted");
                BigDecimal change = new BigDecimal(0);
                change = change.add(payment.subtract(till.getOrders().get(till.getOrders().size() - 1).getTotal()));
                JOptionPane.showMessageDialog(this, "Change : " + change.toString());
                till.getOrders().get(till.getOrders().size() - 1).setCustomerName(till.getOrders().get(till.getOrders().size() - 1).getCustomerName() + "PAID");
            } else {
                JOptionPane.showMessageDialog(this, "Payment Not Accepted, Try Again or Cancel");
            }

        } else if (source == cancelButton) {
            till.getOrders().remove(till.getOrders().size() - 1);
            receiptArea.setText("");
            cusName.setText("Enter Name Here");
        }

    }

    /**
     *
     * @param msg
     * @return
     */
    public double convertStringToDouble(String msg) {
        String aString = msg;
        double aDouble = Double.parseDouble(aString);
        return aDouble;
    }
}
