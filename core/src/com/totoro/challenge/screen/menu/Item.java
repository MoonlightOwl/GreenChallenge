package com.totoro.challenge.screen.menu;

import com.badlogic.gdx.math.Rectangle;

/**
 * Challenge.Item
 * Created by MoonlightOwl on 11/6/2014.
 * ---
 */

public class Item {
    public String text;
    public int id;
    public Rectangle bound;

    public Item(String text, int id){
        this.text = text;
        this.id = id;
    }
    public Item(String text, int id, Rectangle bound){
        this(text, id);
        this.bound = bound;
    }
}