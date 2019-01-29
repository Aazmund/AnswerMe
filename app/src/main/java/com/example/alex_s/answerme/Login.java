package com.example.alex_s.answerme;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText ETemail;
    private EditText ETpassword;
    private EditText Eid;

    String email, password;

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        ETemail = (EditText) findViewById(R.id.email);
        ETpassword = (EditText) findViewById(R.id.password);
        Eid = (EditText) findViewById(R.id.poll_id);

        Button btn_login = (Button) findViewById(R.id.btn_auth);
        Button btn_registration = (Button) findViewById(R.id.btn_reg);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();

            }
        });

        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });
    }

    private void startSignIn(){
        email = ETemail.getText().toString();
        password = ETpassword.getText().toString();
        id = Eid.getText().toString();

        if(email.length() == 0 || password.length() == 0 || id.length() == 0){
            Toast toast = Toast.makeText(Login.this,
                    "Есть незаполненные поля", Toast.LENGTH_SHORT);
            toast.show();
        }else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast toast = Toast.makeText(Login.this,
                                "Авторизация провалена!", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    if(task.isSuccessful()){
                        Toast toast = Toast.makeText(Login.this,
                                "Авторизация успешна!", Toast.LENGTH_SHORT);
                        toast.show();

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        id = Eid.getText().toString();
                        intent.putExtra("id", id);

                        startActivity(intent);
                    }
                }
            });
        }


    }
}
