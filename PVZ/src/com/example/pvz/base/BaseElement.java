package com.example.pvz.base;

import org.cocos2d.nodes.CCSprite;

public abstract class BaseElement extends CCSprite {

	private DieListener dieListener;
	/**
	 * @param filepath
	 *            sprite的初始化图片资源路径
	 */
	public BaseElement(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}
/**
 * 基本动作，必须实现
 */
	public abstract void baseAction();
/**
 * 销毁元素
 */
	public void destroy() {
		if (dieListener!=null) {
			dieListener.die();
		}
		this.removeSelf();
	}
	public interface DieListener{
		void die();
	}
	public void setDieListener (DieListener dieListener) {
		this.dieListener = dieListener;
	}

}
