package com.example.abc.followthelady;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Following extends AppCompatActivity {

    //declare global variables to store the value of each card on the basis of the picture on it
    int card1 = 13;
    int card2 = 12;
    int card3 = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        int noOfTurns = getIntent().getIntExtra("turns", 0);
        
        for(int i = 0; i < noOfTurns; i++)
        {
            int path = 0; //generate 0,1 or 2 for the path and call the animation with their R.ids
        }
    }

    public void animation(int switch1, int switch2)
    {

    }

    public void display(View v)
    {
        findViewById(R.id.card1).setVisibility(View.GONE);
        findViewById(R.id.card2).setVisibility(View.GONE);
        findViewById(R.id.card3).setVisibility(View.GONE);

        //use the values on the cards to declare their positions

        findViewById(R.id.king).setVisibility(View.VISIBLE);
        findViewById(R.id.queen).setVisibility(View.VISIBLE);
        findViewById(R.id.jack).setVisibility(View.VISIBLE);

        //use id of clicked button and check if correct or wrong and call win/lose activity

        int correct = 0; //id of the card which has queen
        int clicked = v.getId();

        Intent i = new Intent(getApplicationContext(), EndScreen.class);

        if(correct == clicked)
        {
            i.putExtra("status", "won");
        }
        else
        {
            i.putExtra("status", "lost");
        }

        startActivity(i);
    }

    //http://stackoverflow.com/questions/33088728/moving-an-image-with-button-android-studio

    /*
    Button king = (Button) findViewById(R.id.king);
    king.setVisibility(1);
    when play is clicked show stop button and hide play button
        playButton.setVisibility(View.GONE);
          stopButton.setVisibility(View.VISIBLE);

     */
}
