package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import java.math.BigDecimal;
import org.junit.*;
import static org.junit.Assert.*;
import uk.ac.aber.dcs.cs12420.aberpizza.data.*;

/**
 *
 * @author Kit
 */
public class TestOrder {

    /**
     *
     */
    @Test
    public void testOrder() {
        Order test = new Order();
        test.setCustomerName("Kit");
        System.out.println(test.getDateFormat().format(test.getDate()));
        assertEquals("Expected to find Kit", "Kit", test.getCustomerName());
    }

    /**
     *
     */
    @Test
    public void testOrderAddItem() {
        Order testOrder = new Order();
        Pizza testItem = new Pizza(BigDecimal.valueOf(8.99), "Pizza", "Cheese", Size.MEDIUM);
        Side testItem2 = new Side(BigDecimal.valueOf(1.99), "Side", "Fries");
        testOrder.addItem(testItem, 3);
        testOrder.addItem(testItem2, 2);
        assertEquals("Expected to find quantity of 3", 3, testOrder.getItems().get(0).getQuantity());
        assertEquals("Expected to find testItem", testItem, testOrder.getItems().get(0).getItem());
        assertEquals("Expected to find quantity of 2", 2, testOrder.getItems().get(1).getQuantity());
        assertEquals("Expected to find testItem2", testItem2, testOrder.getItems().get(1).getItem());
    }

    /**
     *
     */
    @Test
    public void testOrderUpdateItemQuantity() {
        Order testOrder = new Order();
        Pizza testItem = new Pizza(BigDecimal.valueOf(8.99), "Pizza", "Cheese", Size.MEDIUM);
        testOrder.addItem(testItem, 2);
        assertEquals("Expected to find quantity of 2", 2, testOrder.getItems().get(0).getQuantity());
        testOrder.updateItemQuantity(testItem, 4);
        assertEquals("Expected to find quantity of 4", 4, testOrder.getItems().get(0).getQuantity());
    }

    /**
     *
     */
    @Test
    public void testGetSubtotal() {
        Order testOrder = new Order();
        Pizza testItem = new Pizza(BigDecimal.valueOf(8.99), "Pizza", "Cheese", Size.MEDIUM);
        testOrder.addItem(testItem, 2);
        Side testItem2 = new Side(BigDecimal.valueOf(1.99), "Side", "Fries");
        testOrder.addItem(testItem2, 2);
        assertEquals("Expected to find a subtotal of 21.96", BigDecimal.valueOf(21.96), testOrder.getSubtotal());
    }

    /**
     *
     */
    @Test
    public void testGetDiscount() {
        Order testOrder = new Order();
        Pizza testItem = new Pizza(BigDecimal.valueOf(8.99), "", "Cheese", Size.LARGE);
        testItem.calculatePrice();
        testOrder.addItem(testItem, 3);
        Side testItem2 = new Side(BigDecimal.valueOf(1.99), "", "Fries");
        testItem2.calculatePrice();
        testOrder.addItem(testItem2, 1);
        assertEquals("Expected to find discount of 10.99", BigDecimal.valueOf(10.99), testOrder.getDiscount());
    }

    /**
     *
     */
    @Test
    public void testgetTotal() {
        Order testOrder = new Order();
        Pizza testItem = new Pizza(BigDecimal.valueOf(8.99), "", "Cheese", Size.LARGE);
        testItem.calculatePrice();
        testOrder.addItem(testItem, 3);
        Side testItem2 = new Side(BigDecimal.valueOf(1.99), "", "Fries");
        testItem2.calculatePrice();
        testOrder.addItem(testItem2, 1);
        assertEquals("Expected to find total of 23.97", BigDecimal.valueOf(23.97), testOrder.getTotal());
    }
}
