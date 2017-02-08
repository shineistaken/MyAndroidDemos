package com.example.pvz.bean;

import java.util.HashMap;
import java.util.Map;

import org.cocos2d.nodes.CCSprite;

import android.R.integer;

import com.example.pvz.layer.BaseLayer;

public class ShowPlant extends BaseLayer {
	static Map<Integer, HashMap<String, String>> db;
	private CCSprite showSprite;

	public CCSprite getShowSprite() {
		return showSprite;
	}

	public CCSprite getBgSprite() {
		return bgSprite;
	}

	private CCSprite bgSprite;

	// 查询数据库 获取植物
	static {
		// 模拟数据库
		db = new HashMap<Integer, HashMap<String, String>>();
		String format = "image/fight/chose/choose_default%02d.png";
		for (int i = 1; i <= 9; i++) {
			HashMap<String, String> value = new HashMap<String, String>();
			value.put("path", String.format(format, i));
			value.put("sun", 50 + "");
			db.put(i, value);

		}
	}

	public ShowPlant(int id) {
	
		HashMap<String, String> hashMap = db.get(id);
		String path = hashMap.get("path");
		showSprite = CCSprite.sprite(path);
		showSprite.setAnchorPoint(0, 0);

		bgSprite = CCSprite.sprite(path);
		bgSprite.setAnchorPoint(0, 0);
		bgSprite.setOpacity(150);
	}

}
