package com.example.Store_Digikala.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Store_Digikala.R;
import com.example.Store_Digikala.gallery.GallerySperatorActivity;
import com.example.Store_Digikala.navigation.NavigationItemActivity;
import com.example.Store_Digikala.search.SearchResultActivity;
import com.example.Store_Digikala.prefs.UserPrefrences;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private FrameLayout frameLayout;
    private RecyclerView mRecyclerView;
    private TextView mUserName;


    private void findItem() {
        mToolbar = findViewById(R.id.toolbar_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.navigation_view);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        findItem();
        setSupportActionBar(mToolbar);
        View view = mNavigationView.getHeaderView(0);
        mUserName = view.findViewById(R.id.textview_username);
        if (UserPrefrences.getPrefUserName(this) != null) {
            mUserName.setText(UserPrefrences.getPrefUserName(this));
        }

        frameLayout = findViewById(R.id.fram_layout_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fram_layout_main, MainFragment.newInstance()).commit();

        mNavigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getParent(), mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_search:
                if (isNetworkAvailableAndConnected() == true) {
                    Intent intent = SearchResultActivity.newIntent(this, 1);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "your internet is fail please check your internet", Toast.LENGTH_SHORT).show();

                }
                return true;
            case R.id.cart_item_icon_inforamation:
                if (isNetworkAvailableAndConnected() == true) {
                    Intent intents = NavigationItemActivity.NavigationItemIntentCart(MainActivity.this);
                    startActivity(intents);
                } else {
                    Toast.makeText(this, "your internet is fail please check your internet", Toast.LENGTH_SHORT).show();

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (menuItem.getItemId()) {
            case R.id.item_gallery:
                if (isNetworkAvailableAndConnected() == true) {
                    Intent intent = GallerySperatorActivity.GalleryIntenet(this);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "your internet is fail please check your internet", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.item_popular:
                //request code1
                Intent intent = NavigationItemActivity.NavigationItemIntent(MainActivity.this, 1);
                startActivity(intent);
                return true;

            case R.id.item_MostSeler:
                //request code 2
                Intent intent1 = NavigationItemActivity.NavigationItemIntent(MainActivity.this, 2);
                startActivity(intent1);
                return true;
            case R.id.item_new:
                //request code 3
                Intent intent2 = NavigationItemActivity.NavigationItemIntent(MainActivity.this, 3);
                startActivity(intent2);
                return true;

            case R.id.item_setting:
                //request code 4setting
                Intent intentSetting = NavigationItemActivity.NavigationItemIntent(MainActivity.this, 4);
                startActivity(intentSetting);
                return true;
            case R.id.item_about:
                View view1 = findViewById(R.id.drawer_layout);
                Snackbar.make(view1, "Behrooz Android Developer\nbehroozeivazi@yahoo.com", Snackbar.LENGTH_LONG).show();
                return true;

            case R.id.item_cart:
                if (UserPrefrences.getPrefUserName(this) == null) {

                    LayoutInflater inflater = LayoutInflater.from(this);
                    View view = inflater.inflate(R.layout.adduser_dialog, null);

                    final EditText mEditText = view.findViewById(R.id.adduser_edittext);

                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setTitle("Add User")
                            .setView(view)
                            .setIcon(R.drawable.ic_action_adduser)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (mEditText.getText().toString() != null) {
                                        UserPrefrences.setStoredName(MainActivity.this, mEditText.getText().toString());
                                        mUserName.setText(mEditText.getText().toString());
                                        if (UserPrefrences.getPrefCountCart(MainActivity.this) != 0) {
                                            Intent intent = NavigationItemActivity.NavigationItemIntentCart(MainActivity.this);
                                            startActivity(intent);
                                        }
                                    } else {

                                        Toast.makeText(MainActivity.this, "thank for choice", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                            .show();
                } else if (true) {
                    Intent intents = NavigationItemActivity.NavigationItemIntentCart(MainActivity.this);
                    startActivity(intents);
                } else {
                    Toast.makeText(MainActivity.this, "Your Cart is Empty", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.register_item:
                Intent intent4 = NavigationItemActivity.NavigationIntentRegister(MainActivity.this, 5);
                startActivity(intent4);

                return true;


            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkcConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();

        return isNetworkcConnected;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }
}