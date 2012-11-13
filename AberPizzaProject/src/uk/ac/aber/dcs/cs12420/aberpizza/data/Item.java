package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;
import java.io.Serializable;

/**
 * This interface is used to hold the methods for price and description which
 * are used by all Products.
 *
 * @author Kit
 */
public interface Item {

    /**
     * By calling this method we can obtain the price of any particular item.
     *
     * @return BigDecimal price - This is the price of the item.
     */
    public BigDecimal getPrice();

    /**
     * By calling this method we can set the price of any particular item.
     *
     * @param price - This is the price of the item.
     */
    public void setPrice(BigDecimal price);

    /**
     * By calling this method we can obtain the price of any particular item.
     *
     * @return String description - This tells us whether it is a drink, side or
     * pizza.
     */
    public String getDescription();

    /**
     * By calling this method we can obtain the description of any particular
     * item.
     *
     * @param description - This tells us whether it is a drink, side or pizza.
     */
    public void setDescription(String description);

    /**
     * Calculates the price of the item based on varying factors associated with
     * each Product.
     */
    public void calculatePrice();
}
