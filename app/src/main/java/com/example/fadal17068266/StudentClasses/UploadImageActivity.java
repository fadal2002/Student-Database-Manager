package com.example.fadal17068266.StudentClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fadal17068266.DatabaseHelper;
import com.example.fadal17068266.ImageHelper;
import com.example.fadal17068266.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UploadImageActivity extends AppCompatActivity {

    public static final int GET_IMAGE_CODE = 1;
    public static final int PERMISSION_CODE = 2;
    public String imageUri;
    public Bitmap imageBitmap;
    public byte[] imageBytes;
    private ImageButton mStudentImgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        mStudentImgBtn = (ImageButton) findViewById(R.id.selectImgBtn);
    }

    public void selectImgBtnOnClick(View view)
    {
        //check for permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            {
                //permission not granted, request it
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                //request permission
                requestPermissions(permissions, PERMISSION_CODE);
            }
            else
            {
                //permission already granted
                selectImage();
            }
        }
        else
        {
            //system os is less than marshmallow
            selectImage();
        }
    }

    public void submitImageBtn(View view)
    {
        Intent intent = getIntent();
        intent.putExtra("imgBytes", imageBytes);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void returnToStudListFromUploadImgActBtnOnClick(View view)
    {
        finish();
    }

    private void selectImage()
    {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, GET_IMAGE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //permission granted
                    selectImage();
                }
                else
                {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_IMAGE_CODE && resultCode == RESULT_OK) {

            //setting the image button image to the selected image
            mStudentImgBtn.setImageURI(data.getData());
            imageUri = data.getData().toString();
            imageBitmap = ((BitmapDrawable)mStudentImgBtn.getDrawable()).getBitmap();
            imageBytes = ImageHelper.getBytes(imageBitmap, 100);
        }
    }
}
