package com.example.qrcodegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.qr_code_reader.QrCodeReader;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText dataEditText;
    ImageView qrCodeImageView;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        dataEditText = findViewById(R.id.data_et);
        qrCodeImageView = findViewById(R.id.qr_code_image_view);
    }

    public void generateQrCode(View view) throws JSONException {

//        String data1 = dataEditText.getText().toString();
//        List<Object> list = new ArrayList<>();
//
//        list.add("asr");
//        list.add("https://i0.wp.com/programmerworld.co/wp-content/uploads/2019/12/Screenshot_2019-12-14-06-32-25-575_com.android.camera.jpg?resize=768%2C1536&ssl=1");
//        list.add(2);


        MultiFormatWriter writer = new MultiFormatWriter();

        try {
//            BitMatrix matrix = writer.encode(list.toString(), BarcodeFormat.QR_CODE, 400, 400);
//            JSONObject jsonObject = new JSONObject(String.valueOf(getApplicationContext().getAssets().open("data.json")));
            String jsonString = null;

            InputStream is = getApplicationContext().getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, StandardCharsets.UTF_8);

            Log.i(TAG, "abdo inside generateQrCode: "+ jsonString);

//            JSONObject object = new JSONObject(
//                    String.valueOf(getApplicationContext().getAssets().open("data.json").read()));

            Log.i(TAG, "abdo generateQrCode: "+ jsonString);

            BitMatrix matrix2 = writer.encode(
                    jsonString
                    , BarcodeFormat.QR_CODE, 400, 400);

//            Log.i(TAG, "abdo generateQrCode: "+ object);


            BarcodeEncoder encoder = new BarcodeEncoder();

            Bitmap bitmap = encoder.createBitmap(matrix2);

            qrCodeImageView.setImageBitmap(bitmap);

            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

//            manager.hideSoftInputFromWindow(dataEditText.getApplicationWindowToken(), 0);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

    public void readQrCode(View view) {
        startActivity(new Intent(this, QrCodeReader.class));
    }
}