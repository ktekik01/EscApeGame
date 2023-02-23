package com.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private int frameCount;
    private int frameCountG;
    private final Array<Texture> listOfFrames;
    private final Array<Texture> listOfFrames_shield;
    private final Array<Texture> listOfFrames_winged;
    private final Array<Texture> listOfFrames_gorilla;
    private Texture slidingFrame;
    public int frameTimePeriod;

    public Animation() {
        this.frameCount = 0;
        this.frameCountG = 0;
        this.listOfFrames = new Array<>();
        this.listOfFrames_shield = new Array<>();
        this.listOfFrames_winged = new Array<>();
        this.listOfFrames_gorilla = new Array<>();
        for (int i = 0; i < 5; i++) {
            listOfFrames_gorilla.add(new Texture("Gifs/Gorilla/" + i + ".gif"));
        }
        for (int j = 0; j < 8; j++) {
            listOfFrames.add(new Texture("Gifs/" + j + ".gif"));
        }
        for (int i = 0; i < 8; i++) {
            listOfFrames_shield.add(new Texture("Gifs/Shielded/" + i + ".png"));
        }
        for (int k = 0; k < 8; k++) {
            listOfFrames_winged.add(new Texture("Gifs/Winged/" + k + ".gif"));
        }
        this.frameTimePeriod = 1;
        this.slidingFrame = new Texture("Gifs/img_1.png");
    }

    public Texture getFrame() {
        if (frameCount >= 8) frameCount = 0;
        frameCount += 1;
        if (Profile.getShield().isActive())
            return listOfFrames_shield.get(frameCount - 1);
        if (Profile.getWing().isActive())
            return listOfFrames_winged.get(frameCount - 1);
        return listOfFrames.get(frameCount - 1);
    }

    public Texture getGorillaFrame () {
        if (frameCountG >= 5) frameCountG = 0;
        frameCountG += 1;
        return listOfFrames_gorilla.get(frameCountG - 1);
    }

    public Texture getSlidingFrame() {
        return slidingFrame;
    }
}
