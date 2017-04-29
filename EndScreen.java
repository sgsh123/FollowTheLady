package com.example.abc.followthelady;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);

        //receive information from Intent that called this activity
        String winorlose = getIntent().getStringExtra("status");

        TextView message = (TextView) findViewById(R.id.message);

        //set appropriate gifs and messages
        if(winorlose.equals("won"))
        {
            findViewById(R.id.gifset).setBackgroundResource(R.drawable.win1);
            message.setText("Okay, you won");
        }
        else
        {
            message.setText("Haha, you lost");
        }
    }

    //method to call Main Activity on the press of a button
    public void menu_return(View v)
    {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}
