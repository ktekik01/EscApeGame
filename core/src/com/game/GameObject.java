package com.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class GameObject extends Sprite {
    private float x;
    private float y;
    private final World world;
    private Body body;
    private FixtureDef fixDef;
    private Fixture fix;

    public GameObject(Texture text, World world) {
        super(text);
        this.world = world;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public World getWorld() {
        return world;
    }

    public Body getBody() {
        return body;
    }

    public void createCircularBody(float bodyDefX, float bodyDefY, float radius, int ppm, BodyDef.BodyType type, boolean isSensor, short category, String userData) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set((bodyDefX)/ ppm, bodyDefY / ppm);

        body = getWorld().createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius( radius / ppm);
        FixtureDef fixDef = new FixtureDef();

        fixDef.shape = shape;
        fixDef.density = 1;
        fixDef.isSensor = isSensor;

        fixDef.filter.categoryBits = category;
        fix = body.createFixture(fixDef);
        fix.setUserData(userData);
        shape.dispose();
    }

    public void createBoxBody (float bodyDefX, float bodyDefY, float halfWidth, float halfHeight, int ppm, BodyDef.BodyType type, short category, String userData) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(bodyDefX / ppm, bodyDefY / ppm);
        body = getWorld().createBody(bodyDef);
        body.setFixedRotation( true );
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(halfWidth/ppm, halfHeight/ppm);
        fixDef = new FixtureDef();

        fixDef.shape = shape;
        fixDef.density = 1;
        fixDef.filter.categoryBits = category;

        fix = body.createFixture(fixDef);
        fix.setUserData(userData);
        shape.dispose();
    }

    public void update(Vector2 movementVector, int ppm) {
        this.getBody().setLinearVelocity(movementVector);
        this.setPosition(getBody().getPosition().x * ppm, getBody().getPosition().y * ppm);
    }

    public void draw (SpriteBatch batch) {
        batch.draw(this,this.getX() - this.getWidth()/2, this.getY() - this.getHeight()/2);
    }

    public void moveToTheEnd (float x) {
        this.getBody().setTransform(x/ GameScreen.PPM, getY()/ GameScreen.PPM, 0);
        setPosition(getBody().getPosition().x * GameScreen.PPM, getBody().getPosition().y * GameScreen.PPM);
    }


    public void moveToTheUp ( float y){
        this.getBody().setTransform(getX() / GameScreen.PPM, y / GameScreen.PPM,0);
        setPosition(getBody().getPosition().x * GameScreen.PPM, getBody().getPosition().y * GameScreen.PPM);
    }

    public FixtureDef getFixDef() {
        return fixDef;
    }

    public Fixture getFix() {
        return fix;
    }

}
