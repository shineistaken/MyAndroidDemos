package com.example.pvz.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.types.CGPoint;

import com.example.pvz.base.AttackPlant;
import com.example.pvz.base.BaseElement.DieListener;
import com.example.pvz.base.Bullet;
import com.example.pvz.base.Plant;
import com.example.pvz.base.Zombies;

public class FightLine {
	private List<Zombies> zombieList = new ArrayList<Zombies>();
	private Map<Integer, Plant> plantsMap = new HashMap<Integer, Plant>();// 管理添加的植物
	private int lineNum;
	private List<AttackPlant> attackPlants = new ArrayList<AttackPlant>();
	private CGPoint position;
	private List<Bullet> bullets;

	public int getlineNum() {
		return lineNum;
	}

	public FightLine(int lineNum) {
		this.lineNum = lineNum;
		CCScheduler.sharedScheduler()
				.schedule("attackPlant", this, 0.2f, false);
		CCScheduler.sharedScheduler()
				.schedule("creatBullet", this, 0.2f, false);
		CCScheduler.sharedScheduler().schedule("attackZombies", this, 0.1f,
				false);

	}

	// 1.添加僵尸
	public void addZombies(final Zombies zombies) {
		zombieList.add(zombies);
		zombies.setDieListener(new DieListener() {

			@Override
			public void die() {
				// 监听僵尸死亡
				zombieList.remove(zombies);

			}
		});
	}

	// 2.添加植物
	public void addPlant(final Plant plant) {
		plantsMap.put(plant.getRow(), plant);
		if (plant instanceof AttackPlant) {
			attackPlants.add((AttackPlant) plant);
		}
		plant.setDieListener(new DieListener() {

			@Override
			public void die() {
				// 监听植物死亡
				plantsMap.remove(plant.getRow());// 这里用的是map！不能直接remove植物对象
				if (plant instanceof AttackPlant) {
					// attackPlants.remove((AttackPlant)plant);
					attackPlants.remove(plant);
				}
			}
		});
	}

	public boolean containsRow(int row) {
		return plantsMap.containsKey(row);
		// 当前列是否添加过植物
	}

	// 3.僵尸攻击植物
	public void attackPlant(float t) {
		if (zombieList.size() > 0 && plantsMap.size() > 0) {
			// System.out.println("僵尸和植物数量："+zombieList.size()+"\n"+plants.size()
			// );
			for (Zombies zombie : zombieList) {
				CGPoint position = zombie.getPosition();
				int row = (int) (position.x / 46 - 1);// 计算僵尸行进到哪一列
				Plant plant = plantsMap.get(row);
				// System.out.println("我在这里：\n" +lineNum+ "\n"+row);
				if (plant != null) {
					zombie.attack(plant);
				}
			}
		}
	}

	// 4.植物攻击僵尸
	public void attackZombies(float t) {
		if (zombieList.size() > 0 && attackPlants.size() > 0) {
			for (Zombies zombie : zombieList) {
				position = zombie.getPosition();
				float left = position.x - 20;
				float right = position.x + 20;
				for (AttackPlant plant : attackPlants) {
					bullets = plant.getBullets();
					for (Bullet bullet : bullets) {
						float bulletPos = bullet.getPosition().x;
						if (bulletPos > left && bulletPos < right) {
							// 子弹处于碰撞检测区域
							zombie.attacked(bullet.getAttack());
							// bullet.removeSelf(); //有bug
							bullet.setAttack(0);
							bullet.setVisible(false);
						}
					}
				}
			}
		}
	}

	public void creatBullet(float t) {
		if (zombieList.size() > 0) {
			// 当前行上有僵尸
			for (AttackPlant attackPlant : attackPlants) {
				attackPlant.creatBullet();
			}
		}

	}

}
