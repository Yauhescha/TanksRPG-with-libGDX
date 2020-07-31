package com.mygames.tanksrpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenManager {

	public enum ScreenType {
		MENU, GAME
	}

	private static ScreenManager outInstance = new ScreenManager();

	private Game game;
	private GameScreen gameScreen;

	private ScreenManager() {
	}

	public void init(Game game, SpriteBatch batch) {
		this.game = game;
		this.gameScreen = new GameScreen(batch);
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
}
