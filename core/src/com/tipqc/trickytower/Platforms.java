package com.tipqc.trickytower;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by REN on 2/15/2017.
 */

public class Platforms {
    private Platform platform;

    public Platforms() {
        platform = new Platform(Constants.LEFT_BOUNDARY, 0, Constants.WIDTH-50);
    }

    public void render(ShapeRenderer sr) {
        platform.render(sr);

    }


    public void dispose() {
    }
}
