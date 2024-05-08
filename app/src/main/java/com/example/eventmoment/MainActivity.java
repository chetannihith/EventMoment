package com.example.eventmoment;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import com.google.zxing.WriterException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Generate an access code
        String accessCode = CodeGenerator.generateAccessCode();
        System.out.println("Generated Access Code: " + accessCode);

        // Generate a QR code
        try {
            Bitmap qrCode = CodeGenerator.generateQRCode(accessCode);
            // TODO: Display the generated QR code in an ImageView
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}