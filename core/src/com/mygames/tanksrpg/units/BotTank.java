package com.mygames.tanksrpg.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygames.tanksrpg.GameScreen;
import com.mygames.tanksrpg.Weapon;
import com.mygames.tanksrpg.utils.Direction;
import com.mygames.tanksrpg.utils.TankOwner;

public class BotTank extends Tank {
	Direction prefferedDirection;
	Vector3 lastPosition;

	float aiTimer;
	float aiTimerTo;
	boolean active;
	float pursuitRadius;

	public BotTank(GameScreen gameScreen, TextureAtlas atlas) {
		super(gameScreen);
		this.ownerType = TankOwner.AI;
		this.weapon = new Weapon(atlas);
		this.texture = atlas.findRegion("botTankBase");
		this.textureHp = atlas.findRegion("bar");
		this.WIDTH = texture.getRegionWidth();
		this.HEIGHT = texture.getRegionHeight();
		this.position = new Vector2(500, 500);
		this.speed = 200;
		this.hpMax = 10;
		this.hp = this.hpMax;
		this.aiTimerTo = 2.0f;
		this.prefferedDirection = prefferedDirection.UP;
		this.active = false;
		this.pursuitRadius = 200.0f;
		this.circle = new Circle(position.x, position.y, (WIDTH + HEIGHT) / 2);
		lastPosition = new Vector3(0.0f, 0.0f, 0.0f);
		
	}

	public void update(float dt) {
		aiTimer += dt;
		if (aiTimer >= aiTimerTo) {
			aiTimer = 0.0f;
			aiTimerTo = MathUtils.random(1f, 4.0f);
			prefferedDirection = Direction.values()[MathUtils.random(0, Direction.values().length - 1)];
			angle = prefferedDirection.getAngle();
		}
		move(prefferedDirection, dt);
		float dst = this.position.dst(gameScreen.getPlayer().getPosition());
		if (dst < pursuitRadius) {
			rotateTurretToPoint(gameScreen.getPlayer().getPosition().x, gameScreen.getPlayer().getPosition().y, dt);
			fire();
		}
		
		if(Math.abs(lastPosition.x-position.x)<0.5f && 
				Math.abs(lastPosition.y-position.y)<0.5f ) {
			lastPosition.z+=dt;
			if(lastPosition.z>0.3)
			aiTimer+=10;
		}else {
			lastPosition.x=position.x;
			lastPosition.y=position.y;
			lastPosition.z=0.0f;
		}
		
		super.update(dt);
	}

	public void activate(float x, float y) {
		hpMax = 3;
		hp = hpMax;
		prefferedDirection = Direction.values()[MathUtils.random(0, Direction.values().length - 1)];
		position.set(x, y);
		aiTimer = 0.0f;
		active = true;
		angle = prefferedDirection.getAngle();
	}

	public boolean isActive() {
		return active;
	}

	@Override
	public void destroy() {
		active = false;
	}

}
