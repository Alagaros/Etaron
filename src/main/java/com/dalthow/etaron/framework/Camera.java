package com.dalthow.etaron.framework;

import org.newdawn.slick.GameContainer;

/**
 * Etaron
 *
 * @author Trevi Awater
 **/

public class Camera 
{
	// Declaration of position variables.
	private float xPos, yPos;

	// Declaration of the viewport's width and height.
	private int width;
	private int height;
	
	
	// Constructor that fills declared variables.
	public Camera(float xPos, float yPos, GameContainer container)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		
		width = container.getWidth();
		height = container.getHeight();
	}
	
	
	// Makes the screen follow the player.
	public void tick(WorldObject object) 
	{
		xPos =- object.getPosX() + width / 2 - 16;
		yPos =- object.getPosY() + height / 2 - 16 - 25;
		
		if(object.id == Identifier.PLAYER)
		{
			yPos = - object.getPosY() + height / 2 - 32 - 25;
		}
	}

	
	// Getters.
	public float getPosX() 
	{
		return xPos;
	}
	public float getPosY()
	{
		return yPos;
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
}