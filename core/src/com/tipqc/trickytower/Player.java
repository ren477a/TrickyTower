package com.tipqc.trickytower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tipqc.trickytower.Enums.JumpState;
import com.tipqc.trickytower.Enums.WalkState;
import com.tipqc.trickytower.Enums.Facing;



/**
 * Created by REN on 2/12/2017.
 */

public class Player {
    private Texture texture;
    private Array<TextureRegion> idle;
    private int idleFrame;
    private float idleCurrentTime;
    private float idleMaxFrameTime;
    private Array<TextureRegion> walking;
    private int walkingFrame;
    private float walkingCurrentTime;
    private float walkingMaxFrameTime;
    private TextureRegion jumping;
    public Vector2 position;
    private Vector2 lastFramePosition;
    private Vector2 velocity;
    private Platform currentPlatform;
    private JumpState jumpState;
    private WalkState walkState;
    private Facing facing;
    private Rectangle rect;
    Sound landingSound;

    public Player(int x, int y) {
        landingSound = Gdx.audio.newSound(Gdx.files.internal("jumpland.wav"));
        texture = new Texture("playeranimation.png");
        idle = new Array<TextureRegion>();
        for(int i = 0; i < 4; i++) {
            idle.add(new TextureRegion(texture, 60+i*60, 0, 38, 66));
        }
        //animation variables
        idleFrame = 0;
        idleMaxFrameTime = 0.5f / idle.size;
        walking = new Array<TextureRegion>();
        for(int i = 0; i < 3; i++) {
            walking.add(new TextureRegion(texture, 300+i*60, 0, 38, 66));
        }
        walking.add(new TextureRegion(texture, 0, 66, 38, 66));
        //animation variables
        walkingFrame = 0;
        walkingMaxFrameTime = 1.0f / walking.size;



        facing = facing.RIGHT;
        walkState = WalkState.IDLE;
        jumping = new TextureRegion(texture, 60, 66, 38, 66);
        position = new Vector2(x, y);
        lastFramePosition = new Vector2(position);
        velocity = new Vector2(0, 0);
        rect = new Rectangle(position.x, position.y, idle.get(0).getRegionWidth(), idle.get(0).getRegionHeight());
    }

    public void updateAnimation(float delta) {
        if(walkState == WalkState.IDLE) {
            idleCurrentTime+=delta;
            if(idleCurrentTime > idleMaxFrameTime) {
                idleFrame++;
                idleCurrentTime = 0;
            }
            if(idleFrame >= idle.size)
                idleFrame = 0;
        } else if (walkState == WalkState.WALKING) {
            walkingCurrentTime+=delta;
            if(walkingCurrentTime > walkingMaxFrameTime) {
                walkingFrame++;
                walkingCurrentTime = 0;
            }
            if(walkingFrame >= walking.size)
                walkingFrame = 0;
        }

    }

    public void handleInput(float delta) {
        float xAccel = Gdx.input.getAccelerometerX();
        if(Gdx.input.isKeyPressed(Keys.J) || xAccel > 0.75) {
            float speed = -Constants.PLAYER_MOVEMENT_SPEED;
            if(xAccel>0.75) {
                speed = -Constants.PLAYER_MOVEMENT_SPEED*(xAccel/3);
                if(speed < -Constants.PLAYER_MOVEMENT_SPEED)
                    speed = -Constants.PLAYER_MOVEMENT_SPEED;
            }

            position.add(speed, 0);
            walkState = WalkState.WALKING;
            facing = Facing.LEFT;
            if(!jumping.isFlipX())
                jumping.flip(true, false);
            for(int i = 0; i < idle.size; i++) {
                if(!idle.get(i).isFlipX())
                    idle.get(i).flip(true, false);
            }
            if(!walking.get(walkingFrame).isFlipX())
                walking.get(walkingFrame).flip(true, false);
        } else if(Gdx.input.isKeyPressed(Keys.L) || xAccel < -0.75) {
            float speed = Constants.PLAYER_MOVEMENT_SPEED;
            if(xAccel<-0.75) {
                speed = Constants.PLAYER_MOVEMENT_SPEED*(-xAccel/3);
                if(speed > Constants.PLAYER_MOVEMENT_SPEED)
                    speed = Constants.PLAYER_MOVEMENT_SPEED;
            }

            position.add(speed, 0);
            walkState = WalkState.WALKING;
            facing = Facing.RIGHT;
            if(jumping.isFlipX())
                jumping.flip(true, false);
            for(int i = 0; i < idle.size; i++) {
                if(idle.get(i).isFlipX())
                    idle.get(i).flip(true, false);
            }
            if(walking.get(walkingFrame).isFlipX())
                walking.get(walkingFrame).flip(true, false);
        }

        if(Gdx.input.isKeyJustPressed(Keys.I) || Gdx.input.justTouched()) {
            jump();
        }
    }

