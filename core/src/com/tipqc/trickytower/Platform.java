package com.tipqc.trickytower;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by REN on 2/15/2017.
 */

public class Platform {
    public Vector2 position;
    public float width;
    boolean stepped;

    public Platform(float x, float y, float width) {
        position = new Vector2(x, y);
        this.width = width;
        stepped = false;
    }


    public void update() {

    }

    public void render(ShapeRenderer renderer) {
        renderer.setColor(Constants.PLATFORM_COLOR);
        renderer.rect(position.x, position.y, width, Constants.PLATFORM_HEIGHT);
    }

    public float getWidth() {
        return width;
    }


}
