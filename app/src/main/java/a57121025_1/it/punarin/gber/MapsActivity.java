package a57121025_1.it.punarin.gber;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Marker myMarker = null;
    String name ="";
    Intent getIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        getIntent= getIntent();
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        if(getIntent.hasExtra("Permission_Admin")){
            //TODO : Select fname,lname,address,lat,lag From user join order and add maker
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(final Marker marker) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),17));
                    AlertDialog mydialog = new AlertDialog.Builder(MapsActivity.this)
                            .setTitle("Customername")
                            .setMessage("Order Size Have Tank ? Price")
                            .setNeutralButton("Delete Maker", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    marker.remove();
                                }
                            })
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                    return false;
                }
            });
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(getIntent.hasExtra("classAddress")) {
                    mMap.clear();
                    myMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("I'm HERE"));
                    Intent set = new Intent(MapsActivity.this,MainActivity.class);
                    LatLng position = myMarker.getPosition();
                    set.putExtra("MapClass","");
                    Log.d("LAT",position.latitude+"");
                    set.putExtra("lat",position.latitude);
                    set.putExtra("lng",position.longitude);
                    startActivity(set);
                }
            }
        });





        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
