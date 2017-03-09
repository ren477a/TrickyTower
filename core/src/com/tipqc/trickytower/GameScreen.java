package com.tipqc.trickytower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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
    private HUD hud;
    Preferences pref;
    long currentHighScore;


    public GameScreen(TrickyTowerGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        pref = Gdx.app.getPreferences("GameData");
        currentHighScore = pref.getLong("highScore", 0);
        view = new FitViewport(Constants.WIDTH, Constants.HEIGHT);
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        background = new Texture("bg.png");
        player = new Player(Constants.WIDTH/2, 50);
        cam = new GameCam(view.getCamera(), player);
        walls = new Walls();
        platforms = new Platforms();
        killPlane = new Rectangle(0, -10, Constants.WIDTH, 5);
        hud = new HUD(sb, currentHighScore);
    }

    @Override
    public void render(float delta) {

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            newGame();
        killPlane.setPosition(0, cam.getCam().position.y - Constants.HEIGHT/2 - 50);
        if(player.collision(killPlane)) {
            newGame();
        }
        player.update(delta, platforms.getPlatforms(), killPlane, hud, cam.highestReached);
        walls.update(cam.getCam());
        cam.update();
        platforms.update(cam.getCam(), delta, hud.score);
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
        platforms.monster.render(sb);
        player.render(sb);
        hud.render(sb);
        sb.end();
        hud.update(delta);
        sb.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    public void newGame() {
        //game over
        if(hud.score > currentHighScore) {
            pref.putLong("highScore", hud.score);
            pref.flush();
            currentHighScore = hud.score;
        }
        dispose();
        game.setScreen(new GameOverScreen(game, currentHighScore, hud.score));
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
