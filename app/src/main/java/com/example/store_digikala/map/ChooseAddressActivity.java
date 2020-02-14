package com.example.Store_Digikala.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.example.Store_Digikala.R;
import com.example.Store_Digikala.cart.FinalizePostProductActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class ChooseAddressActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, LocationListener{
    private GoogleMap mMap;

    public static final String LAT_INTEGER="androidx.fragment.app.FragmentActivity.LAT";
    public static final String LONG_INTEGER="androidx.fragment.app.FragmentActivity.LONG";
    public static final String ADDRESS_SAVED_CHOOSE ="androidx.fragment.app.FragmentActivity.address";
    private double lat;
    private double lon;
    private LocationManager mLocationManager;
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ChooseAddressActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (ContextCompat.checkSelfPermission
                (getApplicationContext(),
                        android.Manifest.permission
                                .ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission
                        (getApplicationContext(),
                                android.Manifest.permission
                                        .ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this, new String[]{android.Manifest
                            .permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.
                                    ACCESS_COARSE_LOCATION}, 101);

        }

        getLocationFromMap();
    }

    private void getLocationFromMap(){
        try {
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //  locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
        lat = location.getLatitude();
        lon = location.getLongitude();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


        } catch (Exception e) {

        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
      getLocationFromMap();
      googleMap=mMap;
        Log.d("test", "onMapReady()");

//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.69439, 51.42151), 10.0f));

        LatLng sydney = new LatLng(35.689, 51.3890);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .draggable(true)

                .title("Marker in Sydney"));
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
//                Log.d("test out", "onMarkerDragStart..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Log.e("test", "onMarkerDragEnd..." + marker.getPosition().latitude + "..." + marker.getPosition().longitude);
                Intent intent = FinalizePostProductActivity.newIntent(ChooseAddressActivity.this);
                LatLng sydney = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);


                getUserAddress(sydney);
                Intent intent1 = new Intent(ChooseAddressActivity.this, ChooseAddressActivity.class);
                intent.putExtra(LAT_INTEGER,marker.getPosition().latitude);
                intent.putExtra(LONG_INTEGER,marker.getPosition().longitude);
                intent.putExtra(ADDRESS_SAVED_CHOOSE,  getUserAddress(sydney));
                setResult(1,intent1);

                finish();
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
    private String getUserAddress(LatLng userLocation) {
        String userAddress = "not set yet";

        try {
            double latitude = userLocation.latitude;
            double longitude = userLocation.longitude;
            Geocoder geo = new Geocoder(ChooseAddressActivity.this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (addresses.isEmpty()) {
                Log.i("test", "Waiting for location");
            } else {
                userAddress = addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality()
                        + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("test", e.getMessage());// getFromLocation() may sometimes fail
        } finally {
            return userAddress;
        }

    }

}
