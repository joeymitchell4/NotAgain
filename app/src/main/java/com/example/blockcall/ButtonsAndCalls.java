package com.example.blockcall;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


/**
 * Group 6
 * Created by joeym on 4/26/2017.
 * Joseph Mitchell -2594333
 * Ziad El Ghoul -2644641
 *
 */


public class ButtonsAndCalls extends Activity implements RadioGroup.OnCheckedChangeListener, OnClickListener {
    //WindowManager mDPM;
    String AMPM = "";
    private SharedPreferences myPrefs;
    private SharedPreferences.Editor editor;

    private RadioButton allBlock;
    private RadioButton blockNone;

    private Button btnTimePicker;
    private EditText txtTime;
    private int mHour, mMinute;

    //private Button btnAddNumber;
    //private Button btnShowList;

    private Button lockDown;

    int hourHolder;
    int minuteHolder;


    @Override
    //Declare all of my buttons and set thier listners
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
        editor = myPrefs.edit();
        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.radGroup1);

        radiogroup.setOnCheckedChangeListener(this);
        lockDown = (Button) findViewById(R.id.LockDown);
        allBlock = (RadioButton) findViewById(R.id.blockAll);

        blockNone = (RadioButton) findViewById(R.id.blockNone);

        btnTimePicker = (Button) findViewById(R.id.btn_time);
        txtTime = (EditText) findViewById(R.id.in_time);


        setDefaultButtonChecked();
        btnTimePicker.setOnClickListener(this);
        lockDown.setOnClickListener(this);
    }
//turns buttons on and off
    private void setDefaultButtonChecked() {
        String value = getSharedPreferences();
        if (value.equals("all")) {
            allBlock.setChecked(true);
            blockNone.setChecked(false);
        } else {
            allBlock.setChecked(false);
            blockNone.setChecked(true);
        }
    }
//chooses which radio button is turned on
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.blockAll:
                editor.putString("mode", "all");
                editor.commit();
                break;
            case R.id.blockNone:
                editor.putString("mode", "none");
                editor.commit();
                break;
        }
    }
//blow up menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
//for overhead menu itemson top right to send to other pages to add numbers
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent i = new Intent(this, ListOfNumbers.class);
                startActivity(i);
                break;
            case R.id.menu_show:
                Intent ii = new Intent(this, MainActivity.class);
                startActivity(ii);
                break;
        }
        return true;
    }

    private String getSharedPreferences() {
        myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
        String value = myPrefs.getString("mode", "not");
        return value;
    }
//on click switch case statement for all of my buttons
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_time:
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
                                hourHolder = hourOfDay;
                                minuteHolder = minute;

                                Log.i("ButtonsAndCalls HOUR", "" + mHour);
                                Log.i("ButtonsAndCalls MIN", "" + mMinute);
                                if (hourOfDay >= 12) {
                                    AMPM = "PM";
                                }
                                if (hourOfDay < 12) {
                                    AMPM = "AM";
                                }
                                if (hourOfDay > 12) {
                                    hourOfDay = hourOfDay - 12;
                                }
                                if (minute < 10) {

                                    txtTime.setText(hourOfDay + ":0" + minute + " " + AMPM);

                                } else {
                                    txtTime.setText(hourOfDay + ":" + minute + " " + AMPM);
                                }
                            }

                        }, mHour, mMinute, false);
                timePickerDialog.show();

                break;
            //Setting lock down time
            case R.id.LockDown:
                if (hourHolder == 0) {
                    Toast.makeText(ButtonsAndCalls.this, "Please select a time to begin lock down",
                            Toast.LENGTH_LONG).show();
                    break;
                } else {
                    Log.i("ButtonsAndCalls b4 send", "" + hourHolder);
                    Log.i("ButtonsAndCalls b4 send", "" + minuteHolder);
                    Intent iii = new Intent(this, LockScreen.class);
                    iii.putExtra("HOUR", hourHolder);
                    iii.putExtra("MINUTE", minuteHolder);
                    startActivity(iii);
                    break;
                }


            default:
                break;
        }
    }
}
