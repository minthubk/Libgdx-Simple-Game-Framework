package com.autlos.sgf.screens;

import com.autlos.sgf.GuiElement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.Array;

public class ScreenController extends InputAdapter{
	public interface Action{
		public void touchUp();
		public void touchDown();
	}
	
	private Array<GuiElement> items;
	
	public ScreenController() {
		items = new Array<GuiElement>();
	}
	
	public void addItems(Array<GuiElement> items){
		this.items.addAll(items);
	}
	
	public void addItem(GuiElement item){
		items.add(item);
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		float y = Gdx.graphics.getHeight() - screenY;
		for (GuiElement item : items) {
			if (item.isTouchingElement(screenX, y)) {
				item.touchDown();
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		float y = Gdx.graphics.getHeight() - screenY;
		for (GuiElement item : items) {
			if (item.isTouchingElement(screenX, y)) {
				item.touchUp();
			}
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
}
