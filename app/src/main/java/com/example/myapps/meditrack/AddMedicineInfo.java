package com.example.myapps.meditrack;

import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapps.meditrack.Helper.MedicineDoseContract;

import java.util.Calendar;

public class AddMedicineInfo extends AppCompatActivity {
    EditText edtMedName,edtMedNum,edtDoseNum,edtMedNumPur;
    Spinner spDoseType;
    Button btnSubmit;
    TextView setDoseTime;
    int  mHour, mMinute;
    String getID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine_info);
        edtMedName=(EditText)findViewById(R.id.edt_medicine_name);
        edtDoseNum=(EditText)findViewById(R.id.edt_dose_per_day);
        edtMedNum=(EditText)findViewById(R.id.edt_tablet_num);
        edtMedNumPur=(EditText)findViewById(R.id.edt_medicine_buy_num);
        spDoseType=(Spinner)findViewById(R.id.sp_dose_frequency);
        btnSubmit=(Button)findViewById(R.id.btn_submit);
        btnSubmit.setText(getResources().getString(R.string.update_details));
        setDoseTime=(TextView)findViewById(R.id.set_dose_time);
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
    }

    void SubmitData(){
        ContentValues values = new ContentValues();
        values.put(MedicineDoseContract.MedicineDoseEntry.COLUMN_MEDICINE_NAME,edtMedName.getText().toString());
        values.put(MedicineDoseContract.MedicineDoseEntry.COLUMN_DOSE_FREQUENCY,spDoseType.getSelectedItem().toString());
        values.put(MedicineDoseContract.MedicineDoseEntry.COLUMN_NUMBER_OF_DOSE,Integer.parseInt(edtDoseNum.getText().toString()));
        values.put(MedicineDoseContract.MedicineDoseEntry.COLUMN_MEDICINE_QUANTITY,Integer.parseInt(edtMedNum.getText().toString()));
        values.put(MedicineDoseContract.MedicineDoseEntry.COLUMN_MEDICINE_PURCHASED_NUM,Integer.parseInt(edtMedNumPur.getText().toString()));
        values.put(MedicineDoseContract.MedicineDoseEntry.COLUMN_DOSE_TIME,setDoseTime.getText().toString());
        Uri uri = getContentResolver().insert(MedicineDoseContract.MedicineDoseEntry.CONTENT_URI, values);

        long   medicineId = ContentUris.parseId(uri);
        Toast.makeText(this, "Your have added medicine with ID:" + medicineId, Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Medicine Board");
        if (checkExpiry) {
            builder.setMessage(getResources().getString(R.string.expiry_alert));
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
            builder.setMessage(getResources().getString(R.string.validate_alert));
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,

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
}
