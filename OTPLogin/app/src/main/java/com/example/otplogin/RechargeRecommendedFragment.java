package com.example.otplogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.otplogin.R;

public class RechargeRecommendedFragment extends Fragment {

    View Rootview;
    CardView firstCardViewRechargeRecommended;
    CardView secondCardViewRechargeRecommended;

    TextView price,call,data,validity;



    public RechargeRecommendedFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Rootview = inflater.inflate(R.layout.recharge_recommended_fragment, container, false);

        price = (TextView)Rootview.findViewById(R.id.planView);
        call = (TextView)Rootview.findViewById(R.id.talktimeBalanceView);
        data = (TextView)Rootview.findViewById(R.id.dataBalanceView);
        firstCardViewRechargeRecommended= (CardView) Rootview.findViewById(R.id.firstCardViewRchargeRecommended);
        firstCardViewRechargeRecommended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),RazorPayGateway.class);
                intent.putExtra("RechargeUnlimitedFragment","249");
                startActivity(intent);
            }
        });
        secondCardViewRechargeRecommended = (CardView) Rootview.findViewById(R.id.secondCardViewRechargeRecommended);
        secondCardViewRechargeRecommended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(),RazorPayGateway.class);
                intent1.putExtra("RechargeUnlimitedFragment","79");
                startActivity(intent1);
            }
        });

        // validity = (TextView)view.findViewById(R.id.validityView);


        return Rootview;
    }
}
