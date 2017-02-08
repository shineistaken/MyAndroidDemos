package com.example.pvz.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

import android.R.integer;

public class CommonUtils {
	public static void changeLayer(CCLayer ccLayer) {
		CCScene scene = CCScene.node();
		scene.addChild(ccLayer);
		CCFadeTransition transition = CCFadeTransition.transition(2, scene);
		CCDirector.sharedDirector().replaceScene(transition);
	}

	public static List<CGPoint> getMapPoints(CCTMXTiledMap tiledMap, String name) {
		List<CGPoint> points = new ArrayList<CGPoint>();
		CCTMXObjectGroup objectGroupNamed = tiledMap.objectGroupNamed(name);
		ArrayList<HashMap<String, String>> objects = objectGroupNamed.objects;
		for (HashMap<String, String> hashMap : objects) {
			int x = Integer.parseInt(hashMap.get("x"));
			int y = Integer.parseInt(hashMap.get("y"));
			CGPoint cgPoint = CCNode.ccp(x, y);
			points.add(cgPoint);

		}
		return points;
	}

	/**
	 * 
	 * @param format
	 * @return
	 */
	public static CCAction getAnimate(String format, int number,
			Boolean isRepeatForever) {
		ArrayList<CCSpriteFrame> frames = new ArrayList<CCSpriteFrame>();

		for (int i = 1; i <= number; i++) {
			CCSpriteFrame displayedFrame = CCSprite.sprite(
					String.format(format, i)).displayedFrame();
			frames.add(displayedFrame);
		}
		// 配置序列帧的信息 参数1 动作的名字(给程序员看的) 参数2 每一帧播放的时间 单位秒 参数3 所有用到的帧 CCAnimation
		CCAnimation anim = CCAnimation.animation("", 0.2f, frames);

		if (isRepeatForever) {
			CCAnimate animate = CCAnimate.action(anim);
			CCRepeatForever forever = CCRepeatForever.action(animate);
			return forever;
		} else {
			CCAnimate animate = CCAnimate.action(anim, false);
			return animate;
		}
	}

}
