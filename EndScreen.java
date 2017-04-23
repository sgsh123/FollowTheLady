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

        String winorlose = getIntent().getStringExtra("status");
        TextView message = (TextView) findViewById(R.id.message);

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

    public void menu_return(View v)
    {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}
