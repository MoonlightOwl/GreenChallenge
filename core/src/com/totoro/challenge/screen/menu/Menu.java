package com.totoro.challenge.screen.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * Challenge.Menu
 * Created by MoonlightOwl on 11/6/2014.
 * ---
 */

public class Menu {
    private BitmapFont font;
    private ArrayList<Item> items;
    private Color color, color_selected;
    private Vector2 position;
    private int spacing;

    public Menu(BitmapFont font){
        this(font, Color.WHITE);
    }
    public Menu(BitmapFont font, Color color){
        this.font = font;
        items = new ArrayList<Item>();
        position = new Vector2(0, 0);
        setColors(color, Color.YELLOW);
        setSpacing(50);
    }

    private void calculateBound(Item item){
        float width = font.getBounds(item.text).width;
        item.bound = new Rectangle(position.x-width/2, position.y-spacing*item.id,
                width, font.getLineHeight());
    }
    private void recalculateBounds(){
        for(Item item: items){ calculateBound(item); }
    }

    public void add(String text, int id){
        Item item = new Item(text, id);
        calculateBound(item);
        items.add(item);
    }
    public void remove(int id){
        for(Item item: items){
            if(item.id == id){
                items.remove(item);
                break;
            }
        }
        recalculateBounds();
    }

    // set
    public void setColors(Color color, Color selected){
        this.color = color; this.color_selected = selected;
    }
    public void setFont(BitmapFont font){ this.font = font; }
    public void setPosition(int x, int y){
        position.set(x, y); recalculateBounds();
    }
    public void setSpacing(int spacing){
        this.spacing = spacing; recalculateBounds();
    }
    // get
    public Vector2 getPosition(){ return position; }


    public int check(Vector3 click){
        for(Item item: items){
            if(item.bound.contains(click.x, click.y)){
                return item.id;
            }
        }
        return -1;
    }
    public void draw(SpriteBatch batch, int mx, int my){
        for(Item item: items){
            if(item.bound.contains(mx, my)){
                font.setColor(color_selected);
            } else{
                font.setColor(color);
            }
            font.drawMultiLine(batch, item.text,
                    position.x, item.bound.y+30, 0, BitmapFont.HAlignment.CENTER);
        }
    }
}
