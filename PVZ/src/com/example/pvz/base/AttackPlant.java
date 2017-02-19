package com.example.pvz.base;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AttackPlant extends Plant {
	protected List<Bullet> bullets = new CopyOnWriteArrayList<Bullet>();

	public AttackPlant(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}

	public abstract Bullet creatBullet();

	public List<Bullet> getBullets() {
		return bullets;
	}

}
