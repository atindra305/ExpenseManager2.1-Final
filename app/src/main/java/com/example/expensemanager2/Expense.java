package com.example.expensemanager2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

    String userchangedDate;

    private TextView todaysDate;

    private ImageView goBack;

    private PieChartView pieChartView;

    private TextView addIncome;

    private TextView addPayment;

    Calendar c;

    DatePickerDialog dpd;

    List<SliceValue> pieData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_expense);

        todaysDate = findViewById(R.id.todaysdate);

        addIncome = findViewById(R.id.addincome);

        addPayment = findViewById(R.id.addpayment);

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

        addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addactivity(0);
            }
        });

        addPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addactivity(1);
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

        long flag = 0;

        for(DataSnapshot ds : dataSnapshot.getChildren()){

            max = ds.child(uid).getChildrenCount();

            Activity activity = new Activity();

            while(flag<max) {

                activity.setAmount(ds.child(uid).child(String.valueOf(flag)).getValue(Activity.class).getAmount());

                activity.setDescription(ds.child(uid).child(String.valueOf(flag)).getValue(Activity.class).getDescription());

                activity.setTemp(ds.child(uid).child(String.valueOf(flag)).getValue(Activity.class).getTemp());

                if(activity.getTemp() == 0){

                    pieData.add(new SliceValue(Integer.parseInt(activity.getAmount()), Color.rgb(250 + flag*10,100+ flag*5,0)).setLabel("Income "+activity.getAmount()+" "+activity.getDescription()));

                } else {

                    pieData.add(new SliceValue(Integer.parseInt(activity.getAmount()), Color.rgb(250 + flag*10,100+ flag*5,0)).setLabel("Payment "+activity.getAmount()+" "+activity.getDescription()));

                }

                flag += 1;

            }
        }
    }



    private void addactivity(int temp){

            EditText amount = findViewById(R.id.amount);

            EditText description = findViewById(R.id.description);

            final TextView changeDateText = findViewById(R.id.datechangetext);

            Button cancel = findViewById(R.id.cancel);

            final TextView changedate = findViewById(R.id.changedate);

            Button save = findViewById(R.id.save);

            final AlertDialog.Builder alert = new AlertDialog.Builder(Expense.this);

            View view = getLayoutInflater().inflate(R.layout.add_activity_dialog,null);

            alert.setView(view);

            final AlertDialog alertDialog = alert.create();

            alertDialog.show();

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alertDialog.dismiss();

                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    savedata();

                }
            });

            changedate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    c = Calendar.getInstance();

                    int day = c.get(Calendar.DAY_OF_MONTH);

                    int month = c.get(Calendar.MONTH);

                    int year =  c.get(Calendar.YEAR);

                    dpd = new DatePickerDialog(Expense.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            userchangedDate = (dayOfMonth+"-"+month+"-"+year);

                            changeDateText.setText(userchangedDate);

                        }
                    },day,month,year);

                    dpd.show();

                }
            });
    }

}
