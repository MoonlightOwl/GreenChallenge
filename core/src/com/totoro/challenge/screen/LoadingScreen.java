package com.totoro.challenge.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.totoro.challenge.Assets;
import com.totoro.challenge.Challenge;
import com.totoro.challenge.Settings;

/**
 * Challenge.LoadingScreen
 * Created by MoonlightOwl on 11/4/2014.
 * ---
 */

public class LoadingScreen extends ScreenAdapter{
    private Challenge game;
    private OrthographicCamera cam;

    // loading screen assets
    private BitmapFont font_menu;

    public LoadingScreen(Challenge game){
        this.game = game;
    }

    @Override
    public void show(){
        cam = new OrthographicCamera(Settings.WIDTH, Settings.HEIGHT);
        cam.position.set(Settings.WIDTH/2, Settings.HEIGHT/2, 0);

        Assets.load();
        font_menu = Assets.manager.get("font_menu.ttf", BitmapFont.class);
        font_menu.setColor(Assets.YELLOWGREEN);

        int width = Gdx.graphics.getWidth();
    }

    private void update(){
        if(Assets.manager.update()){
            game.setScreen(new MenuScreen(game));
        }
    }
    private void draw(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        font_menu.drawMultiLine(game.batch, "Загрузка...",
                Settings.WIDTH/2, Settings.HEIGHT/2,
                0, BitmapFont.HAlignment.CENTER);
        game.batch.end();
    }

    @Override
    public void render(float delta){
        update();
        draw();
    }

    @Override
    public void hide(){
    }
}
