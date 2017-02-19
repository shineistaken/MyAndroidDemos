package com.example.pvz.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.cocos2d.actions.CCProgressTimer;
import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.util.CGPointUtil;

import com.example.pvz.CommonUtils.CommonUtils;
import com.example.pvz.base.Plant;
import com.example.pvz.bean.Nut;
import com.example.pvz.bean.PeasePlant;
import com.example.pvz.bean.PrimaryZombies;
import com.example.pvz.bean.ShowPlant;
import com.example.pvz.layer.FightLayer;

public class GameController {
	private static List<FightLine> lines;
	static {
		lines = new ArrayList<FightLine>();
		for (int i = 0; i < 5; i++) {
			FightLine fightLine = new FightLine(i);
			lines.add(fightLine);
		}
	}
	private CCTMXTiledMap map;

	private GameController() {
	}

	public static boolean isStart; // 游戏开始
	private static GameController controller = new GameController();
	private List<CGPoint> roadPoints;
	private List<ShowPlant> selectPlants;
	private ShowPlant selectPlant;// 要安放的植物
	private Plant installPlant;

	public List<CGPoint> getRoadPoints() {
		return roadPoints;
	}

	public static GameController getInstance() {
		return controller;
	}

	public void handlePoint(CGPoint point) {
		CCSprite chose = (CCSprite) map.getParent().getChildByTag(
				FightLayer.CHOSE_SPRITE_TAG);
		if (CGRect.containsPoint(chose.getBoundingBox(), point)) {
			// 选中卡槽
			if (selectPlant != null) {
				// 植物已经被安放过了，等CD
				selectPlant.getShowSprite().setOpacity(255);
				selectPlant = null;
			}
			for (ShowPlant plant : selectPlants) {
				CGRect boundingBox = plant.getShowSprite().getBoundingBox();
				if (CGRect.containsPoint(boundingBox, point)) {
					// 选择了植物
					selectPlant = plant;
					selectPlant.getShowSprite().setOpacity(150);
					int id = selectPlant.getId();
					switch (id) {
					case 1:
						installPlant = new PeasePlant();
						break;
					case 4:
						installPlant = new Nut();
						break;
					default:
						break;
					}
				}
			}
		} else {
			// 安放植物
			if (selectPlant != null) {
				int row = (int) (point.x / 46) - 1;
				int line = (int) ((CCDirector.sharedDirector().getWinSize().height - point.y) / 54) - 1;

				// 限制安放的植物的范围
				if (row >= 0 && row <= 8 && line >= 0 && line <= 4) {

					// 安放植物
					// selectPlant.getShowSprite().setPosition(point);
					// installPlant.setPosition(point);
					installPlant.setLine(line);// 设置植物的行号
					installPlant.setRow(row); // 设置植物的列号
					System.out.println("坐标：" + towers[line][row] + "\n" + line
							+ "\n" + row);
					installPlant.setPosition(towers[line][row]); // 修正植物坐标
					// lines.get(line).addPlant(installPlant);
					FightLine fightLine = lines.get(line);
					if (!fightLine.containsRow(row)) {
						// 当前列没有添加过植物
						fightLine.addPlant(installPlant);
						map.addChild(installPlant);
					}

				}
				installPlant = null;
				selectPlant.getShowSprite().setOpacity(255);
				selectPlant = null;// 下次安装需要重新选择
			}
		}
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
		// addZombies();
		CCScheduler.sharedScheduler().schedule("addZombies", this, 2, false);

		// 4.植物攻击僵尸
		// 5.僵尸攻击植物
		// 4.5放在行战场上处理
		// 6.进度处理
		progressTimer();
	}

	CGPoint[][] towers = new CGPoint[5][9];
	private CCProgressTimer prgTimer;
	private int progress;

	private void loadMap() {
		roadPoints = CommonUtils.getMapPoints(map, "road");
		// for (int i = 0; i < roadPoints.size(); i++) {
		// System.out.println("地图坐标:\n"+roadPoints.get(i).x+roadPoints.get(i).y);
		// }
		System.out.println("加载地图");
		for (int i = 1; i <= 5; i++) {
			List<CGPoint> mapPoints = CommonUtils.getMapPoints(map,
					String.format("tower%02d", i));
			for (int j = 0; j < mapPoints.size(); j++) {
				towers[i - 1][j] = mapPoints.get(j);
			}
		}

	}

	public void addZombies(float t) {
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
		// System.out.println("僵尸出现的行号：" + lineNum);
		map.addChild(primaryZombies, 1);
		lines.get(lineNum).addZombies(primaryZombies);

		progress += 5;
		prgTimer.setPercentage(progress);
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

	public void progressTimer() {
		prgTimer = CCProgressTimer.progressWithFile("image/fight/progress.png");
		prgTimer.setPosition(
				CCDirector.sharedDirector().getWinSize().width - 80, 13);
		map.getParent().addChild(prgTimer);

		prgTimer.setScale(0.6f);
		prgTimer.setPercentage(0);
		prgTimer.setType(CCProgressTimer.kCCProgressTimerTypeHorizontalBarRL);// 设置从右往左的水平方向进度条

		CCSprite sprite = CCSprite.sprite("image/fight/flagmeter.png");
		sprite.setPosition(CCDirector.sharedDirector().getWinSize().width - 80,
				13);
		map.getParent().addChild(sprite);
		sprite.setScale(0.6f);
		CCSprite name = CCSprite
				.sprite("image/fight/FlagMeterLevelProgress.png");
		name.setPosition(CCDirector.sharedDirector().getWinSize().width - 80, 5);
		map.getParent().addChild(name);
		name.setScale(0.6f);
	}

	public void endGame() {
		isStart = false;
	}

}
