package com.example.pvz.base;

public abstract class Plant extends BaseElement {

	protected int life;// 生命值
	protected int line; // 行号
	protected int row;// 列号

	public Plant(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
		setScale(0.65f);
		setAnchorPoint(0.5f, 0);
	}

	/**
	 * 当被攻击至生命力低于0时，自动销毁
	 * 
	 * @param attack
	 *            攻击力值
	 */
	public void attacked(int attack) {

		life -= attack;
		if (life <= 0) {
			destroy();
		}
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

}