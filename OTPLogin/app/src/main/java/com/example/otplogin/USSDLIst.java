package com.example.otplogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class USSDLIst extends AppCompatActivity {

    ListView listView;
    String[] CodeNames = {"Check Balance","Check Your Number","V2V Night Minutes","Free SMS Balance"};
    int[] Images = {R.drawable.ic_account_balance_wallet_black_24dp,R.drawable.ic_contact_phone_black_24dp,R.drawable.ic_access_time_black_24dp,R.drawable.ic_sms_black_24dp};
    String[] CodeValues = {"*141#","*121#","*147#","*142#"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ussdlist);

        listView = findViewById(R.id.listview);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        //********************************************** To Display the Clickable list of USSD Codes**********************************************

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),DialCode.class);
                intent.putExtra("name",CodeNames[i]);
                intent.putExtra("values",CodeValues[i]);
                startActivity(intent);
            }
        });

    }

    private  class CustomAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return CodeNames.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.row_data,null);

            TextView CodeN = view1.findViewById(R.id.codes);
            ImageView image = view1.findViewById(R.id.images);

            CodeN.setText(CodeNames[i]);
            image.setImageResource(Images[i]);

            return view1;


        }
    }

    //**********************************************END******************************************************************
}
