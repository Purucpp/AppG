package com.appg.BottomNavigation.Home.FruitNinja.SelectStar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appg.BottomNavigation.Home.FruitNinja.EnterGame.FnEnterGame;
import com.appg.BottomNavigation.Home.FruitNinja.SelectGame.SelectGame;
import com.appg.MainActivity;
import com.appg.R;

import java.util.ArrayList;

public class FNSelectStart extends AppCompatActivity {

    int onei=0;
    int twoi=0;

    ArrayList<String> list = new ArrayList<String>();

    ArrayList<String> one = new ArrayList<String>();
    ArrayList<String> two = new ArrayList<String>();



    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_n_select_start);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Fruit Ninja");

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#c42b0a")));

        gridView = findViewById(R.id.gridview);
        Button btn=(Button)findViewById(R.id.btshow);

        Intent intent = getIntent();
       // String receivedName[] =  intent.getStringExtra("ss");



        list=intent.getStringArrayListExtra("ss");


        Log.d("extra",list.toString());

        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);



        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                //DO SOMETHING! {RUN SOME FUNCTION ... DO CHECKS... ETC}
                // Toast.makeText(getActivity(),league_date.get(i),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(FNSelectStart.this, FnEnterGame.class);
                intent.putExtra("star",list);
                intent.putExtra("one",one);
                intent.putExtra("two",two);

                startActivity(intent);
            }
        });



    }



    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
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
            View view1 = getLayoutInflater().inflate(R.layout.fnselectstart,null);

            CheckedTextView onestar=view1.findViewById(R.id.onestart);
            CheckedTextView towstar=view1.findViewById(R.id.towstar);
            //getting view in row_data
            TextView name = view1.findViewById(R.id.text);
            //ImageView image = view1.findViewById(R.id.images);


          //  towstar.setChecked(false);

            name.setText(list.get(i));
            //  image.setImageResource(fruitImages[i]);
            //Picasso.get().load(urllist.get(i)).into(image);

            onestar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    if(onei==0)
                    {

                            ((CheckedTextView) v).toggle();
                            towstar.setVisibility(View.GONE);


                            if(one.contains(list.get(i)))
                            {
                                one.remove(list.get(i));
                                Log.d("One",one.toString());
                            }
                            else
                            {
                                one.add(list.get(i));
                                Log.d("One",one.toString());

                            }

                            onei=1;

                    }





                }
            });


            towstar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {

                    if(twoi==0)
                    {

                        onestar.setVisibility(View.GONE);

                            ((CheckedTextView) v).toggle();
                            if(two.contains(list.get(i)))
                            {
                                two.remove(list.get(i));
                                Log.d("Two",two.toString());
                            }
                            else
                            {
                                two.add(list.get(i));
                                Log.d("Two",two.toString());

                            }

                            twoi=1;
                    }

                }
            });



            return view1;

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(FNSelectStart.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}