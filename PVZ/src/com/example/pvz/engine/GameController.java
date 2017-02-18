package com.example.pvz.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccPointSprite;
import org.cocos2d.types.util.CGPointUtil;

import com.example.pvz.CommonUtils.CommonUtils;
import com.example.pvz.base.BaseElement;
import com.example.pvz.base.Zombies;
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

	public List<CGPoint> getRoadPoints() {
		return roadPoints;
	}

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
		// for (int i = 0; i < roadPoints.size(); i++) {
		// System.out.println("地图坐标:\n"+roadPoints.get(i).x+roadPoints.get(i).y);
		// }
		System.out.println("加载地图");
	}

	public void addZombies() {
		// TODO Auto-generated method stub
		Random random = new Random();
		int lineNum = random.nextInt(5);
		// 随机在一行上添加一个僵尸

		// CCSprite sprite =
		// CCSprite.sprite("image/zombies/zombies_1/walk/z_1_01.png");
		// sprite.setPosition(roadPoints.get(lineNum * 2));//设置僵尸开始行进的位置
		// move(sprite,roadPoints.get(lineNum * 2+1 ));
		// map.addChild(sprite);
		// System.out.println("添加僵尸");

		PrimaryZombies primaryZombies = new PrimaryZombies(
				roadPoints.get(lineNum * 2), roadPoints.get(lineNum * 2 + 1));
		System.out.println("行号："+lineNum);
		map.addChild(primaryZombies);
	}

	public void move(CCSprite sprite, CGPoint endPoint) {
		// TODO Auto-generated method stub
		CCAction animate = CommonUtils.getAnimate(
				"image/zombies/zombies_1/walk/z_1_%02d.png", 7, true);
		sprite.runAction(animate);
		float t = CGPointUtil.distance(sprite.getPosition(), endPoint) / 50;
		CCMoveTo moveTo = CCMoveTo.action(t, endPoint);
		CCSequence sequence = CCSequence.actions(moveTo,
				CCCallFunc.action(this, "endGame"));
		sprite.runAction(sequence);

	}

	public void endGame() {
		isStart = false;
	}

}
