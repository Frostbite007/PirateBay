package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.PirateBay;
import com.mygdx.game.Screens.PlayScreen;

public class Slime extends Enemy{
	
	private float stateTime;
	private Animation walkAnimation;
	private Array<TextureRegion> frame;
	
	private int[] slimeWalkWidth = {64, 58, 48, 48, 48, 48};
	private int[] slimeWalkHight = {15, 18, 24, 24, 21, 18};
	
	private int walk = 0;

	public Slime(PlayScreen screen, float x, float y) {
		super(screen, x, y);
		frame = new Array<TextureRegion>();
		for(int i = 0; i < 5;i++) {
			frame.add(new TextureRegion(screen.getAtlas().findRegion("slime"), walk, 0, slimeWalkWidth[i], slimeWalkHight[i]));
			walk += slimeWalkWidth[i];
		}
		walkAnimation = new Animation(0.4f, frame);
		stateTime = 0;
		setBounds(getX(), getY(), 70 / PirateBay.PPM, 65 / PirateBay.PPM);
	}
	
	public void update(float dt) {
		stateTime += dt;
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(walkAnimation.getKeyFrame(stateTime, true));
	}

	@Override
	protected void defineEnemy() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(500 / PirateBay.PPM, 300 / PirateBay.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(20 / PirateBay.PPM);
		fdef.filter.categoryBits = PirateBay.ENEMY_BIT;
		fdef.filter.maskBits = PirateBay.GROUND_BIT | 
				PirateBay.COINS_BIT |
				PirateBay.ENEMY_BIT |
				PirateBay.FLOOR_BIT |
				PirateBay.PIRATE_BIT;
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
		
		PolygonShape head = new PolygonShape();
		Vector2[] vertice = new Vector2[4];
		vertice[0] = new Vector2(-15, 25).scl(1 / PirateBay.PPM);
		vertice[1] = new Vector2(15, 25).scl(1 / PirateBay.PPM);
		vertice[2] = new Vector2(-10, 18).scl(1 / PirateBay.PPM);
		vertice[3] = new Vector2(10, 15).scl(1 / PirateBay.PPM);
		head.set(vertice);
		
		fdef.shape = head;
		fdef.restitution = 0.5f;
		fdef.filter.categoryBits = PirateBay.ENEMY_HEAD_BIT;
		b2body.createFixture(fdef).setUserData(this);;
	}

}
