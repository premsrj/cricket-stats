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

import com.premsuraj.cricketstats.R;
import com.premsuraj.cricketstats.addedit.AddEditActivity;
import com.premsuraj.cricketstats.login.GoogleLoginManager;
import com.premsuraj.cricketstats.navigation.NavigationContainerListener;
import com.premsuraj.cricketstats.navigation.NavigationManager;
import com.premsuraj.cricketstats.utils.ObjectSerializer;

import java.io.File;

public class MainActivity extends AppCompatActivity implements NavigationContainerListener, GoogleLoginManager.LoginListener {

    private static final String TAG = "Main";
    public NavigationManager navigationManager;
    GoogleLoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addEditIntent = new Intent(MainActivity.this, AddEditActivity.class);
                startActivity(addEditIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        loginManager = new GoogleLoginManager(this);
        navigationManager = new NavigationManager(this);
        navigationManager.initNavigationView((NavigationView) findViewById(R.id.nav_view));
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
    }

    @Override
    public void loginSucceeded(GoogleLoginManager.UserDetails userDetails) {
        navigationManager.userLoggedIn(this, userDetails);
        String fileName = new File(this.getFilesDir(), "userdetails.dat").getAbsolutePath();
        new ObjectSerializer().putObject(fileName, userDetails);
    }
}
