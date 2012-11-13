package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import java.math.BigDecimal;
import org.junit.*;
import static org.junit.Assert.*;
import uk.ac.aber.dcs.cs12420.aberpizza.data.*;

/**
 *
 * @author Kit
 */
public class TestOrderItem {

    /**
     *
     */
    @Test
    public void testOrderItem() {
        Pizza testitem1 = new Pizza(BigDecimal.ZERO, "Pizza", "Cheese", Size.MEDIUM);
        testitem1.calculatePrice();
        OrderItem test = new OrderItem(testitem1, 3);
        assertEquals("Expected to find 8.99", BigDecimal.valueOf(8.99), testitem1.getPrice());
        assertEquals("Expected to find Pizza", "Medium Pizza", testitem1.getDescription());
        assertEquals("Expected to find Cheese", "Cheese", testitem1.getFlavour());
        assertEquals("Expected to find Medium", Size.MEDIUM, testitem1.getSize());
        assertEquals("Expected to find 3", 3, test.getQuantity());
    }

    /**
     *
     */
    @Test
    public void testOrderItemTotal() {
        Side testitem2 = new Side(BigDecimal.ZERO, "Side", "Fries");
        testitem2.calculatePrice();
        OrderItem test2 = new OrderItem(testitem2, 2);
        assertEquals("Expected to find 3.98", BigDecimal.valueOf(3.98), test2.getOrderItemTotal());
    }
}
