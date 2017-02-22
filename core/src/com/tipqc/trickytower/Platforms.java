package com.tipqc.trickytower;

import com.badlogic.gdx.graphics.Camera;
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
            float yDistance = 75 + r.nextFloat()*75;
            float width = Constants.PLATFORM_MINIMUM_WIDTH + r.nextFloat()*100;
//            float position = Constants.LEFT_BOUNDARY +
//                    r.nextFloat()*(Constants.RIGHT_BOUNDARY - width - Constants.LEFT_BOUNDARY);
            float left = plats.get(i-1).position.x - width - Constants.PLATFORM_X_DISTANCE;
            float right = plats.get(i-1).position.x + plats.get(i-1).width + Constants.PLATFORM_X_DISTANCE;
            if(left < Constants.LEFT_BOUNDARY)
                left = Constants.LEFT_BOUNDARY;
            if(right + width > Constants.RIGHT_BOUNDARY)
                right = Constants.RIGHT_BOUNDARY - width;
            float position = left + r.nextFloat()*(right-left);
            plats.add(new Platform(position, plats.get(i-1).position.y + yDistance, width));
        }
    }

    public void update(Camera cam) {
        for (int i = 0; i < plats.size; i++) {
            if(plats.get(i).position.y < cam.position.y - Constants.HEIGHT/2) {
                reposition(plats.get(i), plats.get((i+9)%10));
            }
        }
    }

    public void render(ShapeRenderer sr) {
        //platform.render(sr);
        for (int i = 0; i < plats.size; i++) {
            plats.get(i).render(sr);
        }
    }

    public void reposition(Platform platform, Platform platform2) {
        platform.position.y = (platform2.position.y) + (75 + r.nextFloat()*75);
        platform.width = Constants.PLATFORM_MINIMUM_WIDTH + r.nextFloat()*100;
        float left = platform2.position.x - platform.width - Constants.PLATFORM_X_DISTANCE;
        float right = platform2.position.x + platform2.width + Constants.PLATFORM_X_DISTANCE;
        if(left < Constants.LEFT_BOUNDARY)
            left = Constants.LEFT_BOUNDARY;
        if(right + platform.width > Constants.RIGHT_BOUNDARY)
            right = Constants.RIGHT_BOUNDARY - platform.width;
        platform.position.x = left + r.nextFloat()*(right-left);
        platform.stepped = false;
//        platform.position.x =  Constants.LEFT_BOUNDARY +
//                r.nextFloat()*(Constants.RIGHT_BOUNDARY - platform.width - Constants.LEFT_BOUNDARY);

    }

    public Array<Platform> getPlatforms() {
        return plats;
    }


    public void dispose() {

    }
}
