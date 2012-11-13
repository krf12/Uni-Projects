package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import java.math.BigDecimal;
import org.junit.*;
import static org.junit.Assert.*;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Side;

/**
 *
 * @author Kit
 */
public class TestSides {

    /**
     *
     */
    @Test
    public void testCreateNewSide() {
        Side side1 = new Side(BigDecimal.valueOf(1.99), "Side", "Fries");
        assertEquals("Expected to find 1.99", BigDecimal.valueOf(1.99), side1.getPrice());
        assertEquals("Expected to find Side", "Side", side1.getDescription());
        assertEquals("Expected to find Fries", "Fries", side1.getSideType());
    }

    /**
     *
     */
    @Test
    public void testCalculatePrice() {
        Side side2 = new Side(BigDecimal.ZERO, "Side", "Fries");
        side2.calculatePrice();
        assertEquals("Expected to find 1.99", BigDecimal.valueOf(1.99), side2.getPrice());
    }
}
