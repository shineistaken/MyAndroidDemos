package com.example.pvz.engine;

import org.cocos2d.types.CGPoint;

public class GameController {
	private GameController() {
	}

	public static boolean isStart; // 游戏开始
	private static GameController controller = new GameController();

	public static GameController getInstance() {
		return controller;
	}

	public void handlePoint(CGPoint cgPoint) {
		// TODO Auto-generated method stub
		
	}

}
