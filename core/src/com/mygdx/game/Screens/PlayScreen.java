package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.PirateBay;
import com.mygdx.game.Scenes.Hud;

public class PlayScreen implements Screen{
	private PirateBay game;
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Hud hud;
	
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	public PlayScreen(PirateBay game){
		this.game = game;
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(PirateBay.V_WIDTH, PirateBay.V_HEIGHT, gamecam);
		hud = new Hud(game.batch);
		maploader = new TmxMapLoader();
		map = maploader.load("stage1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		gamecam.position.set(gamePort.getScreenWidth()/2, gamePort.getWorldHeight()/2, 0);
	}

	@Override
	public void show() {
		
	} 
	
	public void handleInput(float dt) {
		if (Gdx.input.isTouched()) {
			gamecam.position.x += 300 * dt;
		}
	}
	
	public void update(float dt) {
		handleInput(dt);
		
		gamecam.update();
		renderer.setView(gamecam);
	}

	@Override
	public void render(float delta) {
		update(delta);
		
		Gdx.gl.glClearColor(0,  0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.render();
		
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
