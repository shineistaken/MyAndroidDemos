package com.example.pvz.layer;

import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCSprite;

import com.example.pvz.CommonUtils.CommonUtils;


import android.util.Log;

public class MenuLayer extends BaseLayer {
	public MenuLayer() {
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		CCSprite menuSprite = CCSprite.sprite("image/menu/main_menu_bg.jpg");
		menuSprite.setAnchorPoint(0, 0);
		this.addChild(menuSprite);

		CCSprite normalSprite = CCSprite
				.sprite("image/menu/start_adventure_default.png");
		CCSprite selectedSprite = CCSprite
				.sprite("image/menu/start_adventure_press.png");

		CCMenuItemSprite items = CCMenuItemSprite.item(normalSprite,
				selectedSprite, this, "click");
		CCMenu menu = CCMenu.menu(items);
		menu.setScale(0.5f);  // 让菜单 缩放
		menu.setPosition(winSize.width / 2-25, winSize.height / 2 - 110);
		menu.setRotation(4.5f);  
		this.addChild(menu);

	}

	public void click(Object obj) {
		System.out.println("我被点击了" );
		CommonUtils.changeLayer(new FightLayer());
		
	}
}
