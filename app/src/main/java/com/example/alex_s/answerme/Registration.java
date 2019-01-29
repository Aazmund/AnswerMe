package com.example.alex_s.answerme;

import android.content.Intent;
import android.sax.TextElementListener;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity{

    private FirebaseAuth mAuth_reg;
    private FirebaseAuth.AuthStateListener mAuthListener_reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAuthListener_reg = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser n_user = firebaseAuth.getCurrentUser();
                if(n_user != null) {
                    //signed_in
                }else{
                    //signed_out
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void Parser(MenuItem item){

        Intent intent = new Intent(this, Parser.class);
        startActivity(intent);

    }

    public void onClick_reg(View view) {

        Intent intent = new Intent(this, Login.class);
        EditText eFIO = (EditText) findViewById(R.id.FIO);
        EditText eemail = (EditText) findViewById(R.id.email);
        EditText etel_number = (EditText) findViewById(R.id.number);
        EditText epass = (EditText) findViewById(R.id.pass);
        EditText econfirm_pass = (EditText) findViewById(R.id.confirm_pass);

        String FIO = eFIO.getText().toString();
        String email = eemail.getText().toString();
        String tel_number = etel_number.getText().toString();
        String pass = epass.getText().toString();
        String confirm_pass = econfirm_pass.getText().toString();

        String id = "";
        char[] ch_id = tel_number.toCharArray();
        int index = tel_number.length() - 1;
        for(int i = 0; i < 4; i++){
            id += Character.toString(ch_id[index]);
            index --;
        }
        StringBuffer buf_id = new StringBuffer(id);
        buf_id.reverse();
        id = buf_id.toString();

        boolean error = true;
        if(FIO.length() == 0|| email.length() == 0 || tel_number.length() == 0 || pass.length()==0 || confirm_pass.length()==0){
            Toast toast = Toast.makeText(Registration.this,
                    "Есть незаполненые поля", Toast.LENGTH_SHORT);
            toast.show();
        }else if(pass.length() < 8){
            Toast toast = Toast.makeText(Registration.this,
                    "Пароль должен содержать не менее 8 символов", Toast.LENGTH_SHORT);
            toast.show();
        }else if(!pass.equals(confirm_pass)){
            Toast toast = Toast.makeText(Registration.this,
                    "Пароли не совпадают", Toast.LENGTH_SHORT);
            toast.show();
        }else if(pass.indexOf("") > 0){
            Toast toast = Toast.makeText(Registration.this,
                    "Пароль не должен содержать пробелы", Toast.LENGTH_SHORT);
            toast.show();
        }else {error = false;}

        if(!error){
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference ref = db.getReference();
            mAuth_reg = FirebaseAuth.getInstance();

            Pollster pollster = new Pollster(FIO, email, id, tel_number, pass);
            String pol_id = id;
            ref.child(pol_id).child("info_" + pol_id).setValue(pollster);

            mAuth_reg.createUserWithEmailAndPassword(pollster.email, pollster.pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast toast = Toast.makeText(Registration.this,
                                "Регистрация успешна!", Toast.LENGTH_SHORT);
                        toast.show();
                        //startActivity(intent);
                    }else{
                        Toast toast = Toast.makeText(Registration.this,
                                "Регистрация провалена!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });
        }
    }
}