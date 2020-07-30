package com.mygames.tanksrpg.utils;

public enum Direction {
	UP(0, 1, 90), DOWN(0, -1, 270), LEFT(-1, 0, 180), RIGHT(1, 0, 0);

	private int vx;
	private int vy;
	private float angle;

	public int getVx() {
		return vx;
	}

	public int getVy() {
		return vy;
	}

	private Direction(int vx, int vy, float angle) {
		this.vx = vx;
		this.vy = vy;
		this.angle = angle;
	}

	public float getAngle() {
		return angle;
	}

}
