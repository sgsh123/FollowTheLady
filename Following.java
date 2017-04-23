package com.example.abc.followthelady;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.Random;

public class Following extends AppCompatActivity {

    int first = R.id.card1;
    int second = R.id.card2;
    int third = R.id.card3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        final int noOfTurns = getIntent().getIntExtra("turns", 0);

        //prevents the user from triggering display before and during the cards moving
        findViewById(R.id.card1).setClickable(false);
        findViewById(R.id.card2).setClickable(false);
        findViewById(R.id.card3).setClickable(false);

        findViewById(R.id.card1).setBackgroundResource(R.drawable.jack);
        findViewById(R.id.card2).setBackgroundResource(R.drawable.queen);
        findViewById(R.id.card3).setBackgroundResource(R.drawable.king);

        //final TextView tv = (TextView) findViewById(R.id.test);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.card1).setBackgroundResource(R.drawable.back_black);
                findViewById(R.id.card2).setBackgroundResource(R.drawable.back_black);
                findViewById(R.id.card3).setBackgroundResource(R.drawable.back_black);

                for(int i = 0; i < 5; i++)
                {
                    Random r = new Random();

                    //generate 0,1 or 2 for the path and call the animation with their R.ids
                    int path = r.nextInt(2);
                    animation(path, i);

                }



                //allows the user to choose once the turning is complete
                findViewById(R.id.card1).setClickable(true);
                findViewById(R.id.card2).setClickable(true);
                findViewById(R.id.card3).setClickable(true);
            }
        }, 3000);


    }


    public void animation(int path, int i)
    {
        ValueAnimator va_y1 = ValueAnimator.ofFloat(0f, 1210f);
        ValueAnimator va_y2 = ValueAnimator.ofFloat(0f, -1210f);
        int a = first;
        int b = third;

        int temp = first;

        switch(path)
        {
            case 0:
                first = third;
                third = temp;
                break;
            case 1:
                va_y1 = ValueAnimator.ofFloat(0f, 605f);
                va_y2 = ValueAnimator.ofFloat(0f, -605f);
                a = second;
                b = third;
                temp = second;
                second = third;
                third = temp;
                break;
            case 2:
                va_y1 = ValueAnimator.ofFloat(0f, 605f);
                va_y2 = ValueAnimator.ofFloat(0f, -605f);
                a = first;
                b = second;
                temp = first;
                first = second;
                second = temp;
                break;

        }

        va_y1.setDuration(1000);
        va_y2.setDuration(1000);

        final ImageButton switch1 = (ImageButton) findViewById(a);
        final ImageButton switch2 = (ImageButton) findViewById(b);


        va_y1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                switch1.setTranslationY((float)animation.getAnimatedValue());
            }
        });

        va_y2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                switch2.setTranslationY((float)animation.getAnimatedValue());
            }
        });

        va_y1.start();
        va_y2.start();
    //http://www.vogella.com/tutorials/AndroidAnimation/article.html
        //create three paths that move the cards and call one of them based on the parameter passed
    }



    public void display(View v)
    {

        //use id of clicked button and check if correct or wrong and call win/lose activity
        int correct = R.id.card2; //id of the card which has queen
        int clicked = v.getId();

        final Intent i = new Intent(getApplicationContext(), EndScreen.class);

        if(correct == clicked)
        {
            i.putExtra("status", "won");
        }
        else
        {
            i.putExtra("status", "lost");
        }

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
                startActivity(i);
            }
        }, 1000);

    }

    //http://stackoverflow.com/questions/33088728/moving-an-image-with-button-android-studio
}
