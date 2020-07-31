package com.mygames.tanksrpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygames.tanksrpg.ScreenManager.ScreenType;
import com.mygames.tanksrpg.units.BotTank;
import com.mygames.tanksrpg.units.PlayerTank;
import com.mygames.tanksrpg.units.Tank;

public class RunGame extends Game {
	private SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		ScreenManager.getInstance().init(this, batch);
		ScreenManager.getInstance().SetScreen(ScreenType.GAME);
	}

	@Override
	public void render() {
		float dt = Gdx.graphics.getDeltaTime();
		getScreen().render(dt);

	}


	@Override
	public void dispose() {
		batch.dispose();
	}

	

	

}
