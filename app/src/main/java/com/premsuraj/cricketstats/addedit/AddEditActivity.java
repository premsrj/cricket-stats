package com.premsuraj.cricketstats.addedit;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.premsuraj.cricketstats.InningsData;
import com.premsuraj.cricketstats.R;
import com.premsuraj.cricketstats.databinding.ActivityAddEditBinding;

public class AddEditActivity extends AppCompatActivity {
    Spinner outSpinner;
    private InningsData innings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        innings = new InningsData();
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
                innings.out = getResources().getStringArray(R.array.outs)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
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
}
