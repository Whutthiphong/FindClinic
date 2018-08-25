package th.co.dindclinic.findclinic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final ArrayList<Marker> markk = new ArrayList<>();
        ArrayList<item_clinic> item = new ArrayList<>();
        item_clinic item_mark = new item_clinic();
        item_mark.CLI_ID = "100001";
        item_mark.CLI_NAME = "คลินิกที่1";
        item_mark.LAT = 15.260389;
        item_mark.LON = 104.829986;
        item.add(item_mark);
        item_mark = new item_clinic();
        item_mark.CLI_ID = "100002";
        item_mark.CLI_NAME = "คลินิกที่2";
        item_mark.LAT = 15.264082;
        item_mark.LON = 104.821473;
        item.add(item_mark);
        item_mark = new item_clinic();
        item_mark.CLI_ID = "100003";
        item_mark.CLI_NAME = "คลินิกที่3";
        item_mark.LAT = 15.263311;
        item_mark.LON = 104.827240;
        item.add(item_mark);
        item_mark = new item_clinic();
        item_mark.CLI_ID = "100004";
        item_mark.CLI_NAME = "คลินิกที่4";
        item_mark.LAT = 15.260669;
        item_mark.LON = 104.824343;
        item.add(item_mark);
        item_mark = new item_clinic();
        item_mark.CLI_ID = "100005";
        item_mark.CLI_NAME = "คลินิกที่5";
        item_mark.LAT = 15.261066;
        item_mark.LON = 104.832143;
        item.add(item_mark);

        for (int i = 0; i < item.size(); i++) {
            if (item.get(i).LAT != 0 && item.get(i).LON != 0) {
                 markk.add(createMarker(item.get(i).LAT, item.get(i).LON, item.get(i).CLI_ID, item.get(i).CLI_NAME));
            }
        }
        Location currentLocation = (Location) task.getResult();
        moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                DEFAULT_ZOOM);


//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                ArrayList<item_clinic> item = new JsonConverter<item_clinic>().toArrayList(response,item_clinic.class);
//
//                if (item.size()!=0) {
//                    for (int i = 0; i < item.size(); i++) {
//
//                        if (item.get(i).LAT != 0 && item.get(i).LON != 0) {
//                            markk.add(createMarker(item.get(i).LAT, item.get(i).LON, item.get(i).CLI_ID, item.get(i).CLI_NAME ));
//                        }
//                        Log.e("MAP size : ", markk.size() + "");
//                    }
//
//
//                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                    for (Marker marker : markk) {
//                        builder.include(marker.getPosition());
//                        Log.e("MAP E : ", marker.getTitle() + ":" + marker.getPosition());
//                    }
//                    LatLngBounds bounds = builder.build();
//                    int width = getResources().getDisplayMetrics().widthPixels;
//                    int height = getResources().getDisplayMetrics().heightPixels;
//                    int padding = (int) (width * 0.10); // offset from edges of the map 12% of screen
//
//                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
//                    mMap.getUiSettings().setZoomControlsEnabled(true);
//                    mMap.getUiSettings().setMapToolbarEnabled(true);
//                    mMap.animateCamera(cu);
//                    mMap.moveCamera(cu);
//                    mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
//
//                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                        @Override
//                        public boolean onMarkerClick(Marker marker) {
////                Toast.makeText(maoZoneE.this,marker.getTitle()+" : "+marker.getSnippet()+" : ID = "+marker.getId(),Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(MapsActivity.this, ClinicDetailActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("dr_id", marker.getSnippet().trim());
//                            bundle.putString("dr_title", marker.getTitle().trim());
//                            bundle.putDouble("dr_lat", marker.getPosition().latitude);
//                            bundle.putDouble("dr_lon", marker.getPosition().longitude);
//
//                            intent.putExtras(bundle);
//                            startActivity(intent);
//                            return true;
//                        }
//                    });
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Map Zone Error" ,error.toString());
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String ,String > params = new HashMap<>();
//                params.put("dr_zone","E");
//                return params;
//            }
//        };
//         MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        // Add a marker in Sydney and move the camera

    }
    private void moveCamera(LatLng latLng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }
    private void getLocationPermission(){

    }
    protected Marker createMarker(double latitude, double longitude, String snippetText, String title) {

        return mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).anchor(0.5f, 0.5f).title(title).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).snippet(snippetText));
    }
}
