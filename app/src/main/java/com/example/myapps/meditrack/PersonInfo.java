package com.example.myapps.meditrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapps.meditrack.Helper.AppPrefManager;

public class PersonInfo extends AppCompatActivity {
    EditText edtPersonName,edtPersonAge;
    Button btnSubmit;
    AppPrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        edtPersonName=(EditText)findViewById(R.id.edt_person_name);
        edtPersonAge=(EditText)findViewById(R.id.edt_person_age);
        btnSubmit=(Button)findViewById(R.id.btn_submit_user_info);
        prefManager=new AppPrefManager(this);
        if(getIntent().getExtras()!=null){
            edtPersonName.setText(getIntent().getStringExtra("name"));
            edtPersonAge.setText(getIntent().getStringExtra("age"));
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!((edtPersonName.getText().toString().isEmpty())||(edtPersonAge.getText().toString().isEmpty())))
                {
                        prefManager.createLogin(edtPersonName.getText().toString(),edtPersonAge.getText().toString(),"");
                    if(getIntent().getExtras()!=null){
                        finish();
                    }else {
                        Intent sendMainScreen = new Intent(PersonInfo.this, SOSEntryScreen.class);
                        startActivity(sendMainScreen);
                        finish();
                    }
                }else {
                    Toast.makeText(PersonInfo.this,"Please enter the details!",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
