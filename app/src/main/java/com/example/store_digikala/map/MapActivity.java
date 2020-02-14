package com.example.Store_Digikala.map;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.Store_Digikala.SingleFragmentActivity;

public class MapActivity extends SingleFragmentActivity {


    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,MapActivity.class);

        return intent;
    }

    @Override
    public Fragment createFragment() {
        return MapFragment.newInstance();
    }
}
