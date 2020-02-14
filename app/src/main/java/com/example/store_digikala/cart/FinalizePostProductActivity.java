package com.example.store_digikala.cart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.store_digikala.R;

public class FinalizePostProductActivity extends AppCompatActivity {

    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,FinalizePostProductActivity.class);
        return intent;
    }

    private int lat;
    private int lon;
    private String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalize_post_product);

        try {
            lat = (int) getIntent().getSerializableExtra(LAT_INTEGER);
            lon = (int) getIntent().getSerializableExtra(LONG_INTEGER);
            address = (String) getIntent().getSerializableExtra(ADDRESS_SAVED_CHOOSE);
        } catch (Exception e) {
            Log.e("test", "from bag");
        }
        if(lat!=0&&lon!=0){
            Bundle args = new Bundle();
            args.putInt(LAT_INTEGER, lat);
            args.putInt(LAT_INTEGER,lon);
            args.putString(ADDRESS_SAVED_CHOOSE,address);
            FinalizePostProductFragment fragment = new FinalizePostProductFragment();

            fragment.setArguments(args);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout_finalize, fragment, "test")
                    .commit();

        }
        ////////

        else {
            FinalizePostProductFragment fragment1 = FinalizePostProductFragment.newInstance();


            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout_finalize, fragment1, "test")
                    .commit();
        }}
}

