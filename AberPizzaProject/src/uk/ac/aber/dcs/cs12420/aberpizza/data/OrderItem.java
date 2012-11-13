package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;
import java.io.Serializable;

/**
 * This is the OrderItem Class. This class deals with every item which is able
 * to be ordered.
 *
 * @author Kit
 */
public class OrderItem implements Serializable {

    private int quantity;
    private Item item;

    /**
     * This is the constructor for the OrderItem Class.
     *
     * @param item
     * @param quantity
     */
    public OrderItem(Item item, int quantity) {
        this.quantity = quantity;
        this.item = item;
    }

    /**
     * This method can be called to set the quantity of a certain item which is
     * wanted to be ordered.
     *
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * This method can be called to find out the quantity of a certain item
     * which is being ordered.
     *
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * This method can be called to find the object item.
     *
     * @return
     */
    public Item getItem() {
        return item;
    }

    /**
     * This method can be called to find the Total price for a certain Order
     * Item.
     *
     * @return
     */
    public BigDecimal getOrderItemTotal() {
        BigDecimal price = item.getPrice();
        BigDecimal oit = price.multiply(BigDecimal.valueOf(quantity));
        return oit;
    }
}
