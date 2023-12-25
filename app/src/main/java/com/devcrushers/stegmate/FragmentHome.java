package com.devcrushers.stegmate;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class FragmentHome extends Fragment {

    ImageButton encode, decode;
    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        encode=(view).findViewById(R.id.imageEncode);
        decode=(view).findViewById(R.id.imageDecode);
        encode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(((MainActivity)getActivity()),Encode.class);
                startActivity(i);
            }
        });

        decode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(((MainActivity)getActivity()),Decode.class);
                startActivity(i);
            }
        });

        return view;
    }
}