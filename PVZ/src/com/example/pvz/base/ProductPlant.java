package com.example.pvz.base;

public abstract class ProductPlant extends BaseElement {

	public ProductPlant(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 生产类型植物可以产出阳光、金币等
	 */
	public abstract void create();
}
