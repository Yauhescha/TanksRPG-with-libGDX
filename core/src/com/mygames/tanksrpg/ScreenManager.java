package com.mygames.tanksrpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ScreenManager {

	public enum ScreenType {
		MENU, GAME
	}

	private static ScreenManager outInstance = new ScreenManager();
	public static final int WORD_WIDHT = 1280;
	public static final int WORD_HEIGHT = 720;
	private Game game;
	private GameScreen gameScreen;
	private Viewport viewport;
	private Camera camera;

	private ScreenManager() {
	}

	public void init(Game game, SpriteBatch batch) {
		this.game = game;
		this.gameScreen = new GameScreen(batch);
		this.camera = new OrthographicCamera(WORD_WIDHT, WORD_HEIGHT);
		this.camera.position.set(WORD_WIDHT/2, WORD_HEIGHT/2,0);
		this.camera.update();
		
		this.viewport = new FitViewport(WORD_WIDHT, WORD_HEIGHT, camera);
	}
	public void resize(int width, int height) {
		viewport.update(width, height);
		viewport.apply();
	}

	public void SetScreen(ScreenType screenType) {
		Screen currentScreen = game.getScreen();
		switch (screenType) {
		case GAME:
			game.setScreen(gameScreen);
			break;
		}
		if (currentScreen != null)
			currentScreen.dispose();
	}

	public static ScreenManager getInstance() {
		return outInstance;
	}

	public Viewport getViewport() {
		return viewport;
	}

	public Camera getCamera() {
		return camera;
	}
	
}
