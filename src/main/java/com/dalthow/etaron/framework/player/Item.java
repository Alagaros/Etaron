package com.dalthow.etaron.framework.player;

import java.awt.Rectangle;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import com.dalthow.etaron.framework.Identifier;
import com.dalthow.etaron.framework.WorldObject;

/**
 * Etaron
 *
 *
 * @author Dalthow Game Studios 
 * @class Item.java
 *
 **/

// TODO: Document this properly.

public class Item extends WorldObject
{
	//inventory.add(new Item(Run.resourceHandler.get(ImageResource.KEY, false), "Key", -8, 16));

	private Image icon;
	private Animation animation;
	private int offsetX, offsetY;
	
	public Item(float xPos, float yPos, Identifier id, boolean isSolid, Image icon, int offsetX, int offsetY, boolean hasAnimation) 
	{
		super(xPos, yPos, id, isSolid);

		if(hasAnimation)
		{
			SpriteSheet temporarySpriteSheet = new SpriteSheet(icon, 32, 32);
			animation = new Animation(temporarySpriteSheet, 60);
		}
		
		this.icon = icon;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

//	public void itemAction(Callable<Integer> function)
//	{
//		try 
//		{
//			function.call();
//		} 
//		
//		catch(Exception error) 
//		{
//			error.printStackTrace();
//		}
//	}

	public Image getIcon() {
		return icon;
	}


	public int getRenderX() {
		return offsetX;
	}

	public int getRenderY() {
		return offsetY;
	}

	@Override
	public void tick(List<WorldObject> objectList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics graphics) 
	{
		if(animation != null)
		{
			animation.draw(xPos, yPos);
		}
		
		else
		{
			icon.draw(xPos, yPos);
		}
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle((int)xPos, (int)yPos, 32, 32);
	}
}
