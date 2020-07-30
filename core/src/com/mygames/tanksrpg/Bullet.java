package com.mygames.tanksrpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygames.tanksrpg.units.Tank;

public class Bullet {
	private Tank owner;
	private TextureRegion texture;
	private Vector2 position;
	private Vector2 velocity;
	private boolean active;
	private int damage;
	private float currentTime;
	private float maxTime;

	public Bullet(TextureAtlas atlas) {
		this.texture = atlas.findRegion("projectile");
		this.velocity = new Vector2();
		this.position = new Vector2();
		this.active = false;
		this.damage = 0;
	}

	public boolean isActive() {
		return active;
	}

	public void render(SpriteBatch batch) {
		batch.draw(texture, position.x - 8, position.y - 8);
	}

	public void update(float dt) {
		position.mulAdd(velocity,dt);
		currentTime+=dt;
		if(currentTime>=maxTime)
			deactivate();
		if (position.x < 0 || position.x > Gdx.graphics.getWidth() || position.y < 0
				|| position.y > Gdx.graphics.getHeight())
			deactivate();

	}

	public void activate(Tank owner,float x, float y, float vx, float vy, int damage, float maxTime) {
		this.owner=owner;
		this.active = true;
		this.position.set(x,y);
		this.velocity.set(vx,vy);
		this.damage=damage;
		this.maxTime = maxTime;
		this.currentTime=0.0f;
	}

	public void deactivate() {
		this.active = false;
	}

	public int getDamage() {
		return damage;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Tank getOwner() {
		return owner;
	}
	
	
}
