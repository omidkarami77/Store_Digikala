package com.example.Store_Digikala.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.Store_Digikala.R;
import com.example.Store_Digikala.list.ListOfProductFragment;
import com.example.Store_Digikala.registerandsetting.RegisterFragment;
import com.example.Store_Digikala.registerandsetting.SettingFragment;
import com.example.Store_Digikala.cart.ShowProductCartFragment;

public class NavigationItemActivity extends AppCompatActivity {

    private static final String EXTRA_REQUEST_CODE = "com.example.Store_Digikala.activityfolde.NavigationItemActivity";
    private int requestCode;

    public static Intent NavigationItemIntent(Context context, int requsetCode) {
        Intent intent = new Intent(context, NavigationItemActivity.class);
        intent.putExtra(EXTRA_REQUEST_CODE, requsetCode);
        return intent;


    }

    public static Intent NavigationIntentRegister(Context context, int requsetCode) {
        Intent intent = new Intent(context, NavigationItemActivity.class);
        intent.putExtra(EXTRA_REQUEST_CODE, requsetCode);
        return intent;


    }

    public static Intent NavigationItemIntentCart(Context context) {
        Intent intent = new Intent(context, NavigationItemActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        requestCode = getIntent().getIntExtra(EXTRA_REQUEST_CODE, 0);
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (requestCode == 0) {
            fragmentManager.beginTransaction().replace(R.id.fram_layout_main, ShowProductCartFragment.newInstance()).commit();
        }else if (requestCode==4){
            fragmentManager.beginTransaction().replace(R.id.fram_layout_main, SettingFragment.newInstance()).commit();
        }else if (requestCode==5) {
                fragmentManager.beginTransaction().replace(R.id.fram_layout_main, RegisterFragment.newInstance()).commit();
        }else {
            Fragment fragment = ListOfProductFragment.newInstance(requestCode);
            fragmentManager.beginTransaction().replace(R.id.fram_layout_main, fragment).commit();
        }

    }
}
