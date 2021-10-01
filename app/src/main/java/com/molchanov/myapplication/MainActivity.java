package com.molchanov.myapplication;

import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    private TextInputLayout textInputName;
    private TextInputLayout textInputSurname;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputPassword2;
    private TextInputLayout textInputDate;
    Calendar dateAndTime = Calendar.getInstance();
    private SharedPreferences pref;
    private final String save_name = "save_name";
    private final String save_surname = "save_surname";
    private final String save_password = "save_password";
    private final String save_date = "save_date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        fillGaps();
    }

    private void init() {
        textInputName = findViewById(R.id.text_input_name);
        textInputSurname = findViewById(R.id.text_input_surname);
        textInputPassword = findViewById(R.id.text_input_password);
        textInputPassword2 = findViewById(R.id.text_input_password2);
        textInputDate = findViewById(R.id.text_input_date);
        pref = getSharedPreferences("personData", MODE_PRIVATE);
    }

    private boolean validateDate() {
        String dateInput = textInputDate.getEditText().getText().toString().trim();
        //if (dateAndTime.get(Calendar.YEAR) >= Calendar.getInstance().getTimeInMillis()) {
        if(dateInput.isEmpty()) {
            textInputDate.setError("Дата не выбрана");
            return false;
        } else if (dateAndTime.compareTo(Calendar.getInstance()) >0) {
            textInputDate.setError("Вы еще не родились");
            return false;
        } else {
            textInputDate.setError(null);
            return true;
        }
    }

    private boolean validateName() {
        String nameInput = textInputName.getEditText().getText().toString().trim();
        String surnameInput = textInputSurname.getEditText().getText().toString().trim();
        boolean check;

        if (surnameInput.isEmpty()) {
            textInputSurname.setError(getString(R.string.rusErrorEmpty));
            check = false;
        } else if (nameInput.length() > 30) {
            textInputSurname.setError(getString(R.string.rusErrorToolong));
            check = false;
        } else {
            textInputSurname.setError(null);
            check = true;
        }

        if (nameInput.isEmpty()) {
            textInputName.setError(getString(R.string.rusErrorEmpty));
            check = false;
        } else if (nameInput.length() > 30) {
            textInputName.setError(getString(R.string.rusErrorToolong));
            check = false;
        } else {
            textInputName.setError(null);
            check = true;
        }
        return check;
    }

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError(getString(R.string.rusErrorEmpty));
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            textInputPassword.setError(getString(R.string.rusErrorPasswordWeak));
            return false;
        } else if (!textInputPassword.getEditText().getText().toString().equals(textInputPassword2.getEditText().getText().toString())) {
            textInputPassword.setError(null);
            textInputPassword2.setError(getString(R.string.rusErrorPasswordNE));
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }

    public void setDate (View v) {
        new DatePickerDialog(MainActivity.this,d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setInitialDateTime() {
        textInputDate.getEditText().setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    public void confirmInput(View v) {
        if (!validateDate() | !validateName() | !validatePassword()) {
            return;
        }
        savedPref();
        Intent intent = new Intent(this, HelloActivity.class);
        intent.putExtra("name", textInputName.getEditText().getText().toString());
        intent.putExtra("surname", textInputSurname.getEditText().getText().toString());
        startActivity(intent);
    }

    public void fillGaps () {
        textInputName.getEditText().setText(pref.getString(save_name, ""));
        textInputSurname.getEditText().setText(pref.getString(save_surname, ""));
        textInputDate.getEditText().setText(pref.getString(save_date, ""));
        textInputPassword.getEditText().setText(pref.getString(save_password, ""));
        textInputPassword2.getEditText().setText(pref.getString(save_password, ""));
    }

    public void savedPref() {
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(save_name,textInputName.getEditText().getText().toString());
        edit.putString(save_surname,textInputSurname.getEditText().getText().toString());
        edit.putString(save_password,textInputPassword.getEditText().getText().toString());
        edit.putString(save_date,textInputDate.getEditText().getText().toString());
        edit.apply();
    }
}
