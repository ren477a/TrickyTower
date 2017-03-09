package com.tipqc.trickytower;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by REN on 2/22/2017.
 */

public class HUD {
    private BitmapFont font;
    public Stage stage;
    private Viewport view;
    public long score;
    private Label scoreLabel;
    private Label yourScoreLabel;
    private Label highScoreLabel;
    private Label currentHighScoreLabel;

    public HUD(SpriteBatch sb, long currentHighScore) {
        font = new BitmapFont();
        font.getData().setScale(2);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        score = 0;
        view = new FitViewport(Constants.WIDTH, Constants.HEIGHT, new OrthographicCamera());
        stage = new Stage(view, sb);
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        scoreLabel =new Label("SCORE:", new Label.LabelStyle(font, Color.WHITE));
        yourScoreLabel =new Label(String.format("%6d", score), new Label.LabelStyle(font, Color.WHITE));
        highScoreLabel =new Label("HIGH SCORE:", new Label.LabelStyle(font, Color.WHITE));
        currentHighScoreLabel =new Label(String.format("%6d", currentHighScore), new Label.LabelStyle(font, Color.WHITE));

        table.add(scoreLabel).expandX().padTop(10);
        table.add(highScoreLabel).expandX().padTop(10);
        table.row();
        table.add(yourScoreLabel).expandX();
        table.add(currentHighScoreLabel).expandX();

        stage.addActor(table);
    }

    public void update(float delta) {
        yourScoreLabel.setText(String.format("%6d", score));
    }

    public void render(SpriteBatch sb) {


    }

}
