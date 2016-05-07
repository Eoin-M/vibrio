package com.eoinmaguire.vibrio;

/**
 * Created by Eoin on 01/05/2016.
 */
public class Swarm {
    private int pop, owner;

    public Swarm(int pop, int owner){
        this.pop = pop;
        this.owner = owner;
    }

    public int getPop() {
        return pop;
    }

    public int getOwner() {
        return owner;
    }
}