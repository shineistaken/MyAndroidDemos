package com.example.pvz.layer;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import com.example.pvz.CommonUtils.CommonUtils;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.MotionEvent;

public class WelcomeLayer extends BaseLayer {

	private CCSprite start;

	public WelcomeLayer() {
		// 构造函数
		new AsyncTask<Void,Void,Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				SystemClock.sleep(2000);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				System.out.println("后台加载完成了...");
				start.setVisible(true);
				setIsTouchEnabled(true);// 打开触摸事件
			}
		}.execute();
		init();

	}
	

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		// TODO Auto-generated method stub
		CGPoint point = this.convertTouchToNodeSpace(event);
		CGRect boundingBox = start.getBoundingBox();
		if (CGRect.containsPoint(boundingBox, point)) {
			
			CommonUtils.changeLayer(new MenuLayer());
		}
		
		return super.ccTouchesBegan(event);
	}


	


	private void init() {
		logo();// 展示logo
	}

	private void logo() {
		CCSprite logo = CCSprite.sprite("image/popcap_logo.png");
		logo.setPosition(winSize.width / 2, winSize.height / 2);
		this.addChild(logo);
		CCHide hide = CCHide.action();
		CCDelayTime delayTime = CCDelayTime.action(1);
		CCSequence sequence = CCSequence.actions(delayTime, delayTime, hide,
				delayTime, CCCallFunc.action(this,"loadWelcome"));
		logo.runAction(sequence);
		loadWelcome();

	}

	private void loadWelcome() {
		CCSprite bg = CCSprite.sprite("image/welcome.jpg");
		bg.setAnchorPoint(0, 0);
		this.addChild(bg);
		loading();
	}

	private void loading() {
		CCSprite pb = CCSprite.sprite("image/loading/loading_01.png");
		pb.setPosition(winSize.width / 2, 30);
		this.addChild(pb);
		String format = "image/loading/loading_%02d.png";
		// 02d 占位符 可以表示两位的整数 如果不足两位前面用0补足
		CCAction animate = CommonUtils.getAnimate(format,9,false);
		pb.runAction(animate);
		
		start = CCSprite.sprite("image/loading/loading_start.png");
		start.setPosition(winSize.width / 2, 30);
		start.setVisible(false);
		this.addChild(start);

	
	}


	


}
