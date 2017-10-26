package com.example.blockcall;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Group 6
 * Created by joeym on 4/26/2017.
 * Joseph Mitchell -2594333
 * Ziad El Ghoul -2644641
 */

public class LockScreen extends Activity {


    boolean open = false;
    int minute;
    int realHour;
    int realMinute;
    int hour;
    boolean timeLess = false;
    int secondsHolder;
    int totalSecondsReal;
    Date unlockTime;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        minute = Calendar.getInstance().get(Calendar.MINUTE);
        //TODO do i want this to be hour of day or jsut hour (24 hour time or 12)
        hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        day = Calendar.getInstance().get(Calendar.DATE);

        Log.i("THIS SHOULD BE THE TIME", "" + hour + minute);

        int hourHolder = getIntent().getIntExtra("HOUR", 0);
        int minuteHolder = getIntent().getIntExtra("MINUTE", 0);

        unlockTime = new Date(2017-1900,3,28, hourHolder, minuteHolder);

        Log.i("This is unlockTime",""+unlockTime.toString());

      //  Log.i("HELLO I GOT HERE HOUR", "" + hourHolder);
       // Log.i("HELLO I GOT HERE MINUTE", "" + minuteHolder);

        realHour = hourHolder;
        realMinute = minuteHolder;
        //converting input time to seconds
        secondsHolder = realHour * 60 * 60;
        totalSecondsReal += secondsHolder;
        secondsHolder = realMinute * 60;
        totalSecondsReal +=secondsHolder;

        final Button unlock = (Button) findViewById(R.id.unlockButton);

        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  if(realHour>=hour && realMinute>=minute) {
                int totalSeconds=0;
                minute = Calendar.getInstance().get(Calendar.MINUTE);
                hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                long currentTime = System.currentTimeMillis();
                unlockTime.getTime();
                Log.i("Current time via milli",""+currentTime);
                Log.i("Unlock.getTime",""+unlockTime.getTime());
                //converting real time to seconds

                secondsHolder = hour * 60 * 60;
                totalSeconds +=secondsHolder;
                secondsHolder = minute * 60;
                totalSeconds +=secondsHolder;


//Real = input Regular = live
                //if inputed time is less then live time
                if (totalSecondsReal < totalSeconds) {
                    timeLess = true;
                }
                //if timeLess is false continue to regular loop
                if (!timeLess) {
                    if (unlockTime.getTime() <= currentTime) {
                        Log.i("IF LOOP REAL Seconds", "" + totalSecondsReal);
                        Log.i("IF LOOP LIVE Seconds", "" + totalSeconds);
                        open = true;
                        startActivity(new Intent(LockScreen.this, ButtonsAndCalls.class));

                    } else {
                        Toast.makeText(LockScreen.this, "You must wait until your set time to exit lock down the time you set was " + realHour + ":" + realMinute,
                                Toast.LENGTH_LONG).show();
                        Log.i("ELSE LOOP REAL Seconds", "" + totalSecondsReal);
                        Log.i("ELSE LOOP LIVE Seconds", "" + totalSeconds);

                    }
                    //if time time is less than live
                } else {
                    Toast.makeText(LockScreen.this, "You must wait until your set time to exit lock down the time you set was " + realHour + ":" + realMinute,
                            Toast.LENGTH_LONG).show();
                    if (totalSecondsReal <= totalSeconds) {
                        timeLess = false;
                        Log.i("Wait until tomorow", "" + totalSecondsReal);
                        Log.i("until tomorow loop", "" + totalSeconds);
                    }
                }
            }
        });

    }


    //ON BACK BUTTON PRESSED
    @Override
    public void onBackPressed() {

        AlertDialog diaBox = AskOption();
        diaBox.show();

    }


    //ON CLICK OF BACK BUTTON
    private AlertDialog AskOption() {

        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // .setTitle("Exit")
                .setMessage("You are currently locked down - wait until set time please")
                // .setIcon(R.drawable.delete)
                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                })

                .create();
        return myQuittingDialogBox;


    }

    //ON CLICK OF HOME BUTTON BRING BACK UP OR RECENTS BUTTON
    @Override
    protected void onPause() {

        super.onPause();
        if (!open) {
            startActivity(new Intent(getApplicationContext(), LockScreen.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            finish();
        } else if (open) {
            startActivity(new Intent(getApplicationContext(), ButtonsAndCalls.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            finish();
        }


    }


}
