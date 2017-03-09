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
    private Vector2 position, velocity;
    private Array<TextureRegion> idle;
    private Platform platform;
    public Rectangle colBox;

    public Monster(Platform platform) {
        this.platform = platform;
        texture = new Texture("monster.png");
        idle = new Array<TextureRegion>();
        idle.add(new TextureRegion(texture, 0, 0, 32, 35));
        position = new Vector2();
        position.x = platform.position.x;
        position.y = platform.position.y + Constants.PLATFORM_HEIGHT;
        velocity = new Vector2(platform.velocity.x, platform.velocity.y);
        velocity.x = 100 + platform.velocity.x;
        colBox = new Rectangle(position.x, position.y, idle.get(0).getRegionWidth(), idle.get(0).getRegionHeight());
    }

    public void update(float delta) {
        ensureBounds();
        velocity.scl(delta);
        position.add(velocity.x, velocity.y);
        velocity.scl(1/delta);
        colBox.setPosition(position);
    }

    public void ensureBounds() {
        if(position.x < platform.position.x)
            velocity.x = 100 + platform.velocity.x;
        if(position.x + idle.get(0).getRegionWidth() > platform.position.x+platform.width)
            velocity.x = -100 + platform.velocity.x;
    }

    public void render(SpriteBatch sb) {
        sb.draw(idle.get(0), position.x, position.y);
    }

    public void dispose() {
        texture.dispose();
    }
}
