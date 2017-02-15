package com.tipqc.trickytower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by REN on 2/12/2017.
 */

public class GameScreen implements Screen {

    public static final String TAG = GameScreen.class.getName();

    private TrickyTowerGame game;
    private Viewport view;
    private SpriteBatch sb;
    private ShapeRenderer sr;
    private Texture background;
    private Player player;
    private Walls walls;
    private Platforms platforms;

    public GameScreen(TrickyTowerGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        view = new FitViewport(Constants.WIDTH, Constants.HEIGHT);
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        background = new Texture("bg.png");
        player = new Player(Constants.WIDTH/2, 100);
        walls = new Walls();
        platforms = new Platforms();
    }

    @Override
    public void render(float delta) {
        player.update(delta);
        walls.update();
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        view.apply(true);
        sb.setProjectionMatrix(view.getCamera().combined);

        sb.begin();
        sb.draw(background, 0, 0);
        walls.render(sb);
        player.render(sb);
        sb.end();

        sr.setProjectionMatrix(view.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        platforms.render(sr);
        sr.end();

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
        walls.dispose();
        platforms.dispose();
        background.dispose();
        sb.dispose();

    }
}
