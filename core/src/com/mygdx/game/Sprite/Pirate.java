package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.PirateBay;

public class Pirate extends Sprite {
	public World world;
	public Body b2body;
	
	public Pirate(World world) {
		this.world = world;
		definePirate();
	}
	
	public void definePirate() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(100 / PirateBay.PPM, 350 / PirateBay.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(20 / PirateBay.PPM);
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
	}

}
