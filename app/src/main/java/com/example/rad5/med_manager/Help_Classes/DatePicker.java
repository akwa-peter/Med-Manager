package com.example.rad5.med_manager.Help_Classes;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by akwa on 4/4/18.
 */

public class DatePicker implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText _editText;
    private int _day;
    private int _month;
    private int _birthYear;
    private Context _context;

    private Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

    //create the DatePicker constructor to take @params context and edit text view id
    public DatePicker(Context context, int editTextViewID) {
        Activity act = (Activity)context;
        this._editText = (EditText)act.findViewById(editTextViewID);
        this._editText.setOnClickListener(this);
        this._context = context;
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        _birthYear = year;
        _month = month;
        _day = day;
        updateDisplay();
    }

    @Override
    public void onClick(View view) {

        DatePickerDialog dialog = new DatePickerDialog(_context, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }


    // updates the date in the birth date EditText
    private void updateDisplay() {

        _editText.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(_day).append("/").append(_month + 1).append("/").append(_birthYear).append(" "));

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        _editText.setText(sdf.format(calendar.getTime()));
    }

}
