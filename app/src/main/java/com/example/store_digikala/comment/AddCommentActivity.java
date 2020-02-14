package com.example.store_digikala.comment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.store_digikala.R;

public class AddCommentActivity extends AppCompatActivity {
    private int id_product;
    private FrameLayout frameLayout;
    private static final String PRODUCT_ID ="com.example.store_digikala.comment.commentcontroller" ;

    public static Intent newIntent(Context context, int id){
        Intent intent=new Intent(context,AddCommentActivity.class);
        intent.putExtra(PRODUCT_ID,id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information);
        frameLayout=findViewById(R.id.frame_layout_add_comment_activity);
        id_product=getIntent().getIntExtra(PRODUCT_ID,0);
        if (id_product!=0){
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout_add_comment_activity,AddCommentFragment.newInstance(id_product)).commit();

        }

    }
}

