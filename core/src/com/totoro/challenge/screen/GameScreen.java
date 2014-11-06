package com.totoro.challenge.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.totoro.challenge.Assets;
import com.totoro.challenge.Challenge;
import com.totoro.challenge.Settings;

/**
 * Challenge.GameScreen
 * Created by MoonlightOwl on 11/4/2014.
 * ---
 */

public class GameScreen extends ScreenAdapter {
    private Challenge game;
    private OrthographicCamera cam;

    // assets
    private BitmapFont font_base, font_menu;

    public GameScreen(Challenge game) {
        this.game = game;

        cam = new OrthographicCamera(Settings.WIDTH, Settings.HEIGHT);
        cam.position.set(Settings.WIDTH/2, Settings.HEIGHT/2, 0);

        // assets
        font_base = Assets.manager.get("font_base.ttf", BitmapFont.class);
        font_menu = Assets.manager.get("font_menu.ttf", BitmapFont.class);
        font_menu.setColor(Assets.YELLOWGREEN);
    }

    public void update(){

    }

    public void draw(){
        Gdx.gl.glClearColor(0, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();

        game.batch.end();
    }

    @Override
    public void render(float delta){
        update();
        draw();
    }

    @Override
    public void dispose(){

    }
}
