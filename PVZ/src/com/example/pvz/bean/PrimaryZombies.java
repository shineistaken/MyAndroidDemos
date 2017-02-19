package com.example.pvz.bean;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

import com.example.pvz.CommonUtils.CommonUtils;
import com.example.pvz.base.AttackPlant;
import com.example.pvz.base.BaseElement;
import com.example.pvz.base.Plant;
import com.example.pvz.base.Zombies;

/**
 * @author Administrator
 * 
 */
public class PrimaryZombies extends Zombies {

	Plant targetPlant;

	public PrimaryZombies(CGPoint startPoint, CGPoint endPoint) {
		super("image/zombies/zombies_1/walk/z_1_01.png");
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		setPosition(startPoint);// 设置僵尸开始行进的位置
		move();
		attack = 50;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		CCAction animate = CommonUtils.getAnimate(
				"image/zombies/zombies_1/walk/z_1_%02d.png", 7, true);
		this.runAction(animate);
		float t = CGPointUtil.distance(getPosition(), endPoint) / speed;
		CCMoveTo moveTo = CCMoveTo.action(t, endPoint);
		CCSequence sequence = CCSequence.actions(moveTo,
				CCCallFunc.action(this, "endGame"));
		this.runAction(sequence);
	}

	public void endGame() {
		this.destroy();
	}

	@Override
	public void attack(BaseElement elt) {
		// TODO Auto-generated method stub

		if (elt instanceof Plant) {
			if (targetPlant == null) {
				targetPlant = (Plant) elt;
				stopAllActions();// 停下所有动作，开始攻击
				CCAction animate = CommonUtils.getAnimate(
						"image/zombies/zombies_1/attack/z_1_attack_%02d.png",
						10, true);
				this.runAction(animate);
				CCScheduler.sharedScheduler().schedule("attackPlant", this,
						0.5f, false);// 造成伤害

			}
		}

	}

	public void attackPlant(float t) {
		targetPlant.attacked(attack);
		if (targetPlant.getLife() <= 0) {
			CCScheduler.sharedScheduler().unschedule("attackPlant", this);
			targetPlant = null;
			stopAllActions();
			move();
		}
	}

	@Override
	public void attacked(int attack) {
		life -= attack;
		if (life <= 0) {
			destroy();
		}
	}

	@Override
	public void baseAction() {
		// TODO Auto-generated method stub

	}

}
