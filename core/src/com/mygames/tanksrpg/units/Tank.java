package com.mygames.tanksrpg.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygames.tanksrpg.GameScreen;
import com.mygames.tanksrpg.RunGame;
import com.mygames.tanksrpg.Weapon;
import com.mygames.tanksrpg.utils.Direction;
import com.mygames.tanksrpg.utils.TankOwner;
import com.mygames.tanksrpg.utils.Utils;

public abstract class Tank {
	GameScreen gameScreen;
	TankOwner ownerType;
	TextureRegion texture;
	TextureRegion textureHp;
	Weapon weapon;
	Vector2 position;
	Vector2 tmp;
	Circle circle;

	int hp;
	int hpMax;
	float fireTimer;
	float speed;
	float angle;
	float angleTurret;

	int WIDTH;
	int HEIGHT;

	public Tank(GameScreen game) {
		this.gameScreen = game;
		this.tmp = new Vector2(0, 0);
	}

	public void render(SpriteBatch batch) {
		batch.draw(texture, position.x - WIDTH / 2, position.y - HEIGHT / 2, WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT, 1, 1,
				angle);
		batch.draw(weapon.getTexture(), position.x - WIDTH / 2, position.y - HEIGHT / 2, WIDTH / 2, HEIGHT / 2, WIDTH,
				HEIGHT, 1, 1, angleTurret);
		if (hp < hpMax) {
			batch.setColor(0, 0, 0, 0.8f);
			batch.draw(textureHp, position.x - WIDTH / 2 - 2, position.y + HEIGHT / 2 - 8 - 2, 44, 12);
			batch.setColor(1, 0, 0, 0.8f);
			batch.draw(textureHp, position.x - WIDTH / 2, position.y + HEIGHT / 2 - 8, ((float) hp / hpMax) * 40, 8);
			batch.setColor(1, 1, 1, 1);
		}
	}

	public void update(float dt) {
		fireTimer += dt;
		if (position.x < 0)
			position.x = 0;
		if (position.y < 0)
			position.y = 0;
		if (position.x > Gdx.graphics.getWidth())
			position.x = Gdx.graphics.getWidth();
		if (position.y > Gdx.graphics.getHeight())
			position.y = Gdx.graphics.getHeight();
		circle.setPosition(position);
	}

	public void rotateTurretToPoint(float pointX, float pointY, float dt) {
		float angleTo = Utils.getAngle(position.x, position.y, pointX, pointY);

		angleTurret = Utils.makeRotation(angleTurret, angleTo, 2700, dt);
		angleTurret = Utils.angleToFromNegPiToPosPi(angleTurret);
	}

	public void takeDamage(int damage) {
		hp -= damage;
		if (hp <= 0)
			destroy();
	}

	public abstract void destroy();

	public void fire() {

		if (fireTimer > weapon.getFirePeriod()) {
			fireTimer = 0.0f;
			float angleRad = (float) Math.toRadians(angleTurret);
			gameScreen.getBulletEmmiter().activate(this, position.x, position.y,
					weapon.getProjectileSpeed() * (float) Math.cos(angleRad), 320 * (float) Math.sin(angleRad),
					weapon.getDamage(), weapon.getProjectileTimeLife());

		}
	}

	public void move(Direction direction, float dt) {
		tmp.set(position);
		tmp.add(speed * direction.getVx() * dt, speed * direction.getVy() * dt);
		if (gameScreen.getMap().isAreaClear(tmp.x, tmp.y, WIDTH / 2)) {
			angle = direction.getAngle();
			position.set(tmp);
		}
	}

	public Circle getCircle() {
		return circle;
	}

	public Vector2 getPosition() {
		return position;
	}

	public TankOwner getOwnerType() {
		return ownerType;
	}

}
