package com.game;

import java.util.Timer;
import java.util.TimerTask;

public class Shield extends PowerUps {

    private final int BEGINNING_IMPACT_DURATION = 5;
    private final int AMOUNT_OF_INCREMENT = 2;
    private boolean isActive;
    private int numOfShieldsInGame = 3;


    public Shield() {
        setLevel1ImpactDuration(BEGINNING_IMPACT_DURATION);
        setAmountOfIncrementDuration(AMOUNT_OF_INCREMENT);
    }

    private class Deactivator extends TimerTask {
        public void run(){
            isActive = false;
        }
    }

    public void activate() {
        if(numOfShieldsInGame > 0 && !isActive ) {
            isActive = true;
            Timer qwe = new Timer();
            Deactivator asd = new Deactivator();
            qwe.schedule(asd, 5000);
            numOfShieldsInGame--;
        }
    }

    public boolean isActive(){
        return isActive;
    }
}
