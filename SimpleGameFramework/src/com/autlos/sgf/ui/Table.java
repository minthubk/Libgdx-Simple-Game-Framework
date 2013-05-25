package com.autlos.sgf.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Table {
	private Vector2 position;
	private float width;
	private float height;
	private float separationX = 15f;
	private float separationY = 15f;
	private boolean visible = true;

	Array<ITableItem> items;

	public Table(Vector2 position) {
		this.position = position;
		items = new Array<ITableItem>();
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
	}

	public Table() {
		this(new Vector2(0f, 0f));
	}

	public ITableItem addItem(ITableItem item) {
		items.add(item);
		return item;
	}
	
	public ITableItem addRow(ITableItem item){
		float posX = width / 2 - item.getWidth() / 2;
		if (items.size == 0) {
			item.setPosition(posX, position.y - separationY);
		} else {
			ITableItem last = items.get(items.size - 1);
			float y = last.getPosition().y - item.getHeight() - separationY;
			item.setPosition(posX, y);
		}
		
		items.add(item);
		return item;
	}
	
	public ITableItem addColumn(ITableItem item){
		float posY = 0;
		float posX = 0;
		if (items.size == 0) {
			posY = position.y;
			posX = position.x;
		} else {
			ITableItem last = items.get(items.size - 1);
			posY = last.getPosition().y;
			posX = last.getPosition().x + last.getWidth() + separationX;
		}
		item.setPosition(posX, posY);
		items.add(item);
		
		return item;
	}
	
	public Array<ITableItem> getItems(){
		return items;
	}

	public void drawTable(SpriteBatch batch) {
		if (visible) {
			for (ITableItem item : items) {
				item.draw(batch);
			}
		}
	}

	public void setSeparation(float separationX, float separationY) {
		setSeparationX(separationX);
		setSeparationY(separationY);
	}

	public void setSeparationX(float separationX) {
		this.separationX = separationX;
	}

	public void setSeparationY(float separationY) {
		this.separationY = separationY;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

	public void drawShape(ShapeRenderer sr) {
		sr.rect(position.x, position.y, width, height);
		for (ITableItem item : items) {
			item.drawShape(sr);
		}
	}

}
