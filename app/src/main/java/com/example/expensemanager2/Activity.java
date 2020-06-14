package com.example.expensemanager2;

public class Activity {

    public String amount;

    public String description;

    public String date;

    public int temp;

    public Activity(){

    }

    public Activity(String amount, String description, String date, int temp) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.temp = temp;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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
