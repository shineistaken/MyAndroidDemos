package com.example.pvz.base;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class DefancePlant extends Plant {
	public DefancePlant(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
		life = 200;
	}
}
