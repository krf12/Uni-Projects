package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;
import java.io.Serializable;

/**
 * This class is the product class. This is an abstract class which holds all of
 * the methods and attributes for the price and description of every drink,
 * pizza and side.
 *
 * @author Kit
 */
public abstract class Product implements Item, Serializable {

    private BigDecimal price;
    private String description;

    /**
     * This is the constructor for the product class. Each instance of product
     * has a price and a description.
     *
     * @param pr This is the price of the item.
     * @param des This tells us whether it is a drink, side or pizza.
     */
    public Product(BigDecimal pr, String des) {
        price = pr;
        description = des;
    }

    /**
     * This method can be called to find the Price of any specific product.
     *
     * @return
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method can be called to set the price of any specific product.
     *
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * This method can be called find the description of any specific product.
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method can be called to set the description of any specific product.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
