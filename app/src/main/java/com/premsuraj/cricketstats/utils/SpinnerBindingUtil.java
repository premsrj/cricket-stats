package com.premsuraj.cricketstats.utils;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

/**
 * Created by Premsuraj
 */

public class SpinnerBindingUtil {

    @InverseBindingAdapter(attribute = "selection", event = "selectionAttrChanged")
    public static String getSelectedValue(AdapterView view) {
        return (String) view.getSelectedItem();
    }

    @BindingAdapter(value = {"selection", "selectionAttrChanged", "adapter"}, requireAll = false)
    public static void setAdapter(final AdapterView view, String newSelection, final InverseBindingListener bindingListener, final ArrayAdapter adapter) {
        view.setAdapter(adapter);
        view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bindingListener.onChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                view.setSelection(0);
            }
        });
        if (newSelection != null) {
            int pos = ((ArrayAdapter) view.getAdapter()).getPosition(newSelection);
            view.setSelection(pos);
        }
    }
}
