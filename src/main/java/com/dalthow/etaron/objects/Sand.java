package com.dalthow.etaron.objects;

import java.awt.Rectangle;
import java.util.List;

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
 * @class Sand.java
 *
 **/

public class Sand extends WorldObject
{
	// Declaration of the variables the Sand needs to get triggered and fall down.
	
	private boolean isTriggered;
	
	
	// Constructor that sets the variables for the WorldObject as well as the declared variables.
	
	public Sand(float xPos, float yPos, Identifier id, boolean isSolid) 
	{
		super(xPos, yPos, id, isSolid);
		
		this.isTriggered = false;
	}

	
	// Default WorldObject methods.
	
	@Override
	public void tick(List<WorldObject> objectList) 
	{
		// Applying forces to the Sand.
		
		xPos += xVel;
		yPos += yVel;
		
		
		// Applying gravity to the Sand's position if its set off.
		
		if(isTriggered)
		{
			if(yVel <= 0)
			{
				isFalling = true;
			}

			if(isFalling)
			{
				yVel += Game.gravity;

				if(yVel >= Game.maximumVelocity)
				{
					yVel = Game.maximumVelocity;
				}
			}
		}
		
		
		// Stopping the falling if the Sand hits a solid object.

		if(isTriggered)
		{
			for(int i = 0; i < objectList.size(); i++)
			{
				WorldObject temporaryObject = objectList.get(i);
				
				if(temporaryObject.isSolid() == true && temporaryObject != this)
				{
					if(getBoundsBottom().intersects(temporaryObject.getBounds()))
					{
						if(temporaryObject.getId() == Identifier.SAND)
						{
							Sand temporarySand = (Sand)temporaryObject;
							
							temporarySand.isTriggered = true;
						}
						
						yPos = temporaryObject.getPosY() - 31;
						yVel = 0;

						isFalling = false;
					}
				}
				
				else if(temporaryObject.getId() == Identifier.LAVA)
				{
					if(getBounds().intersects(temporaryObject.getBounds()))
					{
						objectList.remove(this);
					}
				}
			}
		}
		
		
		// Making sure the user can touch the block.
		
		Rectangle touchBounds = getBounds();
		touchBounds.grow(1, 1);
		
		Player temporaryPlayer = (Player)Game.cameraFocus;
		
		
		// Trigger the fall when the user touches this block.
		
		if(temporaryPlayer.getBounds().intersects(touchBounds))
		{
			isTriggered = true;
		}
	}

	@Override
	public void render(Graphics graphics) 
	{
		graphics.setColor(new Color(139, 139, 0));
		graphics.fillRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
	}

	@Override
	public Rectangle getBounds() 
	{
		return new Rectangle((int)xPos, (int)yPos, 32, 32);
	}
	
	
	/**
     * getBoundsBottom Used to check if the Sand is touching a solid WorldObject on the bottom.
     * 
     * @return {Rectangle}
     */
	public Rectangle getBoundsBottom()
	{
		return new Rectangle((int)xPos + 8, (int)yPos + 24, 16, 8);
	}
	
	
	/**
     * getBoundsTop Used to check if the Sand is touching a solid WorldObject on the top.
     * 
     * @return {Rectangle}
     */
	public Rectangle getBoundsTop()
	{
		return new Rectangle((int)xPos + 8, (int)yPos, 16, 8);
	}
}