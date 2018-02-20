package com.example.sowmyaram.tcp_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
Button b,b1;
    String s;
    String outMsg;
    ToggleButton tg;
    TextView t;
    int TCP_SERVER_PORT=502;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       b= (Button) findViewById(R.id.button);
        b1= (Button) findViewById(R.id.buttonoff);
         t = (TextView) findViewById(R.id.text);
       // tg= (ToggleButton) findViewById(R.id.toggleButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t =new Thread(){
                    public void run(){
                        outMsg = "*ON#";
                        runTcpClient();
                    }
                };t.start();

            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t =new Thread(){
                    public void run(){
                        outMsg = "*OFF#";
                        runTcpClient();
                    }
                };t.start();

            }
        });



    }

    private void runTcpClient() {
        try {
            // Creating new socket connection to the IP (first parameter) and its opened port (second parameter)
           // Socket s = new Socket("192.168.0.39", TCP_SERVER_PORT);
            Socket s = new Socket("192.168.4.1", TCP_SERVER_PORT);

            // Initialize output stream and input stream to write and read message to the socket stream
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            //send output msg
          //  String outMsg = "TCP connecting to " + TCP_SERVER_PORT ; //+ System.getProperty("line.separator");

               //  outMsg = "*OFF#";
           // }
           // outMsg = "*OFF#";
            PrintWriter p=new PrintWriter(out);
            //write message to stream
            out.write(outMsg);
            //flush the data to indicate that end of message
            out.flush();
            Log.i("TcpClient", "sent: " + outMsg);
            //accept server response
            final String inMsg = in.readLine() + System.getProperty("line.separator");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    t.setText(inMsg);
                    if(inMsg.equals("o/p:-OFF")){

                    }

                }
            });

         //   t.setText(inMsg);
            Log.d("TAG", "sent: " + outMsg);
            Log.i("TcpClient", "received: " + inMsg);
            //close connection
            s.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
