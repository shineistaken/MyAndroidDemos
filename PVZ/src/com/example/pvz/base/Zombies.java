package com.example.pvz.base;

import org.cocos2d.types.CGPoint;


public abstract class Zombies extends BaseElement {

	protected int life=50;
	protected int attack=10;
	protected int speed=20;
	protected CGPoint startPoint;
	protected CGPoint endPoint;

	public Zombies(String filepath) {
		super(filepath);
		setScale(0.5f);
		setAnchorPoint(0.5f, 0);
	}

	/**
	 * 移动僵尸
	 */
	public abstract void move();
	/**
	 * 僵尸攻击
	 * @param elt 攻击对象 可以是植物也可以是僵尸
	 */
	public abstract void attack(BaseElement elt) ;
	/**
	 * 被攻击
	 * @param attack 攻击力
	 */
	public abstract void attacked(int attack);
}
