package com.example.expensemanager2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Expense extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRef = database.getReference();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private Button toincome;

    private Button topayment;

    private Button overview;

    private String uid;

    private String ar = "";


    private ListView listView;


    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_expense);


        toincome = findViewById(R.id.income);

        topayment = findViewById(R.id.payment);

        overview = findViewById(R.id.overview);

        listView = findViewById(R.id.listview);


        uid = user.getUid();

        getData();


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


    public void getData(){

        myRef.addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                showdata(dataSnapshot);

            }

            @Override

            public void onCancelled(DatabaseError error) {

                Toast.makeText(getApplicationContext(),"Error reading database",Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void showdata(DataSnapshot dataSnapshot) {

        long flag = 0;

        for(DataSnapshot ds : dataSnapshot.getChildren()){

            long max = ds.child(uid).getChildrenCount();

            Payment payment = new Payment();

            Income income = new Income();

            while(flag<max) {

                income.setIncome(ds.child(uid).child(String.valueOf(flag)).getValue(Income.class).getIncome());

                income.setDescription(ds.child(uid).child(String.valueOf(flag)).getValue(Income.class).getDescription());

                income.setDate(ds.child(uid).child(String.valueOf(flag)).getValue(Income.class).getDate());

                if(income.getIncome() == null){
                    continue;
                }

                ar += "\n" + (income.getIncome()) + "\n";

                ar += income.getDescription() + "\n";

                ar += income.getDate() + "\n";

                arrayList.add(ar);

                ar = "";

                flag += 1;

            }

            flag = 0;

            while(flag<max){

                payment.setPayment(ds.child(uid).child(String.valueOf(flag)).getValue(Payment.class).getPayment());

                payment.setDescription(ds.child(uid).child(String.valueOf(flag)).getValue(Payment.class).getDescription());

                payment.setDate(ds.child(uid).child(String.valueOf(flag)).getValue(Payment.class).getDate());

                if(payment.getPayment() == null){
                    continue;
                }

                ar += "\n" + payment.getPayment() + "\n";

                ar += payment.getDescription() + "\n";

                ar += payment.getDate() + "\n";

                flag += 1;

                arrayList.add(ar);

                ar = "";

            }

//            ArrayAdapter arrayAdapter =  new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList){

                @Override

                public View getView(int position, View convertView, ViewGroup parent) {

                    View view =super.getView(position, convertView, parent);

                    TextView textView=(TextView) view.findViewById(android.R.id.text1);

                    textView.setTextColor(Color.WHITE);

                    textView.setTextSize(20);

                    return view;

                }

            };


            listView.setAdapter(arrayAdapter);
        }
    }
}
