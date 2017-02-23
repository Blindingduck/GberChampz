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

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
            HttpClient hc = new DefaultHttpClient();
            HttpPost hp = new HttpPost("http://punarin.coolpage.biz/android/vieworder.php");
            HttpResponse hr;
            BufferedReader bf;
            String data = "";
            try {
                hr = hc.execute(hp);
                bf = new BufferedReader(new InputStreamReader(hr.getEntity().getContent()));
                data = bf.readLine();
                JSONArray jsonArray = new JSONArray(data);
                JSONObject jObject = new JSONObject();
                for(int i=0;i<jsonArray.length();i++){
                    jObject = jsonArray.getJSONObject(i);
                    LatLng myLatlng = new LatLng(jObject.getDouble("lat"),jObject.getDouble("lng"));
                    mMap.addMarker(new MarkerOptions()
                            .position(myLatlng)
                            .title("Order ID :"+jObject.getString("order_id"))
                            .snippet("Name "+jObject.getString("fristname")+" "+jObject.getString("lastname")
                                    +"\nPrice : "+jObject.getString("totalprice")
                                    +"\nAddress : "+jObject.getString("address")
                                    +"\nTEL : "+jObject.getString("tel")));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(final Marker marker) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),17));
                    AlertDialog mydialog = new AlertDialog.Builder(MapsActivity.this)
                            .setTitle(marker.getTitle())
                            .setMessage(marker.getSnippet())
                            .setNeutralButton("Delete Maker", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    List<NameValuePair> nv = new ArrayList<NameValuePair>();
                                    HttpClient hc = new DefaultHttpClient();
                                    HttpPost hp = new HttpPost("http://punarin.coolpage.biz/android/deleteorder.php");
                                    String id = marker.getTitle().toString().substring(marker.getTitle().lastIndexOf(":")+1);
                                    nv.add(new BasicNameValuePair("id",id));
                                    try{
                                        hp.setEntity(new UrlEncodedFormEntity(nv));
                                        hc.execute(hp);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    } catch (ClientProtocolException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
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
                    set.putExtra("Map","");
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
