package com.eoinmaguire.vibrio;

import android.content.Intent;
import android.graphics.Point;
import android.widget.AbsoluteLayout;

import java.util.ArrayList;

public class Level2 extends Level{

    @Override
    protected void playNextLevel() {
        Intent i = new Intent(getBaseContext(), Level2.class);
        startActivity(i);
    }

    @Override
    protected ArrayList<Colony> setUpColonies(AbsoluteLayout layout, Point size){
        ArrayList<Colony> colonies = new ArrayList<>(9);
        colonies.add(new Colony(getApplicationContext(), layout, 20, 10, size, 15, -1));
        colonies.add(new Colony(getApplicationContext(), layout, 80, 10, size, 10, -1));
        colonies.add(new Colony(getApplicationContext(), layout, 45, 30, size, 5, -1));
        colonies.add(new Colony(getApplicationContext(), layout, 20, 35, size, 10, 0));
        colonies.add(new Colony(getApplicationContext(), layout, 75, 40, size, 5, 0));
        colonies.add(new Colony(getApplicationContext(), layout, 25, 55, size, 5, 0));
        colonies.add(new Colony(getApplicationContext(), layout, 80, 60, size, 5, 0));
        colonies.add(new Colony(getApplicationContext(), layout, 45, 70, size, 30, 1));
        colonies.add(new Colony(getApplicationContext(), layout, 75, 80, size, 20, 1));
        colonies.add(new Colony(getApplicationContext(), layout, 15, 90, size, 20, 1));

        return colonies;
    }
}