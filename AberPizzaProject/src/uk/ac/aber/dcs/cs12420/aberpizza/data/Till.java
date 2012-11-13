package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * This is the till class. The till handles traffic from orders.
 *
 * @author Kit
 */
public class Till implements Serializable {

    private ArrayList<Order> orders;

    /**
     * This is the constructor for the class till. Every instance of till has an
     * arrayList of orders.
     */
    public Till() {
        orders = new ArrayList<>();
    }

    /**
     * This method can be called to add an order to the till i.e. add an order
     * to the order arrayList.
     *
     * @param order
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * This method can be called to return a list of all the orders in the till.
     *
     * @return
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * This method can be called to find the total monetary value of all orders
     * in a single day.
     *
     * @return
     */
    public BigDecimal getTotalForDay() {
        BigDecimal total = new BigDecimal(0);
        for (int i = 0; i < orders.size(); i++) {
            total = total.add(orders.get(i).getTotal());
        }

        if (total != BigDecimal.ZERO) {
            return total;
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * This method saves the till.
     *
     * @throws IOException
     */
    public void save() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("SavedTill.sef"));
        oos.writeObject(this);
        oos.close();
    }

    /**
     * This method loads the till.
     *
     * @return
     * @throws IOException
     */
    public static Till load() throws IOException {
        Till load = null;

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("SavedTill.sef"));
        try {
            load = (Till) ois.readObject();
        } catch (ClassNotFoundException cnf) {
            throw new IOException("Problem loading file SavedTill");
        }
        if (load != null) {
            return load;
        } else {
            return null;
        }

    }

    /**
     * This method can be called to view the sales history. This can only be
     * viewed if the correct admin password is entered first.
     *
     * @param pw
     * @return
     */
    public String viewSalesHistory(String pw) {
        String password = "Admin";
        String sales = "";

        if (pw.equals(password)) {
            sales = sales.concat("Number of orders : " + Integer.toString(orders.size()));
            sales = sales.concat(System.getProperty("line.separator"));
            for (int i = 0; i < orders.size(); i++) {
                sales = sales.concat(orders.get(i).getReceipt());
                sales = sales.concat(System.getProperty("line.separator"));
            }
            sales = sales.concat(getTotalForDay().toString());
            sales = sales.concat(System.getProperty("line.separator"));

            return sales;
        } else {
            return ("Password is wrong");
        }
    }
}
