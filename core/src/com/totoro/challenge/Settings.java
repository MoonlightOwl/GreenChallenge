package com.totoro.challenge;

/**
 * Challenge.Settings
 * Created by MoonlightOwl on 11/4/2014.
 * ---
 */

public class Settings {
    // graphics
    public static int WIDTH, HEIGHT;
    public static boolean FULLSCREEN;
    public static float TILE_SIZE = 48;

    // physics
    public static float TIME_STEP = 1/45f;
    public static int VELOCITY_ITERATIONS = 6;
    public static int POSITION_ITERATIONS = 2;

    public static void load(){
        WIDTH = 1024; HEIGHT = 768;
        FULLSCREEN = false;
    }
}
