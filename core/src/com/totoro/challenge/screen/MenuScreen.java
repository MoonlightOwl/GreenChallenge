package com.totoro.challenge.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.totoro.challenge.Assets;
import com.totoro.challenge.Challenge;
import com.totoro.challenge.Settings;

/**
 * Challenge.MenuScreen
 * Created by MoonlightOwl on 11/4/2014.
 * ---
 */

public class MenuScreen extends ScreenAdapter {
    private Challenge game;
    private OrthographicCamera cam;

    //private RayHandler rayHandler;
    private Rectangle startBounds;
    private Vector3 touchPoint;

    // assets
    private BitmapFont font_title, font_menu;

    public MenuScreen(Challenge game){
        this.game = game;

        cam = new OrthographicCamera(Settings.WIDTH, Settings.HEIGHT);
        cam.position.set(Settings.WIDTH/2, Settings.HEIGHT/2, 0);

        //rayHandler = new RayHandler(null);
        //rayHandler.setShadows(false);
        startBounds = new Rectangle(0, 180, Settings.WIDTH, 40);
        touchPoint = new Vector3();

        // assets
        font_title = Assets.manager.get("font_title.ttf", BitmapFont.class);
        font_menu = Assets.manager.get("font_menu.ttf", BitmapFont.class);
        font_menu.setColor(Assets.YELLOWGREEN);

        game.setScreen(new GameScreen(game));
        System.out.println(game.getScreen().toString());
    }

    public void update(){
        if(Gdx.input.isTouched()){
            cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if(startBounds.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        }
    }

    public void draw(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        font_title.drawMultiLine(game.batch, "Green Challenge",
                Settings.WIDTH/2, Settings.HEIGHT-150, 0, BitmapFont.HAlignment.CENTER);
        font_menu.drawMultiLine(game.batch, "[ Пуск ]",
                Settings.WIDTH/2, 200, 0, BitmapFont.HAlignment.CENTER);
        game.batch.end();
    }

    @Override
    public void render(float delta){
        update();
        draw();
    }

    @Override
    public void dispose(){
        //rayHandler.dispose();
    }
}
