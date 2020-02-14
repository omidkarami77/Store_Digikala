package com.example.store_digikala.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.store_digikala.ProductInformationActivity;
import com.example.store_digikala.R;
import com.example.store_digikala.UserPrefrences;
import com.example.store_digikala.model.Products;
import com.example.store_digikala.network.Api;
import com.example.store_digikala.network.RetrofitClientInstance;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PollService extends IntentService {
    Products laetesProduct;
    private static final String TAG = "PollService";
    private static final long POLL_TAG_INTERNAL = TimeUnit.MINUTES.toMillis(1);
    private int mServiceLastId;

    public static Intent newIntent(Context context) {
        return new Intent(context, PollService.class);
    }

    public PollService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (!isNetworkAvailableAndConnected())
            return;

//        String lastID = UserPrefrences.getPrefLastID(this);

        pollServiceNotification(PollService.this);
    }

    public static void setServiceAlarm(Context context, boolean isON, int time) {
        Intent i = newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (isON) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME
                    , SystemClock.elapsedRealtime()
                    , POLL_TAG_INTERNAL * time
                    , pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }

    }

    public static boolean isAlarmOn(Context context) {
        Intent intent = newIntent(context);

        PendingIntent pendingIntent = PendingIntent.getService(context
                , 0
                , intent
                , PendingIntent.FLAG_NO_CREATE);
        return pendingIntent != null;
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkcConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();

        return isNetworkcConnected;

    }

    public void pollServiceNotification(Context context) {

        RetrofitClientInstance.getRetrofitInstance().create(Api.class).getProductList().enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    List<Products> productList = response.body();

                    laetesProduct = productList.get(0);
                    mServiceLastId = laetesProduct.getId();

                    Log.e("test", "id is" + mServiceLastId);
                    int savedLastId = UserPrefrences.getStoredQurey(PollService.this);
                    if (savedLastId == mServiceLastId) {
                        //no new res
                    } else {

                        Log.e("test", "id is" + savedLastId);
                        Intent i = ProductInformationActivity.newInent(PollService.this, mServiceLastId);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        PendingIntent pi = PendingIntent.getActivity(PollService.this, 0, i, 0);


                        String channelId = "notification";
                        Notification notification = new NotificationCompat.Builder(PollService.this, channelId)
                                .setContentTitle("New Product :)")
                                .setContentText("Visit our store New products added")
                                .setSmallIcon(R.drawable.ic_placeholder)

                                .setContentIntent(pi)
                                .setAutoCancel(true)

                                .build();

                        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(PollService.this);
                        notificationManagerCompat.notify(0, notification);

                        UserPrefrences.setStoredQuery(PollService.this, mServiceLastId);

                    }
                    Log.d("test", "is not succ");
                }
            }


            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(PollService.this, "be fana raft haji", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
