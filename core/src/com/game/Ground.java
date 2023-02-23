package com.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Ground extends GameObject {
    public static final int HEIGHT_DIFFERENCE = 20;
    public static final int WIDTH_DIFFERENCE = 20;

    public Ground (Texture text, World world, int x, int y) {
        super(text, world);
        this.setPosition(x,y);
        createBoxBody(this.getX() + this.getWidth()/2,this.getY() + this.getHeight()/2, getWidth()/2, getHeight()/2,
                GameScreen.PPM, BodyDef.BodyType.KinematicBody, GameScreen.GROUND, "Ground");
    }

    public void moveGround() {
        update(new Vector2(-3,0), GameScreen.PPM);
    }
}
