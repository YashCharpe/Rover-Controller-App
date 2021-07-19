package com.example.rovercontroller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameet,passwordet;
        Button checkButton,signUpbtn;
        FirebaseAuth fauth;
        ProgressBar progbar;
        ImageView showpasswordimg;

        usernameet = (EditText)findViewById(R.id.userNameEditText);
        passwordet = (EditText)findViewById(R.id.passwordEditText);
        checkButton = (Button)findViewById(R.id.checkbtn);
        signUpbtn=findViewById(R.id.signUpbtn);
        fauth=FirebaseAuth.getInstance();
        progbar=findViewById(R.id.prog);
        showpasswordimg=findViewById(R.id.showpasswordimg);

        progbar.setVisibility(View.INVISIBLE);
        if(fauth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),Dashboard.class));
            finish();
        }


        showpasswordimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pas=passwordet.getText().toString();

                if (passwordet.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ))
                {
                    showpasswordimg.setImageResource(R.drawable.ic_eye_close);
                    passwordet.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );

                }else {
                    showpasswordimg.setImageResource(R.drawable.ic_eye_open);
                    passwordet.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                }

            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usrName = usernameet.getText().toString();
                String pass = passwordet.getText().toString();

                progbar.setVisibility(View.VISIBLE);

                if(usrName.isEmpty())
                {
                    usernameet.setError("Please Enter Username!");
                    usernameet.requestFocus();
                }
                else if(pass.isEmpty())
                {
                    passwordet.setError("Please Enter Password!");
                    passwordet.requestFocus();
                }
                else if(usrName.isEmpty()&&pass.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Credentials",Toast.LENGTH_SHORT).show();
                }

                fauth.signInWithEmailAndPassword(usrName,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            progbar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),"Login Successful!!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Dashboard.class));
                            finish();
                        }
                        else
                        {
                            progbar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),"Error! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progbar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(getApplicationContext(),SignUpPage.class));
                finish();
            }
        });

    }
}