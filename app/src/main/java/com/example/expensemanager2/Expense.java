package com.example.expensemanager2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class Expense extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRef = database.getReference();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private String uid;

    private long max;

    private TextView todaysDate;

    private ImageView goBack;

    private ImageView addactivity;

    private int totalincome;

    private int totalpayment;

    private TextView incomeinfo;

    private TextView paymentinfo;

    private PieChartView pieChartView;

    List<SliceValue> pieData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_expense);

        todaysDate = findViewById(R.id.todaysdate);

        incomeinfo = findViewById(R.id.incomeinfo);

        paymentinfo = findViewById(R.id.paymentinfo);

        addactivity = findViewById(R.id.addactivity);

        goBack = findViewById(R.id.goback);

        pieChartView = findViewById(R.id.chart);

        getData();

        addDate();

        uid = user.getUid();


        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });

        addactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), AddActivity.class));

            }
        });

    }

    public void addpie() {

        PieChartData pieChartData = new PieChartData(pieData);

        pieChartData.setHasLabels(true);

        pieChartData.setHasLabels(true).setValueLabelTextSize(13);

        pieChartData.setHasCenterCircle(true).setCenterText1(max + " activities").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#FF807E7E"));

        pieChartView.setPieChartData(pieChartData);

    }

    public void addDate() {

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

        String formattedDate = df.format(c);

        todaysDate.setText(formattedDate);
    }

    public void getData() {

        myRef.addValueEventListener(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                showdata(dataSnapshot);

            }

            @Override

            public void onCancelled(DatabaseError error) {

                Toast.makeText(getApplicationContext(), "Error reading database", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showdata(DataSnapshot dataSnapshot) {

        max = dataSnapshot.child(uid).getChildrenCount();

        long flag = 0;

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            while(flag < max){

                String amount = ds.child(String.valueOf(flag)).getValue(Activity.class).getAmount();

                String description = ds.child(String.valueOf(flag)).getValue(Activity.class).getDescription();

                int temp = ds.child(String.valueOf(flag)).getValue(Activity.class).getTemp();

                if(temp == 0){

                    pieData.add(new SliceValue(Integer.parseInt(amount), Color.rgb(244,135,47)).setLabel("Income\n"+amount+" "+description));

                    totalincome += Integer.parseInt(amount);

                } else {

                    pieData.add(new SliceValue(Integer.parseInt(amount), Color.rgb(240,165, 0)).setLabel("Payment\n"+amount+" "+description));

                    totalpayment += Integer.parseInt(amount);

                }

                flag += 1;

            }
        }

        incomeinfo.setText("Total income : "+totalincome);

        paymentinfo.setText("Total payment : "+totalpayment);

        addpie();
    }
}