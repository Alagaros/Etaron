package com.dalthow.etaron.objects;

import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.dalthow.etaron.Run;
import com.dalthow.etaron.framework.Identifier;
import com.dalthow.etaron.framework.WorldObject;
import com.dalthow.etaron.media.SoundResource;
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

	
	// Declaration of the Player's inventory.
	
	public LinkedList<Item> inventory = new LinkedList<Item>();

	
	// Constructor that sets the variables for the WorldObject.
	
	public Player(float xPos, float yPos, Identifier id, boolean isSolid) throws SlickException
	{
		super(xPos, yPos, id, isSolid);

		playerWidth = 32F;
		playerHeight = 64F;
	}

	
	// Default WorldObject methods.
	
	@Override
	public void tick(List<WorldObject> objectList)
	{
		// Applying forces to the Player's position.
		
		xPos += xVel;
		yPos += yVel;

		
		// Applying gravity to the Player's position.
		
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

		
		// Drawing the items the Player has.
		
		for(int i = 0; i < inventory.size(); i++) 
		{
			Image icon = inventory.get(i).getIcon();
			
			
			// Checking if an icon is set, if not go to the next entry.
			
			if(inventory.get(i).drawOnPlayer())
			{
				icon.draw(inventory.get(i).getRenderX() + getPosX(), inventory.get(i).getRenderY() + getPosY());
			}
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
			
			else if(getBounds().intersects(temporaryObject.getBounds()))
			{
				// Resetting the level because the player failed.
				
				if(temporaryObject.getId() == Identifier.LAVA || temporaryObject.getId() == Identifier.BULLET)
				{
					Game.objectHandler.reloadLevel();
					Run.resourceHandler.sounds.get(SoundResource.FAILURE).play();
				}
				
				
				else if(temporaryObject.getId() == Identifier.COIN || temporaryObject.getId() == Identifier.KEY)
				{
					Game.objectHandler.objects.remove(temporaryObject);
					inventory.add((Item)temporaryObject);
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
     * getRenderBounds Used to figure out what part of the level to render.
     * 
     * @return {Rectangle}
     */
	public Rectangle getRenderBounds()
	{
		return new Rectangle((int)xPos - (Run.width / 2), (int)yPos - (Run.height / 2), (int)playerWidth + Run.width, (int)playerHeight + Run.height);
	}

	
	/**
     * hasItem Used to figure out if the user has a specific item, if thats true there is also an option to remove the item.
     * 
     * @param  {Identifier} id       The id the Item should have.
     * @param  {boolean} consumeItem Whether the Item should be removed if the user has it.
     * 
     * @return {boolean}
     */
	public boolean hasItem(Identifier id, boolean consumeItem) 
	{
		for(int i = 0; i < inventory.size(); i++) 
		{
			if(inventory.get(i).getId() == id)
			{
				if(consumeItem)
				{
					inventory.remove(i);
				}
				
				return true;
			}
		}
		
		return false;
	}
}