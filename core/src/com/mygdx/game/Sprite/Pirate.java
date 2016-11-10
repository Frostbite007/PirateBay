package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.PirateBay;
import com.mygdx.game.Screens.PlayScreen;

public class Pirate extends Sprite {
	public enum State { FALLING, JUMPING, STANDING, RUNNING, ATTACKING};
	public State currentState;
	public State previousState;
	public World world;
	public Body b2body;
	private TextureRegion pirateStand;
	private Animation pirateRun;
	private Animation pirateJump;
	private Animation pirateAttack;
	private float stateTimer;
	private boolean runningRight;
	
	private int[] pirateRunWidth = {67, 65, 64, 64, 64, 64};
	private int[] pirateRunHeight = {73, 75, 76, 74, 75, 75};
	private int[] pirateStandWidth = {66, 66, 66, 65};
	private int[] pirateStandHeight = {79, 79, 78, 77};
	private int[] pirateAttackWidth = {67, 61, 62, 100, 100, 68};
	private int[] pirateAttackHeight = {78, 75, 76, 85, 64, 68};
	private int[] pirateJumpWidth = {77, 77, 75, 75, 68};
	private int[] pirateJumpHeight = {92, 93, 91, 91, 68};
	private int[] pirateDieWidth = {52, 53, 75, 68};
	private int[] pirateDieHeight = {81, 81, 88, 68};

	private int run = 0;
	private int attack = 0;
	private int stand = 0;
	private int jump = 0;
	private int die = 0;
	
	public boolean attacking = false;
	
	public Pirate(World world, PlayScreen screen) {
		super(screen.getAtlas().findRegion("pirate"));
		this.world = world;
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		runningRight = true;
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i = 0;i < 6 ; i++) {
			frames.add(new TextureRegion(getTexture(), 1504 + run, 0, pirateRunWidth[i], pirateRunHeight[i]));
			run += pirateRunWidth[i];
		}
		pirateRun = new Animation(0.1f, frames);
		frames.clear();
		
		for(int i = 0;i < 5 ; i++) {
			frames.add(new TextureRegion(getTexture(), 869 + jump, 0, pirateJumpWidth[i], pirateJumpHeight[i]));
			jump += pirateJumpWidth[i];
		}
		pirateJump = new Animation(0.1f, frames);
		frames.clear();
		
		for(int i = 0;i < 6 ; i++) {
			frames.add(new TextureRegion(getTexture(), 0 + attack, 0, pirateAttackWidth[i], pirateAttackHeight[i]));
			attack += pirateAttackWidth[i];
		}
		pirateAttack = new Animation(0.1f, frames);
		frames.clear();
			
				
		pirateStand = new TextureRegion(getTexture(), 1241 + pirateStandWidth[0], 0, pirateStandWidth[1], pirateRunHeight[1]);
		
		definePirate();
		setBounds(0, 0, pirateStandWidth[0] / PirateBay.PPM,  pirateStandHeight[0] / PirateBay.PPM);
		setRegion(pirateStand);
	}
	
	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(getFrames(dt));
	}
	
	public TextureRegion getFrames(float dt) {
		currentState = getState();
		
		TextureRegion region;
		switch(currentState) {
			case JUMPING:
				region = pirateJump.getKeyFrame(stateTimer);
				break;
			case RUNNING:
				region = pirateRun.getKeyFrame(stateTimer, true);
				break;
			case ATTACKING:
				region = pirateAttack.getKeyFrame(stateTimer, true);
			case FALLING:
			case STANDING:
			default:
				region = pirateStand;
				break;
		}
		
		if((b2body.getLinearVelocity().x > 0 || runningRight) && !region.isFlipX()) {
			region.flip(true, false);
			runningRight = true;
		} else if ((b2body.getLinearVelocity().x < 0 || !runningRight) && region.isFlipX()) {
			region.flip(true, false);
			runningRight = false;
		}
		
		stateTimer = currentState == previousState ? stateTimer + dt : 0;
		previousState = currentState;
		return region;
	}
	
	public State getState() {
		if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
			return State.JUMPING;
		} else if (b2body.getLinearVelocity().y < 0) {
			return State.FALLING;
		} else if (b2body.getLinearVelocity().x != 0) {
			return State.RUNNING;
		}  else if (attacking) {
			attacking = false;
			return State.ATTACKING;
		} else {
			return State.STANDING;
		}
	}
	
	public void definePirate() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(100 / PirateBay.PPM, 350 / PirateBay.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(35 / PirateBay.PPM);
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
	}

}
