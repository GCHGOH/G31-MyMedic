package com.example.madassignment2;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;





import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;





import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





public class NearbyPharmacy extends AppCompatActivity implements OnMapReadyCallback {



    private GoogleMap mMap;
    private Button ipohB;
    private Button kamparB;
    private Button currentLocationB;
    private Button pharmacyB;
    //currentlocation
    private final int FINE_PERMISSION_CODE = 1;
    Location currentLocation;



    FusedLocationProviderClient fusedLocationProviderClient;
    //private static final int Request_code= 101;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_pharmacy);



        ipohB = findViewById(R.id.ipoh);
        kamparB = findViewById(R.id.kampar);
        currentLocationB = findViewById(R.id.current_location);
        pharmacyB = findViewById(R.id.pharmacy);





        //current location
        fusedLocationProviderClient = LocationServices.
                getFusedLocationProviderClient(this);
        getCurrentLocation();



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





        ipohB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToIpohLocation();
            }
        });



        currentLocationB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToCurrentLocation();
            }
        });



        kamparB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //move camera to kampar
                LatLng kpr = new LatLng(4.3085, 101.1537);
                mMap.addMarker(new MarkerOptions()
                        .position(kpr)
                        .title("Kampar"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(kpr, 15.0f));
            }
        });



        pharmacyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNearbyPharmacy();
            }
        });





    }



    private void ToIpohLocation() {



        //move camera to ipoh
        LatLng ipohCoordinates = new LatLng(4.5975, 101.0901);
        mMap.addMarker(new MarkerOptions()
                .position(ipohCoordinates)
                .title("Marker in Ipoh"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ipohCoordinates, 10.0f));
    }



    private void ToCurrentLocation() {
        // Implement logic to show the user's current location on the map

        if (currentLocation != null) {
            // Implement logic to show the user's current location on the map
            LatLng currentC = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(currentC)
                    .title("Marker in current"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentC, 10.0f));
        } else {
            // Handle the case where the current location is not available
            Toast.makeText(this, "Current location not available", Toast.LENGTH_SHORT).show();
        }
    }



    private void showNearbyPharmacy() {
        // Implement logic to display nearby pharmacies on the map



        String mapAPI = getResources().getString(R.string.map_api);



        RequestQueue requestQueue = Volley.newRequestQueue(this); // Initialize RequestQueue
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=" + 4.3085 + "," + 101.1537 +
                "&radius=1000" +
                "&type=pharmacy" +
                "&key=" + mapAPI;



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray resultsArray = response.getJSONArray("results");
                            mMap.clear(); // Clear existing markers



                            for (int i = 0; i < resultsArray.length(); i++) {
                                JSONObject pharmacyObject = resultsArray.getJSONObject(i);



                                // Retrieve pharmacy name
                                String name = pharmacyObject.getString("name");



                                // Retrieve geometry location
                                JSONObject locationObject = pharmacyObject.getJSONObject("geometry").getJSONObject("location");
                                double pharmacyLatitude = locationObject.getDouble("lat");
                                double pharmacyLongitude = locationObject.getDouble("lng");



                                // Create LatLng for the pharmacy location
                                LatLng pharmacyLocation = new LatLng(pharmacyLatitude, pharmacyLongitude);



                                // Add a marker for the pharmacy on the map
                                mMap.addMarker(new MarkerOptions().position(pharmacyLocation).title(name));



                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }



        });



        requestQueue.add(request);



    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoomLevel = 15.0f;



        // Add a marker in Sydney and move the camera
        LatLng utar = new LatLng(4.3348, 101.1351);
        mMap.addMarker(new MarkerOptions()
                .position(utar)
                .title("Marker in Utar"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(utar, zoomLevel));
    }



    private void getCurrentLocation() {



        //Permission check
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation= location;



                    //display map
                   // SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    //        .findFragmentById(R.id.map);
                   // mapFragment.getMapAsync(NearbyPharmacy.this);



                    //ToCurrentLocation();
                }
            }
        });





    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                getCurrentLocation();
            }else{
                Toast.makeText(this,"location permission is denied",Toast.LENGTH_SHORT).show();
            }
        }
    }



}