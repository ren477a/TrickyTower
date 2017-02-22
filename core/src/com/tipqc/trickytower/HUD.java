package com.tipqc.trickytower;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by REN on 2/22/2017.
 */

public class HUD {
    private BitmapFont font;
    public long score;
    private Vector2 position;

    public HUD() {
        font = new BitmapFont();
        font.getData().setScale(2);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        score = 0;
        position = new Vector2(0, 700);
    }

    public void update(Camera cam) {
        position.x = cam.position.x - Constants.WIDTH/2;
        position.y = cam.position.y + Constants.HEIGHT/2;

    }

    public void render(SpriteBatch sb) {
        font.draw(sb, "SCORE: " + score, position.x, position.y);
    }

}
