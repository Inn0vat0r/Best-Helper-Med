package com.example.myapps.meditrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapps.meditrack.Helper.AppPrefManager;

public class SOSEntryScreen extends AppCompatActivity {

    EditText edtSOSName,edtSOSMobNo;
    Button btnDone;
    AppPrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sosentry_screen);
        edtSOSName=(EditText)findViewById(R.id.edt_sos_name);
        edtSOSMobNo=(EditText)findViewById(R.id.edt_sos_mob);
        btnDone=(Button)findViewById(R.id.btn_submit_sos_info);
        prefManager=new AppPrefManager(this);
        if(getIntent().getExtras()!=null){
            edtSOSName.setText(getIntent().getStringExtra("name"));
            edtSOSMobNo.setText(getIntent().getStringExtra("mob"));
        }
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!((edtSOSName.getText().toString().isEmpty())||(edtSOSMobNo.getText().toString().isEmpty())))
                {
                    prefManager.setSOSPersonName(edtSOSName.getText().toString());
                    if((edtSOSMobNo.getText().toString().length()==10)||(edtSOSMobNo.getText().toString().length()==11)) {
                        prefManager.setSOSMobileNumber(edtSOSMobNo.getText().toString());
                        if(getIntent().getExtras()!=null){
                            finish();
                        }
                        else{
                            Intent sendMainScreen = new Intent(SOSEntryScreen.this, HomeScreen.class);
                            startActivity(sendMainScreen);
                            finish();
                        }
                    }else {
                        Toast.makeText(SOSEntryScreen.this,"Please enter correct mobile number!",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(SOSEntryScreen.this,"Please enter the details!",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
