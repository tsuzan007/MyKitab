package com.sujan.mykitaab.Services;

import android.Manifest;
import android.app.Dialog;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by macbookpro on 4/20/17.
 */

public class FetchAddressIntentService {
//    public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
//
//        GoogleMap mgoogleMap;
//        TextView textView;
//        Button button;
//        EditText destination;
//        GoogleApiClient googleApiClient;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//
//
//            if (googleservicesavailable()) {
//                Toast.makeText(this, "Perfect.", Toast.LENGTH_SHORT).show();
//                setContentView(R.layout.activity_main);
//                button = (Button) findViewById(R.id.button2);
//                button.setOnClickListener(sendbuttonclicked);
//                destination = (EditText) findViewById(R.id.editText2);
//                initMap();
//            } else {
//                setContentView(R.layout.activity_main2);
//                textView = (TextView) findViewById(R.id.textView);
//                textView.setText("Google play services cannot be displayed.");
//
//            }
//        }
//
//        View.OnClickListener sendbuttonclicked = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Geocoder geocoder = new Geocoder(MainActivity.this);
//                try {
//                    List<Address> address = geocoder.getFromLocationName(destination.getText().toString(), 1);
//                    Address address1 = address.get(0);
//                    double lat = address1.getLatitude();
//                    double lng = address1.getLongitude();
//                    gotoLocationZoom(lat, lng, 15);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        };
//
//
//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            getMenuInflater().inflate(R.menu.menu, menu);
//            return true;
//        }
//
//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//            if (item.getTitle().equals("Satellite")) {
//                mgoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//
//            } else if (item.getTitle().equals("terrain")) {
//                mgoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//
//
//            } else if (item.getTitle().equals("Normal")) {
//                mgoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//
//            }
//            return true;
//        }
//
//        public void initMap() {
//            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.my_mapfragment);
//            mapFragment.getMapAsync(this);
//
//        }
//
//        public boolean googleservicesavailable() {
//            GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
//            int isavailable = googleApiAvailability.isGooglePlayServicesAvailable(this);
//            if (isavailable == ConnectionResult.SUCCESS) {
//                return true;
//            } else if (googleApiAvailability.isUserResolvableError(isavailable)) {
//                Dialog dialog = googleApiAvailability.getErrorDialog(this, isavailable, 0);
//                dialog.show();
//            } else {
//                Toast.makeText(this, "Google play services not available", Toast.LENGTH_SHORT).show();
//            }
//            return false;
//        }
//
//        @Override
//        public void onMapReady(GoogleMap googleMap) {
//            mgoogleMap = googleMap;
//            //gotoLocationZoom(39.009469,-76.895880,15);
//            googleApiClient = new GoogleApiClient.Builder(this)
//                    .addApi(LocationServices.API)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .build();
//            googleApiClient.connect();
//
//        }
//
//        public void gotoLocationZoom(double lat, double lon, float zoom) {
//            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), zoom);
//            mgoogleMap.moveCamera(cameraUpdate);
//
//        }
//
//        LocationRequest locationRequest;
//
//        @Override
//        public void onConnected(@Nullable Bundle bundle) {
//            locationRequest = new LocationRequest();
//            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//            locationRequest.setInterval(1000);
//            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},1);
//
//                return;
//            }
//            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
//
//
//
//        }
//
//        @Override
//        public void onConnectionSuspended(int i) {
//
//        }
//
//        @Override
//        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//        }
//
//        @Override
//        public void onLocationChanged(Location location) {
//            CameraUpdate cameraUpdate=CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),15);
//            mgoogleMap.animateCamera(cameraUpdate);
//
//
//        }
//    }

}
