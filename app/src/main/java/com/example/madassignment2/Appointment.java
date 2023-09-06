package com.example.madassignment2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Appointment extends AppCompatActivity {

    EditText et_appointment_Doc,et_appointment_Date,et_appointment_Time;
    Button bt_add_appointment;
    TextView tv_test;
    private db_appointment appointment_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        et_appointment_Doc=findViewById(R.id.et_appointment_doc);
        et_appointment_Date=findViewById(R.id.et_appointment_date);
        et_appointment_Time=findViewById(R.id.et_appointment_time);

        bt_add_appointment=findViewById(R.id.bt_appointment);

        tv_test=findViewById(R.id.result_display);

        appointment_db=new db_appointment(this);
        appointment_db.opentoWrite();


        et_appointment_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        et_appointment_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        bt_add_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_appointment_Doc.getText().toString().equals(""))
                {
                    Toast.makeText(Appointment.this, "Input name of doctor", Toast.LENGTH_SHORT).show();
                }

                else if (et_appointment_Date.getText().toString().equals(""))
                {
                    Toast.makeText(Appointment.this, "Input date of appointment", Toast.LENGTH_SHORT).show();
                }

                else if (et_appointment_Time.getText().toString().equals(""))
                {
                    Toast.makeText(Appointment.this, "Input time of appointment", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    appointment_db.insert_appointment(et_appointment_Doc.getText().toString(),et_appointment_Date.getText().toString(),et_appointment_Time.getText().toString());
                    Toast.makeText(Appointment.this,"Done Inputting Data",Toast.LENGTH_SHORT).show();

                }
            }
        });


        appointment_db.opentoRead();
        String contentread = appointment_db.queueAll();
        tv_test.setText(contentread);

    }
    private void showDatePickerDialog()
    {
        DatePickerDialog datePicker = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    //@SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker timePicker, int year, int month, int day) {
                        //String date = day+"/"+(month+1)+"/"+year;
                        et_appointment_Date.setText(day+"/"+(month+1)+"/"+year);
                        //Toast.makeText(Appointment.this,et_appointment_Date.getText().toString(),Toast.LENGTH_SHORT).show();
                    }
                },2023,0,1);
        datePicker.show();
    }

    private void showTimePickerDialog()
    {
        TimePickerDialog timePicker = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    //@SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        //String time = i+":"+i1;
                        et_appointment_Time.setText(i+":"+i1);
                        //Toast.makeText(Appointment.this,"Done Inputting Data",Toast.LENGTH_SHORT).show();
                    }
                },12,0,true);
        timePicker.show();
    }
}