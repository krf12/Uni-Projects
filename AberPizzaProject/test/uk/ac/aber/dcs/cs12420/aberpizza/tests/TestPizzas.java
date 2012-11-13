package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import java.math.BigDecimal;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Pizza;
import org.junit.*;
import static org.junit.Assert.*;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Size;

/**
 *
 * @author Kit
 */
public class TestPizzas {

    /**
     *
     */
    @Test
    public void testCreateNewPizza() {
        Pizza pizza1 = new Pizza(BigDecimal.valueOf(8.99), "Pizza", "Cheese", Size.MEDIUM);
        assertEquals("Expected to find 8.99", BigDecimal.valueOf(8.99), pizza1.getPrice());
        assertEquals("Expected to find Pizza", "Pizza", pizza1.getDescription());
        assertEquals("Expected to find Cheese", "Cheese", pizza1.getFlavour());
        assertEquals("Expected to find Medium", Size.MEDIUM, pizza1.getSize());
    }

    /**
     *
     */
    @Test
    public void testCalculatePrice() {
        Pizza pizza2 = new Pizza(BigDecimal.ZERO, "Pizza", "Cheese", Size.LARGE);
        pizza2.calculatePrice();
        assertEquals("Expected to find 10.99", BigDecimal.valueOf(10.99), pizza2.getPrice());
        assertEquals("Expected to find Large Pizza", "Large Pizza", pizza2.getDescription());
    }
}
