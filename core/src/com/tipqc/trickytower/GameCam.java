package com.tipqc.trickytower;

import com.badlogic.gdx.graphics.Camera;

/**
 * Created by REN on 2/19/2017.
 */

public class GameCam {
    private Camera cam;
    private Player target;
    public float highestReached;

    public GameCam(Camera cam, Player target) {
        this.cam = cam;
        cam.position.x = Constants.WIDTH/2;
        cam.position.y = Constants.HEIGHT/2;
        highestReached = Constants.HEIGHT/2;
        this.target = target;
    }

    public void update() {
        if(target.position.y > highestReached) {
            highestReached = target.position.y;
            cam.position.y = highestReached;
        }

        cam.update();
    }

    public Camera getCam() {
        return cam;
    }

}
