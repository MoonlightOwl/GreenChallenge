package com.totoro.challenge.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.totoro.challenge.Assets;
import com.totoro.challenge.Challenge;
import com.totoro.challenge.Settings;
import com.totoro.challenge.level.Level;

/**
 * Challenge.GameScreen
 * Created by MoonlightOwl on 11/4/2014.
 * ---
 */

public class GameScreen extends ScreenAdapter {
    private Challenge game;
    private MenuScreen menu_screen;
    private OrthographicCamera cam, debug_cam;
    private PolygonSpriteBatch polybatch;

    // assets
    private BitmapFont font_base, font_menu;

    // world
    private World world;
    private Box2DDebugRenderer drenderer;
    private float physics_accum = 0;

    // level
    private Level level;


    public GameScreen(Challenge game) {
        this.game = game;

        cam = new OrthographicCamera(Settings.WIDTH, Settings.HEIGHT);
        cam.position.set(cam.viewportWidth/2, cam.viewportHeight/2, 0);
        debug_cam = new OrthographicCamera(Settings.WIDTH/Settings.TILE_SIZE, Settings.HEIGHT/Settings.TILE_SIZE);
        debug_cam.position.set(debug_cam.viewportWidth/2, debug_cam.viewportHeight/2, 0);

        // assets
        font_base = Assets.manager.get("font_base.ttf", BitmapFont.class);
        font_menu = Assets.manager.get("font_menu.ttf", BitmapFont.class);

        // world
        Box2D.init();
        drenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(0, -10), true);

        // test
        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(10, 10);

        // Create our body in the world using our body definition
        Body body = world.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(0.5f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.8f; // Make it bounce a little bit

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();

        // level
        level = new Level("levels/test.tmx",game.batch, world);
        polybatch = new PolygonSpriteBatch();
    }

    // set
    public void setMenuScreen(MenuScreen screen){
        menu_screen = screen;
    }

    public void update(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.setScreen(menu_screen);
        }
    }

    public void draw(){
        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        debug_cam.update();
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        font_base.draw(game.batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Settings.HEIGHT-20);
        game.batch.end();
        level.draw(debug_cam, polybatch);

        drenderer.render(world, debug_cam.combined);
    }
    private void doPhysicsStep(float deltaTime) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        physics_accum += frameTime;
        while (physics_accum >= Settings.TIME_STEP) {
            world.step(Settings.TIME_STEP, Settings.VELOCITY_ITERATIONS, Settings.POSITION_ITERATIONS);
            physics_accum -= Settings.TIME_STEP;
        }
    }

    @Override
    public void render(float delta){
        update();
        draw();
        doPhysicsStep(delta);
    }

    @Override
    public void dispose(){

    }
}
