package com.devcrushers.stegmate;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentEncode extends Fragment {

    public FragmentEncode() {
        // Required empty public constructor
    }

    private final int CAMERA_REQ_CODE=10;
    private final int GALLERY_REQ_CODE=100;
    ImageView imgCamera;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_encode, container, false);

        imgCamera = view.findViewById(R.id.imageUpload);
        Button btnCamera = view.findViewById(R.id.selectCamBtn);
        Button btnGallery = view.findViewById(R.id.selectImageBtn);
        Button btnEncode = view.findViewById(R.id.encodeBtn);
        TextView privateKey = view.findViewById(R.id.privateKey);
        TextView secretMessage = view.findViewById(R.id.secretMessage);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(iCamera,CAMERA_REQ_CODE);

            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY_REQ_CODE);
            }
        });



//        Log.d("myTag",);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){

            if (requestCode==CAMERA_REQ_CODE) {
                Bitmap img = (Bitmap) (data.getExtras().get("data"));
                imgCamera.setImageBitmap(img);
            }

            if (requestCode==GALLERY_REQ_CODE){
                imgCamera.setImageURI(data.getData());
            }
        }
    }
}