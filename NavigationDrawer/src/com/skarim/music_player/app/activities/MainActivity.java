package com.skarim.music_player.app.activities;

import java.util.ArrayList;
import com.skarim.music_player.app.adapters.NavDrawerListAdapter;
import com.skarim.music_player.app.classes.NavDrawerItem;
import com.skarim.music_player.app.fragments.AlbumFragment;
import com.skarim.music_player.app.fragments.AllMusicFragment;
import com.skarim.music_player.app.fragments.AllMusicFragmentNew;
import com.skarim.music_player.app.fragments.ArtistListFragment;
import com.skarim.music_player.app.receivers.CallReceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class MainActivity extends FragmentActivity {
	public static final String BROADCAST = "com.skarim.music_player.app.receivers.android.action.broadcast";
    CallReceiver myReceiver;
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private String[] navMenuTags;;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.tv_drawer_items);

        // load slide menu tags
        navMenuTags = getResources().getStringArray(R.array.tv_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.ic_drawer_items);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuIcons.getResourceId(0, -1),navMenuTitles[0]));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuIcons.getResourceId(1, -1),navMenuTitles[1]));
       
        navDrawerItems.add(new NavDrawerItem(navMenuIcons.getResourceId(2, -1),navMenuTitles[2]));
        
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuIcons.getResourceId(3, -1),navMenuTitles[3]));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuIcons.getResourceId(4, -1),navMenuTitles[4]));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuIcons.getResourceId(5, -1),navMenuTitles[5]));
        
        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
       /* IntentFilter intentFilter = new IntentFilter(BROADCAST);
        myReceiver=new CallReceiver();
        registerReceiver( myReceiver , intentFilter);
        
        Intent intent = new Intent(BROADCAST);  
        Bundle extras = new Bundle();  
        extras.putString("send_data", "test");  
        intent.putExtras(extras);  
        sendBroadcast(intent);*/

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	/*if (PlayerActivity.mediaPlayer!= null&&PlayerActivity.mediaPlayer.isPlaying()) {
			PlayerActivity.pauseMediaPlayer();
		}*/
    }

    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {

        /*case R.id.action_settings:
            Toast.makeText(getApplicationContext(), "code", Toast.LENGTH_LONG).show();
            // Create new fragment and transaction
            Fragment newFragment = new Fragment_Java(); 
            // consider using Java coding conventions (upper char class names!!!)
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack
            transaction.replace(R.id.frame_container, newFragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit(); 
            return true;

        case R.id.item1:
            Toast.makeText(getApplicationContext(), "send a suggestion", Toast.LENGTH_LONG).show();
            return true;

        case R.id.item2:
            Toast.makeText(getApplicationContext(), "Meet developers", Toast.LENGTH_LONG).show();
            return true;

        case R.id.item3:
            Toast.makeText(getApplicationContext(), "Rate this app", Toast.LENGTH_LONG).show();
            return true;

        default:
            return super.onOptionsItemSelected(item);*/
        }
        return false;
    }

    /* 
     * Called when invalidateOptionsMenu() is triggered
     **/

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }





    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
        case 0:
            fragment = new AllMusicFragment();
            break;
        case 1:
            fragment = new ArtistListFragment();
            break;
        case 2:
            fragment = new AlbumFragment();
            break;   
        default:
            break;
        }

        if (fragment != null) {
        	android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            //FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
       super.onActivityResult(requestCode, resultCode, result);
    }
}