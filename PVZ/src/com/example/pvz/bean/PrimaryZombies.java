package com.example.pvz.bean;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

import com.example.pvz.CommonUtils.CommonUtils;
import com.example.pvz.base.BaseElement;
import com.example.pvz.base.Plant;
import com.example.pvz.base.Zombies;

public class PrimaryZombies extends Zombies {

	Plant targetPlant;

	public PrimaryZombies(CGPoint startPoint, CGPoint endPoint) {
		super("image/zombies/zombies_1/walk/z_1_01.png");
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		setPosition(startPoint);//设置僵尸开始行进的位置
		move();
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
			Plant plant = (Plant) elt;
			if (targetPlant == null) {
				targetPlant = plant;
			}
		}

	}

	@Override
	public void attacked(int attack) {
		// TODO Auto-generated method stub

	}

	@Override
	public void baseAction() {
		// TODO Auto-generated method stub

	}

}
