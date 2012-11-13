package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import org.junit.*;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Drink;
import static org.junit.Assert.*;
import java.math.BigDecimal;

/**
 *
 * @author Kit
 */
public class TestDrinks {

    /**
     *
     */
    @Test
    public void testCreateNewDrink() {
        Drink drinks1 = new Drink(BigDecimal.valueOf(1.99), "Drink", "Cola");
        assertEquals("Expected to find 1.99", BigDecimal.valueOf(1.99), drinks1.getPrice());
        assertEquals("Expected to find Drink", "Drink", drinks1.getDescription());
        assertEquals("Expected to find Cola", "Cola", drinks1.getDrinkType());
    }

    /**
     *
     */
    @Test
    public void testCalculatePrice() {
        Drink drinks2 = new Drink(BigDecimal.ZERO, "Drink", "Orange Juice");
        drinks2.calculatePrice();
        assertEquals("Expected to find 0.99", BigDecimal.valueOf(0.99), drinks2.getPrice());
    }
}
