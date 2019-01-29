package com.example.alex_s.answerme;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Confirm extends AppCompatActivity {

    FirebaseDatabase db = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button chkButton = (Button) findViewById(R.id.btn_check);
        chkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });
    }

    private void confirm(){
        EditText check_id = (EditText) findViewById(R.id.confirm_int_id);

        Intent intent = getIntent();

        String id = intent.getStringExtra("id");
        String answer1 = intent.getStringExtra("answer1");
        String answer2 = intent.getStringExtra("answer2");
        String answer3 = intent.getStringExtra("answer3");
        String name = intent.getStringExtra("name");
        String age = intent.getStringExtra("age");
        String number = intent.getStringExtra("number");
        String int_id = intent.getStringExtra("int_id");

        if(int_id.equals(check_id.getText().toString())){
            Toast toast = Toast.makeText(Confirm.this,
                    "Коды совпадают!", Toast.LENGTH_SHORT);
            toast.show();

            DatabaseReference ref = db.getReference(id);
            Interviewee interviewee = new Interviewee(int_id, name, number, age, answer1, answer2,answer3);
            ref.child(number).setValue(interviewee);
            toast = Toast.makeText(Confirm.this,
                    "Информация записана!", Toast.LENGTH_SHORT);
            toast.show();
            int_id = "";

        }else{
            Toast toast = Toast.makeText(Confirm.this,
                    "Коды не совпадают", Toast.LENGTH_SHORT);
            toast.show();
        }

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
