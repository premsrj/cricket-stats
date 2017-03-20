package com.premsuraj.cricketstats.addedit;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.premsuraj.cricketstats.InningsData;
import com.premsuraj.cricketstats.R;
import com.premsuraj.cricketstats.databinding.ActivityAddEditBinding;
import com.premsuraj.cricketstats.utils.DatePicker;

import java.util.Calendar;

import static com.premsuraj.cricketstats.statistics.InningsHelper.isNullOrEmpty;

public class AddEditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String KEY_INNINGS_DATA = "innings_data";
    Spinner outSpinner;
    private InningsData innings;
    private View.OnClickListener dateClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DialogFragment datePicker = new DatePicker(AddEditActivity.this);
            datePicker.show(getFragmentManager(), "datePicker");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            innings = (getIntent().getSerializableExtra(KEY_INNINGS_DATA) != null
                    && getIntent().getSerializableExtra(KEY_INNINGS_DATA) instanceof InningsData) ?
                    (InningsData) getIntent().getSerializableExtra(KEY_INNINGS_DATA) :
                    new InningsData();
        }

        ActivityAddEditBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_add_edit, null, false);
        binding.setInnings(innings);
        setContentView(binding.getRoot());
        outSpinner = ((Spinner) findViewById(R.id.out));
        outSpinner.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.outs)));
        outSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                innings.setOut(getResources().getStringArray(R.array.outs)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        updateDate();
    }

    private void updateDate() {
        findViewById(R.id.date).setOnClickListener(dateClicked);

        if (isNullOrEmpty(innings.getDate())) {
            innings.setDateFromCalendar(Calendar.getInstance());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            saveValues();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveValues() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference scores = database.getReference("scores");
        scores.push().setValue(innings);
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        innings.setDateFromCalendar(calendar);
        TextView date = ((TextView) findViewById(R.id.date));
        date.setText(innings.getDate());
    }
}
