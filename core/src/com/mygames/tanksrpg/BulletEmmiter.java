package com.mygames.tanksrpg;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygames.tanksrpg.units.Tank;

public class BulletEmmiter {
	private Bullet[] bullets;
	private TextureRegion texture;
	
	public static final int MAX_BULLET = 500;
	

	public BulletEmmiter(TextureAtlas atlas) {
		this.texture = atlas.findRegion("projectile");
		this.bullets = new Bullet[MAX_BULLET];
		
		for(int i=0; i<MAX_BULLET; i++)
			this.bullets[i]=new Bullet(atlas);
	}
	
	public void render(SpriteBatch batch) {
		for(int i=0; i<MAX_BULLET; i++) {
			if(this.bullets[i].isActive()){
				this.bullets[i].render(batch);
			}
		}
	}
	public void update(float dt) {
		for(int i=0; i<MAX_BULLET; i++) {
			if(this.bullets[i].isActive()){
				this.bullets[i].update(dt);;
			}
		}
	}
	
	public TextureRegion getTexture() {
		return texture;
	}

	public void activate(Tank owner, float x, float y, float vx, float vy, int damage, float maxTime) {
		for(int i=0; i<MAX_BULLET; i++) {
			if(!this.bullets[i].isActive()){
				this.bullets[i].activate(owner,x, y, vx, vy,damage, maxTime);
				break;
			}
		}
	}

	public Bullet[] getBullets() {
		return bullets;
	}
	
	
}
