package com.example.ziade.notagain;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    private String _contactName = "";
    private int _hour = -1;
    private int _minute = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView endTime = (TextView) findViewById(R.id.end_time);
        displayTime();

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Time Picker
                TimePickerDialog timePicker = new TimePickerDialog(MainActivity.this, R.style.DatePickerStyle, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        _hour = hour;
                        _minute = minute;

                        displayTime();
                    }
                }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, false);
                timePicker.setTitle("Drunk count down");
                timePicker.show();
            }
        });


        Button gitDrunk = (Button) findViewById(R.id.git_drunk);
        gitDrunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (_hour != -1 && !_contactName.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).setMessage("Make sure to set an end time and an emergency contact").show();
                }
            }
        });
    }



    private void displayTime() {
        TextView endTime = (TextView) findViewById(R.id.end_time);

        if (_hour != -1 && _minute != -1) {

            StringBuilder time = new StringBuilder();
            time.append((_hour % 12 == 0) ? 12 : _hour % 12); //inline ifs for the win!!!
            time.append(":");
            if (_minute < 10) {
                time.append("0");
            }
            time.append(_minute);
            if (_hour >= 12) {
                time.append(" pm");
            } else {
                time.append(" am");
            }
            endTime.setText(time.toString());
        } else {
            endTime.setText("Click to set time");
        }
    }

}

