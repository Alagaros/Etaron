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
 * @class Projectile.java
 * 
 **/

public class Projectile extends WorldObject
{
	// Declaration of the Projectile's speed and lifetime.
	
	private float xVel, yVel;
	
	private int timeInWorld, maxLifeSpawn;
	
	
	// Constructor that sets the variables for the WorldObject as well as the declared variables.
	
	public Projectile(float xPos, float yPos, float xSpeed, float ySpeed, int maxLifeSpawn, Identifier id, boolean isSolid) 
	{
		super(xPos, yPos, id, isSolid);
		
		this.xVel = xSpeed;
		this.yVel = ySpeed;
		
		this.maxLifeSpawn = maxLifeSpawn;
		this.timeInWorld = 0;
	}

	
	// Default WorldObject methods.
	
	@Override
	public void tick(List<WorldObject> objectList) 
	{
		// Applying forces to the Projectile's position.
		
		xPos += xVel;
		xPos += yVel;
		

		// Makes sure that the Projectile disappears after a certain time.
		
		if(timeInWorld >= maxLifeSpawn)
		{
			objectList.remove(this);
		}
		
		else
		{
			timeInWorld++;
		}
		
		
		// Checking if the Projectile hits something.
		
		for(int i = 0; i < objectList.size(); i++)
		{
			WorldObject temporaryObject = objectList.get(i);
			
			
			// Remove the Projectile from the world if the hit object is solid and not a Player.
			
			if(temporaryObject.isSolid() && temporaryObject.getId() != Identifier.PLAYER)
			{
				if(getBounds().intersects(temporaryObject.getBounds()))
				{
					objectList.remove(this);
				}
			}
		}
	}

	@Override
	public void render(Graphics graphics)
	{
		// Switching the shape and color based on the Identifier.
		
		if(id == Identifier.BULLET)
		{
			graphics.setColor(new Color(255, 255, 255));
			graphics.fillRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
		}
	}
	
	@Override
	public Rectangle getBounds() 
	{
		if(id == Identifier.BULLET)
		{
			return new Rectangle((int)xPos, (int)yPos, 8, 8);
		}
		
		return null;
	}
}