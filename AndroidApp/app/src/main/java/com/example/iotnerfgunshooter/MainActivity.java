package com.example.iotnerfgunshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.String;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Socket socket;

    private static final int SERVERPORT = 5000;
    private static final String SERVER_IP = "RPi IP Address";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void shootGun(View view){
        String str = "shoot55";
        try {
            Thread th = new Thread(new ClientThread(str));
            th.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopServer(View view){
        String str = "stop";
        try {
            Thread th = new Thread(new ClientThread(str));
            th.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ClientThread implements Runnable {
        private String input;
        public ClientThread(String in){
            input = in;
        }

        @Override
        public void run() {

            try {
                InetAddress serverAddress = InetAddress.getByName(SERVER_IP);
                Log.d("tag", "trying to connect to socket server");
                socket = new Socket(serverAddress, SERVERPORT);
                DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
                byte[] b = input.getBytes();
                dOut.write(b);
                dOut.flush(); // Send off the data
                socket.close();

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
                Log.d("tag", "unknown host");
            } catch (IOException e1) {
                e1.printStackTrace();
                Log.d("tag", "io");
            }

        }

    }
}

