package com.example.otplogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class RechargeDataFragment extends Fragment {
    View Rootview;
    CardView firstCardView;
    CardView secondCardView;

    public RechargeDataFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Rootview = inflater.inflate(R.layout.recharge_data_fragment, container, false);
        firstCardView= (CardView) Rootview.findViewById(R.id.firstCardView);
        firstCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),RazorPayGateway.class);
                intent.putExtra("RechargeUnlimitedFragment","249");
                startActivity(intent);
            }
        });
        secondCardView = (CardView) Rootview.findViewById(R.id.secondCardView);secondCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(),RazorPayGateway.class);
                intent1.putExtra("RechargeUnlimitedFragment","79");
                startActivity(intent1);
            }
        });

        return Rootview;
    }
}
