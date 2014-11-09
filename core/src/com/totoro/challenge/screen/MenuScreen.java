package com.totoro.challenge.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.totoro.challenge.Assets;
import com.totoro.challenge.Challenge;
import com.totoro.challenge.Settings;
import com.totoro.challenge.screen.menu.Menu;

/**
 * Challenge.MenuScreen
 * Created by MoonlightOwl on 11/4/2014.
 * ---
 */

public class MenuScreen extends ScreenAdapter {
    private Challenge game;
    private OrthographicCamera cam;

    private Rectangle startBounds;
    private Vector3 touchPoint;
    private Menu menu;
    private int NEW_GAME = 1;
    private int SETTINGS = 2;
    private int EXIT_GAME = 3;

    // screens
    private GameScreen game_screen;
    private SettingsScreen editor_screen;

    // assets
    private BitmapFont font_title;
    private Texture tex_back;
    private ParticleEffect smoke;

    public MenuScreen(Challenge game){
        this.game = game;

        cam = new OrthographicCamera(Settings.WIDTH, Settings.HEIGHT);
        cam.position.set(Settings.WIDTH/2, Settings.HEIGHT/2, 0);

        startBounds = new Rectangle(0, 180, Settings.WIDTH, 40);
        touchPoint = new Vector3();

        // assets
        font_title = Assets.manager.get("font_title.ttf", BitmapFont.class);
        BitmapFont font_menu = Assets.manager.get("font_menu.ttf", BitmapFont.class);

        tex_back = Assets.manager.get("textures/backshade.png", Texture.class);
        tex_back.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        smoke = Assets.manager.get("particles/smoke1.p", ParticleEffect.class);
        smoke.setPosition(Settings.WIDTH/2-400, -50);
        smoke.start();

        // menu
        menu = new Menu(font_menu);
        menu.setPosition(Settings.WIDTH/2, 350);
        menu.setSpacing(50);
        menu.setColors(Assets.YELLOWGREEN, Color.WHITE);

        menu.add("[ Пуск ]", NEW_GAME);
        menu.add("[ Настройки ]", SETTINGS);
        menu.add("[ Выход ]", EXIT_GAME);

        // screens
        game_screen = new GameScreen(game);
        game_screen.setMenuScreen(this);
        editor_screen = new SettingsScreen(game);
        editor_screen.setMenuScreen(this);
    }

    private void quit(){
        dispose();
        Gdx.app.exit();
    }
    public void update(){
        if(Gdx.input.isTouched()){
            cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if(menu.check(touchPoint) == NEW_GAME) {
                game.setScreen(game_screen);
            } else if(menu.check(touchPoint) == SETTINGS) {
                game.setScreen(editor_screen);
            } else if(menu.check(touchPoint) == EXIT_GAME){
                quit();
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            quit();
        }
    }

    public void draw(float delta){
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        smoke.draw(game.batch, delta);
        game.batch.draw(tex_back, 0, Settings.HEIGHT-240, Settings.WIDTH, 140);
        game.batch.draw(tex_back, 100, 60, Settings.WIDTH - 200, 420);
        font_title.drawMultiLine(game.batch, "Green Challenge",
                Settings.WIDTH / 2, Settings.HEIGHT - 150, 0, BitmapFont.HAlignment.CENTER);
        menu.draw(game.batch, Gdx.input.getX(), Settings.HEIGHT - Gdx.input.getY());
        game.batch.end();
    }

    @Override
    public void render(float delta){
        update();
        draw(delta);
    }

    @Override
    public void dispose(){
    }
}
