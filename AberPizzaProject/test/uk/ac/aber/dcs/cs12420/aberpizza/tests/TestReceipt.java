package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import java.math.BigDecimal;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Order;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Pizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Side;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Size;

/**
 *
 * @author Kit
 */
public class TestReceipt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Order testOrder = new Order();
        Pizza testItem = new Pizza(BigDecimal.valueOf(8.99), "Pizza", "Cheese", Size.MEDIUM);
        testOrder.addItem(testItem, 2);
        Side testItem2 = new Side(BigDecimal.valueOf(1.99), "Side", "Fries");
        testOrder.addItem(testItem2, 2);
        testOrder.setCustomerName("Fred");
        System.out.println(testOrder.getReceipt());
    }
}
