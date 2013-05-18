package com.autlos.sgf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Autlos
 * 
 */
public abstract class ScreenAbstract implements Screen {
	protected TextureRegion background;
	protected SpriteBatch batch;

	/**
	 * Creates a Screen with a SpriteBatch and a background.
	 * 
	 * @param background
	 */
	public ScreenAbstract(TextureRegion background) {
		this.background = background;
		if (batch == null) {
			batch = new SpriteBatch();
		}
	}

	/**
	 * Calls glClear and draws the background.
	 */
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(background, 0, 0, GameAbstract.screenWidth, GameAbstract.screenHeight);
		batch.end();

	}

	/**
	 * Disposes the SpriteBatch
	 */
	public void dispose() {
		batch.dispose();
	}

}
