package com.example.rovercontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import static android.widget.Toast.LENGTH_SHORT;

public class Controller extends AppCompatActivity {


    Button upBtn,downBtn,leftBtn,rightBtn;
    Switch aSwitch;

    String address;

    //String address = "FC:A8:9B:00:5C:1C";
    //Yash Mac Address: 00:20:08:00:3C:BF
    //Akarsh Mac Address: FC:A8:9B:00:5C:1C

    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        Bundle bundle = getIntent().getExtras();
        String address1 = bundle.getString("macID");

        address=address1;

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        upBtn =(Button)findViewById(R.id.upBtn);
        downBtn =(Button) findViewById(R.id.downBtn);
        leftBtn =(Button) findViewById(R.id.leftBtn);
        rightBtn =(Button) findViewById(R.id.rightBtn);
        //hornBtn =(Button) findViewById(R.id.btn_horn);
        aSwitch= findViewById(R.id.switchBtn);

        new ConnectBT().execute();

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked == true)
                {
                    if(btSocket!=null)
                    {
                        try {
                            btSocket.getOutputStream().write("A".toString().getBytes());
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }
                }
                else
                {
                    if(btSocket!=null)
                    {
                        try {
                            btSocket.getOutputStream().write("B".toString().getBytes());
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }
                }

            }
        });

//        hornBtn.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                if(event.getAction()==MotionEvent.ACTION_DOWN)
//                {
//                    if (btSocket!=null)
//                    {
//                        try
//                        {
//                            btSocket.getOutputStream().write("H".toString().getBytes());
//                            //Toast.makeText(getApplicationContext(),"Up Pressed!!",Toast.LENGTH_SHORT).show();
//                        }
//                        catch (IOException e)
//                        {
//                            msg("Error");
//                        }
//                    }
//                }
//                else if(event.getAction()==MotionEvent.ACTION_UP)
//                {
//                    if (btSocket!=null)
//                    {
//                        try
//                        {
//                            btSocket.getOutputStream().write("N".toString().getBytes());
//                            //Toast.makeText(getApplicationContext(),"Up Pressed!!",Toast.LENGTH_SHORT).show();
//                        }
//                        catch (IOException e)
//                        {
//                            msg("Error");
//                        }
//                    }
//                }
//                return true;
//            }
//        });

        upBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("U".toString().getBytes());
                            //Toast.makeText(getApplicationContext(),"Up Pressed!!",Toast.LENGTH_SHORT).show();
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("N".toString().getBytes());
                            //Toast.makeText(getApplicationContext(),"Up Pressed!!",Toast.LENGTH_SHORT).show();
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }
                }

                return true;
            }
        });

//        upBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (btSocket!=null)
//                {
//                    try
//                    {
//                        btSocket.getOutputStream().write("U".toString().getBytes());
//                        //Toast.makeText(getApplicationContext(),"Up Pressed!!",Toast.LENGTH_SHORT).show();
//                    }
//                    catch (IOException e)
//                    {
//                        msg("Error");
//                    }
//                }
//            }
//        });

        downBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("D".toString().getBytes());
                            //Toast.makeText(getApplicationContext(),"Up Pressed!!",Toast.LENGTH_SHORT).show();
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("N".toString().getBytes());
                            //Toast.makeText(getApplicationContext(),"Up Pressed!!",Toast.LENGTH_SHORT).show();
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }
                }

                return true;
            }
        });

//        downBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (btSocket!=null)
//                {
//                    try
//                    {
//                        btSocket.getOutputStream().write("D".toString().getBytes());
//                        //Toast.makeText(getApplicationContext(),"Down Pressed!!",Toast.LENGTH_SHORT).show();
//                    }
//                    catch (IOException e)
//                    {
//                        msg("Error");
//                    }
//                }
//            }
//        });

        leftBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("L".toString().getBytes());
                            //Toast.makeText(getApplicationContext(),"Up Pressed!!",Toast.LENGTH_SHORT).show();
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("N".toString().getBytes());
                            //Toast.makeText(getApplicationContext(),"Up Pressed!!",Toast.LENGTH_SHORT).show();
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }
                }

                return true;
            }
        });

//        leftBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (btSocket!=null)
//                {
//                    try
//                    {
//                        btSocket.getOutputStream().write("L".toString().getBytes());
//                        //Toast.makeText(getApplicationContext(),"Left Pressed!!",Toast.LENGTH_SHORT).show();
//                    }
//                    catch (IOException e)
//                    {
//                        msg("Error");
//                    }
//                }
//            }
//        });

        rightBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("R".toString().getBytes());
                            //Toast.makeText(getApplicationContext(),"Up Pressed!!",Toast.LENGTH_SHORT).show();
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("N".toString().getBytes());
                            //Toast.makeText(getApplicationContext(),"Up Pressed!!",Toast.LENGTH_SHORT).show();
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }
                }

                return true;
            }
        });

//        rightBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (btSocket!=null)
//                {
//                    try
//                    {
//                        btSocket.getOutputStream().write("R".toString().getBytes());
//                        //Toast.makeText(getApplicationContext(),"Right Pressed!!",Toast.LENGTH_SHORT).show();
//                    }
//                    catch (IOException e)
//                    {
//                        msg("Error");
//                    }
//                }
//            }
//        });



    }

    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.menu_led_control, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }



    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(Controller.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a HC-04 Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected!!!");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}