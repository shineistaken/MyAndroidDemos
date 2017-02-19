package com.example.pvz.bean;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCNode;

import com.example.pvz.CommonUtils.CommonUtils;
import com.example.pvz.base.AttackPlant;
import com.example.pvz.base.Bullet;

public class PeasePlant extends AttackPlant {

	public PeasePlant() {
		super("image/plant/pease/p_2_01.png");
		baseAction();
	}

	@Override
	public void baseAction() {
		CCAction animate = CommonUtils.getAnimate(
				"image/plant/pease/p_2_%02d.png", 8, true);
		this.runAction(animate);
	}

	@Override
	public Bullet creatBullet() {
		if (bullets.size() < 1) {// 豌豆射手是单发的
			final Pease pease = new Pease();
			pease.setPosition(CCNode.ccp(this.getPosition().x + 20,
					this.getPosition().y + 40));
			bullets.add(pease);
			this.getParent().addChild(pease);
			pease.move();
			pease.setDieListener(new DieListener() {

				@Override
				public void die() {
					bullets.remove(pease);
				}
			});
		}
		return null;
	}

}
