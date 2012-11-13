/**
 *
 */
package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;

/**
 * This is the Pizza class. Any pizza has a combination of both size and
 * flavour. This class extends from the Product Class.
 *
 * @author Kit
 */
public class Pizza extends Product {

    private String flavour;
    private Size size;

    /**
     * This is the constructor for the Pizza Class. Each pizza has a certain
     * combination of size and flavour.
     *
     * @param price This is the price of the item.
     * @param description This tells us whether it is a drink, side or pizza.
     * @param fl Tells us the flavour of the item.
     * @param si Tells us the size of the item.
     */
    public Pizza(BigDecimal price, String description, String fl, Size si) {
        super(price, description);
        flavour = fl;
        size = si;
    }

    /**
     * This method can be called to find out the flavour of a certain pizza.
     *
     * @return flavour
     */
    public String getFlavour() {
        return flavour;
    }

    /**
     * This method can be called to set the flavour of a certain pizza to
     * something.
     *
     * @param flavour
     */
    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    /**
     * This method can be called to find out the size of a certain pizza.
     *
     * @return size - Tells us the size of the item.
     */
    public Size getSize() {
        return size;
    }

    /**
     * This method can be called to change the size of a certain pizza.
     *
     * @param size - Tells us the size of the item.
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Calculates the price of a certain pizza based on the size of the pizza in
     * question.
     *
     */
    @Override
    public void calculatePrice() {
        if (size == Size.SMALL) {
            setPrice(BigDecimal.valueOf(7.99));
            setDescription("Small Pizza");
        } else if (size == Size.MEDIUM) {
            setPrice(BigDecimal.valueOf(8.99));
            setDescription("Medium Pizza");
        } else if (size == Size.LARGE) {
            setPrice(BigDecimal.valueOf(10.99));
            setDescription("Large Pizza");
        }
    }
}
