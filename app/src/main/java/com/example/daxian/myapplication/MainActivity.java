package com.example.daxian.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

    EditText phone1,phone2,phone3;
    String  ph1,ph2,ph3;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configure);

        preferences = getSharedPreferences("phoneconfig",MODE_PRIVATE);
        phone1 = (EditText) findViewById(R.id.qinphone1);
        phone2 = (EditText) findViewById(R.id.qinphone2);
        phone3 = (EditText) findViewById(R.id.qinphone3);
       if(preferences!=null) {
           ph1 = preferences.getString("phone1", null);
           ph2 = preferences.getString("phone2", null);
           ph3 = preferences.getString("phone3", null);



           phone1.setText(ph1);

           phone2.setText(ph2);

           phone3.setText(ph3);
       }
        Button saveconf = (Button) findViewById(R.id.saveconf);
        saveconf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = preferences.edit();

                editor.putString("phone1",phone1.getText().toString());
                editor.putString("phone2",phone2.getText().toString());
                editor.putString("phone3",phone3.getText().toString());
                editor.commit();


                final Intent intent = new Intent();
                intent.setAction("com.example.daxian.myapplication.MyService");
                startService(intent);
            }
        });

    }


}
