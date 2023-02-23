package com.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;


import java.util.ArrayList;

public class ComplexGround {
    private int width;
    public ArrayList<Ground> grounds;

    public ComplexGround (int x, World world, int length) {
        this.grounds = new ArrayList<>();
        int height;
        //int y = (int) (Math.random() * 4) ;
        int z = (int) (Math.random() * 3 );
        for (int i=0; i < length ; i++) {
            if (z == 0 ) height = 0;
            else if (z == 1) height = -1 * Ground.HEIGHT_DIFFERENCE;
            else height = -2 * Ground.HEIGHT_DIFFERENCE;
            Ground ground =new Ground (new Texture("House.jpeg"), world, x, height - i );
            //Ground ground = new Ground (new Texture("UnitGround2.png"), world, x, height);
            x = x + (int) ground.getWidth();
            width = width + (int) ground.getWidth();
            grounds.add(ground);
        }
    }

    public void drawComplexGround (SpriteBatch batch) {
        for (Ground ground : grounds) {
            batch.draw(ground,ground.getX() - ground.getWidth()/2,
                    ground.getY() - ground.getHeight()/2);

        }
    }

    public void moveComplexGround () {
        for (Ground ground : grounds) {
            ground.moveGround();
        }
    }

    public void moveToTheEnd (float x) {
        for (Ground ground : grounds) {
            ground.getBody().setTransform(x / GameScreen.PPM, ground.getY() / GameScreen.PPM, 0);
            ground.setPosition(ground.getBody().getPosition().x * GameScreen.PPM,
                    ground.getBody().getPosition().y * GameScreen.PPM);
            x = x + ground.getWidth();
        }
    }
    public int getWidth() {
        return width;
    }
}
