package com.example.blockcall;

import java.util.ArrayList;


import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * Group 6
 * Created by joeym on 4/26/2017.
 * Joseph Mitchell -2594333
 * Ziad El Ghoul -2644641
 */

public class MainActivity extends android.app.ListActivity implements OnItemLongClickListener {
    /**
     * Called when the activity is first created.
     */

    private DatabaseCalls mDbAdapter;
    private ArrayList<String> numberList = null;
    private ArrayList<String> idList = null;
    private int clickItem;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_landing);
        numberList = new ArrayList<String>();
        idList = new ArrayList<String>();
        mDbAdapter = new DatabaseCalls(this);
        mDbAdapter.open();

        registerForContextMenu(getListView());
        displayLits();
    }

    public void displayLits() {

        Cursor c = mDbAdapter.fetchAllReminders();
        numberList.clear();
        idList.clear();
        startManagingCursor(c);
        c.moveToFirst();
        if (c.moveToFirst()) {

            while (c.isAfterLast() == false) {
                String id = c.getString(c.getColumnIndex(DatabaseCalls.KEY_ROWID));

                String name = c.getString(c
                        .getColumnIndex(DatabaseCalls.KEY_TITLE));
                numberList.add(name);
                idList.add(id);

                c.moveToNext();
            }
        }
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this, R.layout.activity_row, R.id.text1, numberList);
        setListAdapter(ad);

    }

    @Override
    public int getSelectedItemPosition() {
        return super.getSelectedItemPosition();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        clickItem = Integer.parseInt(idList.get(position));
        Toast.makeText(getBaseContext(), "number  " + clickItem, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_items, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_delete:

                Boolean bb = mDbAdapter.deleteReminder(clickItem);
                displayLits();
                return true;
            case R.id.menu_cancel:

                return true;
        }
        return super.onContextItemSelected(item);
    }

    public boolean onItemLongClick(AdapterView<?> arg, View arg1, int pos,
                                   long id) {
        clickItem = Integer.parseInt(idList.get(pos));
        Toast.makeText(getBaseContext(), "number  " + clickItem, Toast.LENGTH_SHORT).show();

        return true;
    }

}