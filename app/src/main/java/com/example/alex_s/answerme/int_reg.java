package com.example.alex_s.answerme;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;


public class int_reg extends AppCompatActivity {

    String answer1, answer2, answer3, id;
    String code = "";
    String int_name, int_age;

    String int_id = "";

    EditText number, name, age;
    Button send;
    String msg = "Для подтверждения опроса сообщите код: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_int_reg);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        answer1 = intent.getStringExtra("answer1");
        answer2 = intent.getStringExtra("answer2");
        answer3 = intent.getStringExtra("answer3");
        id = intent.getStringExtra("id");

        number = findViewById(R.id.int_number);
        send = findViewById(R.id.btn_add);
        name = findViewById(R.id.FIO);
        age = findViewById(R.id.age);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().length() == 0 || number.getText().toString().length() == 0 || age.getText().toString().length() == 0){
                    Toast toast = Toast.makeText(int_reg.this,
                            "есть незаполнненые поля", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    sendTextMessage();

                    Intent intent = new Intent(int_reg.this, Confirm.class);
                    intent.putExtra("answer1", answer1);
                    intent.putExtra("answer2", answer2);
                    intent.putExtra("answer3", answer3);
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("age", age.getText().toString());
                    intent.putExtra("number", number.getText().toString());
                    intent.putExtra("int_id", int_id);
                    intent.putExtra("id", id);


                    int_id = "";
                    startActivity(intent);
                }
            }
        });
    }

    protected void sendTextMessage(){
        int rnd_id = 0;
        Random r = new Random();
        rnd_id = r.nextInt(9899) + 100;
        int_id = Integer.toString(rnd_id);
        msg = msg + int_id;
        String phoneNumber = number.getText().toString();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
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
