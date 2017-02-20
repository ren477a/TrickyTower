package com.tipqc.trickytower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
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
    private Rectangle killPlane;

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
        killPlane = new Rectangle(0, -10, Constants.WIDTH, 5);
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.getCam().position.y+=3;
        }
        killPlane.setPosition(0, cam.getCam().position.y - Constants.HEIGHT/2 - 50);

        player.update(delta, platforms.getPlatforms(), killPlane);
        walls.update(cam.getCam());
        cam.update();
        platforms.update(cam.getCam());
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        view.apply();
        sb.setProjectionMatrix(cam.getCam().combined);
        sr.setProjectionMatrix(cam.getCam().combined);

        sb.begin();
        sb.draw(background, cam.getCam().position.x - Constants.WIDTH/2, cam.getCam().position.y - Constants.HEIGHT/2);
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
