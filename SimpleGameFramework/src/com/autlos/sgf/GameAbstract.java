package com.autlos.sgf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * @author Autlos
 * 
 */
public abstract class GameAbstract extends Game {
	public static enum TARGET_SIZE {
		BIG, SMALL
	}

	// Coordinates which otherwise, you would need to calculate 10000 times during the game- 
	public static float screenMidX;
	public static float screenMidY;
	public static float screenHeight;
	public static float screenWidth;
	public static float screenBotY;
	
	// Scales Y&X for big and small resolution. If you want to add more resolutions, just edit this class and add them.
	public static float SCALE_X_BIG;
	public static float SCALE_Y_BIG;
	public static float SCALE_X_SMALL;
	public static float SCALE_Y_SMALL;
	
	// The actual scale for the game
	public static float scaleX;
	public static float scaleY;
	public static TARGET_SIZE targetSize;

	public static String NAME;
	public static String VERSION;
	
	private float bigResolutionHeight;
	private float bigResolutionWidth;
	private float smallResolutionWidth;
	private float smallResolutionHeight;
	
	/**
	 * You may use the debugMode or not. For example at the WorldRenderer, draw rectangles over the textures if debugMode is true.
	 */
	public static boolean debugMode;

	/**
	 * Initializes the name of the app, the version and whether it is debugMode or not.
	 * @param NAME
	 * @param VERSION
	 * @param debugMode
	 */
	public GameAbstract(String NAME, String VERSION, boolean debugMode) {
		GameAbstract.NAME = NAME;
		GameAbstract.VERSION = VERSION;
		GameAbstract.debugMode = debugMode;
	}

	
	public void init(){
		this.init(bigResolutionWidth, bigResolutionHeight, smallResolutionWidth, smallResolutionHeight);
	}
	
	/**
	 * Initialize coordinates, and scales for different resolutions.
	 * @param bigResolutionWidth
	 * @param bigResolutionHeight
	 * @param smallResolutionWidth
	 * @param smallResolutionHeight
	 */
	protected void init(float bigResolutionWidth, float bigResolutionHeight, float smallResolutionWidth, float smallResolutionHeight) {
		this.bigResolutionWidth = bigResolutionWidth;
		this.bigResolutionHeight = bigResolutionHeight;
		this.smallResolutionWidth = smallResolutionWidth;
		this.smallResolutionHeight = smallResolutionHeight;
		
		screenMidX = Gdx.graphics.getWidth() / 2;
		screenMidY = Gdx.graphics.getHeight() / 2;
		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();
		screenBotY = 0f;
		
		SCALE_X_BIG = screenWidth / this.bigResolutionWidth;
		SCALE_Y_BIG = screenHeight / this.bigResolutionHeight;
		SCALE_X_SMALL = screenWidth / this.smallResolutionWidth;
		SCALE_Y_SMALL = screenHeight / this.smallResolutionHeight;

		detectScale();
	}
	
	public void catchBackButton(boolean catchBack){
		Gdx.input.setCatchBackKey(catchBack);
	}
	
	public void catchMenuButton(boolean catchHome){
		Gdx.input.setCatchMenuKey(catchHome);
	}
	
	public static void switchDebug() {
		debugMode = !debugMode;
	}

	public static void log(String message) {
		if (debugMode)
			Gdx.app.log(NAME + " " + VERSION, message);
	}
	
	/**
	 * Use this method to detect the target scale. 
	 * If target resolutions are 480-320 and 800-480, and the device's resolution is  528-352, target would be 480-320 and scale 1.1f
	 * If device's resolution is 720-432, target would be 800-480 and scale 0.9f.
	 * See the comment below to look an implementation example.
	 */
	protected abstract void detectScale();
	/*
	public void detectScale() {
		if (Math.abs(1 - SCALE_X_BIG) <= Math.abs(1 - SCALE_X_SMALL)) {
			scaleX = SCALE_X_BIG;
			scaleY = SCALE_Y_BIG;
			targetSize = TARGET_SIZE.BIG;
		} else {
			scaleX = SCALE_X_SMALL;
			scaleY = SCALE_Y_SMALL;
			targetSize = TARGET_SIZE.SMALL;
		}
	}
	*/
}
