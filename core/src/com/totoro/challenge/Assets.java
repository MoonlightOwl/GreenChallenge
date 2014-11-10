package com.totoro.challenge;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

/**
 * Challenge.Assets
 * Created by MoonlightOwl on 11/4/2014.
 * ---
 */

public class Assets {
    public static AssetManager manager;

    public static Color YELLOWGREEN;

    public static void load(){
        manager = new AssetManager();

        // fonts
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter font_params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        font_params.fontFileName = "fonts/MPlus.ttf";
        font_params.fontParameters.size = 24;
        font_params.fontParameters.characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
                "абвгдеёжзийклмнопрстуфхцчшщъыьэюя" +
                "[].,<>";
        manager.load("font_menu.ttf", BitmapFont.class, font_params);
        // important, this font needed for loading screen
        manager.finishLoading();

        font_params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        font_params.fontFileName = "fonts/MPlus.ttf";
        font_params.fontParameters.size = 40;
        font_params.fontParameters.characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
                "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        manager.load("font_title.ttf", BitmapFont.class, font_params);

        font_params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        font_params.fontFileName = "fonts/PTMono.ttf";
        font_params.fontParameters.size = 16;
        font_params.fontParameters.characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
                "абвгдеёжзийклмнопрстуфхцчшщъыьэюя" +
                "`1234567890-=\\,./~!@#$%^&*()_+|<>?[]{};':\"";
        manager.load("font_base.ttf", BitmapFont.class, font_params);

        // textures
        TextureParameter param = new TextureParameter();
        param.minFilter = Texture.TextureFilter.Linear;
        manager.load("textures/backshade.png", Texture.class, param);
        manager.load("skins/uiskin.atlas", TextureAtlas.class);

        manager.load("textures/dirt1.png", Texture.class);

        // particle effects
        manager.load("particles/smoke1.p", ParticleEffect.class);

        // colors
        YELLOWGREEN = new Color(0xADD63EFF);
    }

    public static void dispose(){
        manager.dispose();
    }
}
