package com.example.expensemanager2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {


    private FirebaseAuth mAuth;

    private EditText registeremail;

    private EditText registerpassword;

    private Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        register = findViewById(R.id.registration);


        registeremail = findViewById(R.id.email_register);

        registerpassword = findViewById(R.id.password_register);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAccount(registeremail.getText().toString(),registerpassword.getText().toString());
            }
        });
    }

    protected void createAccount(String email,String password){

        mAuth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            updateUI(null);
                        }
                    }
                });
    }


    protected void updateUI(FirebaseUser user){

        startActivity(new Intent(getApplicationContext(),Expense.class));
    }

    public void tologin(View view) {

        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}
