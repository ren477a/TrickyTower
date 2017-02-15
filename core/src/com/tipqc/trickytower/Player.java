package com.tipqc.trickytower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.tipqc.trickytower.Enums.JumpState;


/**
 * Created by REN on 2/12/2017.
 */

public class Player {
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private JumpState jumpState;


    public Player(int x, int y) {
        texture = new Texture("player.png");
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
    }

    public void update(float delta) {

        if(Gdx.input.isKeyPressed(Keys.J)) {
            position.add(-Constants.PLAYER_MOVEMENT_SPEED, 0);
        } else if(Gdx.input.isKeyPressed(Keys.L)) {
            position.add(Constants.PLAYER_MOVEMENT_SPEED, 0);
        }

        if(Gdx.input.isKeyJustPressed(Keys.I)) {
            jump();
        }

        //gravity
        if(position.y > 0)
            velocity.add(0, Constants.GRAVITY);
        velocity.scl(delta);
        position.add(0, velocity.y);

        //stop falling on floor
        if(position.y < Constants.PLATFORM_HEIGHT) {
            position.y = Constants.PLATFORM_HEIGHT;
            jumpState = JumpState.GROUNDED;
        }

        ensureBounds();

        velocity.scl(1/delta);

    }

    public void ensureBounds() {
        if(position.x < Constants.LEFT_BOUNDARY)
            position.x = Constants.LEFT_BOUNDARY;
        if(position.x + texture.getWidth() > Constants.RIGHT_BOUNDARY)
            position.x = Constants.RIGHT_BOUNDARY - texture.getWidth();
    }

    public void jump() {
        if(jumpState == JumpState.GROUNDED) {
            velocity.y = Constants.JUMP_VELOCITY;
            jumpState = JumpState.AERIAL;
        }
    }

    public void render(SpriteBatch sb) {
        sb.draw(texture, position.x, position.y);
    }

    public void dispose() {
        texture.dispose();
    }

}
