package com.example.pvz.layer;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.graphics.Point;
import android.view.MotionEvent;

import com.example.pvz.CommonUtils.CommonUtils;
import com.example.pvz.bean.ShowPlant;
import com.example.pvz.bean.ShowZombies;
import com.example.pvz.engine.GameController;

public class FightLayer extends BaseLayer {
	private static final int TAG_CHOSE = 0;
	private CCTMXTiledMap tiledMap;
	private List<CGPoint> zombiesPoints;
	private List<ShowPlant> showPlants;
	private CCSprite choose;// 表示可选植物框
	private CCSprite chose;// 已选植物框
	private CCSprite start;
	private List<ShowPlant> selectPlants = new CopyOnWriteArrayList<ShowPlant>();// 用来存放已经选好的植物，必须是単例
	private boolean isDel;
	private boolean isLock;
	private CGRect selcetPlantBox;
	private static final int availablePlantCount = 5;

	public FightLayer() {
		// TODO Auto-generated constructor stub
		loadMap();
		parserMap();
		showZombies();
		moveMap();
	}

	private void moveMap() {
		// TODO Auto-generated method stub
		int x = -(int) Math
				.abs(winSize.width - tiledMap.getContentSize().width);
		CCMoveBy moveBy = CCMoveBy.action(2, ccp(x, 0));
		CCSequence sequence = CCSequence
				.actions(CCDelayTime.action(2), moveBy, CCDelayTime.action(2),
						CCCallFunc.action(this, "loadContainer"));
		tiledMap.runAction(sequence);
	}

	public void loadContainer() {
		System.out.println("展示容器");
		chose = CCSprite.sprite("image/fight/chose/fight_chose.png");
		chose.setAnchorPoint(0, 1);
		chose.setPosition(0, winSize.height);// 设置位置是屏幕的左上角
		this.addChild(chose, 0, TAG_CHOSE);

		choose = CCSprite.sprite("image/fight/chose/fight_choose.png");
		choose.setAnchorPoint(0, 0);
		this.addChild(choose);

		loadPlants();
		start = CCSprite.sprite("image/fight/chose/fight_start.png");
		start.setPosition(choose.getContentSize().width / 2, 30);
		choose.addChild(start);
	}

	private void loadPlants() {
		// 展示植物
		showPlants = new ArrayList<ShowPlant>();
		for (int i = 1; i < 9; i++) {
			ShowPlant plant = new ShowPlant(i);
			// 背景精灵
			CCSprite bgSprite = plant.getBgSprite();
			bgSprite.setPosition(16 + ((i - 1) % 4) * 54,
					175 - ((i - 1) / 4 * 59));
			choose.addChild(bgSprite);

			// 展示精灵
			CCSprite showSprite = plant.getShowSprite();
			showSprite.setPosition(16 + ((i - 1) % 4) * 54,
					175 - ((i - 1) / 4 * 59));
			choose.addChild(showSprite);
			showPlants.add(plant);
		}
		setIsTouchEnabled(true);
	}

	public void unlock() {
		isLock = false;
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		// 开启触摸事件,处理植物选择逻辑
		CGPoint cgPoint = this.convertTouchToNodeSpace(event);
		// 游戏开始
		if (GameController.isStart) {
			GameController.getInstance().handlePoint(cgPoint);
		}
		CGRect chooseBox = choose.getBoundingBox();
		CGRect choseBox = chose.getBoundingBox();

		// 选择植物
		if (!isLock) {
			if (CGRect.containsPoint(chooseBox, cgPoint)
					&& selectPlants.size() < availablePlantCount) {

				for (ShowPlant plant : showPlants) {
					if (CGRect.containsPoint(plant.getShowSprite()
							.getBoundingBox(), cgPoint)) {
						// 点击到了可选植物所在的屏幕区域
						System.out.println("植物被选中");
						isLock = true;
						CCMoveTo moveTo = CCMoveTo.action(0.25f,
								ccp(75 + selectPlants.size() * 53, 255));
						CCSequence sequence = CCSequence.actions(moveTo,
								CCCallFunc.action(this, "unlock"));
						plant.getShowSprite().runAction(sequence);
						selectPlants.add(plant);
					}
				}
			}

			if (CGRect.containsPoint(choseBox, cgPoint)) {
				isDel = false;
				// 点击到了已选植物所在方框,处理反选植物逻辑
				for (ShowPlant plant : selectPlants) {
					selcetPlantBox = plant.getShowSprite().getBoundingBox();
					if (CGRect.containsPoint(selcetPlantBox, cgPoint)) {
						System.out.println("反选植物");
						isLock = true;
						CCMoveTo moveTo = CCMoveTo.action(0.25f, plant
								.getBgSprite().getPosition());
						plant.getShowSprite().runAction(moveTo);
						selectPlants.remove(plant);
						isDel = true;
						continue;
					}
					if (isDel) {
						CCMoveBy moveBy = CCMoveBy.action(0.1f, ccp(-53, 0));
						plant.getShowSprite().runAction(moveBy);
					}
				}
				unlock();
			}

		} else if (CGRect.containsPoint(start.getBoundingBox(), cgPoint)) {
			// 点击了一起摇滚
			ready();
		}

		return super.ccTouchesBegan(event);
	}

	private void ready() {
		// 开始游戏
//		1.首先缩小植物卡片栏
//		2.移除选择植物的界面
//		3.移动地图

	}

	private void showZombies() {
		// 展示僵尸
		for (int i = 0; i < zombiesPoints.size(); i++) {
			CGPoint cgPoint = zombiesPoints.get(i);
			ShowZombies showZombies = new ShowZombies();
			showZombies.setPosition(cgPoint);
			tiledMap.addChild(showZombies);
		}

	}

	private void loadMap() {
		tiledMap = CCTMXTiledMap.tiledMap("image/fight/map_day.tmx");
		tiledMap.setAnchorPoint(0.5f, 0.5f);
		CGSize contentSize = tiledMap.getContentSize();
		tiledMap.setPosition(contentSize.width / 2, contentSize.height / 2);
		this.addChild(tiledMap);

	}

	private void parserMap() {
		// 解析地图
		zombiesPoints = CommonUtils.getMapPoints(tiledMap, "zombies");
	}

}
