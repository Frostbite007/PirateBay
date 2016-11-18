package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.PirateBay;
import com.mygdx.game.Sprite.Enemy;
import com.mygdx.game.Sprite.InteractiveTileObject;

public class WorldContactListener implements ContactListener{

	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
		
		if(fixA.getUserData() == "head" || fixB.getUserData() == "head") {
			Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
			Fixture object = head == fixA ? fixB : fixA;
			
			if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
				((InteractiveTileObject) object.getUserData()).onHeadHit();
			}
		}
		
		if(fixA.getUserData() == "front" || fixB.getUserData() == "front") {
			Fixture head = fixA.getUserData() == "front" ? fixA : fixB;
			Fixture object = head == fixA ? fixB : fixA;
			
			if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
				((InteractiveTileObject) object.getUserData()).onFrontHit();
			}
		}
		
		if(fixA.getUserData() == "bottom" || fixB.getUserData() == "bottom") {
			Fixture head = fixA.getUserData() == "bottom" ? fixA : fixB;
			Fixture object = head == fixA ? fixB : fixA;
			
			if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
				((InteractiveTileObject) object.getUserData()).onBottomHit();
			}
		}
	
		if(fixA.getUserData() == "back" || fixB.getUserData() == "back") {
			Fixture head = fixA.getUserData() == "back" ? fixA : fixB;
			Fixture object = head == fixA ? fixB : fixA;
			
			if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
				((InteractiveTileObject) object.getUserData()).onBackHit();
			}
		}
		switch (cDef) {
			case PirateBay.ENEMY_HEAD_BIT | PirateBay.PIRATE_BIT:
				if(fixA.getFilterData().categoryBits == PirateBay.ENEMY_HEAD_BIT) {
					((Enemy)fixA.getUserData()).hitOnHead();
				} else {
					((Enemy)fixB.getUserData()).hitOnHead();
				}
				break;
			case PirateBay.ENEMY_BIT | PirateBay.STAIR_BIT:
				if(fixA.getFilterData().categoryBits == PirateBay.ENEMY_BIT) {
					((Enemy)fixA.getUserData()).reverseVelocity(true, false);
				} else  {
					((Enemy)fixB.getUserData()).reverseVelocity(true, false);;
				}
				break;
			case PirateBay.PIRATE_BIT | PirateBay.ENEMY_BIT:
				Gdx.app.log("Pirate", "Die");
				break;
			case PirateBay.ENEMY_BIT | PirateBay.ENEMY_BIT:
				((Enemy)fixA.getUserData()).reverseVelocity(true, false);
				((Enemy)fixB.getUserData()).reverseVelocity(true, false);
				break;
		}
	}

	@Override
	public void endContact(Contact contact) {}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {}

}
