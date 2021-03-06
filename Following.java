package com.example.abc.followthelady;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Following extends AppCompatActivity {

    //global variables that store the ids of the cards in the respective positions on screen
    int first = R.id.card1;
    int second = R.id.card2;
    int third = R.id.card3;


    AnimatorSet switches = new AnimatorSet();

    //global variables to store multiple animations, going both ways (from 1 to 2 and 2 to 1)
    //for both portrait and landscape
    List<ObjectAnimator> switch_y1 = new ArrayList<>();
    List<ObjectAnimator> switch_y2 = new ArrayList<>();
    List<ObjectAnimator> switch_x1 = new ArrayList<>();
    List<ObjectAnimator> switch_x2 = new ArrayList<>();

    //global variables to store the positions of the cards on the screen
    float ys[] = new float[3];
    float xs[] = new float[3];

    //integer value to store the number of milliseconds the animation will take based on level
    int ani_speed = 1500;

    //global variable to store the value of the level that is being unlocked
    int to_unlock = 2;

    //method that gets called every time the activity is rendered
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        //statements to save the variables passed from the main activity
        final int noOfTurns = getIntent().getIntExtra("turns", 0);
        to_unlock = getIntent().getIntExtra("level", 1) + 1;

        //retrieve difficulty settings from the shared preferences file
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String setting = sharedPreferences.getString("settings", "Easy");

        //assign values to the duration of the animation on the basis of difficulty settings
        if(setting.equals("Easy"))
        {
            ani_speed = 1500;
        }
        else if(setting.equals("Medium"))
        {
            ani_speed = 1000;
        }
        else if(setting.equals("Hard"))
        {
            ani_speed = 500;
        }

        //display to the user the original positions of the cards
        findViewById(R.id.card1).setBackgroundResource(R.drawable.jack);
        findViewById(R.id.card2).setBackgroundResource(R.drawable.queen);
        findViewById(R.id.card3).setBackgroundResource(R.drawable.king);

        //use an empty object to activate Global Layout Listener
        final View view = findViewById(R.id.check_layout);

        //Global Layout Listener to check if the layout has been rendered
        view.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        //Layout has been rendered here
                        ys[0] = findViewById(first).getTop();
                        ys[1] = findViewById(second).getTop();
                        ys[2] = findViewById(third).getTop();

                        xs[0] = findViewById(first).getLeft();
                        xs[1] = findViewById(second).getLeft();
                        xs[2] = findViewById(third).getLeft();

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

                    //Obtain Screen orientation and send commands according to landscape or portrait
                    Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
                    int orientation = display.getRotation();

                    //if landscape
                    if (orientation == Surface.ROTATION_90 || orientation == Surface.ROTATION_270)
                    {
                        //run a loop as many times as the difficulty of the level demands
                        for(int i = 0; i < noOfTurns; i++) {
                            //generate 0,1 or 2 for the path and call the animation with their R.ids
                            Random r = new Random(); //need an instance to call the nextInt method in a non-static context
                            int path = r.nextInt(2);

                            animation_x(path, i, noOfTurns);
                        }

                        //allow clicking only once the last animation is executed
                        switch_x1.get(noOfTurns-1).addListener(new AnimatorListenerAdapter()
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
                    //if portrait
                   else
                    {
                        //run a loop as many times as the difficulty of the level demands
                        for(int i = 0; i < noOfTurns; i++) {
                            //generate 0,1 or 2 for the path and call the animation with their R.ids
                            Random r = new Random(); //need an instance to call the nextInt method in a non-static context
                            int path = r.nextInt(2);

                            animation_y(path, i, noOfTurns);

                        }
                        //allow clicking only once the last animation is executed
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
            }
        }, 3000);
    }

    //method to run the animation in portrait mode
    public void animation_y(int path, int i, int noOfTurns)
    {
        //temporary variable to facilitate the switching of ids
        int temp = first;

        //assign values to Object Animators and carry out the switches according to the path generated
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

        //set speed according to the Global variable that has a value based on difficulty settings
        switch_y1.get(i).setDuration(ani_speed);
        switch_y2.get(i).setDuration(ani_speed);

        //simultaneously execute the switches in opposite directions
        switches.play(switch_y1.get(i)).with(switch_y2.get(i));

        //add to the set switches after the previous one if there is a previous one
        if(i > 0)
        {
            switches.play(switch_y1.get(i)).after(switch_y1.get(i-1));
        }

        //start the animation set once the last animation has been added
        if(i == noOfTurns-1)
        {
            switches.start();
        }
    }


    //method to run the animation in landscape mode
    public void animation_x(int path, int i, int noOfTurns)
    {
        //temporary variable to facilitate the switching of ids
        int temp = first;

        //assign values to Object Animators and carry out the switches according to the path generated
        switch(path)
        {
            case 0:
                switch_x1.add(i, ObjectAnimator.ofFloat(findViewById(first), "x", xs[2]));
                switch_x2.add(i, ObjectAnimator.ofFloat(findViewById(third), "x", xs[0]));
                first = third;
                third = temp;
                break;
            case 1:
                switch_x1.add(i, ObjectAnimator.ofFloat(findViewById(second), "x", xs[2]));
                switch_x2.add(i, ObjectAnimator.ofFloat(findViewById(third), "x", xs[1]));
                temp = second;
                second = third;
                third = temp;
                break;
            case 2:
                switch_x1.add(i, ObjectAnimator.ofFloat(findViewById(first), "x", xs[1]));
                switch_x2.add(i, ObjectAnimator.ofFloat(findViewById(second), "x", xs[0]));
                temp = first;
                first = second;
                second = temp;
                break;
        }

        //set speed according to the Global variable that has a value based on difficulty settings
        switch_x1.get(i).setDuration(ani_speed);
        switch_x2.get(i).setDuration(ani_speed);

        //simultaneously execute the switches in opposite directions
        switches.play(switch_x1.get(i)).with(switch_x2.get(i));

        //add to the set switches after the previous one if there is a previous one
        if(i > 0)
        {
            switches.play(switch_x1.get(i)).after(switch_x1.get(i-1));
        }

        //start the animation set once the last animation has been added
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

            //unlock the next level only if the currently crossed levels are lesser than the new one being unlocked
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();


            int crossed_level = Integer.parseInt(sharedPreferences.getString("levels", "1"));


            if(to_unlock > crossed_level)
            {
                editor.putString("levels", "" + to_unlock);
                editor.apply();
            }

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