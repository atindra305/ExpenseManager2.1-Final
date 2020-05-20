package com.example.expensemanager2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddIncome extends AppCompatActivity {

    private Button tooverview;

    private Button topayment;

    private Button income;

    private EditText amount;

    private EditText description;

    private EditText date;

    private ImageButton saveincome;

    private int usertemp;


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    String uid;


    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_income);


        tooverview = findViewById(R.id.addincome_overview);

        topayment = findViewById(R.id.addincome_payment);

        income = findViewById(R.id.income);

        amount = findViewById(R.id.addincome_amount);

        description  = findViewById(R.id.addincome_description);

        date = findViewById(R.id.addincome_date);

        saveincome = findViewById(R.id.addincome);

        uid = user.getUid();




        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

        final String formattedDate = df.format(c);


        date.setText(formattedDate);



        saveincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String am = amount.getText().toString();

                String descr = description.getText().toString();

                getData();

                Income userincome = new Income(am,descr,formattedDate,usertemp);



                myRef.child("Income").child(uid).child(String.valueOf(usertemp)).setValue(userincome);

                amount.setText("");

                description.setText("");

                Toast.makeText(getApplicationContext(),"Added Successfully",Toast.LENGTH_SHORT).show();
            }
        });

        tooverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Expense.class));
            }
        });

        topayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),AddPayment.class));
            }
        });

    }

    private  void getData(){

        myRef.addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                Income earlierIncome = dataSnapshot.getValue(Income.class);

                usertemp = earlierIncome.temp;

                usertemp += 1;

            }

            @Override
            public void onCancelled(DatabaseError error) {

                Toast.makeText(getApplicationContext(),"Error in reading database",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
