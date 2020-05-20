package com.example.expensemanager2;

public class Payment {

    public String payment;

    public String description;

    public String date;

    public int temp;

    public Payment() {
    }

    public Payment(String payment, String description, String date, int temp) {

        this.payment = payment;

        this.description = description;

        this.date = date;

        this.temp = temp;
    }

    public String getPayment() {

        return payment;
    }

    public void setPayment(String payment) {

        this.payment = payment;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public int getTemp() {

        return temp;
    }

    public void setTemp(int temp) {

        this.temp = temp;
    }
}