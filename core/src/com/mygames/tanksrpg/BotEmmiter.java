package com.mygames.tanksrpg;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygames.tanksrpg.units.BotTank;

public class BotEmmiter {
	private BotTank[] enemies;
	
	public static final int MAX_BOTS = 10;
	

	public BotEmmiter(GameScreen gameScreen, TextureAtlas atlas) {
		this.enemies = new BotTank[MAX_BOTS];
		
		for(int i=0; i<MAX_BOTS; i++)
			this.enemies[i]=new BotTank(gameScreen,atlas);
	}
	
	public void render(SpriteBatch batch) {
		for(int i=0; i<MAX_BOTS; i++) {
			if(this.enemies[i].isActive()){
				this.enemies[i].render(batch);
			}
		}
	}
	public void update(float dt) {
		for(int i=0; i<MAX_BOTS; i++) {
			if(this.enemies[i].isActive()){
				this.enemies[i].update(dt);;
			}
		}
	}
	

	public void activate(float x, float y) {
		for(int i=0; i<MAX_BOTS; i++) {
			if(!this.enemies[i].isActive()){
				this.enemies[i].activate(x, y);
				break;
			}
		}
	}

	public BotTank[] getEnemies() {
		return enemies;
	}
	
	
}
