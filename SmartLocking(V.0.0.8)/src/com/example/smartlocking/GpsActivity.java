package com.example.smartlocking;

import java.util.List;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView; 
import android.content.Context;
import android.content.Intent;

public class GpsActivity extends Activity{

   double a,b;
   String timestamp;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.gps);
      Intent intent = getIntent();
      double a = intent.getDoubleExtra("a", 0.0);
      double b = intent.getDoubleExtra("b", 0.0);
      String timestamp = intent.getStringExtra("timestamp");
      MapView mapView = new MapView(this);
      mapView.setDaumMapApiKey("b5de2eb677f22e7a80325848de3261ee");
      
      mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(a, b), true);
      ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
      mapViewContainer.addView(mapView);
      
      new MailSend(timestamp, a, b);
   }
}