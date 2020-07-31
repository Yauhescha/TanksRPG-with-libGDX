package com.mygames.tanksrpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.mygames.tanksrpg.units.BotTank;
import com.mygames.tanksrpg.units.PlayerTank;
import com.mygames.tanksrpg.units.Tank;

public class GameScreen implements Screen{
	private SpriteBatch batch;
	private PlayerTank player;
	private Map map;
	private BulletEmmiter bulletEmmiter;
	private BotEmmiter botEmmiter;
	private TextureAtlas atlas;
	private BitmapFont font24;
	private Stage stage;
	private boolean paused;
	private float gameTimer;

	private static final boolean FRIENDLY_FIRE = false;
	
	public GameScreen(SpriteBatch batch) {
		this.batch = batch;
	}
	
	@Override
	public void show() {
		font24 = new BitmapFont(Gdx.files.internal("font24.fnt"));
		atlas = new TextureAtlas("game.pack.pack");
		map = new Map(atlas);
		player = new PlayerTank(this, atlas);
		botEmmiter = new BotEmmiter(this, atlas);
		bulletEmmiter = new BulletEmmiter(atlas);
		gameTimer = 10;
		stage = new Stage();

		Skin skin = new Skin();
		skin.add("simpleButton", new TextureRegion(atlas.findRegion("SimpleButton")));
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("simpleButton");
		buttonStyle.font = font24;

		Group group = new Group();
		group.setPosition(1110, 620);
		TextButton pauseButton = new TextButton("Pause", buttonStyle);
		TextButton exitButton = new TextButton("Exit", buttonStyle);
		
		pauseButton.setPosition(0, 40);
		exitButton.setPosition(0, 0);
		
		pauseButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				paused = !paused;
			}
		});
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		group.addActor(pauseButton);
		group.addActor(exitButton);
		stage.addActor(group);
		Gdx.input.setInputProcessor(stage);		
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0, 0.6f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		map.render(batch);
		player.render(batch);
		botEmmiter.render(batch);
		bulletEmmiter.render(batch);
		player.renderHUD(batch, font24);
		batch.end();

		stage.draw();		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
	
	
	public void update(float dt) {
		if (paused)
			return;
		gameTimer += dt;
		if (gameTimer > 5.0f) {
			gameTimer = 0.0f;
			float xCoord, yCoord;
			do {
				xCoord = MathUtils.random(0, Gdx.graphics.getWidth());
				yCoord = MathUtils.random(0, Gdx.graphics.getHeight());
			} while (!map.isAreaClear(xCoord, yCoord, 20));
			botEmmiter.activate(xCoord, yCoord);
		}
		player.update(dt);
		botEmmiter.update(dt);
		bulletEmmiter.update(dt);
		checkCollision();
		stage.act(dt);
	}
	public void checkCollision() {
		for (int i = 0; i < bulletEmmiter.getBullets().length; i++) {
			Bullet bullet = bulletEmmiter.getBullets()[i];
			if (bullet.isActive()) {
				// check bot damage
				for (int j = 0; j < botEmmiter.getEnemies().length; j++) {
					BotTank bot = botEmmiter.getEnemies()[j];
					if (bot.isActive()) {
						if (checkBullerOwner(bot, bullet) && bot.getCircle().contains(bullet.getPosition())) {
							bullet.deactivate();
							bot.takeDamage(bullet.getDamage());
							break;
						}
					}
				}
				// check player damage
				if (checkBullerOwner(player, bullet) && player.getCircle().contains(bullet.getPosition())) {
					bullet.deactivate();
					player.takeDamage(bullet.getDamage());
				}
				map.checkWallAndBulletCollision(bullet);
			}
		}
	}
	
	public boolean checkBullerOwner(Tank tank, Bullet bullet) {
		if (!FRIENDLY_FIRE) {
			return tank.getOwnerType() != bullet.getOwner().getOwnerType();
		} else {
			return tank != bullet.getOwner();
		}
	}
	
	
	
	
	

	public BulletEmmiter getBulletEmmiter() {
		return bulletEmmiter;
	}
	public PlayerTank getPlayer() {
		return player;
	}

	public Map getMap() {
		return map;
	}


}
