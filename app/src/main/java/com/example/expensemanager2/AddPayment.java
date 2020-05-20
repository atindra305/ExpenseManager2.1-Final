package com.example.expensemanager2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.IccOpenLogicalChannelResponse;
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

public class AddPayment extends AppCompatActivity {


    private Button tooverview;
    private Button toincome;
    private Button payment;
    private EditText amount;
    private EditText description;
    private EditText date;
    private ImageButton savepayment;
    private int usertemp;

    String uid;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        tooverview = findViewById(R.id.addpayment_overview);
        toincome = findViewById(R.id.addpayment_income);
        payment = findViewById(R.id.payment);
        amount = findViewById(R.id.addpayment_amount);
        description  = findViewById(R.id.addpayment_description);
        date = findViewById(R.id.addpayment_date);
        savepayment = findViewById(R.id.addpayment);

        uid = user.getUid();


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);
        date.setText(formattedDate);


        savepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amo = amount.getText().toString();
                String descr = description.getText().toString();
                getData();
                usertemp += 1;
                Payment userpayment = new Payment(amo,descr,formattedDate,usertemp);

                myRef.child(uid).child("Payment").child(String.valueOf(usertemp)).setValue(userpayment);
            }
        });

        tooverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Expense.class));
            }
        });

        toincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Income.class));
            }
        });
    }

        private  void getData(){
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Payment earlierpayment = dataSnapshot.getValue(Payment.class);
                    usertemp = earlierpayment.temp;

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(getApplicationContext(),"Error in reading database",Toast.LENGTH_SHORT).show();
                }
            });

    }

}
