package com.dalthow.etaron.objects;

import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.dalthow.etaron.framework.Identifier;
import com.dalthow.etaron.framework.WorldObject;
import com.dalthow.etaron.states.Game;

/**
 * Etaron
 *
 *
 * @author Dalthow Game Studios 
 * @class Block.java
 *
 **/

public class Block extends WorldObject
{
	// Declaring a temporary player.
	
	private Player temporaryPlayer;
	
	
	// Constructor that sets the variables for the WorldObject.
	
	public Block(float xPos, float yPos, Identifier id, boolean isSolid) 
	{
		super(xPos, yPos, id, isSolid);
	}

	
	// Default WorldObject methods.
	
	@Override
	public void tick(List<WorldObject> objectList) 
	{
		temporaryPlayer = (Player)Game.cameraFocus;
		
		if(id == Identifier.DOOR)
		{
			// Making sure the user can touch the block.
			
			Rectangle touchBounds = getBounds();
			touchBounds.grow(1, 1);
			
			if(temporaryPlayer.getBounds().intersects(touchBounds))
			{
				// Checking if the player has a key, if so delete it and open the door.
				
				if(temporaryPlayer.hasItem(Identifier.KEY, true))
				{
					Game.objectHandler.objects.remove(this);
				}
			}
		}
		
		else if(id == Identifier.JELLY)
		{
			Rectangle touchBounds = getBounds();
			touchBounds.grow(0, 1);
			
			Random rand = new Random();
			
			if(temporaryPlayer.getBoundsTop().intersects(touchBounds))
			{
				temporaryPlayer.setVelY((float) (Player.jumpHeight * 1.75));
			}
			
			else if(temporaryPlayer.getBoundsBottom().intersects(touchBounds))
			{
				temporaryPlayer.setVelY((float) (-Player.jumpHeight * 1.75));
			}
		}
	}

	@Override
	public void render(Graphics graphics) 
	{
		// Switching the Block's color based on the Identifier.
		
		Color blockColor = new Color(255, 255, 255);
		
		if(id == Identifier.LAVA)
		{
			blockColor = new Color(255, 0, 0);
		}
		
		else if(id == Identifier.FLAG)
		{
			blockColor = new Color(0, 255, 0);
		}
		
		else if(id == Identifier.DOOR)
		{
			blockColor = new Color(139, 69, 19);
		}
		
		else if(id == Identifier.JELLY)
		{
			blockColor = new Color(255, 175, 175);
		}
		
		else if(id == Identifier.DECOR)
		{
			blockColor = new Color(128, 128, 128);
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