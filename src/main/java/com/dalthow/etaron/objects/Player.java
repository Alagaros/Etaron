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

/**
 * Etaron
 *
 *
 * @author Dalthow Game Studios 
 * @class Player.java
 *
 **/

public class Player extends WorldObject
{
	// Declaration of the player's width and height.

	private float playerWidth;
	private float playerHeight;

	
	// Declaration of some constant variables.
	
	public static final float jumpHeight = 15.75F;
	public static final float walkSpeed = 5F;

	public boolean hasKey;

	
	// Constructor that sets the variables for the WorldObject.
	
	public Player(float xPos, float yPos, Identifier id, boolean isSolid) throws SlickException
	{
		super(xPos, yPos, id, isSolid);

		playerWidth = 32F;
		playerHeight = 64F;

		hasKey = false;
	}

	
	// Default WorldObject methods.
	
	@Override
	public void tick(List<WorldObject> objectList)
	{
		// Applying forces to the player's position.
		
		xPos += xVel;
		yPos += yVel;

		
		// Applying gravity to the players position.
		
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
		// Drawing the player.
		
		graphics.setColor(new Color(0, 0, 255));
		graphics.fillRect(getBounds().x, getBounds().y, (int) playerWidth, (int) playerHeight);

		
		// Drawing the key if the player has one.
		
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
	
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle((int)xPos, (int)yPos, (int) playerWidth, (int)playerHeight);
	}

	
	/**
     * drawImageAtCenter Performs a certain action when the player collides with a certain WorldObject.
     *
     * @param  {List<WorldObject>} objectList The list with WorldObject's we should check collision on.
     * 
     * @return {void}
     */
	private void collision(List<WorldObject> objectList) throws SlickException
	{
		// Looping trough all of the objects in the List.
		
		for(int i = 0; i < objectList.size(); i++)
		{
			WorldObject temporaryObject = Game.objectHandler.objects.get(i);

			
			// Checking if the object is solid and not a Player.
			
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
	
	
	/**
     * getBoundsTop Used to check if we are touching a wall on the top.
     * 
     * @return {Rectangle}
     */
	public Rectangle getBoundsTop()
	{
		return new Rectangle((int)xPos + ((int)playerWidth / 4), (int)yPos, (int)playerWidth / 2, (int)playerHeight / 2);
	}

	
	/**
     * getBoundsBottom Used to check if we are touching a wall on the bottom.
     * 
     * @return {Rectangle}
     */
	public Rectangle getBoundsBottom()
	{
		return new Rectangle((int)xPos + ((int)playerWidth / 4), (int)yPos + ((int)playerHeight / 2), (int)playerWidth / 2, (int)playerHeight / 2 + 1);
	}

	
	/**
     * getBoundsLeft Used to check if we are running into a wall on the left.
     * 
     * @return {Rectangle}
     */
	public Rectangle getBoundsLeft()
	{
		return new Rectangle((int)xPos,(int)yPos + 3, 5, (int)playerHeight - 6);
	}

	
	/**
     * getBoundsRight Used to check if we are running into a wall on the right.
     * 
     * @return {Rectangle}
     */
	public Rectangle getBoundsRight()
	{
		return new Rectangle((int)xPos + ((int)playerWidth - 5), (int)yPos + 3, 5, (int)playerHeight - 6);
	}

	
	/**
     * getUpdateBounds Used to figure out what part of the level to tick.
     * 
     * @return {Rectangle}
     */
	public Rectangle getUpdateBounds()
	{
		return new Rectangle((int)xPos - 48, (int)yPos - 48, (int)playerWidth + 96, (int)playerHeight + 96);
	}
	
	
	/**
     * getRenderBounds Used to figure out what part of the level to render.
     * 
     * @return {Rectangle}
     */
	public Rectangle getRenderBounds()
	{
		return new Rectangle((int)xPos - (Run.width / 2), (int)yPos - (Run.height / 2), (int)playerWidth + Run.width, (int)playerHeight + Run.height);
	}
}