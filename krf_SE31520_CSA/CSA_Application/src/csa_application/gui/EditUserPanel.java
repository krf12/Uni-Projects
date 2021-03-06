/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csa_application.gui;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author Kit
 */
public class EditUserPanel extends javax.swing.JPanel {

    /**
     * Creates new form NewUserPanel
     */
    public EditUserPanel() {
        initComponents();
        for(int i = 2014;i > 1950; i--){
            yearField.addItem(i);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        surnameLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        yearLabel = new javax.swing.JLabel();
        phoneLabel = new javax.swing.JLabel();
        jobsLabel = new javax.swing.JLabel();
        surnameField = new javax.swing.JTextField();
        nameField = new javax.swing.JTextField();
        yearField = new JComboBox<>();
        jobsField = new javax.swing.JCheckBox();
        emailField = new javax.swing.JTextField();
        phoneField = new javax.swing.JTextField();

        surnameLabel.setFont(new java.awt.Font("Kartika", 0, 16)); // NOI18N
        surnameLabel.setText("Surname");

        nameLabel.setFont(new java.awt.Font("Kartika", 0, 16)); // NOI18N
        nameLabel.setText("First Name");

        emailLabel.setFont(new java.awt.Font("Kartika", 0, 16)); // NOI18N
        emailLabel.setText("Email");

        yearLabel.setFont(new java.awt.Font("Kartika", 0, 16)); // NOI18N
        yearLabel.setText("Grad. Year");

        phoneLabel.setFont(new java.awt.Font("Kartika", 0, 16)); // NOI18N
        phoneLabel.setText("Phone");

        jobsLabel.setFont(new java.awt.Font("Kartika", 0, 16)); // NOI18N
        jobsLabel.setText("Jobs");

        surnameField.setFont(new java.awt.Font("Kartika", 0, 14)); // NOI18N
        surnameField.setText("");

        nameField.setFont(new java.awt.Font("Kartika", 0, 14)); // NOI18N
        nameField.setText("");

        yearField.setFont(new java.awt.Font("Kartika", 0, 14)); // NOI18N

        emailField.setFont(new java.awt.Font("Kartika", 0, 14)); // NOI18N
        emailField.setText("");

        phoneField.setFont(new java.awt.Font("Kartika", 0, 14)); // NOI18N
        phoneField.setText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(surnameLabel)
                            .addComponent(nameLabel)
                            .addComponent(emailLabel)
                            .addComponent(yearLabel)
                            .addComponent(jobsLabel))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jobsField)
                                    .addComponent(yearField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(surnameField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                    .addComponent(nameField, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(emailField)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(phoneLabel)
                        .addGap(52, 52, 52)
                        .addComponent(phoneField)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(surnameLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(surnameField)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameLabel)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLabel)
                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yearLabel)
                    .addComponent(yearField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneLabel)
                    .addComponent(phoneField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jobsLabel)
                    .addComponent(jobsField))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JCheckBox jobsField;
    private javax.swing.JLabel jobsLabel;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField phoneField;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JTextField surnameField;
    private javax.swing.JLabel surnameLabel;
    private javax.swing.JComboBox<Integer> yearField;
    private javax.swing.JLabel yearLabel;
    // End of variables declaration//GEN-END:variables

    public JTextField getEmailField() {
        return emailField;
    }

    public void setEmailField(JTextField emailField) {
        this.emailField = emailField;
    }

    public JCheckBox getJobsField() {
        return jobsField;
    }

    public void setJobsField(JCheckBox jobsField) {
        this.jobsField = jobsField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public void setNameField(JTextField nameField) {
        this.nameField = nameField;
    }

    public JTextField getPhoneField() {
        return phoneField;
    }

    public void setPhoneField(JTextField phoneField) {
        this.phoneField = phoneField;
    }

    public JTextField getSurnameField() {
        return surnameField;
    }

    public void setSurnameField(JTextField surnameField) {
        this.surnameField = surnameField;
    }

    public JComboBox<Integer> getYearField() {
        return yearField;
    }

    public void setYearField(JComboBox<Integer> yearField) {
        this.yearField = yearField;
    }
}
