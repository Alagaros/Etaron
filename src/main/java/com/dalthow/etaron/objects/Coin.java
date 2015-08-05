package com.dalthow.etaron.objects;

import java.awt.Rectangle;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.dalthow.etaron.framework.Identifier;
import com.dalthow.etaron.framework.WorldObject;

/**
 * Etaron
 *
 *
 * @author Dalthow Game Studios 
 * @class Coin.java
 *
 **/

public class Coin extends WorldObject
{
	// Declaration of the animation variables.
	
	private int state;
	private int tickToAnimationChange;

	private boolean hasFlipped;
	
	
	// Constructor that sets the variables for the WorldObject.
	
	public Coin(float xPos, float yPos, Identifier id, boolean isSolid) 
	{
		super(xPos, yPos, id, isSolid);
		
		state = 0;
		hasFlipped = false;
	}

	
	// Default WorldObject methods.
	
	@Override
	public void tick(List<WorldObject> objectList) 
	{
		// Checking if the animation is allowed to change.
		
		if(tickToAnimationChange == 0)
		{
			// Checking if the coin has flipped yet.
			
			if(state < 4 && hasFlipped == false)
			{
				state++;
			}

			else if(state > 0 && hasFlipped == true)
			{
				state--;

				if(state == 0)
				{
					hasFlipped = false;
				}
			}

			else
			{
				hasFlipped = true;
			}

			tickToAnimationChange = 3;
		}

		else
		{
			tickToAnimationChange--;
		}
	}

	@Override
	public void render(Graphics graphics) 
	{
		graphics.setColor(new Color(255, 255, 0));
		
		if(state == 0)
		{
			graphics.fillOval(getBounds().x, getBounds().y, 32, getBounds().height);
		}

		else if(state == 1)
		{
			graphics.fillOval(getBounds().x + 4, getBounds().y, 24, getBounds().height);
		}

		else if(state == 2)
		{
			graphics.fillOval(getBounds().x + 8, getBounds().y, 16, getBounds().height);
		}

		else if(state == 3)
		{
			graphics.fillOval(getBounds().x + 12, getBounds().y, 8, getBounds().height);
		}

		else if(state == 4)
		{
			graphics.fillOval(getBounds().x + 16, getBounds().y, 0, getBounds().height);
		}
	}

	@Override
	public Rectangle getBounds() 
	{
		return new Rectangle((int)xPos, (int)yPos, 32, 32);
	}
}
