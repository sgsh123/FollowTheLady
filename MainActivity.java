package com.example.abc.followthelady;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //disable all levels except the first
        findViewById(R.id.lvl9).setClickable(false);
        findViewById(R.id.lvl8).setClickable(false);
        findViewById(R.id.lvl7).setClickable(false);
        findViewById(R.id.lvl6).setClickable(false);
        findViewById(R.id.lvl5).setClickable(false);
        findViewById(R.id.lvl4).setClickable(false);
        findViewById(R.id.lvl3).setClickable(false);
        findViewById(R.id.lvl2).setClickable(false);

        //store difficult settings in the Shared Preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //declares the value for settings based on if an option has previously selected
        String setting = sharedPreferences.getString("settings", "Easy");
        editor.putString("settings", setting);
        editor.commit();

        //receives the number of levels already unlocked
        int level = Integer.parseInt(sharedPreferences.getString("levels", "1"));

        switch(level)
        {
            case 9:
                findViewById(R.id.lock9).setVisibility(View.GONE);
                findViewById(R.id.lvl9).setClickable(true);
            case 8:
                findViewById(R.id.lock8).setVisibility(View.GONE);
                findViewById(R.id.lvl8).setClickable(true);
            case 7:
                findViewById(R.id.lock7).setVisibility(View.GONE);
                findViewById(R.id.lvl7).setClickable(true);
            case 6:
                findViewById(R.id.lock6).setVisibility(View.GONE);
                findViewById(R.id.lvl6).setClickable(true);
            case 5:
                findViewById(R.id.lock5).setVisibility(View.GONE);
                findViewById(R.id.lvl5).setClickable(true);
            case 4:
                findViewById(R.id.lock4).setVisibility(View.GONE);
                findViewById(R.id.lvl4).setClickable(true);
            case 3:
                findViewById(R.id.lock3).setVisibility(View.GONE);
                findViewById(R.id.lvl3).setClickable(true);
            case 2:
                findViewById(R.id.lock2).setVisibility(View.GONE);
                findViewById(R.id.lvl2).setClickable(true);
            case 1:
                break;
        }

    }

        //Method to call the game and pass the appropriate number of turns on the basis of the level chosen
        public void level(View v)
        {
            Intent i = new Intent(getApplicationContext(), Following.class);
            switch(v.getId())
            {
                case R.id.lvl1:
                    i.putExtra("turns", 5);
                    i.putExtra("level", 1);
                    break;
                case R.id.lvl2:
                    i.putExtra("turns", 10);
                    i.putExtra("level", 2);
                    break;
                case R.id.lvl3:
                    i.putExtra("turns", 15);
                    i.putExtra("level", 3);
                    break;
                case R.id.lvl4:
                    i.putExtra("turns", 18);
                    i.putExtra("level", 4);
                    break;
                case R.id.lvl5:
                    i.putExtra("turns", 23);
                    i.putExtra("level", 5);
                    break;
                case R.id.lvl6:
                    i.putExtra("turns", 27);
                    i.putExtra("level", 6);
                    break;
                case R.id.lvl7:
                    i.putExtra("turns", 30);
                    i.putExtra("level", 7);
                    break;
                case R.id.lvl8:
                    i.putExtra("turns", 33);
                    i.putExtra("level", 8);
                    break;
                case R.id.lvl9:
                    i.putExtra("turns", 35);
                    i.putExtra("level", 9);
                    break;
            }

            startActivity(i);
        }

        //Method to display about activity
        public void goToAbout(View v)
        {
            Intent i = new Intent(getApplicationContext(), About.class);
            startActivity(i);
        }

        //Method to create Popo Up Menu and Display it
        public void showPopupMenu(View v)
        {
            //create menu and inflate it with the options from the xml resource file
            PopupMenu popup = new PopupMenu(this, v);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.settings, popup.getMenu());

            //listener for when a menu option is selected
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
            {
                //method to execute when listener detects choice
                public boolean onMenuItemClick(MenuItem item)
                {

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    //store selected option in the shared preferences file
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("settings", "" + item.getTitle());
                    editor.commit();
                    return true;
                }
            });

            popup.show();
        }

    }