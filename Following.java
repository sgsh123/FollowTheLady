package com.example.abc.followthelady;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import java.util.Random;

public class Following extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        int noOfTurns = getIntent().getIntExtra("turns", 0);

        //prevents the user from triggering display before and during the cards moving
        findViewById(R.id.card1).setClickable(false);
        findViewById(R.id.card2).setClickable(false);
        findViewById(R.id.card3).setClickable(false);


        findViewById(R.id.card1).setBackgroundResource(R.drawable.jack);
        findViewById(R.id.card2).setBackgroundResource(R.drawable.queen);
        findViewById(R.id.card3).setBackgroundResource(R.drawable.king);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.card1).setBackgroundResource(R.drawable.back_black);
                findViewById(R.id.card2).setBackgroundResource(R.drawable.back_black);
                findViewById(R.id.card3).setBackgroundResource(R.drawable.back_black);
            }
        }, 5000);

        for(int i = 0; i < noOfTurns; i++)
        {
            Random r = new Random();

            //generate 0,1 or 2 for the path and call the animation with their R.ids
            int path = r.nextInt(2);
            animation(path);
        }

        //allows the user to choose once the turning is complete
        findViewById(R.id.card1).setClickable(true);
        findViewById(R.id.card2).setClickable(true);
        findViewById(R.id.card3).setClickable(true);
    }

    public void animation(int path)
    {
        ValueAnimator va_y1 = ValueAnimator.ofInt(25, 335);
        ValueAnimator va_y2 = ValueAnimator.ofInt(335, 25);
        int a = R.id.card1;
        int b = R.id.card3;

        switch(path)
        {
            case 1:
                va_y1 = ValueAnimator.ofInt(193, 335);
                va_y2 = ValueAnimator.ofInt(335, 193);
                a = R.id.card2;
                b = R.id.card3;
                break;
            case 2:
                va_y1 = ValueAnimator.ofInt(25, 193);
                va_y2 = ValueAnimator.ofInt(193, 25);
                a = R.id.card1;
                b = R.id.card2;
                break;

        }

        va_y1.setDuration(3000);
        va_y2.setDuration(3000);

        final ImageButton switch1 = (ImageButton) findViewById(a);
        final ImageButton switch2 = (ImageButton) findViewById(b);

        va_y1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                switch1.setTranslationY((int)animation.getAnimatedValue());
            }
        });

        va_y2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                switch2.setTranslationY((int)animation.getAnimatedValue());
            }
        });

        va_y1.start();
        va_y2.start();

        //create three paths that move the cards and call one of them based on the parameter passed
    }

    public void display(View v)
    {

        //use id of clicked button and check if correct or wrong and call win/lose activity
        int correct = R.id.card2; //id of the card which has queen
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
}
