package com.totoro.challenge.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.totoro.challenge.Assets;
import com.totoro.challenge.Challenge;
import com.totoro.challenge.Settings;

/**
 * Challenge.SettingsScreen
 * Created by MoonlightOwl on 11/8/2014.
 * ---
 */

public class SettingsScreen extends ScreenAdapter {
    private Challenge game;
    private OrthographicCamera cam;
    // debug
    private ShapeRenderer shrenderer;

    // scene
    private Skin skin;
    private Stage stage;
    private Table root_table;
    private TextButton button;

    // assets
    private BitmapFont font_base, font_menu;

    // screens
    private MenuScreen menu_screen;

    public SettingsScreen(Challenge game) {
        this.game = game;

        cam = new OrthographicCamera(Settings.WIDTH, Settings.HEIGHT);
        cam.position.set(Settings.WIDTH/2, Settings.HEIGHT/2, 0);

        shrenderer = new ShapeRenderer();

        // assets
        font_base = Assets.manager.get("font_base.ttf", BitmapFont.class);
        font_menu = Assets.manager.get("font_menu.ttf", BitmapFont.class);

        // build scene
        skin = new Skin();
        skin.addRegions(Assets.manager.get("skins/uiskin.atlas", TextureAtlas.class));
        skin.add("default-font", font_base);
        skin.load(Gdx.files.internal("skins/uiskin.json"));

        stage = new Stage(new ScreenViewport(cam), game.batch);
        root_table = new Table();
        root_table.setFillParent(true);
        root_table.debug();
        stage.addActor(root_table);

        button = new TextButton("Click Me", skin);
        root_table.add(button);
    }

    // set
    public void setMenuScreen(MenuScreen screen){
        menu_screen = screen;
    }

    public void update(float delta){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.setScreen(menu_screen);
        }
        stage.act(delta);
    }

    public void draw(float delta){
        Gdx.gl.glClearColor(0.2f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        shrenderer.begin(ShapeRenderer.ShapeType.Line);
        root_table.drawDebug(shrenderer);
        shrenderer.end();
    }

    @Override
    public void render(float delta){
        update(delta);
        draw(delta);
    }

    @Override
    public void dispose(){
        stage.dispose();
        shrenderer.dispose();
    }
}
