package com.dalthow.etaron.objects;

import java.awt.Rectangle;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

import com.dalthow.etaron.models.Identifier;
import com.dalthow.etaron.models.WorldObject;

/**
 * Etaron
 *
 * @author Trevi Awater
 **/

public class Item extends WorldObject
{
	// Declaration of the icon or animation.
	private Image icon;
	private Sound pickupSound;
	private Animation animation;
	private int offsetX, offsetY;
	private boolean drawOnPlayer;

	// Constructor that sets the variables for the WorldObject as well as the declared variables.
	public Item(float xPos, float yPos, Identifier id, boolean isSolid, Image icon, Sound pickupSound, int offsetX, int offsetY, boolean hasAnimation, boolean drawOnPlayer) 
	{
		super(xPos, yPos, id, isSolid);

		// Creating the animation if there is one.
		if(hasAnimation)
		{
			SpriteSheet temporarySpriteSheet = new SpriteSheet(icon, 32, 32);
			animation = new Animation(temporarySpriteSheet, 60);
		}
		
		this.icon = icon;
		this.pickupSound = pickupSound;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.drawOnPlayer = drawOnPlayer;
	}

	// Default WorldObject methods.
	@Override
	public void tick(List<WorldObject> objectList) {}

	@Override
	public void render(Graphics graphics) 
	{
		// Checking if there is an animation, if not draw the static icon.
		if(animation != null)
			animation.draw(xPos, yPos);
		
		else
			icon.draw(xPos, yPos);
	}

	@Override
	public Rectangle getBounds() 
	{
		return new Rectangle((int)xPos, (int)yPos, 32, 32);
	}

	// Getters.
	public Image getIcon() 
	{
		return icon;
	}
	public Sound getPickupSound()
	{
		return pickupSound;
	}
	public int getRenderX() 
	{
		return offsetX;
	}
	public int getRenderY() 
	{
		return offsetY;
	}
	public boolean drawOnPlayer() 
	{
		return drawOnPlayer;
	}
}