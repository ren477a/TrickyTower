package com.tipqc.trickytower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    private GameCam cam;
    //TODO: Make a game camera

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
        cam = new GameCam(view.getCamera(), player);
        walls = new Walls();
        platforms = new Platforms();
    }

    @Override
    public void render(float delta) {
        player.update(delta, platforms.getPlatforms());
        walls.update();
        cam.update();
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        view.apply();
        sb.setProjectionMatrix(cam.getCam().combined);
        sr.setProjectionMatrix(cam.getCam().combined);

        sb.begin();
        sb.draw(background, 0, 0);
        walls.render(sb);
        sb.end();

        sr.begin(ShapeRenderer.ShapeType.Filled);
        platforms.render(sr);
        sr.end();

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
        walls.dispose();
        platforms.dispose();
        background.dispose();
        sb.dispose();

    }
}
