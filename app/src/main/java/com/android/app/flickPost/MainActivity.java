package com.android.app.flickPost;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarFragment;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectedListener;


import Helper.SessionManager;

public class MainActivity extends AppCompatActivity {
    private BottomBar mBottomBar;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());

        String UserId =  Integer.toString( session.getUserId());
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_reorder_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);

        mBottomBar = BottomBar.attach(this, savedInstanceState);

        /*mBottomBar.setItems(
                new BottomBarTab(R.drawable.ic_home_white_24dp, "Home"),
                new BottomBarTab(R.drawable.ic_notifications_white_24dp, "Notification"),
                new BottomBarTab(R.drawable.ic_people_white_24dp, "Friends"),
                new BottomBarTab(R.drawable.ic_face_white_24dp, "Me")
        );*/

        mBottomBar.setFragmentItems(getSupportFragmentManager(), R.id.fragment_container,
                new BottomBarFragment(HomeFragment.newInstance("load Feeds."), R.drawable.ic_home_white_24dp, "Home"),
                new BottomBarFragment(NotificationFragment.newInstance("Notification"), R.drawable.ic_notifications_white_24dp, "Notification"),
                new BottomBarFragment(FriendFragment.newInstance("Friends"), R.drawable.ic_people_white_24dp, "Friends"),
                new BottomBarFragment(ProfileFragment.newInstance("Profile"), R.drawable.ic_face_white_24dp, "Me")
        );
        // get your outer relative layout

        mBottomBar.setOnItemSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                switch (position) {
                    case 0:
                        // Item 1 Selected
                    case 1:

                    case 2:

                    case 3:
                        break;
                }
            }
        });
        mBottomBar.setActiveTabColor("#C2185B");
        /*// Make a Badge for the first tab, with red background color and a value of "4".
        BottomBarBadge unreadMessages = bottomBar.makeBadgeForTabAt(1, "#E91E63", 4);

        // Control the badge's visibility
        unreadMessages.show();
        //unreadMessages.hide();

        // Change the displayed count for this badge.
        //unreadMessages.setCount(4);

        // Change the show / hide animation duration.
        unreadMessages.setAnimationDuration(200);*/

        // If you want the badge be shown always after unselecting the tab that contains it.
        //unreadMessages.setAutoShowAfterUnSelection(true);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBottomBar.onSaveInstanceState(outState);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addPost:
                showStatusDlg();
                return true;
            case R.id.settings:
                return true;
            case R.id.signout:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void showStatusDlg() {
        /*FragmentManager fm = getSupportFragmentManager();
        Publish_Status publish_Status = new Publish_Status();
        publish_Status.show(fm, "Status");*/

        Intent intent = new Intent(this, Publish_Status.class);
        startActivity(intent);
        finish();
    }

}