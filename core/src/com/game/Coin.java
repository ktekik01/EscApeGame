package com.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;

public class Coin extends GameObject {
    public static final int AMOUNT = 1;

    public Coin (Texture text, World world, int x, int y) {
        super(text, world);
        this.setPosition(x,y);
        this.createCircularBody(this.getX() + this.getWidth()/2, this.getY() + this.getHeight()/2,
                getHeight()/2, GameScreen.PPM, BodyDef.BodyType.KinematicBody, true, GameScreen.COIN, "Coin");
    }

    public void drawCoin(SpriteBatch batch) {
        batch.draw(this,this.getX() - this.getWidth()/2, this.getY() - this.getHeight()/2);
    }

    /*
    public void moveToTheEnd (float x) {

        this.getBody().setTransform(x/ GameScreen.PPM, getY()/ GameScreen.PPM, 0);
        setPosition(getBody().getPosition().x * GameScreen.PPM, getBody().getPosition().y * GameScreen.PPM);
    }

    public void collectCoin() {
        changeFilter();
        getImage().dispose();
    }
     */

    public void changeFilter() {
        Filter f = new Filter ();
        f.categoryBits = GameScreen.DESTROYED;
        getFix().setFilterData(f);
    }
}
