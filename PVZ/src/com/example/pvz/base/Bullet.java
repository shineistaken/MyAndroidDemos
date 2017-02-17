package com.example.pvz.base;

import android.R.integer;

public abstract class Bullet extends Product {
	protected int attack = 20;
	protected int speed = 100;

	public Bullet(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}

	public abstract void move();

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
