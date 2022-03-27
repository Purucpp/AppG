package com.appg.Firebase.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appg.BottomNavigation.Home.FruitNinja.EnterGame.FnEnterGame;
import com.appg.BottomNavigation.Home.FruitNinja.SelectGame.SelectGame;
import com.appg.Firebase.Register.Register;
import com.appg.MainActivity;
import com.appg.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;

    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {

            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }


        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
       // getWindow().setStatusBarColor(getResources().getColor(R.color.myrandomcolor2));

        inputEmail = (EditText) findViewById(R.id.editTextEmail);
        inputPassword = (EditText) findViewById(R.id.editTextPassword);

        btnLogin = (Button) findViewById(R.id.cirLoginButton);


        auth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(getApplicationContext(), "pressed", Toast.LENGTH_SHORT).show();
                String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }



                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError("password length is less then 6");
                                    } else {
                                        Toast.makeText(Login.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });

    }



    //reset password
    private void showForgotDialog(Context c) {
        final EditText taskEditText = new EditText(c);

        taskEditText.setHint("Email");
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Forgot Password")
                .setMessage("Please enter your email address")
                .setView(taskEditText)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String eml = String.valueOf(taskEditText.getText()).trim();

                        if (TextUtils.isEmpty(eml)) {
                            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();

                        }
                        else {


                            FirebaseAuth.getInstance().sendPasswordResetEmail(eml)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Toast.makeText(getApplicationContext(), "Link to reset password sent to your mail ", Toast.LENGTH_SHORT).show();
                                                // Log.d(TAG, "Email sent.");
                                            }
                                        }
                                    });
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    public void Go_to_Signup(View v)
    {

        startActivity(new Intent(Login.this, Register.class));
    }

    public void viewForgotPAssword(View v)
    {
        showForgotDialog(Login.this);
    }




}