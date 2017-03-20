package com.premsuraj.cricketstats.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by Premsuraj
 */

public class DatePicker extends DialogFragment {
    DatePickerDialog.OnDateSetListener listener;

    public DatePicker(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    public DatePicker() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }
}
