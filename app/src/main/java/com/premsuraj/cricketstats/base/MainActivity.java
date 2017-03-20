package com.premsuraj.cricketstats.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.premsuraj.cricketstats.Constants;
import com.premsuraj.cricketstats.InningsData;
import com.premsuraj.cricketstats.R;
import com.premsuraj.cricketstats.addedit.AddEditActivity;
import com.premsuraj.cricketstats.login.GoogleLoginManager;
import com.premsuraj.cricketstats.navigation.NavigationContainerListener;
import com.premsuraj.cricketstats.navigation.NavigationManager;
import com.premsuraj.cricketstats.statistics.StatisticsEngine;
import com.premsuraj.cricketstats.utils.ObjectSerializer;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationContainerListener, GoogleLoginManager.LoginListener {

    private static final String TAG = "Main";
    public NavigationManager navigationManager;
    public StatisticsEngine statisticsEngine;
    public FloatingActionButton fab;
    GoogleLoginManager loginManager;
    ArrayList<InningsData> data;
    private TextView topScore;
    private TextView average;
    private TextView strikeRate;
    private TextView fours;
    private TextView sixes;
    private TextView teamPercent;
    private TextView fifties;
    private TextView hundreds;
    private TextView catchesTaken;
    private TextView runoutsMade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initFab();
        Toolbar toolbar = initViews();
        initDrawer(toolbar);
        initLogin();
        initNavigation();
        initFirebase();
    }

    private void initFirebase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_REF);
        scoresRef.keepSynced(true);
    }

    private void initNavigation() {
        navigationManager = new NavigationManager(this);
        navigationManager.initNavigationView((NavigationView) findViewById(R.id.nav_view));
    }

    private void initLogin() {
        loginManager = new GoogleLoginManager(this);
    }

    private void initDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addEditIntent = new Intent(MainActivity.this, AddEditActivity.class);
                addEditIntent.putStringArrayListExtra(AddEditActivity.KEY_OPPOSING_TEAMS_AUTOCOMPLETE,
                        new ArrayList<String>(statisticsEngine.getOpposingTeams()));
                startActivity(addEditIntent);
            }
        });
    }

    private Toolbar initViews() {
        topScore = (TextView) findViewById(R.id.top_score);
        fifties = (TextView) findViewById(R.id.fifties);
        hundreds = (TextView) findViewById(R.id.hundreds);
        average = (TextView) findViewById(R.id.average);
        strikeRate = (TextView) findViewById(R.id.strike_rate);
        fours = (TextView) findViewById(R.id.fours);
        sixes = (TextView) findViewById(R.id.sixes);
        teamPercent = (TextView) findViewById(R.id.team_score_percent);
        catchesTaken = (TextView) findViewById(R.id.catches_taken);
        runoutsMade = (TextView) findViewById(R.id.runouts_made);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void login() {
        loginManager.login();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();
        loginManager.removeAuthListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginManager.addAuthListener();

        checkLogin();
        fetchData();
    }

    private void fetchData() {
        fab.setVisibility(View.GONE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference scores = database.getReference(Constants.DATABASE_REF);
        scores.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data = new ArrayList<InningsData>((int) dataSnapshot.getChildrenCount());
                for (DataSnapshot childSnapshot :
                        dataSnapshot.getChildren()) {
                    InningsData innings = childSnapshot.getValue(InningsData.class);
                    data.add(innings);
                }

                statisticsEngine = new StatisticsEngine();
                statisticsEngine.process(data);
                topScore.setText(statisticsEngine.getHighScore());
                fifties.setText(statisticsEngine.getFifites());
                hundreds.setText(statisticsEngine.getHundreds());
                average.setText(statisticsEngine.getAverage());
                strikeRate.setText(statisticsEngine.getStrikeRate());
                fours.setText(statisticsEngine.getFours());
                sixes.setText(statisticsEngine.getSixes());
                teamPercent.setText(statisticsEngine.getTeamScorePercent());
                catchesTaken.setText(statisticsEngine.getCatchesTaken());
                runoutsMade.setText(statisticsEngine.getRunoutsMade());
                fab.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fab.setVisibility(View.VISIBLE);
            }
        });
    }

    private void checkLogin() {
        String fileName = new File(this.getFilesDir(), "userdetails.dat").getAbsolutePath();
        GoogleLoginManager.UserDetails details = (GoogleLoginManager.UserDetails) new ObjectSerializer().get(fileName);
        if (details != null) {
            navigationManager.userLoggedIn(this, details);
        } else {
            navigationManager.userLoggedOut();
        }
    }

    @Override
    public void loginSucceeded(GoogleLoginManager.UserDetails userDetails) {
        navigationManager.userLoggedIn(this, userDetails);
        String fileName = new File(this.getFilesDir(), "userdetails.dat").getAbsolutePath();
        new ObjectSerializer().putObject(fileName, userDetails);
        fetchData();
    }
}
