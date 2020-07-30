package com.mygames.tanksrpg;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Weapon {
	private TextureRegion texture;
	private float radius;
	private float fireTimer;
	private float firePeriod;
	private int damage;
	private float projectileSpeed;
	private float projectileTimeLife;
	


	public float getRadius() {
		return radius;
	}

	public Weapon(TextureAtlas atlas) {
		this.texture = atlas.findRegion("simpleWeapon");
		this.firePeriod = 0.2f;
		this.damage = 1;
		this.radius=320.0f;
		this.projectileSpeed=320.0f;
		this.projectileTimeLife=this.radius/this.projectileSpeed;
	}

	public float getFirePeriod() {
		return firePeriod;
	}

	public TextureRegion getTexture() {
		return texture;
	}

	public int getDamage() {
		return damage;
	}

	public float getProjectileSpeed() {
		return projectileSpeed;
	}

	public float getProjectileTimeLife() {
		return projectileTimeLife;
	}
	
}
