package com.eoinmaguire.vibrio;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class Levels extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void levelSelect(View v){
        Intent i;
        int level = Integer.parseInt((String) v.getTag());
        switch(level){
            case 1: { i = new Intent(getBaseContext(), Level1.class); break; }
            case 2: { i = new Intent(getBaseContext(), Level2.class); break; }
            case 3: { i = new Intent(getBaseContext(), Level3.class); break; }
            default: return;
        }
        startActivity(i);
    }
}
