/**
 * Etaron
 *
 *
 * @Author Dalthow Game Studios 
 * @Class Player.java
 *
 **/

package com.dalthow.etaron.objects;

import java.awt.Rectangle;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.dalthow.etaron.Run;
import com.dalthow.etaron.framework.Identifier;
import com.dalthow.etaron.framework.WorldObject;
import com.dalthow.etaron.states.Game;

public class Player extends WorldObject
{
	// Declaration

	private float playerWidth;
	private float playerHeight;

	public static final float jumpHeight = 15.75F;
	public static final float walkSpeed = 5F;

	public boolean hasKey;

	private boolean hasSaved;

	
	// Constructor

	public Player(float xPos, float yPos, Identifier id, boolean isSolid) throws SlickException
	{
		super(xPos, yPos, id, isSolid);

		playerWidth = 32F;
		playerHeight = 64F;

		hasKey = false;

		hasSaved = false;
	}

	
	// Gets triggered in the handler and then in the main method

	@Override
	public void tick(List<WorldObject> objectList)
	{
		xPos += xVel;
		yPos += yVel;

		if(yVel <= 0)
		{
			isFalling = true;
		}

		if(isFalling || isJumping)
		{
			yVel += Game.gravity;
			
			if(yVel >= Game.maximumVelocity)
			{
				yVel = Game.maximumVelocity;
			}
		}

		try
		{
			collision(objectList);
		}
		
		catch(SlickException error)
		{
			error.printStackTrace();
		}
	}
	
	@Override
	public void render(Graphics graphics)
	{
		graphics.setColor(new Color(0, 0, 255));
		graphics.fillRect(getBounds().x, getBounds().y, (int) playerWidth, (int) playerHeight);

		if(hasKey)
		{
			graphics.setColor(new Color(192, 192, 192));
			graphics.fillRect(getBounds().x - 7, getBounds().y + 24 + 1, 12, 12);
			graphics.fillRect(getBounds().x - 2, getBounds().y + 24 + 12, 4, 19);
			graphics.fillRect(getBounds().x - 7, getBounds().y + 24 + 16, 7, 3);
			graphics.fillRect(getBounds().x - 9, getBounds().y + 24 + 21, 7, 3);
			graphics.fillRect(getBounds().x - 6, getBounds().y + 24 + 26, 4, 3);
		}
	}

	
	// Gets triggered when the player collides with other objects

	private void collision(List<WorldObject> objectList) throws SlickException
	{
		for(int i = 0; i < objectList.size(); i++)
		{
			WorldObject temporaryObject = Game.objectHandler.objects.get(i);

			if(temporaryObject.isSolid() == true && temporaryObject != this)
			{
				if(getBoundsTop().intersects(temporaryObject.getBounds()))
				{
					yPos = temporaryObject.getPosY() + (playerHeight / 2);

					if(!(temporaryObject.getId() == Identifier.ELEVATOR))
					{
						yVel = 0;
					}
				}

				if(getBoundsBottom().intersects(temporaryObject.getBounds()))
				{
					yPos = temporaryObject.getPosY() - playerHeight;
					
					yVel = 0;
					
					isJumping = false;
					isFalling = false;
				}

				if(getBoundsRight().intersects(temporaryObject.getBounds()))
				{
					xPos = temporaryObject.getPosX() - playerWidth;
				}

				if(getBoundsLeft().intersects(temporaryObject.getBounds()))
				{
					xPos = temporaryObject.getPosX() + playerWidth;
				}
			}
		}
	}
	

	// Used for collision detection

	@Override
	public Rectangle getBounds()
	{
		return new Rectangle((int)xPos, (int)yPos, (int) playerWidth, (int)playerHeight);
	}

	public Rectangle getBoundsTop()
	{
		return new Rectangle((int)xPos + ((int)playerWidth / 4), (int)yPos, (int)playerWidth / 2, (int)playerHeight / 2);
	}

	public Rectangle getBoundsBottom()
	{
		return new Rectangle((int)xPos + ((int)playerWidth / 4), (int)yPos + ((int)playerHeight / 2), (int)playerWidth / 2, (int)playerHeight / 2 + 1);
	}

	public Rectangle getBoundsLeft()
	{
		return new Rectangle((int)xPos,(int)yPos + 3, 5, (int)playerHeight - 6);
	}

	public Rectangle getBoundsRight()
	{
		return new Rectangle((int)xPos + ((int)playerWidth - 5), (int)yPos + 3, 5, (int)playerHeight - 6);
	}

	
	// Used for figuring out what to tick
	
	public Rectangle getUpdateBounds()
	{
		return new Rectangle((int)xPos - 48, (int)yPos - 48, (int)playerWidth + 96, (int)playerHeight + 96);
	}
	
	
	// Used for figuring out what to render
	
	public Rectangle getRenderBounds()
	{
		return new Rectangle((int)xPos - (Run.width / 2), (int)yPos - (Run.height / 2), (int)playerWidth + Run.width, (int)playerHeight + Run.height);
	}
}