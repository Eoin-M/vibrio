package com.eoinmaguire.vibrio;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsoluteLayout;
import android.widget.TextView;

import java.sql.Array;

/**
 * Created by Eoin on 01/05/2016.
 */
public class Colony {
    private int x, y, pop, oldPop, owner; //0: Neutral, 1: Friendly, <0: Enemy
    private DisplayMetrics  metrics;
    private TextView t;
    private int loop = 0;
    private int max_pop = 70;
    private int base_size = 100;
    private Point WinSize;
    private float max_size;
    Context context;

    public Colony(Context context, AbsoluteLayout parent, int x, int y, Point size, int pop, int owner) {
        this.context = context;
        this.x = x;
        this.y = y;
        this.WinSize = size;
        this.pop = this.oldPop = pop;
        this.owner = owner;
        max_size = size.x/4.0f;

        t = (TextView) View.inflate(context, R.layout.colony, null);
        parent.addView(t);
        updateColony();

        colorChange();
    }

    public void grow(){
        if(pop >= max_pop) return;
        loop += max_pop - pop;
        if(loop >= max_pop * (owner == 0 ? 6 : 3)) {
            loop = 0;
            pop++;
            updateColony();
        }
    }

    private void updateColony(){
        t.setText(Integer.toString(pop));
        //t.setWidth((int) (diameter()));
        //t.setHeight((int) (diameter()));
        ViewGroup.LayoutParams params = t.getLayoutParams();
        //params.height = (int) diameter();
        //params.width = (int) diameter();
        t.setLayoutParams(params);
        t.setX(getX() - diameter(pop)/2);
        t.setY(getY() - diameter(pop)/2);

        Animation a = new Animation(){
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation T) {

                t.getLayoutParams().height = (int) (diameter(oldPop) + ((diameter(pop) - diameter(pop)) * interpolatedTime));
                t.getLayoutParams().width = (int) (diameter(oldPop) + ((diameter(pop) - diameter(pop)) * interpolatedTime));
                t.requestLayout();
            }
        };

        // 1dp/ms
        a.setDuration(1000);
        t.startAnimation(a);

        oldPop = pop;
    }

    private void colorChange(){
        switch(owner){
            case 0: { t.setBackgroundResource(R.drawable.neutral); break; }
            case 1: { t.setBackgroundResource(R.drawable.friendly); break; }
            case -1: {
                t.setBackgroundResource(R.drawable.enemy); break; }
        }
    }

    private float diameter(int pop){
        return (base_size + ((float)pop / 100.0f) * max_size);
    }

    private float getX(){
        return (WinSize.x * (x/100.0f));
    }

    private float getY(){
        return (WinSize.y * (y/100.0f));
    }

    public boolean onTouch(float rawX, float rawY) {
        return (Math.sqrt((rawX - getX()) * (rawX - getX()) + (rawY - getY()) * (rawY - getY())) < ((diameter(pop)/2 + 120)));
    }

    public Swarm createSwarm() {
        Swarm s = new Swarm(pop/2, owner);
        pop = (int) Math.ceil(pop/2);
        return s;
    }

    public void resolveSwarm(Swarm swarm) {
        if(owner == swarm.getOwner()){
            pop += swarm.getPop();
            if(pop >= 100) pop = 100;
            loop = max_pop * 5;
        }
        else {
            pop -= swarm.getPop();
            if(pop < 0){
                pop = Math.abs(pop);
                owner = swarm.getOwner();
                colorChange();
                updateColony();
            }
            else if(pop == 0){
                owner = 0;
                colorChange();
                updateColony();
            }
        }
    }

    public int getOwner() {
        return owner;
    }

    public int getPop() {
        return pop;
    }
}
