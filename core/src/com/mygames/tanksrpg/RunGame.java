package com.mygames.tanksrpg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.mygames.tanksrpg.units.BotTank;
import com.mygames.tanksrpg.units.PlayerTank;
import com.mygames.tanksrpg.units.Tank;

public class RunGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private PlayerTank player;
	private Map map;
	private BulletEmmiter bulletEmmiter;
	private BotEmmiter botEmmiter;
	private TextureAtlas atlas;

	private float gameTimer;
	float dt;

	private static final boolean FRIENDLY_FIRE = false;

	@Override
	public void create() {
		atlas = new TextureAtlas("game.pack");
		batch = new SpriteBatch();
		map = new Map(atlas);
		player = new PlayerTank(this, atlas);
		botEmmiter = new BotEmmiter(this, atlas);
		bulletEmmiter = new BulletEmmiter(atlas);
		gameTimer = 10;
	}

	@Override
	public void render() {
		dt = Gdx.graphics.getDeltaTime();
		update(dt);
		Gdx.gl.glClearColor(0, 0.6f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		map.render(batch);
		player.render(batch);
		botEmmiter.render(batch);
		bulletEmmiter.render(batch);
		batch.end();
	}

	public void update(float dt) {
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
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	public BulletEmmiter getBulletEmmiter() {
		return bulletEmmiter;
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

	public PlayerTank getPlayer() {
		return player;
	}

	public Map getMap() {
		return map;
	}

}
