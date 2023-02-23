package com.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;


public class Column extends Obstacle {

    public Column (Texture text, World world, int x, int y) {
        super(text, world);
        this.setPosition(x,y);
        this.createBoxBody(this.getX() + this.getWidth()/2, this.getY() + this.getHeight()/2,
                this.getWidth()/2,getHeight()/2, GameScreen.PPM, BodyDef.BodyType.KinematicBody,
                GameScreen.COLUMN, "Column");
    }
}
