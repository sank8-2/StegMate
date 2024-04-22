package com.devcrushers.stegmate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.ayush.imagesteganographylibrary.Text.AsyncTaskCallback.TextEncodingCallback;
import com.ayush.imagesteganographylibrary.Text.ImageSteganography;
import com.ayush.imagesteganographylibrary.Text.TextEncoding;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;

public class Encode extends AppCompatActivity implements TextEncodingCallback {
    private final int CAMERA_REQ_CODE = 10;
    private final int GALLERY_REQ_CODE = 100;
    ImageView imgCamera;
    private Bitmap original_image;
//    private TextView whether_encoded;
    private Bitmap encoded_image;

    private Uri encoded_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);
        imgCamera = findViewById(R.id.imageUpload);
//        encoded = findViewById(R.id.displayEncodedImage);
        ShapeableImageView btnCamera = findViewById(R.id.selectCamBtn);
        ShapeableImageView btnGallery = findViewById(R.id.selectImageBtn);
        ShapeableImageView btnEncode = findViewById(R.id.encodeBtn);
        EditText privateKey = findViewById(R.id.privateKey);
        EditText secretMessage = findViewById(R.id.secretMessage);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(iCamera, CAMERA_REQ_CODE);

            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });

        secretMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        privateKey.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });


        btnEncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                original_image = ((BitmapDrawable) imgCamera.getDrawable()).getBitmap();
                ImageSteganography imageSteganography = new ImageSteganography(secretMessage.getText().toString(), privateKey.getText().toString(), original_image);
                TextEncoding textEncoding = new TextEncoding(Encode.this, Encode.this);

                textEncoding.execute(imageSteganography);
            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == CAMERA_REQ_CODE) {
                Uri camImgUri;
                Bitmap img = (Bitmap) data.getExtras().get("data");

                WeakReference<Bitmap> result = new WeakReference<>(Bitmap.createScaledBitmap(img, img.getWidth(), img.getHeight(), false).copy(
                        Bitmap.Config.RGB_565, true
                ));

                Bitmap bm=result.get();

                camImgUri=saveImage(bm,Encode.this);
                imgCamera.setImageURI(camImgUri);

//                imgCamera.setImageBitmap(bm);
            }

            if (requestCode == GALLERY_REQ_CODE) {
                imgCamera.setImageURI(data.getData());
            }
        }
    }

    private Uri saveImage(Bitmap image, Context context) {
        File imagesFolder=new File(context.getCacheDir(),"images");
        Uri uri=null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder,"captured_image.jpg");
            FileOutputStream stream=new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG,100,stream);
            uri=Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }

    @Override
    public void onStartTextEncoding() {

    }

    public void send(Bitmap result)
    {
        Intent i = new Intent(Encode.this, Encoded.class);
        i.putExtra("image",result);
        startActivity(i);
    }

    @Override
    public void onCompleteTextEncoding(ImageSteganography result) {
//        this.result = result;

        if (result != null && result.isEncoded()) {

            //encrypted image bitmap is extracted from result object
            encoded_image = result.getEncoded_image();
            encoded_uri = saveImage(encoded_image,Encode.this);
//            uri = encoded_image;
            send(encoded_image);
            //set text and image to the UI component.
//            encoded.setImageBitmap(encoded_image);
        }
    }

}