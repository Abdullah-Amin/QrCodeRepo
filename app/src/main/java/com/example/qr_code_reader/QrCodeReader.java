package com.example.qr_code_reader;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qrcodegenerator.R;
import com.google.gson.Gson;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.core.CameraPreview;
import me.dm7.barcodescanner.core.CameraWrapper;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrCodeReader extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    TextView nameTv, phoneTv;
    ImageView imageView;

    private static final String TAG = "QrCodeReader";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_reader);

        scannerView = findViewById(R.id.qr_scanner);
        nameTv = findViewById(R.id.name_tv);
        phoneTv = findViewById(R.id.phone_tv);
        imageView = findViewById(R.id.image);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scannerView.setResultHandler(QrCodeReader.this);
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
    }

    @Override
    protected void onDestroy() {
        scannerView.stopCamera();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        scannerView.stopCamera();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        scannerView.startCamera();
        scannerView.resumeCameraPreview(QrCodeReader.this);
        super.onRestart();
    }

    @Override
    protected void onResume() {
        scannerView.startCamera();
        scannerView.resumeCameraPreview(QrCodeReader.this);
        super.onResume();
    }

    @Override
    protected void onStart() {
        scannerView.startCamera();
        scannerView.resumeCameraPreview(QrCodeReader.this);
        super.onStart();
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.i(TAG, "abdo handleResult: "+ rawResult);
        Log.i(TAG, "abdo handleResult: "+ rawResult.getText());

        scannerView.startCamera();
        scannerView.setAutoFocus(true);

        String json = rawResult.getText();

        Gson gson = new Gson();

        QrData data = gson.fromJson(json, QrData.class);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(data.getName())
                .create();
        dialog.show();

        nameTv.setText(data.getName());
        phoneTv.setText(data.getPhone());
    }

}