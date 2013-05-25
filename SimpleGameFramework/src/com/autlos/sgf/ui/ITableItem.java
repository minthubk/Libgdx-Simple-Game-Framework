package com.autlos.sgf.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public interface ITableItem {
	public void draw(SpriteBatch batch);
	
	public void drawShape(ShapeRenderer sr);

	public Vector2 getPosition();
	
	public void setPosition(float x, float y);

	public float getHeight();

	public float getWidth();
}
