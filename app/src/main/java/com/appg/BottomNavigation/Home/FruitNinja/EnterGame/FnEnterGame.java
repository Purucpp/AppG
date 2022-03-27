package com.appg.BottomNavigation.Home.FruitNinja.EnterGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.appg.BottomNavigation.Home.FruitNinja.GamePlay.FnGamePlay;
import com.appg.BottomNavigation.Home.FruitNinja.SelectGame.SelectGame;
import com.appg.BottomNavigation.Home.FruitNinja.SelectStar.FNSelectStart;
import com.appg.MainActivity;
import com.appg.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class FnEnterGame extends AppCompatActivity {


    ArrayList<String> getlist = new ArrayList<String>();

    ArrayList<String> tt = new ArrayList<String>();

    ArrayList<String> a = new ArrayList<String>();

    ArrayList<String> b = new ArrayList<String>();


   // String a,b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fn_enter_game);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Fruit Ninja");

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#c42b0a")));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();

        Button btn=(Button)findViewById(R.id.btshow);



        Intent intent = getIntent();
        // String receivedName[] =  intent.getStringExtra("ss");

        getlist=intent.getStringArrayListExtra("star");
        a=intent.getStringArrayListExtra("one");
        b=intent.getStringArrayListExtra("two");

        Log.d("gatList",getlist.toString());
        Log.d("one",a.get(0));
        Log.d("two",b.get(0));

        int j=getlist.indexOf(b.get(0));
        int k=getlist.indexOf(a.get(0));

        Log.d("index",j+"");

        for(int i=0;i<getlist.size();i++)
        {
            if(i==j)
            {
             tt.add("https://appg.in/Game/FruitSlasher/index.html?email="+email+"&playtype=3");
            }
            else if(i==k)
            {
                tt.add("https://appg.in/Game/FruitSlasher/index.html?email="+email+"&playtype=1");
            }
            else {
                tt.add("https://appg.in/Game/FruitSlasher/index.html?email="+email+"&playtype=0");
            }

        }


        Log.d("url->",tt.toString());





        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                //DO SOMETHING! {RUN SOME FUNCTION ... DO CHECKS... ETC}
                // Toast.makeText(getActivity(),league_date.get(i),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(FnEnterGame.this, FnGamePlay.class);
                intent.putExtra("url",tt);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(FnEnterGame.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}