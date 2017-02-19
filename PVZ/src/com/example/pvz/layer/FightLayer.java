package com.example.pvz.layer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.view.MotionEvent;

import com.example.pvz.CommonUtils.CommonUtils;
import com.example.pvz.bean.ShowPlant;
import com.example.pvz.bean.ShowZombies;
import com.example.pvz.engine.GameController;

public class FightLayer extends BaseLayer {
	private CCTMXTiledMap tiledMap;
	private List<CGPoint> zombiesPoints;
	private List<ShowPlant> showPlants;
	private CCSprite choose;// 表示可选植物框
	private CCSprite chose;// 已选植物框
	private List<ShowPlant> selectPlants = new CopyOnWriteArrayList<ShowPlant>();// 用来存放已经选好的植物，必须是単例
	private boolean isDel = false;
	private boolean isLock = false;
	private CGRect selcetPlantBox;
	private static final int availablePlantCount = 5;
	public static final int CHOSE_SPRITE_TAG = 0;
	private CCSprite startGame;
	private CCSprite ready;

	public FightLayer() {
		// TODO Auto-generated constructor stub
		loadMap();
		parserMap();
		showZombies();
		moveMap();
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

	private void showZombies() {
		// 展示僵尸
		for (int i = 0; i < zombiesPoints.size(); i++) {
			CGPoint cgPoint = zombiesPoints.get(i);
			ShowZombies showZombies = new ShowZombies();
			showZombies.setPosition(cgPoint);
			tiledMap.addChild(showZombies);
		}

	}

	private void moveMap() {
		// TODO Auto-generated method stub
		int x = -(int) Math
				.abs(winSize.width - tiledMap.getContentSize().width);
		CCMoveBy moveBy = CCMoveBy.action(2, ccp(x, 0));
		CCSequence sequence = CCSequence
				.actions(CCDelayTime.action(2), moveBy, CCDelayTime.action(2),
						CCCallFunc.action(this, "loadContainer"));
		//测试使用
		CCAction sequence2= CCSequence.actions(moveBy,
				CCCallFunc.action(this, "loadContainer"));
		tiledMap.runAction(sequence2);
	}

	public void loadContainer() {
		System.out.println("展示容器");
		chose = CCSprite.sprite("image/fight/chose/fight_chose.png");
		chose.setAnchorPoint(0, 1);
		chose.setPosition(0, winSize.height);// 设置位置是屏幕的左上角
		this.addChild(chose, 0, CHOSE_SPRITE_TAG);

		choose = CCSprite.sprite("image/fight/chose/fight_choose.png");
		choose.setAnchorPoint(0, 0);
		this.addChild(choose);

		loadPlants();
		startGame = CCSprite.sprite("image/fight/chose/fight_start.png");
		startGame.setPosition(choose.getContentSize().width / 2, 30);
		choose.addChild(startGame);
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
			return super.ccTouchesBegan(event);
		}
		CGRect chooseBox = choose.getBoundingBox();
		CGRect choseBox = chose.getBoundingBox();
		CGRect startGameBox = startGame.getBoundingBox();
		if (CGRect.containsPoint(chooseBox, cgPoint)) {
			// 选中了植物容器
			if (CGRect.containsPoint(startGameBox, cgPoint)) {
				// 点击了一起摇滚
				System.out.println("一起来摇滚");
				if (selectPlants.isEmpty()) {
					// 选了植物才能开始游戏
				} else {
					ready();
				}
			} else if (CGRect.containsPoint(chooseBox, cgPoint)
					&& selectPlants.size() < availablePlantCount && !isLock) {
				// 选择植物
				for (ShowPlant plant : showPlants) {
					CGRect boundingBox = plant.getShowSprite().getBoundingBox();
					if (CGRect.containsPoint(boundingBox, cgPoint)) {
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

			} else if (CGRect.containsPoint(choseBox, cgPoint)) {
				isDel = false;
				// 点击到了已选植物所在方框,处理反选植物逻辑
				for (ShowPlant plant : selectPlants) {
					selcetPlantBox = plant.getShowSprite().getBoundingBox();
					if (CGRect.containsPoint(selcetPlantBox, cgPoint)) {
						System.out.println("反选植物");
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
			}
		}

		return super.ccTouchesBegan(event);
	}

	private void ready() {
		// 开始游戏
		// 1.首先缩小植物卡片槽
		chose.setScale(0.65f);
		for (ShowPlant plant : selectPlants) {

			plant.getShowSprite().setScale(0.65f);// 因为父容器缩小了 子元素必须一起缩小

			plant.getShowSprite().setPosition(
					plant.getShowSprite().getPosition().x * 0.65f,
					plant.getShowSprite().getPosition().y

					+ (CCDirector.sharedDirector().getWinSize().height - plant

					.getShowSprite().getPosition().y) * 0.35f);// 重新计算坐标
			this.addChild(plant.getShowSprite());
		}
		// 2.移除选择植物的界面
		choose.removeSelf();
		// 3.移动地图
		int x = (int) (tiledMap.getContentSize().width - winSize.width);
		CCMoveBy moveBy = CCMoveBy.action(1, ccp(x, 0));
		CCSequence sequence = CCSequence.actions(CCDelayTime.action(1), moveBy,
				CCCallFunc.action(this, "preGame"));
		tiledMap.runAction(sequence);
	}

	public void preGame() {
		ready = CCSprite.sprite("image/fight/startready_01.png");
		ready.setPosition(winSize.width / 2, winSize.height / 2);
		this.addChild(ready);
		String format = "image/fight/startready_%02d.png";
		CCAction animate = CommonUtils.getAnimate(format, 3, false);
		CCSequence sequence = CCSequence.actions((CCAnimate) animate,
				CCCallFunc.action(this, "startGame"));
		ready.runAction(sequence);
	}

	public void startGame() {
		// 开始对战
		ready.removeSelf();
		GameController controller = GameController.getInstance();
		controller.startGame(tiledMap, selectPlants);
	}

}
