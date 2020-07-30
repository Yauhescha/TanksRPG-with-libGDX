package com.mygames.tanksrpg.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygames.tanksrpg.RunGame;
import com.mygames.tanksrpg.Weapon;
import com.mygames.tanksrpg.utils.Direction;
import com.mygames.tanksrpg.utils.TankOwner;

public class PlayerTank extends Tank {
	int lifes;
	public PlayerTank(RunGame game, TextureAtlas atlas) {
		super(game);
		this.ownerType=TankOwner.PLAYER;
		this.weapon = new Weapon(atlas);
		this.texture = atlas.findRegion("playerTankBase");
		this.textureHp=atlas.findRegion("bar");
		this.WIDTH = texture.getRegionWidth();
		this.HEIGHT = texture.getRegionHeight();
		this.position = new Vector2(300, 300);
		this.speed = 200;
		this.angle = 0;
		this.angleTurret = 0;
		this.hpMax = 10;
		this.hp = this.hpMax;
		this.circle=new Circle(position.x,position.y,(WIDTH+HEIGHT)/2);
		this.lifes = 5;
	}

	public void checkMovement(float dt) {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			move(Direction.LEFT,dt);
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			move(Direction.RIGHT,dt);
		} else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			move(Direction.UP,dt);
		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			move(Direction.DOWN,dt);
		}
	}

	public void update(float dt) {
		checkMovement(dt);
		
		float mx = Gdx.input.getX();
		float my = Gdx.graphics.getHeight() - Gdx.input.getY();
		rotateTurretToPoint(mx, my, dt);
		if (Gdx.input.isTouched())
			fire();
		super.update(dt);
	}

	@Override
	public void destroy() {
		lifes--;
		hp=hpMax;
	}
}
