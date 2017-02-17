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

	/**
	 * 处理开始游戏后的业务逻辑
	 * 
	 * @param tiledMap
	 *            对战地图
	 * @param selectPlants
	 *            选择参与对战的植物
	 */
	public void startGame(CCTMXTiledMap tiledMap, List<ShowPlant> selectPlants) {
		isStart = true;
		// 1.解析地图
		// 2.安放植物
		// 3.添加僵尸
		addZombies();
		// 4.植物攻击僵尸
		// 5.僵尸攻击植物

	}

	public void addZombies() {
		// TODO Auto-generated method stub
		
	}

	public void endGame() {
		isStart = false;
	}

}
