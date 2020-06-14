package com.example.expensemanager2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

    private PieChartView pieChartView;

    List<SliceValue> pieData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_expense);

        todaysDate = findViewById(R.id.todaysdate);

        addactivity = findViewById(R.id.addactivity);

        goBack = findViewById(R.id.goback);

        pieChartView = findViewById(R.id.chart);

        getData();

        addpie();

        addDate();

        uid = user.getUid();


        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });

        addactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),AddActivity.class));

            }
        });

    }

    public void addpie(){

        PieChartData pieChartData = new PieChartData(pieData);

        pieChartData.setHasLabels(true);

        pieChartData.setHasLabels(true).setValueLabelTextSize(14);

        pieChartData.setHasCenterCircle(true).setCenterText1(max+" activities").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#FF807E7E"));

        pieChartView.setPieChartData(pieChartData);


    }

    public void addDate(){

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

        String formattedDate = df.format(c);

        todaysDate.setText(formattedDate);
    }

    public void getData(){

        myRef.addValueEventListener(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showdata(DataSnapshot dataSnapshot) {

        int flag = 0;

        for(DataSnapshot ds : dataSnapshot.getChildren()){

            max = ds.child(uid).getChildrenCount();

            Activity activity = new Activity();

            while(flag<max) {

                pieData.add(new SliceValue(10,Color.BLACK).setLabel("Test"));

                activity.setAmount(ds.child(uid).child(String.valueOf(flag)).getValue(Activity.class).getAmount());

                activity.setDescription(ds.child(uid).child(String.valueOf(flag)).getValue(Activity.class).getDescription());

                activity.setTemp(ds.child(uid).child(String.valueOf(flag)).getValue(Activity.class).getTemp());

                if(activity.getTemp() == 0){

                    pieData.add(new SliceValue(Integer.parseInt(activity.getAmount()), Color.rgb(250 + flag*10,100+ flag*5,0)).setLabel("Income "+activity.getAmount()+" "+activity.getDescription()));

                } else {

                    pieData.add(new SliceValue(Integer.parseInt(activity.getAmount()), Color.rgb(250 + flag*10,100+ flag*5,0)).setLabel("Payment "+activity.getAmount()+" "+activity.getDescription()));

                }

                pieData.add(new SliceValue(10,Color.BLACK).setLabel("Test"));

                flag += 1;

            }
        }
    }
}
