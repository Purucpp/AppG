package com.appg.BottomNavigation.Home.FruitNinja.SelectGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.appg.BottomNavigation.Home.FruitNinja.Adapter.FNApiInterface;
import com.appg.BottomNavigation.Home.FruitNinja.Adapter.FnApiClient;
import com.appg.BottomNavigation.Home.FruitNinja.SelectGame.Adapter.FnSelectGame;
import com.appg.BottomNavigation.Home.FruitNinja.SelectGame.Adapter.FnSelectGameClient;
import com.appg.BottomNavigation.Home.FruitNinja.SelectStar.FNSelectStart;
import com.appg.MainActivity;
import com.appg.NavigationView.Earn;
import com.appg.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectGame extends AppCompatActivity {


    int ct=0;
    ArrayList<String> selectedItems;


    ArrayList<String> list = new ArrayList<String>();

    ArrayList<String> urllist = new ArrayList<String>();

    ArrayList<String> stime = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Fruit Ninja");

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#c42b0a")));



        selectedItems=new ArrayList<String>();

        ListView chl=(ListView)findViewById(R.id.checkable_list);
        Button btn=(Button)findViewById(R.id.btshow);

        FnSelectGame apiService = FnSelectGameClient.getClient().create(FnSelectGame.class);
        Call<JsonArray> call = apiService.req();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.code() == 400) {

                }
                else {


                    JsonArray json=new JsonArray();
                    json=response.body();

                    JsonArray jsonArray = json.getAsJsonArray();

                    String fruitNames[] = new String[0];

                    for(int i=0;i<jsonArray.size();i++)
                    {

                        JsonObject job=new JsonObject();
                        // job=jsonArray.get(i).getAsJsonObject();
                        job=jsonArray.get(i).getAsJsonObject();

                        //  Log.d("one",job.get("name").toString().substring(1,job.get("name").toString().length()-1));
                        list.add(job.get("id").toString().substring(1,job.get("id").toString().length()-1));

                        urllist.add(job.get("game_url").toString().substring(1,job.get("game_url").toString().length()-1));

                        stime.add(job.get("strtotime").toString().substring(1,job.get("strtotime").toString().length()-1));
                    }
                    Log.d("list",list.toString());
                    Log.d("urllist",urllist.toString());
                    Log.d("stime",stime.toString());
                    chl.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    String[] items={"English","Chinese","French","German","Italian","Khmer"};
                    //supply data itmes to ListView
                    ArrayAdapter<String> aa=new ArrayAdapter<String>(SelectGame.this,R.layout.raw_data,R.id.txt_title,list);
                    chl.setAdapter(aa);
                    //set OnItemClickListener



                    chl.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                            String selectedItem = ((TextView) view).getText().toString();



                            if(ct<9)
                            {


                                        // selected item

                                        if(selectedItems.contains(selectedItem))
                                        {
                                            selectedItems.remove(selectedItem);
                                            Log.d("Selected",selectedItems.toString());

                                            ct--;
                                            Log.d("count",ct+"");
                                            Log.d("position",position+1+"");
                                        }
                                        else
                                        {
                                            selectedItems.add(selectedItem);
                                            Log.d("Selected",selectedItems.toString());
                                            ct++;
                                            Log.d("count",ct+"");
                                            Log.d("position",position+1+"");

                                            if(ct==9)
                                            {
                                                Intent intent = new Intent(SelectGame.this, FNSelectStart.class);
                                                intent.putExtra("ss",selectedItems);

                                                startActivity(intent);
                                            }

                                        }




                            }
                            else {

                                /*
                                if(selectedItems.contains(Integer.toString(position)))
                                {

                                    selectedItems.remove(position+1);

                                    ct--;

                                    Log.d("id","if");

                                    Log.d("count",ct+"");
                                    Log.d("position",position+1+"");
                                    Log.d("Selected",selectedItems.toString());


//                                    chl.setItemChecked(position, true);


                                }
                                else {
                                    chl.setItemChecked(position, false);

                                    Log.d("count",ct+"");
                                    Log.d("position",position+1+"");
                                    Log.d("Selected",selectedItems.toString());

                                }*/


                            }

                        }
                    }


                    );


                }

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v)
                    {
                        //DO SOMETHING! {RUN SOME FUNCTION ... DO CHECKS... ETC}
                        // Toast.makeText(getActivity(),league_date.get(i),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SelectGame.this, FNSelectStart.class);
                        intent.putExtra("ss",selectedItems);

                        startActivity(intent);
                    }
                });



            }
            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {


                Log.d("failed",t.getMessage());

//                Toast.makeText(Register.this,
//                        "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void showSelectedItems(View view){
        String selItems="";
        for(String item:selectedItems){
            if(selItems=="")
                selItems=item;
            else
                selItems+="/"+item;
        }
        //Toast.makeText(getActivity(), selItems, Toast.LENGTH_LONG).show();
        Log.d("selected",selItems);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(SelectGame.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}