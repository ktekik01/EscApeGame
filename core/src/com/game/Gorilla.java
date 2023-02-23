package com.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Gorilla extends GameObject {

    public Animation animatedCharacter;

    public Gorilla(Texture text, World world , int x, int y) {
        super(text, world);
        this.setPosition(x,y);
        this.createBoxBody(this.getX() + this.getWidth()/2, this.getY() + this.getHeight(),getWidth()/2,
                getHeight()/2, GameScreen.PPM, BodyDef.BodyType.DynamicBody, GameScreen.CHARACTER, "Gorilla");
        this.animatedCharacter = new Animation();
    }

    public void drawGorilla(SpriteBatch batch) {
        batch.draw(this, getX() - getWidth()/2, getY() - getHeight()/2);
    }

    public void createBoxBody(float bodyDefX, float bodyDefY, float halfWidth, float halfHeight, int ppm, BodyDef.BodyType type, short category, String userData) {
        super.createBoxBody(bodyDefX, bodyDefY, halfWidth, halfHeight, ppm, type, category, userData);
        this.getFixDef().filter.maskBits = GameScreen.GROUND | GameScreen.COIN | GameScreen.COLUMN | GameScreen.LOW_CEILING;
    }

    public void animate() {
        super.setTexture(animatedCharacter.getGorillaFrame());
        this.setRegionWidth(animatedCharacter.getGorillaFrame().getWidth());
        this.setRegionHeight(animatedCharacter.getGorillaFrame().getHeight());
    }

    public void relocateGorilla(Ground ground) {
        setPosition(ground.getWidth() + ground.getX(),
                ground.getY() + ground.getHeight() + this.getHeight() / 2);
        getBody().setTransform(this.getX() / GameScreen.PPM, this.getY() / GameScreen.PPM, 0);
    }

    public void imitate(Character ch) {
        if (!Profile.getWing().isActive() && !Profile.getShield().isActive()) {
            update(new Vector2( 0.75f * ch.getMovementVector().x, ch.getMovementVector().y), GameScreen.PPM);
        }
        else {
            update(new Vector2(0.75f * ch.getMovementVector().x, 0), GameScreen.PPM);
            setPosition(this.getWidth(), this.getY());
            getBody().setTransform(this.getX() / GameScreen.PPM, this.getY() / GameScreen.PPM, 0);
        }
        if (this.getX() < -this.getWidth()) {
            setPosition(this.getX() + this.getWidth(), this.getY());
            getBody().setTransform(this.getX() / GameScreen.PPM, this.getY() / GameScreen.PPM, 0);
        }
    }
}
