package com.mygames.tanksrpg;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Map {
	public static final int SIZE_X = 64;
	public static final int SIZE_Y = 36;

	public static final int CELL_SIZE = 20;

	private TextureRegion grassTexture;
	private TextureRegion wallTexture;
	private int obstacles[][];

	public Map(TextureAtlas atlas) {
		this.grassTexture = atlas.findRegion("grass40");
		this.wallTexture = atlas.findRegion("block");
		this.obstacles = new int[SIZE_X][SIZE_Y];
		for (int i = 0; i < SIZE_X; i++)
			for (int j = 0; j < SIZE_Y; j++) {
				int cx=(int)(i/4);
				int cy=(int)(j/4);
				if(cx%2==0 && cy%2==0)
					this.obstacles[i][j] = 5;
			}
				
	}

	public void render(SpriteBatch batch) {
		for (int i = 0; i < 1280 / 40; i++)
			for (int j = 0; j < 720 / 40; j++)
				batch.draw(grassTexture, i * 40, j * 40);

		for (int i = 0; i < SIZE_X; i++)
			for (int j = 0; j < SIZE_Y; j++) {
				if (obstacles[i][j] > 0)
					batch.draw(wallTexture, i * CELL_SIZE, j * CELL_SIZE);
			}
	}

	public void checkWallAndBulletCollision(Bullet bullet) {
		int cx = (int) (bullet.getPosition().x / CELL_SIZE);
		int cy = (int) (bullet.getPosition().y / CELL_SIZE);

		if (cx >= 0 && cy >= 0 && cx <= SIZE_X && cy <= SIZE_Y) {
			if (obstacles[cx][cy] > 0) {
				obstacles[cx][cy] -= bullet.getDamage();
				bullet.deactivate();
			}
		}
	}

	public boolean isAreaClear(float x, float y, float halfSize) {
		int xLeft = (int) ((x - halfSize) / CELL_SIZE);
		int xRight = (int) ((x + halfSize) / CELL_SIZE);
		int yTop = (int) ((y + halfSize) / CELL_SIZE);
		int yBottom = (int) ((y - halfSize) / CELL_SIZE);

		if (xLeft < 0)
			xLeft = 0;
		if (xRight >= SIZE_X)
			xRight = SIZE_X - 1;
		if (yBottom < 0)
			yBottom = 0;
		if (yTop >= SIZE_Y)
			yTop = SIZE_Y - 1;
		for(int i=xLeft; i<=xRight;i++)
			for(int j=yBottom; j<=yTop;j++) {
				if(obstacles[i][j]>0)
					return false;
			}
			
		return true;
	}
}
