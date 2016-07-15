package com.dalthow.etaron.objects;

import java.awt.Rectangle;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.dalthow.etaron.models.Identifier;
import com.dalthow.etaron.models.WorldObject;
import com.dalthow.etaron.utils.DrawUtils ;

/**
 * Etaron
 *
 * @author Trevi Awater
 **/

public class Elevator extends WorldObject
{
	// Declaration of the variables the Elevator needs to go up or down.
	private int direction;
	private int amount;
	
	private int timePassed;
	
	
	// Constructor that sets the variables for the WorldObject as well as the declared variables.
	public Elevator(float xPos, float yPos, int direction, int amount, Identifier id, boolean isSolid) 
	{
		super(xPos, yPos, id, isSolid);
		
		this.direction = direction;
		this.amount = amount;
		
		this.timePassed = 0;
	}

	
	// Default WorldObject methods.
	@Override
	public void tick(List<WorldObject> objectList) 
	{
		// Applying forces to the Elevator.
		xPos += xVel;
		yPos += yVel;
		
		
		// Switching which direction it goes in.
		if(timePassed < (amount * 32))
			yVel = (direction == 0 ? 1 : -1);
		
		if(timePassed > (amount * 32))
			yVel = (direction == 0 ? -1 : 1);
	
		timePassed++;
		
		if(timePassed > (amount * 32) * 2)
		{
			timePassed = 0;
			
			yPos += (direction == 0 ? -1 : 1);
		}
	}

	@Override
	public void render(Graphics graphics) 
	{
		// Switching the Elevator's color based on the direction.
		Color elevatorColor = DrawUtils.DrawHelper.ELEVATOR_DOWN.getColor();
	
		if(direction == 1)
			elevatorColor = DrawUtils.DrawHelper.ELEVATOR_UP.getColor();
		
		graphics.setColor(elevatorColor);
		graphics.fillRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
	}

	@Override
	public Rectangle getBounds() 
	{
		return new Rectangle((int)xPos, (int)yPos, 32, 32);
	}
}