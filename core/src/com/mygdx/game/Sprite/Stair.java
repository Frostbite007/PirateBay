package com.mygdx.game.Sprite;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.PirateBay;
import com.mygdx.game.Screens.PlayScreen;

public class Stair extends InteractiveTileObject {

	public Stair(PlayScreen screen, Rectangle bounds) {
		super(screen, bounds);
		fixture.setUserData(this);
		setCategoryFilter(PirateBay.STAIR_BIT);
	}

	@Override
	public void onHeadHit() {}

	@Override
	public void onFrontHit() {}

	@Override
	public void onBottomHit() {}

	@Override
	public void onBackHit() {}
}
