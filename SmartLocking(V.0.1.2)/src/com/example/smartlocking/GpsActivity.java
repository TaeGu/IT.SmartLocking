package com.example.smartlocking;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class GpsActivity extends Activity{
	
	//****************************ȭ�� ��Ÿ���� ����Ŭ���� (�������������)******************************

   double a,b;
   String timestamp;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.gps);
      Intent intent = getIntent();
      double a = intent.getDoubleExtra("a", 0.0);
      double b = intent.getDoubleExtra("b", 0.0);
      int function = intent.getIntExtra("function", 0);
      String timestamp = intent.getStringExtra("timestamp");
      MapView mapView = new MapView(this);
      mapView.setDaumMapApiKey("b5de2eb677f22e7a80325848de3261ee");
      
      MapPOIItem marker = new MapPOIItem();
      marker.setItemName("Default Marker");
      marker.setMapPoint(MapPoint.mapPointWithGeoCoord(a, b)); 
      marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // �⺻���� �����ϴ� BluePin ��Ŀ ���.
      marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // ��Ŀ�� Ŭ��������, �⺻���� �����ϴ� RedPin ��Ŀ ���.
      mapView.addPOIItem(marker);

      MapCircle circle = new MapCircle(
            MapPoint.mapPointWithGeoCoord(a, b), // center
            20, // radius
            Color.argb(50, 255, 0, 0), // strokeColor 
            Color.argb(30, 0, 255, 0) // fillColor
         );
      mapView.addCircle(circle);
      
      mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(a, b), -1, true);
      ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
      mapViewContainer.addView(mapView);
      
      new MailSend(timestamp, a, b, function);
   }
}