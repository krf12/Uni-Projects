package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import java.math.BigDecimal;
import uk.ac.aber.dcs.cs12420.aberpizza.data.*;

/**
 *
 * @author Kit
 */
public class TestSalesHistory {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Till testTill = new Till();
        Order testOrder = new Order();
        testOrder.setCustomerName("Fred");
        Pizza testItem = new Pizza(BigDecimal.valueOf(8.99), "Pizza", "Cheese", Size.LARGE);
        testItem.calculatePrice();
        testOrder.addItem(testItem, 1);
        Side testItem2 = new Side(BigDecimal.valueOf(1.99), "Side", "Fries");
        testItem2.calculatePrice();
        testOrder.addItem(testItem2, 1);
        Drink testItem5 = new Drink(BigDecimal.ZERO, "", "Cola");
        testItem5.calculatePrice();
        testOrder.addItem(testItem5, 1);
        testTill.addOrder(testOrder);
        Order testOrder2 = new Order();
        testOrder2.setCustomerName("Bill");
        Pizza testItem3 = new Pizza(BigDecimal.valueOf(8.99), "Pizza", "Cheese", Size.LARGE);
        testOrder2.addItem(testItem, 3);
        Side testItem4 = new Side(BigDecimal.valueOf(1.99), "Side", "Fries");
        testOrder2.addItem(testItem2, 2);
        testTill.addOrder(testOrder2);
        System.out.println(testTill.viewSalesHistory("Admin"));
    }
}
