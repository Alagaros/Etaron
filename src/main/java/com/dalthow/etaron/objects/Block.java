package com.dalthow.etaron.objects;

import java.awt.Rectangle;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.dalthow.etaron.framework.Identifier;
import com.dalthow.etaron.framework.WorldObject;

public class Block extends WorldObject
{
	// Constructor that sets the variables for the WorldObject.
	
	public Block(float xPos, float yPos, Identifier id, boolean isSolid) 
	{
		super(xPos, yPos, id, isSolid);
	}

	
	// Default WorldObject methods.
	
	@Override
	public void tick(List<WorldObject> objectList) 
	{
		
	}

	@Override
	public void render(Graphics graphics) 
	{
		// Switching the block color based on its type.
		
		Color blockColor = new Color(255, 255, 255);
		
		if(id == Identifier.LAVA)
		{
			blockColor = new Color(255, 0, 0);
		}
		
		
		// Drawing the block.
		
		graphics.setColor(blockColor);		
		graphics.fillRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
	}

	@Override
	public Rectangle getBounds() 
	{
		return new Rectangle((int)xPos, (int)yPos, 32, 32);
	}
}