package com.example.store_digikala.information;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.store_digikala.SingleFragmentActivity;

public class ProductInformationActivity extends SingleFragmentActivity {
    private static final String EXTRA_PRODUCT_ID = "com.example.store_digikala.product_id";

    public static Intent newInent(Context context, int id) {
        Intent intent = new Intent(context, ProductInformationActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, id);
        return intent;
    }




    @Override
    public Fragment createaFragment() {
        int id = (int) getIntent().getSerializableExtra(EXTRA_PRODUCT_ID);
        return ProductInformationFragment.newInstance(id);

    }
}
