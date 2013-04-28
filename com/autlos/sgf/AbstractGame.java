package com.autlos.sgf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * @author Autlos
 * 
 */
public abstract class AbstractGame extends Game {
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
	public AbstractGame(String NAME, String VERSION, boolean debugMode) {
		AbstractGame.NAME = NAME;
		AbstractGame.VERSION = VERSION;
		AbstractGame.debugMode = debugMode;
	}

	/**
	 * Initialize coordinates, and scales for different resolutions.
	 * @param bigResolutionWidth
	 * @param bigResolutionHeight
	 * @param smallResolutionWidth
	 * @param smallResolutionHeight
	 */
	protected void init(int bigResolutionWidth, int bigResolutionHeight, int smallResolutionWidth, int smallResolutionHeight) {
		screenMidX = Gdx.graphics.getWidth() / 2;
		screenMidY = Gdx.graphics.getHeight() / 2;
		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();
		screenBotY = 0f;
		
		SCALE_X_BIG = screenWidth / bigResolutionWidth;
		SCALE_Y_BIG = screenHeight / bigResolutionHeight;
		SCALE_X_SMALL = screenWidth / smallResolutionWidth;
		SCALE_Y_SMALL = screenHeight / smallResolutionHeight;

		detectScale();
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
