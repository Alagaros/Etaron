/**
 * Etaron
 *
 *
 * @author Dalthow Game Studios
 * @class Pixel.java
 *
 **/

package com.dalthow.etaron.framework.editor;

import org.newdawn.slick.Color;

public class Pixel
{
	// Declaration of the Pixel's position.

	private int xPos, yPos;


	// Declaration of the pixel's color.

	private Color color;


	// Constructor that sets the Pixel's position.

	public Pixel(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
	}


	// Getters.
	
	public Color getColor()
	{
		return color;
	}

	public int getX()
	{
		return xPos;
	}

	public int getY()
	{
		return yPos;
	}


	// Setters.

	public void setColor(Color color)
	{
		this.color = color;
	}
}
