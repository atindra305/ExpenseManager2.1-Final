package com.example.expensemanager2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
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
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private EditText amount;

    private EditText description;

    private Button cancel;

    private Button save;

    private TextView changedate;

    private TextView userdate;

    private RadioGroup selection;

    String activitydate = "";

    private long usertemp;

    private int mYear, mMonth, mDay;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    String uid;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        amount = findViewById(R.id.amount);

        getData();

        uid = user.getUid();

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

        activitydate = df.format(c);

        description = findViewById(R.id.description);

        cancel = findViewById(R.id.cancel);

        selection = findViewById(R.id.selection);

        save = findViewById(R.id.save);

        changedate = findViewById(R.id.changedate);

        userdate = findViewById(R.id.userdate);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Expense.class));

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mamount = amount.getText().toString();

                String mdescription = description.getText().toString();

                if(mamount != ""){

                    if(mdescription != ""){

                        String am = amount.getText().toString();

                        String descr = description.getText().toString();

                        getData();

                        int selectedId = selection.getCheckedRadioButtonId();

                        int flag = 0;

                        if(selectedId == R.id.incomeradio){

                            flag = 0;

                        } else {

                            flag = 1;

                        }

                        Activity useractivity = new Activity(am,descr,activitydate,flag);

                        myRef.child(uid).child(String.valueOf(usertemp)).setValue(useractivity);

                        amount.setText("");

                        description.setText("");

                        Toast.makeText(getApplicationContext(),"Added Successfully", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(),Expense.class));

                    }
                }

            }
        });

        changedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                activitydate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                                userdate.setText("Selected Day - "+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }

    private  void getData(){

        myRef.addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                    usertemp = dataSnapshot.child(uid).getChildrenCount();

            }
            @Override
            public void onCancelled(DatabaseError error) {

                Toast.makeText(getApplicationContext(),"Error in reading database",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
