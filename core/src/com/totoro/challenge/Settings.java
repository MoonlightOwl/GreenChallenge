package com.totoro.challenge;

/**
 * Challenge.Settings
 * Created by MoonlightOwl on 11/4/2014.
 * ---
 */

public class Settings {
    public static int WIDTH, HEIGHT;
    public static boolean FULLSCREEN;

    public static void load(){
        WIDTH = 1024; HEIGHT = 768;
        FULLSCREEN = false;
    }
}
