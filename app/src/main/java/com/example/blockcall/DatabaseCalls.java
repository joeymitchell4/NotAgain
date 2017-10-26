package com.example.blockcall;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Group 6
 * Created by joeym on 4/26/2017.
 * Joseph Mitchell -2594333
 * Ziad El Ghoul -2644641
 */

public class DatabaseCalls {

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "reminders";
    private static final int DATABASE_VERSION = 1;

    public static final String KEY_TITLE = "title";
    public static final String KEY_MODE = "mode";
    public static final String KEY_DATE_TIME = "reminder_date_time";
    public static final String KEY_ROWID = "d";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " ("
                    + KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_TITLE + " text not null, "
                    + KEY_MODE + " text not null, "
                    + KEY_DATE_TIME + " text not null);";
    private final Context mCtx;

    public DatabaseCalls(Context ctx) {
        this.mCtx = ctx;
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public DatabaseCalls open() throws android.database.SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    public long createReminder(String title, String mode, String reminderDateTime) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_MODE, mode);
        initialValues.put(KEY_DATE_TIME, reminderDateTime);
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }


    public boolean deleteReminder(long rowId) {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }




    public Cursor fetchAllReminders() {
        return mDb.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_TITLE,
                KEY_MODE, KEY_DATE_TIME}, null, null, null, null, null);
    }






}
