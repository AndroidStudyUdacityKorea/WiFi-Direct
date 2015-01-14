package com.udacity.hackathon.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.udacity.hackathon.R;
import com.udacity.hackathon.WiFiDirectActivity;
import com.udacity.hackathon.util.PrefUtils;

public class NameInputActivity extends Activity {

    private Button btnJoin;
    private EditText txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_input);

        btnJoin = (Button) findViewById(R.id.btnJoin);
        txtName = (EditText) findViewById(R.id.name);

        getActionBar().hide();

        btnJoin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (txtName.getText().toString().trim().length() > 0) {

                    String name = txtName.getText().toString().trim();
                    PrefUtils.setString(getApplicationContext(), "name", name);

                    Intent intent = new Intent(NameInputActivity.this, WiFiDirectActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your name", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
