package com.example.abc.followthelady;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Methods to call the game and pass the appropriate number of turns on the basis of the level chosen

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

    }