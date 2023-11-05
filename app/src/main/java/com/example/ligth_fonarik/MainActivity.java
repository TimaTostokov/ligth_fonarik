package com.example.ligth_fonarik;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Bitmap originalImage;
    Bitmap newImage;
    ImageView imageView;
    Boolean isFonaricOn;
    int newWidth, newHeigth;
    CameraManager cameraManager;
    String camareID;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemI = item.getItemId();
        if (itemI == R.id.menu_utem1) {

        } else if (itemI == R.id.menu_utem2) {}
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            camareID = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }
        setStartPostition();
        uploadFonaricImage();
        viewRigthPartOfImage();

        newWidth = originalImage.getWidth() / 2;
        newHeigth = originalImage.getHeight();
        isFonaricOn = false;

        isFonaricOn = false;
        imageView.setOnClickListener(v -> {
            if (isFonaricOn) {
                isFonaricOn = false;
                viewRigthPartOfImage();

                try {
                    cameraManager.setTorchMode(camareID, isFonaricOn);
                } catch (CameraAccessException e) {
                    throw new RuntimeException(e);
                }

            } else {
                isFonaricOn = true;
                newImage = Bitmap.createBitmap(originalImage, 0, 0, newWidth, newHeigth);
                imageView.setImageBitmap(newImage);
                try {
                    cameraManager.setTorchMode(camareID, isFonaricOn);
                } catch (CameraAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void viewRigthPartOfImage() {
        newImage = Bitmap.createBitmap(originalImage, newWidth, 0, newWidth, newHeigth);
        imageView.setImageBitmap(newImage);

    }

    private void uploadFonaricImage() {
        imageView = findViewById(R.id.imageView);
        originalImage = BitmapFactory.decodeResource(getResources(), R.drawable.switcher);
    }

    private void uploadFridgeImage() {
        imageView = findViewById(R.id.imageView);
        originalImage = BitmapFactory.decodeResource(getResources(), R.drawable.fridge);

    }

    private void setStartPostition() {
        imageView = findViewById(R.id.imageView);

        originalImage = BitmapFactory.decodeResource(getResources(), R.drawable.switcher);
        newWidth = originalImage.getWidth() / 2;
        newHeigth = originalImage.getHeight();

        newImage = Bitmap.createBitmap(originalImage, newWidth, 0, newWidth, newHeigth);
        imageView.setImageBitmap(newImage);
        isFonaricOn = false;
    }

}