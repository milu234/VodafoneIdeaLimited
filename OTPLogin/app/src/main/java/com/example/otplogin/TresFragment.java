package com.example.otplogin;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class TresFragment extends Fragment {


    public TresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_three, container, false);
        Button button1 = (Button)RootView.findViewById(R.id.arButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),  AugmentedFacesActivity.class);
                startActivity(intent);
            }
        });
        Button buttonTask = (Button)RootView.findViewById(R.id.task);

        buttonTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getActivity(), USSDLIst.class);
                startActivity(intent);
            }
        });


        return RootView;
    }





}
