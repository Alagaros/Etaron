package com.dalthow.etaron.framework.player;

import java.util.concurrent.Callable;

import org.newdawn.slick.Image;

import com.dalthow.etaron.Run;
import com.dalthow.etaron.media.ImageResource;

/**
 * Etaron
 *
 *
 * @author Dalthow Game Studios 
 * @class Item.java
 *
 **/

// TODO: Document this properly.

public class Item 
{
	//inventory.add(new Item(Run.resourceHandler.get(ImageResource.KEY, false), "Key", -8, 16));
	
	private Image icon;
	private String name;
	private int renderX, renderY;

	public Item(Image icon, String name, int renderX, int renderY) {

		this.icon = icon;
		this.name = name;
		this.renderX = renderX;
		this.renderY = renderY;
	}
	public void itemAction(Callable<Integer> function)
	{
		try 
		{
			function.call();
		} 
		
		catch(Exception error) 
		{
			error.printStackTrace();
		}
	}

	public Image getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	public int getRenderX() {
		return renderX;
	}

	public int getRenderY() {
		return renderY;
	}
}
