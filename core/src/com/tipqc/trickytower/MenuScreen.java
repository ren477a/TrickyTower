package com.tipqc.trickytower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by REN on 2/13/2017.
 */

public class MenuScreen implements Screen {

    private TrickyTowerGame game;
    private Texture background;
    private SpriteBatch sb;
    private Viewport view;

    public MenuScreen(TrickyTowerGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        view = new FitViewport(Constants.WIDTH, Constants.HEIGHT, new OrthographicCamera());
        view.getCamera().position.x = Constants.WIDTH/2;
        view.getCamera().position.y = Constants.HEIGHT/2;
        background = new Texture("menubg.png");
        sb = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        view.apply();
        sb.setProjectionMatrix(view.getCamera().combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.end();
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.justTouched())
            game.setScreen(new GameScreen(game));

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
        System.out.println("Menu screen disposed");

    }
}
