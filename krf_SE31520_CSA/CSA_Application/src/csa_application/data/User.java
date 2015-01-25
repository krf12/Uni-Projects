/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csa_application.data;

/**
 *
 * @author Kit
 */
public class User {

    private String surname;
    private String firstName;
    private String phone;
    private Integer grad_year;
    private Boolean jobs;
    private String email;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getGrad_year() {
        return grad_year;
    }

    public void setGrad_year(Integer grad_year) {
        this.grad_year = grad_year;
    }

    public Boolean getJobs() {
        return jobs;
    }

    public void setJobs(Boolean jobs) {
        this.jobs = jobs;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
