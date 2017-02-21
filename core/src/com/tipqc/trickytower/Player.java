package com.tipqc.trickytower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.ConeShapeBuilder;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tipqc.trickytower.Enums.JumpState;


/**
 * Created by REN on 2/12/2017.
 */

public class Player {
    private Texture texture;
    public Vector2 position;
    private Vector2 lastFramePosition;
    private Vector2 velocity;
    private Platform currentPlatform;
    private JumpState jumpState;
    private Rectangle rect;


    public Player(int x, int y) {
        texture = new Texture("player2.png");
        position = new Vector2(x, y);
        lastFramePosition = new Vector2(position);
        velocity = new Vector2(0, 0);
        rect = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public void handleInput(float delta) {
        float xAccel = Gdx.input.getAccelerometerX();

        if(Gdx.input.isKeyPressed(Keys.J) || xAccel > 1) {
            position.add(-Constants.PLAYER_MOVEMENT_SPEED, 0);
        } else if(Gdx.input.isKeyPressed(Keys.L) || xAccel < -1) {
            position.add(Constants.PLAYER_MOVEMENT_SPEED, 0);
        }

        if(Gdx.input.isKeyJustPressed(Keys.I) || Gdx.input.justTouched()) {
            jump();
        }
    }

    public void update(float delta, Array<Platform> plats, Rectangle killPlane) {
        //TODO: modify when to fall
        if(currentPlatform == null)
            currentPlatform = plats.get(0);
        if(position.x+texture.getWidth() < currentPlatform.position.x ||
                position.x > currentPlatform.position.x+ currentPlatform.getWidth()) {
            jumpState = JumpState.FALLING;
        }


        lastFramePosition.set(position);
        handleInput(delta);

        //gravity
        if(jumpState != JumpState.GROUNDED)
            velocity.add(0, Constants.GRAVITY);
        velocity.scl(delta);
        position.add(0, velocity.y);

        if(velocity.y < 0)
            jumpState = JumpState.FALLING;

        for (int i = 0; i < plats.size; i++) {
            if(landedOnPlatform(plats.get(i)))
                currentPlatform = plats.get(i);
        }

        ensureBounds();

        velocity.scl(1/delta);
        rect.setPosition(position.x, position.y);

    }

    public boolean landedOnPlatform(Platform platform) {
        float platformLevel = platform.position.y+Constants.PLATFORM_HEIGHT;
        float playerCenter = position.x + texture.getWidth()/2;
        if(jumpState == JumpState.FALLING && lastFramePosition.y > platformLevel && position.y < platformLevel
                && playerCenter > platform.position.x && playerCenter < platform.position.x + platform.getWidth()) {
            position.y = platformLevel;
            velocity.y = 0;
            jumpState = JumpState.GROUNDED;
            return true;
        }
        return false;
    }

    public boolean collision(Rectangle killPlane) {
        return rect.overlaps(killPlane);
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
            jumpState = JumpState.JUMPING;
        }
    }

    public void render(SpriteBatch sb) {
        sb.draw(texture, position.x, position.y);
    }

    public void dispose() {
        texture.dispose();
    }

}
