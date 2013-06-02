package com.autlos.sgf.tests;

import com.autlos.sgf.BasicLabel;
import com.autlos.sgf.GuiTextElement;
import com.autlos.sgf.ui.Table;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

public class TableTest {
	public static void main(String []args){
		BasicLabel label = new BasicLabel(new BitmapFont(), "asdf");
		GuiTextElement guiElement = new GuiTextElement(null, null, "asdfr");
		
		Table t = new Table(new Vector2());
		t.addItem(guiElement);
		t.addItem(label);
		
		
	}
}
