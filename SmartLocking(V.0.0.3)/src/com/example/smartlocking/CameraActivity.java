package com.example.smartlocking;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraActivity extends Activity 
{
    SurfaceView sv_viewFinder;
    SurfaceHolder sh_viewFinder;
    Camera camera;
    Button btn_shutter;
    ImageView iv_preview;
    
    boolean inProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        Toast.makeText(CameraActivity.this, "���Ʋ�Ƚ�", Toast.LENGTH_SHORT).show();
        // findViewById
        sv_viewFinder = (SurfaceView) findViewById(R.id.sv_viewFinder);
        sh_viewFinder = sv_viewFinder.getHolder();
        sh_viewFinder.addCallback(surfaceListener);
        sh_viewFinder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        btn_shutter = (Button) findViewById(R.id.btn_shutter);
        iv_preview = (ImageView) findViewById(R.id.iv_preview);

        // setListener
        btn_shutter.setOnClickListener(onClickListener_btn_shutter);

        // 3�� �� �ڵ��Կ�
        Timer timer = new Timer();
        TimerTask tt = new TimerTask() 
        {
            @Override
            public void run() 
            {
                startTakePicture ();
            }
        };
        timer.schedule(tt, 3000);
    }

    public SurfaceHolder.Callback surfaceListener = new SurfaceHolder.Callback() 
    {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
        {
            Log.i("1", "sufraceListener ī�޶� �̸����� Ȱ��");

            Camera.Parameters parameters = camera.getParameters();
            parameters.setPreviewSize(width, height);
            camera.startPreview();
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) 
        {
            Log.i("1", "sufraceListener ī�޶� ����");

            int int_cameraID = 0;
            /* ī�޶� ������ �� ��� �� ���� ������  */
            int numberOfCameras = Camera.getNumberOfCameras();
            CameraInfo cameraInfo = new CameraInfo();

            for(int i=0; i < numberOfCameras; i++) 
            {
                Camera.getCameraInfo(i, cameraInfo);

                // ����ī�޶�
                //                if(cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT)
                //                    int_cameraID = i;
                // �ĸ�ī�޶�
                if(cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK)
                    int_cameraID = i;
            }

            camera = Camera.open(int_cameraID);

            try 
            {
                camera.setPreviewDisplay(sh_viewFinder);
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) 
        {
            Log.i("1", "sufraceListener ī�޶� ����");

            camera.release();
            camera = null;
        }
    };

    /**
     * 
     * @brief  : ���� ��� 
     * @date   : 2014. 10. 14.
     * @author : �赿��
     */
    public void startTakePicture ()
    {
        if (camera != null && inProgress == false)
        {
            camera.takePicture(null, null, takePicture);
            inProgress = true;
        }
    }

    View.OnClickListener onClickListener_btn_shutter = new OnClickListener() 
    {    
        @Override
        public void onClick(View v) 
        {
            startTakePicture ();
        }
    };

    public Camera.PictureCallback takePicture = new Camera.PictureCallback() 
    {    
        @Override
        public void onPictureTaken(byte[] data, Camera camera) 
        {
            Log.d("1", "=== takePicture ===");

            if (data != null)
            {
                Log.v("1", "takePicture JPEG ���� ����");

                Bitmap bitmap = BitmapFactory.decodeByteArray(data,  0,  data.length);
                iv_preview.setImageBitmap(bitmap);

                camera.startPreview();
                inProgress = false;
            }
            else
            {
                Log.e("1", "takePicture data null");
            }
        }
    };
}
