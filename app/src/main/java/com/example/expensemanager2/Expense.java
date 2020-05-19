package com.example.expensemanager2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Expense extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference Expense;

    private Button toincome;
    private Button topayment;
    private Button overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        toincome = findViewById(R.id.income);
        topayment = findViewById(R.id.payment);
        overview = findViewById(R.id.overview);


        toincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddIncome.class));
            }
        });

        topayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddPayment.class));
            }
        });

    }
}
