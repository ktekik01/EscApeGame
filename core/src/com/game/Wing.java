package com.game;

import java.util.Timer;
import java.util.TimerTask;

public class Wing extends PowerUps {

    private final int BEGINNING_IMPACT_DURATION = 3;
    private final int AMOUNT_OF_INCREMENT = 1;
    private boolean isActive;
    private int numOfWingsInGame = 3;

    public Wing() {
        setLevel1ImpactDuration(BEGINNING_IMPACT_DURATION);
        setAmountOfIncrementDuration(AMOUNT_OF_INCREMENT);
    }

    private class Deactivator extends TimerTask {
        public void run(){
            isActive = false;
        }
    }

    public void activate() {
        if(numOfWingsInGame > 0 && !isActive) {
            isActive = true;
            Timer tyu = new Timer();
            Deactivator ghj = new Deactivator();
            tyu.schedule(ghj,3000);
            numOfWingsInGame--;
        }
    }

    public boolean isActive(){
        return isActive;
    }


    public void backToGround() {

    }
}
