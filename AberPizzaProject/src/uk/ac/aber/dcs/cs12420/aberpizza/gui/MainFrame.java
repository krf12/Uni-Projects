package uk.ac.aber.dcs.cs12420.aberpizza.gui;

/**
 *
 * @author Kit
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import uk.ac.aber.dcs.cs12420.aberpizza.data.*;

/**
 *
 * @author Kit
 */
public class MainFrame extends BasicFrame implements ActionListener {

    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenu adminMenu = new JMenu("Admin");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem loadItem = new JMenuItem("Load");
    JMenuItem exitItem = new JMenuItem("Exit");
    JMenuItem salesHistItem = new JMenuItem("View Sales History");
    Till till = new Till();
    JPanel oPanel = new OrderPanel(till);
    JPanel tPanel = new TillPanel(till);

    /**
     * This is the constructor that creates the MainFrame, which extends from
     * BasicFrame and creates the basis for the complex GUI.
     */
    public MainFrame() {
        setLayout(new BorderLayout(50, 0));
        showIt("AberPizza");

        this.setJMenuBar(menuBar);

        this.add(oPanel);
        this.add(tPanel, BorderLayout.EAST);

        menuBar.add(fileMenu);
        menuBar.add(adminMenu);

        saveItem.addActionListener(this);
        loadItem.addActionListener(this);
        exitItem.addActionListener(this);
        salesHistItem.addActionListener(this);
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(exitItem);
        adminMenu.add(salesHistItem);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JMenuItem source = (JMenuItem) (ae.getSource());
        if (source == saveItem) {
            try {
                till.save();
            } catch (IOException ex) {
                JOptionPane.showInternalMessageDialog(rootPane, ex.getMessage());
            }
        } else if (source == loadItem) {
            try {
                till = Till.load();

            } catch (IOException ex) {
                JOptionPane.showInternalMessageDialog(rootPane, ex.getMessage());
            }
        } else if (source == exitItem) {
            System.exit(0);
        } else if (source == salesHistItem) {
            String password = JOptionPane.showInputDialog("Please enter password:");
            JOptionPane.showMessageDialog(rootPane, till.viewSalesHistory(password));

        }
    }
}