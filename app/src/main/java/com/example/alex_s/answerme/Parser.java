package com.example.alex_s.answerme;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser extends AppCompatActivity{

    String password = "8208";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parser);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void Parser(View view){

        EditText pass = (EditText) findViewById(R.id.usr_pass);
        //final EditText emailtosand = (EditText) findViewById(R.id.email_to_sand);

        String usr_pass = pass.getText().toString();
        //final String email_to_sand = emailtosand.getText().toString();

        if(!usr_pass.equals(password)){
            Toast toast = Toast.makeText(Parser.this,
                    "Неверный пароль!", Toast.LENGTH_SHORT);
            toast.show();
        }
//        else if(email_to_sand.length() == 0){
//            Toast toast = Toast.makeText(Parser.this,
//                    "Заполните все поля", Toast.LENGTH_SHORT);
//            toast.show();
        else{

            EditText id = (EditText) findViewById(R.id.trgt_id);
            final String trgt_id = id.getText().toString();

            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference ref = db.getReference(trgt_id);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String text = "";
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Interviewee res = snapshot.getValue(Interviewee.class);

                        if (res.name != null){
                            text += "Номер телефона " + res.number + "\n";
                            text += "Имя " + res.name + "\n";
                            text += "Возраст " + res.age + "\n";
                            text += "\n";
                        }
                    }
//                    Toast toast = Toast.makeText(Parser.this,
//                            text, Toast.LENGTH_LONG);
//                    toast.show();

                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("plain/text");

                    emailIntent.putExtra(Intent.EXTRA_EMAIL, "email");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, text);
                    Parser.this.startActivity(Intent.createChooser(emailIntent,
                            "Отправка письма..."));

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Error handling
                }
            });

        }

    }

    public void Clear_data(View view){

        EditText id = (EditText) findViewById(R.id.trgt_id);
        final String trgt_id = id.getText().toString();
        Delete(trgt_id);

        Toast toast = Toast.makeText(Parser.this,
                "Удалено!", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void Delete(String trgt_id){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference(trgt_id);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot_dell) {
                for (DataSnapshot dataSnapshot1: dataSnapshot_dell.getChildren()) {

                    Interviewee res = dataSnapshot1.getValue(Interviewee.class);

                    if (res.age != null){

                    dataSnapshot1.getRef().removeValue();

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Error handling
            }
        });
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
}
