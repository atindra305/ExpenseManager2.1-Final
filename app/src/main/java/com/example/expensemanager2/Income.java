package com.example.expensemanager2;

import android.widget.TextView;

import java.util.Date;

public class Income {
    public String income;
    public String description;
    public String date;
    public int temp;

    public Income(){
    }

    public Income(String income, String description,String date,int temp){
        this.income = income;
        this.description = description;
        this.date = date;
        this.temp = temp;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
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
