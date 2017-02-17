package com.example.pvz.engine;

import java.util.List;

import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.types.CGPoint;

import com.example.pvz.bean.ShowPlant;

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

	public void startGame(CCTMXTiledMap tiledMap, List<ShowPlant> selectPlants) {
		// TODO Auto-generated method stub
		
	}

}
