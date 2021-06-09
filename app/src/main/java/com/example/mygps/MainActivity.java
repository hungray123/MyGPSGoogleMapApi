package com.example.mygps;
import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends AppCompatActivity  implements OnMapReadyCallback {

    Button btnShowLocation;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    private GoogleMap mMap;

    // GPSTracker class
    GPSTracker gps;
    LatLng HungDz;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment =(MapFragment) getFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);

//        try {
//
//            if (ActivityCompat.checkSelfPermission(this, mPermission)
//                    != MockPackageManager.PERMISSION_GRANTED) {
//
//                ActivityCompat.requestPermissions(this, new String[]{mPermission},
//                        REQUEST_CODE_PERMISSION);
//
//                // If any permission above not allowed by user, this condition will
////                execute every time, else your else part will work
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        btnShowLocation = findViewById(R.id.button);

        // show location button click event
        btnShowLocation.setOnClickListener(arg0 -> {
            // create class object
            gps = new GPSTracker(MainActivity.this);



            // check if GPS enabled
            if(gps.canGetLocation()){

                 latitude = gps.getLatitude();
                 longitude = gps.getLongitude();

                // \n is for new line
                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                        + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                //gps present
                HungDz = new LatLng(latitude, longitude);
                // Add a marker in Sydney and move the camera
                mMap.addMarker(new
                        MarkerOptions()
                        .position(HungDz)
                        .title("I'm Here !"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HungDz,15));
            }else{
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }

        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
    }
}