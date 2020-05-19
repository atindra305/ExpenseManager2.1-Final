package com.example.expensemanager2;

import android.widget.TextView;

import java.util.Date;

public class Income {
    public String income;
    public String description;

    public Income(){
    }

    public Income(String income, String description) {
        this.income = income;
        this.description = description;
    }
}
