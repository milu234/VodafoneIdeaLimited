package com.example.otplogin;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class TresFragment extends Fragment {


    public TresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_three, container, false);

        Button playGames = (Button)RootView.findViewById(R.id.playGames);
        playGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),VideoWebView.class);
                startActivity(intent);
            }
        });

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
                Intent intent =  new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        Button buttonSurvey = (Button)RootView.findViewById(R.id.survey);

        buttonSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getActivity(), SurveyActivity.class);
                startActivity(intent);
            }
        });

        Button spendCoins = (Button)RootView.findViewById(R.id.spend_voda_coins);
        spendCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new DealsOffers());
            }
        });
        return RootView;
    }

    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }






}
