package com.tipqc.trickytower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by REN on 2/12/2017.
 */

public class Player {
    private static final int GRAVITY = -15;
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;



    public Player(int x, int y) {
        texture = new Texture("bird.png");
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
    }

    public void update(float delta) {
        if(position.y > 0)
            velocity.add(0, GRAVITY);
        velocity.scl(delta);
        position.add(0, velocity.y);

        if(position.y < 0)
            position.y = 0;

        velocity.scl(1/delta);

    }

    public void dispose() {
        texture.dispose();
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return texture;
    }

}
