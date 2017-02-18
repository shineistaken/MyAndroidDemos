package com.example.pvz.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.types.CGPoint;

import com.example.pvz.CommonUtils.CommonUtils;
import com.example.pvz.bean.PrimaryZombies;
import com.example.pvz.bean.ShowPlant;

public class GameController {
	private CCTMXTiledMap map;

	private GameController() {
	}

	public static boolean isStart; // 游戏开始
	private static GameController controller = new GameController();
	private List<CGPoint> roadPoints;
	private List<ShowPlant> selectPlants;
	private int lineNum;

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
		this.map = tiledMap;
		this.selectPlants = selectPlants;
		// 1.加载地图
		loadMap();
		// 2.添加僵尸
		addZombies();
		// 3.安放植物
		// 4.植物攻击僵尸
		// 5.僵尸攻击植物
	}

	private void loadMap() {
		roadPoints = CommonUtils.getMapPoints(map, "road");
//		for (int i = 0; i < roadPoints.size(); i++) {
//			System.out.println("地图坐标:\n"+roadPoints.get(i).x+roadPoints.get(i).y);
//		}
		System.out.println("加载地图");
	}

	public void addZombies() {
		// TODO Auto-generated method stub
		Random random = new Random();
		lineNum = random.nextInt(5);
		// 随机在一行上添加一个僵尸
		PrimaryZombies primaryZombies = new PrimaryZombies(
				roadPoints.get(lineNum * 2), roadPoints.get(lineNum * 2+1 ));
		map.addChild(primaryZombies);
		System.out.println("添加僵尸");

	}

	public void endGame() {
		isStart = false;
	}

}
