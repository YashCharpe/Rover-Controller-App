package com.example.rovercontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SelectType extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);

        Bundle bundle=getIntent().getExtras();
        String macID=bundle.getString("macID");

        ImageView controllerImg=findViewById(R.id.controllerImg);
        ImageView voiceSelect=findViewById(R.id.voiceSelect);

        controllerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectType.this,Controller.class);
                intent.putExtra("macID",macID);
                startActivity(intent);
            }
        });

        voiceSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectType.this,VoiceController.class);
                intent.putExtra("macID",macID);
                startActivity(intent);
            }
        });

    }
}