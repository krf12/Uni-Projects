package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;

/**
 * This is the side class. Every instance of side has a type, price and
 * description.
 *
 * @author Kit
 */
public class Side extends Product {

    private String type;

    /**
     * This is the constructor for the side class.
     *
     * @param price This is the price of the item.
     * @param description This tells us whether it is a drink, side or pizza.
     * @param type This tells us what kind of side it is.
     */
    public Side(BigDecimal price, String description, String type) {
        super(price, description);
        this.type = type;
    }

    /**
     * This method can be called to set the type of any specific instance of
     * side.
     *
     * @param type - This tells us what kind of side it is.
     */
    public void setSideType(String type) {
        this.type = type;
    }

    /**
     * This method can be called to find the type of any specific instance of
     * side.
     *
     * @return type - This tells us what kind of side it is.
     */
    public String getSideType() {
        return type;
    }

    @Override
    /**
     * This method can be called to calculate the price of any specific instance
     * of side.
     */
    public void calculatePrice() {
        if (type == "Garlic Bread") {
            setPrice(BigDecimal.valueOf(3.99));
            setDescription("Side");
        } else if (type == "Fries") {
            setPrice(BigDecimal.valueOf(1.99));
            setDescription("Side");
        } else if (type == "Potato Wedges") {
            setPrice(BigDecimal.valueOf(3.99));
            setDescription("Side");
        } else if (type == "Coleslaw") {
            setPrice(BigDecimal.valueOf(0.99));
            setDescription("Side");
        }
    }
}
