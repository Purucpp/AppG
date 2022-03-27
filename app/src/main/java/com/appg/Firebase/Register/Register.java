package com.appg.Firebase.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.appg.Firebase.Login.Login;
import com.appg.Firebase.Register.ApiModule.ApiClient;
import com.appg.Firebase.Register.ApiModule.ApiInterface;
import com.appg.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private EditText inputEmail, inputPassword,inputphone,inputdob,inputname;
    private Button btnSignUp;

    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;

    private FirebaseAuth auth;

    String name,dob,phone,gender,bal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        radioSexGroup=findViewById(R.id.radioSex);
        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radioMale)
                {
                    gender="Male";
                   // Toast.makeText(getApplicationContext(), "Male", Toast.LENGTH_SHORT).show();
                }
                else {
                   // Toast.makeText(getApplicationContext(), "Femail!", Toast.LENGTH_SHORT).show();
                    gender="femail";
                }
            }
        });

        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        btnSignUp = (Button) findViewById(R.id.registerButton);
        inputEmail = (EditText) findViewById(R.id.registeremail);
        inputPassword = (EditText) findViewById(R.id.registerpassword);

        inputdob=(EditText)findViewById(R.id.registerdob);
        inputname=(EditText)findViewById(R.id.registername);
        inputphone=(EditText)findViewById(R.id.registerphone);

        ///

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                phone=inputphone.getText().toString().trim();
                bal="10";
                dob=inputdob.getText().toString().trim();
                name=inputname.getText().toString().trim();

                ///api call
                Map<String, String> map = new HashMap<>();
                map.put("name",name);
                map.put("email",email);
                map.put("phone",phone);
                map.put("dob",dob);
                map.put("gender",gender);
                map.put("wallet_balance",bal);

                Log.d("req->",map.toString());


                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponseBody> call = apiService.req(map);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.code() == 400) {

                        }
                        else {

                            String temp = null;
                            try {
                                temp = response.body().string();

                                Log.d("response",temp);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {


                        Log.d("failed",t.getMessage());

                        Toast.makeText(Register.this,
                                "Failed", Toast.LENGTH_LONG).show();
                    }

                });


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }


                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    //startActivity(new Intent(RegisterActivity.this, WelcomeActivity.class));
                                    //

                                    Toast.makeText(Register.this, "Authentication Successful." + task.getException(),
                                            Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(getApplicationContext(), Login.class);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        });



            }
        });

    }

    public void Go_to_Login(View v)
    {

        startActivity(new Intent(Register.this, Login.class));
    }
}