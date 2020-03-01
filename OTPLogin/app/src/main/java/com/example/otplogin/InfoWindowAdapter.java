package com.example.otplogin;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public InfoWindowAdapter(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v =  inflater.inflate(R.layout.activity_info_page, null);

        ImageView imageView = (ImageView) v.findViewById(R.id.image_map);
        TextView nameOfPlace = (TextView) v.findViewById(R.id.nameOfPlace);
        TextView vicinity = (TextView) v.findViewById(R.id.vicinity);





        // TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
        String nameofplace = marker.getTitle();


        if (nameofplace.contains("Vodafone") || (nameofplace.contains("Store")) || nameofplace.contains("Idea") || nameofplace.contains("Vodafone Service") || nameofplace.contains("Idea Stores") || nameofplace.contains("Residency") || nameofplace.contains("Airtel") || nameofplace.contains("Cottage") ){
            imageView.setImageResource(R.drawable.techsupport);
        }


        nameOfPlace.setText(marker.getTitle());
        vicinity.setText(marker.getSnippet());

        //tvLng.setText("Longitude:"+ latLng.longitude);
        return v;
    }


}