package com.totoro.challenge;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.totoro.challenge.screen.LoadingScreen;

public class Challenge extends Game {
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

        Settings.load();
        Gdx.graphics.setDisplayMode(Settings.WIDTH, Settings.HEIGHT, Settings.FULLSCREEN);

        setScreen(new LoadingScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

    @Override
    public void dispose(){
        Assets.dispose();
        batch.dispose();
    }
}
