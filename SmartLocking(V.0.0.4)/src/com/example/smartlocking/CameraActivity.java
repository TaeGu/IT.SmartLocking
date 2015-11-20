package com.example.smartlocking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraActivity extends Activity {

   protected static final String TAG = null;
   private SurfaceView surfaceView;     
   private SurfaceHolder surfaceHolder;
   private Camera camera;
   private ImageView imageview;
   private boolean inProgress;

   
   
   // 2. �״��� �����ϴ°� onCreate. Activity ������ �� ���� ����ȴ�.
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
   
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      
      setContentView(R.layout.camera);
      
      Toast.makeText(CameraActivity.this, "���Ʋ�Ƚ�", Toast.LENGTH_SHORT).show();
      
      imageview = (ImageView)findViewById(R.id.iv_preview);
      surfaceView =  (SurfaceView) findViewById(R.id.sv_viewFinder);
      
      surfaceHolder = surfaceView.getHolder();
      surfaceHolder.addCallback(surfaceListener);
      //surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

      findViewById(R.id.btn_shutter).setOnClickListener(
         new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
               startTakePicture(); // �ؿ� ���� ������ �Լ�.
            }
         }
      );
      
      Timer timer = new Timer();
      TimerTask tt = new TimerTask() 
       {
         @Override
         public void run()
         {
            startTakePicture(); // �ؿ� ���� ������ �Լ�.
         }
      };
      timer.schedule(tt, 1000);
      
   }
   private static File getOutputMediaFile(){
       //SD ī�尡 ����Ʈ �Ǿ��ִ��� ���� Ȯ��
       // Environment.getExternalStorageState() �� ����Ʈ ���� Ȯ�� �����մϴ�
       File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");

       // ���� ��ζ�� ���� ����
       if(!mediaStorageDir.exists()){
           if(! mediaStorageDir.mkdirs()){
               Log.d("MyCamera", "failed to create directory");
               return null;
           }
       }

       // ���ϸ��� ������ ����, ���⼱ �ð����� ���ϸ� �ߺ��� ���Ѵ�
       String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
       File mediaFile;

       mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timestamp + ".jpg");
       Log.i("MyCamera", "Saved at"+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
       System.out.println(mediaFile.getPath());
       return mediaFile;
   }

   private Camera.PictureCallback takePicture = new Camera.PictureCallback() {
      
      @Override
      public void onPictureTaken(byte[] data, Camera camera) {
         // TODO Auto-generated method stub
         Log.i(TAG, "���� ���� Ȯ��");
         
         File pictureFile = getOutputMediaFile();
         if(pictureFile == null){
        	 Log.i(TAG, "Error camera image saving");  
             return;
         }

         try{
             FileOutputStream fos = new FileOutputStream(pictureFile);
             fos.write(data);
             fos.close();
             //Thread.sleep(500);
             camera.startPreview();
         } catch (FileNotFoundException e) {
             Log.d(TAG, "File not found: " + e.getMessage());
         } catch (IOException e) {
             Log.d(TAG, "Error accessing file: " + e.getMessage());
         }
         
         if(data!=null)
            Log.i(TAG, "JPEG ���� �����!");            
            Bitmap originalImg = BitmapFactory.decodeByteArray(data, 0, data.length);
            
            //Bitmap originalImg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            
            Matrix sideInversion = new Matrix();
            sideInversion.setScale(-1, 1);
            
            Bitmap sideInversionImg  = Bitmap.createBitmap(originalImg, 0, 0,
                   originalImg.getWidth(), originalImg.getHeight(), 
                   sideInversion, false);
            
            imageview.setRotation(90);
            imageview.setImageBitmap(sideInversionImg);   
            camera.startPreview();
            
            inProgress = false;
      }
   };
   
   private SurfaceHolder.Callback surfaceListener = new SurfaceHolder.Callback() {
      
      @Override
      public void surfaceDestroyed(SurfaceHolder holder) {
         // TODO Auto-generated method stub
         camera.release();
         camera = null;
         Log.i(TAG, "ī�޶� ��� ����");
      }
      
      @Override
      public void surfaceCreated(SurfaceHolder holder) {
         // TODO Auto-generated method stub
         Log.i(TAG, "ī�޶� �̸����� Ȱ��");
         
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
         Log.i(TAG,"ī�޶� �̸����� Ȱ��");
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
}