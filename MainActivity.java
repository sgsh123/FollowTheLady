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

    public void level1(View v) {
        Intent i = new Intent(getApplicationContext(), Following.class);
        i.putExtra("turns", 5);
        startActivity(i);
    }

        public void level2(View v)
        {
            Intent i = new Intent(getApplicationContext(),Following.class);
            i.putExtra("turns",10);
            startActivity(i);
        }

        public void level3(View v)
        {
            Intent i = new Intent(getApplicationContext(),Following.class);
            i.putExtra("turns",18);
            startActivity(i);
        }

        public void level4(View v)
        {
            Intent i = new Intent(getApplicationContext(),Following.class);
            i.putExtra("turns",25);
            startActivity(i);
        }

        public void level5(View v)
        {
            Intent i = new Intent(getApplicationContext(),Following.class);
            i.putExtra("turns",30);
            startActivity(i);
        }

        public void level6(View v)
        {
            Intent i = new Intent(getApplicationContext(),Following.class);
            i.putExtra("turns",35);
            startActivity(i);
        }

        public void level7(View v)
        {
            Intent i = new Intent(getApplicationContext(),Following.class);
            i.putExtra("turns",40);
            startActivity(i);
        }

        public void level8(View v)
        {
            Intent i = new Intent(getApplicationContext(),Following.class);
            i.putExtra("turns",45);
            startActivity(i);
        }

        public void level9(View v)
        {
            Intent i = new Intent(getApplicationContext(),Following.class);
            i.putExtra("turns",50);
            startActivity(i);
        }

    }
