package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.util.*;
import java.math.BigDecimal;
import java.text.*;
import java.io.Serializable;

/**
 * This is the Order Class. This class deals with every different instance of
 * order.
 *
 * @author Kit
 */
public class Order implements Serializable {

    private Date date = new Date();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private String customerName;
    private ArrayList<OrderItem> items = new ArrayList<>();

    /**
     * This is the constructor for the Order class.
     */
    public Order() {
    }

    /**
     * This method can be called to obtain the name for any particular customer.
     *
     * @return
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * This method can be called to obtain the date in a particular instance.
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * This method can be called to obtain the format for the date.
     *
     * @return
     */
    public DateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     * This method can be called to set the name of any particular customer.
     *
     * @param cn
     */
    public void setCustomerName(String cn) {
        customerName = cn;
    }

    /**
     * This method can be called to get an arrayList of items.
     *
     * @return
     */
    public ArrayList<OrderItem> getItems() {
        return items;
    }

    /**
     * This method can be called to add one or more item to the order.
     *
     * @param item
     * @param quantity
     */
    public void addItem(Item item, int quantity) {
        items.add(new OrderItem(item, quantity));
    }

    /**
     * This method can be called to change the quantity of a certain item in an
     * order.
     *
     * @param item
     * @param quantity
     */
    public void updateItemQuantity(Item item, int quantity) {
        for (int i = 0; i < items.size(); i++) {
            if (item == items.get(i).getItem()) {
                items.get(i).setQuantity(quantity);
            } else {
                System.out.println("Could not find item");
            }
        }
    }

    /**
     * This method can be called to find the Subtotal of the an order. The
     * Subtotal is the total before offers are applied to the order.
     *
     * @return
     */
    public BigDecimal getSubtotal() {
        BigDecimal subtotal = new BigDecimal(0);
        for (int j = 0; j < items.size(); j++) {
            BigDecimal oit = (items.get(j).getOrderItemTotal());
            subtotal = subtotal.add(oit);
        }
        if (subtotal != null) {
            return subtotal;
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * This method can be called to apply a discount to a particular order.
     *
     * @return
     */
    public BigDecimal getDiscount() {
        BigDecimal discount = new BigDecimal(0);

        int count = 0;

        for (int i = 0; i < items.size(); i++) {
            if (("Large Pizza".equals(items.get(i).getItem().getDescription())) && (items.get(i).getQuantity() == 1)) {
                count = count + 1;
            }
            if (("Drink".equals(items.get(i).getItem().getDescription())) && (items.get(i).getQuantity() == 1)) {
                count = count + 1;
            }
            if (("Side".equals(items.get(i).getItem().getDescription())) && (items.get(i).getQuantity() == 1)) {
                count = count + 1;
            }
        }
        for (int i = 0; i < items.size(); i++) {
            if (("Large Pizza".equals(items.get(i).getItem().getDescription())) && (items.get(i).getQuantity() == 3)) {
                discount = discount.add(BigDecimal.valueOf(10.99));
            }
        }
        if (count == 3) {
            discount = discount.add(BigDecimal.valueOf(5.00));
        }
        if (discount != null) {
            return discount;
        } else {
            return null;
        }
    }

    /**
     * This method can be called in order to get the Total. This is the full
     * Total which includes discounts.
     *
     * @return
     */
    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal(0);
        total = total.add(this.getSubtotal());
        total = total.subtract(this.getDiscount());
        if (total != null) {
            return total;
        }
        return BigDecimal.ZERO;
    }

    /**
     * This method can be called to create a receipt for a particular order.
     *
     * @return
     */
    public String getReceipt() {
        String receipt = "";
        String quantity = "";
        receipt = receipt.concat(dateFormat.format(date));
        receipt = receipt.concat(System.getProperty("line.separator"));
        receipt = receipt.concat("Customer name: " + getCustomerName());
        receipt = receipt.concat(System.getProperty("line.separator"));
        for (int i = 0; i < items.size(); i++) {
            receipt = receipt.concat(items.get(i).getItem().getDescription());
            receipt = receipt.concat("   ");
            receipt = receipt.concat(quantity.valueOf(items.get(i).getQuantity()));
            receipt = receipt.concat(System.getProperty("line.separator"));
            receipt = receipt.concat(items.get(i).getOrderItemTotal().toString());
            receipt = receipt.concat(System.getProperty("line.separator"));
        }
        receipt = receipt.concat("Subtotal £" + this.getSubtotal().toString());
        receipt = receipt.concat(System.getProperty("line.separator"));
        receipt = receipt.concat("Discount £" + this.getDiscount().toString());
        receipt = receipt.concat(System.getProperty("line.separator"));
        receipt = receipt.concat("Total £" + this.getTotal().toString());
        receipt = receipt.concat(System.getProperty("line.separator"));
        if (!"".equals(receipt)) {
            return receipt;
        } else {
            return ("Receipt is empty");
        }
    }
}
