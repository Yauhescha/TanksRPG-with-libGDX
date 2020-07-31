package com.mygames.tanksrpg;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Map {

	public enum WallType {
		HARD(0, 5, true, false,false), 
		SOFT(1, 3, true, false,false), 
		INDESTRUCTIBLE(2, 1, false, false,false), 
		WATER(3, 1, false,false,true), 
		NONE(0, 0, false,true,true);

		int index;
		int maxHp;
		boolean destructible;
		boolean isUnitPossible;
		boolean isProjectilePossible;

		private WallType(int index, int maxHp, boolean destructible, boolean isUnitPossible,
				boolean isProjectilePossible) {
			this.index = index;
			this.maxHp = maxHp;
			this.destructible = destructible;
			this.isUnitPossible = isUnitPossible;
			this.isProjectilePossible = isProjectilePossible;
		}

	}

	private class Cell {
		WallType type;
		int hp;

		public Cell(WallType type) {
			super();
			this.type = type;
			this.hp = type.maxHp;
		}

		public void damage() {
			if (type.destructible) {
				hp--;
				if (hp <= 0)
					type = WallType.NONE;
			}
		}

		public void changeType(WallType type) {
			this.type = type;
			this.hp = type.maxHp;
		}
	}

	public static final int SIZE_X = 64;
	public static final int SIZE_Y = 36;

	public static final int CELL_SIZE = 20;

	private TextureRegion grassTexture;
	private TextureRegion[][] wallsTexture;
	private Cell cells[][];

	public Map(TextureAtlas atlas) {
		this.wallsTexture = new TextureRegion(atlas.findRegion("obstacles")).split(CELL_SIZE, CELL_SIZE);
		this.grassTexture = atlas.findRegion("grass40");
		this.cells = new Cell[SIZE_X][SIZE_Y];
		for (int i = 0; i < SIZE_X; i++)
			for (int j = 0; j < SIZE_Y; j++) {
				cells[i][j] = new Cell(WallType.NONE);
				int cx = (int) (i / 4);
				int cy = (int) (j / 4);
				if (cx % 2 == 0 && cy % 2 == 0)
					if (Math.random() < 0.8)
						this.cells[i][j].changeType(WallType.WATER);
					else
						this.cells[i][j].changeType(WallType.SOFT);
				;
			}
		for (int i = 0; i < SIZE_X; i++) {
			cells[i][0].changeType(WallType.INDESTRUCTIBLE);
			cells[i][SIZE_Y - 1].changeType(WallType.INDESTRUCTIBLE);
		}
	}

	public void render(SpriteBatch batch) {
		for (int i = 0; i < 1280 / 40; i++)
			for (int j = 0; j < 720 / 40; j++)
				batch.draw(grassTexture, i * 40, j * 40);

		for (int i = 0; i < SIZE_X; i++)
			for (int j = 0; j < SIZE_Y; j++) {
				if (cells[i][j].type != WallType.NONE)
					batch.draw(wallsTexture[cells[i][j].type.index][cells[i][j].hp - 1], i * CELL_SIZE, j * CELL_SIZE);
			}
	}

	public void checkWallAndBulletCollision(Bullet bullet) {
		int cx = (int) (bullet.getPosition().x / CELL_SIZE);
		int cy = (int) (bullet.getPosition().y / CELL_SIZE);

		if (cx >= 0 && cy >= 0 && cx <= SIZE_X && cy <= SIZE_Y) {
			if (!cells[cx][cy].type.isProjectilePossible) {
				cells[cx][cy].damage();
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
		for (int i = xLeft; i <= xRight; i++)
			for (int j = yBottom; j <= yTop; j++) {
				if (!cells[i][j].type.isUnitPossible)
					return false;
			}

		return true;
	}
}
