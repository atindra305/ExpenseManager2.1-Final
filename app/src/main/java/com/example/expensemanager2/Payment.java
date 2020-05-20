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
}
