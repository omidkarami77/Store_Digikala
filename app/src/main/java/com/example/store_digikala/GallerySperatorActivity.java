package com.example.store_digikala;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class GallerySperatorActivity extends SingleFragmentActivity {


    public static Intent GalleryIntenet(Context context) {
        Intent intent = new Intent(context, GallerySperatorActivity.class);
        return intent;
    }


    @Override
    public Fragment createaFragment() {
        return null;
    }
}