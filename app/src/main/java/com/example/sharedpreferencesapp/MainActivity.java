package com.example.sharedpreferencesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();

    private DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(view);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("CREATE", "onCreate");

        String name, email, pass, date;

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String defaultValue = "";
        name = sharedPref.getString("name", defaultValue);
        email = sharedPref.getString("email", defaultValue);
        pass = sharedPref.getString("pass", defaultValue);
        date = sharedPref.getString("date", defaultValue);

        ((EditText) findViewById(R.id.et_name)).setText(name);
        ((EditText) findViewById(R.id.et_email)).setText(email);
        ((EditText) findViewById(R.id.et_password)).setText(pass);
        if(!date.equals("")){
            ((TextView) findViewById(R.id.tv_birthdate)).setText(date);
        }

        if(!name.equals("") || !email.equals("") || !pass.equals("") || !date.equals("")) {
            ((CheckBox) findViewById(R.id.cb_remember)).setChecked(true);
        }

    }

    public void openDatePicker(View view) {
        Log.d("DATEPICKER", "here");
        (new DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)))
                .show();

    }

    private void updateLabel(DatePicker view) {
        Log.d("LABEL", "updateLabel");
        ((TextView) findViewById(R.id.tv_birthdate)).setText(view.getYear() + "." + view.getMonth() + "." + view.getDayOfMonth() + ".");
    }

    public void writeSharedPref(View view) {
        Log.d("SHARED", "writeSharedPref");
        if(!((CheckBox) findViewById(R.id.cb_remember)).isChecked()) {
            return;
        }

        String name, email, pass, date;

        name = ((TextView) findViewById(R.id.et_name)).getText().toString();
        email = ((TextView) findViewById(R.id.et_email)).getText().toString();
        pass = ((TextView) findViewById(R.id.et_password)).getText().toString();
        date = ((TextView) findViewById(R.id.tv_birthdate)).getText().toString();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("pass", pass);
        editor.putString("date", date);
        editor.commit();

        Toast.makeText(this, "Data with the following name saved: " + name, Toast.LENGTH_LONG).show();

    }
}
