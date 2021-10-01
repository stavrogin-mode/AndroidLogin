package com.molchanov.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HelloActivity extends AppCompatActivity {

    //private TextView textResult;
    private String exName;
    private String exSurname;
    //private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        //textResult = findViewById(R.id.textView);
        Bundle arguments = getIntent().getExtras();
        exName = arguments.get("name").toString();
        exSurname = arguments.get("surname").toString();

    }

    public void showResult(View view) {
        //textResult.setText("Привет, " + exName);
        openDialog();

    }

    public void openDialog(){
        CustomDialog mDialog = new CustomDialog();
        Bundle args = new Bundle();
        args.putString("exName", exName);
        args.putString("exSurname", exSurname);
        mDialog.setArguments(args);
        mDialog.show(getSupportFragmentManager(), "example dialog");
    }
}