package com.example.rovercontroller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class SignUpPage extends AppCompatActivity {

    EditText emailEt,passEt;
    Button loginbtn,signbtn;
    FirebaseAuth fauth;
    ProgressBar progbar;
    ImageView showpasswordimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        emailEt=findViewById(R.id.emailEditText);
        passEt=findViewById(R.id.passwordEditText);
        loginbtn=findViewById(R.id.loginbtn);
        signbtn=findViewById(R.id.signBtn);
        fauth=FirebaseAuth.getInstance();
        progbar=findViewById(R.id.prog);
        progbar.setVisibility(View.INVISIBLE);
        showpasswordimg=findViewById(R.id.showpasswordimg);

        showpasswordimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pas=passEt.getText().toString();

                if (passEt.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ))
                {
                    showpasswordimg.setImageResource(R.drawable.ic_eye_close);
                    passEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );

                }else {
                    showpasswordimg.setImageResource(R.drawable.ic_eye_open);
                    passEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                }

            }
        });

        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailstr=emailEt.getText().toString();
                String passstr=passEt.getText().toString();

                progbar.setVisibility(View.VISIBLE);
                if(emailstr.isEmpty())
                {
                    emailEt.setError("Please Enter Username!");
                    emailEt.requestFocus();
                }
                else if(passstr.isEmpty())
                {
                    passEt.setError("Please Enter Password!");
                    passEt.requestFocus();
                }
                else if(emailstr.isEmpty()&&passstr.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Credentials",Toast.LENGTH_SHORT).show();
                }
                if(passstr.length()<6)
                {
                    Toast.makeText(getApplicationContext(),"Minimum Password Length should be 6",Toast.LENGTH_SHORT).show();
                }

                fauth.createUserWithEmailAndPassword(emailstr,passstr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            progbar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),"Account Created!!Login to App Now",Toast.LENGTH_SHORT).show();
                            fauth.signOut();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
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
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progbar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });


    }
}