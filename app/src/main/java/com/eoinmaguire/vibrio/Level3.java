package com.eoinmaguire.vibrio;

import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

public class Level3 extends AppCompatActivity implements View.OnTouchListener{
    private ArrayList<Colony> colonies = new ArrayList<>(10);
    private Handler handler;
    private Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        AbsoluteLayout layout = (AbsoluteLayout) findViewById(R.id.levelView);
        colonies.add(new Colony(getApplicationContext(), layout, 20, 10, size, 15, -1));
        colonies.add(new Colony(getApplicationContext(), layout, 80, 10, size, 8, -1));
        colonies.add(new Colony(getApplicationContext(), layout, 45, 30, size, 12, -1));
        colonies.add(new Colony(getApplicationContext(), layout, 20, 35, size, 20, 0));
        colonies.add(new Colony(getApplicationContext(), layout, 75, 40, size, 5, 0));
        colonies.add(new Colony(getApplicationContext(), layout, 25, 55, size, 5, 0));
        colonies.add(new Colony(getApplicationContext(), layout, 80, 60, size, 5, 0));
        colonies.add(new Colony(getApplicationContext(), layout, 45, 70, size, 15, 1));
        colonies.add(new Colony(getApplicationContext(), layout, 75, 80, size, 20, 1));
        colonies.add(new Colony(getApplicationContext(), layout, 15, 90, size, 25, 1));

        layout.setOnTouchListener(this);

        handler = new Handler();
        r = new Runnable() {
            public void run() {
                handler.postDelayed(this, 80);
                int enemies = 0;
                int friends = 0;
                Stack AISwarmpFrom = new Stack();
                int AITarget = -1;
                for(int i = 0; i < colonies.size(); i++){
                    Colony c = colonies.get(i);
                    if(c.getOwner() < 0) enemies++;
                    else if(c.getOwner() > 0) friends++;
                    c.grow();
                    if(c.getOwner() < 0 && c.getPop() > 25){
                        AISwarmpFrom.push(i);
                    }
                    else if(AITarget < 0 && c.getOwner() >= 0) AITarget = i;
                    if(c.getOwner() >= 0 && c.getPop() < 15) AITarget = i;
                }
                while(!AISwarmpFrom.isEmpty() && AITarget >= 0){
                    Swarm s = colonies.get((Integer) AISwarmpFrom.pop()).createSwarm();
                    colonies.get(AITarget).resolveSwarm(s);
                }
                if(enemies == 0) gameOver(true);
                else if(friends == 0) gameOver(false);
                else{
                    enemies = friends = 0;
                }
            }
        };

        handler.postDelayed(r, 80);
    }

    protected void gameOver(Boolean result){
        handler.removeCallbacks(r);
        RelativeLayout go = (RelativeLayout) findViewById(R.id.gameOverSplash);
        TextView got = (TextView) findViewById(R.id.gameOverText);
        String text = result ? "You Win!" : "You Lose!";
        got.setText(text);
        //if(result) findViewById(R.id.nextLevelBtn).setVisibility(View.VISIBLE);
        go.bringToFront();
        go.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(r);
        super.onPause();
    }

    @Override
    protected void onStop() {
        handler.removeCallbacks(r);
        super.onStop();
    }

    @Override
    protected void onResume()
    {
        handler.postDelayed(r, 80);
        super.onResume();
    }

    private int swarmPos = -1;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            for (int i = 0; i < colonies.size(); i++) {
                if(colonies.get(i).onTouch(event.getX(), event.getY()) && colonies.get(i).getOwner() == 1){
                    swarmPos = i;
                    return true;
                }
            }
        }

        else if(event.getAction() == MotionEvent.ACTION_UP) {
            if(swarmPos < 0) return true;
            for (int i = 0; i < colonies.size(); i++) {
                if(colonies.get(i).onTouch(event.getX(), event.getY())){
                    if(i == swarmPos) return true;

                    Swarm swarm = colonies.get(swarmPos).createSwarm();
                    swarmPos = -1;
                    colonies.get(i).resolveSwarm(swarm);
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getBaseContext(), Levels.class);
        startActivity(i);
    }
}