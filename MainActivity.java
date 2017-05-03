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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creates instances of the shared preferences file of the app and its editor
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //declares the value for settings based on if an option has previously selected
        String setting = sharedPreferences.getString("settings", "Easy");
        editor.putString("settings", setting);
        editor.commit();
    }

        //Method to call the game and pass the appropriate number of turns on the basis of the level chosen
        public void level(View v)
        {
            Intent i = new Intent(getApplicationContext(), Following.class);
            switch(v.getId())
            {
                case R.id.lvl1:
                    i.putExtra("turns", 5);
                    break;
                case R.id.lvl2:
                    i.putExtra("turns", 10);
                    break;
                case R.id.lvl3:
                    i.putExtra("turns", 15);
                    break;
                case R.id.lvl4:
                    i.putExtra("turns", 18);
                    break;
                case R.id.lvl5:
                    i.putExtra("turns", 23);
                    break;
                case R.id.lvl6:
                    i.putExtra("turns", 27);
                    break;
                case R.id.lvl7:
                    i.putExtra("turns", 30);
                    break;
                case R.id.lvl8:
                    i.putExtra("turns", 33);
                    break;
                case R.id.lvl9:
                    i.putExtra("turns", 35);
                    break;
            }

            startActivity(i);
        }

        public void goToAbout(View v)
        {

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