package com.example;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PlanetFragment extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.planet_fragment, 
        		container, false);
        int position = getArguments().getInt("position");
        String planet = Planet.planetTitles[position];

        int imageId = Planet.planetImgResId[position];
        
        ImageView iv = (ImageView) rootView.findViewById(R.id.image);
        iv.setImageResource(imageId);
        
        getActivity().setTitle(planet);
        return rootView;
    }
}