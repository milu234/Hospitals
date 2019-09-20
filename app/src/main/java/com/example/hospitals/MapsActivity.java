package com.example.hospitals;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

//import android.location.LocationListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location lastLocation;
    private Marker currentLocationMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    double latitude , longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkUserLocationPermission();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);





    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        if (client == null){
                            buildGoogleApiClient();

                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }

                else {
                    Toast.makeText(this,"Permission Denied!!" , Toast.LENGTH_LONG).show();
                }
                return;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);

            }

        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

//    public void  onClick(View v){
//        if (v.getId() == R.id.btn_search){
//            EditText tf_location = (EditText)findViewById(R.id.TF_Location);
//            String location = tf_location.getText().toString();
//            List<Address> addressList = null;
//            MarkerOptions mo = new MarkerOptions();
//
//
//            if ( !location.equals("")){
//                Geocoder geocoder = new Geocoder(this);
//                try {
//                    addressList = geocoder.getFromLocationName(location,5);
//
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                for (int i  = 0; i < addressList.size();i++){
//                    Address myAddress = addressList.get(i);
//                    LatLng latLng = new LatLng(myAddress.getLatitude(),myAddress.getLongitude());
//
//                    mo.position(latLng);
//                    mo.title("Your Search is here");
//                    mMap.addMarker(mo);
//                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//                    mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
//
//                }
//            }
//        }
//    }

    protected synchronized void buildGoogleApiClient(){
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        if (currentLocationMarker != null){
            currentLocationMarker.remove();

        }

        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("You are here");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        currentLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        if (client != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }


    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(3000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }


    }


    public  boolean checkUserLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , REQUEST_LOCATION_CODE);

            }
                else {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , REQUEST_LOCATION_CODE);
                }
                return false;
        }
        else
            return true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
