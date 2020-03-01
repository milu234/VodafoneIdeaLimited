package com.example.otplogin;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.provider.DocumentsContract;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.graphics.Color;
import android.os.Bundle;

import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class UnoFragment extends Fragment implements TextToSpeech.OnInitListener {
    private static final int REQUEST_CALL = 1;

    Button sastaRecharge;
    Button earnCoins;
    Button dealsOffers;
    Button scratchCards;

    String arrayName[]={
            "1","2","3","4","5"
    };


    public UnoFragment() {
// Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View RootView = inflater.inflate(R.layout.fragment_home, container, false);

        CircleMenu circleMenu=(CircleMenu)RootView.findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#ffcccc"),R.drawable.menu,R.drawable.icon_home)
                .addSubMenu(Color.parseColor("#ff0000"),R.drawable.icon_cancel)
                .addSubMenu(Color.parseColor("#cc0000"),R.drawable.icon_notify)
                .addSubMenu(Color.parseColor("#b20000"),R.drawable.icon_gps)
                .addSubMenu(Color.parseColor("#990000"),R.drawable.icon_setting)
                .addSubMenu(Color.parseColor("#ff0000"),R.drawable.icon_home)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {
                        switch (index){

                            case 1:

                                Toast.makeText(getActivity(), "This is my Toast message!"+arrayName[index],
                                        Toast.LENGTH_LONG).show();
                                Intent intent =  new Intent(getActivity(), USSDLIst.class);
                                startActivity(intent);
                                break;
                            case 2 :
                                Toast.makeText(getActivity(), "This is my Toast message!"+arrayName[index],
                                        Toast.LENGTH_LONG).show();
                                Intent intent1 =  new Intent(getActivity(), MapsActivity.class);
                                startActivity(intent1);
                                break;
                            case 3 :
                                Intent intent2 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                                startActivityForResult(intent2, 10);
                        }


                    }
                });


        SwipeButton swipeButton=(SwipeButton) RootView.findViewById(R.id.swipe_btn);

        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {

                Toast.makeText(getActivity(), "active"+active,
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),RazorPayGateway.class);
                intent.putExtra("RechargeUnlimitedFragment","399");
                startActivity(intent);

            }
        });

        sastaRecharge = (Button)RootView.findViewById(R.id.sastaRecharge);
        sastaRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new RechargeGroupFragment());
            }
        });

        earnCoins = (Button)RootView.findViewById(R.id.earnCoins);
        earnCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new TresFragment());
            }
        });
        dealsOffers = (Button)RootView.findViewById(R.id.dealsOffers);
        dealsOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new DealsOffers());
            }
        });
        scratchCards = (Button)RootView.findViewById(R.id.scratchCards);
        scratchCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VideoWebView.class);
                startActivity(intent);
            }
        });

        return RootView;
    }

    @Override
    public void onInit(int i) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 10:
                    String Found = new UnoFragment().getNumberFromResult(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS));
                    if (Found.equals("Recharge")) {
                        loadFragment(new DosFragment());

                        //firstNumTextView.setText(String.valueOf(intFound));
                    } else if (Found.equals("Home")){
                        Intent intent = new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);

                    } else if (Found.equals("Vodafone Stores Near Me")){
                        Intent intent = new Intent(getActivity(),MapsActivity.class);
                        startActivity(intent);

                    }  else if (Found.equals("QuickRecharge")){
                        Intent intent = new Intent(getActivity(),RazorPayGateway.class);
                        intent.putExtra("RechargeUnlimitedFragment","399");
                        startActivity(intent);


                    } else if (Found.equals("Profile")) {
                       loadFragment(new AccountFragment());
                    }

                    else if (Found.equals("Offers")) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }

                    else if (Found.equals("Calling")) {
                        makePhoneCall();

                    }

                    else {
                        Toast.makeText(getActivity(), "Sorry, I didn't catch that! Please try again", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }else {
            Toast.makeText(getActivity(), "Failed to recognize speech!", Toast.LENGTH_LONG).show();
        }
    }


    String getNumberFromResult(ArrayList<String> results) {
        for (String str : results) {
            if (!getIntNumberFromText(str).equals("Sorry")) {
                return getIntNumberFromText(str);
            }
        }
        return "No";
    }


    private String getIntNumberFromText(String strNum) {
        switch (strNum) {
            case "recharge":
                return "Recharge";
            case "home":
                return "Home";
            case "Vodafone stores near me":
                return "Vodafone Stores Near Me";
            case "Vodafone stores":
                return "Vodafone Stores Near Me";
            case "vodacoins":
                return "Profile";
            case "balance":
                return "Profile";
            case "data":
                return "Profile";
            case "quick recharge":
                return "QuickRecharge";
            case "offers":
                return "Offers";
            case "call customer care":
                return "Calling";
        }
        return "Sorry";
    }



    //***********************************************************************************************************
    private void makePhoneCall(){

        String number = "9820098200";
        if (number.trim().length()> 0) {

//
//
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

            } else {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + Uri.encode(number)));
                startActivity(callIntent);
            }
        }
        else {
            Toast.makeText(getActivity(),"Enter USSD Code",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode ==REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            } else {
                Toast.makeText(getActivity(),"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}






