package com.example.blockcall;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Group 6
 * Created by joeym on 4/26/2017.
 * Joseph Mitchell -2594333
 * Ziad El Ghoul -2644641
 */

public class ListOfNumbers extends Activity implements OnClickListener {
    private DatabaseCalls mDbAdapter;
    private EditText numbr;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mDbAdapter = new DatabaseCalls(this);
        mDbAdapter.open();


        numbr = (EditText) findViewById(R.id.editNumber);
        btnAdd = (Button) findViewById(R.id.btnSave);
        btnAdd.setOnClickListener(this);

    }
//on click of empty number ask for number and then push to database
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSave:
                if ((numbr.getText().toString() != null) && (numbr.getText().toString().length() >= 7)) {
                    mDbAdapter.createReminder(numbr.getText().toString(), "", "");
                    mDbAdapter.close();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a full phone number", Toast.LENGTH_SHORT).show();
                }

            default:
                break;
        }
    }
//on delete destroy number
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDbAdapter.close();
    }

}
