package com.tipqc.trickytower;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by REN on 2/15/2017.
 */

public class Platform {
    public Vector2 position;
    public Vector2 velocity;
    public float width;
    boolean stepped;
    boolean isMoving;

    public Platform(float x, float y, float width, boolean isMoving) {
        position = new Vector2(x, y);
        this.width = width;
        stepped = false;
        this.isMoving = isMoving;
        Random r = new Random();
        float speed = 100 + r.nextFloat()*150;
        velocity = new Vector2(speed, 0);
    }


    public void update(float delta) {
        if(isMoving) {
            velocity.scl(delta);
            position.add(velocity.x, 0);
            velocity.scl(1/delta);
            ensureBounds();
        }


    }

    public void ensureBounds() {
        if(position.x < Constants.LEFT_BOUNDARY) {
            position.x = Constants.LEFT_BOUNDARY;
            velocity.x *= -1;
        }
        if(position.x + width > Constants.RIGHT_BOUNDARY) {
            position.x = Constants.RIGHT_BOUNDARY - width;
            velocity.x *= -1;
        }
    }

    public void render(ShapeRenderer renderer) {
        renderer.setColor(Constants.PLATFORM_COLOR);
        renderer.rect(position.x, position.y, width, Constants.PLATFORM_HEIGHT);
    }

    public float getWidth() {
        return width;
    }


}
