package com.example.blockcall;

import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;

import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Group 6
 * Created by joeym on 4/26/2017.
 * Joseph Mitchell -2594333
 * Ziad El Ghoul -2644641
 */


public class CallBlocking extends BroadcastReceiver {
    private static final int MODE_WORLD_READABLE = 1;
    private ITelephony telephonyService;
    private String incommingNumber;
    private SharedPreferences myPrefs;
//Pull the incoming number and if it is not equal to white lsit call telephony and block it
    @Override
    public void onReceive(Context context, Intent intent) {
        myPrefs = context.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
        String blockingMode = myPrefs.getString("mode", "not retrieved");
        if (blockingMode == "none") {
            return;
        }
        Bundle bb = intent.getExtras();
        String state = bb.getString(TelephonyManager.EXTRA_STATE);
        incommingNumber = bb.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

        if ((state != null) && (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING))) {

            incommingNumber = bb.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

            DatabaseCalls mDbAdapter = new DatabaseCalls(context);
            mDbAdapter.open();
            Cursor c = mDbAdapter.fetchAllReminders();

            Log.i("HELLO IM HERE", "HELLO IM HERE" + c.getCount());
            boolean isWhitlisted = false;

            while (c.moveToNext()) {
                Log.i("HELLO THIS IS A TEST", "HELLO THIS IS A TEST" + incommingNumber);

                String title = c.getString(c.getColumnIndex(DatabaseCalls.KEY_TITLE));
                if (title.equals(incommingNumber)) {
                    isWhitlisted = true;
                }
            }
            if (!isWhitlisted) {
                blockCall(context, bb);
            }
        }

    }

//calls telephony and uses its end call function
    public void blockCall(Context c, Bundle b) {

        TelephonyManager telephony = (TelephonyManager)
                c.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class cls = Class.forName(telephony.getClass().getName());
            Method m = cls.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            telephonyService = (ITelephony) m.invoke(telephony);
            telephonyService.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
