package com.dalthow.etaron.framework;

import java.awt.Rectangle;
import java.util.List;

import org.newdawn.slick.Graphics;


/**
 * Etaron
 *
 * 
 * @author Dalthow Game Studios 
 * @class WorldObject.java
 * 
 **/

public abstract class WorldObject
{
	// Declaration of the position and velocity.

	protected float xPos, yPos;
	protected float xVel, yVel;

	
	// Declaration of the status boolean's.

	protected boolean isJumping;
	protected boolean isFalling;
	protected boolean isSolid;

	
	// Declaration of the identifier.
	
	protected Identifier id;

	
	// Constructor that fills the declared variables.

	public WorldObject(float xPos, float yPos, Identifier id, boolean isSolid)
	{
		this.xPos = xPos;
		this.yPos = yPos;

		this.isJumping = false;
		this.isFalling = false;

		this.id = id;

		this.isSolid = isSolid;
	}

	
	// Used to make the object tick and render.

	public abstract void tick(List<WorldObject> objectList);
	public abstract void render(Graphics graphics);

	
	// Used for collision.

	public abstract Rectangle getBounds();

	
	// Getters.

	public float getPosX()
	{
		return xPos;
	}

	public float getPosY()
	{
		return yPos;
	}

	public float getVelX()
	{
		return xVel;
	}

	public float getVelY()
	{
		return yVel;
	}

	public boolean isJumping()
	{
		return isJumping;
	}

	public boolean isFalling()
	{
		return isFalling;
	}

	public Identifier getId()
	{
		return id;
	}

	public boolean isSolid()
	{
		return isSolid;
	}

	
	// Setters.

	public void setPosX(float xPos)
	{
		this.xPos = xPos;
	}

	public void setPosY(float yPos)
	{
		this.yPos = yPos;
	}

	public void setVelX(float xVel)
	{
		this.xVel = xVel;
	}

	public void setVelY(float yVel)
	{
		this.yVel = yVel;
	}

	public void setJumping(boolean isJumping)
	{
		this.isJumping = isJumping;
	}

	public void setFalling(boolean isFalling)
	{
		this.isFalling = isFalling;
	}

	public void setSolid(boolean isSolid)
	{
		this.isSolid = isSolid;
	}
}