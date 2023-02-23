package com.game;

public abstract class PowerUps {

    public final int UNLOCK_POWER_UP = 5000;
    public final int MAX_LEVEL = 5;
    private int impactDuration;
    private boolean isLocked;
    private int level;
    private int amountOfIncrementDuration;
    private int level1ImpactDuration;

    public PowerUps() {
        level = 0;
        impactDuration = 0;
        isLocked = true;
    }

    public void upgrade() {
        //Second Version
        if (level < MAX_LEVEL) {
            int thresholdToUpgrade = (int) Math.pow(2, getLevel()) * UNLOCK_POWER_UP;
            if (Profile.getCoinCount() > thresholdToUpgrade) {
                if (level == 0) {
                    isLocked = false;
                    impactDuration = level1ImpactDuration;
                }
                level++;
                impactDuration += amountOfIncrementDuration;
                Profile.setCoinCount(Profile.getCoinCount() - thresholdToUpgrade);
            }
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAmountOfIncrementDuration() {
        return amountOfIncrementDuration;
    }

    public void setAmountOfIncrementDuration(int amountOfIncrementDuration) {
        this.amountOfIncrementDuration = amountOfIncrementDuration;
    }

    public int getLevel1ImpactDuration() {
        return level1ImpactDuration;
    }

    public void setLevel1ImpactDuration(int level1ImpactDuration) {
        this.level1ImpactDuration = level1ImpactDuration;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean getIsLocked() {
        return isLocked;
    }
}
