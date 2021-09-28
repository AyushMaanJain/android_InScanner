package com.sm.test_scanner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.camera.camera2.Camera2Config;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraXConfig;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;
import com.sm.test_scanner.databinding.ActivityScannerBinding;
import com.sm.test_scanner.ui.DataContainer;

import java.lang.reflect.Proxy;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScannerActivity extends AppCompatActivity {
    Handler handler=new Handler();
    private ImageCapture imageCapture;
    private ExecutorService ce;
    PreviewView viewFinder;
    Button btn;
    Runnable r;
String data="";
    ImageProxy ip=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
count=0;
        // You can directly ask for the permission.
        //iv=findViewById(R.id.imageView);
        btn=findViewById(R.id.capture_button);
        btn.setVisibility(View.GONE);
        viewFinder= findViewById(R.id.view_finder);
        ce= Executors.newSingleThreadExecutor();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            startCam();    // You can use the API that requires the permission.
        }
        else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 12);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        takeSnap();
                    }
                }).start();

            }
        });

    }

    public void startCam(){

        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                Log.e("??","--");
                // Camera provider is now guaranteed to be available
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                // Set up the view finder use case to display camera preview
                Preview preview = new Preview.Builder().build();

                // Set up the capture use case to allow users to take photos
                imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .setTargetResolution(new Size(720, 720))
                        .build();

                // Choose the camera by requiring a lens facing
                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                ImageAnalysis imageAnalysis =new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(720, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();
                imageAnalysis.setAnalyzer( ContextCompat.getMainExecutor(this), new YourAnalyzer());
                // Attach use cases to the camera with the same lifecycle owner
                Camera camera = cameraProvider.bindToLifecycle(
                        ((LifecycleOwner) this)
                        ,cameraSelector
                        ,preview
                        ,imageAnalysis
                //        ,imageCapture
                );

                // Connect the preview use case to the previewView
                preview.setSurfaceProvider(
                        viewFinder.getSurfaceProvider());



            } catch (Exception e) {
                Log.e("Bhosadaa",e.toString());
            }
        }, ContextCompat.getMainExecutor(this));

    }

    public void takeSnap(){
        Log.e("??","?????");
        try{
        imageCapture.takePicture(ce, new ImageCapture.OnImageCapturedCallback() {
            @Override
            public void onCaptureSuccess(@NonNull ImageProxy imageProxy) {

                super.onCaptureSuccess(imageProxy);

                Image mediaImage = imageProxy.getImage();
                if (mediaImage != null) {
                    InputImage image =
                            InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
                    scanBarcodes(image);
                }
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                super.onError(exception);
                Toast.makeText(getApplicationContext(),"capture Unsuccessfull",Toast.LENGTH_LONG).show();
                Log.e("??",exception.toString());
            }
        });
    }catch (Exception e){
            Log.e("??",e.toString());
        }}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ce.shutdown();
        handler.removeCallbacks(r);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCam();
        }else{
            Toast.makeText(this, "Camera is required", Toast.LENGTH_SHORT).show();
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 12);
        }
    }
    static int count=0;

    private class YourAnalyzer implements ImageAnalysis.Analyzer{
        public YourAnalyzer(){
        }

        @Override
        public void analyze(@NonNull ImageProxy imageProxy) {
 ip=imageProxy;
        if(data.equals("")) {
            try {
                int rotationDegrees = imageProxy.getImageInfo().getRotationDegrees();
                Log.e("??", count++ + "--");
                //btn.setText("--"+count++);
                Image mediaImage = imageProxy.getImage();
                if (mediaImage != null) {
                    InputImage image =
                            InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
                    scanBarcodes(image);
                }

            } catch (Exception e) {
                Log.e("226", e.toString());
            }
        }else{

           finish();
        }
        }
    }

    private void scanBarcodes(InputImage image) {
        BarcodeScannerOptions options =
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(
                                Barcode.FORMAT_QR_CODE,
                                Barcode.FORMAT_AZTEC)
                        .build();
        BarcodeScanner scanner = BarcodeScanning.getClient();

        Task<List<Barcode>> result = scanner.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
            @Override
            public void onSuccess(@NonNull List<Barcode> barcodes) {

                if(!(barcodes.size()==0)) {
                    Barcode barcode = barcodes.get(0);
                    Rect bounds = barcode.getBoundingBox();
                    Point[] corners = barcode.getCornerPoints();
                    String rawValue = barcode.getRawValue();
DataContainer.QRDATA=rawValue;
                    data=rawValue;
//                    finish();
                }else{
                    DataContainer.QRDATA="";
                    Log.e("33","not found");
                    //Toast.makeText(getApplicationContext(),"cannot find barcode",Toast.LENGTH_LONG).show();

                }
if(ip!=null){
    ip.close();
}
                // [END get_barcodes]
                // [END_EXCLUDE]
            }
        })
               /* .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("",e.toString());
                        btn.setText(e.toString());
                       // Toast.makeText(getApplicationContext(), "---"+e.toString(), Toast.LENGTH_LONG).show();
                    }
        })*/
                .addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Barcode>> task) {
                        Log.e("--","completed");
                    }
                });
    }



}