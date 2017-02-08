package com.example.pvz;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import com.example.pvz.layer.BaseLayer;
import com.example.pvz.layer.WelcomeLayer;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	private CCDirector director;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CCGLSurfaceView surfaceView = new CCGLSurfaceView(this);
		setContentView(surfaceView);

		director = CCDirector.sharedDirector();
		director.attachInView(surfaceView);
		director.setDisplayFPS(true);
		director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
		director.setScreenSize(480, 320);
		
		CCScene scene = CCScene.node();
		scene.addChild(new WelcomeLayer());
		director.runWithScene(scene);
		

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
			super.onResume();
			director.resume();
	}
	@Override
	protected void onPause() {
	// TODO Auto-generated method stub
		super.onPause();
		director.onPause();
		
}
	@Override
	protected void onDestroy() {
	// TODO Auto-generated method stub
		super.onDestroy();
}
}
