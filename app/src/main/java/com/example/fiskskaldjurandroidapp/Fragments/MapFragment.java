package com.example.fiskskaldjurandroidapp.Fragments;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.fiskskaldjurandroidapp.MainActivity;
import com.example.fiskskaldjurandroidapp.R;
import com.example.fiskskaldjurandroidapp.StartScreenFragmentHolder;
import com.example.fiskskaldjurandroidapp.locationSender;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

public class MapFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    LayoutInflater inflaterG;
    ViewGroup containerG;
    Bundle savedInstanceStateG;
    View rootViewG;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        inflaterG = inflater;
        containerG = container;
        savedInstanceStateG = savedInstanceState;
        rootViewG = rootView;


        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            initialize();

        } else {
            ActivityCompat.requestPermissions(getActivity(),
            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
            1);
        }

        return rootView;
    }


    public void initialize() {
        mMapView = (MapView) rootViewG.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceStateG);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);




                LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        double longitude = location.getLongitude();
                        double latitude = location.getLatitude();

                        LatLng myLocation = new LatLng(latitude, longitude);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
                    }
                }

//                // For dropping a marker at a point on the Map
//                LatLng sydney = new LatLng(-34, 151);
//                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();

        Switch sendLocationSwitch = rootViewG.findViewById(R.id.sendLocationSwitch);

        boolean isSendingLocation = pref.getBoolean("isSendingLocation", false);


       if (isSendingLocation) {
           sendLocationSwitch.setChecked(!sendLocationSwitch.isChecked());
       }

        locationSender.getInstance();

        sendLocationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    editor.putBoolean("isSendingLocation", true);
                    editor.apply();

                    System.out.println("On");
                } else {


                    editor.putBoolean("isSendingLocation", false);
                    editor.apply();

                    System.out.println("Off");
                }
            }
        });

    }






}


