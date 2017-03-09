package com.tipqc.trickytower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by REN on 3/8/2017.
 */

public class Monster {
    private Texture texture;
    public Vector2 position, velocity;
    private Array<TextureRegion> idle;
    private Platform platform;
    public Rectangle colBox;
    private float offset;
    private int idleFrame;
    private float idleCurrentTime;
    private float idleMaxFrameTime;

    public Monster(Platform platform) {
        this.platform = platform;
        texture = new Texture("monster.png");
        idle = new Array<TextureRegion>();
        idle.add(new TextureRegion(texture, 0, 0, 32, 35));
        idle.add(new TextureRegion(texture, 32, 0, 32, 35));
        idleMaxFrameTime = 1.0f / idle.size;
        position = new Vector2();
        position.x = platform.position.x;
        position.y = platform.position.y + Constants.PLATFORM_HEIGHT;
        velocity = new Vector2(platform.velocity.x, platform.velocity.y);
        velocity.x = 100 + platform.velocity.x;
        offset = 10f;
        colBox = new Rectangle(position.x+offset, position.y, idle.get(0).getRegionWidth()-2*offset, idle.get(0).getRegionHeight()-2*offset);
    }

    public void update(float delta) {
        ensureBounds();
        velocity.scl(delta);
        position.add(velocity.x, velocity.y);
        velocity.scl(1/delta);
        colBox.setPosition(position.x+offset, position.y);

        idleCurrentTime+=delta;
        if(idleCurrentTime > idleMaxFrameTime) {
            idleFrame++;
            idleCurrentTime = 0;
        }
        if(idleFrame >= idle.size)
            idleFrame = 0;

        if(velocity.x > 0) {
            for (int i = 0; i < idle.size; i++) {
                if(!idle.get(i).isFlipX())
                    idle.get(i).flip(true, false);
            }
        } else {
            for(int i = 0; i < idle.size; i++) {
                if(idle.get(i).isFlipX())
                    idle.get(i).flip(true, false);
            }
        }
    }

    public void ensureBounds() {
        if(position.x < platform.position.x)
            velocity.x = 100 + platform.velocity.x;
        if(position.x + idle.get(0).getRegionWidth() > platform.position.x+platform.width)
            velocity.x = -100 + platform.velocity.x;
    }

    public void render(SpriteBatch sb) {
        sb.draw(idle.get(idleFrame), position.x, position.y);
    }

    public void dispose() {
        texture.dispose();
    }

    public void putAbove(Platform platform) {
        this.platform = platform;
        position.x = platform.position.x;
        position.y = platform.position.y + Constants.PLATFORM_HEIGHT;
    }

    public void hide() {
        position.y = -100;
    }

    public void show() {
        position.y = platform.position.y + Constants.PLATFORM_HEIGHT;
    }
}
