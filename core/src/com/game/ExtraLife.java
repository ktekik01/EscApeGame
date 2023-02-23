package com.game;

public class ExtraLife extends PowerUps {

    public final int EXTRA_LIFE_PRICE = 3000;
    private int extraLifeCount;

    public ExtraLife() {
        extraLifeCount = 10000;
        setLocked(false);
    }

    public void purchase() {
        if(Profile.getCoinCount() > EXTRA_LIFE_PRICE) {
            extraLifeCount++;
            Profile.setCoinCount(Profile.getCoinCount() - EXTRA_LIFE_PRICE);
        }
    }

    public void activate(){
        if(extraLifeCount > 0){
            extraLifeCount--;
        }
    }

    public int getExtraLifeCount(){
        return extraLifeCount;
    }
}
