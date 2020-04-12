package com.example.fadal17068266;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//a class that contains function that are required to deal with bitmap images with SQL databases
public class ImageHelper {

    private static final String TAG = "ImageHelper";

    //a function that will return the bitmap of an image from an image uri
    public static Bitmap getBitmap(String ImgUri)
    {
        //opening the file of the image
        File imageFile = new File(ImgUri.toString());
        FileInputStream fis = null;
        Bitmap bitmap = null;

        try {
            //creating the FileInputStream for the image file
            fis = new FileInputStream(imageFile);
            //decoding the file input stream to create the bitmap
            bitmap = BitmapFactory.decodeStream(fis);
        }catch (FileNotFoundException e)
        {
            Log.d("UploadImgAct", "onActivityResult: FileNotFoundException " + e.getMessage());
        }finally {
            try {
                fis.close();
            }catch (IOException e)
            {
                Log.d("UploadImgAct", "onActivityResult: IOException " + e.getMessage());
            }
        }
        //returning the bitmap
        return bitmap;
    }

    //a function that converts the bitmap of an image into a byte array so that it can be stored into the database
    public static byte[] getBytes(Bitmap bm, int quality)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        try {
            stream.close();
        }catch (IOException e)
        {
            Log.d("UploadImgAct", "onActivityResult: IOException " + e.getMessage());
        }
        return stream.toByteArray();
    }

    //a function that converts the image byte array into a bitmap
    public static Bitmap getImageFromByteArr(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    //a function that will resize the image so that it fits the image view in the student item view and the student-module item view
    public static Bitmap getResizedImage(byte[] image)
    {
        //converting the byte array into a bitmap
        Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length);
        //resizing the image to 250 width and 250 height
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bm, 250, 250, false);
        return resizedBitmap;
    }
}
