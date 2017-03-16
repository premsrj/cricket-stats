package com.premsuraj.cricketstats.navigation;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;

import com.premsuraj.cricketstats.R;
import com.premsuraj.cricketstats.login.GoogleLoginManager;

/**
 * Created by Premsuraj
 */

public class NavigationManager
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationContainerListener mListener;

    public NavigationManager(NavigationContainerListener listener) {
        this.mListener = listener;
    }

    public void initNavigationView(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getHeaderView(0).findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.login();
                }
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        if(mListener != null)
            mListener.closeDrawer();
        return true;
    }

    public void userLoggedIn(GoogleLoginManager.UserDetails userDetails) {

    }
}
