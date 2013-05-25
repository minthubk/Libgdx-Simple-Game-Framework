package com.autlos.sgf;

/**
 * @author Autlos
 * 
 */
public abstract class WorldAbstract {
	public static enum GAME_STATE{
		RUNNING, PAUSED, GAME_OVER
	}
	
	public abstract void update(float delta);
	public abstract void checkCollisions();

	
}
