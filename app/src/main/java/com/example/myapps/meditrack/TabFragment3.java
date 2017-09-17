package com.example.myapps.meditrack;

import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapps.meditrack.Helper.AppPrefManager;
import com.example.myapps.meditrack.Helper.MedicineDoseContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class TabFragment3 extends Fragment {
    EditText edtMedName,edtMedNum,edtDoseNum,edtMedNumPur;
    Spinner spDoseType;
    Button btnSubmit;
    TextView setDoseTime;
    int  mHour, mMinute;
    TextView userName,userAge;
    TextView sosName,sosMob;
    AppPrefManager prefManager;
    HashMap<String, String> profile;
    String name,age;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View view=inflater.inflate(R.layout.tab_fragment3, container, false);
        prefManager=new AppPrefManager(getActivity());
        profile = prefManager.getUserDetails();
        userName=(TextView)view.findViewById(R.id.txt_user_name);
        userAge=(TextView)view.findViewById(R.id.txt_user_age);
        sosName=(TextView)view.findViewById(R.id.txt_sos_name);
        sosMob=(TextView)view.findViewById(R.id.txt_sos_mob);
        edtMedName=(EditText)view.findViewById(R.id.edt_medicine_name);
        edtDoseNum=(EditText)view.findViewById(R.id.edt_dose_per_day);
        edtMedNum=(EditText)view.findViewById(R.id.edt_tablet_num);
        edtMedNumPur=(EditText)view.findViewById(R.id.edt_medicine_buy_num);
        spDoseType=(Spinner)view.findViewById(R.id.sp_dose_frequency);
        btnSubmit=(Button)view.findViewById(R.id.btn_submit);
        setDoseTime=(TextView) view.findViewById(R.id.set_dose_time);
        name=profile.get("name");
        userName.setText(name);
        age=profile.get("age") +" years";
        userAge.setText(age);
        String sos=prefManager.getSOSPersonName();
        sosName.setText(sos);
        sosMob.setText(prefManager.getSOSMobileNumber());
        setDoseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DataValidation()){
                    showAlert(true);
                }else showAlert(false);
            }
        });
        userAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent=new Intent(getActivity(),PersonInfo.class);
                editIntent.putExtra("name",name);
                editIntent.putExtra("age",age);
                getActivity().startActivity(editIntent);
            }
        });
        sosMob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent=new Intent(getActivity(),SOSEntryScreen.class);
                editIntent.putExtra("name",prefManager.getSOSPersonName());
                editIntent.putExtra("mob",prefManager.getSOSMobileNumber());
                getActivity().startActivity(editIntent);
            }
        });
        return view;
    }
    void SubmitData(){
        ContentValues values = new ContentValues();
        values.put(MedicineDoseContract.MedicineDoseEntry.COLUMN_MEDICINE_NAME,edtMedName.getText().toString());
        values.put(MedicineDoseContract.MedicineDoseEntry.COLUMN_DOSE_FREQUENCY,spDoseType.getSelectedItem().toString());
        values.put(MedicineDoseContract.MedicineDoseEntry.COLUMN_NUMBER_OF_DOSE,Integer.parseInt(edtDoseNum.getText().toString()));
        values.put(MedicineDoseContract.MedicineDoseEntry.COLUMN_MEDICINE_QUANTITY,Integer.parseInt(edtMedNum.getText().toString()));
        values.put(MedicineDoseContract.MedicineDoseEntry.COLUMN_MEDICINE_PURCHASED_NUM,Integer.parseInt(edtMedNumPur.getText().toString()));
        values.put(MedicineDoseContract.MedicineDoseEntry.COLUMN_DOSE_TIME,setDoseTime.getText().toString());
        Uri uri = getActivity().getContentResolver().insert(MedicineDoseContract.MedicineDoseEntry.CONTENT_URI, values);

        long   medicineId = ContentUris.parseId(uri);
        Toast.makeText(getActivity(), "Your have added medicine with ID:" + medicineId, Toast.LENGTH_SHORT).show();
        clearAll();

    }

    private void clearAll() {
        edtMedName.setText("");
        edtDoseNum.setText("");
        edtMedNum.setText("");
        edtMedNumPur.setText("");
        setDoseTime.setText(getResources().getString(R.string.set_time));
        spDoseType.setSelection(0);
    }

    boolean DataValidation(){
        return (!(edtMedName.getText().toString().isEmpty()||edtDoseNum.getText().toString().isEmpty()
        ||edtMedNum.getText().toString().isEmpty()||edtMedNumPur.getText().toString().isEmpty()||setDoseTime.getText().toString().isEmpty()));
    }
    void showAlert(boolean checkExpiry) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Medicine Board");
        if (checkExpiry) {
            builder.setMessage(getActivity().getResources().getString(R.string.expiry_alert));
            builder.setPositiveButton("Submit",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            SubmitData();
                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                }
                            }).create();
        } else {
            builder.setMessage(getActivity().getResources().getString(R.string.validate_alert));
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }
    private void pickTime() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),

                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        updateTime(hourOfDay,minute);

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";
        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String pickedTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        setDoseTime.setText(pickedTime);
    }
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        userName.setText("");
        userAge.setText("");
        name=profile.get("name");
        userName.setText(name);
        age=profile.get("age") +" years";
        userAge.setText(age);
        String sos=prefManager.getSOSPersonName();
        sosName.setText(sos);
        sosMob.setText(prefManager.getSOSMobileNumber());
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_main, menu);
    }
}
