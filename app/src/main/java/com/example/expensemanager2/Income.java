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
}
