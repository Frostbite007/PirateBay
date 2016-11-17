package com.mygdx.game.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.PirateBay;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;


public class Coins extends InteractiveTileObject {
	public Coins(PlayScreen screen, Rectangle bounds) {
		super(screen, bounds);
		fixture.setUserData(this);
		setCategoryFilter(PirateBay.COINS_BIT);
	}

	@Override
	public void onHeadHit() {
		Gdx.app.log("Coins", "Collision");
		PirateBay.manager.get("audio/coin.wav", Sound.class).play();
		setCategoryFilter(PirateBay.DESTROYED_BIT);
		getCell().setTile(null);
		Hud.addScore(100);
	}

	@Override
	public void onFrontHit() {
		Gdx.app.log("Coins", "Collision");
		PirateBay.manager.get("audio/coin.wav", Sound.class).play();
		setCategoryFilter(PirateBay.DESTROYED_BIT);
		getCell().setTile(null);
		Hud.addScore(100);
	}

	@Override
	public void onBottomHit() {
		Gdx.app.log("Coins", "Collision");
		PirateBay.manager.get("audio/coin.wav", Sound.class).play();
		setCategoryFilter(PirateBay.DESTROYED_BIT);
		getCell().setTile(null);
		Hud.addScore(100);
	}

	@Override
	public void onBackHit() {
		Gdx.app.log("Coins", "Collision");
		PirateBay.manager.get("audio/coin.wav", Sound.class).play();
		setCategoryFilter(PirateBay.DESTROYED_BIT);
		getCell().setTile(null);
		Hud.addScore(100);
	}

}
