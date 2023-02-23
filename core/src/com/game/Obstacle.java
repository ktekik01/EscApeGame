package com.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Obstacle extends GameObject {
    Obstacle(Texture text, World world) {
        super(text, world);
    }
}