    public void update(float delta, Array<Platform> plats, Rectangle killPlane, HUD hud, float highestReached) {
        walkState = WalkState.IDLE;
        if(currentPlatform == null)
            currentPlatform = plats.get(0);
        if(position.x+idle.get(0).getRegionWidth() < currentPlatform.position.x ||
                position.x > currentPlatform.position.x+ currentPlatform.getWidth()) {
            jumpState = JumpState.FALLING;
        }

        lastFramePosition.set(position);
        handleInput(delta);
        //gravity
        if(jumpState != JumpState.GROUNDED)
            velocity.add(0, Constants.GRAVITY);

        //velocity.x = currentPlatform.velocity.x;
        if(jumpState == JumpState.GROUNDED) {
            velocity.x = currentPlatform.velocity.x;
        }
        else
            velocity.x = 0;
        velocity.scl(delta);
        position.add(velocity.x, velocity.y);

        if(velocity.y < 0)
            jumpState = JumpState.FALLING;

        for (int i = 0; i < plats.size; i++) {
            if(landedOnPlatform(plats.get(i))) {
                currentPlatform = plats.get(i);
                if(!currentPlatform.stepped) {
                    hud.score++;
                    currentPlatform.stepped = true;
                }
            }
        }

        ensureBounds();

        velocity.scl(1/delta);
        rect.setPosition(position.x, position.y);
        updateAnimation(delta);

    }

    public boolean landedOnPlatform(Platform platform) {
        float platformLevel = platform.position.y+Constants.PLATFORM_HEIGHT;
        float playerCenter = position.x + idle.get(0).getRegionWidth()/2;
        if(jumpState == JumpState.FALLING && lastFramePosition.y > platformLevel && position.y < platformLevel
                && playerCenter > platform.position.x && playerCenter < platform.position.x + platform.getWidth()) {
            position.y = platformLevel;
            velocity.y = 0;
            jumpState = JumpState.GROUNDED;
            landingSound.play(1.0f);
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
        if(position.x + idle.get(0).getRegionWidth() > Constants.RIGHT_BOUNDARY)
            position.x = Constants.RIGHT_BOUNDARY - idle.get(0).getRegionWidth();
    }

    public void jump() {
        if(jumpState == JumpState.GROUNDED) {
            velocity.y = Constants.JUMP_VELOCITY;
            jumpState = JumpState.JUMPING;
        }
    }

    public void render(SpriteBatch sb) {
        if(jumpState == JumpState.FALLING || jumpState == JumpState.JUMPING)
            sb.draw(jumping, position.x, position.y);
        else if(walkState == WalkState.WALKING)
            sb.draw(walking.get(walkingFrame), position.x, position.y);
        else if(walkState == WalkState.IDLE)
            sb.draw(idle.get(idleFrame), position.x, position.y);
    }

    public void dispose() {
        texture.dispose();
        landingSound.dispose();
    }

}
