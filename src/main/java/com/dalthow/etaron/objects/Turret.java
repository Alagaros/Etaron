package com.dalthow.etaron.objects;

import java.awt.Rectangle;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.dalthow.etaron.Run;
import com.dalthow.etaron.framework.Identifier;
import com.dalthow.etaron.framework.WorldObject;
import com.dalthow.etaron.media.SoundResource;
import com.dalthow.etaron.states.Game;
import com.dalthow.etaron.utils.DrawUtils ;

/**
 * Etaron
 *
 *
 * @author Dalthow Game Studios 
 * @class Turret.java
 *
 **/

public class Turret extends WorldObject
{
	// Declaration of the variables the Turret needs to decide if it should fire and where.

	private int direction;
	private int tickToFire;

	private boolean shouldFire;
	
	
	// Declaration of the variables required to make the Turret look nice.
	
	private int backFire;

	private Color detectionColor;

	
	// Constructor that sets the variables for the WorldObject as well as the declared variables.
	
	public Turret(float xPos, float yPos, Identifier id, boolean isSolid)
	{
		super(xPos, yPos, id, isSolid);

		direction = 0;
		tickToFire = 45;
		detectionColor = new Color(255, 0, 0);
	}
	

	// Default WorldObject methods.
	
	@Override
	public void tick(List<WorldObject> objectList)
	{
		boolean detected = false;
		
		for(int i = 0; i < Game.objectHandler.players.size(); i++)
		{
			Player player = (Player)Game.objectHandler.players.get(i);

			if(player.getBounds().intersects(getVisionLeft()) || player.getBounds().intersects(getVisionRight()))
			{
				shouldFire = true;

				if(player.getBounds().intersects(getVisionLeft()))
				{
					direction = -1;
				}
				
				else if(player.getBounds().intersects(getVisionRight()))
				{
					direction = 1;
				}
			}
			
			else
			{
				shouldFire = false;
			}

			if(shouldFire == true)
			{
				detected = true;
			}
		}

		if(detectionColor.equals(new Color(255, 0, 0)))
		{
			if(tickToFire <= 0)
			{
				switch(direction)
				{
					case -1: Game.objectHandler.addObject(new Projectile(xPos - 24, yPos + 8, (direction * 7.5F), 0, 300, Identifier.BULLET, false));
						
					break;
					
					case 1: Game.objectHandler.addObject(new Projectile(xPos + 52, yPos + 8, (direction * 7.5F), 0, 300, Identifier.BULLET, false));
					
					break;
				}
				
				backFire = 10;
				tickToFire = 45;

				Run.resourceHandler.sounds.get(SoundResource.BULLET).play();
			}

			else
			{
				if(backFire != 0)
				{
					backFire--;
				}

				tickToFire--;
			}
		}

		if(detected == true)
		{
			detectionColor = new Color(255, 0, 0);
		}

		else
		{
			detectionColor = new Color(0, 255, 0);
		}
	}

	@Override
	public void render(Graphics graphics)
	{
		graphics.setColor(DrawUtils.DrawHelper.TURRET.getColor());

		graphics.fillRect(getBounds().x, getBounds().y, 32, 32);
		graphics.fillRect(getBounds().x + 8, getBounds().y + 32, 16, 8);

		switch(direction)
		{
			case -1: graphics.fillRect(getBounds().x - 20 + backFire, getBounds().y + 10, 20, 8);
					 graphics.setColor(detectionColor);
					 graphics.fillRect(getBounds().x + 20, getBounds().y + 4, 8, (int)(tickToFire / 1.85F));
			break;

			case 1: graphics.fillRect(getBounds().x + 32 - backFire, getBounds().y + 10, 20, 8);
					graphics.setColor(detectionColor);
					graphics.fillRect(getBounds().x + 4, getBounds().y + 4, 8, (int)(tickToFire / 1.85F));
			break;
		}
	}

	@Override
	public Rectangle getBounds()
	{
		return new Rectangle((int) xPos, (int)yPos, 32, 32);
	}


	/**
     * getVisionLeft Used to check if a Player is in the area left from the Turret.
     * 
     * @return {Rectangle}
     */
	public Rectangle getVisionLeft()
	{
		return new Rectangle((int)xPos - 290, (int)yPos - 128, 288, 216);
	}

	
	/**
     * getVisionRight Used to check if a Player is in the area right from the Turret.
     * 
     * @return {Rectangle}
     */
	public Rectangle getVisionRight()
	{
		return new Rectangle((int)xPos + 34, (int)yPos - 128, 288, 216);
	}
}