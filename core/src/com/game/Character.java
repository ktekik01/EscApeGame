package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Character extends GameObject {
    public Animation animatedCharacter;
    private boolean ableToJump = true;
    boolean isRotated = false;
    private Vector2 movementVector;

    public Character(Texture text, World world , int x, int y) {
        super(text, world);
        this.setPosition(x,y);
        this.createBoxBody(this.getX() + this.getWidth()/2, this.getY() + this.getHeight(),getWidth()/2,
                getHeight()/2, GameScreen.PPM, BodyDef.BodyType.DynamicBody, GameScreen.CHARACTER, "Character");
        this.animatedCharacter = new Animation();
    }

    public void moveCharacter() {
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            if(!Profile.getWing().isActive()){
                Profile.getWing().activate();
            }
        }
        if (0 < this.getX() && this.getX() < 800 ) {
            setMovementVector(new Vector2(1.1F, 0));
        }
        else {
            setMovementVector(new Vector2(0.5f,0f));
        }
        update(getMovementVector(), GameScreen.PPM);
        //update(new Vector2(2f,0f), GameScreen.PPM);
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && ableToJump) {
            setMovementVector(new Vector2(0.5F, 70F));
            update(movementVector, GameScreen.PPM);
            setAbleToJump(Profile.getWing().isActive());
        }
        slideCharacter();
        if (getIsRotated() && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            rotateCharacter(0);
        }
    }

    public void drawCharacter(SpriteBatch batch) {
        batch.draw(this, getX() - getWidth()/2, getY() - getHeight()/2);
    }

    public void createBoxBody(float bodyDefX, float bodyDefY, float halfWidth, float halfHeight, int ppm, BodyDef.BodyType type, short category, String userData) {
        super.createBoxBody(bodyDefX, bodyDefY, halfWidth, halfHeight, ppm, type, category, userData);
        this.getFixDef().filter.maskBits = GameScreen.GROUND | GameScreen.COIN | GameScreen.COLUMN | GameScreen.LOW_CEILING;
    }

    public void animate() {
        if (!Gdx.input.isKeyPressed(Input.Keys.DOWN)) super.setTexture(animatedCharacter.getFrame());
        else super.setTexture(animatedCharacter.getSlidingFrame());
        this.setRegionWidth(animatedCharacter.getFrame().getWidth());
        this.setRegionHeight(animatedCharacter.getFrame().getHeight());
    }

    public void setAbleToJump(boolean ableToJump) {
        this.ableToJump = ableToJump;
    }

    public void slideCharacter() {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            rotateCharacter(Math.PI/2);
            this.setTexture(new Texture("Gifs/img_1.png"));
            isRotated = true;
        }
    }

    public void rotateCharacter(double angle) {
        this.getBody().setTransform(this.getX() / GameScreen.PPM, this.getY()/ GameScreen.PPM, (float)angle);
        this.setPosition(getBody().getPosition().x * GameScreen.PPM, getBody().getPosition().y * GameScreen.PPM);
    }

    public boolean getIsRotated() {
        return isRotated;
    }

    public Vector2 getMovementVector() {
        return movementVector;
    }

    public void setMovementVector(Vector2 movementVector) {
        this.movementVector = movementVector;
    }
}// end class
