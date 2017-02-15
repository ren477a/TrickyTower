package com.tipqc.trickytower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by REN on 2/12/2017.
 */

public class GameScreen implements Screen {

    public static final String TAG = GameScreen.class.getName();

    private TrickyTowerGame game;
    private Viewport view;
    private Player player;
    private SpriteBatch sb;

    public GameScreen(TrickyTowerGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        player = new Player(0, 100);
        view = new FitViewport(Constants.WIDTH, Constants.HEIGHT);
        sb = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        player.update(delta);
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        view.apply(true);
        sb.setProjectionMatrix(view.getCamera().combined);

        sb.begin();
        player.render(sb);
        sb.end();

    }

    @Override
    public void resize(int width, int height) {
        view.update(width, height);
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
