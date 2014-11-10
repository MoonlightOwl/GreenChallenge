package com.totoro.challenge.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ShortArray;
import com.totoro.challenge.Assets;
import com.totoro.challenge.Settings;

import java.util.ArrayList;

/**
 * Challenge.Level
 * Created by MoonlightOwl on 11/10/2014.
 * ---
 * Loads *.tmx map, adapts it and generates Box2D bodies.
 */

public class Level {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private ArrayList<PolygonSprite> polyList;

    public Level(String filename, SpriteBatch batch, World world){
        map = new TmxMapLoader().load(filename);
        renderer = new OrthogonalTiledMapRenderer(map, 1f/Settings.TILE_SIZE, batch);

        // set tile statics
        TiledMapTileLayer tlayer = (TiledMapTileLayer)map.getLayers().get("Tiles");
        for(int x=0; x<tlayer.getWidth(); x++){
            for(int y=0; y<tlayer.getHeight(); y++){
                TiledMapTileLayer.Cell cell = tlayer.getCell(x, y);
                if(cell != null){
                    System.out.println(cell.getTile().getId());
                    BodyDef def = new BodyDef();
                    def.position.set(0.5f+x, 0.5f+y);
                    Body body = world.createBody(def);
                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(0.5f, 0.5f);
                    body.createFixture(shape, 1);
                    shape.dispose();
                }
            }
        }

        // load polygons
        Texture tex = Assets.manager.get("textures/dirt1.png", Texture.class);
        tex.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        EarClippingTriangulator triangulator = new EarClippingTriangulator();
        MapLayer layer = map.getLayers().get("Objects");
        MapObjects objects = layer.getObjects();

        polyList = new ArrayList<PolygonSprite>();
        for(PolygonMapObject polyObject: objects.getByType(PolygonMapObject.class)){
            Polygon poly = polyObject.getPolygon();
            ShortArray triangles = triangulator.computeTriangles(poly.getVertices());
            PolygonRegion polyReg = new PolygonRegion(new TextureRegion(tex), poly.getVertices(), triangles.items);

            PolygonSprite sprite = new PolygonSprite(polyReg);
            sprite.setPosition(poly.getX(), poly.getY());
            polyList.add(sprite);

            // add body & scale polygon for Box2D units
            BodyDef def = new BodyDef();
            def.position.set(poly.getX()/Settings.TILE_SIZE, poly.getY()/Settings.TILE_SIZE);
            Body body = world.createBody(def);
            ChainShape shape = new ChainShape();
            poly.setPosition(0, 0);
            poly.setScale(1/Settings.TILE_SIZE, 1/Settings.TILE_SIZE);
            shape.createLoop(poly.getTransformedVertices());
            body.createFixture(shape, 1);
            shape.dispose();
        }
    }

    public void draw(OrthographicCamera camera, PolygonSpriteBatch shr){
        renderer.setView(camera);
        renderer.render();

        shr.begin();
        for(PolygonSprite poly: polyList) {
            poly.draw(shr);
        }
        shr.end();
    }

    public void dispose(){
        map.dispose();
        renderer.dispose();
    }
}
