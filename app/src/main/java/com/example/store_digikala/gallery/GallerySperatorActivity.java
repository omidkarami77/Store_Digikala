package com.example.Store_Digikala.gallery;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.Store_Digikala.SingleFragmentActivity;
import com.example.Store_Digikala.categories.CategoriesSeperatorFragment;

public class GallerySperatorActivity extends SingleFragmentActivity {


    public static Intent GalleryIntenet(Context context) {
        Intent intent = new Intent(context, GallerySperatorActivity.class);
        return intent;
    }



    @Override
    public Fragment createFragment() {
        return CategoriesSeperatorFragment.newInstance();
    }

}
