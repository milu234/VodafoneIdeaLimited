package com.example.otplogin;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;





import android.graphics.Color;
import android.os.Bundle;

import android.view.Menu;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class CautroFragment extends Fragment {

    String arrayName[]={
            "1","2","3","4","5"
    };


    public CautroFragment() {
// Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_four, container, false);

//        CircleMenu circleMenu=(CircleMenu)RootView.findViewById(R.id.circle_menu);
//        circleMenu.setMainMenu(Color.parseColor("#ffcccc"),R.drawable.icon_menu,R.drawable.icon_home)
//                .addSubMenu(Color.parseColor("#ff0000"),R.drawable.icon_cancel)
//                .addSubMenu(Color.parseColor("#cc0000"),R.drawable.icon_notify)
//                .addSubMenu(Color.parseColor("#b20000"),R.drawable.icon_gps)
//                .addSubMenu(Color.parseColor("#990000"),R.drawable.icon_setting)
//                .addSubMenu(Color.parseColor("#ff0000"),R.drawable.icon_home)
//                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
//                    @Override
//                    public void onMenuSelected(int index) {
//                        Toast.makeText(getActivity(), "This is my Toast message!",
//                                Toast.LENGTH_LONG).show();
//
//                    }
//                });
        return RootView;
    }
}






