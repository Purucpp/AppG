package com.appg.NavigationView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.appg.Firebase.Register.ApiModule.ApiClient;
import com.appg.Firebase.Register.ApiModule.ApiInterface;
import com.appg.Firebase.Register.Register;
import com.appg.MainActivity;
import com.appg.NavigationView.ApiModule.GetClient;
import com.appg.NavigationView.ApiModule.GetInterface;
import com.appg.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyInfoSetting extends AppCompatActivity {

    private EditText inputEmail, inputgender,inputphone,inputdob,inputname,bal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info_setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("My Info Setting");

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#c42b0a")));


        inputEmail = (EditText) findViewById(R.id.registeremail);

        inputdob=(EditText)findViewById(R.id.registerdob);
        inputname=(EditText)findViewById(R.id.registername);
        inputphone=(EditText)findViewById(R.id.registerphone);

        inputgender=(EditText)findViewById(R.id.registergender);
        bal=(EditText)findViewById(R.id.registerbal);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();

        inputEmail.setText(email);



        Map<String, String> map = new HashMap<>();
        map.put("email",email);

        GetInterface apiService = GetClient.getClient().create(GetInterface.class);
        Call<JsonArray> call = apiService.req(map);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.code() == 400) {

                }
                else {



                    JsonArray json=new JsonArray();
                    json=response.body();

                    JsonObject job=new JsonObject();
                    job=json.get(0).getAsJsonObject();

                    Log.d("json res",job.get("email").toString());


                    inputname.setText(job.get("name").toString().substring(1,job.get("name").toString().length()-1));

                    inputphone.setText(job.get("phone").toString().substring(1,job.get("phone").toString().length()-1));

                    inputdob.setText(job.get("dob").toString().substring(1,job.get("dob").toString().length()-1));

                    inputgender.setText(job.get("gender").toString().substring(1,job.get("gender").toString().length()-1));
                    bal.setText(job.get("wallet_balance").toString().substring(1,job.get("wallet_balance").toString().length()-1));

                   // JsonArray jsonArray = json.getAsJsonArray();

                }

            }
            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {


                Log.d("failed",t.getMessage());

//                Toast.makeText(Register.this,
//                        "Failed", Toast.LENGTH_LONG).show();
            }

        });





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(MyInfoSetting.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}