package com.mygdx.game.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.PirateBay;
import com.mygdx.game.Scenes.Hud;


public class Coins extends InteractiveTileObject {
	public Coins(World world, TiledMap map, Rectangle bounds) {
		super(world, map, bounds);
		fixture.setUserData(this);
		setCategoryFilter(PirateBay.COINS_BIT);
	}

	@Override
	public void onHeadHit() {
		Gdx.app.log("Coins", "Collision");
		setCategoryFilter(PirateBay.DESTROYED_BIT);
		getCell().setTile(null);
		Hud.addScore(100);
	}

	@Override
	public void onFrontHit() {
		Gdx.app.log("Coins", "Collision");
		setCategoryFilter(PirateBay.DESTROYED_BIT);
		getCell().setTile(null);
		Hud.addScore(100);
	}

	@Override
	public void onBottomHit() {
		Gdx.app.log("Coins", "Collision");
		setCategoryFilter(PirateBay.DESTROYED_BIT);
		getCell().setTile(null);
		Hud.addScore(100);
	}

	@Override
	public void onBackHit() {
		Gdx.app.log("Coins", "Collision");
		setCategoryFilter(PirateBay.DESTROYED_BIT);
		getCell().setTile(null);
		Hud.addScore(100);
	}

}
