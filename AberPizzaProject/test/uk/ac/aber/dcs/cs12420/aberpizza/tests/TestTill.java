package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.*;
import static org.junit.Assert.*;
import uk.ac.aber.dcs.cs12420.aberpizza.data.*;

/**
 *
 * @author Kit
 */
public class TestTill {

    /**
     *
     */
    @Test
    public void testTillAddOrder() {
        Till testTill = new Till();
        Order testOrder = new Order();
        testTill.addOrder(testOrder);
        assertEquals("Expected to find testOrder", testOrder, testTill.getOrders().get(0));
    }

    /**
     *
     */
    @Test
    public void testTillGetTotal() {
        Till testTill = new Till();
        Order testOrder = new Order();
        Pizza testItem = new Pizza(BigDecimal.valueOf(8.99), "Pizza", "Cheese", Size.MEDIUM);
        testOrder.addItem(testItem, 2);
        Side testItem2 = new Side(BigDecimal.valueOf(1.99), "Side", "Fries");
        testOrder.addItem(testItem2, 2);
        testTill.addOrder(testOrder);
        Order testOrder2 = new Order();
        Pizza testItem3 = new Pizza(BigDecimal.valueOf(8.99), "Pizza", "Cheese", Size.MEDIUM);
        testOrder2.addItem(testItem3, 2);
        Side testItem4 = new Side(BigDecimal.valueOf(1.99), "Side", "Fries");
        testOrder2.addItem(testItem4, 2);
        testTill.addOrder(testOrder2);
        assertEquals("Expected to find 43.92", BigDecimal.valueOf(43.92), testTill.getTotalForDay());
    }

    /**
     *
     * @throws IOException
     */
    @Test
    public void testLoadFail() throws IOException {
        Till testTill = null;
        try {
            testTill = Till.load();
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe);
        }
        assertNull("Expected no till", testTill);
    }

    /**
     *
     * @throws IOException
     */
    @Test
    public void testSaveAndLoad() throws IOException {
        Till testTill = new Till();
        Order testOrder = new Order();
        Pizza testItem = new Pizza(BigDecimal.valueOf(8.99), "Pizza", "Cheese", Size.MEDIUM);
        testOrder.addItem(testItem, 2);
        Side testItem2 = new Side(BigDecimal.valueOf(1.99), "Side", "Fries");
        testOrder.addItem(testItem2, 2);
        testTill.addOrder(testOrder);
        testTill.save();
        Till testTill2 = testTill.load();
        assertEquals("Expected to find 17.98", BigDecimal.valueOf(17.98), testTill2.getOrders().get(0).getItems().get(0).getOrderItemTotal());

    }
}
