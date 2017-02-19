package com.tipqc.trickytower;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Created by REN on 2/15/2017.
 */

public class Platforms {


    private Array<Platform> plats;
    private Platform platform;
    private Random r;

    public Platforms() {
        r = new Random();
        plats = new Array<Platform>();
        plats.add(new Platform(Constants.LEFT_BOUNDARY, 0, Constants.WIDTH-50));
        for(int i = 1; i < 10; i++) {
            plats.add(new Platform(Constants.LEFT_BOUNDARY, plats.get(i-1).getPosition().y + (75 + r.nextInt(75)), r.nextInt(Constants.WIDTH-50)));
        }
    }

    public void render(ShapeRenderer sr) {
        //platform.render(sr);
        for (int i = 0; i < plats.size; i++) {
            plats.get(i).render(sr);
        }
    }

    public Array<Platform> getPlatforms() {
        return plats;
    }


    public void dispose() {

    }
}
