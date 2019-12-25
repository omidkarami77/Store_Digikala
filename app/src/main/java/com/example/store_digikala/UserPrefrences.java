package com.example.store_digikala;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserPrefrences {
    private static final String PREF_USER_NAME = "usernameQuery";
    private static final String PREF_COUNT_CART = "counterProductQuery";
    private static final String PREF_USER_PHONE_NUMBER = "phone_number";
    private static final String PREF_USER_EMAIL = "email";
    private static final String PREF_USER_PASSWORD = "password";
    private static final String PREF_CUSTOMER_ID = "customer_id";
    private static final String PREF_USER_LAST_NAME = "userLastName";
    private static final String PREF_LAST_ID = "lastId";
    private static final String LEATEST_ITEM_ID = "leatesItemId";

    //service Tags
    private static final String PREF_COUNT_BEFORE = "count_before_add_product";
    private static final String PREF_COUNT_AFTER = "count_after_add_product";
    //AlarmManagerIntegerForNotification
    private static final String PREF_INT_PERIDOC_ALARM = "count_after_add_product";

    public static void setStoredName(Context context, String query) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putString(PREF_USER_NAME, query)
                .apply();
    }

    public static String getPrefUserName(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PREF_USER_NAME, null);
    }

    public static void setStoredCount(Context context, Integer count) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putInt(PREF_COUNT_CART, count)
                .apply();
    }

    public static int getPrefCountCart(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(PREF_COUNT_CART, 0);
    }


    public static void setPrefFirstName(Context context, String name) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putString(PREF_USER_NAME, name)
                .apply();
    }

    public static String getPrefFirstName(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PREF_USER_NAME, "Register");
    }

    public static void setPrefLastName(Context context, String lastName) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putString(PREF_USER_LAST_NAME, lastName)
                .apply();
    }

    public static String getPrefLastName(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PREF_USER_NAME, "");
    }

    public static void setPrefUserName(Context context, String phoneNumber) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putString(PREF_USER_PHONE_NUMBER, phoneNumber)
                .apply();
    }

    public static void setPrefUserEmail(Context context, String email) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putString(PREF_USER_EMAIL, email)
                .apply();
    }

    public static String getPrefUserEmail(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PREF_USER_EMAIL, "");
    }

    public static void setPrefUserPassword(Context context, String password) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putString(PREF_USER_PASSWORD, password)
                .apply();
    }

    public static String getPrefUserPassword(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PREF_USER_PASSWORD, "");
    }

    public static void setPrefCustomerId(Context context, int customerId) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putString(PREF_CUSTOMER_ID, String.valueOf(customerId))
                .apply();
    }

    public static int getPrefCustomerId(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return Integer.parseInt(sharedPreferences.getString(PREF_CUSTOMER_ID, "0"));
    }

    public static void setPrefLastId(Context context, String lastId) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putString(PREF_LAST_ID, lastId)
                .apply();
    }

    public static String getPrefLastID(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PREF_LAST_ID, null);

    }

    public static void setPrefBeforeCount(Context context, int count) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences
                .edit()
                .putFloat(PREF_COUNT_BEFORE, count)
                .apply();
    }

    public static int getPrefBeforeCount(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(PREF_COUNT_BEFORE, 0);
    }

    public static void setPrefAfterCount(Context context, int count) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences
                .edit()
                .putFloat(PREF_COUNT_AFTER, count)
                .apply();
    }

    public static int getPrefAfterCount(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(PREF_COUNT_AFTER, 0);
    }


    public static void setPrefIntPeridocAlarm(Context context, int count) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences
                .edit()
                .putFloat(PREF_INT_PERIDOC_ALARM, count)
                .apply();
    }

    public static int getPrefIntPeridocAlarm(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(PREF_INT_PERIDOC_ALARM, 3);
    }


    public static void setStoredQuery(Context context, int qurey) {
        // context.getSharedPreferences("ezShopPrefs", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences
                .edit()
                .putInt(LEATEST_ITEM_ID, qurey)
                .apply();

    }

    public static int getStoredQurey(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(LEATEST_ITEM_ID, 0);
    }


}
