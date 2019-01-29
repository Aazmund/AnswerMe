package com.example.alex_s.answerme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    String answer1 = "";
    String answer2 = "";
    String answer3 = "";
    String id;

    CheckBox cb1_1, cb1_2, cb1_3;
    CheckBox cb2_1, cb2_2, cb2_3;
    CheckBox cb3_1, cb3_2;
    EditText et_1, et_2, et_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }

    public void cont_reg(View view) {

        cb1_1 = (CheckBox) findViewById(R.id.cb_answer1_1);
        cb1_2 = (CheckBox) findViewById(R.id.cb_answer1_2);
        cb1_3 = (CheckBox) findViewById(R.id.cb_answer1_3);
        cb2_1 = (CheckBox) findViewById(R.id.cb_answer2_1);
        cb2_2 = (CheckBox) findViewById(R.id.cb_answer2_2);
        cb2_3 = (CheckBox) findViewById(R.id.cb_answer2_3);
        cb3_1 = (CheckBox) findViewById(R.id.cb_answer3_1);
        cb3_2 = (CheckBox) findViewById(R.id.cb_answer3_2);

        et_1 = (EditText) findViewById(R.id.et_answer1);
        et_2 = (EditText) findViewById(R.id.et_answer2);
        et_3 = (EditText) findViewById(R.id.et_answer3);

        if (cb1_1.isChecked() == true){answer1 += "Торговый центр ";}
        if (cb1_2.isChecked() == true){answer1 += "Рынок ";}
        if (cb1_3.isChecked() == true){answer1 += "Интернет ";}
        answer1 += et_1.getText().toString();

        if (cb2_1.isChecked() == true){answer2 += "Хорошая цена и качество ";}
        if (cb2_2.isChecked() == true){answer2 += "Близкое расположение к дому ";}
        if (cb2_3.isChecked() == true){answer2 += "Хороший выбор продукции ";}
        answer2 += et_2.getText().toString();

        if (cb3_1.isChecked() == true){answer3 += "Неквалифицированный персонал ";}
        if (cb3_2.isChecked() == true){answer3 += "Мало акций ";}
        answer3 += et_3.getText().toString();

        Intent intent = new Intent(MainActivity.this, int_reg.class);
        intent.putExtra("answer1", answer1);
        intent.putExtra("answer2", answer2);
        intent.putExtra("answer3", answer3);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}

