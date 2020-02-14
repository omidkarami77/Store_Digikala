package com.example.Store_Digikala.informaion;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.Store_Digikala.SingleFragmentActivity;


public class ProductInformationActivity extends SingleFragmentActivity {
    private static final String EXTRA_PRODUCT_ID = "example.Store_Digikala.product_id";

    public static Intent newInent(Context context, int id) {
        Intent intent = new Intent(context, ProductInformationActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, id);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        int id = (int) getIntent().getSerializableExtra(EXTRA_PRODUCT_ID);
            return ProductInformationFragment.newInstance(id);

    }




}
