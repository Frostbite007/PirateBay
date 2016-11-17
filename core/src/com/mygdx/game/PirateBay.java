package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.PlayScreen;

public class PirateBay extends Game {
	public static final int V_WIDTH = 500;
	public static final int V_HEIGHT = 400;
	public static final float PPM = 100;

	public static final short GROUND_BIT = 1;
	public static final short PIRATE_BIT = 2;
	public static final short COINS_BIT = 4;
	public static final short DESTROYED_BIT = 8;
	public static final short FLOOR_BIT = 16;
	public static final short ENEMY_BIT = 32;
	public static final short ENEMY_HEAD_BIT = 64;
	
	
	public SpriteBatch batch;
	
	public static AssetManager manager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/pirategamemusic.mp3", Music.class);
		manager.load("audio/piratemainmenumusic.mp3", Music.class);
		manager.load("audio/pirate_jump.mp3", Sound.class);
		manager.load("audio/coin.wav", Sound.class);
		manager.finishLoading();
		
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		manager.dispose();
		batch.dispose();
	}
}
