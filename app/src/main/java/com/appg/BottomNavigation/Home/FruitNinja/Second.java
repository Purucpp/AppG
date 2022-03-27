package com.appg.BottomNavigation.Home.FruitNinja;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.appg.BottomNavigation.Home.FruitNinja.Adapter.FNApiInterface;
import com.appg.BottomNavigation.Home.FruitNinja.Adapter.FnApiClient;
import com.appg.BottomNavigation.Home.FruitNinja.SelectGame.SelectGame;
import com.appg.Firebase.Register.ApiModule.ApiClient;
import com.appg.Firebase.Register.ApiModule.ApiInterface;
import com.appg.Firebase.Register.Register;
import com.appg.NavigationView.Earn;
import com.appg.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Second extends Fragment {



    //ArrayList<String> selectedItems;

    ArrayList<String> list = new ArrayList<String>();

    ArrayList<String> urllist = new ArrayList<String>();

    ArrayList<String> stime = new ArrayList<String>();

    ArrayList<String> id = new ArrayList<String>();
    ArrayList<String> total_amount = new ArrayList<String>();
    ArrayList<String> league_date = new ArrayList<String>();
    ArrayList<String> entery_fee = new ArrayList<String>();
    ArrayList<String> total_users = new ArrayList<String>();
    ArrayList<String> connected_uses = new ArrayList<String>();
    ArrayList<String> strtotime = new ArrayList<String>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_second, container, false);









        GridView gridView; gridView = v.findViewById(R.id.gridview);







        FNApiInterface apiService = FnApiClient.getClient().create(FNApiInterface.class);
        Call<JsonArray> call = apiService.req();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.code() == 400) {

                }
                else {
/*
                    String temp = null;
                    try {
                        temp = response.body().string();
                        //txt.setText(temp);

                        Log.d("response",temp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } */

                    JsonArray json=new JsonArray();
                    json=response.body();

                    Log.d("res ->",json.toString());

                    JsonArray jsonArray = json.getAsJsonArray();

                    String fruitNames[] = new String[0];



                    for(int i=0;i<jsonArray.size();i++)
                    {
                        JsonObject job=new JsonObject();
                        // job=jsonArray.get(i).getAsJsonObject();
                        job=jsonArray.get(i).getAsJsonObject();

                        //  Log.d("one",job.get("name").toString().substring(1,job.get("name").toString().length()-1));
                      /*  Log.d("id",job.get("id").toString());
                        Log.d("Url",job.get("url").toString());
                        Log.d("imgurl",job.get("imgurl").toString());

                        */
                        // fruitNames[i]=job.get("name").toString();
                        // arrayList.add(job.get("name").toString());

                        //list.add(job.get("name").toString());
                        id.add(job.get("id").toString().substring(1,job.get("id").toString().length()-1));
                        total_amount.add(job.get("total_amount").toString().substring(1,job.get("total_amount").toString().length()-1));
                        league_date.add(job.get("league_date").toString().substring(1,job.get("league_date").toString().length()-1));

                        entery_fee.add(job.get("entry_fee").toString().substring(1,job.get("entry_fee").toString().length()-1));
                        total_users.add(job.get("total_users").toString().substring(1,job.get("total_users").toString().length()-1));
                        connected_uses.add(job.get("connected_uses").toString().substring(1,job.get("connected_uses").toString().length()-1));
                        strtotime.add(job.get("strtotime").toString().substring(1,job.get("strtotime").toString().length()-1));




                    }
                  //  Log.d("id ->",id.toString());
//                    Log.d("urllist",urllist.toString());
//                    Log.d("stime",stime.toString());
                    //  CustomAdapter customAdapter = new CustomAdapter();
                  //  gridView.setAdapter(customAdapter);

                    CustomAdapter customAdapter = new CustomAdapter();
                    gridView.setAdapter(customAdapter);

                    //  Toast.makeText(getActivity(),"clicked",Toast.LENGTH_LONG).show();

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getActivity(),"clicked",Toast.LENGTH_LONG).show();

                            Log.d("log","Clicked");

                            Intent intent = new Intent(getActivity(),SelectGame.class);
                            startActivity(intent);



                        }
                    });














                }
            }
            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {


                Log.d("failed",t.getMessage());

//                Toast.makeText(Register.this,
//                        "Failed", Toast.LENGTH_LONG).show();
            }

        });

        return v;
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return total_amount.size();
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
            View view1 = getLayoutInflater().inflate(R.layout.fn_mega_conterst,null);

            Button btn=view1.findViewById(R.id.jn);








           // CheckedTextView ch=view1.findViewById(R.id.checkbox);
            //getting view in row_data
            TextView name = view1.findViewById(R.id.fruits);
            TextView dt=view1.findViewById(R.id.date);
            TextView ttime=view1.findViewById(R.id.time);
            TextView joined=view1.findViewById(R.id.joined);
            TextView fee=view1.findViewById(R.id.fee);

            TextView totalu=view1.findViewById(R.id.totalu);
           // TextView connectedu=view1.findViewById(R.id.connectedu);
            //ImageView image = view1.findViewById(R.id.images);

            name.setText("Rs "+total_amount.get(i));
            dt.setText("Date : "+league_date.get(i));
            ttime.setText("Time :"+strtotime.get(i));
            joined.setText(connected_uses.get(i)+" JOINED");
            totalu.setText(total_users.get(i));
            fee.setText("Rs "+entery_fee.get(i));
           // name.setText(list.get(i));
            //  image.setImageResource(fruitImages[i]);
            //Picasso.get().load(urllist.get(i)).into(image);


            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    //DO SOMETHING! {RUN SOME FUNCTION ... DO CHECKS... ETC}
                   // Toast.makeText(getActivity(),league_date.get(i),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), SelectGame.class);

                    startActivity(intent);
                }
            });


            return view1;

        }


    }

}
