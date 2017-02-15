package com.tipqc.trickytower;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by REN on 2/15/2017.
 */

public class Platform {
    private Vector2 position;
    private float width;

    public Platform(float x, float y, float width) {
        position = new Vector2(x, y);
        this.width = width;
    }


    public void update() {

    }

    public void render(ShapeRenderer renderer) {
        renderer.setColor(Color.BLACK);
        renderer.rect(position.x, position.y, width, Constants.PLATFORM_HEIGHT);
    }
}
