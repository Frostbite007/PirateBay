package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.PirateBay;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprite.Enemy;
import com.mygdx.game.Sprite.Pirate;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.WorldContactListener;

public class PlayScreen implements Screen{
	private PirateBay game;
	private TextureAtlas atlas;
	
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Hud hud;
	
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	private World world;
	private Box2DDebugRenderer b2dr;
	private B2WorldCreator creator;
	
	private Pirate pirate;
	
	private Music music;
	
	public PlayScreen(PirateBay game) {
		atlas = new TextureAtlas("pirate&enemy.txt");
		this.game = game;
		
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(PirateBay.V_WIDTH / PirateBay.PPM, PirateBay.V_HEIGHT / PirateBay.PPM, gamecam);
		hud = new Hud(game.batch);
		
		maploader = new TmxMapLoader();
		map = maploader.load("stage1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / PirateBay.PPM);
		gamecam.position.set(gamePort.getScreenWidth() / 2 , gamePort.getWorldHeight()/2, 0);
		
		world = new World(new Vector2(0, -20), true);
		b2dr = new Box2DDebugRenderer();
		
		creator = new B2WorldCreator(this);
		
		pirate = new Pirate(this);
		
		
		world.setContactListener(new WorldContactListener());
		
		music = PirateBay.manager.get("audio/pirategamemusic.mp3", Music.class);
		music.setLooping(true);
		music.play();
		
		
	}
	
	public TextureAtlas getAtlas() {
		return atlas;
	}
	
	@Override
	public void show() {
		
	} 
	
	public void handleInput(float dt) {
		if (Gdx.input.isKeyJustPressed(Keys.UP)) {
			PirateBay.manager.get("audio/pirate_jump.mp3", Sound.class).play();
			pirate.b2body.applyLinearImpulse(new Vector2(0 , 5f), pirate.b2body.getWorldCenter(), true);
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT) && pirate.b2body.getLinearVelocity().x <=3) {
			pirate.b2body.applyLinearImpulse(new Vector2(0.7f, 0), pirate.b2body.getWorldCenter(), true);
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT) && pirate.b2body.getLinearVelocity().x >= -3) {
			pirate.b2body.applyLinearImpulse(new Vector2(-0.7f, 0), pirate.b2body.getWorldCenter(), true);
		}
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			pirate.attacking = true;
		}
	}
	
	public void update(float dt) {
		handleInput(dt);
		
		world.step(1/60f, 6, 2);
		
		pirate.update(dt);
		for(Enemy enemy : creator.getSlime()) {
			enemy.update(dt);
		}
		hud.update(dt);
		
		gamecam.position.x = pirate.b2body.getPosition().x;
		
		gamecam.update();
		renderer.setView(gamecam);
	}

	@Override
	public void render(float delta) {
		update(delta);
		
		Gdx.gl.glClearColor(0,  0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.render();
		b2dr.render(world, gamecam.combined);
		
		game.batch.setProjectionMatrix(gamecam.combined);
		game.batch.begin();
		pirate.draw(game.batch);
		for(Enemy enemy : creator.getSlime()) {
			enemy.draw(game.batch);
		}
		game.batch.end();
		
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
		
	}
	
	public TiledMap getMap() {
		return map;
	}
	
	public World getWorld() {
		return world;
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2dr.dispose();
		hud.dispose();
		
	}

}
