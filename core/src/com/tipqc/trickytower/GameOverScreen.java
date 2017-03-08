package com.tipqc.trickytower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by REN on 3/1/2017.
 */

public class GameOverScreen implements Screen {

    private TrickyTowerGame game;
    private Texture background;
    private SpriteBatch sb;
    private BitmapFont font;
    private long highScore;
    private long yourScore;
    private Color color;
    private Viewport view;

    public GameOverScreen(TrickyTowerGame game, long highScore, long yourScore) {
        this.game = game;
        this.highScore = highScore;
        this.yourScore = yourScore;
        color = new Color(255, 75, 0, 1);
    }

    @Override
    public void show() {
        background = new Texture("gameover.png");
        sb = new SpriteBatch();
        view = new FitViewport(Constants.WIDTH, Constants.HEIGHT, new OrthographicCamera());
        view.getCamera().position.x = Constants.WIDTH/2;
        view.getCamera().position.y = Constants.HEIGHT/2;
        font = new BitmapFont();
        font.getData().setScale(3);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(color);
    }

    @Override
    public void render(float delta) {
        String x = Long.toString(yourScore);
        String y = Long.toString(highScore);
        float xoff = x.length() * 10;
        float yoff = y.length() * 10;
        view.apply();
        sb.setProjectionMatrix(view.getCamera().combined);
        sb.begin();
        sb.draw(background, 0, 0);
        font.draw(sb, x, 230-xoff, 420);
        font.draw(sb, y, 230-yoff, 250);
        sb.end();

        if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            dispose();
            game.setScreen(new GameScreen(game));
        }
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
        sb.dispose();
        background.dispose();
        font.dispose();
    }
}
