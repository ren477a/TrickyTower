package com.tipqc.trickytower;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import static com.badlogic.gdx.utils.Align.left;


/**
 * Created by REN on 2/15/2017.
 */

public class Platforms {


    private Array<Platform> plats;
    private Platform platform;
    private Random r;
    Monster monster;

    public Platforms() {
        r = new Random();
        plats = new Array<Platform>();
        plats.add(new Platform(Constants.LEFT_BOUNDARY, 0, Constants.WIDTH-50, false, false));
        plats.get(0).color.r = r.nextFloat()*255;
        plats.get(0).color.g = r.nextFloat()*255;
        plats.get(0).color.b = r.nextFloat()*255;
        for(int i = 1; i < 10; i++) {
            float yDistance = 75 + r.nextFloat()*75;
            float width = Constants.PLATFORM_MINIMUM_WIDTH + r.nextFloat()*100;
            float left = plats.get(i-1).position.x - width - Constants.PLATFORM_X_DISTANCE;
            float right = plats.get(i-1).position.x + plats.get(i-1).width + Constants.PLATFORM_X_DISTANCE;

            if(left < Constants.LEFT_BOUNDARY)
                left = Constants.LEFT_BOUNDARY;
            if(right + width > Constants.RIGHT_BOUNDARY)
                right = Constants.RIGHT_BOUNDARY - width;
            float position = left + r.nextFloat()*(right-left);
            plats.add(new Platform(position, plats.get(i-1).position.y + yDistance, width, false, false));
            plats.get(i).color.r = r.nextFloat()*255;
            plats.get(i).color.g = r.nextFloat()*255;
            plats.get(i).color.b = r.nextFloat()*255;
        }
        monster = new Monster(plats.get(0));
        monster.putAbove(plats.get(3));
    }

    public void update(Camera cam, float delta, long score) {
        monster.update(delta);
        for (int i = 0; i < plats.size; i++) {
            if(plats.get(i).position.y < cam.position.y - Constants.HEIGHT/2) {
                reposition(plats.get(i), plats.get((i+9)%10), score, cam);
            }
            plats.get(i).update(delta);
        }
    }

    public void render(ShapeRenderer sr) {
        //platform.render(sr);
        for (int i = 0; i < plats.size; i++) {
            plats.get(i).render(sr);
        }
    }

    public void reposition(Platform platform, Platform platform2, long score, Camera cam) {
        platform.color.r = r.nextFloat()*255;
        platform.color.g = r.nextFloat()*255;
        platform.color.b = r.nextFloat()*255;
        platform.isMoving = false;
        platform.isKick = false;
        platform.velocity.x = 0;
        platform.position.y = (platform2.position.y) + (75 + r.nextFloat()*75);
        platform.width = Constants.PLATFORM_MINIMUM_WIDTH + r.nextFloat()*100;
        if(monster.position.y< cam.position.y - Constants.HEIGHT/2) {
            if(platform.width>100) {
                monster.putAbove(platform);
                monster.show();
            } else {
                monster.hide();
            }
        }

        float left = platform2.position.x - platform.width - Constants.PLATFORM_X_DISTANCE;
        float right = platform2.position.x + platform2.width + Constants.PLATFORM_X_DISTANCE;
        if(left < Constants.LEFT_BOUNDARY)
            left = Constants.LEFT_BOUNDARY;
        if(right + platform.width > Constants.RIGHT_BOUNDARY)
            right = Constants.RIGHT_BOUNDARY - platform.width;
        platform.position.x = left + r.nextFloat()*(right-left);
        platform.stepped = false;
        float rMoving = r.nextFloat();
        if(score > 32) {
            if(rMoving < 0.10) {
                platform.isMoving = true;
                platform.velocity.x = 100;
            }
            if(rMoving > 0.90 && rMoving < 1.00) {
                platform.isKick = true;
            }
        } else if (score > 64) {
            if(rMoving < 0.20) {
                platform.isMoving = true;
                platform.velocity.x = 110;
            }
            if(rMoving > 0.80 && rMoving < 1.00) {
                platform.isKick = true;
            }
        } else if (score > 128) {
            if(rMoving < 0.30) {
                platform.isMoving = true;
                platform.velocity.x = 140;
            }
            if(rMoving > 0.70 && rMoving < 1.00) {
                platform.isKick = true;
            }
        } else if (score > 256) {
            if(rMoving < 0.40) {
                platform.isMoving = true;
                platform.velocity.x = 180;
            }
            if(rMoving > 0.60 && rMoving < 1.00) {
                platform.isKick = true;
            }
        } else if (score > 512) {
            if(rMoving < 0.50) {
                platform.isMoving = true;
                platform.velocity.x = 200;
            }
            if(rMoving > 0.50 && rMoving < 1.00) {
                platform.isKick = true;
            }
        }

    }

    public Array<Platform> getPlatforms() {
        return plats;
    }


    public void dispose() {
        monster.dispose();
    }
}
