package com.tipqc.trickytower;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by REN on 2/15/2017.
 */

public class Walls {
    private Texture texture;
    private Vector2 leftWall1, leftWall2, rightWall1, rightWall2;

    public Walls() {
        texture = new Texture("wall.png");
        leftWall1 = new Vector2(-3*texture.getWidth()/4, 0);
        leftWall2 = new Vector2(-3*texture.getWidth()/4, Constants.HEIGHT);
        rightWall1 = new Vector2(Constants.WIDTH - texture.getWidth()/4, 0);
        rightWall2 = new Vector2(Constants.WIDTH - texture.getWidth()/4, Constants.HEIGHT);

    }

    public void update(Camera cam) {
        if(leftWall1.y + Constants.HEIGHT < cam.position.y - Constants.HEIGHT/2)
            leftWall1.y += Constants.HEIGHT*2;
        if(rightWall1.y + Constants.HEIGHT < cam.position.y - Constants.HEIGHT/2)
            rightWall1.y += Constants.HEIGHT*2;
        if(leftWall2.y + Constants.HEIGHT < cam.position.y - Constants.HEIGHT/2)
            leftWall2.y += Constants.HEIGHT*2;
        if(rightWall2.y + Constants.HEIGHT < cam.position.y - Constants.HEIGHT/2)
            rightWall2.y += Constants.HEIGHT*2;
    }

    public void render(SpriteBatch sb) {
        sb.draw(texture, leftWall1.x, leftWall1.y);
        sb.draw(texture, leftWall2.x, leftWall2.y);
        sb.draw(texture, rightWall1.x, rightWall1.y);
        sb.draw(texture, rightWall2.x, rightWall2.y);
    }

    public void dispose() {
        texture.dispose();
    }
}
