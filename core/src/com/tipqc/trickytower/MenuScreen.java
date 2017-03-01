package com.tipqc.trickytower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by REN on 2/13/2017.
 */

public class MenuScreen implements Screen {

    private TrickyTowerGame game;
    private Texture background;
    private SpriteBatch sb;

    public MenuScreen(TrickyTowerGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        background = new Texture("menubg.png");
        sb = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched())
            game.setScreen(new GameScreen(game));

        sb.begin();
        sb.draw(background, 0, 0, Constants.WIDTH, Constants.HEIGHT);
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
        sb.dispose();
        background.dispose();
        System.out.println("Menu screen disposed");

    }
}
