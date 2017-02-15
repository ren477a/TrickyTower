package com.tipqc.trickytower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.reflect.Constructor;


/**
 * Created by REN on 2/12/2017.
 */

public class Player {
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;



    public Player(int x, int y) {
        texture = new Texture("player.png");
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
    }

    public void update(float delta) {
        if(Gdx.input.isKeyPressed(Keys.LEFT)) {
            position.add(-Constants.PLAYER_MOVEMENT_SPEED, 0);
        } else if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
            position.add(Constants.PLAYER_MOVEMENT_SPEED, 0);
        }


        if(position.y > 0)
            velocity.add(0, Constants.GRAVITY);
        velocity.scl(delta);
        position.add(0, velocity.y);

        if(position.y < 0)
            position.y = 0;

        velocity.scl(1/delta);

    }

    public void render(SpriteBatch sb) {
        sb.draw(texture, position.x, position.y);
    }

    public void dispose() {
        texture.dispose();
    }

}
