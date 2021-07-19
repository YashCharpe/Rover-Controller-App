package com.example.rovercontroller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Dashboard extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;

    TextView mStatusTv, mPaired;
    ImageView StatusImg,logoutimg;
    Button turn_on_btn,turn_off_btn,discoverable_btn,paired_btn,continue_btn;
    FirebaseAuth fauth;

    BluetoothAdapter mBlueAdapter;

    Spinner spinner;
    List<String> list1=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mStatusTv = findViewById(R.id.status);

        StatusImg = findViewById(R.id.bluetoothIv);
        turn_on_btn = findViewById(R.id.turn_on_btn);
        turn_off_btn = findViewById(R.id.turn_off_btn);
        discoverable_btn = findViewById(R.id.discoverable_btn);

        logoutimg=findViewById(R.id.logoutimg);
        continue_btn = findViewById(R.id.continue_btn);

        spinner= findViewById(R.id.deviceSpinner);

        fauth=FirebaseAuth.getInstance();

        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();

        //check if bluetooth is available or not
        if(mBlueAdapter == null)
        {
            mStatusTv.setText("Bluetooth is not available");
        }
        else
        {
            mStatusTv.setText("Bluetooth is available");
            Set<BluetoothDevice> devices= mBlueAdapter.getBondedDevices();

            list1.add("---Select Device:---");
            for(BluetoothDevice device: devices)
            {
                list1.add(device.getName()+"\n"+device.getAddress());
            }
            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list1);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
        }

        //set image according to bluetooth status(on/off)
        if(mBlueAdapter.isEnabled())
        {
            StatusImg.setImageResource(R.drawable.ic_action_on);
//            Intent intent = new Intent(Dashboard.this,Controller.class);
//            startActivity(intent);
        }
        else{
            StatusImg.setImageResource(R.drawable.ic_action_off);
        }

        if(!mBlueAdapter.isEnabled())
        {
            StatusImg.setImageResource(R.drawable.ic_action_off);
        }


        logoutimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fauth.signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });

        //on btn click
        turn_on_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mBlueAdapter.isEnabled())
                {
                    showToast("Turning On Bluetooth...");
                    //Intent to on bluetooth
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent,REQUEST_ENABLE_BT);
                }
                else
                {
                    showToast("Bluetooth is Already On!!");
                }
            }
        });

        //discover bluetooth btn
        discoverable_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mBlueAdapter.isDiscovering())
                {
                    showToast("Making Your Device Discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent,REQUEST_DISCOVER_BT);
                }
            }
        });

        //off btn click
        turn_off_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBlueAdapter.isEnabled())
                {
                    mBlueAdapter.disable();
                    showToast("Turning Bluetooth Off...");
                    StatusImg.setImageResource(R.drawable.ic_action_off);
                }
                else
                {
                    showToast("Bluetooth is already off!!");
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String k=spinner.getSelectedItem().toString();
                String macID=k.substring(k.length()-17,k.length());
                Intent intent = new Intent(Dashboard.this,SelectType.class);
                intent.putExtra("macID",macID);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode)
        {
            case REQUEST_ENABLE_BT:
                if(resultCode ==RESULT_OK)
                {
                    //bluetooth is on
                    StatusImg.setImageResource(R.drawable.ic_action_on);
                    showToast("Bluetooth is On!!");

                    Set<BluetoothDevice> devices= mBlueAdapter.getBondedDevices();

                    for(BluetoothDevice device: devices)
                    {
                        list1.add(device.getName()+"\n"+device.getAddress());
                    }
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list1);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(arrayAdapter);
                }
                else
                {
                    //user denied to turn bluetooth on
                    showToast("Couldn't Turn On Bluetooth!!");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Toast message function
    private void showToast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}