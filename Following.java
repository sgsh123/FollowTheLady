package com.example.abc.followthelady;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Following extends AppCompatActivity {

    //global variables that store the ids of the cards in the respective positions on screen
    int first = R.id.card1;
    int second = R.id.card2;
    int third = R.id.card3;

    AnimatorSet switches = new AnimatorSet();
    List<ObjectAnimator> switch_y1 = new ArrayList<>();
    List<ObjectAnimator> switch_y2 = new ArrayList<>();

    float ys[] = new float[3];

    //method that gets called every time the activity is rendered
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        //statement to save the variable passed from the main activity
        final int noOfTurns = getIntent().getIntExtra("turns", 0);

        //display to the user the original positions of the cards
        findViewById(R.id.card1).setBackgroundResource(R.drawable.jack);
        findViewById(R.id.card2).setBackgroundResource(R.drawable.queen);
        findViewById(R.id.card3).setBackgroundResource(R.drawable.king);

        final View view = findViewById(R.id.check_layout);

        view.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        //Layout has been rendered here
                        ys[0] = findViewById(first).getTop();
                        ys[1] = findViewById(second).getTop();
                        ys[2] = findViewById(third).getTop();
                    }
                });

        //prevent the user from triggering display before and while the cards start moving
        findViewById(R.id.card1).setClickable(false);
        findViewById(R.id.card2).setClickable(false);
        findViewById(R.id.card3).setClickable(false);

        //runs the code after giving the user 3 seconds to memorize the cards
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //upturn the cards back to identical backgrounds
                findViewById(R.id.card1).setBackgroundResource(R.drawable.back_black);
                findViewById(R.id.card2).setBackgroundResource(R.drawable.back_black);
                findViewById(R.id.card3).setBackgroundResource(R.drawable.back_black);

                //run a loop as many times as the difficulty of the level demands
                for(int i = 0; i < noOfTurns; i++)
                {
                    //generate 0,1 or 2 for the path and call the animation with their R.ids
                    Random r = new Random(); //need an instance to call the nextInt method in a non-static context
                    int path = r.nextInt(2);
                    animation(path, i, noOfTurns);
                }

                switch_y1.get(noOfTurns-1).addListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        //allows the user to choose once the turning is complete
                        findViewById(R.id.card1).setClickable(true);
                        findViewById(R.id.card2).setClickable(true);
                        findViewById(R.id.card3).setClickable(true);
                    }
                });

            }
        }, 3000);


    }

    //method to run the animation
    public void animation(int path, int i, int noOfTurns)
    {
        int temp = first;

        switch(path)
        {
            case 0:
                switch_y1.add(i, ObjectAnimator.ofFloat(findViewById(first), "y", ys[2]));
                switch_y2.add(i, ObjectAnimator.ofFloat(findViewById(third), "y", ys[0]));
                first = third;
                third = temp;
                break;
            case 1:
                switch_y1.add(i, ObjectAnimator.ofFloat(findViewById(second), "y", ys[2]));
                switch_y2.add(i, ObjectAnimator.ofFloat(findViewById(third), "y", ys[1]));
                temp = second;
                second = third;
                third = temp;
                break;
            case 2:
                switch_y1.add(i, ObjectAnimator.ofFloat(findViewById(first), "y", ys[1]));
                switch_y2.add(i, ObjectAnimator.ofFloat(findViewById(second), "y", ys[0]));
                temp = first;
                first = second;
                second = temp;
                break;
        }

        switch_y1.get(i).setDuration(1000);
        switch_y2.get(i).setDuration(1000);

        switches.play(switch_y1.get(i)).with(switch_y2.get(i));

        if(i > 0)
        {
            switches.play(switch_y1.get(i)).after(switch_y1.get(i-1));
        }

        if(i == noOfTurns-1)
        {
            switches.start();
        }
    }

    //method that is executed when one of the cards is selected
    public void display(View v)
    {
        //use id of clicked button and check if correct or wrong and call win/lose activity
        int correct = R.id.card2; //id of the queen card
        int clicked = v.getId(); //id of the button that called the method

        //intent object to call the End Screen with the results
        final Intent i = new Intent(getApplicationContext(), EndScreen.class);

        if(correct == clicked)
        {
            i.putExtra("status", "won");
        }
        else
        {
            i.putExtra("status", "lost");
        }

        //prevent the user from changing their click
        findViewById(R.id.card1).setClickable(false);
        findViewById(R.id.card2).setClickable(false);
        findViewById(R.id.card3).setClickable(false);

        //display to the user the values of the cards
        findViewById(R.id.card1).setBackgroundResource(R.drawable.jack);
        findViewById(R.id.card2).setBackgroundResource(R.drawable.queen);
        findViewById(R.id.card3).setBackgroundResource(R.drawable.king);

        //change to the End Screen after giving the user a second to see the resultant positions
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
            }
        }, 1000);
    }
}