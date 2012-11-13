package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;

/**
 * This class defines the attributes and methods required by drinks.
 *
 * @author Kit
 */
public class Drink extends Product {

    private String type;

    /**
     * This is the constructor for the drink class. When this method is called,
     * the three parameters of price, description and price must be entered.
     *
     * @param price This is the price of the item.
     * @param description This tells us whether it is a drink, side or pizza.
     * @param type This tells us what kind of drink it is.
     */
    public Drink(BigDecimal price, String description, String type) {
        super(price, description);
        this.type = type;
    }

    /**
     * This sets the type of the drink - tells us what kind of drink it is.
     *
     */
    public void setDrinkType(String type) {
        this.type = type;
    }

    /**
     * This method can be called to find out what kind of drink a certain drink
     * is.
     *
     * @return type - This tells us what kind of drink it is.
     */
    public String getDrinkType() {
        return type;
    }

    /**
     * Calculates the price of the item based on the type of item.
     */
    @Override
    public void calculatePrice() {
        if (type == "Cola") {
            setPrice(BigDecimal.valueOf(1.99));
            setDescription("Drink");
        } else if (type == "Orange Juice") {
            setPrice(BigDecimal.valueOf(0.99));
            setDescription("Drink");
        } else if (type == "Lemonade") {
            setPrice(BigDecimal.valueOf(1.99));
            setDescription("Drink");
        }
    }
}
