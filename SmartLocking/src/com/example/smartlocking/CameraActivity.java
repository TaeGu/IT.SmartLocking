package com.example.smartlocking;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
//import android.widget.ImageView;
import android.widget.Toast;

public class CameraActivity extends Activity{
	protected static final String TAG = null;
	private SurfaceView surfaceView;     
	private SurfaceHolder surfaceHolder;
	private Camera camera;
	private boolean inProgress;
   
	public static String timestamp = null;
   
	//GPS ����
	double a,b = 0.0;
	LocationManager m_lmLocation;
	List<String> m_lstProviders;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.camera);

		//Log.i("��� Ʋ����", "��� Ʋ��"); 
      
		Toast.makeText(CameraActivity.this, "���Ʋ�Ƚ�", Toast.LENGTH_SHORT).show();
      
		//imageview = (ImageView)findViewById(R.id.iv_preview);
      
		surfaceView =  (SurfaceView) findViewById(R.id.sv_viewFinder);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(surfaceListener);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
      
		m_lmLocation = (LocationManager)getSystemService( Context.LOCATION_SERVICE );
		m_lstProviders = m_lmLocation.getProviders( true );
      
		/*Timer timer = new Timer();
		TimerTask tt = new TimerTask() 
		{
			@Override
			public void run()
			{
				startTakePicture(); // �ؿ� ���� ������ �Լ�.
			}
		};
		timer.schedule(tt, 800);*/
	}
   
	//���� ������ ���� getOutputMediaFile�Լ� ����
	public static File getOutputMediaFile()
	{
		//SD ī�尡 ����Ʈ �Ǿ��ִ��� ���� Ȯ��
		// Environment.getExternalStorageState() �� ����Ʈ ���� Ȯ�� �����մϴ�

		// Find the SD Card path
		File filepath = Environment.getExternalStorageDirectory();
		File mediaStorageDir = new File(filepath.getAbsolutePath() + "/DCIM/SmartLocking");
       
		Log.i("Saved Path", filepath.getAbsolutePath() + "/DCIM/SmartLocking");

		// ���� ��ζ�� ���� ����
		if(!mediaStorageDir.exists()){
			mediaStorageDir.mkdirs();
			Log.i("MyCamera", "failed to create directory");
		}

		// ���ϸ��� ������ ����, ���⼱ �ð����� ���ϸ� �ߺ��� ���Ѵ�
		timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile;
		mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timestamp + ".jpg");
		return mediaFile;
   }

	//������ �������� �ൿ
	private Camera.PictureCallback takePicture = new Camera.PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			
			if(data!=null)
				Log.i("Camera", "JPEG ���� �����!");
         
			InputStream is = new ByteArrayInputStream(data);
			Bitmap originalImg = BitmapFactory.decodeStream(is);
          
			int w = originalImg.getWidth();
			int h = originalImg.getHeight();
         
			Matrix mtx = new Matrix();
			mtx.setScale(-1, 1); // �¿����
			mtx.postRotate(90);
         
			Bitmap sideInversionImg  = Bitmap.createBitmap(originalImg, 0, 0, w, h, mtx, true);
           
			//imageview.setRotation(90);
			//imageview.setImageBitmap(sideInversionImg);
			//camera.startPreview();
            
			inProgress = false;
         
			//********************���� ����****************************************
			File pictureFile = getOutputMediaFile();
         
			if(pictureFile == null){
        		Log.i(TAG, "Error camera image saving");  
        		return;
			}
			try{
				FileOutputStream fos = new FileOutputStream(pictureFile);
				sideInversionImg.compress(Bitmap.CompressFormat.JPEG, 100, fos);
             
				fos.flush();
				fos.close();
				//Thread.sleep(500);
				camera.startPreview();
				Log.i("Camera", "���� ���� �Ϸ�");
             
			}catch (FileNotFoundException e) {
				Log.d(TAG, "File not found: " + e.getMessage());
			}catch (IOException e) {
				Log.d(TAG, "Error accessing file: " + e.getMessage());
				}
         
			//*********************************** GPS ****************************************
			Log.i("GPS", "GPS ON/OFF ���� Ȯ����");
			LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			
			if(!locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
				Log.i("GPS", "GPS OFF");
				new MailSend(timestamp, a, b);
				finish();
			}else{
				Log.i("GPS", "GPS ON");
				Log.i("GPS", "GPS ȭ�� �ҷ�������...");
				getMyLocation();
				finish();
			}
      }
   };
   
   //SurfaceHolder	
   private SurfaceHolder.Callback surfaceListener = new SurfaceHolder.Callback() {
      @Override
      public void surfaceDestroyed(SurfaceHolder holder) {
         // TODO Auto-generated method stub
         camera.release();
         camera = null;
         Log.i("Camera", "ī�޶� ��� ����");
      }
      
      @Override
      public void surfaceCreated(SurfaceHolder holder) {
         // TODO Auto-generated method stub
         Log.i("SurfaceHolder", "ī�޶� SurfaceView ����");
         
         int int_cameraID = 0;
         
         int numberOfCameras = Camera.getNumberOfCameras();
            CameraInfo cameraInfo = new CameraInfo();
         
            for(int i=0; i < numberOfCameras; i++) 
            {
                Camera.getCameraInfo(i, cameraInfo);

                // ����ī�޶�
                if(cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT)
                   int_cameraID = i;
                // �ĸ�ī�޶�
                //if(cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK)
                    //int_cameraID = i;
            }
            
            camera = Camera.open(int_cameraID);
         
         try {
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);
         }catch(Exception e) {
            e.printStackTrace();
         }
      }
      
      @Override
      public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
         // TODO Auto-generated method stub
         Camera.Parameters parameters = camera.getParameters();
         parameters.setPreviewSize(width, height);
         camera.startPreview();
         startTakePicture();
         Log.i("SurfaceHolder","ī�޶� SurfaceView ��� �غ� �Ϸ�");
      }
   };
   
   //���� ���
   public void startTakePicture ()
    {
        if (camera != null && inProgress == false)
        {
            camera.takePicture(null, null, takePicture);
            inProgress = true;
        }
    }
   
   public static String getTime(){
	   return timestamp;
   }
      
   class MyLocationListener implements LocationListener {
	   @Override
	   public void onLocationChanged(Location location) {
		   a = location.getLatitude();
		   b = location.getLongitude();
		   Log.i("GPS","����,�浵 = "+ a + "," + b);
		   
		   m_lmLocation.removeUpdates(this);
		   
		   Intent intent = new Intent(getApplicationContext(),GpsActivity.class);
		   intent.putExtra("a", a);
		   intent.putExtra("b", b);
		   intent.putExtra("timestamp", timestamp);
		   startActivity(intent);
	   }
	   @Override
	   public void onStatusChanged(String provider, int status, Bundle extras) {}
	   @Override
	   public void onProviderEnabled(String provider) {}
	   @Override
	   public void onProviderDisabled(String provider) {}
   }
   
   private void getMyLocation() {
	   long minTime = 0;
	   float minDistance = 0;
	   
	   MyLocationListener listener = new MyLocationListener();	
	   
	   for( String strName : m_lstProviders ) // ��� ��ġ �����ڿ� ��ġ ���� �����ʸ� ����Ѵ�.
	   {
		   m_lmLocation.requestLocationUpdates(strName, minTime, minDistance, listener);
	   }
	   
   }
}