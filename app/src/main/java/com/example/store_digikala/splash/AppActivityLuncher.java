package com.example.Store_Digikala.splash;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.Store_Digikala.R;
import com.example.Store_Digikala.main.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

public class AppActivityLuncher extends AppCompatActivity {

    private LottieAnimationView lottieAnimationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luncher);
        lottieAnimationView=findViewById(R.id.animation);
        lottieAnimationView.playAnimation();

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lottieAnimationView.pauseAnimation();
                if (isNetworkAvailable()) {
                    Intent intent = new Intent(AppActivityLuncher.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AppActivityLuncher.this, "Please check your internet :(", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        },4000);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkcConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();

        return isNetworkcConnected;
    }
}
