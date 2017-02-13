package com.tipqc.trickytower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by REN on 2/12/2017.
 */

public class GameScreen implements Screen {

    private TrickyTowerGame game;
    private Player player;
    private SpriteBatch sb;

    public GameScreen(TrickyTowerGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        player = new Player(100, 700);
        sb = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        player.update(delta);

        sb.begin();
        sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y);
        sb.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        player.dispose();
        sb.dispose();
    }
}
