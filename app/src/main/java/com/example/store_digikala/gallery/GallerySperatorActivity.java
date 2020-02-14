package com.example.store_digikala.gallery;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.store_digikala.SingleFragmentActivity;
import com.example.store_digikala.categories.CategoriesSeperatorFragment;

public class GallerySperatorActivity extends SingleFragmentActivity {




    public static Intent GalleryIntenet(Context context) {
        Intent intent = new Intent(context, GallerySperatorActivity.class);
        return intent;
    }





    @Override
    public Fragment createaFragment() {
        return CategoriesSeperatorFragment.newInstance();
    }
}